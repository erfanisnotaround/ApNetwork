package org.example.clientofnetwork.model.listeningAndReading;

import org.example.clientofnetwork.model.contexts.NetContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public final class SenderAndGetter implements Communicable, AutoCloseable {

    private final NetContext netContext;

    private BufferedReader reader;
    private BufferedWriter writer;

    // Blocks when empty → no polling, no sleeps
    private final LinkedBlockingQueue<String> outbound = new LinkedBlockingQueue<>();

    // Shared on/off flag for both runnables
    private final AtomicBoolean running = new AtomicBoolean(false);

    private WriterTCP writerRunnable;
    private ReaderTCP readerRunnable;

    private Thread writerThread, readerThread;

    // Incoming-line callback
    private volatile Consumer<String> lineProcessor = s -> {};

    public SenderAndGetter(NetContext netContext) {
        this.netContext = netContext;
    }

    @Override
    public void addString(String newComer) {
        if (newComer != null) outbound.offer(newComer);
    }

    @Override
    public void setLineProcessor(Consumer<String> processor) {
        this.lineProcessor = (processor != null ? processor : s -> {});
        if (readerRunnable != null) readerRunnable.setOnLine(this.lineProcessor);
    }

    /** Start two blocking threads (reader: readLine, writer: queue.take). No timers. */
    @Override
    public void StartCommunicatingTCP() {
        if (!running.compareAndSet(false, true)) return;

        reader = netContext.in();
        writer = netContext.out();

        writerRunnable = new WriterTCP(outbound, writer, running);
        readerRunnable = new ReaderTCP(reader, running);

        // attach callback BEFORE starting reader to avoid races
        readerRunnable.setOnLine(lineProcessor);

        writerThread = new Thread(writerRunnable, "tcp-writer");
        readerThread = new Thread(readerRunnable, "tcp-reader");
        writerThread.setDaemon(true);
        readerThread.setDaemon(true);
        writerThread.start();
        readerThread.start();
    }

    /** Stop the two threads (does NOT close the socket). Useful if someone else owns NetContext. */
    public void stopOnly() {
        if (!running.compareAndSet(true, false)) return;
        try { if (writerThread != null) writerThread.interrupt(); } catch (Exception ignore) {}
        try { if (readerThread != null) readerThread.interrupt(); } catch (Exception ignore) {}
    }

    /** Stop threads AND close the underlying socket/streams. */
    @Override
    public void close() {
        stopOnly();
        try { netContext.close(); } catch (Exception ignore) {}
    }
}
