package org.example.server.helpers;

import org.example.core.port.LineServer;
import org.example.sameInfoes.UserPlaceState;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


import org.example.core.port.LineServer;
import org.example.sameInfoes.UserPlaceState;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Sessions {

    public static final class Ctx {
        public String userId;
        public UserPlaceState place = UserPlaceState.LOGGING_IN;
        public String currentBoardId;
    }

    private final ConcurrentHashMap<LineServer.Session, Ctx> ctxBySession = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<LineServer.Session>> byUser  = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<LineServer.Session>> byBoard = new ConcurrentHashMap<>();

    public void add(LineServer.Session s) { ctxBySession.put(s, new Ctx()); }

    public void remove(LineServer.Session s) {
        Ctx c = ctxBySession.remove(s);
        if (c != null && c.userId != null) {
            var u = byUser.get(c.userId);
            if (u != null) u.remove(s);
        }
        byBoard.values().forEach(set -> set.remove(s));
    }

    public Ctx ctx(LineServer.Session s) { return ctxBySession.computeIfAbsent(s, k -> new Ctx()); }

    /** Call after successful login (or in requireUser for safety) */
    public void attachUser(LineServer.Session s, String userId) {
        var c = ctx(s);
        String prev = c.userId;
        c.userId = userId;
        if (prev != null && !prev.equals(userId)) {
            var old = byUser.get(prev);
            if (old != null) old.remove(s);
        }
        byUser.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(s);
    }

    // === Strategy A (live viewers)
    public void subscribeBoard(String boardId, LineServer.Session s) {
        ctx(s).currentBoardId = boardId;
        byBoard.computeIfAbsent(boardId, k -> ConcurrentHashMap.newKeySet()).add(s);
    }
    public void unsubscribeBoard(String boardId, LineServer.Session s) {
        var set = byBoard.get(boardId);
        if (set != null) set.remove(s);
        var c = ctx(s);
        if (Objects.equals(c.currentBoardId, boardId)) c.currentBoardId = null;
    }
    public Set<LineServer.Session> subscribers(String boardId) {
        return byBoard.getOrDefault(boardId, Set.of());
    }

    // === Strategy B (online-by-user)
    public Set<LineServer.Session> sessionsOfUser(String userId) {
        return byUser.getOrDefault(userId, Set.of());
    }

    public void setPlace(LineServer.Session s, UserPlaceState p) { ctx(s).place = p; }
    public void setBoard(LineServer.Session s, String boardId) { ctx(s).currentBoardId = boardId; }
}

