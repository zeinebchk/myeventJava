package com.example.myevent.ChefProjet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private Button nextPageButton; // Assurez-vous que l'identifiant fx:id correspond à celui dans votre fichier FXML
    @FXML
    private Button nextPageButtonn; // Assurez-vous que l'identifiant fx:id correspond à celui dans votre fichier FXML
    @FXML
    private Button studio_btn;
    @FXML
    private Button venue_btn;
    @FXML
    private Button emp_btn;
    @FXML
    private Button event_btn;// Vérifiez cette ligne

    @FXML
    private void handleConnexionButtonAction(ActionEvent event) {
        // Charger la nouvelle page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nextPageButton.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Imprimer un message
            System.out.println("Navigué vers la deuxième page avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConnexionButtonActionn(ActionEvent event) {
        // Charger la nouvelle page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nextPageButtonn.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Imprimer un message
            System.out.println("Navigué vers la deuxième page avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConnexionButtonActionnListeClient(ActionEvent event) {
        // Charger la nouvelle page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) studio_btn.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Imprimer un message
            System.out.println("Navigué vers la deuxième page avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConnexionButtonActionnListeTransaction(ActionEvent event) {
        // Charger la nouvelle page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transaction.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) venue_btn.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Imprimer un message
            System.out.println("Navigué vers la deuxième page avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewProductButtonAction(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/offre.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) emp_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            System.out.println("Navigué vers la page hello-view avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddProductButtonAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/offre-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) event_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            System.out.println("Navigué vers la page hello-view avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

