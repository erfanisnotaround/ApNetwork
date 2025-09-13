package org.example.Networking;


import org.example.core.port.LineServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public final class TcpLineServer implements LineServer {
    private final int port; private volatile boolean running;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    public TcpLineServer(int port){ this.port = port; }

    @Override public void start(Consumer<Session> onAccept) {
        running = true;
        new Thread(() -> {
            try (ServerSocket ss = new ServerSocket(port)) {
                while (running) {
                    Socket s = ss.accept();
                    var sess = new TcpSession(s);
                    onAccept.accept(sess);
                    pool.submit(sess::start);
                }
            } catch (IOException ignore) {}
        }, "tcp-acceptor").start();
    }

    @Override public void close() { running=false; pool.shutdownNow(); }

    private static final class TcpSession implements Session {
        private final Socket socket; private final BufferedReader in; private final BufferedWriter out;
        private final BlockingQueue<String> outbound = new LinkedBlockingQueue<>();
        private volatile boolean running = true;
        private Consumer<String> onLine = s -> {};

        TcpSession(Socket s) throws IOException {
            socket = s;
            in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        }

        void start() {
            var writer = new Thread(() -> {
                try {
                    while (running) { String line = outbound.take(); out.write(line); out.write('\n'); out.flush(); }
                } catch (Exception ignore) { } finally { running=false; }
            }, "tcp-writer-"+socket.getPort());
            writer.setDaemon(true); writer.start();

            try {
                String line ;
                while (running && (line = in.readLine()) != null) onLine.accept(line);
            } catch (IOException ignore) { } finally { running=false; try{socket.close();}catch(Exception ignore){} }
        }

        @Override public void setLineHandler(Consumer<String> onLine) { this.onLine = (onLine!=null?onLine:s->{}); }
        @Override public void sendLine(String oneLineJson) { if (running) outbound.offer(oneLineJson); }
        @Override public void close() { running=false; try{socket.close();}catch(Exception ignore){} }
    }
}