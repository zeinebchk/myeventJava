package com.example.myevent.controllers;





import com.example.myevent.Models.signupA;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignupAController {
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField numTelField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nomProjetField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField gouvernoratField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField adresseExacteField;

    @FXML
    private TextField passwordField;

    private signupA signupModel;

    public void initialize() {
        signupModel = new signupA();
        bindFieldsToModel();
    }

    private void bindFieldsToModel() {
        nomField.textProperty().bindBidirectional(signupModel.nomProperty());
        prenomField.textProperty().bindBidirectional(signupModel.prenomProperty());
        numTelField.textProperty().bindBidirectional(signupModel.numTelProperty());
        emailField.textProperty().bindBidirectional(signupModel.emailProperty());
        nomProjetField.textProperty().bindBidirectional(signupModel.nomProjetProperty());
        categorieField.textProperty().bindBidirectional(signupModel.categorieProperty());
        gouvernoratField.textProperty().bindBidirectional(signupModel.gouvernoratProperty());
        villeField.textProperty().bindBidirectional(signupModel.villeProperty());
        adresseExacteField.textProperty().bindBidirectional(signupModel.adresseExacteProperty());
        passwordField.textProperty().bindBidirectional(signupModel.passwordProperty());
    }

    @FXML
    private void SubmitButton(ActionEvent event) {
        // Insérer ici la logique pour traiter les données du formulaire
        System.out.println("Formulaire soumis !");
        System.out.println("Nom: " + signupModel.getNom());
        System.out.println("Prénom: " + signupModel.getPrenom());
        System.out.println("numTel: " + signupModel.getNumTel());
        System.out.println("email: " + signupModel.getEmail());
        System.out.println("nomProjet: " + signupModel.getNomProjet());
        System.out.println("categorie: " + signupModel.getCategorie());
        System.out.println("gouvernorat: " + signupModel.getGouvernorat());
        System.out.println("ville: " + signupModel.getVille());
        System.out.println("adresseExacte: " + signupModel.getAdresseExacte());
        System.out.println("password: " + signupModel.getPassword());

    }
}


