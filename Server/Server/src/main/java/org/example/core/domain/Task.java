package org.example.core.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.sameInfoes.TaskPriority;
import org.example.sameInfoes.TaskStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Task {
    private final String id;
    private final String boardId;
    private final String title;
    private final String description;
    private final TaskPriority priority;
    private final long createdAt;
    private volatile TaskStatus status;

    /** Use this when creating new tasks in code */
    public Task(String id, String boardId, String title, String description, TaskPriority p) {
        this(id, boardId, title, description, p, System.currentTimeMillis(), TaskStatus.TODO);
    }

    /** Used by Jackson when loading from JSON */
    @JsonCreator
    public Task(@JsonProperty("id")          String id,
                @JsonProperty("boardId")     String boardId,
                @JsonProperty("title")       String title,
                @JsonProperty("description") String description,
                @JsonProperty("priority")    TaskPriority priority,
                @JsonProperty("createdAt")   Long createdAt,
                @JsonProperty("status")      TaskStatus status) {
        this.id = id;
        this.boardId = boardId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdAt = (createdAt != null ? createdAt : System.currentTimeMillis());
        this.status = (status != null ? status : TaskStatus.TODO);
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
