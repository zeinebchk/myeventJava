package com.example.myevent.controllers;

import com.example.myevent.entities.*;
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
import com.example.myevent.tools.Connexion;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;
import java.math.BigInteger;


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
    private String imageFileName;
    @FXML
    private Button ajouterOffre;
    @FXML
    private Button retourMenuButton;
    private BigInteger id = BigInteger.ZERO;
    private String imageData;
    private OffreDAO offreDAO;
    private Connection connection;
    private BigInteger offre_id;
    private String generatedOffreId;
    private BigInteger BigIntegerOffreId;
    private BigInteger offreId;
    private BigInteger entrepreneur_id;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = Connexion.getInstance().getCnx();
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

        gouverneratField.setItems(FXCollections.observableArrayList("Sfax", "Tunis", "Monastir", "Sousse"));
        villeField.setItems(FXCollections.observableArrayList("Monastir", "Moknine", "Sahline"));

        gouverneratField.setValue("Monastir");

        gouverneratField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                villeField.getItems().clear();
                switch (newValue) {
                    case "Sfax":
                        villeField.setItems(FXCollections.observableArrayList("Sfax Ville", "Sakiet Eddaier", "Sakiet Ezzit"));
                        break;
                    case "Tunis":
                        villeField.setItems(FXCollections.observableArrayList("Tunis", "Ariana", "Ben Arous"));
                        break;
                    case "Monastir":
                        villeField.setItems(FXCollections.observableArrayList("Monastir", "Moknine", "Sahline"));
                        break;
                    case "Sousse":
                        villeField.setItems(FXCollections.observableArrayList("Sousse", "Hammam Sousse", "Kalaa Kebira"));
                        break;
                }
            }
        });
    }

    @FXML
    private void handleAjouterOffre() throws SQLException {
        Entrepreneur ee=new Entrepreneur();
        entrepreneur_id= UserSession.getInstance().getUser().getId();
        st = con.prepareStatement("SELECT * FROM entrepreneurs WHERE user_id = ?");
        st.setBigDecimal(1,new BigDecimal(entrepreneur_id));
        rs = st.executeQuery();
        if (rs.next()) {

           ee.setId(rs.getBigDecimal("id").toBigInteger());
        }
        try {
            connection.setAutoCommit(false);

            String titre = titreField.getText().trim();
            String description = descriptionField.getText().trim();
            double prixInitial = Double.parseDouble(prixInitialField.getText());
            double prixRemise = Double.parseDouble(prixRemiseField.getText());
            LocalDate dateFinRemise = dateFinRemisePicker.getValue();
            int surface = Integer.parseInt(surfaceField.getText());
            int capacitePersonne = Integer.parseInt(capaciteField.getText());
            String gouvernerat = gouverneratField.getValue();
            String ville = villeField.getValue();
            String adresseExacte =adresseField.getText();
            String optionInclus =optionsInclusField.getText();

            if (imageFileName == null || imageFileName.isEmpty()) {
                showAlert("Erreur", "Veuillez sélectionner une image.");
                return;
            }
            if (titre.isEmpty() || description.isEmpty() || gouvernerat == null || ville == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }


            Offre offre = new Offre(id, titre, description, prixInitial, prixRemise, dateFinRemise, ee.getId());
            if (offre.getDateFinRemise() == null) {
                throw new IllegalArgumentException("La date de fin de remise ne peut pas être null.");
            }
            SalleFete salleFete = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseExacte, optionInclus ,offre.getId());
            Image image = new Image(imageFileName, offre.getId());
            image.setImageURL(imageFileName);

            offreDAO.ajouterOffre(offre, salleFete, image);
            clearFormFields();
            connection.commit();
            connection.setAutoCommit(true);

            showAlert("Succès", "L'offre a été ajoutée avec succès.");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'offre dans la base de données : " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                showAlert("Erreur", "Erreur lors du rollback de la transaction : " + ex.getMessage());
                ex.printStackTrace();
            }
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
        optionsInclusField.clear();
        gouverneratField.setValue("Monastir");
        villeField.getItems().clear();
    }



    @FXML
    private void onUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Path sourcePath = file.toPath();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
            Path destinationPath = Paths.get("C:","Users", "R I B", "images", uniqueFileName);

            try {
                // Vérifier si le fichier existe déjà, et si c'est le cas, générer un nouveau nom
                while (Files.exists(destinationPath)) {
                    uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
                    destinationPath = Paths.get("C:","Users", "R I B", "images", uniqueFileName);
                }

                Files.copy(sourcePath, destinationPath);
                imageFileName = uniqueFileName;

                // Afficher une alerte pour informer l'utilisateur que le fichier a été copié avec succès
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le fichier a été importé avec succès.");
            } catch (IOException e) {
                e.printStackTrace();
                // Afficher une alerte en cas d'erreur
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'importation du fichier.");
            }
        } else {
            // Afficher une alerte demandant à l'utilisateur de sélectionner une image
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une image.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }




    @FXML
    private void handleRetourMenu(ActionEvent event) {
        try {
            Parent menuParent = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Scene menuScene = new Scene(menuParent);
            Stage window = (Stage) retourMenuButton.getScene().getWindow();
            window.setScene(menuScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la page de menu : " + e.getMessage());
        }
    }
}

