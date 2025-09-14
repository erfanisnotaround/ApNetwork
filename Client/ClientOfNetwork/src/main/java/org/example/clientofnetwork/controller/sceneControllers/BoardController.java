// org/example/clientofnetwork/controller/sceneControllers/BoardController.java
package org.example.clientofnetwork.controller.sceneControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.clientofnetwork.Maker;

import org.example.clientofnetwork.controller.boards.AddTaskDialog;
import org.example.clientofnetwork.controller.task.TaskListPane;
import org.example.clientofnetwork.model.agents.screenChanging.ControlledScreen;
import org.example.clientofnetwork.model.agents.screenChanging.DataReceivingController;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskStatus;
import org.example.clientofnetwork.model.sceneModels.BoardModel;
import org.example.clientofnetwork.model.task.TaskItem;


import java.util.List;
import java.util.function.Consumer;

public final class BoardController implements Maker, ControlledScreen,
        DataReceivingController<BoardController.Data>, BoardModel.View {

    public static final class Data {
        public final CommandManager commands;
        public final ClientInfo client;
        public final BoardSummary board;
        public Data(CommandManager commands, ClientInfo client, BoardSummary board) {
            this.commands = commands; this.client = client; this.board = board;
        }
    }

    @FXML private VBox leftContainer;
    @FXML private Button addTaskBtn, refreshBtn, deleteBtn;
    @FXML private Label titleValue, prioValue, statusLabel;
    @FXML private TextArea descValue;
    @FXML private ComboBox<TaskStatus> statusBox;

    private SceneManager scenes;
    private BoardModel model;
    private TaskListPane listPane;

    private CommandManager commands;
    private ClientInfo client;
    private BoardSummary board;

    @Override public void setSceneManager(SceneManager sceneManager) { this.scenes = sceneManager; }

    @Override public void initData(Data data) {
        this.commands = data.commands;
        this.client   = data.client;
        this.board    = data.board;
    }

    @Override public void MakeFirst() {
        if (commands == null || client == null || board == null)
            throw new IllegalStateException("BoardController requires commands, client, and board.");

        model = new BoardModel(scenes, commands, this, client, board);

        // Left list
        listPane = new TaskListPane();
        listPane.setActions(new TaskListPane.Actions() {
            @Override public Consumer<TaskItem> onSelect() { return model::onSelectTask; }
        });
        leftContainer.getChildren().setAll(listPane);

        // Right controls
        statusBox.getItems().setAll(TaskStatus.values());
        statusBox.setOnAction(e -> {
            TaskStatus s = statusBox.getValue();
            if (s != null) model.onChangeStatus(s);
        });
        deleteBtn.setOnAction(e -> model.onDeleteSelected());

        addTaskBtn.setOnAction(e -> openAddDialog());
        refreshBtn.setOnAction(e -> model.loadTasks());

        commands.addNotifyListener(env ->
                Platform.runLater(() -> {
                    // Optional filter: only show notifies for this board
                    // if (env.getDataRec() != null && env.getDataRec().has("boardId") &&
                    //     !board.getBoardId().equals(env.getDataRec().get("boardId").asText())) return;

                    String msg = env.getMessage();
                    if (msg == null && env.getDataRec() != null) msg = env.getDataRec().toString();
                    statusLabel.setText(msg != null ? msg : "(notify)");
                })
        );
        // Initial load
        model.loadTasks();
    }

    private void openAddDialog() {
        var dlg = new AddTaskDialog(scenes.getStage());
        dlg.onOk((title, desc, prio) -> {
            if (title == null || title.isBlank()) { dlg.setStatus("Title required"); return; }
            dlg.close();
            model.onAddTask(title.trim(), desc==null? "" : desc.trim(), prio);
        });
        dlg.onCancel(v -> dlg.close());
        dlg.show();
    }

    /* ===== BoardModel.View ===== */
    @Override public void renderTasks(List<TaskItem> tasks) { listPane.setTasks(tasks); }

    @Override public void renderEmptyTasks() { listPane.setTasks(List.of()); }

    @Override public void setStatusMessage(String msg) { statusLabel.setText(msg); }

    @Override public void showTask(TaskItem t) {
        if (t == null) {
            titleValue.setText(""); descValue.setText(""); prioValue.setText(""); statusBox.getSelectionModel().clearSelection();
            return;
        }
        titleValue.setText(t.getTitle());
        descValue.setText(t.getDescription());
        prioValue.setText(String.valueOf(t.getPriority()));
        statusBox.getSelectionModel().select(t.getStatus());
    }
}
