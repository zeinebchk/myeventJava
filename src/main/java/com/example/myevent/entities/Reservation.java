package com.example.myevent.entities;

import java.math.BigInteger;
import java.time.LocalDate;

public class Reservation {
    private BigInteger id;
    private String status;
    private String heureDebut;
    private String heureFin;
    private LocalDate dateReservation;
    private double avanceClient;
    private Offre offre; // Changer le nom de la variable
    private User user;
    private Offre offre_id;
    private Offre offreId;

    public Reservation(BigInteger id, String status, String heureDebut, String heureFin, LocalDate dateReservation, double avanceClient, Offre offre, User user) {
        this.id = id;
        this.status = status;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.dateReservation = dateReservation;
        this.avanceClient = avanceClient;
        this.offre = offre;
        this.user = user;
    }

    public Reservation() {

    }

    // Getters and setters
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public double getAvanceClient() {
        return avanceClient;
    }

    public void setAvanceClient(double avanceClient) {
        this.avanceClient = avanceClient;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offre getOffre_id() {
        Offre offre_id;
        
        return offreId;
    }

    public void setOffre_id(Offre offreId) {
        this.offre_id = offreId;
    }
}
