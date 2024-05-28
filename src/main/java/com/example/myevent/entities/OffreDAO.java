package com.example.myevent.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;

public class OffreDAO {
    private ObservableList<Offre> offres;
    private Connection connection;

    public OffreDAO(Connection connection) {
        offres = FXCollections.observableArrayList();
        this.connection = connection;
        loadOffresData(); // Charger les offres au moment de la création de l'instance
    }

    public ObservableList<Offre> getAllOffres() {
        if (offres.isEmpty()) {
            loadOffresData();
        }
        return offres;
    }

    public void deleteOffre(Offre offre) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM offre WHERE id = ?")) {
                statement.setInt(1, offre.getId().intValue());
                statement.executeUpdate();
                offres.remove(offre); // Retirer l'offre de la liste après la suppression
            }
        } else {
            System.err.println("The database connection is null or closed.");
        }
    }

    public void updateOffre(Offre offre) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            // Mise à jour de l'offre dans la base de données
            String updateOffreQuery = "UPDATE offre SET titre = ?, description = ?, prixInitial = ?, prixRemise = ?, dateFinRemise = ?, entrepreneur_id = ? WHERE id = ?";
            try (PreparedStatement offreStatement = connection.prepareStatement(updateOffreQuery)) {
                offreStatement.setString(1, offre.getTitre());
                offreStatement.setString(2, offre.getDescription());
                offreStatement.setDouble(3, offre.getPrixInitial());
                offreStatement.setDouble(4, offre.getPrixRemise());
                offreStatement.setDate(5, java.sql.Date.valueOf(offre.getDateFinRemise()));
                offreStatement.setBigDecimal(6, new BigDecimal(offre.getEntrepreneurId()));
                offreStatement.setInt(7, offre.getId().intValue());
                offreStatement.executeUpdate();
            }

            // Mise à jour de la salle de fête, si elle existe
            if (offre.getSalleFete() != null) {
                // Code pour mettre à jour la salle de fête dans la base de données
            }

            // Mise à jour de l'image, si elle existe
            if (offre.getImage() != null) {
                // Code pour mettre à jour l'image dans la base de données
            }

            // Recharger les offres après la mise à jour
            loadOffresData();
        } else {
            System.err.println("The database connection is null or closed.");
        }
    }

    private void loadOffresData() {
        String query = "SELECT id, titre, description, prixInitial, prixRemise, dateFinRemise, entrepreneur_id FROM offre";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            offres.clear();
            while (resultSet.next()) {
                Offre offre = new Offre();
                offre.setId(BigInteger.valueOf(resultSet.getLong("id")));
                offre.setTitre(resultSet.getString("titre"));
                offre.setDescription(resultSet.getString("description"));
                offre.setPrixInitial(resultSet.getDouble("prixInitial"));
                offre.setPrixRemise(resultSet.getDouble("prixRemise"));
                offre.setDateFinRemise(resultSet.getDate("dateFinRemise").toLocalDate());
                offre.setEntrepreneurId(BigInteger.valueOf(resultSet.getLong("entrepreneur_id")));

                offres.add(offre);
            }
        } catch (SQLException e) {
            System.err.println("Error loading offer data: " + e.getMessage());
        }
    }

    public BigInteger ajouterOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try (PreparedStatement offreStatement = connection.prepareStatement("INSERT INTO offre (titre, description, prixInitial, prixRemise, dateFinRemise, entrepreneur_id) VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                offreStatement.setString(1, offre.getTitre());
                offreStatement.setString(2, offre.getDescription());
                offreStatement.setDouble(3, offre.getPrixInitial());
                offreStatement.setDouble(4, offre.getPrixRemise());
                offreStatement.setDate(5, java.sql.Date.valueOf(offre.getDateFinRemise()));
                offreStatement.setBigDecimal(6, new BigDecimal(offre.getEntrepreneurId()));

                offreStatement.executeUpdate();

                // Récupérer l'ID généré pour l'offre ajoutée
                ResultSet generatedKeys = offreStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    BigInteger offreId = BigInteger.valueOf(generatedKeys.getLong(1));
                    offre.setId(offreId);
                } else {
                    throw new SQLException("Failed to retrieve generated offre ID.");
                }
            }

            // Ajouter la salle de fête si elle n'est pas null
            if (salleFete != null) {
                // Code pour ajouter la salle de fête dans la base de données
            }

            // Ajouter l'image si elle n'est pas null
            if (image != null) {
                // Code pour ajouter l'image dans la base de données
            }

            // Ajouter l'offre, la salle de fête et l'image aux listes observables si nécessaire
            if (offre != null) {
                offres.add(offre);
            }
            // Ajouter d'autres éléments à la liste observable si nécessaire

        } else {
            System.err.println("The database connection is null or closed.");
        }
        return null;
    }
}
