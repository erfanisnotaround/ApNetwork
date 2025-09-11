package org.example.server.commanding.handlers;


import org.example.EnvelopeData;
import org.example.core.port.LineServer;
import org.example.passingInformation.entry.AddTaskPass;
import org.example.passingInformation.exit.AddTaskRes;
import org.example.sameInfoes.TaskPriority;
import org.example.server.helpers.AccessControl;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;
import java.util.Set;

public final class AddTaskHandler implements CommandHandler<AddTaskPass , AddTaskRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<AddTaskPass,AddTaskRes> env) {
        var user = ctx.requireUser(env); if (user==null) return;
        AddTaskPass p = (AddTaskPass) env.getDataPas();
        String boardId = p.boardId;
        var b = ctx.services.boards.byId(boardId);
        if (b == null) { ctx.fail(env,"no_board","board not found"); return; }
        if (!AccessControl.mayMutateBoard(user,b)) { ctx.fail(env,"forbidden","no_access_to_board"); return; }
        var pri = TaskPriority.valueOf(p.priority);
        var t = ctx.services.tasks.add(boardId, String.valueOf(p.title), String.valueOf(p.description), pri);
        ctx.<AddTaskPass , AddTaskRes> ok(env, env.dataRec, "task_added");
        ctx.notify(ctx.sessions.subscribers(boardId), Map.of("taskID", t.getId()), "task_added");
    }

}