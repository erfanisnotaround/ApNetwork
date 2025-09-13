package org.example.clientofnetwork.model.commands.buildInfo;

public class ViewBoardPass {
    private final String boardId;
    public ViewBoardPass(String boardId) {
        this.boardId = boardId;
    }
    public String getBoardId() {
        return boardId;
    }
}
