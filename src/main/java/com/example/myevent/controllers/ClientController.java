package com.example.myevent.controllers;

import com.example.myevent.Models.client;
import com.example.myevent.tools.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ClientController {

    @FXML
    private TableView<client> clientTable;
    @FXML
    private TableColumn<client, Integer> idColumn;
    @FXML
    private TableColumn<client, String> nomColumn;
    @FXML
    private TableColumn<client, String> prenomColumn;
    @FXML
    private TableColumn<client, String> emailColumn;
    @FXML
    private TableColumn<client, String> genreColumn;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField idField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField genreField;

    private ObservableList<client> clients = FXCollections.observableArrayList();

    private Connexion myConnection;

    @FXML
    private void initialize() {
        myConnection = Connexion.getInstance();
        loadData();
        clientTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showClientDetails(newValue));
    }

    // Charge les données à partir de la base de données MySQL
    private void loadData() {
        String query = "SELECT * FROM clients";
        try (Connection connection = myConnection.getCnx();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String genre = resultSet.getString("genre");
                String image = resultSet.getString("image");
                clients.add(new client(Integer.toString(id), nom, prenom, email, genre, image));
            }
            clientTable.setItems(clients);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de chargement", "Une erreur est survenue lors du chargement des données.");
        }
    }

    // Affiche les détails du client sélectionné dans le formulaire d'édition
    private void showClientDetails(client client) {
        if (client != null) {
            idField.setText(client.getId());
            nomField.setText(client.getNom());
            prenomField.setText(client.getPrenom());
            emailField.setText(client.getEmail());
            genreField.setText(client.getGenre());
            // Charger l'image du client
            // Assurez-vous que votre ImageView est correctement configuré pour afficher l'image
            // Vous pouvez utiliser une bibliothèque comme JavaFX ImageLoader pour charger l'image à partir d'URL ou de fichier
            // imageView.setImage(new Image(client.getImage()));
        } else {
            // Le client est null, effacer tous les champs
            idField.clear();
            nomField.clear();
            prenomField.clear();
            emailField.clear();
            genreField.clear();
            imageView.setImage(null);
        }
    }

    @FXML
    private void AddC() {
        // Récupérer les valeurs des champs de texte
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String genre = genreField.getText();
        // Vous pouvez ajouter une validation des données ici

        // Créer un nouveau client avec les valeurs des champs de texte
        client newClient = new client(null, nom, prenom, email, genre, null);
        // Ajouter le nouveau client à la liste observable
        clients.add(newClient);
        // Insérer les données dans la base de données (optionnel, en fonction de votre implémentation)

        // Effacer les champs de texte après l'ajout
        clearFields();

        // Afficher une confirmation de succès
        showAlert(AlertType.INFORMATION, "Succès", "Nouveau client ajouté avec succès !");
    }

    @FXML
    private void UpdateC() {
        // Récupérer le client sélectionné dans le TableView
        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            // Mettre à jour les propriétés du client avec les valeurs des champs de texte
            selectedClient.setNom(nomField.getText());
            selectedClient.setPrenom(prenomField.getText());
            selectedClient.setEmail(emailField.getText());
            selectedClient.setGenre(genreField.getText());
            // Vous pouvez également mettre à jour les données dans la base de données (optionnel)

            // Effacer les champs de texte après la mise à jour
            clearFields();

            // Afficher une confirmation de succès
            showAlert(AlertType.INFORMATION, "Succès", "Client mis à jour avec succès !");
        } else {
            // Afficher une erreur si aucun client n'est sélectionné
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un client à mettre à jour.");
        }
    }

    @FXML
    private void DeleteC() {
        // Récupérer le client sélectionné dans le TableView
        client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            // Supprimer le client de la liste observable
            clients.remove(selectedClient);
            // Vous pouvez également supprimer les données de la base de données (optionnel)

            // Afficher une confirmation de succès
            showAlert(AlertType.INFORMATION, "Succès", "Client supprimé avec succès !");
        } else {
            // Afficher une erreur si aucun client n'est sélectionné
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un client à supprimer.");
        }
    }

    // Méthode pour effacer les champs de texte
    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        genreField.clear();
    }

    // Méthode pour afficher une alerte
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




