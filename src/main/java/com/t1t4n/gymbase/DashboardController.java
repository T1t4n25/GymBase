package com.t1t4n.gymbase;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.shape.Arc;

public class DashboardController {
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
    TableColumn<Member, String> newName;
    @FXML
    TableColumn<Member, String> dueName;
    @FXML
    TableColumn<Member, String> overdueName;
    @FXML
    TableColumn<Member, Double> dueCash;
    @FXML
    TableColumn<Member, Double> overdueCash;
    @FXML
    TableColumn<Member, String> newDuration;


}
