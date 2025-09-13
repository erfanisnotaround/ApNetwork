package org.example.clientofnetwork.model.contexts;

import java.io.Closeable;
import java.net.DatagramSocket;
import java.net.SocketException;

public final class UdpContext implements Closeable {
    private final DatagramSocket ds;

    public UdpContext() throws SocketException { this.ds = new DatagramSocket(0); } // ephemeral port
    public DatagramSocket socket() { return ds; }
    public int localPort() { return ds.getLocalPort(); }

    @Override public void close() { ds.close(); }
}