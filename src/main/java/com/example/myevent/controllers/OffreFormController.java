package com.example.myevent.controllers;

import com.example.myevent.Services.OffreService;
import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.myevent.entities.Image;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.myevent.controllers.CardEventController.event;

public class OffreFormController implements Initializable {
    @FXML
    private Label imagefileName;

    @FXML
    private ChoiceBox<String> gouverneratField;

    @FXML
    private ChoiceBox<String> villeField;

    @FXML
    private TextField optionsInclusField;

    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField capaciteField;

    @FXML
    private TextField prixInitialField;


    @FXML
    private TextField surfaceField;

    @FXML
    private TextField adresseField;

    @FXML
    private Button ajouterOffre;

    @FXML
    private Button retourMenuButton;

    private Connection connection;
    private BigInteger id = BigInteger.ZERO;
    private BigInteger entrepreneur_id;
    private byte[] imageBytes;
    private OffreService offreDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = Connexion.getInstance().getCnx();
        offreDAO = new OffreService();

        // Initialisation des valeurs par défaut
        titreField.setText("");
        descriptionField.setText("");
        prixInitialField.setText("0.0");
        surfaceField.setText("0");
        adresseField.setText("");
        capaciteField.setText("");
        optionsInclusField.setText("");

        gouverneratField.setItems(FXCollections.observableArrayList("Sfax", "Tunis", "Monastir", "Sousse"));
        villeField.setItems(FXCollections.observableArrayList("Monastir", "Moknine", "Sahline"));
        gouverneratField.setValue("Monastir");

        gouverneratField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                villeField.getItems().clear();
                switch (newValue) {
                    case "Sfax" ->
                            villeField.setItems(FXCollections.observableArrayList("Sfax Ville", "Sakiet Eddaier", "Sakiet Ezzit"));
                    case "Tunis" ->
                            villeField.setItems(FXCollections.observableArrayList("Tunis", "Ariana", "Ben Arous"));
                    case "Monastir" ->
                            villeField.setItems(FXCollections.observableArrayList("Monastir", "Moknine", "Sahline"));
                    case "Sousse" ->
                            villeField.setItems(FXCollections.observableArrayList("Sousse", "Hammam Sousse", "Kalaa Kebira"));
                }
            }
        });
    }

    @FXML
    private void handleAjouterOffre(ActionEvent event) {
        try {


            // Validation et parsing des champs
            String titre = titreField.getText().trim();
            String description = descriptionField.getText().trim();
            double prixInitial = parseDoubleField(prixInitialField, "Le prix initial doit être un nombre valide.");
            int surface = parseIntegerField(surfaceField, "La surface doit être un nombre valide.");
            int capacitePersonne = parseIntegerField(capaciteField, "La capacité doit être un nombre valide.");
            String gouvernerat = gouverneratField.getValue();
            String ville = villeField.getValue();
            String adresseExacte = adresseField.getText().trim();
            String optionInclus = optionsInclusField.getText().trim();

            // Vérification des champs obligatoires
            if (titre.isEmpty() || description.isEmpty() || gouvernerat == null || ville == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }


            // Création des objets Offre et SalleFete
            Offre offre = new Offre(titre, description);
            offre.setPrixInitial(prixInitial);
            SalleFete sallefete = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseExacte, optionInclus, offre.getId());

            com.example.myevent.entities.Image image;
            if (imageBytes == null) {
                image = new com.example.myevent.entities.Image();
            } else {
                image = new com.example.myevent.entities.Image(imageBytes, offre.getId());
            }

            // Ajout dans la base de données
            offreDAO.ajouterOffre(offre, sallefete, image);

            showAlert("Succès", "L'offre a été ajoutée avec succès.");
            clearFormFields();
        } catch (SQLException | IOException e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'offre : " + e.getMessage());

        }
    }

    private double parseDoubleField(TextField field, String errorMessage) {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", errorMessage);
            throw e;
        }
    }

    private int parseIntegerField(TextField field, String errorMessage) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", errorMessage);
            throw e;
        }
    }

    private void clearFormFields() {
        titreField.clear();
        descriptionField.clear();
        prixInitialField.clear();
        surfaceField.clear();
        capaciteField.clear();
        adresseField.clear();
        optionsInclusField.clear();
        gouverneratField.setValue("Monastir");
        villeField.getItems().clear();
    }

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                Path imagePath = file.toPath();
                imageBytes = Files.readAllBytes(imagePath);
                imagefileName.setText(file.getName());
                showAlert("Succès", "L'image a été importée avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur s'est produite lors de l'importation de l'image.");
            }
        } else {
            showAlert("Attention", "Veuillez sélectionner une image.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleRetourMenu(ActionEvent event) {
        try {
            Parent menuParent = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
            Scene menuScene = new Scene(menuParent);
            Stage window = (Stage) retourMenuButton.getScene().getWindow();
            window.setScene(menuScene);
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors du retour au menu : " + e.getMessage());
        }
    }
}