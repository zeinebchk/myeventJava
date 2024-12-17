package com.example.myevent.entities;

import java.math.BigInteger;

public class Image extends Offre {
    private BigInteger id;
    private String offreId;
    private String url;  // Propriété 'url' pour stocker l'URL de l'image

    // Constructeur par défaut
    public Image() {
        super("0", "Nom");  // Remplacer "Nom" par le nom de l'offre si nécessaire
        this.id = BigInteger.ZERO;  // Valeur par défaut pour id
        this.offreId = "defaultOffreId";  // Valeur par défaut pour offreId
        this.url = "";  // Valeur par défaut vide pour url
    }

    // Constructeur supplémentaire pour une image avec un fichier et une offre
    public Image(String imageFileName, BigInteger offreId) {
        super(offreId != null ? offreId.toString() : "defaultId", "Nom");  // Convertir offreId en String
        this.url = (imageFileName != null && !imageFileName.isEmpty()) ? imageFileName : "defaultImageUrl";
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", offreId='" + offreId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
