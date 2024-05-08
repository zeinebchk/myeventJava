package com.example.myevent.controllers;
import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.OffreSession;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            }

        String req = "insert into offre_event(event_id,offre_id)values(?,?)";
        PreparedStatement stmt = con.prepareStatement(req);
        stmt.setBigDecimal(1, new BigDecimal(e.getId()));
        stmt.setBigDecimal(2,new BigDecimal(OffreSession.getInstance().getSalle().getId()));
        int result = stmt.executeUpdate();
        if (result>0) {
          System.out.println("ajouter avec succées");
    }}
}
