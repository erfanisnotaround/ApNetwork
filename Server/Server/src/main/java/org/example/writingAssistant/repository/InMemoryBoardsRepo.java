package org.example.writingAssistant.repository;

import org.example.core.domain.Board;
import org.example.core.port.BoardsRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryBoardsRepo implements BoardsRepo {
    private final ConcurrentHashMap<String, Board> store = new ConcurrentHashMap<>();
    @Override public Board create(String name, String ownerId){
        var b = new Board(UUID.randomUUID().toString(), name, ownerId);
        store.put(b.getBoardId(), b); return b;
    }
    @Override public Board byId(String id){ return store.get(id); }
    @Override public List<Board> forUser(String userId){
        var list = new ArrayList<Board>();
        for (var b: store.values()) if (b.getOwnerId().equals(userId) || b.getMembers().contains(userId)) list.add(b);
        list.sort(java.util.Comparator.comparingLong(Board::getCreatedAt)); return list;
    }

    @Override
    public Board InviteToBoard(String boardId, String userId, String inviteeId) {
        var board = store.get(boardId);

        if (board == null) return null;

        board.getMembers().add(inviteeId);

        return board;

    }
}
