package org.example.clientofnetwork.model.responseTypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;

import java.util.List;

public class BoardListRes {
    private List<BoardSummary> boards;


    @JsonCreator
    public BoardListRes(@JsonProperty("boards") List<BoardSummary> boards) {
        this.boards = boards;
    }

    public List<BoardSummary> getBoards() { return boards; }
    public void setBoards(List<BoardSummary> boards) { this.boards = boards; }
}
