package com.example.myevent.controllers;

import com.example.myevent.entities.Event;
import com.example.myevent.Services.EventService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    @FXML
    private TableView<Event> table_event;
    @FXML
    private TableColumn<Event, String> titre;
    @FXML
    private TableColumn<Event, LocalDate> dateEvent;
    @FXML
    private TableColumn<Event, Time> heuredebutEvent;
    @FXML
    private TableColumn<Event, Time>heureFinEvent;
    @FXML
    private TableColumn<Event, Integer> nbInvites;
    @FXML
    private TableColumn<Event, String> gouvernerat;
    @FXML
    private TableColumn<Event, String> ville;
    @FXML
    private TableColumn<Event, String> adresse;

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
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateEvent.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        heuredebutEvent.setCellValueFactory(new PropertyValueFactory<>("heureDebutEvent")); // Utilisation du nom correct de l'attribut
        heureFinEvent.setCellValueFactory(new PropertyValueFactory<>("heureFinEvent"));
        nbInvites.setCellValueFactory(new PropertyValueFactory<>("nbInvites"));
        gouvernerat.setCellValueFactory(new PropertyValueFactory<>("gouvernerat"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresseExacte"));

        // Création des objets Time à partir des champs de texte
        Time heureDebut = convertirEnTime(ft_hd);
        Time heureFin = convertirEnTime(ft_hf);

        loadUserData();

        table_event.setOnMouseClicked(event -> selectEvent(event));
    }

    private void loadUserData() {
        ObservableList<Event> events = FXCollections.observableArrayList();
        try {
            List<Event> eventList = eventService.afficher();
            if (eventList != null) {
                for (Event event : eventList) {
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

    @FXML
    private void addUser(ActionEvent event) {
        String titre = tf_titre.getText();
        LocalDate dateEvent = tf_date.getValue();
        Time heuredebutEvent = Time.valueOf(ft_hd.getText());
        Time heureFinEvent = Time.valueOf(ft_hf.getText());
        Integer nbInvites = Integer.valueOf(ft_nb.getText());
        String gouvernerat = ft_gouvert.getText();
        String ville = ft_ville.getText();
        String adresseExacte = ft_adresseExacte.getText();

        if (titre.isEmpty() || dateEvent == null || heuredebutEvent == null || heureFinEvent == null || nbInvites == null || gouvernerat.isEmpty() || ville.isEmpty() || adresseExacte.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Ajout impossible", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            Event events = new Event(0, titre, dateEvent, heuredebutEvent, heureFinEvent, nbInvites, gouvernerat, ville, adresseExacte);
            eventService.ajouter(events);
            showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Événement ajouté avec succès.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    private void selectEvent(javafx.scene.input.MouseEvent mouseEvent) {
        // Logique de sélection d'un événement
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}