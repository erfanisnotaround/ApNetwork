package org.example.savingAndKeeping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.domain.Board;
import org.example.core.port.BoardsRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.example.savingAndKeeping.AtomicFiles.writeJsonAtomically;

public final class FileBoardsRepo implements BoardsRepo {
    private static final TypeReference<List<Board>> LIST = new TypeReference<>(){};
    private final ObjectMapper M;
    private final Path file;

    private final ConcurrentHashMap<String, Board> byId = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    public FileBoardsRepo(Path dataDir, ObjectMapper mapper){
        this.M = Objects.requireNonNull(mapper);
        this.file = dataDir.resolve("Boards.json");
        loadIfExists();
    }

    @Override public Board create(String name, String ownerId) {
        rw.writeLock().lock();
        try {
            var b = new Board(UUID.randomUUID().toString(), name, ownerId);
            byId.put(b.getId(), b);
            persistUnsafe(); return b;
        } finally { rw.writeLock().unlock(); }
    }

    @Override public Board byId(String id) {
        rw.readLock().lock(); try { return byId.get(id); } finally { rw.readLock().unlock(); }
    }

    @Override public List<Board> forUser(String userId) {
        rw.readLock().lock();
        try {
            var out = new ArrayList<Board>();
            for (var b: byId.values())
                if (b.getOwnerId().equals(userId) || b.getMembers().contains(userId)) out.add(b);
            out.sort(Comparator.comparingLong(Board::getCreatedAt));
            return out;
        } finally { rw.readLock().unlock(); }
    }

    @Override
    public Board InviteToBoard(String boardId, String userId, String inviteeId) {
        rw.writeLock().lock();
        try {
            var b = byId.get(boardId); if (b==null) return null;
            boolean added = b.getMembers().add(userId);
            if (added) persistUnsafe();
            return b;
        } finally { rw.writeLock().unlock(); }
    }



    private void loadIfExists() {
        if (!Files.exists(file)) return;
        rw.writeLock().lock();
        try {
            List<Board> all = M.readValue(file.toFile(), LIST);
            byId.clear();
            for (Board b: all) byId.put(b.getId(), b);
        } catch (Exception e) { throw new RuntimeException("load boards", e); }
        finally { rw.writeLock().unlock(); }
    }

    private void persistUnsafe() {
        var list = new ArrayList<>(byId.values());
        list.sort(Comparator.comparingLong(Board::getCreatedAt));
        writeJsonAtomically(M, file, list);
    }
}
