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
import java.util.ResourceBundle;

public class addMemberController implements Initializable{
    @FXML
    TextField nameField;
    @FXML
    TextField numberField;
    @FXML
    TextField searchNameField;
    @FXML
    TextField searchNumberField;
    @FXML
    ChoiceBox<String> subStateBox;
    @FXML
    ChoiceBox<String> subTypeBox;
    @FXML
    DatePicker joinDate;
    @FXML
    TableView<Member> membersTable;
    @FXML
    TableColumn<Member, Integer> idCol;
    @FXML
    TableColumn<Member, String> nameCol;
    @FXML
    TableColumn<Member, String> numberCol;
    @FXML
    TableColumn<Member, String> subStateCol;
    @FXML
    TableColumn<Member, String> subTypeCol;
    @FXML
    TableColumn<Member, Double> subValueCol;
    @FXML
    TableColumn<Member, Date> joinDateCol;
    @FXML
    TableColumn<Member, Date> deadlineDateCol;
    @FXML
    TableColumn<Member, Date> lastPayDateCol;

    ResultSet resultSet;
    private final String[] stateChoices = {"نشط", "غير نشط", "معلق"};
    private final String[] typeChoices = {"كمال اجسام", "تخسيس", "برايفت", "حصة"};
    ObservableList<Member> data;

    int id;
    double value;
    String name, number, status, type;
    Date date, deadline, lastPay;

    boolean editing = false;
    int editId;
    String allQuery = "SELECT * FROM `members_data`";
    public addMemberController() throws SQLException {
//        membersTable = new TableView<>();
//        refreshTable(allQuery);

    }

    public void refreshTable(String query) throws SQLException {
        resultSet =DBConnection.statement.executeQuery(query);
        data = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while(resultSet.next()){
            id = resultSet.getInt(1);
            name = resultSet.getString(2);
            number = resultSet.getString(3);
            status = resultSet.getString(4);
            type = resultSet.getString(5);
            value = resultSet.getDouble(6);
            date = resultSet.getDate(7);
            deadline = resultSet.getDate(8);
            lastPay = resultSet.getDate(9);

            data.add(new Member(id, name, status, type, value, deadline, date, number, lastPay));
            membersTable.setItems(data);
        }

    }
    @FXML
    private void saveMemberData() throws SQLException {
        resultSet =DBConnection.statement.executeQuery("SELECT * FROM `members_data`");
        if(editing) {
            resultSet.beforeFirst();
            while (resultSet.next()){
                if (editId == resultSet.getInt(1))
                    break;
            }
        }else {
            resultSet.moveToInsertRow();
        }
        resultSet.updateString("name", nameField.getText());
        resultSet.updateString("number", numberField.getText());
        resultSet.updateString("subState", subStateBox.getValue());
        resultSet.updateString("subType", subTypeBox.getValue());
        resultSet.updateDate("joinDate", java.sql.Date.valueOf(joinDate.getValue()));
        if (editing)
            resultSet.updateRow();
        else
            resultSet.insertRow();
        clearFields();
        refreshTable(allQuery);
    }

    @FXML
    private void search() throws SQLException {
        String nameText = searchNameField.getText();
        String numberText = searchNumberField.getText();
        if (!nameText.isEmpty() && numberText.isEmpty()) {
            String nameSearch = "SELECT * FROM `members_data` WHERE `name` LIKE '%" + nameText + "%'";
            refreshTable(nameSearch);
        } else if (nameText.isEmpty() && !numberText.isEmpty()) {
            String numberSearch = "SELECT * FROM `members_data` WHERE `number` LIKE '%" + numberText + "%'";
            refreshTable(numberSearch);
        } else if (!nameText.isEmpty() && !numberText.isEmpty()) {
            String bothSearch = "SELECT * FROM `members_data` WHERE `name` LIKE '%" + nameText + "%' AND `number` LIKE '%" + numberText + "%'";
            refreshTable(bothSearch);
        } else {
            refreshTable(allQuery);
        }
    }
    @FXML
    private void edit() throws SQLException {
        editing = true;
        Member selectedMember = membersTable.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            nameField.setText(selectedMember.getName());
            numberField.setText(selectedMember.getNumber());
            subStateBox.setValue(selectedMember.getSubState());
            subTypeBox.setValue(selectedMember.getSubType());
            joinDate.setValue(new java.sql.Date((selectedMember.getJoinDate()).getTime()).toLocalDate());
            editId = selectedMember.getId();
        }

    }
    @FXML
    private void clearFields(){
        nameField.setText("");
        numberField.setText("");
        joinDate.setValue(LocalDate.now());
        editing = false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subStateBox.getItems().addAll(stateChoices);
        subStateBox.setValue(stateChoices[0]);

        subTypeBox.getItems().addAll(typeChoices);
        subTypeBox.setValue(typeChoices[0]);
        joinDate.setValue(LocalDate.now());

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subStateCol.setCellValueFactory(new PropertyValueFactory<>("subState"));
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
        subValueCol.setCellValueFactory(new PropertyValueFactory<>("subValue"));
        deadlineDateCol.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        lastPayDateCol.setCellValueFactory(new PropertyValueFactory<>("lastPayDate"));

        try {
            refreshTable(allQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
