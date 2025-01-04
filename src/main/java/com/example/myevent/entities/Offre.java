package com.example.myevent.entities;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Blob;
import java.time.LocalDate;

public class Offre {
    public BigInteger id;
    private String titre;
    private String description;
    private double prixInitial;
    private double prixRemise;
    private LocalDate dateFinRemise;
    private BigInteger entrepreneurId;
    private Image image;

    public Offre(String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prixInitial = prixInitial;
        this.prixRemise = prixRemise;
        this.dateFinRemise = dateFinRemise;
        this.entrepreneurId = entrepreneurId;

    }

    public Offre(BigInteger entrepreneurId, String nom) {
        this.entrepreneurId = entrepreneurId;
    }
    public Offre(){

    }
    // Constructeur sans arguments avec des valeurs par défaut
    public Offre(int id, String nom) {
        this.id = BigInteger.ZERO;
        this.titre = "";
        this.description = "";
        this.prixInitial = 0.0;
        this.prixRemise = 0.0;
        this.dateFinRemise = LocalDate.now();
        this.entrepreneurId = BigInteger.ZERO;
    }

    public Offre(BigInteger id, String titre, String desription, double prixInitial) {
     this.id = id;
     this.titre = titre;
     this.description = desription;
     this.prixInitial = prixInitial;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public Offre(BigInteger id, String titre, String description, double prixInitial, double prixRemise, LocalDate dateFinRemise) {
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



    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", prixInitial=" + prixInitial +
                ", prixRemise=" + prixRemise +
                ", dateFinRemise=" + dateFinRemise +
                ", entrepreneurId=" + entrepreneurId +
                ", image=" + image +
                '}';
    }

}
