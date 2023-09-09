package com.t1t4n.gymbase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    TextField monthsField;
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
    TableColumn<Member, Integer> idCol;
    @FXML
    TableColumn<Member, String> nameCol;
    @FXML
    TableColumn<Member, String> stateCol;
    @FXML
    TableColumn<Member, String> typeCol;
    @FXML
    TableColumn<Member, Double> valueCol;
    @FXML
    TableColumn<Member, Date> lastPayDateCol;
    @FXML
    TableColumn<Member, Date> deadLineCol;

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

    ObservableList<Member> membersData;
    ObservableList<Expenses> expData;
    ResultSet resultSet;
    int editId;
    private final String[] typeChoices = {"كمال اجسام","تخسيس","برايفت", "حصة"};

    public payController() throws SQLException {

        subType = new ChoiceBox<>();
        expDate = new DatePicker();
        idCol = new TableColumn<>();
        nameCol = new TableColumn<>();
        monthsField = new TextField();
        stateCol = new TableColumn<>();
        typeCol = new TableColumn<>();
        valueCol = new TableColumn<>();
        lastPayDateCol = new TableColumn<>();
        deadLineCol = new TableColumn<>();
        expNameCol = new TableColumn<>();
        commentCol = new TableColumn<>();
        dateCol = new TableColumn<>();
        expValueCol = new TableColumn<>();
        membersTable = new TableView<>();
        expensesTable = new TableView<>();

    }

    @FXML
    private void memberTableFill() throws SQLException {
        resultSet = DBConnection.statement.executeQuery("SELECT * FROM `members_data`");
        membersData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String status = resultSet.getString(4);
            String type = resultSet.getString(5);
            int value = resultSet.getInt(6);
            Date date = resultSet.getDate(7);
            Date lastPay = resultSet.getDate(9);
            Date deadline = resultSet.getDate(8);

            membersData.add(new Member(id, name, status, type, value, date, lastPay, deadline));

        }
        membersTable.setItems(membersData);
    }
    @FXML
    private void expensesTableFill() throws SQLException {
        resultSet = DBConnection.statement.executeQuery("SELECT * FROM `expenses_data`");
        expData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            String name = resultSet.getString(2);
            double value = resultSet.getDouble(3);
            Date date = resultSet.getDate(4);
            String comment = resultSet.getString(5);
            expData.add(new Expenses(name, comment, date, value));
            expensesTable.setItems(expData);
        }
    }
    @FXML
    private void savePayData() throws SQLException {
        if (!subName.getText().equals("-")) {
            resultSet = DBConnection.statement.executeQuery("SELECT * FROM `members_data`");
            int months = Integer.parseInt(monthsField.getText());
            while (resultSet.next()) {
                if (resultSet.getInt(1) == editId) {
                    resultSet.updateDate("lastPayDate", java.sql.Date.valueOf(subStartDate.getValue()));
                    resultSet.updateDate("deadlineDate", java.sql.Date.valueOf(subStartDate.getValue().plusMonths(months)));
                    resultSet.updateString("subType", subType.getValue());
                    resultSet.updateDouble("subValue", Double.parseDouble(subValueField.getText()));
                    resultSet.updateString("subState", "نشط");
                    resultSet.updateRow();
                }
            }
            if (Integer.parseInt(subValueField.getText()) > 0) {
                resultSet = DBConnection.statement.executeQuery("SELECT * FROM `money_in_data`");
                resultSet.moveToInsertRow();
                resultSet.updateString(2, subName.getText());
                resultSet.updateDate(3, java.sql.Date.valueOf(subStartDate.getValue()));
                resultSet.updateDouble(4, months * Double.parseDouble(subValueField.getText()));
                resultSet.insertRow();
            }
            clearMemberFields();
        }

        memberTableFill();
    }
    @FXML
    private void saveExpenses() throws SQLException {
        if (Double.parseDouble(expAmountField.getText()) > 0.0) {
            resultSet = DBConnection.statement.executeQuery("SELECT * FROM `expenses_data`");
            resultSet.moveToInsertRow();
            resultSet.updateString(2, expField.getText());
            resultSet.updateDouble(3, Double.parseDouble(expAmountField.getText()));
            resultSet.updateDate(4, java.sql.Date.valueOf(expDate.getValue()));
            resultSet.updateString(5, comments.getText());
            resultSet.insertRow();
            clearExpFields();
        }
        expensesTableFill();
    }
    @FXML
    private void edit() {
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            subName.setText(selectedMember.getName());
            subType.setValue(selectedMember.getSubType());
            if (Objects.equals(selectedMember.getSubState(), "نشط") && (selectedMember.getLastPayDate() != null))
                subStartDate.setValue(new java.sql.Date((selectedMember.getDeadlineDate()).getTime()).toLocalDate());
            else if (Objects.equals(selectedMember.getSubState(), "غير نشط"))
                subStartDate.setValue(LocalDate.now());
            else
                subStartDate.setValue(new java.sql.Date((selectedMember.getJoinDate()).getTime()).toLocalDate());
            editId = selectedMember.getId();
        }

    }

    private void clearExpFields(){
        expField.setText(null);
        expAmountField.setText(null);
        comments.setText(null);
    }
    @FXML
    private void clearRefresh() throws SQLException {
        clearMemberFields();
        memberTableFill();
        expensesTableFill();
    }

    private void clearMemberFields(){
        subValueField.setText(null);
        monthsField.setText("1");
        subName.setText("-");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subType.getItems().addAll(typeChoices);
        expDate.setValue(LocalDate.now());
        monthsField.setText("1");
        //Members Table
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("subState"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("subValue"));
        lastPayDateCol.setCellValueFactory(new PropertyValueFactory<>("lastPayDate"));
        deadLineCol.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));
        //Expenses Table
        expNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        expValueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        try {
            memberTableFill();
            expensesTableFill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
