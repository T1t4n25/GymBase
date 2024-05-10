package com.t1t4n.gymbase;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Arc;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    Arc late;
    @FXML
    Arc done;
    @FXML
    Arc scheduled;
    @FXML
    Label lateNum;
    @FXML
    Label doneNum;
    @FXML
    Label scheduledNum;
    @FXML
    Label lateMem;
    @FXML
    Label schMem;
    @FXML
    Label doneMem;
    @FXML
    TableView<Member> newMembers;
    @FXML
    TableView<Member> todayDues;
    @FXML
    TableView<Member> overdue;
    @FXML
    TableColumn<Member, String> newNameCol;
    @FXML
    TableColumn<Member, String> subTypeCol;
    @FXML
    TableColumn<Member, String> dueNameCol;
    @FXML
    TableColumn<Member, String> overdueNameCol;
    @FXML
    TableColumn<Member, Double> dueCashCol;
    @FXML
    TableColumn<Member, Double> overdueCashCol;
    @FXML
    TableColumn<Member, String> newDurationCol;
    @FXML
    TableColumn<Member, String> dueNumberCol;
    @FXML
    TableColumn<Member, String> overdueNumberCol;
    @FXML
    TableColumn<Member, Date> overdueDateCol;

    ObservableList<Member> newData, dueData, overdueData;
    ResultSet resultSet;
    int lateSum = 0;
    int lateMemSum = 0;
    int schMemSum = 0;
    int doneMemSum = 0;
    public DashboardController() {
        newNameCol = new TableColumn<>();
        subTypeCol = new TableColumn<>();
        newDurationCol = new TableColumn<>();
        overdueDateCol = new TableColumn<>();

    }

    private void setArcs() throws SQLException {
        //late
        int lateLength = 0;
        int schLength = 0;
        int doneLength = 0;
        if((scheduledCash() + doneCash()> 0)) {
            lateLength = (lateSum * 360) / (scheduledCash() + doneCash());
            schLength = (scheduledCash() * 360) / (scheduledCash() + doneCash());
            doneLength = (doneCash() * 360) / (scheduledCash() + doneCash());
        }
        //late
        lateNum.setText(lateSum + "ج.م");
        late.setLength(lateLength);
        lateSum = 0;
        //scheduled
        scheduledNum.setText(scheduledCash() + "ج.م");
        scheduled.setLength(schLength);
        //done
        doneNum.setText(doneCash() + "ج.م");
        done.setLength(doneLength);

    }
    private int doneCash() throws SQLException {
        int doneSum = 0;
        resultSet = DBConnection.statement.executeQuery(
                "SELECT `subValue`FROM `members_data` WHERE MONTH(`lastPayDate`) = MONTH(CURDATE()) AND YEAR(`lastPayDate`) = YEAR(CURDATE()) AND `subType` != 'حصة';");
        resultSet.beforeFirst();
        while(resultSet.next()) {
            doneSum += resultSet.getInt("subValue");
            doneMemSum++;
        }
        return doneSum;
    }
    private int scheduledCash() throws SQLException {
        int scheduledSum = 0;
        resultSet = DBConnection.statement.executeQuery(
                "SELECT `subValue`, `subType` FROM `members_data` WHERE (MONTH(`deadlineDate`) = MONTH(CURDATE()) AND YEAR(`deadlineDate`) = YEAR(CURDATE())) AND `subState` = 'نشط' AND `subType` != 'حصة'");
        resultSet.beforeFirst();
        while(resultSet.next()) {
            scheduledSum += resultSet.getInt("subValue");
            schMemSum++;
        }
        return scheduledSum;
    }
    private void newMembersFill() throws SQLException {

        resultSet = DBConnection.statement.executeQuery(
                "SELECT `name`, `subType`, `joinDate` FROM `members_data` WHERE `subState` = 'نشط' AND `joinDate` > DATE_SUB(CURDATE(), INTERVAL 1 MONTH) AND `subType` != 'حصة';"
        );
        newData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while(resultSet.next()){
                String name = resultSet.getString("name");
                String type = resultSet.getString("subType");
                Date joinDate = resultSet.getDate("joinDate");

                LocalDate localJoinDate = joinDate.toLocalDate();
                LocalDate currentDate = LocalDate.now();
                Period period = Period.between(localJoinDate, currentDate);
                int joinDuration = period.getDays();
                newData.add(new Member(name, type, joinDuration, true));
        }
        newMembers.setItems(newData);
    }
    private void dueMembersFill() throws SQLException {
        resultSet = DBConnection.statement.executeQuery(
                "SELECT `name`, `subValue`, `subType`, `number` FROM `members_data` WHERE  `deadlineDate` = CURRENT_DATE AND `subState` = 'نشط' AND `subType` != 'حصة';");
        dueData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while (resultSet.next()){
                String name = resultSet.getString("name");
                String number = resultSet.getString("number");
                int value = resultSet.getInt("subValue");
                dueData.add(new Member(name, number, value));
        }
        todayDues.setItems(dueData);
    }
    private void overdueMembersFill() throws SQLException {
        resultSet = DBConnection.statement.executeQuery(
                "SELECT `name`, `subValue`, `number`, `deadlineDate` FROM `members_data` WHERE (`deadlineDate` < CURDATE() OR `deadlineDate` IS NULL) AND `subState` = 'نشط' AND `subType` != 'حصة';");
        overdueData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while (resultSet.next()){
            String name = resultSet.getString("name");
            String number = resultSet.getString("number");
            int value = resultSet.getInt("subValue");
            Date deadline = resultSet.getDate("deadlineDate");
            overdueData.add(new Member(name, value, deadline, number));
            lateSum += value;
            lateMemSum++;
        }
        overdue.setItems(overdueData);
    }
    @FXML
    private void refreshTables() throws SQLException {
        newMembersFill();
        dueMembersFill();
        overdueMembersFill();
        setArcs();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
        newDurationCol.setCellValueFactory(new PropertyValueFactory<>("joinDuration"));
        //due today table
        dueNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dueCashCol.setCellValueFactory(new PropertyValueFactory<>("subValue"));
        dueNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        //overdue table
        overdueNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        overdueCashCol.setCellValueFactory(new PropertyValueFactory<>("subValue"));
        overdueNumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        overdueDateCol.setCellValueFactory(new PropertyValueFactory<>("deadlineDate"));

        try {
            refreshTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
