package com.t1t4n.gymbase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

public class HelloApplication extends Application {
    static Preferences prefs;

    @Override
    public void start(Stage stage) throws IOException {
        prefs = Preferences.userNodeForPackage(this.getClass());
        if (prefs.get("THEMES","light").equals("light"))
            Application.setUserAgentStylesheet("cupertino-light.css");
        else if (prefs.get("THEMES","light").equals("dark")) {

            Application.setUserAgentStylesheet("cupertino-dark.css");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GymBase");

        Image icon = new Image("dumble.png");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        DBConnection.getConnect();
        launch();
    }
}