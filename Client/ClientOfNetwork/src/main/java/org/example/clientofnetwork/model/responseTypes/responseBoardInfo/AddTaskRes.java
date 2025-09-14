package org.example.clientofnetwork.model.responseTypes.responseBoardInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clientofnetwork.model.task.TaskItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddTaskRes {
    @JsonProperty("task") private TaskItem task;
    public AddTaskRes() {}
    public TaskItem getTask() { return task; }
    public void setTask(TaskItem task) { this.task = task; }
}