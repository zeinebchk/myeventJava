package com.example.myevent.controllers;

import com.example.myevent.Models.entrepreneur;
import com.example.myevent.tools.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EntrepreneurController implements Initializable {

    @FXML
    private TableView<entrepreneur> tableEntrepreneurs;


    @FXML
    private TableColumn<entrepreneur, String> colID_User;

    @FXML
    private TableColumn<entrepreneur, String> colprojet;

    @FXML
    private TableColumn<entrepreneur, String> colCategorie;

    @FXML
    private TableColumn<entrepreneur, String> colGouvernerat;

    @FXML
    private TableColumn<entrepreneur, String> colVille;

    @FXML
    private TableColumn<entrepreneur, String> colAdresse_Exacte;

    @FXML
    private TableColumn<entrepreneur, String> colNumTelPro;

    @FXML
    private TextField tfID_User;

    @FXML
    private TextField tfprojet;

    @FXML
    private TextField tfCategorie;

    @FXML
    private TextField tfGouvernerat;

    @FXML
    private TextField tfVille;

    @FXML
    private TextField tfAdresse_Exacte;

    @FXML
    private TextField tfNumTelPro;

    @FXML
    private Button addEntrepreneur;

    @FXML
    private Button updateEntrepreneur;

    @FXML
    private Button deleteEntrepreneur;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID_User.setCellValueFactory(new PropertyValueFactory<>("Id_User"));
        colprojet.setCellValueFactory(new PropertyValueFactory<>("projet"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("Categorie"));
        colGouvernerat.setCellValueFactory(new PropertyValueFactory<>("Gouvernerat"));
        colVille.setCellValueFactory(new PropertyValueFactory<>("Ville"));
        colAdresse_Exacte.setCellValueFactory(new PropertyValueFactory<>("Adresse_Exacte"));
        colNumTelPro.setCellValueFactory(new PropertyValueFactory<>("NumTelPro"));

        connection = Connexion.getInstance().getCnx();

        loadEntrepreneurs();

        tableEntrepreneurs.setOnMouseClicked(event -> {
            entrepreneur selectedEntrepreneur = tableEntrepreneurs.getSelectionModel().getSelectedItem();
            if (selectedEntrepreneur != null) {
                tfID_User.setText(selectedEntrepreneur.getId_User());
                tfprojet.setText(selectedEntrepreneur.getProjet());
                tfCategorie.setText(selectedEntrepreneur.getCategorie());
                tfGouvernerat.setText(selectedEntrepreneur.getGouvernerat());
                tfVille.setText(selectedEntrepreneur.getVille());
                tfAdresse_Exacte.setText(selectedEntrepreneur.getAdresse_Exacte());
                tfNumTelPro.setText(selectedEntrepreneur.getNumTelPro());
            }
        });

        addEntrepreneur.setOnAction(this::add);
        updateEntrepreneur.setOnAction(this::update);
        deleteEntrepreneur.setOnAction(this::delete);
    }

    private void loadEntrepreneurs() {
        ObservableList<entrepreneur> entrepreneurs = FXCollections.observableArrayList();
        String query = "SELECT * FROM entrepreneur";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                entrepreneurs.add(new entrepreneur(
                        rs.getString("Id_User"),
                        rs.getString("projet"),
                        rs.getString("Categorie"),
                        rs.getString("Gouvernerat"),
                        rs.getString("Ville"),
                        rs.getString("Adresse_Exacte"),
                        rs.getString("NumTelPro")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des entrepreneurs : " + e.getMessage());
        }
        tableEntrepreneurs.setItems(entrepreneurs);
    }

    private void add(ActionEvent event) {
        String Id_User = tfID_User.getText();
        String projet = tfprojet.getText();
        String Categorie = tfCategorie.getText();
        String Gouvernerat = tfGouvernerat.getText();
        String Ville = tfVille.getText();
        String Adresse_Exacte = tfAdresse_Exacte.getText();
        String NumTelPro = tfNumTelPro.getText();

        String query = "INSERT INTO entrepreneurs (Id_User, projet, Categorie, Gouvernerat, Ville, Adresse_Exacte, NumTelPro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, Id_User);
            pstmt.setString(2, projet);
            pstmt.setString(3, Categorie);
            pstmt.setString(4, Gouvernerat);
            pstmt.setString(5, Ville);
            pstmt.setString(6, Adresse_Exacte);
            pstmt.setString(7, NumTelPro);
            pstmt.executeUpdate();
            loadEntrepreneurs();
            clearFields();
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Entrepreneur ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'entrepreneur : " + e.getMessage());

        }
    }

    private void update(ActionEvent event) {
        entrepreneur selectedEntrepreneur = tableEntrepreneurs.getSelectionModel().getSelectedItem();
        if (selectedEntrepreneur != null) {
            String query = "UPDATE entrepreneur SET projet = ?, Categorie = ?, Gouvernerat = ?, Ville = ?, Adresse_Exacte = ?, NumTelPro = ? WHERE Id_User = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, tfprojet.getText());
                pstmt.setString(2, tfCategorie.getText());
                pstmt.setString(3, tfGouvernerat.getText());
                pstmt.setString(4, tfVille.getText());
                pstmt.setString(5, tfAdresse_Exacte.getText());
                pstmt.setString(6, tfNumTelPro.getText());
                pstmt.setString(7, selectedEntrepreneur.getId_User());
                pstmt.executeUpdate();
                loadEntrepreneurs();
                clearFields();
                showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Entrepreneur mis à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour de l'entrepreneur : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Entrepreneur sélectionné", "Veuillez sélectionner un entrepreneur à mettre à jour.");
        }
    }

    private void delete(ActionEvent event) {
        entrepreneur selectedEntrepreneur = tableEntrepreneurs.getSelectionModel().getSelectedItem();
        if (selectedEntrepreneur != null) {
            String query = "DELETE FROM entrepreneur WHERE Id_User = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, selectedEntrepreneur.getId_User());
                pstmt.executeUpdate();
                loadEntrepreneurs();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Entrepreneur supprimé avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'entrepreneur : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Entrepreneur sélectionné", "Veuillez sélectionner un entrepreneur à supprimer.");
        }
    }

    private void clearFields() {
        tfID_User.clear();
        tfprojet.clear();
        tfCategorie.clear();
        tfGouvernerat.clear();
        tfVille.clear();
        tfAdresse_Exacte.clear();
        tfNumTelPro.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
