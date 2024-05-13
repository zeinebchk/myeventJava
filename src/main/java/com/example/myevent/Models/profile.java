package com.example.myevent.Models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class profile {
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty numeroTelephone;
    private final StringProperty numDeProjet;
    private final StringProperty categorie;
    private final StringProperty gouvernorat;
    private final StringProperty ville;
    private final StringProperty adresseExacte;
    private final StringProperty numTelePro;

    // Constructeur
    public profile(String nom, String prenom, String email, String numeroTelephone, String numDeProjet, String categorie, String gouvernorat, String ville, String adresseExacte, String numTelePro) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.numeroTelephone = new SimpleStringProperty(numeroTelephone);
        this.numDeProjet = new SimpleStringProperty(numDeProjet);
        this.categorie = new SimpleStringProperty(categorie);
        this.gouvernorat = new SimpleStringProperty(gouvernorat);
        this.ville = new SimpleStringProperty(ville);
        this.adresseExacte = new SimpleStringProperty(adresseExacte);
        this.numTelePro = new SimpleStringProperty(numTelePro);
    }

    // Getters et Setters
    public StringProperty nomProperty() {
        return nom;
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty numeroTelephoneProperty() {
        return numeroTelephone;
    }

    public StringProperty numDeProjetProperty() {
        return numDeProjet;
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public StringProperty gouvernoratProperty() {
        return gouvernorat;
    }

    public StringProperty villeProperty() {
        return ville;
    }

    public StringProperty adresseExacteProperty() {
        return adresseExacte;
    }

    public StringProperty numTeleProProperty() {
        return numTelePro;
    }

    // Méthode pour générer des profils de test (à remplacer avec vos propres données)
    public static ObservableList<profile> getSampleProfiles() {
        ObservableList<profile> profiles = FXCollections.observableArrayList();
        profiles.add(new profile("Nom1", "Prenom1", "email1@example.com", "123456789", "001", "Catégorie1", "Gouvernorat1", "Ville1", "Adresse1", "987654321"));
        profiles.add(new profile("Nom2", "Prenom2", "email2@example.com", "987654321", "002", "Catégorie2", "Gouvernorat2", "Ville2", "Adresse2", "123456789"));
        return profiles;
    }





}
