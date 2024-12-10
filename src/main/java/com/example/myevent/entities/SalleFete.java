package com.example.myevent.entities;

public class SalleFete extends Offre {
    private int surface;
    private int capacitePersonne;
    private String gouvernerat;
    private String ville;
    private String adresseExacte;
    private double latitude; // Ajoutez le champ latitude
    private double longitude; // Ajoutez le champ longitude
    private String optionInclus;
    private String id;


    // Constructeur avec tous les paramètres nécessaires
    public SalleFete(int surface, int capacitePersonne, String gouvernerat, String ville, String adresseExacte, String optionInclus, String offreId) {
        super(offreId, "Nom"); // Remplacer "Nom" par le vrai nom de l'offre si vous avez
        this.surface = surface;
        this.capacitePersonne = capacitePersonne;
        this.gouvernerat = gouvernerat;
        this.ville = ville;
        this.adresseExacte = adresseExacte;
        this.latitude = latitude;
        this.longitude = longitude;
        this.optionInclus = optionInclus;
        this.id = offreId;
    }

    // Constructeur sans paramètres
    public SalleFete() {
        super("0", "Nom"); // Remplacer "Nom" par un nom par défaut, ou selon votre logique
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SalleFete{" +
                "surface=" + surface +
                ", capacitePersonne=" + capacitePersonne +
                ", gouvernerat='" + gouvernerat + '\'' +
                ", ville='" + ville + '\'' +
                ", adresseExacte='" + adresseExacte + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", optionInclus='" + optionInclus + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
