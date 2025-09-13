package org.example.passingInformation.entry;

public class CreateBoardPass {
    private final String boardName;
    private final String owner;

    public CreateBoardPass(String boardName, String owner) {
        this.boardName = boardName;
        this.owner = owner;
    }
    public String getBoardName() {
        return boardName;
    }

    public String getOwner() {
        return owner;
    }
}
