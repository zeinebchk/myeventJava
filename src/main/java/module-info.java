module com.example.myevent {
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires com.jfoenix;
    requires jBCrypt;
   // requires jakarta.mail;
    requires animatefx; // Ensure you require the javafx.base module
    opens com.example.myevent.entities to javafx.base; // This opens your entities package to javafx.base

    opens com.example.myevent.ChefProjet to javafx.fxml;
    exports com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent.controllers to javafx.fxml;
    opens com.example.myevent to javafx.fxml;

    exports com.example.myevent;
}