package org.example.clientofnetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.clientofnetwork.controller.WiringChangeable;

public class Main extends Application {

    @Override public void start(Stage stage) throws Exception {
        var fxml = new FXMLLoader(getClass().getResource("/app/client/ui/MainView.fxml"));
        stage.setScene(new Scene(fxml.load()));
        stage.setTitle("TodoNet – Connect");
        stage.show();

        WiringChangeable.Result result = WiringChangeable.boot();

    }
    public static void main(String[] args){ launch(args); }
}
