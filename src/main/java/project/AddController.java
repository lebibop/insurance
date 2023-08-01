package project;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable  {
    @FXML
    private DatePicker conclusion_date;
    @FXML
    private ChoiceBox<String> company;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private DatePicker begin_date;
    @FXML
    private DatePicker end_date;
    @FXML
    private TextField fio;
    @FXML
    private TextField contract_number;
    @FXML
    private TextField vin;
    @FXML
    private ChoiceBox<Integer> percentage;
    @FXML
    private TextField cost;
    @FXML
    private ChoiceBox<Integer> payments_number;

    @FXML
    private ChoiceBox<String> year;
    @FXML
    private TextField kolvo;




    /**
     * Сохраняет нового клиента в базу данных.
     * Если данные прошли валидацию и клиент успешно сохранен, закрывает окно.
     * @param event событие, вызвавшее метод
     */
    @FXML
    private void saveNewVetToDb(ActionEvent event){
        if (validateInputs()) {
            insurance vet = createVetFromInput();
            boolean isSaved = new insuranceService().createinsurance(vet);
            if (isSaved) {
                UpdateStatus.setIsInsuranceAdded(true);
                delayWindowClose(event);
            }
        }
    }

    /**
     * Проверяет, является ли строка числом.
     * @param str строка для проверки
     * @return true, если строка является числом, иначе false
     */
    public static boolean isNumeric(String str) {
        try {
            return Integer.parseInt(str) >= 0;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Проверяет корректность введенных данных.
     * @return true, если данные корректны, иначе false
     */
    private boolean validateInputs() {
        Alert IOAlert = new Alert(Alert.AlertType.ERROR, "Input Error", ButtonType.OK);
        if (fio.getText().equals("") || contract_number.getText().equals("") || vin.getText().equals("") || cost.getText().equals("") || company.getValue().equals("Компания") || type.getValue().equals("Тип страховки") || conclusion_date.getValue() == null || begin_date.getValue() == null) {
            IOAlert.setContentText("You must fill empty field(-s) to continue");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.OK)
            {
                IOAlert.close();
            }
            return false;
        }

//        if (!isNumeric(exp.getText())){
//            IOAlert.setContentText("Incorrect toe input for EXPERIENCE - you must input a number (>=0)");
//            IOAlert.showAndWait();
//            if(IOAlert.getResult() == ButtonType.OK)
//            {
//                IOAlert.close();
//            }
//            return false;
//        }


        return true;
    }

//    public static String convertToFullName(String name) {
//        String[] parts = name.toLowerCase().split(" ");
//        StringBuilder fullName = new StringBuilder();
//        String firstLetter = parts[0].substring(0, 1).toUpperCase();
//        String restOfString = parts[0].substring(1).toLowerCase();
//        fullName.append(firstLetter).append(restOfString).append(" ");
//        parts[1] = parts[1].toUpperCase();
//        fullName.append(parts[1].charAt(0));
//        fullName.append(".");
//        fullName.append(parts[1].charAt(1));
//        fullName.append(".");
//
//        return fullName.toString().trim();
//    }

    /**
     * Создает объект работника на основе введенных данных.
     * @return объект работника
     */
    private insurance createVetFromInput() {
        insurance vet = new insurance();
        vet.setConclusion_date(conclusion_date.getValue());
        vet.setCompany(company.getValue());
        vet.setType(type.getValue());
        vet.setBegin_date(begin_date.getValue());
        if (!year.getValue().equals("Годы/Месяцы/Дни")){
            if (year.getValue().equals("Годы"))
                vet.setEnd_date(begin_date.getValue().plusYears(Integer.parseInt(kolvo.getText())).minusDays(1));
            if (year.getValue().equals("Месяцы"))
                vet.setEnd_date(begin_date.getValue().plusMonths(Integer.parseInt(kolvo.getText())).minusDays(1));
            if (year.getValue().equals("Дни"))
                vet.setEnd_date(begin_date.getValue().plusDays(Integer.parseInt(kolvo.getText())).minusDays(1));
        }
        else vet.setEnd_date(end_date.getValue());
        vet.setFio(fio.getText());
        vet.setContract_number(contract_number.getText().toUpperCase());
        vet.setVin(vin.getText().toUpperCase());
        vet.setCost(Integer.parseInt(cost.getText()));
        vet.setPercentage(percentage.getValue());
        vet.setPayments_number(payments_number.getValue());
        if (vet.getPayments_number() == 1) {
            double xx = (double) vet.getCost() * ((double) vet.getPercentage() / 100);
            vet.setKv1((int) xx);
        }
        if (vet.getPayments_number() == 2) {
            double xx = (double) vet.getCost()*((double) vet.getPercentage() / 200);
            vet.setKv1((int) xx);
            vet.setKv2((int) xx);
        }
        return vet;
    }

    /**
     * Задерживает закрытие окна на 1 секунду.
     * @param event событие, вызвавшее метод
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Закрывает текущее окно.
     * @param event событие, вызвавшее метод
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        SceneController.close(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        company.getItems().addAll("РЕСО", "РЕНЕССАНС", "ВСК", "ИНГОССТРАХ",
                "РОСГОССТРАХ", "СБЕР", "ЮГОРИЯ", "СОВКОМ", "ЗЕТТА", "ГЕЛЛИОС", "АЛЬФА");
        company.setValue("Компания");
        type.getItems().addAll("ОСАГО", "КАСКО", "ИПОТЕКА-КВ", "ИПОТЕКА-Ж",
                "ИПОТЕКА-ТИТ", "ИПОТЕКА-КОМ", "НС", "ЗЕЛ. КАРТА", "КВАРТИРА-ДОМ", "МИГРАНТ", "ВЗР");
        type.setValue("Тип страховки");
        percentage.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
                23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50);
        payments_number.getItems().addAll(1, 2);
        year.getItems().addAll("Годы", "Месяцы", "Дни");
        year.setValue("Годы/Месяцы/Дни");
    }
}


