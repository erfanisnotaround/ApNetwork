package org.example.clientofnetwork.controller.boards;

// org/example/clientofnetwork/controller/boards/AddTaskDialog.java


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskPriority;


import java.util.function.Consumer;

public final class AddTaskDialog {
    private final Stage stage = new Stage();
    private final TextField title = new TextField();
    private final TextArea  desc  = new TextArea();
    private final ComboBox<TaskPriority> prio = new ComboBox<>();
    private final Label status = new Label();
    private final Button ok = new Button("Add");
    private final Button cancel = new Button("Cancel");

    private Consumer<Triple> onOk = t -> {};
    private Consumer<Void> onCancel = v -> {};

    public static final class Triple {
        public final String title, desc; public final TaskPriority prio;
        public Triple(String t, String d, TaskPriority p){ this.title=t; this.desc=d; this.prio=p; }
    }

    public AddTaskDialog(Window owner) {
        if (owner != null) stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add Task");

        prio.getItems().setAll(TaskPriority.values());
        prio.getSelectionModel().select(TaskPriority.MEDIUM);

        desc.setPrefRowCount(5);

        var grid = new GridPane();
        grid.setPadding(new Insets(14)); grid.setHgap(8); grid.setVgap(10);

        grid.add(new Label("Title"), 0, 0); grid.add(title, 1, 0);
        grid.add(new Label("Description"), 0, 1); grid.add(desc, 1, 1);
        grid.add(new Label("Priority"), 0, 2); grid.add(prio, 1, 2);

        var buttons = new HBox(8, ok, cancel); buttons.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttons, 0, 3, 2, 1);
        grid.add(status, 0, 4, 2, 1);

        ok.setOnAction(e -> onOk.accept(new Triple(title.getText(), desc.getText(), prio.getValue())));
        cancel.setOnAction(e -> { onCancel.accept(null); stage.close(); });

        stage.setScene(new Scene(grid, 420, 280));
    }

    public void onOk(Consumer3<String,String,TaskPriority> handler) {
        this.onOk = t -> handler.accept(t.title, t.desc, t.prio);
    }
    public void onCancel(Consumer<Void> handler) { this.onCancel = (handler != null ? handler : v->{}); }
    public void setStatus(String s){ status.setText(s); }
    public void show(){ stage.showAndWait(); }
    public void close(){ stage.close(); }

    @FunctionalInterface
    public interface Consumer3<A,B,C> { void accept(A a, B b, C c); }
}
