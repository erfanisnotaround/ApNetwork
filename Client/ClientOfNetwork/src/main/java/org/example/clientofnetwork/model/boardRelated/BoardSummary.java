package org.example.clientofnetwork.model.boardRelated;

public class BoardSummary {
    private final String boardName;
    private final String boardId;

    public BoardSummary(String boardName, String boardId) {
        this.boardName = boardName;
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }
    public String getBoardId() {
        return boardId;
    }
}
