package com.example.myevent.entities;

import com.example.myevent.tools.Connexion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;



public class OffreDAO {
    private ObservableList<Offre> offres;
    private Connection connection;
    private BigInteger offreId;
    private String message;


    public OffreDAO(Connection connection ) {
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
        connection = Connexion.getInstance().getCnx();
        if (connection != null && !connection.isClosed()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM offre WHERE id = ?")) {
                statement.setInt(1, offre.getId().intValue());
                statement.executeUpdate();
                offres.remove(offre); // Retirer l'offre de la liste après la suppression

                // Supprimer les données liées dans la table sallefete
                String deleteSalleFeteQuery = "DELETE FROM sallefete WHERE offre_id = ?";
                try (PreparedStatement salleFeteStatement = connection.prepareStatement(deleteSalleFeteQuery)) {
                    salleFeteStatement.setInt(1, offre.getId().intValue());
                    salleFeteStatement.executeUpdate();
                }

                // Supprimer les données liées dans la table image
                String deleteImageQuery = "DELETE FROM image WHERE offre_id = ?";
                try (PreparedStatement imageStatement = connection.prepareStatement(deleteImageQuery)) {
                    imageStatement.setInt(1, offre.getId().intValue());
                    imageStatement.executeUpdate();
                }
            }
        } else {
            System.err.println("The database connection is null or closed.");
        }
    }

    public boolean doesEntrepreneurExist(BigDecimal entrepreneurId) throws SQLException {
        connection = Connexion.getInstance().getCnx();
        if (connection != null && !connection.isClosed()) {
            String query = "SELECT COUNT(*) FROM entrepreneurs WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setBigDecimal(1, entrepreneurId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
            }
        } else {
            System.err.println("The database connection is null or closed.");
        }
        return false;
    }


    public void updateOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException {
        connection = Connexion.getInstance().getCnx();
        if (connection != null && !connection.isClosed()) {
            // Vérification de l'ID de l'entrepreneur
            BigDecimal entrepreneurId = new BigDecimal(offre.getEntrepreneurId());
            if (!doesEntrepreneurExist(entrepreneurId)) {
                throw new SQLException("L'ID de l'entrepreneur " + entrepreneurId + " n'existe pas.");
            }

            // Mise à jour de l'offre dans la base de données
            String updateOffreQuery = "UPDATE offre SET titre = ?, description = ?, prixInitial = ?, prixRemise = ?, dateFinRemise = ?, entrepreneur_id = ? WHERE id = ?";
            try (PreparedStatement offreStatement = connection.prepareStatement(updateOffreQuery)) {
                offreStatement.setString(1, offre.getTitre());
                offreStatement.setString(2, offre.getDescription());
                offreStatement.setDouble(3, offre.getPrixInitial());
                offreStatement.setDouble(4, offre.getPrixRemise());
                offreStatement.setDate(5, Date.valueOf(offre.getDateFinRemise()));
                offreStatement.setBigDecimal(6, entrepreneurId);
                offreStatement.setInt(7, offre.getId().intValue());
                offreStatement.executeUpdate();
            }

            // Mise à jour de la salle de fête, si elle existe
            if (salleFete != null) {
                String updateSalleFeteQuery = "UPDATE sallefete SET surface = ?, capacitePersonne = ?, gouvernerat = ?, ville = ?, adresseExacte = ?, latitude = ?, longitude = ?, optionInclus = ? WHERE offre_id = ?";
                try (PreparedStatement salleFeteStatement = connection.prepareStatement(updateSalleFeteQuery)) {
                    salleFeteStatement.setInt(1, salleFete.getSurface());
                    salleFeteStatement.setInt(2, salleFete.getCapacitePersonne());
                    salleFeteStatement.setString(3, salleFete.getGouvernerat());
                    salleFeteStatement.setString(4, salleFete.getVille());
                    salleFeteStatement.setString(5, salleFete.getAdresseExacte());
                    salleFeteStatement.setDouble(6, salleFete.getLatitude());
                    salleFeteStatement.setDouble(7, salleFete.getLongitude());
                    salleFeteStatement.setString(8, salleFete.getOptionInclus());
                    salleFeteStatement.setBigDecimal(9, new BigDecimal(offre.getId()));
                    salleFeteStatement.executeUpdate();
                }
            }

            // Mise à jour de l'image, si elle existe
            if (image != null) {
                String updateImageQuery = "UPDATE image SET url = ? WHERE offre_id = ?";
                try (PreparedStatement imageStatement = connection.prepareStatement(updateImageQuery)) {
                    imageStatement.setString(1, image.getImageURL());
                    imageStatement.setBigDecimal(2, new BigDecimal(offre.getId()));
                    imageStatement.executeUpdate();
                }
            }

            // Recharger les offres après la mise à jour
            loadOffresData();
        } else {
            System.err.println("The database connection is null or closed.");
        }
    }



    private void loadOffresData() {
        connection = Connexion.getInstance().getCnx();
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
                offreStatement.setDate(5, Date.valueOf(offre.getDateFinRemise()));
                BigInteger entrepreneurId = offre.getEntrepreneurId();
                if (offre == null || entrepreneurId == null) {
                    throw new IllegalArgumentException("L'offre ou l'ID de l'entrepreneur ne peut pas être null.");
                }
// Utiliser l'entrepreneurId pour créer le BigDecimal
                offreStatement.setBigDecimal(6, new BigDecimal(entrepreneurId));
                offreStatement.executeUpdate();
                // Récupérer l'ID généré pour l'offre ajoutée
                ResultSet generatedKeys = offreStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                     offreId = BigInteger.valueOf(generatedKeys.getLong(1));
                    offre.setId(offreId);
                } else {
                    throw new SQLException("Failed to retrieve generated offre ID.");
                }
            }

            // Ajout de la salle de fête
            if (salleFete != null && salleFete.getGouvernerat() != null) {
                String sql = "INSERT INTO sallefete (surface, capacitePersonne, gouvernerat, ville, adresseExacte, latitude, longitude, optionInclus, offre_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, salleFete.getSurface());
                    statement.setInt(2, salleFete.getCapacitePersonne());
                    statement.setString(3, salleFete.getGouvernerat());
                    statement.setString(4, salleFete.getVille());
                    statement.setString(5, salleFete.getAdresseExacte());
                    statement.setDouble(6, salleFete.getLatitude());
                    statement.setDouble(7, salleFete.getLongitude());
                    statement.setString(8, salleFete.getOptionInclus());
                    statement.setBigDecimal(9, new BigDecimal(offre.getId())); // Assurez-vous d'utiliser l'ID correct ici
                    statement.executeUpdate();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.WARNING, "Erreur lors de l'ajout de la salle de fête :", e.getMessage());
                    e.printStackTrace();
                    // Vous pouvez choisir de lancer une nouvelle exception ici ou de gérer l'erreur différemment
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Données manquantes pour ajouter la salle de fête.");
            }
            // Ajouter l'image si elle n'est pas null
            if (image != null) {
                // Code pour ajouter l'image dans la base de données
                String sql = "INSERT INTO image (url,offre_id) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, image.getImageURL());
                    statement.setBigDecimal(2, new BigDecimal(offreId));
                    statement.executeUpdate();}
                catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("image non inseréé");
                }
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

    private void showAlert(Alert.AlertType alertType, String s) {
    }

    private void showAlert(Alert.AlertType alertType, String s, String message) {
    }


    }




