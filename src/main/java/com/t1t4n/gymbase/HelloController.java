package com.t1t4n.gymbase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    BorderPane container;
    AnchorPane dashboard;
    AnchorPane addMember;
    AnchorPane pay;
    AnchorPane billing;
    AnchorPane settings;

    ResultSet resultSet;

    public HelloController() throws IOException {
        dashboard = FXMLLoader.<AnchorPane>load(this.getClass().getResource("dashboard.fxml"));
        addMember = FXMLLoader.<AnchorPane>load(this.getClass().getResource("addMember.fxml"));
        pay = FXMLLoader.<AnchorPane>load(this.getClass().getResource("pay.fxml"));
        billing = FXMLLoader.<AnchorPane>load(this.getClass().getResource("billing.fxml"));
        settings = FXMLLoader.<AnchorPane>load(this.getClass().getResource("settings.fxml"));
    }
    @FXML
    private void setDashboard() {
        container.setCenter(dashboard);
    }
    @FXML
    private void setAddMember() {
        container.setCenter(addMember);
    }
    @FXML
    private void setPay() {
        container.setCenter(pay);
    }
    @FXML
    private void setBilling() {
        container.setCenter(billing);
    }
    @FXML
    private void setSettings() {
        container.setCenter(settings);
    }

    private void DBUpdateActivity() throws SQLException {
        DBConnection.statement.executeUpdate(
                "UPDATE members_data SET subState = 'غير نشط' WHERE deadlineDate <= DATE_SUB(NOW(), INTERVAL 7 DAY) AND subState = 'نشط';");
        DBConnection.statement.executeUpdate(
                "UPDATE members_data SET subState = 'غير نشط' WHERE deadlineDate != NOW() AND subState = 'نشط' AND subType = 'حصة';");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DBUpdateActivity();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        container.setCenter(settings);

    }

}