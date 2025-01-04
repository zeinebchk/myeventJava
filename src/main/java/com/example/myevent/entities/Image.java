package com.example.myevent.entities;

import java.math.BigInteger;

public class Image {
    private BigInteger id;
    private String offreId;
    private byte[] url;  // Propriété pour stocker le blob d'image

    // Constructeur par défaut
    public Image() {
        this.id = BigInteger.ZERO;  // Valeur par défaut pour id
        this.offreId = "defaultOffreId";  // Valeur par défaut pour offreId
        this.url = new byte[0];  // Valeur par défaut vide pour url
    }

    // Constructeur supplémentaire pour une image avec un fichier et une offre
    public Image(byte[] imageBlob, BigInteger offreId) {
        this.offreId = offreId != null ? offreId.toString() : "defaultOffreId";
        this.url = (imageBlob != null) ? imageBlob : new byte[0];
    }

    // Getters et Setters
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getOffreId() {
        return offreId;
    }

    public void setOffreId(String offreId) {
        this.offreId = offreId;
    }

    public byte[] getUrl() {
        return url;
    }

    public void setUrl(byte[] url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", offreId='" + offreId + '\'' +
                ", url=" + (url != null ? "[binary data]" : "null") +
                '}';
    }
}