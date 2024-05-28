package com.example.myevent.controllers;

import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
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
    private ImageView date;

    @FXML
    private Label dateDebut;

    @FXML
    private Label dateReservation;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView heuredeb;

    @FXML
    private ImageView invites;

    @FXML
    private Label nbInvites;

    @FXML
    private Label titre;

    @FXML
    private ImageView titreevent;
    public static User UserConnected;
    public String key = "ThisIsASecretKey";
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    private List<Reservation> res=new ArrayList<>();

    public List<Reservation> getData() throws SQLException {
        Evennement e= EvennementSession.getInstance().getEvent();
        titre.setText(e.getTitre());
        dateReservation.setText(e.getDateEvent().toString());
        dateDebut.setText(e.getHeuredebutEvent().toString());
        nbInvites.setText(e.getNbInvites()+"");
        System.out.println("hhhhhh");

        List<Reservation> reservationList=new ArrayList<>();
        SalleFete salle;
        st = con.prepareStatement("SELECT * FROM offre_event oe " +
                        "JOIN offre o ON o.id = oe.offre_id "+
                        "JOIN reservations r ON r.offre_id = o.id "+
                       // "JOIN entrepreneurs on o.entrepreneur_id= entrepreneurs.id"+
                        //"JOIN users on entrepreneurs.user_id = users.id"+
                        "WHERE oe.event_id = ? "+
                        "AND r.dateReservation = ? " +
                        "AND r.client_id = ?");
        st.setBigDecimal(1, new BigDecimal(e.getId()));
        st.setDate(2, e.getDateEvent());
        st.setBigDecimal(3, new BigDecimal(UserSession.getInstance().getUser().getId()));

        rs = st.executeQuery();
        while (rs.next()) {
            Reservation s=new Reservation();
            s.setOffre_id(new Offre());
            s.getOffre_id().setTitre(rs.getString("titre"));
            s.getOffre_id().setPrixInitial(rs.getDouble("prixInitial"));
            s.setStatus(rs.getString("status"));
            s.setId(rs.getBigDecimal("r.id").toBigInteger());

            if (!reservationList.contains(s)) {
                reservationList.add(s);
            }
        }
      /*  st = con.prepareStatement("SELECT * FROM offre"
                +
                "offre_event oe " +
                "JOIN offre o ON o.id = oe.offre_id " +
                "JOIN entrepreneurs en ON en.id = o.entrepreneur_id " +
                "JOIN image i ON i.offre_id = o.id " +
                "JOIN reservations r ON r.offre_id = o.id " +
                "WHERE oe.event_id = ? " +
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
            s.getOffre_id().setTitre(rs.getString("titre"));
            s.getOffre_id().setId(rs.getBigDecimal("id").toBigInteger());
            s.getOffre_id().setDescription(rs.getString("description"));
            s.getOffre_id().setPrixInitial(rs.getDouble("prixInitial"));


           if (!salles.contains(s)) {
                salles.add(s);
            }
            System.out.println("FFFF");
        }*/
       // System.out.println("Nombre de lignes retournées : " + rowCount);
        for(int i=0;i<reservationList.size();i++){
            System.out.println(reservationList.get(i).toString());
        }
        return reservationList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile1 = new File("images/party.png");
        javafx.scene.image.Image brandingImage1 = new Image(brandingFile1.toURI().toString());
        titreevent.setImage(brandingImage1);
        File brandingFile2 = new File("images/calendar-event.png");
        javafx.scene.image.Image brandingImage2 = new Image(brandingFile2.toURI().toString());
        date.setImage(brandingImage2);
        File brandingFile3 = new File("images/clock.png");
        javafx.scene.image.Image brandingImage3 = new Image(brandingFile3.toURI().toString());
        heuredeb.setImage(brandingImage3);
        File brandingFile4 = new File("images/people.png");
        javafx.scene.image.Image brandingImage4 = new Image(brandingFile4.toURI().toString());
        invites.setImage(brandingImage4);

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
    @FXML
    void affichMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
