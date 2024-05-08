package com.example.myevent.entities;

import java.math.BigInteger;

public class Entrepreneur extends User{
    private BigInteger id;
    private String nomProjet;
    private String categorie;
    private String gouvernerat;
    private String ville;
    private String adresseExacte;
    private int numTelPro;
    private double latitude;
    private double longitude;

    public Entrepreneur(){};
    public Entrepreneur(BigInteger id, String nom, String prenom, String email, String password, int numTel,
                        String genre, String image, BigInteger id2, String nomProjet, String categorie, String gouvernerat,
                        String ville, String adresseExacte, int numTelPro, double latitude, double longitude) {
        super(id, nom, prenom, email, password, numTel, genre, image);
        id = id2;
        this.nomProjet = nomProjet;
        this.categorie = categorie;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.numTelPro = numTelPro;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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

    public int getNumTelPro() {
        return numTelPro;
    }

    public void setNumTelPro(int numTelPro) {
        this.numTelPro = numTelPro;
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


}
