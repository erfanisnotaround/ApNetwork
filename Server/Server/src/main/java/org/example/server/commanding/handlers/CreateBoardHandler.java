package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.CreateBoarPass;
import org.example.passingInformation.exit.CreateBoardRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;

public final class CreateBoardHandler implements CommandHandler<CreateBoarPass , CreateBoardRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<CreateBoarPass , CreateBoardRes> env) {
        var user = ctx.requireUser(env); if (user==null) return;
        CreateBoarPass pass = env.getDataPas();
        CreateBoardRes res = env.dataRec;
        String name = pass.boardId;
        var board = ctx.services.boards.create(name, pass.userId);
        ctx.ok(env, env.dataRec, "board_created");
    }
}
