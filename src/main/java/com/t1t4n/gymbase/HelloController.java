package com.t1t4n.gymbase;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    Button dashboard_btn;
    @FXML
    Button member_menu_btn;
    @FXML
    Button add_member_btn;
    @FXML
    Button billing_btn;
    @FXML
    Button billing_reports_btn;
    @FXML
    BorderPane container;
    AnchorPane dashboard;
    AnchorPane addMember;
    AnchorPane pay;
    AnchorPane billing;
    AnchorPane settings;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        container.setCenter(dashboard);

    }

}