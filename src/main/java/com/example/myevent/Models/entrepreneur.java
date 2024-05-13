package com.example.myevent.Models;



public class entrepreneur {
    private String ID_User;
    private String Projet;
    private String Categorie;
    private String Gouvernerat;
    private String Ville;
    private String Adresse_Exacte;
    private String NumTelPro;

    // Constructeur
    public entrepreneur(String ID_User, String Projet, String Categorie, String Gouvernerat, String Ville, String Adresse_Exacte, String NumTelPro) {
        this.ID_User = ID_User;
        this.Projet = Projet;
        this.Categorie = Categorie;
        this.Gouvernerat = Gouvernerat;
        this.Ville = Ville;
        this.Adresse_Exacte = Adresse_Exacte;
        this.NumTelPro = NumTelPro;
    }

    // Getters et Setters
    public String getID_User() {
        return ID_User;
    }

    public void setID_User(String ID_User) {
        this.ID_User = ID_User;
    }

    public String getProjet() {
        return Projet;
    }

    public void setProjet(String Projet) {
        this.Projet = Projet;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String Categorie) {
        this.Categorie = Categorie;
    }

    public String getGouvernerat() {
        return Gouvernerat;
    }

    public void setGouvernerat(String Gouvernerat) {
        this.Gouvernerat = Gouvernerat;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String Ville) {
        this.Ville = Ville;
    }

    public String getAdresse_Exacte() {
        return Adresse_Exacte;
    }

    public void setAdresse_Exacte(String Adresse_Exacte) {
        this.Adresse_Exacte = Adresse_Exacte;
    }

    public String getNumTelPro() {
        return NumTelPro;
    }

    public void setNumTelPro(String NumTelPro) {
        this.NumTelPro = NumTelPro;
    }
}

