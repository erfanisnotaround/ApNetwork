package org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands;

import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskPriority;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class AddTaskPass {
    private final String boardId;
    private final String title;
    private final String description;
    private final TaskPriority priority;

    @JsonCreator
    public AddTaskPass(@JsonProperty("boardId") String boardId,@JsonProperty("title") String title,
                       @JsonProperty("description")String description, @JsonProperty("priority") TaskPriority priority) {
        this.boardId = boardId; this.title = title; this.description = description; this.priority = priority;
    }
    public String getBoardId(){ return boardId; }
    public String getTitle(){ return title; }
    public String getDescription(){ return description; }
    public TaskPriority getPriority(){ return priority; }
}
