package com.t1t4n.gymbase;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.t1t4n.gymbase.HelloApplication.prefs;

public class settingsController implements Initializable {
    @FXML
    RadioButton light1;
    @FXML
    RadioButton dark1;
    @FXML
    ToggleButton themes;


    @FXML
    private void setTheme(){
        if (themes.isSelected()){
            Application.setUserAgentStylesheet("cupertino-dark.css");
            prefs.put("THEMES", "dark");
            themes.setText("التحويل للشكل النهاري");
        } else {
            Application.setUserAgentStylesheet("cupertino-light.css");
            prefs.put("THEMES", "light");
            themes.setText("التحويل للشكل الليلي");
        }
    }

    public settingsController() {
        themes = new ToggleButton();

    }
    private void setToggleText(){
        if (prefs.get("THEMES", "light").equals("light")) {
            themes.setSelected(false);
            themes.setText("التحويل للشكل الليلي");
        }
        else if (prefs.get("THEMES","light").equals("dark")){
            themes.setSelected(true);
            themes.setText("التحويل للشكل النهاري");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Preferences.userNodeForPackage(this.getClass());
        setToggleText();

    }
}
