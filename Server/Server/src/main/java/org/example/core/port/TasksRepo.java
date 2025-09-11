package org.example.core.port;

import org.example.core.domain.Task;
import org.example.sameInfoes.TaskPriority;

import java.util.List;

public interface TasksRepo {
    Task add(String boardId, String title, String desc, TaskPriority p);
    Task byId(String id);
    List<Task> forBoard(String boardId);
}
