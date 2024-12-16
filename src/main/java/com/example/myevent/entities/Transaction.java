package com.example.myevent.entities;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;


public class Transaction {
   private User client;
   private SalleFete salle;
   private Reservation reservation;


    public Transaction(User client, SalleFete salle, Reservation reservation) {
        this.client = client;
        this.salle = salle;
        this.reservation = reservation;
    }

    public User getClient() {
        return client;
    }

    public SalleFete getSalle() {
        return salle;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setSalle(SalleFete salle) {
        this.salle = salle;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setClient(User client) {
        this.client = client;
    }


    // Méthodes pour TableView
    public String getNom() {
        return client != null ? client.getNom() : "";
    }

    public String getPrenom() {
        return client != null ? client.getPrenom() : "";
    }

    public String getEmail() {
        return client != null ? client.getEmail() : "";
    }

    public Date getDateReservation() {
        return reservation != null ? reservation.getDateReservation() : null;
    }

    public String getTitreSalle() {
        return salle != null ? salle.getTitre() : "";
    }

    public Double getPrixSalle() {
        return salle != null ? salle.getPrixInitial() : 0.0;
    }

    public String getStatutReservation() {
        return reservation != null ? reservation.getStatus() : "";
    }

    public Integer getAvance() {
        return reservation != null ? reservation.getAvanceClient() : 0;
    }





    @Override
    public String toString() {
        return "Transaction{" +
                "client=" + client.toString() +
                ", salle=" + salle.toString() +
                ", reservation=" + reservation.toString() +
                '}';
    }
}