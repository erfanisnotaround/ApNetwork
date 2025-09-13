package org.example.clientofnetwork.model.commands.buildInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BoardListPass {
    private final String owner;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BoardListPass(@JsonProperty("owner") String owner) {
        this.owner = owner;
    }

    public String getOwner() { return owner; }
}
