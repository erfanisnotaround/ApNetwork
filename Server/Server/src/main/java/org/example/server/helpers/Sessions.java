package org.example.server.helpers;

import org.example.core.port.LineServer;
import org.example.sameInfoes.UserPlaceState;

import java.util.Objects;
import java.util.Set;
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

    /** Return session context (create if missing). */
    public Ctx ctx(LineServer.Session s) { return ctxBySession.computeIfAbsent(s, k -> new Ctx()); }

    /** Bind this TCP session to a user after LOGIN. */
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

    /** Unbind but keep the session alive (e.g., logout). */
    public void detach(LineServer.Session s) {
        var c = ctxBySession.get(s);
        if (c == null) return;
        if (c.userId != null) {
            var set = byUser.get(c.userId);
            if (set != null) set.remove(s);
            c.userId = null;
        }
        c.place = UserPlaceState.LOGGING_IN;
        c.currentBoardId = null;
    }

    /** Lookup logged-in userId for this session (null if not logged in). */
    public String userIdOf(LineServer.Session s) {
        var c = ctxBySession.get(s);
        return (c != null ? c.userId : null);
    }

    // === Board subscriptions (as you had)
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

    public Set<LineServer.Session> sessionsOfUser(String userId) {
        return byUser.getOrDefault(userId, Set.of());
    }

    public void setPlace(LineServer.Session s, UserPlaceState p) { ctx(s).place = p; }
    public void setBoard(LineServer.Session s, String boardId) { ctx(s).currentBoardId = boardId; }
}
