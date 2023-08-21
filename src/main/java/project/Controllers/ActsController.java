package project.Controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import project.Helpers.UpdateStatus;
import project.Model.insurance;
import project.Helpers.insuranceService;

import java.net.URL;
import java.util.ResourceBundle;

public class ActsController implements Initializable  {
    private insurance editedObject;
    @FXML
    private DatePicker payment_date1;
    @FXML
    private DatePicker payment_date2;
    @FXML
    private DatePicker signature_date1;
    @FXML
    private DatePicker signature_date2;

    public void setEditedObject(insurance editedObject) {
        this.editedObject = editedObject;

        payment_date1.setValue(editedObject.getPayment_date1());
        payment_date2.setValue(editedObject.getPayment_date2());
        signature_date1.setValue(editedObject.getSignature_date1());
        signature_date2.setValue(editedObject.getSignature_date2());
    }


    /**
     * Сохраняет нового клиента в базу данных.
     * Если данные прошли валидацию и клиент успешно сохранен, закрывает окно.
     * @param event событие, вызвавшее метод
     */
    @FXML
    private void saveNewVetToDb(ActionEvent event){
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


        if (!payment_date2.getEditor().getText().isEmpty())
            vet.setPayment_date2(payment_date2.getValue());
        else
            vet.setPayment_date2(null);

        if (!signature_date2.getEditor().getText().isEmpty())
            vet.setSignature_date2(signature_date2.getValue());
        else
            vet.setSignature_date2(null);

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



