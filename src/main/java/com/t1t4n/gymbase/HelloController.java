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
    AnchorPane dashboard= FXMLLoader.load(this.getClass().getResource("dashboard.fxml"));
    AnchorPane addMember= FXMLLoader.load(this.getClass().getResource("addMember.fxml"));
    AnchorPane pay = FXMLLoader.load(this.getClass().getResource("pay.fxml"));
    AnchorPane billing= FXMLLoader.load(this.getClass().getResource("billing.fxml"));
    AnchorPane settings= FXMLLoader.load(this.getClass().getResource("settings.fxml"));

    public HelloController() throws IOException {
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

        container.setCenter(pay);

    }

}