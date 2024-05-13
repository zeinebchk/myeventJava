package com.example.myevent.entities;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

public class Offre {

    private BigInteger id;
    private String titre;
    private String description;
    private Double prixInitial;
    private Double prixRemise;
    private LocalDate dateFinRemise;

    public Offre(BigInteger id, String titre, String description, Double prixInitial, Double prixRemise, LocalDate dateFinRemise) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixRemise = prixRemise;
        this.dateFinRemise = dateFinRemise;
    }

    public Offre() {

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(Double prixInitial) {
        this.prixInitial = prixInitial;
    }

    public Double getPrixRemise() {
        return prixRemise;
    }

    public void setPrixRemise(Double prixRemise) {
        this.prixRemise = prixRemise;
    }


    public LocalDate getDateFinRemise() {
        return dateFinRemise;
    }

    public void setDateFinRemise(LocalDate dateFinRemise) {
        this.dateFinRemise = dateFinRemise;
    }
}

