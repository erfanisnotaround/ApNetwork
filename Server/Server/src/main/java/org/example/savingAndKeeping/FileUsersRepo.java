package org.example.savingAndKeeping;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.domain.Board;
import org.example.core.domain.User;
import org.example.core.port.BoardsRepo;
import org.example.core.port.UsersRepo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.example.savingAndKeeping.AtomicFiles.writeJsonAtomically;

public final class FileUsersRepo implements UsersRepo {
    private static final TypeReference<List<User>> LIST = new TypeReference<>(){};
    private final ObjectMapper M;
    private final Path file;

    private final ConcurrentHashMap<String, User> byId = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, User> byName = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    public FileUsersRepo(Path dataDir, ObjectMapper mapper){
        this.M = Objects.requireNonNull(mapper);
        this.file = dataDir.resolve("Users.json");
        loadIfExists();
    }

    @Override public User create(String username, String salt, String passHash) {
        rw.writeLock().lock();
        try {
            if (byName.containsKey(username)) return null;
            User u = new User(UUID.randomUUID().toString(), username, salt, passHash);
            byId.put(u.getId(), u); byName.put(u.getUsername(), u);
            persistUnsafe(); return u;
        } finally { rw.writeLock().unlock(); }
    }

    @Override public User byUsername(String username) {
        rw.readLock().lock(); try { return byName.get(username); } finally { rw.readLock().unlock(); }
    }

    @Override public User byId(String id) {
        rw.readLock().lock(); try { return byId.get(id); } finally { rw.readLock().unlock(); }
    }

    private void loadIfExists() {
        if (!Files.exists(file)) return;
        rw.writeLock().lock();
        try {
            List<User> all = M.readValue(file.toFile(), LIST);
            byId.clear(); byName.clear();
            for (User u: all){ byId.put(u.getId(), u); byName.put(u.getUsername(), u); }
        } catch (Exception e) { throw new RuntimeException("load users", e); }
        finally { rw.writeLock().unlock(); }
    }

    private void persistUnsafe() {
        var list = new ArrayList<>(byId.values());
        list.sort(Comparator.comparing(User::getUsername));
        writeJsonAtomically(M, file, list);
    }
}
