package org.example.passingInformation.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListTasksPass {
    private final String boardId;
    @JsonCreator
    public ListTasksPass(@JsonProperty("boardId") String boardId){ this.boardId = boardId; }
    public String getBoardId(){ return boardId; }
}