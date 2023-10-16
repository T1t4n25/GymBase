module com.t1t4n.gymbase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.prefs;
    requires java.datatransfer;
    requires java.desktop;


    opens com.t1t4n.gymbase to javafx.fxml;
    exports com.t1t4n.gymbase;
    
}
//jpackage --type exe --input . --dest . --main-jar .\GymBase.jar --main-class com.t1t4n.gymbase.Main --module-path "D:\T1t4nProject\Java\javafx-jmods-20.0.2" --add-modules javafx.controls, javafx.fxml, javafx.sql, javafx.graphics --win-shortcut --winmenu

//jpackage --type exe --input . --dest . --main-jar .\GymBase.jar --main-class com.t1t4n.gymbase.Main --module-path "C:\Program Files\BellSoft\LibericaJDK-20-Full\jmods" --add-modules javafx.controls,javafx.fxml,java.sql,javafx.graphics --win-shortcut