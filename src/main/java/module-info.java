module com.t1t4n.gymbase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires javafx.graphics;


    opens com.t1t4n.gymbase to javafx.fxml;
    exports com.t1t4n.gymbase;
    
}