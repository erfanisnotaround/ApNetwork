package org.example.clientofnetwork.controller.uiChanging;


import javafx.application.Platform;

public final class JavaFxUiExecutor implements UiExecutor {
    @Override public void exec(Runnable r) { Platform.runLater(r); }
}