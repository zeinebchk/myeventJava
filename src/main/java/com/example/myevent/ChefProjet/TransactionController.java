package com.example.myevent.ChefProjet;

import com.example.myevent.Services.UserService;
import com.example.myevent.entities.Transaction;
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
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {
    @FXML
    private TableView<Transaction> MainTable;


    @FXML
    private TextField Text_Searchbar;
    UserService userService = new UserService();
    public void initialize() {
        System.out.println("aaaaaaaaaaaaaaaaa");
        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(userService.getConfirmedUserReservation());
        for (Transaction transaction : observableTransactions) {
            System.out.println(transaction.toString());
        }
        MainTable.setItems(observableTransactions);
        initializeTableView();


    }

    public void initializeTableView() {

        TableColumn<Transaction, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<Transaction, String> prenomColumn = new TableColumn<>("Prenom");
        TableColumn<Transaction, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Transaction, Date> dateColumn = new TableColumn<>("Date de reservation");
        TableColumn<Transaction, String> offreColumn = new TableColumn<>("Offre");
        TableColumn<Transaction, Double> prixColumn = new TableColumn<>("Prix");
        TableColumn<Transaction, String> statutColumn = new TableColumn<>("Statut");
        TableColumn<Transaction, Integer> avanceColumn = new TableColumn<>("Avance du client");

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        offreColumn.setCellValueFactory(new PropertyValueFactory<>("titreSalle"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixSalle"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statutReservation"));  // Modifié ici
        avanceColumn.setCellValueFactory(new PropertyValueFactory<>("avance"));

        MainTable.getColumns().addAll( nomColumn, prenomColumn, emailColumn, dateColumn, offreColumn, prixColumn, statutColumn, avanceColumn);
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