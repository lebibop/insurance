package project.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import project.Helpers.UpdateStatus;
import project.Helpers.insuranceService;
import project.Model.insurance;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public TableColumn<insurance, LocalDate> conclusion_date;
    @FXML
    public TableColumn<insurance, String> company;
    @FXML
    public TableColumn<insurance, String> type;
    @FXML
    public TableColumn<insurance, LocalDate> begin_date;
    @FXML
    public TableColumn<insurance, LocalDate> end_date;
    @FXML
    public TableColumn<insurance, String> fio;
    @FXML
    public TableColumn<insurance, String> contract_number;
    @FXML
    public TableColumn<insurance, String> vin;
    @FXML
    public TableColumn<insurance, Integer> cost;
    @FXML
    public TableColumn<insurance, Integer> percentage;
    @FXML
    public TableColumn<insurance, Integer> payments_number;
    @FXML
    public TableColumn<insurance, Integer> kv1;
    @FXML
    public TableColumn<insurance, Integer> kv2;
    @FXML
    public TableColumn<insurance, LocalDate> signature_date1;
    @FXML
    public TableColumn<insurance, LocalDate> signature_date2;
    @FXML
    public TableColumn<insurance, LocalDate> payment_date1;
    @FXML
    public TableColumn<insurance, LocalDate> payment_date2;
    @FXML
    private TextField search;
    @FXML
    private Text mode;
    @FXML
    private Text mode1;
    @FXML
    private TableView<insurance> table = new TableView<>();

    ObservableList<insurance> List = FXCollections.observableArrayList();
    project.Helpers.insuranceService insuranceService = new insuranceService();

    public static String convertWithCommas(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(number);
    }

    @FXML
    void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getScene(event);
    }
    @FXML
    private void add(ActionEvent event) throws IOException {
        newWindowController.getNewWindow("/add.fxml");
        if(UpdateStatus.isIsInsuranceAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsInsuranceAdded(false);
        }
    }

    @FXML
    private void edit(ActionEvent event) {
        try {
            int selectedID = table.getSelectionModel().getSelectedIndex();
            if (selectedID == -1) throw new MyException();
            insurance selectedItem = table.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));

                Parent root = loader.load();
                // получение контроллера для окна редактирования
                EditController editController = loader.getController();
                // передаем выбранный элемент в контроллер
                editController.setEditedObject(selectedItem);

                // создание нового окна
                Stage editStage = new Stage();
                editStage.setTitle("Редактирование");
                editStage.setScene(new Scene(root));
                // отображение нового окна как модального
                editStage.initModality(Modality.APPLICATION_MODAL);
                editStage.showAndWait();
                if (UpdateStatus.isIsInsuranceAdded()) {
                    refreshScreen(event);
                    UpdateStatus.setIsInsuranceAdded(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (MyException ex){
            Alert IOAlert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            IOAlert.setContentText("Вы не выбрали элемент");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.OK)
            {
                IOAlert.close();
            }
        }
    }

    @FXML
    private void acts(ActionEvent event) {
        try {
            int selectedID = table.getSelectionModel().getSelectedIndex();
            if (selectedID == -1) throw new MyException();
            insurance selectedItem = table.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader loader;
                Parent root;
                if (selectedItem.getPayments_number() == 2) {
                    loader = new FXMLLoader(getClass().getResource("/acts.fxml"));
                    root = loader.load();
                    // получение контроллера для окна редактирования
                    ActsController editController = loader.getController();
                    editController.setEditedObject(selectedItem);
                }
                else {
                    loader = new FXMLLoader(getClass().getResource("/acts2.fxml"));
                    root = loader.load();
                    // получение контроллера для окна редактирования
                    Acts2Controller editController = loader.getController();
                    editController.setEditedObject(selectedItem);
                }


                // создание нового окна
                Stage editStage = new Stage();
                editStage.setTitle("Рдактирование актов и выплат");
                editStage.setScene(new Scene(root));
                // отображение нового окна как модального
                editStage.initModality(Modality.APPLICATION_MODAL);
                editStage.showAndWait();
                if (UpdateStatus.isIsInsuranceAdded()) {
                    refreshScreen(event);
                    UpdateStatus.setIsInsuranceAdded(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (MyException ex){
            Alert IOAlert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            IOAlert.setContentText("Вы не выбрали элемент");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.OK)
            {
                IOAlert.close();
            }
        }
    }


    static class MyException extends Exception
    {
        public MyException()
        {
            super("Choose a row to delete");
        }
    }
    private void setObList() {
        List.clear();
        List.addAll(insuranceService.getinsurances());
        mode.setText("");
        mode1.setText("");
    }

    @FXML
    private void expiring() {
        List.clear();
        List.addAll(insuranceService.getinsurances_expiring());
        mode.setText("Список пролонгаций за 2 месяца");
        mode1.setText("Для возврата к основному списку нажми кнопку ОСН. СПИСОК");
    }

    @FXML
    private void signature() {
        List.clear();
        List.addAll(insuranceService.getinsurances_signature());
        mode.setText("Список актов, которые не были подписаны");
        mode1.setText("Для возврата к основному списку нажми кнопку ОСН. СПИСОК");
    }

    @FXML
    private void payment() {
        List.clear();
        List.addAll(insuranceService.getinsurances_payment());
        mode.setText("Список выплат, которые не были осуществлены");
        mode1.setText("Для возврата к основному списку нажми кнопку ОСН. СПИСОК");
    }

    @FXML
    private void all() {
        setObList();
    }



    private void remove_row(ActionEvent event) throws MyException, IOException {
        int selectedID = table.getSelectionModel().getSelectedIndex();
        if (selectedID == -1) throw new MyException();
        else {
            Alert IOAlert = new Alert(Alert.AlertType.CONFIRMATION, "111", ButtonType.OK, ButtonType.CANCEL);
            IOAlert.setContentText("Точно хотите удалить?");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.CANCEL)
            {
                IOAlert.close();
            }
            if(IOAlert.getResult() == ButtonType.OK)
            {
                ObservableList<insurance> selectedRows = table.getSelectionModel().getSelectedItems();
                for (insurance insurance : selectedRows) {
                    insuranceService.deleteinsurance(insurance);
                }
                refreshScreen(event);
                IOAlert.close();
            }
        }
    }
    @FXML
    private void delete(ActionEvent event)
    {
        try {
            remove_row(event);
        }

        catch (MyException | IOException myEx){
            Alert IOAlert = new Alert(Alert.AlertType.ERROR, "Вы не выбрали ни одного элемента", ButtonType.OK);
            IOAlert.setContentText("Вы не выбрали ни одного элемента");
            IOAlert.showAndWait();
            if(IOAlert.getResult() == ButtonType.OK)
            {
                IOAlert.close();
            }
        }
    }

    private SortedList<insurance> getSortedList() {
        SortedList<insurance> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        return sortedList;
    }

    private FilteredList<insurance> getFilteredList() {
        FilteredList<insurance> filteredList = new FilteredList<>(List, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(insurance -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (insurance.getCompany().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (insurance.getFio().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (date_converter(insurance.getConclusion_date().toString()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (date_converter(insurance.getBegin_date().toString()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (date_converter(insurance.getEnd_date().toString()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (insurance.getContract_number().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (insurance.getVin().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (insurance.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return String.valueOf(insurance.getCost()).contains(lowerCaseFilter);
                }));
        return filteredList;
    }

    private String date_converter(String temp){
        String[] temp2 = temp.split("-");
        return temp2[2] + '.' + temp2[1] + '.' + temp2[0];
    }

    @FXML
    private void search_money() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/vyruchka.fxml")));

        // Создаем новую сцену с корневым узлом
        Scene scene = new Scene(root);

        // Создаем новое всплывающее окно
        Stage popupStage = new Stage();

        // Устанавливаем сцену для всплывающего окна
        popupStage.setScene(scene);

        // Показываем всплывающее окно
        popupStage.show();
    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //table.setStyle("-fx-table-cell-border-color: black; -fx-table-cell-border-width: 0 0 1 0;");

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        setObList();

        conclusion_date.setCellValueFactory(new PropertyValueFactory<>("conclusion_date"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        begin_date.setCellValueFactory(new PropertyValueFactory<>("begin_date"));
        end_date.setCellValueFactory(new PropertyValueFactory<>("end_date"));
        fio.setCellValueFactory(new PropertyValueFactory<>("fio"));
        contract_number.setCellValueFactory(new PropertyValueFactory<>("contract_number"));
        vin.setCellValueFactory(new PropertyValueFactory<>("vin"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        payments_number.setCellValueFactory(new PropertyValueFactory<>("payments_number"));
        kv1.setCellValueFactory(new PropertyValueFactory<>("kv1"));
        kv2.setCellValueFactory(new PropertyValueFactory<>("kv2"));
        signature_date1.setCellValueFactory(new PropertyValueFactory<>("signature_date1"));
        signature_date2.setCellValueFactory(new PropertyValueFactory<>("signature_date2"));
        payment_date1.setCellValueFactory(new PropertyValueFactory<>("payment_date1"));
        payment_date2.setCellValueFactory(new PropertyValueFactory<>("payment_date2"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy"); // Здесь указывается новый формат вывода даты

        Callback<TableColumn<insurance, LocalDate>, TableCell<insurance, LocalDate>> cellFactory = column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setStyle("");
                insurance ins = getTableRow().getItem();
                if (!empty && item != null && ins!= null) {
                    switch (ins.getCompany()) {
                        case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
                        case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
                        case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
                        case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
                        case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
                        case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
                        case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
                        case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
                        case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
                        case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
                        default ->
                                setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
                    }
                } else {
                    setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
                }
                // Выводим значение ячейки
                if (item!=null) {
                    setText(item.format(dateFormatter));
                    setAlignment(Pos.CENTER);
                }
                else
                    setText(null);

            }
        };

        Callback<TableColumn<insurance, String>, TableCell<insurance, String>> cellFactoryString = column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setStyle("");
                insurance ins = getTableRow().getItem();
                if (!empty && item != null && ins!= null) {
                    switch (ins.getCompany()) {
                        case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
                        case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
                        case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
                        case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
                        case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
                        case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
                        case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
                        case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
                        case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
                        case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
                        default ->
                                setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
                    }
                } else {
                    setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
                }
                // Выводим значение ячейки
                if (item!=null) {
                    setText(item);
                    setAlignment(Pos.CENTER);
                }
                else
                    setText(null);

            }
        };

        Callback<TableColumn<insurance, Integer>, TableCell<insurance, Integer>> cellFactoryInt = column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item));
                    setAlignment(Pos.CENTER); // Установить выравнивание текста по центру
                    setStyle("-fx-font-weight: bold");
                }
            }
        };


        conclusion_date.setCellFactory(cellFactory);
        begin_date.setCellFactory(cellFactory);
        end_date.setCellFactory(cellFactory);
        company.setCellFactory(cellFactoryString);
        type.setCellFactory(cellFactoryString);
        fio.setCellFactory(cellFactoryString);
        contract_number.setCellFactory(cellFactoryString);
        percentage.setCellFactory(cellFactoryInt);
        payments_number.setCellFactory(cellFactoryInt);



        signature_date1.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);
                    setTextFill(Color.BLACK);

                    if (insurance.getSignature_date1() == null) {
                        setText("Не было");
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: #f58871"); // Задает черный фон и белый текст для ячейки
                    } else {
                        setText((insurance.getSignature_date1()).format(dateFormatter));
                    }
                }
            }
        });

        payment_date1.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);
                    setTextFill(Color.BLACK);

                    if (insurance.getPayment_date1() == null) {
                        setText("Не было");
                        setTextFill(Color.WHITE);
                        setStyle("-fx-background-color: #f58871"); // Задает черный фон и белый текст для ячейки
                    } else {
                        setText((insurance.getPayment_date1()).format(dateFormatter));
                    }
                }
            }
        });


        signature_date2.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);
                    setTextFill(Color.BLACK);

                    if (insurance.getPayments_number() == 1) {
                        setText(null);
                        setStyle("-fx-background-color: #4d5660"); // Задает черный фон и белый текст для ячейки
                    } else {
                        if (insurance.getSignature_date2() == null){
                            setText("Не было");
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: #f58871");
                        }
                        else
                            setText((insurance.getSignature_date2()).format(dateFormatter));
                    }
                }
            }
        });

        payment_date2.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);
                    setTextFill(Color.BLACK);

                    if (insurance.getPayments_number() == 1) {
                        setText(null);
                        setStyle("-fx-background-color: #4d5660"); // Задает черный фон и белый текст для ячейки
                    } else {
                        if (insurance.getPayment_date2() == null){
                            setText("Не было");
                            setTextFill(Color.WHITE);
                            setStyle("-fx-background-color: #f58871");
                        }
                        else
                            setText((insurance.getPayment_date2()).format(dateFormatter));
                    }
                }
            }
        });

        kv2.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer kv1, boolean empty) {
                super.updateItem(kv1, empty);
                if (empty || kv2 == null) {
                    setText("");
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {
                    insurance insurance = getTableRow().getItem();
                    if (insurance != null && insurance.getPayments_number() == 1) {
                        setText("");
                        setStyle("-fx-background-color: #4d5660"); // Задает черный фон и белый текст для ячейки
                    } else {
                        setStyle(""); // Сбрасывает стиль ячейки, если она не должна быть закрашена
                        if (kv1!=null) {
                            setText(convertWithCommas(kv1));
                            setStyle("-fx-text-fill: black; -fx-background-color: #FCF4A3;");
                        }
                        setAlignment(Pos.CENTER);
                    }
                }
            }
        });

        cost.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer cost, boolean empty) {
                super.updateItem(cost, empty);
                if (empty || cost == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(convertWithCommas(cost));
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-text-fill: black; -fx-background-color: #FCF4A3;");
                }
            }
        });

        vin.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String vin, boolean empty) {
                super.updateItem(vin, empty);
                if (empty || vin == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.format("%s (%s)-%s-%s-%s",
                            "+7",
                            vin.substring(0, 3),
                            vin.substring(3, 6),
                            vin.substring(6, 8),
                            vin.substring(8, 10)));
                    setAlignment(Pos.CENTER);
                }
            }
        });

        kv1.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer kv1, boolean empty) {
                super.updateItem(kv1, empty);
                if (empty || kv1 == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(convertWithCommas(kv1));
                    setAlignment(Pos.CENTER);
                    setStyle("-fx-text-fill: black; -fx-background-color: #FCF4A3;");
                }
            }
        });

        table.setEditable(true);
        table.setItems(getSortedList());

        table.setRowFactory(tv -> {
            TableRow<insurance> row = new TableRow<>();
            row.setStyle(""); // Здесь можно указать цвет, который вы хотите использовать

            row.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    row.setStyle("-fx-background-color: #d70e17;"); // Здесь можно указать цвет для выделенной строки
                }
                else row.setStyle("");
            });

            return row;
        });

        table.setOnKeyPressed(event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                // Получение выбранной ячейки
                var selectedCell = table.getSelectionModel().getSelectedCells().get(0);
                if (selectedCell.getTableColumn() == contract_number || selectedCell.getTableColumn() == fio || selectedCell.getTableColumn() == vin) {
                    // Получение значения из выбранной ячейки
                    String contractNumber = (String) selectedCell.getTableColumn().getCellData(selectedCell.getRow());

                    // Копирование значения в буфер обмена
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(contractNumber);
                    clipboard.setContent(content);
                }
            }
        });
    }
}