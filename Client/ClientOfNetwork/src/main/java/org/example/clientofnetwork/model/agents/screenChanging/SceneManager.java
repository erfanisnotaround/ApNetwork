package org.example.clientofnetwork.model.agents.screenChanging;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.clientofnetwork.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private final Stage stage;
    private Scene scene;
    private final Map<PositionStatus, ScreenBundle> cache = new HashMap<>();


    public SceneManager(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new Parent() {});
        this.stage.setScene(scene);

    }

    public void switchScreen(PositionStatus screen) {
        switchScreen(screen ,null);
    }

    @SuppressWarnings("unchecked")
    public <T> void switchScreen(PositionStatus screen, T data) {
        ScreenBundle bundle = cache.computeIfAbsent(screen, this::loadScreenBundle);
        if (bundle == null) {
            System.out.println("ss");
        }
        if (bundle != null) {
            if (data != null && bundle.getController() instanceof DataReceivingController) {
                ((DataReceivingController<T>) bundle.getController()).initData(data);
            }


            ((Maker) bundle.getController()).MakeFirst();
            animateAndSetScreen(bundle.getRoot());
        }

    }

    private ScreenBundle loadScreenBundle(PositionStatus screen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(screen.getPath()));
            Parent root = loader.load();
            Maker controller = loader.getController();

            if (controller instanceof ControlledScreen) {
                ((ControlledScreen) controller).setSceneManager(this);
            }



            return new ScreenBundle(root, controller);
        } catch (IOException e) {
            System.err.println("Error loading screen: " + screen.getPath());
            e.printStackTrace();
            return null;
        }
    }

    private void animateAndSetScreen(Parent newRoot) {
        Parent currentRoot = scene.getRoot();

        if (currentRoot == null || currentRoot.getChildrenUnmodifiable().isEmpty()) {
            scene.setRoot(newRoot);
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            scene.setRoot(newRoot);
            newRoot.setOpacity(0.0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public Stage getStage() {
        return stage;
    }
    public Scene getScene() {
        return scene;
    }


}
