package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.exit.ListTasksRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

// org/example/server/commanding/handlers/ListTasksHandler.java


import org.example.EnvelopeData;
import org.example.core.domain.Board;
import org.example.core.domain.Task;
import org.example.core.domain.User;
import org.example.passingInformation.entry.ListTasksPass;

import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;
import org.example.task.TaskItem;

import java.util.ArrayList;
import java.util.List;

public final class ListTasksHandler implements CommandHandler<ListTasksPass, ListTasksRes> {

    @Override
    public void handle(RequestContext ctx, EnvelopeData<ListTasksPass, ListTasksRes> env) {
        // 1) auth
        User me = ctx.requireUser(env);
        if (me == null) return;

        // 2) validate input
        var pass = env.getDataPas();
        if (pass == null || isBlank(pass.getBoardId())) {
            ctx.fail(env, "bad_request", "boardId_required");
            return;
        }

        // 3) board + authorization
        Board board = ctx.services.boards.byId(pass.getBoardId());
        if (board == null) { ctx.fail(env, "not_found", "board_not_found"); return; }

        if (!hasAccess(board, me.getId())) {
            ctx.fail(env, "forbidden", "no_access_to_board");
            return;
        }

        // 4) fetch tasks
        List<Task> tasks = ctx.services.tasks.forBoard(pass.getBoardId());

        // 5) map to response view
        List<TaskItem> out = new ArrayList<>(tasks.size());
        for (Task t : tasks) out.add(toView(t));

        var res = new ListTasksRes();
        res.setTasks(out);
        ctx.ok(env, res, "tasks_list");
    }

    private static boolean hasAccess(Board b, String userId) {
        return b.getOwnerId().equals(userId) || b.getMembers().contains(userId);
    }
    private static boolean isBlank(String s) { return s == null || s.isBlank(); }

    private static TaskItem toView(Task t) {
        TaskItem item = new TaskItem();
        item.setTitle(t.getTitle());
        item.setDescription(t.getDescription());
        item.setTaskId(t.getId());
        item.setStatus(t.getStatus());
        item.setPriority(t.getPriority());
        return item;
    }
}
