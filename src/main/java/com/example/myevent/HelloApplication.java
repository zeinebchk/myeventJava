package com.example.myevent;

import com.example.myevent.tools.Connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fxml/Acceuil.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Reservation Manager");
            stage.setScene(scene);
            stage.show();

            //   TransactionController tr = fxmlLoader.getController();
            //     tr.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connexion connexion = Connexion.getInstance();
        if (connexion.getCnx() != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("La connexion a échoué.");
        }
        launch();
    }
}
