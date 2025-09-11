package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            new TCPServer(8080).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}