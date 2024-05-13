package com.example.myevent.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.myevent.entities.Image;
import com.example.myevent.entities.Offre;
import com.example.myevent.entities .OffreDAO;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TableView<Offre> TableViewOffres;

    @FXML
    private TableColumn<Offre, Integer> C_ID;

    @FXML
    private TableColumn<Offre, String> C_FN;

    @FXML
    private TableColumn<Offre, Double> C_LN;

    @FXML
    private TableColumn<Offre, Double> C_CNIC;

    @FXML
    private TableColumn<Offre, String> C_DOB;

    private OffreDAO offreDAO;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialiser les colonnes de la TableView avec les propriétés des Offres
        C_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        C_FN.setCellValueFactory(new PropertyValueFactory<>("titre"));
        C_LN.setCellValueFactory(new PropertyValueFactory<>("prixInitial"));
        C_CNIC.setCellValueFactory(new PropertyValueFactory<>("prixRemise"));
        C_DOB.setCellValueFactory(new PropertyValueFactory<>("dateFinRemise"));

        // Créer une connexion à votre base de données
        Connection con = Connexion.getInstance().getCnx();
        offreDAO = new OffreDAO(connection);

        // Charger les données des offres dans la TableView
        loadOffresData();
    }

    private void loadOffresData() {
        try {
            // Vérifier que la connexion est valide avant de charger les données
            if (connection != null && !connection.isClosed()) {
                TableViewOffres.setItems(offreDAO.getAllOffres());
            } else {
                System.err.println("La connexion à la base de données est nulle ou fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des données des offres : " + e.getMessage());
        }
    }
}
