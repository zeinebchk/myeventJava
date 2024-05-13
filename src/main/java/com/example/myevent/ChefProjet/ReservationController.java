package com.example.myevent.ChefProjet;

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
import java.sql.*;
import java.time.LocalDate;


public class ReservationController {
    @FXML
    private TableView<Reservation> MainTable;
    private Button confirmerButton;
    @FXML
    private TextField Text_Searchbar;

    public void initialize() {
        initializeTableView();

        ObservableList<Reservation> reservationsDB = getAllReservations();
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
        try (Connection connection = connect()) {
            // Préparer la requête SQL pour mettre à jour le statut de la réservation à "Refusé"
            String sql = "UPDATE reservations SET status = 'Refusé' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reservationId);

            // Exécuter la requête SQL pour mettre à jour le statut de la réservation
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le statut de la réservation avec l'ID " + reservationId + " a été mis à jour avec succès.");
                showAlert("L'offre a ete refusée ");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));

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
        try (Connection connection = connect()) {
            // Préparer la requête SQL pour mettre à jour le statut de la réservation
            String sql = "UPDATE reservations SET status = 'en cours' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reservationId);

            // Exécuter la requête SQL pour mettre à jour le statut de la réservation
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Le statut de la réservation avec l'ID " + reservationId + " a été mis à jour avec succès.");
                showAlert("L'offre a ete confirmée avec succées");
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));

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

        TableColumn<Reservation, String> idColumn = new TableColumn<>("ID");
        TableColumn<Reservation, String> nomClientColumn = new TableColumn<>("Nom");
        TableColumn<Reservation, String> heureDebutColumn = new TableColumn<>("Prenom");
        TableColumn<Reservation, String> heureFinColumn = new TableColumn<>("Email");
        TableColumn<Reservation, String> NumOffre = new TableColumn<>("Offre");

        TableColumn<Reservation, String> offreTitreColumn = new TableColumn<>("Titre de l'Offre");
        TableColumn<Reservation, String> offreDesciptionColumn = new TableColumn<>("Description");
        TableColumn<Reservation, Double> avanceClientColumn = new TableColumn<>("AvanceClient");
        TableColumn<Reservation, String> statusColumn = new TableColumn<>("Status");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getNom()));
        heureDebutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getPrenom()));
        heureFinColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getEmail()));
        offreTitreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getTitre()));
        offreDesciptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getDescription()));
        avanceClientColumn.setCellValueFactory(new PropertyValueFactory<>("avanceClient"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        NumOffre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffre().getId()));

        MainTable.getColumns().addAll(idColumn, nomClientColumn, heureDebutColumn, heureFinColumn, offreTitreColumn, offreDesciptionColumn, avanceClientColumn, NumOffre,statusColumn);
    }

    public ObservableList<Reservation> getAllReservations() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT r.*, u.*, o.* FROM reservations r JOIN users u ON r.client_id = u.id JOIN offre o ON r.offre_id = o.id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String status = resultSet.getString("status");
                String heureDebut = resultSet.getString("heureDebut");
                String heureFin = resultSet.getString("heureFin");
                LocalDate dateReservation = resultSet.getDate("dateReservation").toLocalDate();
                double avanceClient = resultSet.getDouble("avanceClient");
                // Récupérer les informations de l'offre
                String offreId = resultSet.getString("offre_id");
                String titreOffre = resultSet.getString("titre");
                String descriptionOffre = resultSet.getString("description");
                double prixInitial = resultSet.getDouble("prixInitial");
                double prixRemise = resultSet.getDouble("prixRemise");
                LocalDate dateFinRemise = resultSet.getDate("dateFinRemise").toLocalDate();
                // Créer une instance de l'objet Offre
                Offre offre = new Offre(offreId, titreOffre, descriptionOffre, prixInitial, prixRemise, dateFinRemise);
                // Récupérer les informations du client
                String clientId = resultSet.getString("client_id");
                String nomClient = resultSet.getString("nom");
                String prenomClient = resultSet.getString("prenom");
                String emailClient = resultSet.getString("email");
                Client client = new Client(clientId, nomClient, prenomClient, emailClient);

                // Créer une nouvelle réservation avec l'objet Client et l'objet Offre
                Reservation reservation = new Reservation(id, status, heureDebut, heureFin, dateReservation, avanceClient, offre, client);
                reservations.add(reservation);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/events";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }
    @FXML
   public void afficherMenu(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}