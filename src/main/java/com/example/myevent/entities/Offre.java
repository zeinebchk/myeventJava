package com.example.myevent.entities;

import java.math.BigInteger;
import java.time.LocalDate;
public class Offre {
    private BigInteger id;
    private String titre;
    private String description;
    private double prixInitial;
    private double prixRemise;
    private LocalDate dateFinRemise;
    BigInteger entrepreneurId;

    public Offre(BigInteger id, String titre, String description, double prixInitial, double prixRemise, LocalDate dateFinRemise, BigInteger entrepreneurId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixRemise = prixRemise;
        this.dateFinRemise = dateFinRemise;

    }

    public Offre() {
        this.id = id;
        this.titre = "";
        this.description = "";
        this.prixInitial = 0.0;
        this.prixRemise = 0.0;
        this.dateFinRemise = LocalDate.now();

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

    public double getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(double prixInitial) {
        this.prixInitial = prixInitial;
    }

    public double getPrixRemise() {
        return prixRemise;
    }

    public void setPrixRemise(double prixRemise) {
        this.prixRemise = prixRemise;
    }

    public LocalDate getDateFinRemise() {
        return dateFinRemise;
    }

    public void setDateFinRemise(LocalDate dateFinRemise) {
        this.dateFinRemise = dateFinRemise;
    }

    public BigInteger getEntrepreneurId() {

        return entrepreneurId;
    }

    public void setEntrepreneurId(BigInteger entrepreneurId) {
        this.entrepreneurId = entrepreneurId;
    }

}