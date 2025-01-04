package com.example.myevent.controllers;


import com.example.myevent.Services.OffreService;
import com.example.myevent.entities.Offre;

import com.example.myevent.entities.Image;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private TableColumn<Offre, Void> C_ACTIONS;

    @FXML
    private Button retour;

    OffreService offreDAO=new OffreService();
    private int surface;
    private int capacitePersonne;
    private String gouvernerat;
    private String ville;
    private Connection connection;
    private String optionInclus;
    private String adresseExacte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        C_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        C_FN.setCellValueFactory(new PropertyValueFactory<>("titre"));
        C_LN.setCellValueFactory(new PropertyValueFactory<>("description"));
        C_CNIC.setCellValueFactory(new PropertyValueFactory<>("prixInitial"));

        connection = Connexion.getInstance().getCnx();
        loadOffresData();
        addActionsButtons();

        Scene scene = TableViewOffres.getScene();
        if (scene != null) {
            scene.getStylesheets().add(getClass().getResource("/stylee.css").toExternalForm());
        }
    }

    private void loadOffresData() {
        try {
            ObservableList<Offre> offres = offreDAO.getOffres();
            TableViewOffres.setItems(offres);
        } catch (SQLException e) {
            System.err.println("Error loading offers data: " + e.getMessage());
        }
    }

    private void addActionsButtons() {
        Callback<TableColumn<Offre, Void>, TableCell<Offre, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Offre, Void> call(final TableColumn<Offre, Void> param) {
                return new TableCell<>() {
                    private final Button btnDelete = new Button("Delete");
                    private final Button btnUpdate = new Button("Update");

                    {
                        btnDelete.setOnAction(event -> {
                            Offre offre = getTableView().getItems().get(getIndex());
                            deleteOffre(offre);
                        });

                        btnUpdate.setOnAction(event -> {
                            Offre offre = getTableView().getItems().get(getIndex());
                            showUpdateDialog(offre);
                        });
                    }

                    private final HBox pane = new HBox(btnDelete, btnUpdate);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };

        C_ACTIONS.setCellFactory(cellFactory);
    }

    @FXML
    private void deleteOffre(Offre offre) {
        try {
            offreDAO.deleteOffre(offre);
            loadOffresData(); // Refresh the TableView
            showAlert("Succès", "L'offre " + offre.getTitre() + " est supprimée");
        } catch (SQLException e) {
            System.err.println("Error deleting the offer: " + e.getMessage());
        }
    }

    private void showUpdateDialog(Offre offre) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Update Offre");

        TextField titreField = new TextField(offre.getTitre());
        TextField descriptionField = new TextField(offre.getDescription());
        TextField prixInitialField = new TextField(String.valueOf(offre.getPrixInitial()));
        TextField prixRemiseField = new TextField(String.valueOf(offre.getPrixRemise()));


        VBox dialogVBox = new VBox(
                new Label("Titre: "), titreField,
                new Label("Description: "), descriptionField,
                new Label("Prix Initial: "), prixInitialField,
                new Label("Prix Remise: "), prixRemiseField,

                new Button("Save")
        );

        Scene dialogScene = new Scene(dialogVBox, 300, 450);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private SalleFete getSalleFeteByOffreId(BigInteger offreId) {
        Connection connection = Connexion.getInstance().getCnx();
        String query = "SELECT * FROM sallefete WHERE offre_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, new BigDecimal(offreId));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    SalleFete salleFete = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseExacte, optionInclus, offreId);
                    salleFete.setSurface(resultSet.getInt("surface"));
                    salleFete.setCapacitePersonne(resultSet.getInt("capacitePersonne"));
                    salleFete.setGouvernerat(resultSet.getString("gouvernerat"));
                    salleFete.setVille(resultSet.getString("ville"));
                    salleFete.setAdresseExacte(resultSet.getString("adresseExacte"));
                    salleFete.setOptionInclus(resultSet.getString("optionInclus"));
                    return salleFete;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching SalleFete data: " + e.getMessage());
        }
        return null;
    }




    private void showAlert(String succès, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRetourButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
            System.out.println("Navigué vers la page hello-view avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}