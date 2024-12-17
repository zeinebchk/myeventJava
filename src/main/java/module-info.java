module com.example.myevent {
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires mysql.connector.j;
    requires java.desktop;
    requires com.jfoenix;
    requires jBCrypt;
    requires javax.mail.api;
    requires animatefx;
    requires javafx.swingEmpty; // Ensure you require the javafx.base module
    // This opens your entities package to javafx.base

    opens com.example.myevent.entities to javafx.base;  // Permet à javafx.base d'accéder à ce package
    opens com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent.ChefProjet to javafx.fxml;
    exports com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent to javafx.fxml;

    exports com.example.myevent;
}