package org.example.clientofnetwork.model.sceneModels;

import javafx.scene.control.Label;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.controller.sceneControllers.MainController;
import org.example.clientofnetwork.controller.uiChanging.UiExecutor;
import org.example.clientofnetwork.controller.uiChanging.types.FxLoginPresenter;
import org.example.clientofnetwork.controller.uiChanging.types.FxRegisterPresenter;
import org.example.clientofnetwork.controller.uiplots.TwoFieldDialog;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.commands.CommandManager;
import org.example.clientofnetwork.model.commands.Commendable;
import org.example.clientofnetwork.model.commands.buildInfo.LoginPass;
import org.example.clientofnetwork.model.commands.buildInfo.RegisterPass;
import org.example.clientofnetwork.model.commands.commandTypes.LoginCommand;
import org.example.clientofnetwork.model.commands.commandTypes.RegisterCommand;
import org.example.clientofnetwork.model.responseTypes.LoginRes;
import org.example.clientofnetwork.model.responseTypes.RegisterRes;
import org.example.clientofnetwork.model.sceneInformation.GoingToLoginFromScratch;

public class LoginModel {
    private GoingToLoginFromScratch gointTologinFromScratch;
    private Commendable<LoginPass, LoginRes> loginCOmmand;
    private Commendable<RegisterPass, RegisterRes> registerCOmmand;


    private CommandManager commandManager;
    public LoginModel(GoingToLoginFromScratch gointTologinFromScratch) {
        this.gointTologinFromScratch = gointTologinFromScratch;

        this.commandManager = gointTologinFromScratch.commandManager;
    }

    public void onLogin(String username, String password , String token) {
        commandManager.<LoginPass, LoginRes> send(loginCOmmand , new LoginPass(username , password , token));
    }
    public void onRegister(String username, String password) {
        commandManager.send(registerCOmmand , new RegisterPass(username , password));
    }

    public void RegisterLoginCommand(SceneManager sceneManager , Label ingp  , UiExecutor uiExecutor) {
        loginCOmmand = new LoginCommand(new FxLoginPresenter(ingp , sceneManager , gointTologinFromScratch.clientInfo , uiExecutor , () -> {
            sceneManager.switchScreen(PositionStatus.MAIN_VIEW , new MainController.Data(commandManager , gointTologinFromScratch.clientInfo));
        }));
    }
    public void RegisterDataCommand(SceneManager sceneManager , TwoFieldDialog fieldDialog, UiExecutor uiExecutor) {
        registerCOmmand = new RegisterCommand(new FxRegisterPresenter(sceneManager , fieldDialog , uiExecutor , () ->{
            sceneManager.switchScreen(PositionStatus.MAIN_VIEW  , new MainController.Data(commandManager , gointTologinFromScratch.clientInfo));
            fieldDialog.hide();
        }));
    }
}
