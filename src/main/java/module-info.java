module com.example.myevent {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires mysql.connector.j;
    requires AnimateFX;
    requires java.desktop;
    requires com.jfoenix;
    requires javafx.base;
    requires jBCrypt;
    requires javax.mail.api; // Ensure you require the javafx.base module
    opens com.example.myevent.entities to javafx.base; // This opens your entities package to javafx.base


    opens com.example.myevent.ChefProjet to javafx.fxml;
    exports com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent to javafx.fxml;

    exports com.example.myevent;
}