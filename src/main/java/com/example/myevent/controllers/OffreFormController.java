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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
import java.util.UUID;


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
    private Connection connection;
    private BigInteger offre_id;
    private String generatedOffreId;
    private BigInteger BigIntegerOffreId;
    private BigInteger offreId;
    private BigInteger entrepreneur_id;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    OffreService offreDAO = new OffreService();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = Connexion.getInstance().getCnx();


        // Initialisation des valeurs par défaut dans le formulaire
        titreField.setText("");
        descriptionField.setText("");
        prixInitialField.setText("0.0");
        prixRemiseField.setText("0.0");
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
        Entrepreneur ee = new Entrepreneur();
        entrepreneur_id = UserSession.getInstance().getUser().getId();

        // Vérifier si l'utilisateur est connecté
        if (entrepreneur_id == null || entrepreneur_id.equals(BigInteger.ZERO)) {
            showAlert("Erreur", "L'utilisateur n'est pas connecté.");
            return;
        }

        // Récupérer les informations de l'entrepreneur
        st = con.prepareStatement("SELECT * FROM entrepreneurs WHERE user_id = ?");
        st.setBigDecimal(1, new BigDecimal(entrepreneur_id));  // Utilisation de BigDecimal
        rs = st.executeQuery();
        if (rs.next()) {
            ee.setId(rs.getBigDecimal("id").toBigInteger());  // Utilisation correcte de BigInteger
        }

        try {
            connection.setAutoCommit(false);

            // Validation des champs obligatoires et numériques
            String titre = titreField.getText().trim();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + titre); // Afficher la valeur sélectionnée

            String description = descriptionField.getText().trim();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + description); // Afficher la valeur sélectionnée

            double prixInitial = parseDoubleField(prixInitialField, "Le prix initial doit être un nombre valide.");
            System.out.println("Insertion dans la base de données avec gouvernerat: " +prixInitial ); // Afficher la valeur sélectionnée

            double prixRemise = parseDoubleField(prixRemiseField, "Le prix de remise doit être un nombre valide.");
            System.out.println("Insertion dans la base de données avec gouvernerat: " +prixRemise ); // Afficher la valeur sélectionnée
            int surface = parseIntegerField(surfaceField, "La surface doit être un nombre valide.");
            System.out.println("Insertion dans la base de données avec gouvernerat: " + surface ); // Afficher la valeur sélectionnée
            int capacitePersonne = parseIntegerField(capaciteField, "La capacité doit être un nombre valide.");
            System.out.println("Insertion dans la base de données avec gouvernerat: " +capacitePersonne ); // Afficher la valeur sélectionnée

            // Vérification des champs obligatoires
            if (gouverneratField.getValue() == null || gouverneratField.getValue().isEmpty()) {
                showAlert("Erreur", "Le gouvernerat doit être sélectionné.");
                return;
            }


            String gouvernerat = gouverneratField.getValue();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + gouvernerat); // Afficher la valeur sélectionnée

            String ville = villeField.getValue();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + ville); // Afficher la valeur sélectionnée

            if (ville == null || ville.isEmpty()) {
                showAlert("Erreur", "La ville doit être sélectionnée.");
                return;
            }

            String adresseExacte = adresseField.getText();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + adresseExacte); // Afficher la valeur sélectionnée

            String optionInclus = optionsInclusField.getText();
            System.out.println("Insertion dans la base de données avec gouvernerat: " + optionInclus); // Afficher la valeur sélectionnée

            if (surface <= 0 || capacitePersonne <= 0) {
                showAlert("Erreur", "Les valeurs de surface et de capacité doivent être positives.");
                return;
            }

            if (prixInitial <= 0 || prixRemise < 0 || prixRemise > prixInitial) {
                showAlert("Erreur", "Les valeurs de prix sont incorrectes.");
                return;
            }

            if (imageFileName == null || imageFileName.isEmpty()) {
                showAlert("Erreur", "Veuillez sélectionner une image.");
                return;
            }

            if (titre.isEmpty() || description.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            // Création de l'objet Offre et SalleFete
            Offre offre = new Offre(titre, description);
            SalleFete sallefete = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseExacte, optionInclus, offre.getId());
            Image image = new Image();
            image.setUrl(imageFileName);

            // Ajouter l'offre dans la base de données
           // offreDAO.ajouterOffre(offre, sallefete,image);

            // Réinitialiser le formulaire et commettre la transaction
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
        } /*catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    // Méthodes utilitaires pour parsing
    private double parseDoubleField(TextField field, String errorMessage) {
        try {
            return Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", errorMessage);
            throw e;
        }
    }

    private int parseIntegerField(TextField field, String errorMessage) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", errorMessage);
            throw e;
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
            Path destinationPath = Paths.get("D:","projects", "images", uniqueFileName);

            try {
                // Vérifier si le fichier existe déjà, et si c'est le cas, générer un nouveau nom
                while (Files.exists(destinationPath)) {
                    uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
                    destinationPath = Paths.get("D:","projects", "images", uniqueFileName);
                }

                Files.copy(sourcePath, destinationPath);
                imageFileName = uniqueFileName;

                // Afficher une alerte pour informer l'utilisateur que le fichier a été copié avec succès
                showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Le fichier a été importé avec succès.");
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
            Parent menuParent = FXMLLoader.load(getClass().getResource("/fxml/Menu.fxml"));
            Scene menuScene = new Scene(menuParent);
            Stage window = (Stage) retourMenuButton.getScene().getWindow();
            window.setScene(menuScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la page de menu : " + e.getMessage());
        }
    }

   public void afficherMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

