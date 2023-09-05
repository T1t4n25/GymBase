package com.t1t4n.gymbase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class payController implements Initializable {
    @FXML
    TextField subValueField;
    @FXML
    Label subName;
    @FXML
    DatePicker subStartDate;
    @FXML
    ChoiceBox<String> subType;
    @FXML
    TextField expField;
    @FXML
    TextField expAmountField;
    @FXML
    TextArea comments;
    @FXML
    DatePicker expDate;
    @FXML
    TableView<Member> membersTable;
    @FXML
    TableColumn<Member, String> nameCol;
    @FXML
    TableColumn<Member, String> stateCol;
    @FXML
    TableColumn<Member, String> typeCol;
    @FXML
    TableColumn<Member, Double> valueCol;

    @FXML
    TableView<Expenses> expensesTable;
    @FXML
    TableColumn<Expenses, String> expNameCol;
    @FXML
    TableColumn<Expenses, String> commentCol;
    @FXML
    TableColumn<Expenses, Date> dateCol;
    @FXML
    TableColumn<Expenses, Double> expValueCol;

    ResultSet resultSet;
    int editId;
    private final String[] typeChoices = {"كمال اجسام","تخسيس","برايفت", "حصة"};

    public payController() {
        subType.getItems().addAll(typeChoices);
        expDate.setValue(LocalDate.now());
        //Members Table
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("subState"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("subValue"));
        //Expenses Table
        expNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        expValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @FXML
    private void savePayData() throws SQLException {
        resultSet =DBConnection.statement.executeQuery("SELECT * FROM `members_data` WHERE `subState` LIKE");

    }
    @FXML
    private void saveExpenses() throws SQLException {
        resultSet =DBConnection.statement.executeQuery("SELECT * FROM `expenses_data`");
        resultSet.moveToInsertRow();
        resultSet.updateString(1, expField.getText());
        resultSet.updateDouble(2, Double.parseDouble(expAmountField.getText()));
        resultSet.updateDate(3, java.sql.Date.valueOf(expDate.getValue()));
        resultSet.updateString(4, expField.getText());
    }
    @FXML
    private void edit() throws SQLException {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            subName.setText(selectedMember.getName());
            subType.setValue(selectedMember.getSubType());
            if (Objects.equals(selectedMember.getSubState(), "نشط"))
                subStartDate.setValue(new java.sql.Date((selectedMember.getJoinDate()).getTime()).toLocalDate());
            else
                subStartDate.setValue(LocalDate.now());
            editId = selectedMember.getId();
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
