package org.example.clientofnetwork.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.clientofnetwork.Maker;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.SessionState;
import org.example.clientofnetwork.model.agents.screenChanging.ControlledScreen;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.contexts.AppContext;
import org.example.clientofnetwork.model.contexts.NetContext;

import java.io.IOException;
import java.net.Socket;

public class LoginController implements Maker , ControlledScreen {
    @FXML
    private TextField hostField, portField, usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private SceneManager sceneManager;

    @Override public void setSceneManager(SceneManager sceneManager){ this.sceneManager = sceneManager; }

    @Override public void MakeFirst() {
        hostField.setText("127.0.0.1");
        portField.setText("5050");
        statusLabel.setText("Disconnected");
    }

    @FXML public void onConnect() {
        String host = hostField.getText();
        int port = Integer.parseInt(portField.getText());
        statusLabel.setText("Connecting...");
        new Thread(() -> {
            try {
                Socket s = new Socket(host, port); // connects to your 0.1 echo server
                NetContext net = new NetContext(s);
                AppContext app = new AppContext(net, new SessionState());
                Platform.runLater(() -> {
                    statusLabel.setText("Connected ✓");
                    sceneManager.switchScreen(PositionStatus.MAIN_VIEW , app);
                });
            } catch (IOException e) {
                Platform.runLater(() -> statusLabel.setText("Connect error: " + e.getMessage()));
            }
        }, "connect-thread").start();
    }
}
