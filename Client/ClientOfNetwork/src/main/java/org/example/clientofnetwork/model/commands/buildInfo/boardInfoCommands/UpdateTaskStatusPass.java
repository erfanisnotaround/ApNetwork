package org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskStatus;

public class UpdateTaskStatusPass {
    private final String taskId;
    private final TaskStatus status;
    @JsonCreator
    public UpdateTaskStatusPass(@JsonProperty("taskId") String taskId, @JsonProperty("status") TaskStatus status){ this.taskId=taskId; this.status=status; }
    public String getTaskId(){ return taskId; }
    public TaskStatus getStatus(){ return status; }
}
