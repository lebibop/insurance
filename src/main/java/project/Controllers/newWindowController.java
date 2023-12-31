package project.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Контроллер переключения.
 * @author lebibop
 */
public class newWindowController {
    /**
     * Открывает новое окно для добавления нового работника.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    public static void getNewWindow(String s) throws IOException {
        getPopUpWindow(s);
    }

    /**
     * Открывает новое всплывающее окно с заданным FXML-файлом.
     * @param path Путь к FXML-файлу.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(newWindowController.class.getResource(path));
        AnchorPane main = fxmlLoader.load();
        main.setStyle("-fx-background-color: #c6c6c6;");

        // Найдите кнопки внутри AnchorPane
        Button button1 = (Button) main.lookup("#save");
        button1.setStyle("-fx-background-color: #404040;");
        button1.setTextFill(Paint.valueOf("#f1f1f1"));

        main.lookup("#company").setStyle("-fx-background-color: #f1f1f1;");
        main.lookup("#type").setStyle("-fx-background-color: #f1f1f1;");
        main.lookup("#year").setStyle("-fx-background-color: #f1f1f1;");
        main.lookup("#percentage").setStyle("-fx-background-color: #f1f1f1;");
        main.lookup("#payments_number").setStyle("-fx-background-color: #f1f1f1;");


        stage.setScene(new Scene(main));
        stage.setTitle("Pet Clinic CRM");
        stage.getScene();
        stage.showAndWait();
    }
}
