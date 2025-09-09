package org.example.clientofnetwork.model.listeningAndReading;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class WriterTCP implements Runnable {

    private volatile LinkedBlockingQueue<String> outbound;
    private BufferedWriter writer;
    private volatile AtomicBoolean running;

    public WriterTCP(LinkedBlockingQueue<String> outbound, BufferedWriter writer , AtomicBoolean running) {
        this.outbound = outbound;
        this.writer = writer;
        this.running = running;
    }

    @Override
    public void run() {
        writeLoop();
    }
    private void writeLoop() {
        try {
            while (running.get()) {
                String line = outbound.take();
                writer.write(line); writer.write('\n'); writer.flush();
            }
        } catch (InterruptedException | IOException ignore) {
        } finally { running.set(false); }
    }
}
