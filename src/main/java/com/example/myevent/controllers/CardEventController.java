package com.example.myevent.controllers;
import com.example.myevent.Services.EventService;
import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import com.mysql.cj.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Authenticator;
import java.sql.*;
import java.util.Properties;

public class CardEventController {
    @FXML
    private Label date;

    @FXML
    private Label nbInvites;

    @FXML
    private Label titre;
    @FXML
    private Label offre;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    public static Evennement event;
    EventService es = new EventService();
    public void setData(Evennement event){
        this.event=event;
        date.setText(event.getDateEvent().toString());
        titre.setText(event.getTitre());
        nbInvites.setText(event.getNbInvites()+"");

    }
    @FXML
    void ajouterOffreAuEvennement(ActionEvent event) throws SQLException, IOException {

        Evennement e=new Evennement();
        st = con.prepareStatement("SELECT * FROM evennements  WHERE titre = ?");
        st.setString(1, titre.getText());
        rs = st.executeQuery();
        if (rs.next()) {
            e.setId(rs.getBigDecimal("id").toBigInteger());
            e.setTitre(rs.getString("titre"));
            e.setHeuredebutEvent(rs.getTime("heureDebutEvent"));
            e.setHeureFinEvent(rs.getTime("heureFinEvent"));
            e.setDateEvent(rs.getDate("dateEvent"));
            e.setNbInvites(rs.getInt("nbInvites"));
            }
        System.out.println(e.toString());
        if(es.chercherOffreDansEvent(e.getId(),OffreSession.getInstance().getSalle().getId())){
            showWorningAlert("cette offre existe deja dans votre evennement ");
        }
       else{
        String req = "insert into offre_event(event_id,offre_id)values(?,?)";
        PreparedStatement stmt = con.prepareStatement(req);
        stmt.setBigDecimal(1, new BigDecimal(e.getId()));
        stmt.setBigDecimal(2,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
        int result = stmt.executeUpdate();
        if (result>0) {
          System.out.println("ajouter au offreEvent avec succées");
    }
        String req2 = "insert into reservations(status,heureDebut,heureFin,dateReservation,avanceClient,offre_id,client_id)values(?,?,?,?,?,?,?)";
        PreparedStatement stmt2 = con.prepareStatement(req2);
        stmt2.setString(1, "enAttente");
        stmt2.setTime(2,e.getHeuredebutEvent());
        stmt2.setTime(3,e.getHeureFinEvent());
        stmt2.setDate(4, Date.valueOf(e.getDateEvent()));
        stmt2.setInt(5,0);
        stmt2.setBigDecimal(6,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
        stmt2.setBigDecimal(7,new BigDecimal(UserSession.getInstance().getUser().getId()));
        int result2 = stmt2.executeUpdate();
        if (result2>0) {
            showAlert("Votre demande a éte enregistré avec succés");
            EvennementSession.getInstance().setEvent(e);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailEvennement.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
       }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }
    private void showWorningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.show();
    }


}
