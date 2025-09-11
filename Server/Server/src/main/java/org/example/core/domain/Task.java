package org.example.core.domain;

import org.example.sameInfoes.TaskPriority;
import org.example.sameInfoes.TaskStatus;

public final class Task {
    private final String id, boardId, title, description;
    private final TaskPriority priority;
    private final long createdAt = System.currentTimeMillis();
    private volatile TaskStatus status = TaskStatus.TODO;

    public Task(String id, String boardId, String title, String description, TaskPriority p){
        this.id=id; this.boardId=boardId; this.title=title; this.description=description; this.priority=p;
    }
    public String getId(){ return id; }
    public String getBoardId(){ return boardId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public TaskPriority getPriority(){ return priority; }
    public long getCreatedAt(){ return createdAt; }
    public TaskStatus getStatus(){ return status; }
    public void setStatus(TaskStatus s){ this.status = s; }
}
