package org.example.clientofnetwork.model.responseTypes.responseBoardInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskStatus;
@JsonIgnoreProperties(ignoreUnknown = true)

public class UpdateTaskStatusRes {

    @JsonProperty("taskId") private String taskId;
    @JsonProperty("status") private TaskStatus status;
    public UpdateTaskStatusRes() {}
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
