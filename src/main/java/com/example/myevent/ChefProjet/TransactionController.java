package com.example.myevent.ChefProjet;

import com.example.myevent.entities.Offre;
import com.example.myevent.entities.User;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;

public class TransactionController {
    @FXML
    private TableView<Transaction> MainTable;
    BigInteger BigInteger;

    @FXML
    private TextField Text_Searchbar;
    @FXML
    private JFXButton Menu;

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
             PreparedStatement statement = connection.prepareStatement("SELECT r.*, u.*, o.* FROM reservations r JOIN users u ON r.user_id = u.id JOIN offre o ON r.offre_id = o.id WHERE r.status = 'confirme'");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String status = resultSet.getString("status");
                String heureDebut = resultSet.getString("heureDebut");
                String heureFin = resultSet.getString("heureFin");
                LocalDate dateReservation = resultSet.getDate("dateReservation").toLocalDate();
                double avanceUser = resultSet.getDouble("avanceUser");

                // Récupérer les informations de l'offre
                String offreId = resultSet.getString("offre_id");
                String titreOffre = resultSet.getString("titre");
                String descriptionOffre = resultSet.getString("description");
                double prixInitial = resultSet.getDouble("prixInitial");
                double prixRemise = resultSet.getDouble("prixRemise");
                LocalDate dateFinRemise = resultSet.getDate("dateFinRemise").toLocalDate();

                // Créer une instance de l'objet Offre
                Offre offre = new Offre(resultSet.getInt("id"), resultSet.getString("nom"));
                offre.setId(offreId);
                offre.setTitre(titreOffre);
                offre.setDescription(descriptionOffre);
                offre.setPrixInitial(prixInitial);
                offre.setPrixRemise(prixRemise);
                offre.setDateFinRemise(dateFinRemise);

                // Récupérer les informations de l'utilisateur
                String userId = resultSet.getString("user_id");
                String nomUser = resultSet.getString("nom");
                String prenomUser = resultSet.getString("prenom");
                String emailUser = resultSet.getString("email");
                User user = new User(nomUser, prenomUser, emailUser, null, 0, null, null);

                // Créer une nouvelle transaction avec l'objet User et l'objet Offre
                Transaction transaction = new Transaction(id, nomUser, prenomUser, dateReservation, prixRemise, offreId, status);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/chachaaa";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    @FXML
    private void handleConnexionButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) Menu.getScene().getWindow();
            stage.setScene(new Scene(root));

            System.out.println("Navigué vers la page menu avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
