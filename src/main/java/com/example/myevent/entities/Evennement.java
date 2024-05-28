package com.example.myevent.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Evennement {
    private BigInteger id;
    private String titre;
    private Date dateEvent;
    private Time heuredebutEvent;
    private Time heureFinEvent;
    private int nbInvites;
    private String gouvernerat;
    private String ville;
    private String adresseExacte;
    private User client_id;

    public Evennement() {
        super();
    }
    public Evennement(BigInteger id, String titre, Date dateEvent, Time heuredebutEvent, Time heureFinEvent, Integer nbInvites, String gouvernerat, String ville, String adresseExacte) {
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
    public void setId(BigInteger id) {
        this.id = id;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }
    public void setHeuredebutEvent(Time heuredebutEvent) {
        this.heuredebutEvent = heuredebutEvent;
    }
    public void setHeureFinEvent(Time heureFinEvent) {
        this.heureFinEvent = heureFinEvent;
    }
    public void setNbInvites(int nbInvites) {
        this.nbInvites = nbInvites;
    }
    public void setGouvernerat(String gouvernerat) {
        this.gouvernerat = gouvernerat;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte = adresseExacte;
    }
    public void setClient_id(User client_id) {
        this.client_id = client_id;
    }
    public String getTitre() {
        return titre;
    }

    public Date getDateEvent() {
        return dateEvent;
    }
    public Time getHeuredebutEvent() {
        return heuredebutEvent;
    }
    public Time getHeureFinEvent() {
        return heureFinEvent;
    }
    public int getNbInvites() {
        return nbInvites;
    }
    public String getGouvernerat() {
        return gouvernerat;
    }
    public String getVille() {
        return ville;
    }
    public String getAdresseExacte() {
        return adresseExacte;
    }
    public User getClient_id() {
        return client_id;
    }
    public BigInteger getId() {
        return id;
    }


}
