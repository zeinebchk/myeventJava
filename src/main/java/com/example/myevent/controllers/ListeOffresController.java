package com.example.myevent.controllers;

import com.example.myevent.entities.Image;
import com.example.myevent.entities.SalleFete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.myevent.entities.Offre;
import com.example.myevent.entities.OffreDAO;
import com.example.myevent.tools.Connexion;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class ListeOffresController implements Initializable {
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

    @FXML
    private TableColumn<Offre, Void> C_ACTIONS;
    @FXML
    private Button retour;
    private OffreDAO offreDAO;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize the columns of the TableView with the properties of the Offers
        C_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        C_FN.setCellValueFactory(new PropertyValueFactory<>("titre"));
        C_LN.setCellValueFactory(new PropertyValueFactory<>("prixInitial"));
        C_CNIC.setCellValueFactory(new PropertyValueFactory<>("prixRemise"));
        C_DOB.setCellValueFactory(new PropertyValueFactory<>("dateFinRemise"));

        // Create a connection to your database
        connection = Connexion.getInstance().getCnx();
        offreDAO = new OffreDAO(connection);

        // Load offer data into the TableView
        loadOffresData();

        // Add action buttons
        addActionsButtons();
        Scene scene = TableViewOffres.getScene();
        if (scene != null) {
            scene.getStylesheets().add(getClass().getResource("/stylee.css").toExternalForm());
        }
    }


    private void loadOffresData() {
        try {
            // Ensure the connection is valid before loading data
            if (connection != null && !connection.isClosed()) {
                TableViewOffres.setItems(offreDAO.getAllOffres());
            } else {
                System.err.println("The database connection is null or closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error loading offer data: " + e.getMessage());
        }
    }

    private void addActionsButtons() {
        Callback<TableColumn<Offre, Void>, TableCell<Offre, Void>> cellFactory = new Callback<TableColumn<Offre, Void>, TableCell<Offre, Void>>() {
            @Override
            public TableCell<Offre, Void> call(final TableColumn<Offre, Void> param) {
                final TableCell<Offre, Void> cell = new TableCell<Offre, Void>() {
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
                return cell;
            }
        };

        C_ACTIONS.setCellFactory(cellFactory);
    }

    @FXML
    private void deleteOffre(Offre offre) {
        try {
            offreDAO.deleteOffre(offre);
            loadOffresData(); // Refresh the TableView
            showAlert("L'offre " + offre.getTitre() + " est supprimée");
        } catch (SQLException e) {
            System.err.println("Error deleting the offer: " + e.getMessage());
        }
    }

    private void showUpdateDialog(Offre offre) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Update Offre");

        // Create the form fields with the data from the selected offer
        TextField titreField = new TextField(offre.getTitre());
        TextField descriptionField = new TextField(offre.getDescription());
        TextField prixInitialField = new TextField(String.valueOf(offre.getPrixInitial()));
        TextField prixRemiseField = new TextField(String.valueOf(offre.getPrixRemise()));
        DatePicker dateFinRemisePicker = new DatePicker(offre.getDateFinRemise());

        SalleFete salleFete = getSalleFeteByOffreId(offre.getId());
        TextField capaciteField = new TextField(salleFete != null ? String.valueOf(salleFete.getCapacitePersonne()) : "");
        TextField adresseField = new TextField(salleFete != null ? salleFete.getAdresseExacte() : "");

        Image image = getImageByOffreId(offre.getId());
        TextField imageURLField = new TextField(image != null ? image.getImageURL() : "");
// Apply styles to form elements
        titreField.getStyleClass().add("dialog-textfield");
        descriptionField.getStyleClass().add("dialog-textfield");
        prixInitialField.getStyleClass().add("dialog-textfield");
        prixRemiseField.getStyleClass().add("dialog-textfield");
        dateFinRemisePicker.getStyleClass().add("dialog-textfield");
        capaciteField.getStyleClass().add("dialog-textfield");
        adresseField.getStyleClass().add("dialog-textfield");
        imageURLField.getStyleClass().add("dialog-textfield");

        Label titleLabel = new Label("Update Offre");
        titleLabel.getStyleClass().add("dialog-title");
        // Create the layout
        VBox dialogVBox = new VBox();
        dialogVBox.getChildren().addAll(
                new Label("Titre: "), titreField,
                new Label("Description: "), descriptionField,
                new Label("Prix Initial: "), prixInitialField,
                new Label("Prix Remise: "), prixRemiseField,
                new Label("Date Fin Remise: "), dateFinRemisePicker,
                new Label("Capacite: "), capaciteField,
                new Label("Adresse: "), adresseField,
                new Label("Image URL: "), imageURLField,
                new Button("Save")
        );

        // Handle save button action
        Button saveButton = (Button) dialogVBox.getChildren().get(dialogVBox.getChildren().size() - 1);
        saveButton.setOnAction(event -> {
            try {
                // Update the offer object with the new values
                offre.setTitre(titreField.getText());
                offre.setDescription(descriptionField.getText());
                offre.setPrixInitial(Double.parseDouble(prixInitialField.getText()));
                offre.setPrixRemise(Double.parseDouble(prixRemiseField.getText()));
                offre.setDateFinRemise(dateFinRemisePicker.getValue());

                // Update SalleFete if it exists
                if (salleFete != null) {
                    salleFete.setCapacitePersonne(Integer.parseInt(capaciteField.getText()));
                    salleFete.setAdresseExacte(adresseField.getText());
                }

                // Update Image if it exists
                if (image != null) {
                    image.setImageURL(imageURLField.getText());
                }

                // Call the update method of your DAO to update the offer in the database
                offreDAO.updateOffre(offre, salleFete, image);
                // Refresh the TableView
                loadOffresData();
                // Close the dialog
                dialog.close();
            } catch (NumberFormatException e) {
                showAlert("Invalid number format: " + e.getMessage());
            } catch (SQLException e) {
                showAlert("SQL Error: " + e.getMessage());
            }
        });

        Scene dialogScene = new Scene(dialogVBox, 300, 450); // Adjust the size as needed
        dialogScene.getStylesheets().add(getClass().getResource("/stylee.css").toExternalForm());
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private SalleFete getSalleFeteByOffreId(BigInteger offreId) {
        connection = Connexion.getInstance().getCnx();
        String query = "SELECT * FROM sallefete WHERE offre_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, new BigDecimal(offreId));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    SalleFete salleFete = new SalleFete();
                    salleFete.setSurface(resultSet.getInt("surface"));
                    salleFete.setCapacitePersonne(resultSet.getInt("capacitePersonne"));
                    salleFete.setGouvernerat(resultSet.getString("gouvernerat"));
                    salleFete.setVille(resultSet.getString("ville"));
                    salleFete.setAdresseExacte(resultSet.getString("adresseExacte"));
                    salleFete.setLatitude(resultSet.getDouble("latitude"));
                    salleFete.setLongitude(resultSet.getDouble("longitude"));
                    salleFete.setOptionInclus(resultSet.getString("optionInclus"));
                    return salleFete;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching SalleFete data: " + e.getMessage());
        }
        return null;
    }

    private Image getImageByOffreId(BigInteger offreId) {
        connection = Connexion.getInstance().getCnx();
        String query = "SELECT * FROM image WHERE offre_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, new BigDecimal(offreId));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Image image = new Image();
                    image.setImageURL(resultSet.getString("url"));
                    return image;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Image data: " + e.getMessage());
        }
        return null;
    }
    @FXML
    private void handleRetourButtonAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
            System.out.println("Navigué vers la page hello-view avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}