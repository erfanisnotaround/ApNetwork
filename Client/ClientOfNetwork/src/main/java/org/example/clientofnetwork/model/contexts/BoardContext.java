package org.example.clientofnetwork.model.contexts;

public class BoardContext {
    private final AppContext app;
    private final String boardId;
    private final String boardName;
    public BoardContext(AppContext app, String boardId, String boardName){
        this.app = app; this.boardId = boardId; this.boardName = boardName;
    }

    public AppContext getApp() {
        return app;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getBoardName() {
        return boardName;
    }
}
