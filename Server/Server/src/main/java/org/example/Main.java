package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new TCPServer(5050).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}