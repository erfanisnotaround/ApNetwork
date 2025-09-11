package org.example.writingAssistant.repository;

import org.example.core.domain.User;
import org.example.core.port.UsersRepo;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryUsersRepo implements UsersRepo {
    private final ConcurrentHashMap<String, User> byId = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,User> byName = new ConcurrentHashMap<>();
    @Override public User create(String username, String salt, String passHash) {
        if (byName.containsKey(username)) return null;
        User u = new User(UUID.randomUUID().toString() , username, salt, passHash);
        byName.put(username,u); byId.put(u.getId(),u); return u;
    }
    @Override public User byUsername(String username){ return byName.get(username); }
    @Override public User byId(String id){ return byId.get(id); }
}
