package org.example.passingInformation.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddingUserToTheBoardPass {
    private final String boardId;
    private final String invitee; // username to invite

    @JsonCreator
    public AddingUserToTheBoardPass(@JsonProperty("boardId") String boardId, @JsonProperty("invitee") String invitee) {
        this.boardId = boardId;
        this.invitee = invitee;
    }
    public String getBoardId() { return boardId; }
    public String getInvitee() { return invitee; }
}
