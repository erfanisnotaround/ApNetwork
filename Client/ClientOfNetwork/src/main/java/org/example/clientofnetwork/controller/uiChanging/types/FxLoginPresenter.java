package org.example.clientofnetwork.controller.uiChanging.types;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.controller.sceneControllers.MainController;
import org.example.clientofnetwork.controller.uiChanging.UiExecutor;
import org.example.clientofnetwork.controller.uiChanging.types.interfaces.CommandPresenter;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.passingAndRecievingData.client.ClientInfo;
import org.example.clientofnetwork.model.responseTypes.LoginRes;

public class FxLoginPresenter implements CommandPresenter<LoginRes> {
    private final SceneManager sceneManager;
    private final Label LoginResultLabel;
    private final ClientInfo clientInfo;
    private UiExecutor uiExecutor;
    private Runnable runnable;
    public FxLoginPresenter(Label loginResultLabel, SceneManager sceneManager , ClientInfo clientInfo , UiExecutor uiExecutor , Runnable runnable) {
        this.LoginResultLabel = loginResultLabel;
        this.sceneManager = sceneManager;
        this.clientInfo = clientInfo;
        this.uiExecutor = uiExecutor;
        this.runnable = runnable;
    }

    @Override
    public void showSuccess(LoginRes res) {
        uiExecutor.exec(() ->{
            System.out.println("login success");
            clientInfo.setUserName(res.getUserName());
            clientInfo.setId(res.getId());

            runnable.run();
        });


    }

    @Override
    public void showFailure(String message) {
        uiExecutor.exec(()->{
            LoginResultLabel.setText(message);

        });
    }
}
