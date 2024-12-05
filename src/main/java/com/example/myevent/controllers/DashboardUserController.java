package com.example.myevent.controllers;

import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardUserController implements Initializable {

    @FXML
    private DatePicker dateReservation;

    @FXML
    private ImageView filter2;

    @FXML
    private ComboBox<String> gouvs;

    @FXML
    private GridPane grid;

    @FXML
    private Slider maxBudget;

    @FXML
    private Slider minBudget;

    @FXML
    private TextField nbInvites;

    @FXML
    private ChoiceBox<String> villes;

    private final Connection con = Connexion.getInstance().getCnx();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFilters();
    }

    private void initializeFilters() {
        // Initialiser les gouvernorats et les villes
        gouvs.getItems().addAll("Sfax", "Tunis", "Monastir", "Sousse");
        gouvs.setValue("Monastir");

        villes.getItems().addAll("Monastir", "Moknine", "Sahline");
        villes.setValue("Monastir");

        // Écouteur pour mise à jour des villes en fonction du gouvernorat
        gouvs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateVilles(newValue));

        loadImages();
    }

    private void updateVilles(String gouvernorat) {
        // Mise à jour des villes en fonction du gouvernorat sélectionné
        villes.getItems().clear();
        switch (gouvernorat) {
            case "Sfax":
                villes.getItems().addAll("Sfax Ville", "Sakiet Eddaier", "Sakiet Ezzit");
                break;
            case "Tunis":
                villes.getItems().addAll("Tunis", "Ariana", "Ben Arous");
                break;
            case "Monastir":
                villes.getItems().addAll("Monastir", "Moknine", "Sahline");
                break;
            case "Sousse":
                villes.getItems().addAll("Sousse", "Hammam Sousse", "Kalaa Kebira");
                break;
            default:
                break;
        }
        villes.setValue(villes.getItems().get(0));
    }

    private void loadImages() {
        // Charger l'image du filtre
        File brandingFile = new File("images/search.png");
        filter2.setImage(new Image(brandingFile.toURI().toString()));
    }

    @FXML
    private void applyFilter(ActionEvent event) {
        // Récupérer les valeurs des champs de filtre
        String gouvernorat = gouvs.getValue();
        String ville = villes.getValue();
        LocalDate date = dateReservation.getValue();
        int nbGuests;
        try {
            nbGuests = Integer.parseInt(nbInvites.getText());
        } catch (NumberFormatException e) {
            showError("Veuillez entrer un nombre valide d'invités.");
            return;
        }
        double minPrice = minBudget.getValue();
        double maxPrice = maxBudget.getValue();

        // Valider les champs avant de filtrer
        if (gouvernorat != null && ville != null && date != null && nbGuests > 0) {
            // Construire la requête SQL dynamique
            String sql = "SELECT sf.id, sf.nom, sf.capacite, o.prix, r.date_disponibilite " +
                    "FROM sallefete sf " +
                    "JOIN reservations r ON sf.id = r.salle_id " +
                    "JOIN offre o ON sf.id = o.sallefete_id " +
                    "WHERE sf.gouvernorat = ? " +
                    "AND sf.ville = ? " +
                    "AND sf.capacite >= ? " +
                    "AND o.prix BETWEEN ? AND ? " +
                    "AND r.date_disponibilite >= ? " +
                    "AND r.date_disponibilite NOT IN (SELECT date_disponibilite FROM reservations WHERE salle_id = sf.id)";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                // Ajouter les paramètres de la requête
                stmt.setString(1, gouvernorat);
                stmt.setString(2, ville);
                stmt.setInt(3, nbGuests);
                stmt.setDouble(4, minPrice);
                stmt.setDouble(5, maxPrice);
                stmt.setDate(6, Date.valueOf(date)); // Date de réservation minimum

                // Exécuter la requête et récupérer les résultats
                ResultSet rs = stmt.executeQuery();

                // Effacer la GridPane avant d'ajouter de nouveaux résultats
                grid.getChildren().clear();

                // Afficher les résultats dans la vue (mettre à jour la GridPane)
                int row = 0;
                while (rs.next()) {
                    String salleNom = rs.getString("sf.nom");
                    double prix = rs.getDouble("o.prix");
                    Date dateDisponibilite = rs.getDate("r.date_disponibilite");
                    int capacite = rs.getInt("sf.capacite");

                    // Créer un label pour chaque résultat et l'ajouter à la GridPane
                    Label label = new Label("Salle: " + salleNom + ", Prix: " + prix + " TND, Capacité: " + capacite + " personnes, Date: " + dateDisponibilite);
                    label.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-background-color: #2D9596; -fx-padding: 10px;");

                    // Placer chaque label dans la GridPane
                    grid.add(label, 0, row++);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showError("Erreur lors de l'exécution de la requête.");
            }
        } else {
            showError("Veuillez remplir tous les critères.");
        }
    }

    // Méthode pour afficher un message d'erreur
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void affichMenu(ActionEvent event) throws IOException {
        // Navigation vers le menu utilisateur
        Parent root = FXMLLoader.load(getClass().getResource("/MenuUser.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
