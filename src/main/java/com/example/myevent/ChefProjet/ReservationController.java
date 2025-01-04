package com.example.myevent.ChefProjet;

import com.example.myevent.entities.Offre;
import com.example.myevent.entities.Reservation;
import com.example.myevent.entities.User;
import com.example.myevent.tools.Connexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;


public class ReservationController {

    @FXML
    private TableView<Reservation> MainTable;
    private Button confirmerButton;
    @FXML
    private TextField Text_Searchbar;

    @FXML
    private TableColumn<Reservation, String> idColumn;
    @FXML
    private TableColumn<Reservation, String> nomClientColumn;
    @FXML
    private TableColumn<Reservation, String> prenomColumn;
    @FXML
    private TableColumn<Reservation, String> emailColumn;
    @FXML
    private TableColumn<Reservation, String> nomOffre;
    @FXML
    private TableColumn<Reservation, Integer> avanceClientColumn;
    @FXML
    private TableColumn<Reservation, String> statusColumn;

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();

    public void initialize() {
        initializeTableView();  // Initialisation de la TableView
        ObservableList<Reservation> reservationsDB = getAllReservations();  // Récupérer les réservations
        MainTable.setItems(reservationsDB);
    }
    @FXML
    private void confirmerReservations(ActionEvent event) {
        String texteSaisi = Text_Searchbar.getText();
        int reservationId = Integer.parseInt(texteSaisi);
        confirmReservation(reservationId,event);
    }
    @FXML
    private void supprimerReservations(ActionEvent event) {
        String texteSaisi = Text_Searchbar.getText();
        int reservationId = Integer.parseInt(texteSaisi);
        supprimerReservation(reservationId,event);
    }

    private void supprimerReservation(int reservationId, ActionEvent event) {
        try {
            // Préparer la requête SQL pour mettre à jour le statut de la réservation à "Refusé"
            String sql = "UPDATE reservations SET status = 'Refuse' WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, reservationId);

            // Exécuter la requête SQL pour mettre à jour le statut de la réservation
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le statut de la réservation avec l'ID " + reservationId + " a été mis à jour avec succès.");
                showAlert("L'offre a ete refusée ");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));

                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID " + reservationId + ". Le statut n'a pas été mis à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void confirmReservation(int reservationId, ActionEvent event) {
        try {
            // Préparer la requête SQL pour mettre à jour le statut de la réservation
            String sql = "UPDATE reservations SET status = 'enCours' WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, reservationId);

            // Exécuter la requête SQL pour mettre à jour le statut de la réservation
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le statut de la réservation avec l'ID " + reservationId + " a été mis à jour avec succès.");
                showAlert("L'offre a ete confirmée avec succées");
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-view.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID " + reservationId + ". Le statut n'a pas été mis à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }

    public void initializeTableView() {

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient_id().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient_id().getPrenom()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient_id().getEmail()));
        nomOffre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre_id().getTitre()));
        avanceClientColumn.setCellValueFactory(new PropertyValueFactory<>("avanceClient"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        /*ObservableList<Reservation> reservationsDB = getAllReservations();
        MainTable.setItems(reservationsDB);*/
    }

    public ObservableList<Reservation> getAllReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        try {
             PreparedStatement statement = con.prepareStatement("SELECT r.*, u.*, o.* FROM reservations r JOIN users u ON r.client_id = u.id JOIN offre o ON r.offre_id = o.id");
             ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BigInteger id = resultSet.getBigDecimal("r.id").toBigInteger();
                String status = resultSet.getString("status");
                Time heureDebut = resultSet.getTime("heureDebut");
                Time heureFin = resultSet.getTime("heureFin");
                Date dateReservation = resultSet.getDate("dateReservation");
                int avanceClient = resultSet.getInt("avanceClient");
                // Récupérer les informations de l'offre
                BigInteger offreId = resultSet.getBigDecimal("offre_id").toBigInteger();
                String titreOffre = resultSet.getString("titre");
               /* String descriptionOffre = resultSet.getString("description");
                double prixInitial = resultSet.getDouble("prixInitial");
                double prixRemise = resultSet.getDouble("prixRemise");*/
//                LocalDate dateFinRemise = resultSet.getDate("dateFinRemise").toLocalDate();
                // Créer une instance de l'objet Offre
                Offre offre = new Offre(offreId, titreOffre);
                // Récupérer les informations du client
                BigInteger clientId = resultSet.getBigDecimal("client_id").toBigInteger();
                String nomClient = resultSet.getString("nom");
                String prenomClient = resultSet.getString("prenom");
                String emailClient = resultSet.getString("email");
                User client = new User(clientId, nomClient, prenomClient, emailClient);

                // Créer une nouvelle réservation avec l'objet Client et l'objet Offre
                Reservation reservation = new Reservation(id, status, heureDebut, heureFin, dateReservation, avanceClient, offre, client);
                reservations.add(reservation);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Reservation reservation : reservations ) {
            System.out.println(reservation.toString());
        }

        return reservations;
    }

        @FXML
   public void afficherMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}