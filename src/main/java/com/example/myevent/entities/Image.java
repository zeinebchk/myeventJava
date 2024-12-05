package com.example.myevent.entities;

import java.math.BigInteger;

public class Image extends Offre  {
    private BigInteger id;
    private String imageFileName;
    private BigInteger offreId;
    private String imageURL;
    private String url;  // Ajout de la propriété 'url'

    // Constructeur avec tous les champs
    public Image(String imageFileName, BigInteger offreId, String imageURL, String url) {
        super();
        this.imageFileName = imageFileName;
        this.offreId = offreId;
        this.imageURL = imageURL;
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

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public BigInteger getOffreId() {
        return offreId;
    }

    public void setOffreId(BigInteger offreId) {
        this.offreId = offreId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}