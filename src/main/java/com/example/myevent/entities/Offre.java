package com.example.myevent.entities;

import java.math.BigInteger;
import java.util.Date;

public class Offre {
    private BigInteger id;
    private Entrepreneur entrepreneur_id;
    private String titre;
    private String description;
    private Double prixInitial;
    private Double prixRemise;
    private Date dateFinRemise;

    public Offre() {}

    public Offre(Entrepreneur entrepreneur_id, String titre, String description, Double prixInitial, Double prixRemise,
                 Date dateFinRemis) {
        super();
        this.entrepreneur_id = entrepreneur_id;
        this.titre = titre;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixRemise = prixRemise;
        this.dateFinRemise = dateFinRemis;
    }
    public Entrepreneur getEntrepreneur_id() {
        return entrepreneur_id;
    }
    public String getTitre() {
        return titre;
    }
    public BigInteger getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }

    public Double getPrixInitial() {
        return prixInitial;
    }
    public Double getPrixRemise() {
        return prixRemise;
    }
    public Date getDateFinRemis() {
        return dateFinRemise;
    }

    public void setEntrepreneur_id(Entrepreneur entrepreneur_id) {
        this.entrepreneur_id = entrepreneur_id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrixInitial(Double prixInitial) {
        this.prixInitial = prixInitial;
    }

    public void setPrixRemise(Double prixRemise) {
        this.prixRemise = prixRemise;
    }

    public void setDateFinRemis(Date dateFinRemise) {
        this.dateFinRemise = dateFinRemise;
    }



}
