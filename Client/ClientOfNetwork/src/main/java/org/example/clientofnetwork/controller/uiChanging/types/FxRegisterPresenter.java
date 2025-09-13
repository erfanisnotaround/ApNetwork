package org.example.clientofnetwork.controller.uiChanging.types;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.clientofnetwork.PositionStatus;
import org.example.clientofnetwork.controller.uiChanging.UiExecutor;
import org.example.clientofnetwork.controller.uiChanging.types.interfaces.CommandPresenter;
import org.example.clientofnetwork.controller.uiplots.TwoFieldDialog;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.responseTypes.RegisterRes;

public class FxRegisterPresenter implements CommandPresenter<RegisterRes> {

    private final SceneManager sceneManager;

    private final TwoFieldDialog twoFieldDialog;
    private final Runnable runnable;
    private UiExecutor uiExecutor;
    private final double duration = 10;

    public FxRegisterPresenter(SceneManager sceneManager, TwoFieldDialog twoFieldDialog , UiExecutor uiExecutor , Runnable runnable) {
        this.sceneManager = sceneManager;
        this.twoFieldDialog = twoFieldDialog;
        this.uiExecutor = uiExecutor;
        this.runnable = runnable;
    }
    @Override
    public void showSuccess(RegisterRes res) {
        uiExecutor.exec(() -> {
            twoFieldDialog.setFirstLabelText("Registered Successfully");
            twoFieldDialog.setSecondLabelText(res.getToken());


            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(duration));
            pauseTransition.setOnFinished(event -> {
                runnable.run();
            });
            pauseTransition.play();
        });
    }

    @Override
    public void showFailure(String message) {
        uiExecutor.exec(() -> {
            twoFieldDialog.setFirstLabelText(message);

        });
    }
}
