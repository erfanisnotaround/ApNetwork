package org.example.clientofnetwork;

import javafx.scene.Parent;

public class ScreenBundle {
    private Parent root;
    private Object controller;

    public ScreenBundle(Parent root, Object controller) {
        this.root = root;
        this.controller = controller;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
