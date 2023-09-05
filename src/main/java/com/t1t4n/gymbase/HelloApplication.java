package com.t1t4n.gymbase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("برنامج إدارة الجيم");

        Image icon = new Image("dumble.png");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBConnection.getConnect();
        launch();
    }
}