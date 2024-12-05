package com.example.myevent.entities;

import java.math.BigInteger;

public class SalleFete extends Offre {
    private int surface;
    private int capacitePersonne;
    private String gouvernerat;
    private String ville;
    private String adresseExacte;
    private double latitude;
    private double longitude;
    private String optionInclus;

    public SalleFete(int surface, int capacitePersonne, String gouvernerat, String ville, String adresseExacte,
                     String optionInclus, BigInteger offreId) {
        // Appel du constructeur de la classe parente (Offre)
        super();
        this.surface = surface;
        this.capacitePersonne = capacitePersonne;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionInclus = optionInclus;
    }

    public SalleFete() {
        super();  // Constructeur par défaut
    }

    // Getters et setters pour les attributs
    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getCapacitePersonne() {
        return capacitePersonne;
    }

    public void setCapacitePersonne(int capacitePersonne) {
        this.capacitePersonne = capacitePersonne;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOptionInclus() {
        return optionInclus;
    }

    public void setOptionInclus(String optionInclus) {
        this.optionInclus = optionInclus;
    }

    // Getters et setters pour l'id
    public BigInteger getId() {
        return super.getId();
    }

    public void setId(BigInteger id) {
        super.setId(id);
    }
}
