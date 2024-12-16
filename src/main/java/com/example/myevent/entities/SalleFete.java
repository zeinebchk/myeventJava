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

    // Constructor with all parameters
    public SalleFete(BigInteger id, String nom, int surface, int capacitePersonne, String gouvernerat, String ville, String adresseExacte, double latitude, double longitude, String optionInclus) {
        super(id, nom);
        this.surface = surface;
        this.capacitePersonne = capacitePersonne;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionInclus = optionInclus;
    }

    // Constructor with essential parameters
    public SalleFete(BigInteger id, String nom, int surface, int capacitePersonne, String gouvernerat, String ville, String adresseExacte, String optionInclus) {
        this(id, nom, surface, capacitePersonne, gouvernerat, ville, adresseExacte, 0.0, 0.0, optionInclus);
    }

    // Constructor matching the error signature
    public SalleFete(int surface, int capacitePersonne, String gouvernerat, String ville, String adresseExacte, String optionInclus, BigInteger id) {
        super(id, "Default Nom");
        this.surface = surface;
        this.capacitePersonne = capacitePersonne;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.optionInclus = optionInclus;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return super.toString() + "SalleFete{" +
                "surface=" + surface +
                ", capacitePersonne=" + capacitePersonne +
                ", gouvernerat='" + gouvernerat + '\'' +
                ", ville='" + ville + '\'' +
                ", adresseExacte='" + adresseExacte + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", optionInclus='" + optionInclus + '\'' +
                '}';
    }
}
