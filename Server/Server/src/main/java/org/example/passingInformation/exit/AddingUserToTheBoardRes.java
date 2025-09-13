package org.example.passingInformation.exit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddingUserToTheBoardRes {

    @JsonProperty("boardId")   private String boardId;
    @JsonProperty("inviteeId") private String inviteeId; // server may return userId of invitee


    public String getBoardId()   { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public String getInviteeId() { return inviteeId; }
    public void setInviteeId(String inviteeId) { this.inviteeId = inviteeId; }
}
