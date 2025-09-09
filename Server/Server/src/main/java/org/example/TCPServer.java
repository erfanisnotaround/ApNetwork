package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private final int port;
    public TCPServer(int port){ this.port = port; }

    public void start() throws IOException {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("[SERVER] Listening on " + port);
            while (true) {
                Socket s = ss.accept();
                System.out.println("[SERVER] Client " + s.getRemoteSocketAddress());
                Thread t = new Thread(() -> handle(s), "client-" + s.getPort());
                t.start();
            }
        }
    }

    private void handle(Socket s) {
        try (var in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
             var out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[SERVER] <- " + line);
                out.write("ECHO: " + line + "\n");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("[SERVER] closed: " + e.getMessage());
        }
    }
}
