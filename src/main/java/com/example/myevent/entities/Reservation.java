package com.example.myevent.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;

public class Reservation {
    private BigInteger id;
    private String status;
    private Time heureDebut;
    private Time heureFin;
    private Date dateReservation;
    private int avanceClient;
    private Offre offre_id;
    private User client_id;

    public Reservation() {
        super();
    }
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
    public Time getHeureDebut() {
        return heureDebut;
    }
    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }
    public Time getHeureFin() {
        return heureFin;
    }
    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }
    public Date getDateReservation() {
        return dateReservation;
    }
    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
    public int getAvanceClient() {
        return avanceClient;
    }
    public void setAvanceClient(int avanceClient) {
        this.avanceClient = avanceClient;
    }
    public Offre getOffre_id() {
        return offre_id;
    }
    public void setOffre_id(Offre offre_id) {
        this.offre_id = offre_id;
    }
    public User getClient_id() {
        return client_id;
    }
    public void setClient_id(User client_id) {
        this.client_id = client_id;
    }
}
