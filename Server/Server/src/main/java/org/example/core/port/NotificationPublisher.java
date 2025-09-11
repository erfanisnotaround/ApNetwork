package org.example.core.port;

import java.util.Set;

public interface NotificationPublisher {
    void notifyTo(Set<LineServer.Session> sessions, Object payload, String msg);
}