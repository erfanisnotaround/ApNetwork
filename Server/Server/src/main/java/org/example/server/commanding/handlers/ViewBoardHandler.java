package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.ViewBoardPass;
import org.example.passingInformation.exit.ViewBoardRes;
import org.example.server.helpers.AccessControl;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;

public final class ViewBoardHandler implements CommandHandler<ViewBoardPass , ViewBoardRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<ViewBoardPass , ViewBoardRes> env) {
        var user = ctx.requireUser(env); if (user==null) return;
        ViewBoardPass pass = env.getDataPas();
        ViewBoardRes res = env.getDataRec();
        String boardId = pass.boardId;
        var b = ctx.services.boards.byId(boardId);
        if (b == null) { ctx.fail(env,"no_board","board not found"); return; }
        if (!AccessControl.mayViewBoard(user,b)) { ctx.fail(env,"forbidden","no_access_to_board"); return; }
        ctx.sessions.setBoard(ctx.session , boardId);
        ctx.ok(env, res, "view_board_ok");
    }
}
