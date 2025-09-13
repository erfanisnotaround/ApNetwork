package org.example.passingInformation.exit;

public class CreateBoardRes {
    private final String boardName;
    private final String boardId;

    public CreateBoardRes(String boardName , String boardId) {
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
