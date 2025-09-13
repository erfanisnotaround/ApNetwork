package org.example.clientofnetwork.controller.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.clientofnetwork.Maker;
import org.example.clientofnetwork.controller.uiChanging.JavaFxUiExecutor;
import org.example.clientofnetwork.controller.uiChanging.UiExecutor;
import org.example.clientofnetwork.controller.uiplots.TwoFieldDialog;
import org.example.clientofnetwork.model.agents.screenChanging.ControlledScreen;
import org.example.clientofnetwork.model.agents.screenChanging.DataReceivingController;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;

import org.example.clientofnetwork.model.sceneInformation.GoingToLoginFromScratch;
import org.example.clientofnetwork.model.sceneModels.LoginModel;

public class LoginController implements Maker, ControlledScreen , DataReceivingController<GoingToLoginFromScratch> {
    @FXML private TextField usernameField, tokenField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML private Button loginButton , registerButton;

    private SceneManager sceneManager;
    private UiExecutor uiExecutor;
    private TwoFieldDialog twoFieldDialog;


    private LoginModel loginModel;




    @Override public void setSceneManager(SceneManager sceneManager){ this.sceneManager = sceneManager; }

    @Override public void MakeFirst() {

        twoFieldDialog = new TwoFieldDialog(sceneManager.getStage() , sceneManager);
        uiExecutor = new JavaFxUiExecutor();
        registerButton.setOnAction(event -> {onRegister();});
        loginButton.setOnAction(event -> {onLogin();});
        loginModel.RegisterLoginCommand(sceneManager , statusLabel, uiExecutor);
        loginModel.RegisterDataCommand(sceneManager , twoFieldDialog , uiExecutor);

        twoFieldDialog.OkButtonSetOnAction(() -> {
            System.out.println("da");
            loginModel.onRegister(twoFieldDialog.getFirstText() , twoFieldDialog.getSecondText());
        });
        twoFieldDialog.CancelButtonSetOnAction(() -> {twoFieldDialog.hide();});


    }

    @FXML private void onLogin() {
        loginModel.onLogin(usernameField.getText() , passwordField.getText() , tokenField.getText());
    }

    @FXML private void onRegister() {
        twoFieldDialog.show();
    }

    @Override
    public void initData(GoingToLoginFromScratch data) {

        loginModel = new LoginModel(data);
    }
}
