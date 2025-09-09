package org.example.clientofnetwork;

public enum PositionStatus {
    LOGIN_VIEW("/org/example/clientofnetwork/fxmlFiles/Login.fxml"),
    MAIN_VIEW("/org/example/clientofnetwork/fxmlFiles/Main.fxml"),
    BOARD_VIEW("/org/example/clientofnetwork/fxmlFiles/Board.fxml");
    private String path;
    PositionStatus(String path) {
        this.path = path;
    }
    public String getPath() {
        return this.path;
    }
}
