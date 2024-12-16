package com.example.myevent.entities;

import com.example.myevent.tools.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreDAO {

    private BigInteger entrepreneur_id;
    PreparedStatement st = null;
    int rs ;
    Connection con = Connexion.getInstance().getCnx();


    // Méthode pour ajouter une offre
    public void ajouterOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException, IOException {
        // Démarrer la transaction
      //  connection.setAutoCommit(false);
        try {
            String query = "INSERT INTO offre (titre, description, prixInitial, prixRemise, entrepreneur_id) VALUES (?, ?, ?, ?, ?)";
            st=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

                st.setString(1, offre.getTitre());
                st.setString(2, offre.getDescription());
                st.setDouble(3, offre.getPrixInitial());
                st.setDouble(4, offre.getPrixRemise());
                st.setObject(5, entrepreneur_id);   // Utiliser l'ID de l'entrepreneur ici

                rs = st.executeUpdate();
                if (rs > 0) {
                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        BigInteger offreId = rs.getBigDecimal(1).toBigInteger();
                        offre.setId(offreId);
                    } else {
                        throw new SQLException("Échec de la récupération de l'ID généré de l'offre.");
                    }
                }
            // 2. Insertion de l'image si elle est fournie
            if (image != null) {
                String imageQuery = "INSERT INTO image (offre_id, url) VALUES (?, ?)";
                PreparedStatement stmt2 = con.prepareStatement(imageQuery,Statement.RETURN_GENERATED_KEYS);
                    stmt2.setInt(1, offre.getId().intValue());
                    String imageUrl = "blob:" + image.getId();
                    stmt2.setString(2, imageUrl);
                    stmt2.executeUpdate();
            }

            // 3. Insertion dans la table "salle_fete" si elle est fournie
            if (salleFete != null) {
                String salleFeteQuery = "INSERT INTO sallefete (offre_id, surface, capacitePersonne, gouvernerat, ville, adresseExacte, latitude, longitude, optionInclus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement st3=con.prepareStatement(salleFeteQuery);
                    st3.setInt(1, offre.getId().intValue());
                    st3.setInt(2, salleFete.getSurface());
                    st3.setInt(3, salleFete.getCapacitePersonne());
                    st3.setString(4, salleFete.getGouvernerat());  // Ici, assurez-vous que la valeur est bien envoyée
                    st3.setString(5, salleFete.getVille());
                    st3.setString(6, salleFete.getAdresseExacte());
                    st3.setDouble(7, salleFete.getLatitude());
                    st3.setDouble(8, salleFete.getLongitude());
                    st3.setString(9, salleFete.getOptionInclus());
                    st3.executeUpdate();


            }

            // Commiter la transaction

        } catch (SQLException e) {

            throw new SQLException("Erreur lors de l'ajout de l'offre", e);
        }
    }

    public void deleteOffre(Offre offre) throws SQLException {
        String query = "DELETE FROM offre WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, offre.getId().intValue());
            statement.executeUpdate();
        }
    }

    public void updateOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException, IOException {
        String query = "UPDATE offre SET titre = ?, description = ?, prixInitial = ?, prixRemise = ?,  WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, offre.getTitre());
            statement.setString(2, offre.getDescription());
            statement.setDouble(3, offre.getPrixInitial());
            statement.setDouble(4, offre.getPrixRemise());



            statement.setInt(5, offre.getId().intValue());
            statement.executeUpdate();
        }

        if (image != null) {
            String updateImageQuery = "UPDATE image SET url = ? WHERE offre_id = ?";
            try (PreparedStatement imageStatement = con.prepareStatement(updateImageQuery)) {
                String imageUrl = "blob:" + image.getId();
                imageStatement.setString(1, imageUrl);

                imageStatement.setInt(2, offre.getId().intValue());
                imageStatement.executeUpdate();
            }
        }
        if (salleFete != null) {
            String updateSalleFeteQuery = "UPDATE sallefete SET surface = ?, capacitePersonne = ?, gouvernerat = ?, ville = ?, adresseExacte = ?, latitude = ?, longitude = ?, optionInclus = ? WHERE offre_id = ?";
            try (PreparedStatement salleFeteStatement = con.prepareStatement(updateSalleFeteQuery)) {
                salleFeteStatement.setInt(1, salleFete.getSurface());
                salleFeteStatement.setInt(2, salleFete.getCapacitePersonne());
                salleFeteStatement.setString(3, salleFete.getGouvernerat());
                salleFeteStatement.setString(4, salleFete.getVille());
                salleFeteStatement.setString(5, salleFete.getAdresseExacte());
                salleFeteStatement.setDouble(6, salleFete.getLatitude());
                salleFeteStatement.setDouble(7, salleFete.getLongitude());
                salleFeteStatement.setString(8, salleFete.getOptionInclus());
                salleFeteStatement.setInt(9, offre.getId().intValue());
                salleFeteStatement.executeUpdate();
            }
        }
    }

    public ObservableList<Offre> getOffres() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String query = "SELECT * FROM offre";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Offre offre = new Offre(rs.getInt("id"), rs.getString("titre"));
                offres.add(offre);
            }
        }
        return FXCollections.observableArrayList(offres);
    }
}
