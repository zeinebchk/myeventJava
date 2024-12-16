//package com.example.myevent.controllers;
//
//import com.example.myevent.Models.client;
//import com.example.myevent.tools.Connexion;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//import java.net.URL;
//import java.sql.*;
//import java.util.ResourceBundle;
//
//public class ClientController implements Initializable {
//
//    @FXML
//    private TableView<client> clientTable;
//
//    @FXML
//    private TableColumn<client, Integer> colid;
//    @FXML
//    private TableColumn<client, String> colnom;
//    @FXML
//    private TableColumn<client, String> colprenom;
//    @FXML
//    private TableColumn<client, String> colemail;
//    @FXML
//    private TableColumn<client, String> colgenre;
//
//    @FXML
//    private TextField tfid;
//    @FXML
//    private TextField tfnom;
//    @FXML
//    private TextField tfprenom;
//    @FXML
//    private TextField tfemail;
//    @FXML
//    private TextField tfgenre;
//
//    @FXML
//    private Button addClient;
//
//    @FXML
//    private Button updateClient;
//
//    @FXML
//    private Button deleteClient;
//
//    private Connection connection;
//
//
//
//    @FXML
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
//        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
//        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
//        colgenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
//
//
//        connection = Connexion.getInstance().getCnx();
//
//        loadClients();
//        clientTable.setOnMouseClicked(event -> {
//            client selectedclient = clientTable.getSelectionModel().getSelectedItem();
//            if (selectedclient != null) {
//                tfid.setText(selectedclient.getid());
//                tfnom.setText(selectedclient.getnom());
//                tfprenom.setText(selectedclient.getprenom());
//                tfemail.setText(selectedclient.getemail());
//                tfgenre.setText(selectedclient.getgenre());
//            }
//        });
//        addClient.setOnAction(this::add);
//        updateClient.setOnAction(this::update);
//        deleteClient.setOnAction(this::delete);
//    }
//
//
//    private void loadClients() {
//        ObservableList<client> clients = FXCollections.observableArrayList();
//        String query = "SELECT * FROM client";
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                clients.add(new client(
//                        rs.getString("id"),
//                        rs.getString("nom"),
//                        rs.getString("prenom"),
//                        rs.getString("email"),
//                        rs.getString("genre")
//
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des clients : " + e.getMessage());
//        }
//        clientTable.setItems(clients);
//    }
//
//
//    private void add(ActionEvent event) {
//        String id = tfid.getText();
//        String nom = tfnom.getText();
//        String prenom = tfprenom.getText();
//        String email = tfemail.getText();
//        String genre = tfgenre.getText();
//
//
//        String query = "INSERT INTO client (id, nom, prenom, email, genre) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, id);
//            pstmt.setString(2, nom);
//            pstmt.setString(3, prenom);
//            pstmt.setString(4, email);
//            pstmt.setString(5, genre);
//            pstmt.executeUpdate();
//            loadClients();
//            clearFields();
//            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "client ajouté avec succès.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de client : " + e.getMessage());
//
//        }
//    }
//
//    private void update(ActionEvent event) {
//        client  selectedClient = clientTable.getSelectionModel().getSelectedItem();
//        if (selectedClient != null) {
//            String query = "UPDATE client SET nom = ?, prenom = ?, email = ?,  genre = ?, WHERE id = ?";
//            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//                pstmt.setString(1, tfnom.getText());
//                pstmt.setString(2, tfprenom.getText());
//                pstmt.setString(3, tfemail.getText());
//                pstmt.setString(4, tfgenre.getText());
//
//                pstmt.executeUpdate();
//                loadClients();
//                clearFields();
//                showAlert(Alert.AlertType.CONFIRMATION, "Succès", "client mis à jour avec succès.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour de client : " + e.getMessage());
//            }
//        } else {
//            showAlert(Alert.AlertType.WARNING, "Aucun Entrepreneur sélectionné", "Veuillez sélectionner un client à mettre à jour.");
//        }
//    }
//
//    private void delete(ActionEvent event) {
//        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
//        if (selectedClient != null) {
//            String query = "DELETE FROM client WHERE id = ?";
//            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//                pstmt.setString(1, selectedClient.getid());
//                pstmt.executeUpdate();
//                loadClients();
//                clearFields();
//                showAlert(Alert.AlertType.INFORMATION, "Succès", "client supprimé avec succès.");
//            } catch (SQLException e) {
//                e.printStackTrace();
//                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de client : " + e.getMessage());
//            }
//        } else {
//            showAlert(Alert.AlertType.WARNING, "Aucunclient sélectionné", "Veuillez sélectionner un client à supprimer.");
//        }
//    }
//
//    private void clearFields() {
//        tfid.clear();
//        tfnom.clear();
//        tfprenom.clear();
//        tfemail.clear();
//        tfgenre.clear();
//
//    }
//
//    private void showAlert(Alert.AlertType alertType, String title, String content) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//}
//
//
//
//
//
