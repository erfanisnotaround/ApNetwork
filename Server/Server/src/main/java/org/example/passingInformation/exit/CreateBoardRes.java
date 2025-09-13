package org.example.passingInformation.exit;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBoardRes {
    private final String boardName;
    private final String boardId;

    @JsonCreator
    public CreateBoardRes(@JsonProperty("boardName") String boardName ,@JsonProperty("boardId") String boardId) {
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
