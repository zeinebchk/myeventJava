module com.example.myevent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires mysql.connector.j;
    requires AnimateFX;
    requires java.desktop;
    requires javax.mail.api;
    exports com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent to javafx.fxml;
    exports com.example.myevent;
}