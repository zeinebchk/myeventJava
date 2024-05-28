package com.example.myevent.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class client {
    private final StringProperty id;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty genre;


    // Constructeur
    public client(String id, String nom, String prenom, String email, String genre) {
        this.id = new SimpleStringProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.genre = new SimpleStringProperty(genre);

    }

    // Getters et Setters pour les propriétés observables
    public String getid() {
        return id.get();
    }

    public void setid(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getnom() {
        return nom.get();
    }

    public void setnom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getprenom() {
        return prenom.get();
    }

    public void setprenom(String prenom) {
        this.prenom.set(prenom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getemail() {
        return email.get();
    }

    public void setemail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getgenre() {
        return genre.get();
    }

    public void setgenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }
}

