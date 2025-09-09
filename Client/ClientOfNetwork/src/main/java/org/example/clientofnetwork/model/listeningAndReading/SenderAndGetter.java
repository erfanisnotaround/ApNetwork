package org.example.clientofnetwork.model.listeningAndReading;

import org.example.clientofnetwork.model.contexts.NetContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class SenderAndGetter implements Communicable{
    private final int port;
    private final String host;
    private Socket socket;
    private NetContext netContext;

    private BufferedReader reader;
    private BufferedWriter writer;
    private volatile LinkedBlockingQueue<String> outbound = new LinkedBlockingQueue<>();
    private volatile AtomicBoolean running = new AtomicBoolean(false);

    private WriterTCP TcpWriterRunnable;
    private ReaderTCP TcpReaderRuuRunnable;

    private Thread TcpWriterThread, TcpReaderThread;
    private volatile Consumer<String> lineProcessor = s -> {};
    public SenderAndGetter(int port, String host , NetContext netContext) {
        this.port = port;
        this.host = host;
        this.netContext = netContext;
    }

    @Override
    public void addString(String newComer) {
        outbound.offer(newComer);
    }

    @Override
    public void StartCommunicatingTCP() {

        reader = netContext.in();
        writer = netContext.out();

        running.set(true);

        TcpWriterRunnable = new WriterTCP(outbound , writer , running);
        TcpReaderRuuRunnable = new ReaderTCP( reader , running);

        TcpReaderRuuRunnable.setOnLine(lineProcessor);
        TcpWriterThread = new Thread(TcpWriterRunnable);TcpWriterThread.setDaemon(true);TcpWriterThread.start();
        TcpReaderThread = new Thread(TcpReaderRuuRunnable);TcpReaderThread.setDaemon(true);TcpReaderThread.start();
    }
    public void setLineProcessor(Consumer<String> processor) {
        this.lineProcessor = (processor != null ? processor : s -> {});
        if (TcpReaderRuuRunnable != null) TcpReaderRuuRunnable.setOnLine(this.lineProcessor);
    }
}
