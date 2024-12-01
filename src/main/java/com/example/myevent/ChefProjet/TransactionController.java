package com.example.myevent.ChefProjet;

import com.example.myevent.entities.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class TransactionController {
    @FXML
    private TableView<Transaction> MainTable;


    @FXML
    private TextField Text_Searchbar;

    public void initialize() {
        initializeTableView();


        ObservableList<Transaction> reservationsDB = getAllReservations();
        MainTable.setItems(reservationsDB);

    }

    public void initializeTableView() {
        TableColumn<Transaction, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Transaction, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<Transaction, String> prenomColumn = new TableColumn<>("Prenom");
        TableColumn<Transaction, LocalDate> dateColumn = new TableColumn<>("Date");
        TableColumn<Transaction, Double> prixColumn = new TableColumn<>("Prix");
        TableColumn<Transaction, String> referenceColumn = new TableColumn<>("Reference");
        TableColumn<Transaction, String> statutColumn = new TableColumn<>("Statut");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        MainTable.getColumns().addAll(idColumn, nomColumn, prenomColumn, dateColumn, prixColumn, referenceColumn, statutColumn);
    }

    public ObservableList<Transaction> getAllReservations() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("SELECT r.*, u.*, o.* FROM reservations r JOIN users u ON r.client_id = u.id JOIN offre o ON r.offre_id = o.id WHERE r.status = 'confirme'");
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
               // Offre offre = new Offre(offreId, titreOffre, descriptionOffre, prixInitial, prixRemise, dateFinRemise);
                // Récupérer les informations du client
                String clientId = resultSet.getString("client_id");
                String nomClient = resultSet.getString("nom");
                String prenomClient = resultSet.getString("prenom");
                String emailClient = resultSet.getString("email");
               // Client client = new Client(clientId, nomClient, prenomClient, emailClient);

                // Créer une nouvelle transaction avec l'objet Client et l'objet Offre
                Transaction transaction = new Transaction(id, nomClient, prenomClient, dateReservation, prixRemise, offreId, status);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/events";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    public void afficherMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}