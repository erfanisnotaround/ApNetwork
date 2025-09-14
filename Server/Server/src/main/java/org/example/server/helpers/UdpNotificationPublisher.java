// org/example/server/helpers/UdpNotificationPublisher.java
package org.example.server.helpers;

import org.example.EnvelopeData;
import org.example.core.port.EnvelopeCodec;
import org.example.core.port.LineServer;
import org.example.core.port.NotificationPublisher;
import org.example.sameInfoes.KindOfCommunication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public final class UdpNotificationPublisher implements NotificationPublisher, AutoCloseable {
    private final EnvelopeCodec codec;
    private final Sessions sessions;
    private final DatagramSocket socket;

    public UdpNotificationPublisher(EnvelopeCodec codec, Sessions sessions) {
        try {
            this.codec = codec;
            this.sessions = sessions;
            this.socket = new DatagramSocket(); // ephemeral sender
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void notifyTo(Set<LineServer.Session> targets, Object payload, String msg) {
        var env = new EnvelopeData<Void,Object>();
        env.setKindOfCommunication(KindOfCommunication.NOTIFY);
        env.setMessage(msg);
        env.setDataRec(payload);

        String json = codec.write(env);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        for (var s : targets) {
            var dst = sessions.udpEndpointOf(s); // IP from TCP accept, port from client login
            if (dst == null) continue;
            try { socket.send(new DatagramPacket(bytes, bytes.length, dst)); }
            catch (Exception ignore) {}
        }
    }

    @Override public void close() { socket.close(); }
}
