package com.example.myevent.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import com.example.myevent.entities.Image;
import com.example.myevent.entities.Offre;
import com.example.myevent.entities .OffreDAO;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class OffreFormController implements Initializable {
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
    private TextField prixRemiseField;

    @FXML
    private DatePicker dateFinRemisePicker;

    @FXML
    private TextField surfaceField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField urlField;

    @FXML
    private Label imageName;

    @FXML
    private Button ajouterOffre;



    private String imageData;
    private OffreDAO offreDAO;
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection con = Connexion.getInstance().getCnx();
        offreDAO = new OffreDAO(connection);

        // Initialisation des valeurs par défaut dans le formulaire
        titreField.setText("");
        descriptionField.setText("");
        prixInitialField.setText("0.0");
        prixRemiseField.setText("0.0");
        dateFinRemisePicker.setValue(LocalDate.now());
        surfaceField.setText("0");
        adresseField.setText("");
        capaciteField.setText("0");


        optionsInclusField.setText("");
        ChoiceBox<String> gouvernerat = gouverneratField;
        gouvernerat.getItems().add("Sfax");
        gouvernerat.getItems().add("Tunis");
        gouvernerat.getItems().add("Monastir");
        gouvernerat.getItems().add("Sousse");
        gouvernerat.setValue("Monastir");
        ChoiceBox<String> ville = villeField;
        ville.getItems().addAll("Monastir", "Moknine", "Sahline");
        ville.setValue("Monastir");
        gouvernerat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Effacer les anciennes villes
            ville.getItems().clear();
            // Ajouter les nouvelles villes en fonction du gouvernorat sélectionné
            switch (newValue) {
                case "Sfax":
                    ville.getItems().addAll("Sfax Ville", "Sakiet Eddaier", "Sakiet Ezzit");
                    break;
                case "Tunis":
                    ville.getItems().addAll("Tunis", "Ariana", "Ben Arous");
                    break;
                case "Monastir":
                    ville.getItems().addAll("Monastir", "Moknine", "Sahline");
                    break;
                case "Sousse":
                    ville.getItems().addAll("Sousse", "Hammam Sousse", "Kalaa Kebira");
                    break;

                default:
            }
        });}
    @FXML
    private void handleAjouterOffre() throws SQLException {
        try {
            String titre = titreField.getText().trim();
            String description = descriptionField.getText().trim();
            double prixInitial = Double.parseDouble(prixInitialField.getText());
            double prixRemise = Double.parseDouble(prixRemiseField.getText());
            LocalDate dateFinRemise = dateFinRemisePicker.getValue();
            int surface = Integer.parseInt(surfaceField.getText());
            int capacitePersonne = Integer.parseInt(capaciteField.getText());
            String gouvernerat = gouverneratField.getValue();
            String ville = villeField.getValue();
            String img = imageData;
            String optionsInclus = optionsInclusField.getText().trim();

            if (titre.isEmpty() || description.isEmpty() || gouvernerat.isEmpty() || ville.isEmpty() || img.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            Offre offre = new Offre(null, titre, description, prixInitial, prixRemise, dateFinRemise);
            SalleFete salleFete = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseField.getText(), optionsInclus);
            Image image = new Image(img); // Assurez-vous que votre constructeur Image prend l'URL en paramètre

            offreDAO.ajouterOffre(offre, salleFete, image);
            clearFormFields();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir des valeurs valides dans les champs numériques.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFormFields() {
        titreField.clear();
        descriptionField.clear();
        prixInitialField.clear();
        prixRemiseField.clear();
        dateFinRemisePicker.setValue(LocalDate.now());
        surfaceField.clear();
        adresseField.clear();
        gouverneratField.getItems().clear();
        villeField.getItems().clear();
        optionsInclusField.clear();
    }

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imageData = selectedFile.getPath();
            imageName.setText(imageData);
        }
    }
}
