module com.example.myevent {
    opens com.example.myevent.ChefProjet to javafx.fxml, javafx.base;

    opens com.example.myevent.entities to javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires java.desktop;
    requires javax.mail.api;
    requires AnimateFX;
    requires mysql.connector.j;
    requires jBCrypt;
    exports com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent to javafx.fxml;
    exports com.example.myevent;
}
