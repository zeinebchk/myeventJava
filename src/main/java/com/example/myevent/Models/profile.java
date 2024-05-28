package com.example.myevent.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class profile {
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty numTel;
    private final StringProperty nomProjet;
    private final StringProperty categorie;
    private final StringProperty gouvernorat;
    private final StringProperty ville;
    private final StringProperty adresseExacte;
    private final StringProperty password;

    // Constructor
    // Constructor
    public profile(String nom, String prenom, String email, String numTel, String nomProjet, String categorie,
                   String gouvernorat, String ville, String adresseExacte, String password) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.numTel = new SimpleStringProperty(numTel);
        this.nomProjet = new SimpleStringProperty(nomProjet);
        this.categorie = new SimpleStringProperty(categorie);
        this.gouvernorat = new SimpleStringProperty(gouvernorat);
        this.ville = new SimpleStringProperty(ville);
        this.adresseExacte = new SimpleStringProperty(adresseExacte);
        this.password = new SimpleStringProperty(password);
    }


    // Getters and Setters
    public StringProperty nomProperty() {
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty numTelProperty() {
        return numTel;
    }

    public String getNumTel() {
        return numTel.get();
    }

    public void setNumTel(String numTel) {
        this.numTel.set(numTel);
    }

    public StringProperty nomProjetProperty() {
        return nomProjet;
    }

    public String getNomProjet() {
        return nomProjet.get();
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet.set(nomProjet);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public StringProperty gouvernoratProperty() {
        return gouvernorat;
    }

    public String getGouvernorat() {
        return gouvernorat.get();
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat.set(gouvernorat);
    }

    public StringProperty villeProperty() {
        return ville;
    }

    public String getVille() {
        return ville.get();
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public StringProperty adresseExacteProperty() {
        return adresseExacte;
    }

    public String getAdresseExacte() {
        return adresseExacte.get();
    }

    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte.set(adresseExacte);
    }



    public StringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    // Method to generate sample profiles (replace with your own data)
    public static ObservableList<profile> getSampleProfiles() {
        ObservableList<profile> profiles = FXCollections.observableArrayList();
        profiles.add(new profile("nom1", "prenom1", "email1@example.com", "123456789", "001", "catégorie1", "gouvernorat1", "ville1", "adresse1", "password1"));
        profiles.add(new profile("nom2", "prenom2", "email2@example.com", "987654321", "002", "catégorie2", "gouvernorat2", "ville2", "adresse2", "password2"));

        return profiles;
    }
}
