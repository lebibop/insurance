package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage MainStage) throws IOException {
        Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Scene MainScene = new Scene(MainParent);

        Button add = (Button) MainParent.lookup("#add");
        add.setStyle("-fx-background-color: #404040;");
        add.setTextFill(Paint.valueOf("#f1f1f1"));

        Button delete = (Button) MainParent.lookup("#delete");
        delete.setStyle("-fx-background-color: #404040;");
        delete.setTextFill(Paint.valueOf("#f1f1f1"));

        Button change = (Button) MainParent.lookup("#change");
        change.setStyle("-fx-background-color: #404040;");
        change.setTextFill(Paint.valueOf("#f1f1f1"));


        Button acts = (Button) MainParent.lookup("#acts");
        acts.setStyle("-fx-background-color: #404040;");
        acts.setTextFill(Paint.valueOf("#f1f1f1"));


        MainStage.setScene(MainScene);
        MainStage.setTitle("Hotel");
        MainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}