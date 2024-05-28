package com.example.myevent.entities;

public class EvennementSession {
    private static EvennementSession instance;
    private Evennement event;

    private EvennementSession() {
        // Private constructor to prevent instantiation
    }

    public static EvennementSession getInstance() {
        if (instance == null) {
            instance = new EvennementSession();
        }
        return instance;
    }

    public Evennement getEvent() {
        return event;
    }

    public void setEvent(Evennement event) {
        this.event = event;
    }

    public void clearUser() {
        this.event = null;
    }

    public boolean isLoggedIn() {
        return event != null;
    }
}
