package com.example.myevent.controllers;

import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailEvennementController implements Initializable {
    @FXML
    private Label dateDebut;

    @FXML
    private Label dateReservation;

    @FXML
    private GridPane grid;

    @FXML
    private Label nbInvites;

    @FXML
    private Label titre;
    public static User UserConnected;
    public String key = "ThisIsASecretKey";
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    private List<Reservation> res=new ArrayList<>();

    public List<Reservation> getData() throws SQLException {
        Evennement e= EvennementSession.getInstance().getEvent();
        System.out.println("hhhhhh");

        List<Reservation> salles=new ArrayList<>();
        SalleFete salle;
        st = con.prepareStatement("SELECT * FROM evennements e " +
                "JOIN offre_event of ON e.id = of.event_id " +
                "JOIN offre o ON o.id = of.offre_id " +
                "JOIN entrepreneurs en ON en.id = o.entrepreneur_id " +
                "JOIN image i ON i.offre_id = o.id " +
                "JOIN reservations r ON r.offre_id = o.id " +
                "WHERE e.id = ? " +
                "AND r.dateReservation = ? " +
                "AND r.client_id = ?");
        st.setBigDecimal(1, new BigDecimal(e.getId()));
        st.setDate(2, e.getDateEvent());
        st.setBigDecimal(3, new BigDecimal(UserSession.getInstance().getUser().getId()));
        rs = st.executeQuery();
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            Reservation s=new Reservation();
            s.getOffre_id().setTitre(rs.getString("o.titre"));
            s.getOffre_id().setId(rs.getBigDecimal("o.id").toBigInteger());
            s.getOffre_id().setDescription(rs.getString("description"));
            s.getOffre_id().setPrixInitial(rs.getDouble("prixInitial"));
            s.getOffre_id().setDescription(rs.getString("description"));

             /* Entrepreneur entrepreneur = getEntrepreneurFromResultSet(rs.getBigDecimal("entrepreneur_id"));
              s.setEntrepreneur_id(entrepreneur);*/
            if (!salles.contains(s)) {
                salles.add(s);
            }
            System.out.println("hhhhhh");
        }
        System.out.println("Nombre de lignes retournées : " + rowCount);
        for(int i=0;i<salles.size();i++){
            System.out.println(salles.get(i).toString());
        }
        return salles;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            res.addAll(getData());
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < res.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/cardOffreEvent.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    CardOffreEventController itemController = fxmlLoader.getController();
                    itemController.setData(res.get(i));

                    if (column == 2) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row); //(child,column,row)
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
