package org.example.clientofnetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.clientofnetwork.controller.WiringChangeable;
import org.example.clientofnetwork.model.agents.screenChanging.SceneManager;
import org.example.clientofnetwork.model.sceneInformation.GoingToLoginFromScratch;

public class Main extends Application {

    @Override public void start(Stage stage) throws Exception {

        WiringChangeable.Result result = WiringChangeable.boot();
        SceneManager manager = new SceneManager(stage);

        stage.show();
        manager.switchScreen(PositionStatus.LOGIN_VIEW , new GoingToLoginFromScratch(result.commandManager() , result.info()));
    }
    public static void main(String[] args){ launch(args); }
}
