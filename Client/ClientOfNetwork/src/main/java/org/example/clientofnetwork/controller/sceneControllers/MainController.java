// org/example/clientofnetwork/controller/sceneControllers/MainController.java
package org.example.clientofnetwork.controller.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.example.clientofnetwork.Maker;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.controller.boards.BoardListPane;
import org.example.clientofnetwork.controller.boards.InviteDialog;
import org.example.clientofnetwork.controller.uiplots.TwoFieldDialog;
import org.example.clientofnetwork.model.agents.screenChanging.ControlledScreen;
import org.example.clientofnetwork.model.agents.screenChanging.DataReceivingController;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.sceneModels.MainModel;

import java.util.List;
import java.util.function.Consumer;

public final class MainController implements Maker, ControlledScreen,
        DataReceivingController<MainController.Data>, MainModel.View {

    /** Only pass what the scene truly needs to start (DIP). */
    public static final class Data {
        public final CommandManager commandManager;
        public final ClientInfo clientInfo;
        public Data(CommandManager cm, ClientInfo info) { this.commandManager = cm; this.clientInfo = info; }
    }

    @FXML private VBox leftContainer;
    @FXML private Label sumIndicator;
    @FXML private Label statusLabel;
    @FXML private Button logoutBtn;
    @FXML private Button showBoardsBtn;
    @FXML private Button createBoardBtn; // <-- ensure in FXML

    private SceneManager scenes;
    private BoardListPane boardListPane;
    private MainModel model;
    private CommandManager commandManager;
    private ClientInfo clientInfo;

    @Override public void setSceneManager(SceneManager sceneManager) { this.scenes = sceneManager; }

    @Override public void initData(Data data) {
        this.commandManager = data.commandManager;
        this.clientInfo     = data.clientInfo;
    }

    @Override public void MakeFirst() {
        if (commandManager == null || clientInfo == null)
            throw new IllegalStateException("CommandManager and ClientInfo must be passed via Data");

        model = new MainModel(scenes, commandManager, this, clientInfo);

        boardListPane = new BoardListPane();
        leftContainer.getChildren().setAll(boardListPane);

        // Wire pane actions to model
        boardListPane.setActions(new BoardListPane.Actions() {
            @Override public Consumer<BoardSummary> onOpenBoard() { return model::onOpenBoard; }
            @Override public java.util.function.BiConsumer<BoardSummary, Void> onInvite() {
                return (b, v) -> model.onInvite(b);
            }
        });

        // Right side buttons
        showBoardsBtn.setOnAction(e -> model.onShowBoards());
        logoutBtn.setOnAction(e -> model.onLogout());
        createBoardBtn.setOnAction(e -> openCreateBoardDialog()); // NEW
    }

    /* ===== MainModel.View implementation ===== */
    @Override public void showBoards(List<BoardSummary> boards) { boardListPane.setBoards(boards); }
    @Override public void showEmpty() { boardListPane.setBoards(List.of()); }
    @Override public void setSumCount(int n) { sumIndicator.setText("Boards: " + n); }
    @Override public void setStatus(String msg) { statusLabel.setText(msg); }
    @Override public void openBoardScene(BoardSummary b) { scenes.switchScreen(PositionStatus.BOARD_VIEW); }

    @Override public void openInviteDialog(BoardSummary board) {
        Window owner = scenes.getStage();
        var dialog = new InviteDialog(owner);
        dialog.setTitle("Invite to “" + board.getBoardName() + "”");
        dialog.setPrompts("Username", "(optional)");
        dialog.prefill("", "");
        dialog.onOk((username, extra) -> {
            if (username.isBlank()) { dialog.setStatus("Username required"); return; }
            dialog.setStatus("Sending invite…");
            // TODO: execute InviteToBoard command here (board.getBoardId(), username)
            dialog.setStatus("Invite sent"); dialog.close();
            setStatus("Invited " + username + " to " + board.getBoardName());
        });
        dialog.onCancel(v -> dialog.close());
        dialog.show();
    }

    /* ===== Create Board dialog → calls model.onCreateBoard(...) ===== */
    private void openCreateBoardDialog() {
        var dlg = new TwoFieldDialog(scenes.getStage(), scenes);
        dlg.setTitle("Create Board");
        dlg.setHeaderText("Enter a name for the new board");
        dlg.setFirstLabelText("Board name");
        dlg.setSecondLabelText("(optional)");
        dlg.setFirstPrompt("e.g. My Team");
        dlg.setSecondPrompt("");
        dlg.setValidator((a,b) -> !a.isBlank());  // require a name
        dlg.setFirstText(""); dlg.setSecondText("");

        dlg.OkButtonSetOnAction(() -> {
            String name = dlg.getFirstText().trim();
            dlg.hide();
            model.onCreateBoard(name); // sends command; navigates on success
        });
        dlg.CancelButtonSetOnAction(dlg::hide);
        dlg.show();
    }
}
