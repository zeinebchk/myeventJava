package com.example.myevent.entities;

import java.math.BigInteger;

public class User {
        private BigInteger id;
        private String nom;
        private String prenom;
        private String email;
        private String password;
        private int numTel;
        private String genre;
        private String image;

        public User() {
        }
        public User(BigInteger id, String nom, String prenom, String email, String password, int numTel, String genre,
                    String image) {
                super();
                this.id = id;
                this.nom = nom;
                this.prenom = prenom;
                this.email = email;
                this.password = password;
                this.numTel = numTel;
                this.genre = genre;
                this.image = image;
        }

        public BigInteger getId() {
                return id;
        }

        public void setId(BigInteger idEntrepreneur) {
                this.id = idEntrepreneur;
        }

        public String getNom() {
                return nom;
        }

        public void setNom(String nom) {
                this.nom = nom;
        }

        public String getPrenom() {
                return prenom;
        }

        public void setPrenom(String prenom) {
                this.prenom = prenom;
        }

        public String getEmail() {
                return this.email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public int getNumTel() {
                return numTel;
        }

        public void setNumTel(int numTel) {
                this.numTel = numTel;
        }

        public String getGenre() {
                return genre;
        }

        public void setGenre(String genre) {
                this.genre = genre;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        @Override
        public String toString() {
                return "User{" +
                        "id=" + id +
                        ", nom='" + nom + '\'' +
                        ", prenom='" + prenom + '\'' +
                        ", email='" + email + '\'' +
                        ", password='" + password + '\'' +
                        ", numTel=" + numTel +
                        ", genre='" + genre + '\'' +
                        ", image='" + image + '\'' +
                        '}';
        }
}
