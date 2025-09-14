package org.example.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.sameInfoes.TaskPriority;
import org.example.sameInfoes.TaskStatus;


@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskItem {
    @JsonProperty("taskId") private String taskId;
    @JsonProperty("title") private String title;
    @JsonProperty("description") private String description;
    @JsonProperty("priority") private TaskPriority priority;
    @JsonProperty("status") private TaskStatus status;

    public TaskItem() {}
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}