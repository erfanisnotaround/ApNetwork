package org.example.clientofnetwork.controller.uiplots;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;

import java.util.Optional;
import java.util.function.BiPredicate;

public final class TwoFieldDialog {

    /* --- result type --- */
    public static final class Values {
        public final String first, second;
        public Values(String first, String second) { this.first = first; this.second = second;  }
    }

    /* --- UI --- */
    private final Stage stage = new Stage();
    private final Label header = new Label("Please enter values");
    private final Label firstLabel = new Label("First");
    private final Label secondLabel = new Label("Second");
    private final TextField firstField = new TextField();
    private final TextField secondField = new TextField();
    private final Button okBtn = new Button("OK");
    private final Button cancelBtn = new Button("Cancel");

    private Optional<Values> result = Optional.empty();

    /* default: both non-blank */
    private BiPredicate<String,String> validator = (a,b) -> !a.isBlank() && !b.isBlank();

    public TwoFieldDialog(Window owner , SceneManager sceneManager) {
        if (owner != null) stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Input");

        GridPane root = new GridPane();
        root.setPadding(new Insets(14));
        root.setHgap(8);
        root.setVgap(10);

        HBox buttons = new HBox(8, okBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        root.add(header,       0, 0, 2, 1);
        root.add(new Separator(), 0, 1, 2, 1);
        root.add(firstLabel,   0, 2);
        root.add(firstField,   1, 2);
        root.add(secondLabel,  0, 3);
        root.add(secondField,  1, 3);
        root.add(buttons,      0, 4, 2, 1);

        okBtn.setDefaultButton(true);
        cancelBtn.setCancelButton(true);



        // Enter to OK, Esc to Cancel
//        firstField.setOnKeyPressed(e -> { if (e.getCode()== KeyCode.ENTER && !okBtn.isDisabled()) okBtn.fire(); });
//        secondField.setOnKeyPressed(e -> { if (e.getCode()== KeyCode.ENTER && !okBtn.isDisabled()) okBtn.fire(); });
//        root.setOnKeyPressed(e -> { if (e.getCode()==KeyCode.ESCAPE) cancelBtn.fire(); });

        // initial validation & live updates
        Runnable refresh = () ->
                okBtn.setDisable(!validator.test(firstField.getText(), secondField.getText()));
        firstField.textProperty().addListener((obs, o, n) -> refresh.run());
        secondField.textProperty().addListener((obs, o, n) -> refresh.run());
        refresh.run();

        stage.setScene(new Scene(root, 360, 180));
    }

    /* --- API --- */
    public Optional<Values> showAndGet() { stage.showAndWait(); return result; }
    public Stage stage() { return stage; }  // if you need to centerOnScreen(), icons, etc.

    /* --- Simple setters you asked for --- */
    public void setTitle(String v)             { stage.setTitle(v); }
    public void setHeaderText(String v)        { header.setText(v); }
    public void setFirstLabelText(String v)    { firstLabel.setText(v); }
    public void setSecondLabelText(String v)   { secondLabel.setText(v); }
    public void setFirstPrompt(String v)       { firstField.setPromptText(v); }
    public void setSecondPrompt(String v)      { secondField.setPromptText(v); }
    public void setOkText(String v)            { okBtn.setText(v); }
    public void setCancelText(String v)        { cancelBtn.setText(v); }
    public void setValidator(BiPredicate<String,String> v) {
        validator = (v != null ? v : (a,b) -> true);
        okBtn.setDisable(!validator.test(firstField.getText(), secondField.getText()));
    }
    public void setPrefill(String first, String second) {
        firstField.setText(first != null ? first : "");
        secondField.setText(second != null ? second : "");
    }
    public void setFirstText(String v)         { firstField.setText(v != null ? v : ""); }
    public void setSecondText(String v)        { secondField.setText(v != null ? v : ""); }

    public String getFirstText()  { return firstField.getText(); }
    public String getSecondText() { return secondField.getText(); }

    public void show(){
        stage.show();
    }
    public void hide(){
        stage.close();
    }
    public void OkButtonSetOnAction(Runnable runnable) {
        okBtn.setOnAction(e -> {
            System.out.println(766);
            runnable.run();
        });
    }

    public void CancelButtonSetOnAction(Runnable runnable) {
        cancelBtn.setOnAction(e -> runnable.run());  // fixed
    }
}

