// org/example/clientofnetwork/model/sceneModels/MainModel.java
package org.example.clientofnetwork.model.sceneModels;

import javafx.application.Platform;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.commands.buildInfo.AddingUserToTheBoardPass;
import org.example.clientofnetwork.model.commands.buildInfo.BoardListPass;
import org.example.clientofnetwork.model.commands.commandTypes.AddUserToTheBoardCommand;
import org.example.clientofnetwork.model.commands.commandTypes.ListBoardCommand;
import org.example.clientofnetwork.model.commands.commandTypes.CreateBoardCommand;
import org.example.clientofnetwork.model.commands.buildInfo.CreateBoardPass;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.responseTypes.BoardListRes;
import org.example.clientofnetwork.model.responseTypes.CreateBoardRes;
import org.example.clientofnetwork.model.sceneInformation.BoardChanging;

import java.util.List;
import java.util.Objects;

public final class MainModel {


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
                new BoardListPass(clientInfo.getId()) // LIST_BOARDS has no pass
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


    public void onOpenBoard(BoardSummary b) {
        fx(() -> view.setStatus("Opening \"" + b.getBoardName() + "\"..."));

        fx(() -> {
            view.openBoardScene(b);
            scenes.switchScreen(PositionStatus.BOARD_VIEW);
        });
    }


    public void onInvite(BoardSummary b) {
        fx(() -> view.openInviteDialog(b));
    }


    public void onLogout() {
        fx(() -> {
            view.setStatus("Logging out...");
            scenes.switchScreen(PositionStatus.LOGIN_VIEW);
        });
    }

    public void onCreateBoard(String name) {
        if (name == null || name.isBlank()) { fx(() -> view.setStatus("Board name required")); return; }
        fx(() -> view.setStatus("Creating board..."));

        commands.send(
                new CreateBoardCommand(
                        this::onCreateBoardSuccess,
                        err -> fx(() -> view.setStatus("Create failed: " + err))
                ),
                new CreateBoardPass(name , clientInfo.getId())
        );
    }

    public void onInviteConfirm(BoardSummary board, String username) {
        if (board == null || username == null || username.isBlank()) {
            fx(() -> view.setStatus("Invite requires a username."));
            return;
        }
        fx(() -> view.setStatus("Inviting " + username + " to " + board.getBoardName() + "..."));

        commands.send(
                new AddUserToTheBoardCommand(
                        res -> fx(() -> view.setStatus("Invite sent to " + username)),
                        err -> fx(() -> view.setStatus("Invite failed: " + err))
                ),
                new AddingUserToTheBoardPass(board.getBoardId(), username)
        );
    }
    private void onCreateBoardSuccess(CreateBoardRes res) {

        BoardSummary created = new BoardSummary();
        created.setBoardName(res.getBoardName());
        created.setBoardId(res.getBoardId());
        fx(() -> {
            view.setStatus("Board created: " + created.getBoardName());
            view.openBoardScene(created);
            scenes.switchScreen(PositionStatus.BOARD_VIEW , new BoardChanging(commands , clientInfo , created));
        });
    }

    /* Ensure UI updates happen on the FX application thread */
    private static void fx(Runnable r) {
        if (Platform.isFxApplicationThread()) r.run();
        else Platform.runLater(r);
    }
}
