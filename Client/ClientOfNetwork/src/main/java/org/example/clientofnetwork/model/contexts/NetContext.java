package org.example.clientofnetwork.model.contexts;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetContext implements Closeable {
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;

    public NetContext(Socket s) throws IOException {
        this.socket = s;
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
        this.in  = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
    }
    public Socket socket(){ return socket; }
    public BufferedWriter out(){ return out; }
    public BufferedReader in(){ return in; }
    @Override public void close() throws IOException { socket.close(); }
}