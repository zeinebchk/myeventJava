package com.example.myevent.controllers;



import com.example.myevent.Models.entrepreneur;
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

public class EntrepreneurController {
    @FXML
    private TextField ID_UserField;
    @FXML
    private TextField ProjetField;
    @FXML
    private TextField CategorieField;
    @FXML
    private TextField VilleField;
    @FXML
    private TextField GouverneratField;
    @FXML
    private TextField Adresse_ExacteField;
    @FXML
    private TextField NumTelProField;
    @FXML
    private TableView<entrepreneur> entrepreneursTable;

    private ObservableList<entrepreneur> entrepreneurs = FXCollections.observableArrayList();
    private Connexion myConnection;

    private void initialize() {
        myConnection = Connexion.getInstance();
        loadData();
        entrepreneursTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showentrepreneurDetails(newValue));
    }
    private void showentrepreneurDetails(entrepreneur entrepreneur) {
        if (entrepreneur != null) {
            // Mettre à jour les champs de texte avec les détails de l'entrepreneur sélectionné
            ID_UserField.setText(entrepreneur.getID_User());
            ProjetField.setText(entrepreneur.getProjet());
            CategorieField.setText(entrepreneur.getCategorie());
            VilleField.setText(entrepreneur.getVille());
            GouverneratField.setText(entrepreneur.getGouvernerat());
            Adresse_ExacteField.setText(entrepreneur.getAdresse_Exacte());
            NumTelProField.setText(entrepreneur.getNumTelPro());
        } else {
            // Effacer les champs de texte s'il n'y a pas d'entrepreneur sélectionné
            ID_UserField.clear();
            ProjetField.clear();
            CategorieField.clear();
            VilleField.clear();
            GouverneratField.clear();
            Adresse_ExacteField.clear();
            NumTelProField.clear();
        }
    }


    // Charge les données à partir de la base de données MySQL
    private void loadData() {
        String query = "SELECT * FROM entrepreneur";
        try (Connection connection = myConnection.getCnx();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String ID_User = resultSet.getString("ID_User");
                String Projet = resultSet.getString("Projet");
                String Categorie = resultSet.getString("Categorie");
                String Gouvernerat = resultSet.getString("Gouvernorat");
                String Ville = resultSet.getString("Ville");
                String Adresse_Exacte = resultSet.getString("Adresse_Exacte");
                String NumTelPro = resultSet.getString("NumTelPro");
                entrepreneurs.add(new entrepreneur(Integer.toString(id),  Projet, Categorie, Gouvernerat, Ville, Adresse_Exacte, NumTelPro));
            }
            entrepreneursTable.setItems(entrepreneurs);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Une erreur est survenue lors du chargement des données.");
        }
    }

    @FXML
    private void AddE() {
        // Récupérer les valeurs des champs de texte
        String ID_User = ID_UserField.getText();
        String Projet = ProjetField.getText();
        String Categorie = CategorieField.getText();
        String Ville = VilleField.getText();
        String Gouvernerat = GouverneratField.getText();
        String Adresse_Exacte = Adresse_ExacteField.getText();
        String NumTelPro = NumTelProField.getText();

        // Créer un nouveau entrepreneur avec les valeurs des champs de texte
        entrepreneur newEntrepreneur = new entrepreneur(null,  Projet, Categorie, Ville, Gouvernerat, Adresse_Exacte, NumTelPro);
        // Ajouter le nouveau entrepreneur à la liste observable
        entrepreneurs.add(newEntrepreneur);

        // Insérer les données dans la base de données
        insertData(newEntrepreneur);

        // Effacer les champs de texte après l'ajout
        clearFields();

        // Afficher une confirmation de succès
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Nouveau entrepreneur ajouté avec succès !");
    }

    private void insertData(entrepreneur newEntrepreneur) {
        String query = "INSERT INTO entrepreneur (ID_User, Projet, Categorie, Gouvernorat, Ville, Adresse_Exacte, NumTelPro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = myConnection.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newEntrepreneur.getID_User());
            statement.setString(2, newEntrepreneur.getProjet());
            statement.setString(3, newEntrepreneur.getCategorie());
            statement.setString(4, newEntrepreneur.getGouvernerat());
            statement.setString(5, newEntrepreneur.getVille());
            statement.setString(6, newEntrepreneur.getAdresse_Exacte());
            statement.setString(7, newEntrepreneur.getNumTelPro());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur d'insertion", "Une erreur est survenue lors de l'insertion des données dans la base de données.");
        }
    }

    @FXML

    private void UpdateE() {
        // Vérifier si un entrepreneur est sélectionné dans la table
        entrepreneur selectedEntrepreneur = entrepreneursTable.getSelectionModel().getSelectedItem();
        if (selectedEntrepreneur != null) {
            // Récupérer les nouvelles valeurs des champs de texte
            String ID_User = ID_UserField.getText();
            String Projet = ProjetField.getText();
            String Categorie = CategorieField.getText();
            String Ville = VilleField.getText();
            String Gouvernerat = GouverneratField.getText();
            String Adresse_Exacte = Adresse_ExacteField.getText();
            String NumTelPro = NumTelProField.getText();

            // Mettre à jour les données de l'entrepreneur sélectionné
            selectedEntrepreneur.setID_User(ID_User);
            selectedEntrepreneur.setProjet(Projet);
            selectedEntrepreneur.setCategorie(Categorie);
            selectedEntrepreneur.setVille(Ville);
            selectedEntrepreneur.setGouvernerat(Gouvernerat);
            selectedEntrepreneur.setAdresse_Exacte(Adresse_Exacte);
            selectedEntrepreneur.setNumTelPro(NumTelPro);

            // Mettre à jour les données dans la base de données
            updateData(selectedEntrepreneur);

            // Rafraîchir la table
            entrepreneursTable.refresh();

            // Afficher une confirmation de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Les informations de l'entrepreneur ont été mises à jour avec succès !");
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un entrepreneur à mettre à jour.");
        }
    }

    @FXML
    private void DeleteE() {
        // Vérifier si un entrepreneur est sélectionné dans la table
        entrepreneur selectedEntrepreneur = entrepreneursTable.getSelectionModel().getSelectedItem();
        if (selectedEntrepreneur != null) {
            // Supprimer l'entrepreneur de la liste observable
            entrepreneurs.remove(selectedEntrepreneur);

            // Supprimer les données de la base de données
            deleteData(selectedEntrepreneur);

            // Afficher une confirmation de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'entrepreneur a été supprimé avec succès !");
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un entrepreneur à supprimer.");
        }
    }

    private void updateData(entrepreneur entrepreneur) {
        String query = "UPDATE entrepreneur SET ID_User=?, Projet=?, Categorie=?, Gouvernorat=?, Ville=?, Adresse_Exacte=?, NumTelPro=? WHERE ID=?";
        try (Connection connection = myConnection.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entrepreneur.getID_User());
            statement.setString(2, entrepreneur.getProjet());
            statement.setString(3, entrepreneur.getCategorie());
            statement.setString(4, entrepreneur.getGouvernerat());
            statement.setString(5, entrepreneur.getVille());
            statement.setString(6, entrepreneur.getAdresse_Exacte());
            statement.setString(7, entrepreneur.getNumTelPro());
            statement.setInt(8, Integer.parseInt(entrepreneur.getID_User()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de mise à jour", "Une erreur est survenue lors de la mise à jour des données dans la base de données.");
        }
    }

    private void deleteData(entrepreneur entrepreneur) {
        String query = "DELETE FROM entrepreneur WHERE ID=?";
        try (Connection connection = myConnection.getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(entrepreneur.getID_User()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur est survenue lors de la suppression des données de la base de données.");
        }
    }


    private void clearFields() {
        ID_UserField.clear();
        ProjetField.clear();
        CategorieField.clear();
        VilleField.clear();
        GouverneratField.clear();
        Adresse_ExacteField.clear();
        NumTelProField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}