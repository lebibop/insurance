package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage MainStage) throws IOException {
        Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene MainScene = new Scene(MainParent);
        MainStage.setScene(MainScene);
        MainStage.setTitle("Hotel");
        MainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}