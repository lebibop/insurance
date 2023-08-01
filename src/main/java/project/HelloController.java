package project;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private TableView<insurance> table = new TableView<>();

    ObservableList<insurance> List = FXCollections.observableArrayList();
    insuranceService insuranceService = new insuranceService();

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
        newWindowController.getNewWindow();
        if(UpdateStatus.isIsInsuranceAdded()) {
            refreshScreen(event);
            UpdateStatus.setIsInsuranceAdded(false);
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
            Alert IOAlert = new Alert(Alert.AlertType.ERROR, myEx.getMessage(), ButtonType.OK);
            IOAlert.setContentText(myEx.getMessage());
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        Callback<TableColumn<insurance, LocalDate>, TableCell<insurance, LocalDate>> cellFactory = column -> new TableCell<insurance, LocalDate>() {
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

        Callback<TableColumn<insurance, String>, TableCell<insurance, String>> cellFactoryString = column -> new TableCell<insurance, String>() {
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

        Callback<TableColumn<insurance, Integer>, TableCell<insurance, Integer>> cellFactoryInt = column -> new TableCell<insurance, Integer>() {
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

//        Callback<TableColumn<insurance, LocalDate>, TableCell<insurance, LocalDate>> cellFactoryDate2 = column -> new TableCell<insurance, LocalDate>() {
//            @Override
//            protected void updateItem(LocalDate item, boolean empty) {
//                super.updateItem(item, empty);
//                insurance insurance = getTableRow().getItem();
//                if ((empty || item == null) && insurance == null) {
//                    setText(null);
//                    setStyle(""); // Сбрасывает стиль ячейки
//                } else {
//                    setText("Не было");
//                    setTextFill(Color.WHITE);
//                    setAlignment(Pos.CENTER);
//                    if (insurance != null && insurance.getPayments_number() == 1) {
//                        setText(null);
//                        setStyle("-fx-background-color: #4d5660"); // Задает черный фон и белый текст для ячейки
//                    } else {
//                        setStyle("-fx-background-color: #f58871"); // Сбрасывает стиль ячейки, если она не должна быть закрашена
//                    }
//                }
//            }
//        };

        conclusion_date.setCellFactory(cellFactory);
        begin_date.setCellFactory(cellFactory);
        end_date.setCellFactory(cellFactory);
        company.setCellFactory(cellFactoryString);
        type.setCellFactory(cellFactoryString);
        fio.setCellFactory(cellFactoryString);
        contract_number.setCellFactory(cellFactoryString);
//        vin.setCellFactory(cellFactoryString);
        percentage.setCellFactory(cellFactoryInt);
        payments_number.setCellFactory(cellFactoryInt);



        signature_date1.setCellFactory(column -> new TableCell<insurance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);

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

        payment_date1.setCellFactory(column -> new TableCell<insurance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);

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


        signature_date2.setCellFactory(column -> new TableCell<insurance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);

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

        payment_date2.setCellFactory(column -> new TableCell<insurance, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                insurance insurance = getTableRow().getItem();
                if (insurance == null) {
                    setText(null);
                    setStyle(""); // Сбрасывает стиль ячейки
                } else {

                    setAlignment(Pos.CENTER);

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






        kv2.setCellFactory(column -> new TableCell<insurance, Integer>() {
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

        cost.setCellFactory(column -> new TableCell<insurance, Integer>() {
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

        kv1.setCellFactory(column -> new TableCell<insurance, Integer>() {
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



//
//        signature_date2.setCellFactory(column -> new TableCell<insurance, LocalDate>() {
//            @Override
//            protected void updateItem(LocalDate signature_date1, boolean empty) {
//                super.updateItem(signature_date1, empty);
//                if (empty || signature_date2 == null) {
//                    setText(null);
//                    setStyle(""); // Сбрасывает стиль ячейки
//                } else {
//                    setText("Не было");
//                    setTextFill(Color.WHITE);
//                    setAlignment(Pos.CENTER);
//                    insurance insurance = getTableRow().getItem();
//                    if (insurance != null && insurance.getPayments_number() == 1) {
//                        setText(null);
//                        setStyle("-fx-background-color: #4d5660"); // Задает черный фон и белый текст для ячейки
//                    } else {
//                        setStyle("-fx-background-color: #f58871"); // Сбрасывает стиль ячейки, если она не должна быть закрашена
//                    }
//                }
//            }
//        });

//        company.setCellFactory(column -> {
//            return new TableCell<insurance, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    if (!empty && item != null) {
//                        switch (item) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    setText(item);
//                }
//            };
//        });
//
//        type.setCellFactory(column -> {
//            return new TableCell<insurance, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    setText(item);
//                }
//            };
//        });
//
//        fio.setCellFactory(column -> {
//            return new TableCell<insurance, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    setText(item);
//                }
//            };
//        });
//
//        contract_number.setCellFactory(column -> {
//            return new TableCell<insurance, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    setText(item);
//                }
//            };
//        });
//
//        vin.setCellFactory(column -> {
//            return new TableCell<insurance, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    setText(item);
//                }
//            };
//        });

//        begin_date.setCellFactory(column -> {
//            return new TableCell<insurance, LocalDate>() {
//                @Override
//                protected void updateItem(LocalDate item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    if (item!=null) {
//                        setText(item.format(dateFormatter));
//                        setAlignment(Pos.CENTER);
//                    }
//                    else
//                        setText(null);
//
//                }
//            };
//        });
//
//        end_date.setCellFactory(column -> {
//            return new TableCell<insurance, LocalDate>() {
//                @Override
//                protected void updateItem(LocalDate item, boolean empty) {
//                    super.updateItem(item, empty);
//                    setStyle("");
//                    insurance ins = getTableRow().getItem();
//                    if (!empty && item != null && ins!= null) {
//                        switch (ins.getCompany()) {
//                            case "РЕНЕССАНС" -> setStyle("-fx-background-color: #d885d9;");
//                            case "ВСК" -> setStyle("-fx-background-color: #aedef0;");
//                            case "ИНГОССТРАХ" -> setStyle("-fx-background-color: #7bb0db;");
//                            case "РОСГОССТРАХ" -> setStyle("-fx-background-color: #f4abb4;");
//                            case "СБЕР" -> setStyle("-fx-background-color: #9ead7c;");
//                            case "ЮГОРИЯ" -> setStyle("-fx-background-color: #dddce9;");
//                            case "СОВКОМ" -> setStyle("-fx-background-color: #e4dfd1;");
//                            case "ЗЕТТА" -> setStyle("-fx-background-color: #cdda61;");
//                            case "ГЕЛЛИОС" -> setStyle("-fx-background-color: #ffcc85;");
//                            case "АЛЬФА" -> setStyle("-fx-background-color: #ff8682;");
//                            default ->
//                                    setStyle(null); // Сбросить стиль строки таблицы, если значение не совпадает с предопределенными значениями
//                        }
//                    } else {
//                        setStyle(null); // Сбросить стиль строки таблицы, если ячейка пуста или имеет значение null
//                    }
//                    // Выводим значение ячейки
//                    if (item!=null){
//                        setText(item.format(dateFormatter));
//                        setAlignment(Pos.CENTER);
//                    }
//                    else
//                        setText(null);
//
//                }
//            };
//        });

//        percentage.setCellFactory(column -> {
//            return new TableCell<insurance, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    if (empty || item == null) {
//                        setText(null);
//                    } else {
//                        setText(String.valueOf(item));
//                        setAlignment(Pos.CENTER); // Установить выравнивание текста по центру
//                        setStyle("-fx-font-weight: bold");
//                    }
//                }
//            };
//        });
//        payments_number.setCellFactory(column -> {
//            return new TableCell<insurance, Integer>() {
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    if (empty || item == null) {
//                        setText(null);
//                    } else {
//                        setText(String.valueOf(item));
//                        setAlignment(Pos.CENTER); // Установить выравнивание текста по центру
//                    }
//                }
//            };
//        });


//        conclusion_date.setCellFactory(new LocalDateCellFactory());
//        company.setCellFactory(ChoiceBoxTableCell.forTableColumn("РЕСО", "РЕНЕССАНС", "ВСК", "ИНГОССТРАХ",
//                "РОСГОССТРАХ", "СБЕР", "ЮГОРИЯ", "СОВКОМ", "ЗЕТТА", "ГЕЛЛИОС", "АЛЬФА"));
//        type.setCellFactory(ChoiceBoxTableCell.forTableColumn("ОСАГО", "КАСКО", "ИП-КВ", "ИП-Ж",
//                "ИП-ТИТ", "ИП-КОМ", "НС", "ЗК", "КВ-ДОМ", "МИГРАНТ", "ВЗР"));
//        begin_date.setCellFactory(new LocalDateCellFactory());
//        signature_date1.setCellFactory(new LocalDateCellFactory());
//        payment_date1.setCellFactory(new LocalDateCellFactory());
//        fio.setCellFactory(TextFieldTableCell.forTableColumn());
//        contract_number.setCellFactory(TextFieldTableCell.forTableColumn());
//        vin.setCellFactory(TextFieldTableCell.forTableColumn());
//        cost.setCellFactory(TextFieldTableCell.forTableColumn(new CustomIntegerStringConverter()));
//        percentage.setCellFactory(ChoiceBoxTableCell.forTableColumn(0,10,20,30));
//        payments_number.setCellFactory(ChoiceBoxTableCell.forTableColumn(1, 2));
    }
}