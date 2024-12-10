package com.example.myevent.entities;

import java.math.BigInteger;
import java.time.LocalDate;

public class Offre {
    private String titre;
    private String description;
    private double prixInitial;
    private double prixRemise;
    private LocalDate dateFinRemise;
    public BigInteger entrepreneurId;
    private byte[] blobData; // Données binaires (par exemple, pour une image)
    private String id;

    // Getters et setters avec validations

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être null ou vide.");
        }
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description; // Non obligatoire, aucune validation nécessaire
    }

    public double getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(double prixInitial) {
        if (prixInitial < 0) {
            throw new IllegalArgumentException("Le prix initial ne peut pas être négatif.");
        }
        this.prixInitial = prixInitial;
    }

    public double getPrixRemise() {
        return prixRemise;
    }

    public void setPrixRemise(double prixRemise) {
        if (prixRemise < 0 || prixRemise > prixInitial) {
            throw new IllegalArgumentException("Le prix de remise doit être compris entre 0 et le prix initial.");
        }
        this.prixRemise = prixRemise;
    }

    public LocalDate getDateFinRemise() {
        return dateFinRemise;
    }

    public void setDateFinRemise(LocalDate dateFinRemise) {
        if (dateFinRemise != null && dateFinRemise.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de fin de remise ne peut pas être dans le passé.");
        }
        this.dateFinRemise = dateFinRemise;
    }

    public BigInteger getEntrepreneurId() {
        return entrepreneurId;
    }

    public void setEntrepreneurId(BigInteger entrepreneurId) {
        if (entrepreneurId == null) {
            throw new IllegalArgumentException("L'ID de l'entrepreneur ne peut pas être null.");
        }
        this.entrepreneurId = entrepreneurId;
    }

    public byte[] getBlobData() {
        return blobData;
    }

    public void setBlobData(byte[] blobData) {
        this.blobData = blobData; // Aucune validation nécessaire ici
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID ne peut pas être null ou vide.");
        }
        this.id = id;
    }

    // Constructeur par défaut pour permettre l'instanciation sans paramètres
    public Offre(int id, String nom) {}

    // Constructeur avec paramètres essentiels
    public Offre(String titre, String description) {
        setTitre(titre);
        setPrixInitial(prixInitial);
        setEntrepreneurId(entrepreneurId);
    }
}
