package com.example.myevent.entities;

public class OffreSession {
    private static OffreSession instance;
    private SalleFete salle;

    private OffreSession() {
        // Private constructor to prevent instantiation
    }

    public static OffreSession getInstance() {
        if (instance == null) {
            instance = new OffreSession();
        }
        return instance;
    }

    public SalleFete getSalle() {
        return salle;
    }

    public void setSalle(SalleFete salle) {
        this.salle = salle;
    }

    public void clearUser() {
        this.salle = null;
    }

    public boolean isLoggedIn() {
        return salle != null;
    }
}
