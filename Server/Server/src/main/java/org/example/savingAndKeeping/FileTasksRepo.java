package org.example.savingAndKeeping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.domain.Task;
import org.example.core.port.TasksRepo;
import org.example.sameInfoes.TaskPriority;
import org.example.sameInfoes.TaskStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.example.savingAndKeeping.AtomicFiles.writeJsonAtomically;

public final class FileTasksRepo implements TasksRepo {
    private static final TypeReference<List<Task>> LIST = new TypeReference<>(){};
    private final ObjectMapper M;
    private final Path file;

    private final ConcurrentHashMap<String, Task> byId = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    public FileTasksRepo(Path dataDir, ObjectMapper mapper){
        this.M = Objects.requireNonNull(mapper);
        this.file = dataDir.resolve("Tasks.json");
        loadIfExists();
    }

    @Override public Task add(String boardId, String title, String desc, TaskPriority p) {
        rw.writeLock().lock();
        try {
            var t = new Task(UUID.randomUUID().toString(), boardId, title, desc, p);
            byId.put(t.getId(), t);
            persistUnsafe(); return t;
        } finally { rw.writeLock().unlock(); }
    }

    @Override public Task byId(String id) {
        rw.readLock().lock(); try { return byId.get(id); } finally { rw.readLock().unlock(); }
    }

    @Override public List<Task> forBoard(String boardId) {
        rw.readLock().lock();
        try {
            var out = new ArrayList<Task>();
            for (var t: byId.values()) if (t.getBoardId().equals(boardId)) out.add(t);
            out.sort(Comparator.comparingLong(Task::getCreatedAt));
            return out;
        } finally { rw.readLock().unlock(); }
    }

    // Optional: updates
    @Override
    public Task updateStatus(String taskId, TaskStatus s){
        rw.writeLock().lock();
        try {
            var t = byId.get(taskId);
            t.setStatus(s);
            persistUnsafe();
            return t;
        } finally { rw.writeLock().unlock(); }
    }

    @Override
    public boolean delete(String taskId){
        rw.writeLock().lock();
        try {
            var removed = (byId.remove(taskId) != null);
            if (removed) persistUnsafe();
            return removed;
        } finally { rw.writeLock().unlock(); }
    }

    private void loadIfExists() {
        if (!Files.exists(file)) return;
        rw.writeLock().lock();
        try {
            List<Task> all = M.readValue(file.toFile(), LIST);
            byId.clear();
            for (Task t: all) byId.put(t.getId(), t);
        } catch (Exception e) { throw new RuntimeException("load tasks", e); }
        finally { rw.writeLock().unlock(); }
    }

    private void persistUnsafe() {
        var list = new ArrayList<>(byId.values());
        list.sort(Comparator.comparingLong(Task::getCreatedAt));
        writeJsonAtomically(M, file, list);
    }
}
