package com.example.myevent.controllers;
import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.OffreSession;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.entities.UserSession;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            e.setHeuredebutEvent(rs.getTime("heureDebutEvent"));
            e.setHeureFinEvent(rs.getTime("heureFinEvent"));
            e.setDateEvent(rs.getDate("dateEvent"));
            }

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
        stmt2.setDate(4,e.getDateEvent());
        stmt2.setInt(5,0);
        stmt2.setBigDecimal(6,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
        stmt2.setBigDecimal(7,new BigDecimal(UserSession.getInstance().getUser().getId()));
        int result2 = stmt2.executeUpdate();
        if (result2>0) {
            showAlert("Votre demande a éte enregistré avec succés");
        }

    }
    private void sendEmail(String email){
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myEmail="zeinebchekir742@gmail.com";
        String password="vxwe vwaf uqqx lkak";

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }
}
