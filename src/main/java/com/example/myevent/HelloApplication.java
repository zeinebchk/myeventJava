package com.example.myevent;

import com.example.myevent.tools.Connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fxml/offre-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
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