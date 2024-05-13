package com.example.myevent.controllers;



import com.example.myevent.Models.profile;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ProfileController {

    @FXML
    private TableView<profile> profileTable;

    @FXML
    private TableColumn<profile, String> nomColumn;

    @FXML
    private TableColumn<profile, String> prenomColumn;

    @FXML
    private TableColumn<profile, String> emailColumn;

    @FXML
    private TableColumn<profile, String> numeroTelephoneColumn;

    @FXML
    private TableColumn<profile, String> numDeProjetColumn;

    @FXML
    private TableColumn<profile, String> categorieColumn;

    @FXML
    private TableColumn<profile, String> gouvernoratColumn;

    @FXML
    private TableColumn<profile, String> villeColumn;

    @FXML
    private TableColumn<profile, String> adresseExacteColumn;

    @FXML
    private TableColumn<profile, String> numTeleProColumn;

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField numeroTelephoneField;

    @FXML
    private TextField numDeProjetField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField gouvernoratField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField adresseExacteField;

    @FXML
    private TextField numTeleProField;

    @FXML
    private Hyperlink linkToUpdateProfilebtn;

    // Méthode d'initialisation
    public void initialize() {
        // Configurer les colonnes de la table
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        numeroTelephoneColumn.setCellValueFactory(cellData -> cellData.getValue().numeroTelephoneProperty());
        numDeProjetColumn.setCellValueFactory(cellData -> cellData.getValue().numDeProjetProperty());
        categorieColumn.setCellValueFactory(cellData -> cellData.getValue().categorieProperty());
        gouvernoratColumn.setCellValueFactory(cellData -> cellData.getValue().gouvernoratProperty());
        villeColumn.setCellValueFactory(cellData -> cellData.getValue().villeProperty());
        adresseExacteColumn.setCellValueFactory(cellData -> cellData.getValue().adresseExacteProperty());
        numTeleProColumn.setCellValueFactory(cellData -> cellData.getValue().numTeleProProperty());

        // Remplir la table avec des données fictives (vous pouvez remplacer cela avec vos données réelles)
        profileTable.setItems(profile.getSampleProfiles());
    }

    // Méthode appelée lorsque l'utilisateur clique sur le bouton de sauvegarde
    @FXML
    private void updateProfile() {
        // Récupérer les données saisies par l'utilisateur depuis les champs texte
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String numeroTelephone = numeroTelephoneField.getText();
        String numDeProjet = numDeProjetField.getText();
        String categorie = categorieField.getText();
        String gouvernorat = gouvernoratField.getText();
        String ville = villeField.getText();
        String adresseExacte = adresseExacteField.getText();
        String numTelePro = numTeleProField.getText();

        // Créer un nouvel objet Profile avec les données saisies
        profile newProfile = new profile(nom, prenom, email, numeroTelephone, numDeProjet, categorie, gouvernorat, ville, adresseExacte, numTelePro);

        // Ajouter le nouvel objet Profile à la table et à la liste des profils
        profileTable.getItems().add(newProfile);

        // Effacer les champs texte après avoir sauvegardé les données
        clearFields();
    }

    // Méthode pour effacer les champs texte
    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        numeroTelephoneField.clear();
        numDeProjetField.clear();
        categorieField.clear();
        gouvernoratField.clear();
        villeField.clear();
        adresseExacteField.clear();
        numTeleProField.clear();
    }

    @FXML
    private void UpdateProfilebtn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/dashborda/profile.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            Stage currentStage = (Stage) linkToUpdateProfilebtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception IOException ici
        }
    }
}

