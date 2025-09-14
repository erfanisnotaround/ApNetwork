package org.example.passingInformation.entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteTaskPass {

    private final String taskId;
    @JsonCreator
    public DeleteTaskPass(@JsonProperty("taskId") String taskId){ this.taskId=taskId;}
    public String getTaskId(){ return taskId; }
}
