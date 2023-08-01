package project;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;


public class LocalDateCellFactory implements Callback<TableColumn<insurance, LocalDate>, TableCell<insurance, LocalDate>> {
    insuranceService workerService = new insuranceService();
    @Override
    public TableCell<insurance, LocalDate> call(TableColumn<insurance, LocalDate> col) {
        return new TableCell<insurance, LocalDate>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.editableProperty().set(false);
                datePicker.setOnAction((e) -> {
                    commitEdit(datePicker.getValue());
                });
                this.setGraphic(datePicker);
            }

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    insurance insurance = getTableRow().getItem();
                    datePicker.setValue(item);
                    setGraphic(datePicker);
                }
            }

            @Override
            public void commitEdit(LocalDate newValue) {
                super.commitEdit(newValue);
                insurance data = getTableView().getColumns().get(0).getTableView().getItems().get(getIndex());
                data.setSignature_date1(Date.valueOf(newValue).toLocalDate());
//                System.out.println(data.getDate_bd());
                workerService.updateinsurance(data);
            }

            @Override
            public void startEdit() {
                super.startEdit();
                LocalDate value = getItem();
                if (value != null) {
                    datePicker.setValue(value);
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
            }
        };
    }
}
