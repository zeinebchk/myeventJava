package com.example.myevent.entities;

public class OffreAPayer {
    private static OffreAPayer instance;
    private Reservation reservation;

    private OffreAPayer() {
        // Private constructor to prevent instantiation
    }

    public static OffreAPayer getInstance() {
        if (instance == null) {
            instance = new OffreAPayer();
        }
        return instance;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation res) {
        this.reservation = res;
    }

    public void clearReservation() {
        this.reservation = null;
    }

    public boolean isLoggedIn() {
        return reservation != null;
    }
}
