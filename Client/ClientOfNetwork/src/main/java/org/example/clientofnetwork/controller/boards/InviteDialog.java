// org/example/clientofnetwork/controller/boards/InviteDialog.java
package org.example.clientofnetwork.controller.boards;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class InviteDialog {
    private final Stage stage = new Stage();
    private final TextField userField = new TextField();
    private final TextField secondField = new TextField();
    private final Label status = new Label();
    private final Button ok = new Button("OK");
    private final Button cancel = new Button("Cancel");

    private BiConsumer<String,String> onOk = (u,s) -> {};
    private Consumer<Void> onCancel = v -> {};

    public InviteDialog(Window owner) {
        if (owner != null) stage.initOwner(owner);
        stage.setTitle("Invite");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        var grid = new GridPane();
        grid.setPadding(new Insets(14));
        grid.setHgap(8);
        grid.setVgap(10);

        grid.add(new Label("Username"), 0, 0);
        grid.add(userField, 1, 0);
        grid.add(new Label("Extra"), 0, 1);
        grid.add(secondField, 1, 1);

        var buttons = new HBox(8, ok, cancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttons, 0, 2, 2, 1);
        grid.add(status, 0, 3, 2, 1);

        ok.setOnAction(e -> onOk.accept(userField.getText().trim(), secondField.getText().trim()));
        cancel.setOnAction(e -> { onCancel.accept(null); stage.close(); });

        stage.setScene(new Scene(grid, 360, 180));
    }

    public void setTitle(String t) { stage.setTitle(t); }
    public void setPrompts(String u, String s) { userField.setPromptText(u); secondField.setPromptText(s); }
    public void prefill(String u, String s) { userField.setText(u != null ? u : ""); secondField.setText(s != null ? s : ""); }
    public void setStatus(String msg) { status.setText(msg); }
    public void onOk(BiConsumer<String,String> h) { this.onOk = (h != null ? h : (a,b)->{}); }
    public void onCancel(Consumer<Void> h) { this.onCancel = (h != null ? h : v->{}); }
    public void show() { stage.showAndWait(); }
    public void close() { stage.close(); }
}
