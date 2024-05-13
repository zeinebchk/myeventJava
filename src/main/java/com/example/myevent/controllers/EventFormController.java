package com.example.myevent.controllers;

import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.OffreSession;
import com.example.myevent.entities.UserSession;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.sql.Time.valueOf;

public class EventFormController implements Initializable {
    @FXML
    private TextField adresse;

    @FXML
    private DatePicker date;

    @FXML
    private Label erreurDate;

    @FXML
    private Label erreurHF;

    @FXML
    private Label erreurInvites;

    @FXML
    private Label erreurTitre;

    @FXML
    private Label errorHD;

    @FXML
    private ComboBox<String> gouvs;

    @FXML
    private TextField heureDebut;

    @FXML
    private TextField heureFin;

    @FXML
    private TextField invites;

    @FXML
    private TextField titre;

    @FXML
    private ComboBox<String> villes;

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gouvs.getItems().add("Sfax");
        gouvs.getItems().add("Tunis");
        gouvs.getItems().add("Monastir");
        gouvs.getItems().add("Sousse");
        gouvs.setValue("Monastir");
        villes.getItems().addAll("Monastir", "Moknine", "Sahline");
        villes.setValue("Monastir");
        gouvs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Effacer les anciennes villes
            villes.getItems().clear();
            // Ajouter les nouvelles villes en fonction du gouvernorat sélectionné
            switch (newValue) {
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
                    // Par défaut, ne rien faire
                    break;
            }
        });
    }
    public static boolean isValidTime(String timeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(timeString, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @FXML
    void reserverNouvEvent(ActionEvent event) throws SQLException {
       if(titre.getText().isEmpty()){
           erreurTitre.setText("le titre est obligatoire");
       }else {
           erreurTitre.setText(""); // Efface le message d'erreur s'il y en avait un
       }
        if(heureDebut.getText().isEmpty()){
            errorHD.setText("l'heure de debut est obligatoire");
        }
        else if(!isValidTime(heureDebut.getText())){
            errorHD.setText("l'heure doit etre sous format hh:mm:ss");
        }
        else {
            errorHD.setText(""); // Efface le message d'erreur s'il y en avait un
        }
        if(heureFin.getText().isEmpty()){
            erreurHF.setText("l'heure de fin est obligatoire");
        }
        else if(!isValidTime(heureFin.getText())){
            erreurHF.setText("l'heure doit etre sous format hh:mm:ss");
        }else {
            erreurHF.setText(""); // Efface le message d'erreur s'il y en avait un
        }
        if(date.getValue()==null){
            erreurDate.setText("la date est obligatoire");
        }else {
            erreurDate.setText(""); // Efface le message d'erreur s'il y en avait un
        }
        if(invites.getText().isEmpty()){
            erreurInvites.setText("Veuillez saisir le nombre des invités");
        }else {
            erreurInvites.setText(""); // Efface le message d'erreur s'il y en avait un
        }
        if(!titre.getText().isEmpty() && !invites.getText().isEmpty() && date.getValue()!=null && isValidTime(heureDebut.getText()) && isValidTime(heureFin.getText())){
            Evennement e=new Evennement();
            String req2 = "insert into evennements(titre,dateEvent,heureDebutEvent,heureFinEvent,nbInvites,gouvernerat,ville,adresseExacte,client_id)values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt2 = con.prepareStatement(req2);
            stmt2.setString(1, titre.getText());
            stmt2.setDate(2, Date.valueOf(date.getValue()));
            stmt2.setTime(3, valueOf(heureDebut.getText()));
            stmt2.setTime(4, valueOf(heureDebut.getText()));
            stmt2.setInt(5, Integer.parseInt(invites.getText()));
            stmt2.setString(6,gouvs.getValue());
            stmt2.setString(7,villes.getValue());
            stmt2.setString(8,adresse.getText());
            stmt2.setBigDecimal(9,new BigDecimal(UserSession.getInstance().getUser().getId()));
            int result2 = stmt2.executeUpdate();
            if (result2>0) {
                e.setId(rs.getBigDecimal("id").toBigInteger());

                String req = "insert into offre_event(event_id,offre_id)values(?,?)";
                PreparedStatement stmt = con.prepareStatement(req);
                stmt.setBigDecimal(1, new BigDecimal(e.getId()));
                stmt.setBigDecimal(2,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
                int result = stmt.executeUpdate();
                if (result>0) {
                    System.out.println("ajouter au offreEvent avec succées");
                }
                String req3 = "insert into reservations(status,heureDebut,heureFin,dateReservation,avanceClient,offre_id,client_id)values(?,?,?,?,?,?,?)";
                PreparedStatement stmt3 = con.prepareStatement(req2);
                stmt3.setString(1, "enAttente");
                stmt3.setTime(2,e.getHeuredebutEvent());
                stmt3.setTime(3,e.getHeureFinEvent());
                stmt3.setDate(4,e.getDateEvent());
                stmt3.setInt(5,0);
                stmt3.setBigDecimal(6,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
                stmt3.setBigDecimal(7,new BigDecimal(UserSession.getInstance().getUser().getId()));
                int result3 = stmt3.executeUpdate();
                if (result3>0) {
                    showAlert("Votre demande a éte enregistré avec succés");
                }
            }
        }


    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }
}
