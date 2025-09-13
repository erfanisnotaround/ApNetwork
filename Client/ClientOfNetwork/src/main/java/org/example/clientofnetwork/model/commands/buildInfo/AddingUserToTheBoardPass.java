package org.example.clientofnetwork.model.commands.buildInfo;

public class AddingUserToTheBoardPass {
    private String boardId;
    private String invited;

    public AddingUserToTheBoardPass(String boardId, String invited) {
        this.boardId = boardId;
        this.invited = invited;
    }
    public String getBoardId() {
        return boardId;
    }
    public String getInvited() {
        return invited;
    }

}
