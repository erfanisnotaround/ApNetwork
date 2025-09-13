package org.example.clientofnetwork.model.commands.buildInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBoardPass {
    private final String boardName;
    private final String owner;

    @JsonCreator
    public CreateBoardPass(@JsonProperty("boardName") String boardName,
                           @JsonProperty("owner") String owner) {
        this.boardName = boardName;
        this.owner = owner;
    }

    public String getBoardName() { return boardName; }
    public String getOwner()     { return owner; }
}

