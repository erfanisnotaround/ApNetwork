package org.example.clientofnetwork.model.responseTypes.responseBoardInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteTaskRes {

    @JsonProperty("taskId") private String taskId;
    public DeleteTaskRes() {}
    public String getTaskId() { return taskId; }
    public void setTaskId(String taskId) { this.taskId = taskId; }
}
