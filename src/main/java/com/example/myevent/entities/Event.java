package com.example.myevent.entities;

import java.sql.Time;
import java.time.LocalDate;

public class Event {
    private long id;  // Assuming 'id' is used as a primary key and is a bigint in the database
    private String titre;  // User's name
    private LocalDate dateEvent;  // User's email address
    private Time heuredebutEvent;
    private Time heureFinEvent;//
    private Integer nbInvites;
    private String gouvernerat;
    private String ville;// User's password
    private String adresseExacte;// User's password

    // Default constructor
    public Event() {
    }

    // Constructor with all parameters
    public Event(long id, String titre, LocalDate dateEvent, Time heuredebutEvent, Time heureFinEvent, Integer nbInvites, String gouvernerat, String ville, String adresseExacte) {
        this.id = id;
        this.titre = titre;
        this.dateEvent = dateEvent;
        this.heuredebutEvent = heuredebutEvent;
        this.heureFinEvent = heureFinEvent;
        this.nbInvites = nbInvites;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
    }


    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public void setdateEvent(LocalDate date_event) {
        this.dateEvent = dateEvent;
    }

    public Time getHeureDebutEvent() {
        return heuredebutEvent;
    }


    public void setHeureDebutEvent(Time heuredebutEvent) {
        this.heuredebutEvent = heuredebutEvent;
    }

    public Time getHeureFinEvent() {
        return heureFinEvent;
    }

    public void setheureFinEvent(Time heureFinEvent) {
        this.heureFinEvent = heureFinEvent;
    }

    public Integer getNbInvites() {
        return nbInvites;
    }

    public void setNbInvites(Integer nb_invites) {
        this.nbInvites = nbInvites;
    }

    public String getGouvernerat() {
        return gouvernerat;
    }


    public void setGouvernerat(String gouvernerat) {
        this.gouvernerat = gouvernerat;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresseExacte() {
        return adresseExacte;
    }

    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte = adresseExacte;
    }

    // Optional: ToString method for debugging purposes
    @Override
    public String toString() {
        return "events{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", dateEvent='" + dateEvent + '\'' +
                ", heuredebutEvent='" + heuredebutEvent + '\'' +
                ", heureFinEvent='" + heureFinEvent + '\'' +
                ", nb_invites='" + nbInvites + '\'' +
                ", gouvernorate='" + gouvernerat + '\'' +
                ", ville='" + ville + '\'' +
                ",adresseExacte ='" + adresseExacte + '\'' +
                '}';
    }



}
