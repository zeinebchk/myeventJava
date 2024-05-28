package com.example.myevent.entities;

import com.example.myevent.tools.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OffreDAO {
    private ObservableList<Offre> offres;
     Connection connection;
    public OffreDAO(Connection connection) {
        offres = FXCollections.observableArrayList(); // Initialisation de la liste ici
        this.connection = connection;
    }

    public ObservableList<Offre> getAllOffres() {
        try {
            Connection connection = Connexion.getInstance().getCnx(); // Obtenez la connexion à partir de Connexion
            System.err.println("mar7be.");

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM offre");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String titre = resultSet.getString("titre");
                String description = resultSet.getString("description");
                double prixInitial = resultSet.getDouble("prixInitial");
                double prixRemise = resultSet.getDouble("prixRemise");
                java.sql.Date dateFinRemiseSQL = resultSet.getDate("dateFinRemise");

                LocalDate dateFinRemise = null;
                if (dateFinRemiseSQL != null) {
                    dateFinRemise = dateFinRemiseSQL.toLocalDate();
                }
                System.out.println("ff");
                BigInteger offreId = BigInteger.valueOf(id);
                Offre offre = new Offre(offreId, titre, description, prixInitial, prixRemise, dateFinRemise);
                offres.add(offre);
                for (int i = 0; i < offres.size(); i++) {
                    System.out.println(offres.get(i));
                }
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des offres : " + e.getMessage());
        }

        return offres;
    }

    public void ajouterOffre(Offre offre, SalleFete salleFete, Image image) {
        try {
             connection = Connexion.getInstance().getCnx();
            String titre = offre.getTitre();
            String description = offre.getDescription();

            // Vérifier que le titre et la description ne sont pas vides
            if (titre == null || titre.trim().isEmpty() || description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("Le titre ou la description de l'offre est vide ou nul. L'ajout ne peut pas être effectué.");
            }

            double prixInitial = offre.getPrixInitial();
            double prixRemise = offre.getPrixRemise();
            LocalDate dateFinRemise = offre.getDateFinRemise();

          //  LocalDate dateFinRemise = offre.getDateFinRemise();
            java.sql.Date sqlDateFinRemise = java.sql.Date.valueOf(dateFinRemise);
            PreparedStatement offreStatement = connection.prepareStatement("INSERT INTO offre (titre, description, prixInitial, prixRemise, dateFinRemise) VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            offreStatement.setString(1, titre);
            offreStatement.setString(2, description); // Utiliser la description récupérée
            offreStatement.setDouble(3, prixInitial);
            offreStatement.setDouble(4, prixRemise);
            offreStatement.setDate(5, sqlDateFinRemise); // Utiliser la date SQL calculée

            offreStatement.executeUpdate();
            ResultSet generatedKeys = offreStatement.getGeneratedKeys();
            int offreId = -1;
            if (generatedKeys.next()) {
                offreId = generatedKeys.getInt(1);
            }

            PreparedStatement salleFeteStatement = connection.prepareStatement("INSERT INTO sallefete (surface, capacitePersonne, gouvernerat, ville, adresseExacte, latitude, longitude, optionInclus, offre_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            salleFeteStatement.setInt(1, salleFete.getSurface());
            salleFeteStatement.setInt(2, salleFete.getCapacitePersonne());
            salleFeteStatement.setString(3, salleFete.getGouvernerat());
            salleFeteStatement.setString(4, salleFete.getVille());
            salleFeteStatement.setString(5, salleFete.getAdresseExacte());
            salleFeteStatement.setDouble(6, salleFete.getLatitude()); // Insérer la latitude depuis l'objet salleFete
            salleFeteStatement.setDouble(7, salleFete.getLongitude()); // Insérer la longitude depuis l'objet salleFete
            salleFeteStatement.setString(8, salleFete.getOptionInclus());
            salleFeteStatement.setInt(9, offreId);
            salleFeteStatement.executeUpdate();

            PreparedStatement imageStatement = connection.prepareStatement("INSERT INTO image (url, offre_id) VALUES (?, ?)");
            imageStatement.setString(1, image.getUrl());
            imageStatement.setInt(2, offreId);
            imageStatement.executeUpdate();
            Image imageObject = new Image(image.getUrl());
            offreStatement.close();
            salleFeteStatement.close();
            imageStatement.close();

            System.out.println("L'offre a été ajoutée avec succès avec ID = " + offreId);
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'offre : " + e.getMessage());
        }
    }
}
