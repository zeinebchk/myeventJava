package com.example.myevent.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class signupA {
    private final StringProperty nom = new SimpleStringProperty();
    private final StringProperty prenom = new SimpleStringProperty();
    private final StringProperty numTel = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty nomProjet = new SimpleStringProperty();
    private final StringProperty categorie = new SimpleStringProperty();
    private final StringProperty gouvernorat = new SimpleStringProperty();
    private final StringProperty ville = new SimpleStringProperty();
    private final StringProperty adresseExacte = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    // Constructor
    public signupA() {
    }

    // Getter and setter methods for nom
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    // Getter and setter methods for prenom
    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    // Getter and setter methods for numTel
    public String getNumTel() {
        return numTel.get();
    }

    public void setNumTel(String numTel) {
        this.numTel.set(numTel);
    }

    public StringProperty numTelProperty() {
        return numTel;
    }

    // Getter and setter methods for email
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    // Getter and setter methods for nomProjet
    public String getNomProjet() {
        return nomProjet.get();
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet.set(nomProjet);
    }

    public StringProperty nomProjetProperty() {
        return nomProjet;
    }

    // Getter and setter methods for categorie
    public String getCategorie() {
        return categorie.get();
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    // Getter and setter methods for gouvernorat
    public String getGouvernorat() {
        return gouvernorat.get();
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat.set(gouvernorat);
    }

    public StringProperty gouvernoratProperty() {
        return gouvernorat;
    }

    // Getter and setter methods for ville
    public String getVille() {
        return ville.get();
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public StringProperty villeProperty() {
        return ville;
    }

    // Getter and setter methods for adresseExacte
    public String getAdresseExacte() {
        return adresseExacte.get();
    }

    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte.set(adresseExacte);
    }

    public StringProperty adresseExacteProperty() {
        return adresseExacte;
    }

    // Getter and setter methods for password
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
