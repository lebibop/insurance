package project.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import project.Helpers.insuranceService;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static project.Controllers.HelloController.convertWithCommas;

public class VyruchkaController implements Initializable  {
    @FXML
    private DatePicker starting_date;
    @FXML
    private DatePicker ending_date;
    @FXML
    private TextField aaaa;

    project.Helpers.insuranceService insuranceService = new insuranceService();

    @FXML
    private void search_money(){
        try {
            LocalDate arr = starting_date.getValue();
            LocalDate dep = ending_date.getValue();

            if (starting_date.getValue() == null || ending_date.getValue() == null || ending_date.getValue().isBefore(starting_date.getValue()))
                throw new Exception();


            aaaa.setText(convertWithCommas(insuranceService.getinsurances_sum(arr, dep)));
            aaaa.setAlignment(Pos.CENTER);
            aaaa.setEditable(false);

        }
        catch (Exception e) {
            Alert IOAlert = new Alert(Alert.AlertType.ERROR, "Error!", ButtonType.OK);
            IOAlert.setContentText("Дата начала должно быть не больше, чем дата окончания!");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.OK) {
                IOAlert.close();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}



