package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.ListBoardsPass;
import org.example.passingInformation.exit.ListBoardRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;
import java.util.stream.Collectors;

public final class ListBoardsHandler implements CommandHandler<ListBoardsPass , ListBoardRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<ListBoardsPass , ListBoardRes> env) {
        var user = ctx.requireUser(env); if (user==null) return;
        ListBoardsPass pass = env.getDataPas();
        ListBoardRes res = env.dataRec;
        ctx.ok(env, res, "boards_list");
    }
}
