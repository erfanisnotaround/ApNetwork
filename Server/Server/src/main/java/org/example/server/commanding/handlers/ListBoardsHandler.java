package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.boarding.BoardSummary;
import org.example.core.domain.Board;
import org.example.passingInformation.entry.BoardListPass;
import org.example.passingInformation.exit.BoardListRes;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.ArrayList;
import java.util.List;

public final class ListBoardsHandler implements CommandHandler<BoardListPass, BoardListRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<BoardListPass, BoardListRes> env) {
        var user = ctx.requireUser(env); if (user==null) return;
        BoardListPass pass = env.getDataPas();
        BoardListRes res = env.dataRec;
        var services = ctx.services;
        List<Board> boards = services.boards.forUser(pass.getOwner());

        ctx.ok(env, new BoardListRes(getBoards(boards)), "boards_list");
    }
    private List<BoardSummary> getBoards(List<Board> boards) {
        List<BoardSummary> boardSummaries = new ArrayList<>();
        for (Board board : boards) {
            boardSummaries.add(new BoardSummary(board.getBoardName() , board.getBoardId()));
        }
        return boardSummaries;
    }


}
