package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

// org/example/server/commanding/handlers/DeleteTaskHandler.java

import org.example.EnvelopeData;
import org.example.core.domain.Board;
import org.example.core.domain.Task;
import org.example.core.domain.User;
import org.example.passingInformation.entry.DeleteTaskPass;
import org.example.passingInformation.exit.DeleteTaskRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

public final class DeleteTaskHandler implements CommandHandler<DeleteTaskPass, DeleteTaskRes> {
    @Override
    public void handle(RequestContext ctx, EnvelopeData<DeleteTaskPass, DeleteTaskRes> env) {
        // 1) auth
        User me = ctx.requireUser(env);
        if (me == null) return;

        // 2) validate input
        var pass = env.getDataPas();
        if (pass == null || isBlank(pass.getTaskId())) {
            ctx.fail(env, "bad_request", "taskId_required");
            return;
        }

        // 3) locate task
        Task t = ctx.services.tasks.byId(pass.getTaskId());
        if (t == null) { ctx.fail(env, "not_found", "task_not_found"); return; }

        // 4) authz via board
        Board b = ctx.services.boards.byId(t.getBoardId());
        if (b == null) { ctx.fail(env, "not_found", "board_not_found"); return; }
        if (!hasAccess(b, me.getId())) { ctx.fail(env, "forbidden", "no_access_to_board"); return; }

        // 5) delete
        boolean ok = ctx.services.tasks.delete(t.getId());
        if (!ok) { ctx.fail(env, "conflict", "cannot_delete_task"); return; }

        // 6) respond
        var res = new DeleteTaskRes();
        res.setTaskId(t.getId());
        ctx.ok(env, res, "task_deleted");

        // 7) (optional) notify board members
        // ctx.services.notifier.notifyBoard(b.getBoardId(), "task_deleted", Map.of("taskId", t.getId()));
    }

    private static boolean hasAccess(Board b, String userId) {
        return b.getOwnerId().equals(userId) || b.getMembers().contains(userId);
    }
    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
}
