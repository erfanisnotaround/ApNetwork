package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.UpdateTaskStatusPass;
import org.example.passingInformation.exit.UpdateTaskStatusRes;
import org.example.sameInfoes.TaskStatus;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

// org/example/server/commanding/handlers/UpdateTaskStatusHandler.java

import org.example.EnvelopeData;
import org.example.core.domain.Board;
import org.example.core.domain.Task;

import org.example.core.domain.User;

import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

public final class UpdateTaskStatusHandler implements CommandHandler<UpdateTaskStatusPass, UpdateTaskStatusRes> {
    @Override
    public void handle(RequestContext ctx, EnvelopeData<UpdateTaskStatusPass, UpdateTaskStatusRes> env) {
        // 1) auth
        User me = ctx.requireUser(env);
        if (me == null) return;

        // 2) validate input
        var pass = env.getDataPas();
        if (pass == null || isBlank(pass.getTaskId()) || pass.getStatus() == null) {
            ctx.fail(env, "bad_request", "taskId_and_status_required");
            return;
        }

        // 3) locate task
        Task t = ctx.services.tasks.byId(pass.getTaskId());
        if (t == null) { ctx.fail(env, "not_found", "task_not_found"); return; }

        // 4) authz via board
        Board b = ctx.services.boards.byId(t.getBoardId());
        if (b == null) { ctx.fail(env, "not_found", "board_not_found"); return; }
        if (!hasAccess(b, me.getId())) { ctx.fail(env, "forbidden", "no_access_to_board"); return; }

        // 5) perform update
        TaskStatus newStatus = pass.getStatus();
        Task updated = ctx.services.tasks.updateStatus(t.getId(), newStatus);
        if (updated == null) { ctx.fail(env, "conflict", "cannot_change_status"); return; }

        // 6) respond
        var res = new UpdateTaskStatusRes();
        res.setStatus(newStatus);
        res.setTaskId(updated.getId());
        ctx.ok(env, res, "task_status_updated");

        // 7) (optional) notify board members
        // ctx.services.notifier.notifyBoard(b.getBoardId(), "task_status_changed", Map.of("taskId", updated.getId(), "status", updated.getStatus().name()));
    }

    private static boolean hasAccess(Board b, String userId) {
        return b.getOwnerId().equals(userId) || b.getMembers().contains(userId);
    }
    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
}
