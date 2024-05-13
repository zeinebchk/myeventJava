package com.example.myevent.Models;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class signupA {
    private final StringProperty nom = new SimpleStringProperty(this, "nom");
    private final StringProperty prenom = new SimpleStringProperty(this, "prenom");
    private final StringProperty numTel = new SimpleStringProperty(this, "numTel");
    private final StringProperty email = new SimpleStringProperty(this, "email");
    private final StringProperty nomProjet = new SimpleStringProperty(this, "nomProjet");
    private final StringProperty categorie = new SimpleStringProperty(this, "categorie");
    private final StringProperty gouvernorat = new SimpleStringProperty(this, "gouvernorat");
    private final StringProperty ville = new SimpleStringProperty(this, "ville");
    private final StringProperty adresseExacte = new SimpleStringProperty(this, "adresseExacte");
    private final StringProperty password = new SimpleStringProperty(this, "password");

    // Getters and Setters
    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getNumTel() {
        return numTel.get();
    }

    public StringProperty numTelProperty() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel.set(numTel);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getNomProjet() {
        return nomProjet.get();
    }

    public StringProperty nomProjetProperty() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet.set(nomProjet);
    }

    public String getCategorie() {
        return categorie.get();
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }

    public String getGouvernorat() {
        return gouvernorat.get();
    }

    public StringProperty gouvernoratProperty() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat.set(gouvernorat);
    }

    public String getVille() {
        return ville.get();
    }

    public StringProperty villeProperty() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public String getAdresseExacte() {
        return adresseExacte.get();
    }

    public StringProperty adresseExacteProperty() {
        return adresseExacte;
    }

    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte.set(adresseExacte);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}


