package com.example.myevent.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class SalleFete extends Offre {
    private int surface;
    private int capacitePersonne;
    private String gouvernerat;
    private String ville;
    private String adresseExacte;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String optionInclus;

   public SalleFete(){}
    public SalleFete(Entrepreneur entrepreneur_id, String titre, String description, Double prixInitial, Double prixRemise,
                     Date dateFinRemise, int surface, int capacitePersonne, String gouvernerat, String ville,
                     String adresseExacte, BigDecimal latitude, BigDecimal longitude, String optionInclus) {
        super(entrepreneur_id, titre, description, prixInitial, prixRemise, dateFinRemise);
        this.surface = surface;
        this.capacitePersonne = capacitePersonne;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionInclus = optionInclus;
    }

    public int getSurface() {
        return surface;
    }


    public int getCapacitePersonne() {
        return capacitePersonne;
    }


    public String getGouvernerat() {
        return gouvernerat;
    }


    public String getVille() {
        return ville;
    }


    public String getAdresseExacte() {
        return adresseExacte;
    }




    public BigDecimal getLatitude() {
        return latitude;
    }


    public BigDecimal getLongitude() {
        return longitude;
    }


    public String getOptionInclus() {
        return optionInclus;
    }


    public void setSurface(int surface) {
        this.surface = surface;
    }


    public void setCapacitePersonne(int capacitePersonne) {
        this.capacitePersonne = capacitePersonne;
    }


    public void setGouvernerat(String gouvernerat) {
        this.gouvernerat = gouvernerat;
    }


    public void setVille(String ville) {
        this.ville = ville;
    }


    public void setAdresseExacte(String adresseExacte) {
        this.adresseExacte = adresseExacte;
    }


    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }


    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }


    public void setOptionInclus(String optionInclus) {
        this.optionInclus = optionInclus;
    }
    @Override
    public String toString() {
        return "SalleFete [surface=" + surface + ", capacitePersonne=" + capacitePersonne + ", gouvernerat="
                + gouvernerat + ", ville=" + ville + ", adresseExacte=" + adresseExacte + ", latitude=" + latitude
                + ", longitude=" + longitude + ", optionInclus=" + optionInclus + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalleFete salleFete = (SalleFete) o;
        return Objects.equals(this.getTitre(), salleFete.getTitre());
    }

}
