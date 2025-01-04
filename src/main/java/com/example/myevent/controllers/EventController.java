package com.example.myevent.controllers;

import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.EvennementSession;
import com.example.myevent.entities.Event;
import com.example.myevent.Services.EventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class EventController implements Initializable {
    @FXML
    private TableColumn<Evennement, BigInteger> id;
    @FXML
    private TableView<Evennement> table_event;
    @FXML
    private TableColumn<Evennement, String> titre;
    @FXML
    private TableColumn<Evennement, Date> dateEvent;
    @FXML
    private TableColumn<Evennement, Time> heuredebutEvent;
    @FXML
    private TableColumn<Evennement, Time> heurefinEvent;
    @FXML
    private TableColumn<Evennement, Integer> nbInvites;
    @FXML
    private TableColumn<Evennement, String> gouvernerat;
    @FXML
    private TableColumn<Evennement, String> ville;
    @FXML
    private TableColumn<Evennement, String> adresse;
    @FXML
    private TableColumn<Evennement, Void> detailsColumn;

    @FXML
    private TextField tf_titre;
    @FXML
    private DatePicker tf_date;
    @FXML
    private TextField ft_hd;
    @FXML
    private TextField ft_hf;
    @FXML
    private TextField ft_nb;
    @FXML
    private TextField ft_gouvert;
    @FXML
    private TextField ft_ville;
    @FXML
    private TextField ft_adresseExacte;

    @FXML
    private Button btn_evenement;

    private final EventService eventService = new EventService();

    private Time convertirEnTime(TextField textField) {
        String texte = textField.getText(); // Récupérer le texte du champ de texte
        if (texte.isEmpty()) {
            return null; // Retourner null si le champ de texte est vide
        }
        // Convertir le texte en LocalTime
        return Time.valueOf(LocalTime.parse(texte)); // Utilisation directe de LocalTime.parse()
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuration des PropertyValueFactory pour les colonnes de la TableView
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateEvent.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        heuredebutEvent.setCellValueFactory(new PropertyValueFactory<>("heuredebutEvent")); // Utilisation du nom correct de l'attribut
       // heurefinEvent.setCellValueFactory(new PropertyValueFactory<>("heurefinEvent"));
        nbInvites.setCellValueFactory(new PropertyValueFactory<>("nbInvites"));
        gouvernerat.setCellValueFactory(new PropertyValueFactory<>("gouvernerat"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresseExacte"));

        addButtonToTable();
        loadUserData();
    }

    private void loadUserData() {
        ObservableList<Evennement> events = FXCollections.observableArrayList();
        try {
            Set<Evennement> eventList = eventService.getEventsByClIent_id();
            if (eventList != null) {
                for (Evennement event : eventList) {
                    if (event != null) {
                        events.add(event);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table_event.setItems(events);
    }

    private void addButtonToTable() {
        Callback<TableColumn<Evennement, Void>, TableCell<Evennement, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Evennement, Void> call(final TableColumn<Evennement, Void> param) {
                final TableCell<Evennement, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Evennement data = getTableView().getItems().get(getIndex());
                            try {
                                showEventDetails(event,data);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        detailsColumn.setCellFactory(cellFactory);
    }

    private void showEventDetails(ActionEvent event, Evennement e) throws IOException {
        EvennementSession.getInstance().setEvent(e);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailEvennement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        // Remplacer ce code par l'ouverture d'une nouvelle fenêtre pour afficher les détails de l'événement
       /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText("Details de l'événement");
        alert.setContentText("Titre: " + event.getTitre() + "\nDate: " + event.getDateEvent() +
                "\nHeure de début: " + event.getHeureDebutEvent() +
                "\nHeure de fin: " + event.getHeureFinEvent() +
                "\nNombre d'invités: " + event.getNbInvites() +
                "\nGouvernerat: " + event.getGouvernerat() +
                "\nVille: " + event.getVille() +
                "\nAdresse: " + event.getAdresseExacte());
        alert.showAndWait();*/
    }



    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void afficherMenu(ActionEvent event) {

    }
}