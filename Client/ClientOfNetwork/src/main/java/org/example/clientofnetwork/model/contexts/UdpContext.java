package org.example.clientofnetwork.model.listeningAndReading;

import java.io.Closeable;
import java.net.DatagramSocket;
import java.net.SocketException;

public final class UdpContext implements  Closeable {
    private final DatagramSocket ds;
    public UdpContext(int localPort) throws SocketException { this.ds = new DatagramSocket(localPort); }
    public DatagramSocket socket(){ return ds; }
    @Override public void close(){ ds.close(); }
}