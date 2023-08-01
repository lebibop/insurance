package project;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Acts2Controller implements Initializable  {
    private insurance editedObject;
    @FXML
    private DatePicker payment_date1;
    @FXML
    private DatePicker signature_date1;

    public void setEditedObject(insurance editedObject) {
        this.editedObject = editedObject;

        payment_date1.setValue(editedObject.getPayment_date1());
        signature_date1.setValue(editedObject.getSignature_date1());
    }


    /**
     * Сохраняет нового клиента в базу данных.
     * Если данные прошли валидацию и клиент успешно сохранен, закрывает окно.
     * @param event событие, вызвавшее метод
     */
    @FXML
    private void saveNewVetToDb(ActionEvent event){
        if (validateInputs()) {
            insurance vet = createVetFromInput();
            vet.setConclusion_date(editedObject.getConclusion_date());
            vet.setCompany(editedObject.getCompany());
            vet.setType(editedObject.getType());
            vet.setBegin_date(editedObject.getBegin_date());
            vet.setEnd_date(editedObject.getEnd_date());
            vet.setFio(editedObject.getFio());
            vet.setContract_number(editedObject.getContract_number());
            vet.setVin(editedObject.getVin());
            vet.setPercentage(editedObject.getPercentage());
            vet.setCost(editedObject.getCost());
            vet.setPayments_number(editedObject.getPayments_number());
            vet.setKv1(editedObject.getKv1());
            vet.setKv2(editedObject.getKv2());
            new insuranceService().deleteinsurance(editedObject);
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
            return Integer.parseInt(str) < 0;
        } catch(NumberFormatException e){
            return true;
        }
    }

    /**
     * Проверяет корректность введенных данных.
     * @return true, если данные корректны, иначе false
     */
    private boolean validateInputs() {
//        Alert IOAlert = new Alert(Alert.AlertType.ERROR, "Input Error", ButtonType.OK);
//        if (fio.getText().equals("") || contract_number.getText().equals("") || cost.getText().equals("")|| conclusion_date.getValue() == null || begin_date.getValue() == null || year.getValue().equals("год/месяц/день")) {
//            IOAlert.setContentText("Заполнены не все поля!");
//            IOAlert.showAndWait();
//            if(IOAlert.getResult() == ButtonType.OK)
//            {
//                IOAlert.close();
//            }
//            return false;
//        }
//
//        if (isNumeric(kolvo.getText())){
//            IOAlert.setContentText("Для поля КОЛИЧЕСТВО нужно ввести ЧИСЛО");
//            IOAlert.showAndWait();
//            if(IOAlert.getResult() == ButtonType.OK)
//            {
//                IOAlert.close();
//            }
//            return false;
//        }
//
//        if (isNumeric(cost.getText())){
//            IOAlert.setContentText("Для поля СТОИМОСТЬ нужно ввести ЧИСЛО");
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

        if (!payment_date1.getEditor().getText().isEmpty())
            vet.setPayment_date1(payment_date1.getValue());
        else
            vet.setPayment_date1(null);

        if (!signature_date1.getEditor().getText().isEmpty())
            vet.setSignature_date1(signature_date1.getValue());
        else
            vet.setSignature_date1(null);

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
    public void initialize(URL url, ResourceBundle rb) {

    }
}



