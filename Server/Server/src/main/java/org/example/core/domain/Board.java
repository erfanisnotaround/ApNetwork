package org.example.core.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Board {

    private final String boardId;
    private final String boardName;
    private final String ownerId;
    private final long createdAt;
    private final Set<String> members;

    /** Use from code for new boards */
    public Board(String boardId, String boardName, String ownerId) {
        this(boardId, boardName, ownerId, System.currentTimeMillis(), null);
    }

    /** Used by Jackson when reading JSON */
    @JsonCreator
    public Board(
            @JsonProperty("boardId")   @JsonAlias("id")   String boardId,
            @JsonProperty("boardName") @JsonAlias("name") String boardName,
            @JsonProperty("ownerId")   String ownerId,
            @JsonProperty("createdAt") Long createdAt,
            @JsonProperty("members")   Collection<String> members
    ) {
        this.boardId   = boardId;
        this.boardName = boardName;
        this.ownerId   = ownerId;
        this.createdAt = (createdAt != null ? createdAt : System.currentTimeMillis());
        this.members   = new CopyOnWriteArraySet<>(members == null ? Set.of() : members);
    }

    public String getBoardId()   { return boardId; }
    public String getBoardName() { return boardName; }
    public String getOwnerId()   { return ownerId; }
    public long   getCreatedAt() { return createdAt; }
    public Set<String> getMembers() { return members; }
}
