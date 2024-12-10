package com.example.myevent.entities;

import java.math.BigInteger;

public class Image extends Offre {
    private String id;
    private String offreId;
    private String url;  // Ajout de la propriété 'url'

    // Constructeur par défaut sans 'ResultSet' (pas besoin de rs)
    public Image() {
        super(Integer.parseInt(String.valueOf(Integer.parseInt(null))), "Nom");  // Remplacer "Nom" par le nom de l'offre si nécessaire
        this.id = id;
        this.offreId = offreId;
        this.url = url;
    }

    // Constructeur supplémentaire pour une image avec un fichier et une offre
    public Image(String imageFileName, BigInteger offreId) {
        super(offreId.toString(), "Nom");  // Remplacer "Nom" par le vrai nom si nécessaire
        // Si vous souhaitez définir des valeurs par défaut ou effectuer une action spécifique avec le nom du fichier image, faites-le ici
        this.url = imageFileName;  // Exemple, vous pouvez utiliser imageFileName comme URL
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
                "id='" + id + '\'' +
                ", offreId='" + offreId + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
