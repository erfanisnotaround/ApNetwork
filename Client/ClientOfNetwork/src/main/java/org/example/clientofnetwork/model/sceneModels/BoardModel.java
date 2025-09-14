// org/example/clientofnetwork/model/sceneModels/BoardModel.java
package org.example.clientofnetwork.model.sceneModels;

import javafx.application.Platform;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.AddTaskPass;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.DeleteTaskPass;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.ListTasksPass;
import org.example.clientofnetwork.model.commands.buildInfo.boardInfoCommands.UpdateTaskStatusPass;
import org.example.clientofnetwork.model.commands.commandTypes.BoardTasks.AddTaskCommand;
import org.example.clientofnetwork.model.commands.commandTypes.BoardTasks.DeleteTaskCommand;
import org.example.clientofnetwork.model.commands.commandTypes.BoardTasks.ListTasksCommand;
import org.example.clientofnetwork.model.commands.commandTypes.BoardTasks.UpdateTaskStatusCommand;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskPriority;
import org.example.clientofnetwork.model.passingAndRecievingData.sameInfoes.TaskStatus;
import org.example.clientofnetwork.model.responseTypes.responseBoardInfo.ListTasksRes;
import org.example.clientofnetwork.model.task.TaskItem;


import java.util.List;
import java.util.Objects;

public final class BoardModel {

    public interface View {
        void renderTasks(List<TaskItem> tasks);
        void renderEmptyTasks();
        void setStatusMessage(String msg);
        void showTask(TaskItem t);
    }

    private final SceneManager scenes;
    private final CommandManager commands;
    private final View view;
    private final ClientInfo client;
    private final BoardSummary board;

    private TaskItem selected;

    public BoardModel(SceneManager scenes, CommandManager commands, View view, ClientInfo client, BoardSummary board) {
        this.scenes = Objects.requireNonNull(scenes);
        this.commands = Objects.requireNonNull(commands);
        this.view = Objects.requireNonNull(view);
        this.client = Objects.requireNonNull(client);
        this.board = Objects.requireNonNull(board);
    }

    public void loadTasks() {
        fx(() -> view.setStatusMessage("Loading tasks..."));
        commands.send(
                new ListTasksCommand(
                        this::onTasksOk,
                        err -> fx(() -> view.setStatusMessage("Load failed: " + err))
                ),
                new ListTasksPass(board.getBoardId())
        );
    }

    private void onTasksOk(ListTasksRes res) {
        List<TaskItem> tasks = (res != null && res.getTasks() != null) ? res.getTasks() : List.of();
        if (tasks.isEmpty()) {
            fx(() -> {
                view.renderEmptyTasks();
                selected = null;
                view.showTask(null);
                view.setStatusMessage("No tasks.");
            });
        } else {
            fx(() -> {
                view.renderTasks(tasks);
                // auto-select first
                selected = tasks.get(0);
                view.showTask(selected);
                view.setStatusMessage("Loaded " + tasks.size() + " task(s).");
            });
        }
    }

    public void onSelectTask(TaskItem t) {
        this.selected = t;
        fx(() -> view.showTask(t));
    }

    public void onChangeStatus(TaskStatus newStatus) {
        if (selected == null || newStatus == null) return;
        if (selected.getStatus() == newStatus) return;

        final String taskId = selected.getTaskId();
        fx(() -> view.setStatusMessage("Changing status..."));
        commands.send(
                new UpdateTaskStatusCommand(
                        ok -> fx(() -> {
                            selected.setStatus(newStatus);
                            view.showTask(selected);
                            view.setStatusMessage("Status updated.");
                        }),
                        err -> fx(() -> view.setStatusMessage("Change failed: " + err))
                ),
                new UpdateTaskStatusPass(taskId, newStatus)
        );
    }

    public void onDeleteSelected() {
        if (selected == null) return;
        final String taskId = selected.getTaskId();
        fx(() -> view.setStatusMessage("Deleting..."));
        commands.send(
                new DeleteTaskCommand(
                        ok -> fx(() -> {
                            view.setStatusMessage("Deleted.");
                            loadTasks(); // refresh list
                        }),
                        err -> fx(() -> view.setStatusMessage("Delete failed: " + err))
                ),
                new DeleteTaskPass(taskId)
        );
    }

    public void onAddTask(String title, String description, TaskPriority priority) {
        fx(() -> view.setStatusMessage("Adding task..."));
        commands.send(
                new AddTaskCommand(
                        ok -> fx(() -> {
                            view.setStatusMessage("Task added.");
                            loadTasks();
                        }),
                        err -> fx(() -> view.setStatusMessage("Add failed: " + err))
                ),
                new AddTaskPass(board.getBoardId(), title, description, priority)
        );
    }

    private static void fx(Runnable r) {
        if (Platform.isFxApplicationThread()) r.run();
        else Platform.runLater(r);
    }
}
