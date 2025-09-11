package org.example.core.port;

import org.example.core.domain.Board;

import java.util.List;

public interface BoardsRepo {
    Board create(String name, String ownerId);
    Board byId(String id);
    List<Board> forUser(String userId);
    Board InviteToBoard(String boardId, String userId , String inviteeId);
}
