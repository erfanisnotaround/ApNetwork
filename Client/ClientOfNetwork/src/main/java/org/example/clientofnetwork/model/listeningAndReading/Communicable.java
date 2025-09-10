package org.example.clientofnetwork.model.listeningAndReading;

import java.util.function.Consumer;

public interface Communicable {
    void addString(String newComer);
    void setLineProcessor(Consumer<String> processor);
    void StartCommunicatingTCP();
}
