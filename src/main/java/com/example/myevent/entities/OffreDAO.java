package com.example.myevent.entities;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreDAO {

    private final Connection connection;

    public OffreDAO(Connection connection) {
        this.connection = connection;
    }

    // Méthode pour obtenir toutes les offres
    public ObservableList<Offre> getOffres() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String query = "SELECT * FROM offres"; // Assurez-vous d'ajuster la requête SQL selon votre base de données
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Créez un objet Offre à partir des résultats
                Offre offre = new Offre(rs.getInt("id"), rs.getString("nom"));
                offres.add(offre);
            }
        }
        return (ObservableList<Offre>) offres;
    }

    // Méthode pour ajouter une offre
    public void ajouterOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException {
        String query = "INSERT INTO offres (titre, description, prix_initial, prix_remise, date_fin_remise, entrepreneur_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, offre.getTitre());
            statement.setString(2, offre.getDescription());
            statement.setDouble(3, offre.getPrixInitial());
            statement.setDouble(4, offre.getPrixRemise());
            statement.setDate(5, java.sql.Date.valueOf(offre.getDateFinRemise()));
            statement.setInt(6, offre.getEntrepreneurId().intValue()); // Conversion explicite de BigInteger à int
            statement.executeUpdate();
        }
    }

    public void deleteOffre(Offre offre) throws SQLException {
        String query = "DELETE FROM offres WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Utiliser l'ID de l'offre comme un int
            statement.setInt(1, Integer.parseInt(offre.getId()));  // Assurez-vous que getId() retourne un int ou un BigInteger que vous convertissez en int
            statement.executeUpdate();
        }
    }


    // Méthode pour mettre à jour une offre
    public void updateOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException {
        String query = "UPDATE offres SET titre = ?, description = ?, prix_initial = ?, prix_remise = ?, date_fin_remise = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, offre.getTitre());
            statement.setString(2, offre.getDescription());
            statement.setDouble(3, offre.getPrixInitial());
            statement.setDouble(4, offre.getPrixRemise());
            statement.setDate(5, java.sql.Date.valueOf(offre.getDateFinRemise()));
            statement.setInt(6, Integer.parseInt(String.valueOf(offre.getId())));
            statement.executeUpdate();
        }
    }
}
