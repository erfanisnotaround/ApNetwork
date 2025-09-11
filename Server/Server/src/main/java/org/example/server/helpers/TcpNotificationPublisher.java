package org.example.server.helpers;

import org.example.EnvelopeData;
import org.example.core.port.EnvelopeCodec;
import org.example.core.port.LineServer;
import org.example.core.port.NotificationPublisher;
import org.example.sameInfoes.KindOfCommunication;

import java.util.Set;

public final class TcpNotificationPublisher implements NotificationPublisher {
    private final EnvelopeCodec codec;

    public TcpNotificationPublisher(EnvelopeCodec codec) {
        this.codec = codec;
    }

    @Override
    public void notifyTo(Set<LineServer.Session> sessions, Object payload, String msg) {
        EnvelopeData<Void, Object> env = new EnvelopeData<>();
        env.setKindOfCommunication( KindOfCommunication.NOTIFY);
        env.setMessage(msg);
        env.dataRec = payload;

        String line = codec.write(env); // one-line JSON
        for (var s : sessions) s.sendLine(line);
    }
}
