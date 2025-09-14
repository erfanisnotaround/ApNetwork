package org.example.writingAssistant.repository;

import org.example.core.domain.Task;
import org.example.core.port.TasksRepo;
import org.example.sameInfoes.TaskPriority;
import org.example.sameInfoes.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryTasksRepo implements TasksRepo {
    private final ConcurrentHashMap<String, Task> byId = new ConcurrentHashMap<>();
    @Override public Task add(String boardId, String title, String desc, TaskPriority p){
        var t = new Task(java.util.UUID.randomUUID().toString(), boardId, title, desc, p); byId.put(t.getId(),t); return t;
    }
    @Override public Task byId(String id){ return byId.get(id); }
    @Override public List<Task> forBoard(String boardId){
        var list = new ArrayList<Task>();
        for (var t: byId.values()) if (t.getBoardId().equals(boardId)) list.add(t);
        list.sort(java.util.Comparator.comparingLong(Task::getCreatedAt)); return list;
    }

    @Override
    public Task updateStatus(String taskId, TaskStatus newStatus) {
        return null;
    }

    @Override
    public boolean delete(String taskId) {
        return false;
    }
}