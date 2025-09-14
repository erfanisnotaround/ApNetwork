// org/example/clientofnetwork/model/listeningAndReading/UdpNotificationReceiver.java
package org.example.clientofnetwork.model.listeningAndReading;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class UdpNotificationReceiver implements AutoCloseable {
    private final int port;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private volatile Consumer<String> onMessage = s -> {};
    private DatagramSocket socket;
    private Thread thread;

    public UdpNotificationReceiver(int port) { this.port = port; }

    /** Set a callback to receive each UDP packet as a UTF-8 string (trimmed). */
    public void setOnMessage(Consumer<String> c) { this.onMessage = (c != null ? c : s -> {}); }

    public void start() throws SocketException {
        if (!running.compareAndSet(false, true)) return;
        socket = new DatagramSocket(port);
        thread = new Thread(this::loop, "udp-notify");
        thread.setDaemon(true);
        thread.start();
    }

    private void loop() {
        byte[] buf = new byte[8192];
        DatagramPacket pkt = new DatagramPacket(buf, buf.length);
        while (running.get()) {
            try {
                socket.receive(pkt);
                String s = new String(pkt.getData(), pkt.getOffset(), pkt.getLength(), StandardCharsets.UTF_8);
                onMessage.accept(s.trim());
            } catch (IOException e) {
                if (running.get()) { /* optional log */ }
            }
        }
    }

    @Override public void close() {
        running.set(false);
        if (socket != null) socket.close(); // unblocks receive()
    }
}
