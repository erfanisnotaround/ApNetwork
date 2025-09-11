package org.example.core.domain;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class Board {
    private final String id, name, ownerId;
    private final long createdAt = System.currentTimeMillis();
    private final Set<String> members = new CopyOnWriteArraySet<>();
    public Board(String id, String name, String ownerId){ this.id=id; this.name=name; this.ownerId=ownerId; }
    public String getId(){ return id; }
    public String getName(){ return name; }
    public String getOwnerId(){ return ownerId; }
    public long getCreatedAt(){ return createdAt; }
    public Set<String> getMembers(){ return members; }
}
