package com.example.myevent.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class entrepreneur {
    private final StringProperty Id_User;
    private final StringProperty projet;
    private final StringProperty Categorie;
    private final StringProperty Gouvernerat;
    private final StringProperty Ville;
    private final StringProperty Adresse_Exacte;
    private final StringProperty NumTelPro;

    // Constructeur
    public entrepreneur(String Id_User, String projet, String Categorie, String Gouvernerat, String Ville, String Adresse_Exacte, String NumTelPro) {
        this.Id_User = new SimpleStringProperty(Id_User);
        this.projet = new SimpleStringProperty(projet);
        this.Categorie = new SimpleStringProperty(Categorie);
        this.Gouvernerat = new SimpleStringProperty(Gouvernerat);
        this.Ville = new SimpleStringProperty(Ville);
        this.Adresse_Exacte = new SimpleStringProperty(Adresse_Exacte);
        this.NumTelPro = new SimpleStringProperty(NumTelPro);
    }

    // Getters et Setters pour les propriétés observables
    public String getId_User() {
        return Id_User.get();
    }

    public void setId_User(String Id_User) {
        this.Id_User.set(Id_User);
    }

    public StringProperty Id_UserProperty() {
        return Id_User;
    }

    public String getProjet() {
        return projet.get();
    }

    public void setProjet(String projet) {
        this.projet.set(projet);
    }

    public StringProperty projetProperty() {
        return projet;
    }

    public String getCategorie() {
        return Categorie.get();
    }

    public void setCategorie(String Categorie) {
        this.Categorie.set(Categorie);
    }

    public StringProperty CategorieProperty() {
        return Categorie;
    }

    public String getGouvernerat() {
        return Gouvernerat.get();
    }

    public void setGouvernerat(String Gouvernerat) {
        this.Gouvernerat.set(Gouvernerat);
    }

    public StringProperty GouverneratProperty() {
        return Gouvernerat;
    }

    public String getVille() {
        return Ville.get();
    }

    public void setVille(String Ville) {
        this.Ville.set(Ville);
    }

    public StringProperty VilleProperty() {
        return Ville;
    }

    public String getAdresse_Exacte() {
        return Adresse_Exacte.get();
    }

    public void setAdresse_Exacte(String Adresse_Exacte) {
        this.Adresse_Exacte.set(Adresse_Exacte);
    }

    public StringProperty Adresse_ExacteProperty() {
        return Adresse_Exacte;
    }

    public String getNumTelPro() {
        return NumTelPro.get();
    }

    public void setNumTelPro(String NumTelPro) {
        this.NumTelPro.set(NumTelPro);
    }

    public StringProperty NumTelProProperty() {
        return NumTelPro;
    }
}
