package org.example.server.commanding;

import org.example.EnvelopeData;
import org.example.core.domain.User;
import org.example.core.port.LineServer;
import org.example.server.helpers.EnvelopeIO;
import org.example.server.helpers.Services;
import org.example.server.helpers.Sessions;

import java.util.HashSet;
import java.util.Set;

public final class RequestContext {
    public final LineServer.Session session;
    public final Sessions sessions;
    public final Services services;
    public final EnvelopeIO io;

    public RequestContext(LineServer.Session s, Sessions sessions, Services services, EnvelopeIO io) {
        this.session = s; this.sessions = sessions; this.services = services; this.io = io;
    }

    public <P, R> void ok(EnvelopeData<P, R> env, R dataRec, String msg) {io.respondOk(session, env, dataRec, msg);}

    public <P, R> void fail(EnvelopeData<P, R> env, String code, String msg) {io.respondFail(session, env, code, msg);}
    public void notify(Set<LineServer.Session> targets, Object data, String msg){ services.notifier.notifyTo(targets, data, msg); }

    // auth helper
    public User requireUser(EnvelopeData<?,?> env) {
        String userId = services.tokens.verify(env.getToken());
        if (userId == null) { fail(env, "unauth", "invalid_or_expired_token"); return null; }
        var u = services.users.byId(userId);
        if (u == null) { fail(env, "unauth", "unknown_user"); return null; }

        // make sure Sessions knows this socket == this user
        sessions.attachUser(session, userId);
        return u;
    }
    public Set<LineServer.Session> onlineMembersOfBoard(String boardId) {
        var b = services.boards.byId(boardId);
        if (b == null) return Set.of();

        var targets = new HashSet<LineServer.Session>(sessions.sessionsOfUser(b.getOwnerId()));
        for (String uid : b.getMembers()) {
            targets.addAll(sessions.sessionsOfUser(uid));
        }
        return targets;
    }
}