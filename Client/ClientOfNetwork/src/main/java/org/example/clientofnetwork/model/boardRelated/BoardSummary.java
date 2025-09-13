package org.example.clientofnetwork.model.boardRelated;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardSummary {
    @JsonProperty("boardId")
    private String boardId;

    @JsonProperty("boardName")
    private String boardName;

    public BoardSummary() { } // Needed for Jackson

    public String getBoardId()   { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }

    public String getBoardName() { return boardName; }
    public void setBoardName(String boardName) { this.boardName = boardName; }
}
