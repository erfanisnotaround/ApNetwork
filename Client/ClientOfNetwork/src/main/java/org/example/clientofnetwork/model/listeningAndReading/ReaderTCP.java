package org.example.clientofnetwork.model.listeningAndReading;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ReaderTCP implements Runnable {

    private volatile BufferedReader reader;
    private volatile AtomicBoolean running;
    private Consumer<String> onLine;

    public ReaderTCP(BufferedReader reader, AtomicBoolean running) {

        this.reader = reader;
        this.running = running;

        onLine = line -> {

        };
    }
    public void setOnLine(Consumer<String> onLine) { this.onLine = (onLine != null ? onLine : s -> {}); }
    @Override
    public void run() {
        readLoop();
    }
    private void readLoop() {
        try {
            String line;
            while (running.get() && (line = reader.readLine()) != null) {
                onLine.accept(line);
            }
        } catch (IOException ignore) {
        } finally { running.set(false); }
    }
}
