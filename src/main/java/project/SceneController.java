package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * Контроллер для переключения таблиц.
 * @author lebibop
 */
public class SceneController {

    private static final Logger logger = LoggerFactory.getLogger("Scene Logger");

    /**
     * Метод для перехода на сцену со списком работников.
     * @param event Событие, вызвавшее переход.
     * @throws IOException Если произошла ошибка ввода-вывода при загрузке сцены.
     */
    public static void getScene(ActionEvent event) throws IOException {

        logger.info("Transition to list scene");

        changeScreen(event, "hello-view.fxml");
    }



    /**
     * Метод для смены сцены на заданную.
     * @param event Событие, вызвавшее смену сцены.
     * @param path Путь к файлу FXML новой сцены.
     * @throws IOException Если произошла ошибка ввода-вывода при загрузке сцены.
     */
    private static void changeScreen(ActionEvent event, String path) throws IOException
    {
        logger.info("Changing scene");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(SceneController.class.getResource(path)));

        Parent main = loader.load();
        Scene visitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(visitScene);
        window.show();
    }

    /**
     * Метод для закрытия текущего окна.
     * @param actionEvent Событие, вызвавшее закрытие окна.
     */
    public static void close(ActionEvent actionEvent) {

        logger.info("Closing window");

        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
