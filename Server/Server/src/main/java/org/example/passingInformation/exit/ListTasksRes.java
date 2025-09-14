package org.example.passingInformation.exit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.task.TaskItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListTasksRes {

    @JsonProperty("tasks") private List<TaskItem> tasks;
    public ListTasksRes() {}
    public List<TaskItem> getTasks() { return tasks; }
    public void setTasks(List<TaskItem> tasks) { this.tasks = tasks; }
}
