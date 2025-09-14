package org.example.clientofnetwork.controller.task;

// org/example/clientofnetwork/controller/tasks/TaskListPane.java


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.clientofnetwork.model.task.TaskItem;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class TaskListPane extends VBox {

    public interface Actions { Consumer<TaskItem> onSelect(); }

    private Actions actions = () -> t -> {};

    public TaskListPane() { setSpacing(8); setPadding(new Insets(4)); }

    public void setActions(Actions actions) { this.actions = Objects.requireNonNull(actions); }

    public void setTasks(List<TaskItem> tasks) {
        getChildren().clear();
        if (tasks == null || tasks.isEmpty()) {
            var empty = new Label("No tasks.");
            empty.setStyle("-fx-opacity: 0.7;");
            getChildren().add(empty); return;
        }
        for (var t : tasks) getChildren().add(row(t));
    }

    private HBox row(TaskItem t) {
        var box = new HBox(8); box.setAlignment(Pos.CENTER_LEFT);
        var btn = new Button(t.getTitle());
        btn.setTooltip(new Tooltip("Status: " + t.getStatus() + " | Priority: " + t.getPriority()));
        btn.setMaxWidth(Double.MAX_VALUE); HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setOnAction(e -> actions.onSelect().accept(t));
        box.getChildren().addAll(btn);
        return box;
    }
}
