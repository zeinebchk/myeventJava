package com.example.myevent.entities;

public class UserSession {
    private static UserSession instance;
    private User user;

    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clearUser() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }
}
