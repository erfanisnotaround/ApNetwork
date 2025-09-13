// org/example/server/commanding/handlers/AddingUserToTheBoardHandler.java
package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.core.domain.Board;
import org.example.core.domain.User;
import org.example.passingInformation.entry.AddingUserToTheBoardPass;
import org.example.passingInformation.exit.AddingUserToTheBoardRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

public final class AddingUserToTheBoardHandler
        implements CommandHandler<AddingUserToTheBoardPass, AddingUserToTheBoardRes> {

    @Override
    public void handle(RequestContext ctx, EnvelopeData<AddingUserToTheBoardPass, AddingUserToTheBoardRes> env) {
        // 1) auth
        User me = ctx.requireUser(env);
        if (me == null) return; // ctx.requireUser already responded with failure

        // 2) input
        AddingUserToTheBoardPass pass = env.getDataPas();
        if (pass == null || isBlank(pass.getBoardId()) || isBlank(pass.getInvitee())) {
            ctx.fail(env, "bad_request", "boardId and invitee are required");
            return;
        }

        // 3) board exists?
        Board board = ctx.services.boards.byId(pass.getBoardId());
        if (board == null) {
            ctx.fail(env, "not_found", "board_not_found");
            return;
        }

        System.out.println(board.getMembers() + " " + me.getId());
        boolean allowed = board.getOwnerId().equals(me.getId()) || board.getMembers().contains(me.getId());
        if (!allowed) {
            ctx.fail(env, "forbidden", "not_authorized_to_invite");
            return;
        }

        // 5) resolve invitee (prefer username; fallback to id if repo supports it)
        User invitee = ctx.services.users.byUsername(pass.getInvitee());
        if (invitee == null) {
            // if your UsersRepo also supports byId, allow passing a raw id:
            try { invitee = ctx.services.users.byId(pass.getInvitee()); } catch (Throwable ignore) {}
        }
        if (invitee == null) {
            ctx.fail(env, "not_found", "invitee_not_found");
            return;
        }

        // 6) update repo (idempotent add)
        Board updated = ctx.services.boards.InviteToBoard(board.getBoardId(), me.getId(), invitee.getId());
        if (updated == null) {
            ctx.fail(env, "conflict", "cannot_add_member");
            return;
        }

        // 7) respond
        AddingUserToTheBoardRes res = new AddingUserToTheBoardRes();
        res.setBoardId(updated.getBoardId());
        res.setInviteeId(invitee.getId());
        ctx.ok(env, res, "invite_added");

        // 8) (optional) notify board members + invitee
        try {
            // If your NotificationPublisher exposes these helpers, use them:
            // ctx.services.notifier.notifyBoard(updated.getBoardId(), "member_added",
            //         Map.of("boardId", updated.getBoardId(), "userId", invitee.getId()));

            // Or at least notify the invitee:
            // ctx.services.notifier.notifyUser(invitee.getId(), "added_to_board",
            //         Map.of("boardId", updated.getBoardId()));
        } catch (Exception ignore) { /* non-fatal */ }
    }

    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
}
