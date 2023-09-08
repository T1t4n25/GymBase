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

    ObservableList<Member> newData, dueData, overdueData;
    ResultSet resultSet;
    public DashboardController() {
        newNameCol = new TableColumn<>();
        subTypeCol = new TableColumn<>();
        newDurationCol = new TableColumn<>();

    }

    private void newMembersFill() throws SQLException {

        resultSet = DBConnection.statement.executeQuery(
                "SELECT `name`, `subType`, `joinDate` FROM `members_data` WHERE `subState` = 'نشط' AND (`joinDate` >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) AND `joinDate` <= CURDATE());"
        );
        newData = FXCollections.observableArrayList();
        resultSet.beforeFirst();
        while(resultSet.next()){
            String name = resultSet.getString("name");
            String type = resultSet.getString("subType");
            Date joinDate = resultSet.getDate("joinDate");

            LocalDate localJoinDate = joinDate.toLocalDate();

            // Get the current date as LocalDate
            LocalDate currentDate = LocalDate.now();

            // Calculate the difference between currentDate and localJoinDate
            Period period = Period.between(localJoinDate, currentDate);

            // Extract the number of days from the Period
            int joinDuration = period.getDays();
            newData.add(new Member(name, type, joinDuration));
        }
        newMembers.setItems(newData);
    }
    private void dueMembersFill(){

    }
    private void overdueMembersFill(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));
        newDurationCol.setCellValueFactory(new PropertyValueFactory<>("joinDuration"));

        try {
            newMembersFill();
            //dueMembersFill();
            //overdueMembersFill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
