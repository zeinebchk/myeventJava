package com.example.myevent.entities;

import java.math.BigInteger;

public class Image extends Offre  {
    private BigInteger id;
    private BigInteger offreId;
    private String url;  // Ajout de la propriété 'url'

    // Constructeur avec tous les champs
    public Image(BigInteger offreId, String url) {

        this.offreId = offreId;

        this.url = url;
    }

    // Constructeur vide (non utilisé dans cet exemple)
    public Image() {
    }

    public Image(String imageFileName, BigInteger offreId) {
    }

    // Getters et Setters
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }



    public BigInteger getOffreId() {
        return offreId;
    }

    public void setOffreId(BigInteger offreId) {
        this.offreId = offreId;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
