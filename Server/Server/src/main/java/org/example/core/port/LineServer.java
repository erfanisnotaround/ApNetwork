package org.example.core.port;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

public interface LineServer extends AutoCloseable {
    void start(Consumer<Session> onAccept); // gives a Session when a socket connects
    interface Session extends AutoCloseable {
        void setLineHandler(Consumer<String> onLine);
        void sendLine(String oneLineJson);
        InetSocketAddress remoteAddress();
    }
}
