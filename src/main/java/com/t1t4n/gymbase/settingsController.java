package com.t1t4n.gymbase;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.t1t4n.gymbase.HelloApplication.prefs;


public class settingsController implements Initializable {
    @FXML
    TextField bbPrice;
    @FXML
    TextField fitPrice;
    @FXML
    TextField privPrice;
    @FXML
    TextField sesPrice;
    @FXML
    ToggleButton themes;


    @FXML
    private void setTheme(){
        if (themes.isSelected()){
            Application.setUserAgentStylesheet("cupertino-dark.css");
            prefs.put("THEMES", "dark");
            //themes.setText("التحويل للشكل النهاري");
        } else {
            Application.setUserAgentStylesheet("cupertino-light.css");
            prefs.put("THEMES", "light");
            //themes.setText("التحويل للشكل الليلي");
        }
        setToggleText();
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
    @FXML
    private void setPrice(){
        bbPrice.setText(String.valueOf(prefs.getInt("BBPRICE", 0)));
        fitPrice.setText(String.valueOf(prefs.getInt("FITPRICE", 0)));
        privPrice.setText(String.valueOf(prefs.getInt("PRIVPRICE", 0)));
        sesPrice.setText(String.valueOf(prefs.getInt("SESPRICE", 0)));
    }
    @FXML
    private void savePrices(){
        prefs.putInt("BBPRICE", Integer.parseInt(bbPrice.getText()));
        prefs.putInt("FITPRICE", Integer.parseInt(fitPrice.getText()));
        prefs.putInt("PRIVPRICE", Integer.parseInt(privPrice.getText()));
        prefs.putInt("SESPRICE", Integer.parseInt(sesPrice.getText()));
    }
    public settingsController() {
        themes = new ToggleButton();



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Preferences.userNodeForPackage(this.getClass());
        setToggleText();
        setPrice();
    }
}
