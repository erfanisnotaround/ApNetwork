// org/example/clientofnetwork/model/sceneModels/MainModel.java
package org.example.clientofnetwork.model.sceneModels;

import javafx.application.Platform;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.commands.buildInfo.BoardListPass;
import org.example.clientofnetwork.model.commands.commandTypes.ListBoardCommand;
import org.example.clientofnetwork.model.commands.commandTypes.CreateBoardCommand;
import org.example.clientofnetwork.model.commands.buildInfo.CreateBoardPass;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.responseTypes.BoardListRes;
import org.example.clientofnetwork.model.responseTypes.CreateBoardRes;

import java.util.List;
import java.util.Objects;

public final class MainModel {

    /** View port keeps the controller thin (SRP, DIP). */
    public interface View {
        void showBoards(List<BoardSummary> boards);
        void showEmpty();
        void setSumCount(int n);
        void setStatus(String msg);
        void openBoardScene(BoardSummary b);
        void openInviteDialog(BoardSummary b);
    }

    private final SceneManager scenes;
    private final CommandManager commands;
    private final View view;
    @SuppressWarnings("unused")
    private final ClientInfo clientInfo; // kept if you need token/username later

    public MainModel(SceneManager scenes, CommandManager commands, View view, ClientInfo clientInfo) {
        this.scenes     = Objects.requireNonNull(scenes);
        this.commands   = Objects.requireNonNull(commands);
        this.view       = Objects.requireNonNull(view);
        this.clientInfo = Objects.requireNonNull(clientInfo);
    }

    /** Right-side: Show Boards */
    public void onShowBoards() {
        fx(() -> view.setStatus("Loading boards..."));
        commands.send(
                new ListBoardCommand(
                        this::onBoardsSuccess,
                        err -> fx(() -> view.setStatus("Load failed: " + err))
                ),
                new BoardListPass(clientInfo.getUserName()) // LIST_BOARDS has no pass
        );
    }

    private void onBoardsSuccess(BoardListRes res) {
        List<BoardSummary> boards = (res != null && res.getBoards()!=null) ? res.getBoards() : List.of();
        if (boards.isEmpty()) {
            fx(() -> {
                view.showEmpty();
                view.setSumCount(0);
                view.setStatus("You have no boards.");
            });
        } else {
            fx(() -> {
                view.showBoards(boards);
                view.setSumCount(boards.size());
                view.setStatus("Loaded " + boards.size() + " board(s).");
            });
        }
    }

    /** Per-row: Open board */
    public void onOpenBoard(BoardSummary b) {
        fx(() -> view.setStatus("Opening \"" + b.getBoardName() + "\"..."));
        // If you need a VIEW_BOARD command, send it here; then:
        fx(() -> {
            view.openBoardScene(b);
            scenes.switchScreen(PositionStatus.BOARD_VIEW);
        });
    }

    /** Per-row: Invite */
    public void onInvite(BoardSummary b) {
        fx(() -> view.openInviteDialog(b));
    }

    /** Right-side: Logout */
    public void onLogout() {
        fx(() -> {
            view.setStatus("Logging out...");
            scenes.switchScreen(PositionStatus.LOGIN_VIEW);
        });
    }

    /** Right-side: Create Board (called after dialog OK) */
    public void onCreateBoard(String name) {
        if (name == null || name.isBlank()) { fx(() -> view.setStatus("Board name required")); return; }
        fx(() -> view.setStatus("Creating board..."));

        commands.send(
                new CreateBoardCommand(
                        this::onCreateBoardSuccess,
                        err -> fx(() -> view.setStatus("Create failed: " + err))
                ),
                new CreateBoardPass(name , clientInfo.getUserName())
        );
    }

    private void onCreateBoardSuccess(CreateBoardRes res) {
        // Adapt getters to your DTO if names differ
        BoardSummary created = new BoardSummary(res.getBoardId(), res.getName());
        fx(() -> {
            view.setStatus("Board created: " + created.getBoardName());
            view.openBoardScene(created);
            scenes.switchScreen(PositionStatus.BOARD_VIEW);
        });
    }

    /* Ensure UI updates happen on the FX application thread */
    private static void fx(Runnable r) {
        if (Platform.isFxApplicationThread()) r.run();
        else Platform.runLater(r);
    }
}
