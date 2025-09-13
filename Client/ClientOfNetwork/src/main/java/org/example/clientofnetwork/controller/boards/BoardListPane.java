// org/example/clientofnetwork/controller/boards/BoardListPane.java
package org.example.clientofnetwork.controller.boards;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.clientofnetwork.model.boardRelated.BoardSummary;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class BoardListPane extends VBox {

    public interface Actions {
        Consumer<BoardSummary> onOpenBoard();
        BiConsumer<BoardSummary, Void> onInvite();
    }

    private Actions actions = new Actions() {
        @Override public Consumer<BoardSummary> onOpenBoard() { return b -> {}; }
        @Override public BiConsumer<BoardSummary, Void> onInvite() { return (b, v) -> {}; }
    };

    public BoardListPane() {
        setSpacing(8);
        setPadding(new Insets(10));
    }

    public void setActions(Actions actions) { this.actions = Objects.requireNonNull(actions); }

    /** Re-render rows from given list, or show empty message. */
    public void setBoards(List<BoardSummary> boards) {
        getChildren().clear();
        if (boards == null || boards.isEmpty()) {
            var empty = new Label("You have no boards.");
            empty.setStyle("-fx-text-fill: -fx-text-background-color; -fx-opacity: 0.7;");
            getChildren().add(empty);
            return;
        }
        for (var b : boards) getChildren().add(rowFor(b));
    }

    private HBox rowFor(BoardSummary b) {
        var box = new HBox(8);
        box.setAlignment(Pos.CENTER_LEFT);

        var open = new Button(b.getBoardName());
        open.setTooltip(new Tooltip("Open board \"" + b.getBoardName() + "\""));
        open.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(open, Priority.ALWAYS);

        var invite = new Button("Invite");
        invite.setTooltip(new Tooltip("Invite a user to this board"));

        open.setOnAction(e -> actions.onOpenBoard().accept(b));
        invite.setOnAction(e -> actions.onInvite().accept(b, null));

        box.getChildren().addAll(open, invite);
        return box;
    }
}
