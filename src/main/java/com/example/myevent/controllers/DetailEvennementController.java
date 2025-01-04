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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
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
        Evennement e = EvennementSession.getInstance().getEvent();
        titre.setText(e.getTitre());
        dateReservation.setText(e.getDateEvent().toString());
        dateDebut.setText(e.getHeuredebutEvent().toString());
        nbInvites.setText(e.getNbInvites() + "");
        System.out.println("hhhhhh");

        List<Reservation> reservationList = new ArrayList<>();

        // Préparez votre requête SQL
        st = con.prepareStatement("SELECT * FROM offre_event oe " +
                "JOIN offre o ON o.id = oe.offre_id " +
                "JOIN reservations r ON r.offre_id = o.id " +
                "WHERE oe.event_id = ? " +
                "AND r.dateReservation = ? " +
                "AND r.client_id = ?");

        // Définir les paramètres de la requête
        st.setBigDecimal(1, new BigDecimal(e.getId()));
        st.setDate(2, e.getDateEvent());  // Utilisation de java.sql.Date pour la conversion
        st.setBigDecimal(3, new BigDecimal(UserSession.getInstance().getUser().getId()));

        rs = st.executeQuery();

        while (rs.next()) {
            // Récupérer les informations de la base de données
            double prixInitial = rs.getDouble("prixInitial");
            double prixRemise = rs.getDouble("prixRemise");  // Récupérer la valeur de prixRemise depuis la base de données
            java.sql.Date sqlDate = rs.getDate("dateFinRemise");
            LocalDate dateFinRemise = (sqlDate != null) ? sqlDate.toLocalDate() : null;  // Convertir la date SQL en LocalDate

            // Créer l'objet Offre avec les informations récupérées
            Offre offre = new Offre(
                    rs.getInt("id"),
                    rs.getString("titre"));

            // Créer l'objet Reservation et le remplir avec les données récupérées
            Reservation s = new Reservation();
            s.setOffre_id(offre);  // Assigner l'objet Offre à la réservation
            s.setStatus(rs.getString("status"));
            s.setId(rs.getBigDecimal("r.id").toBigInteger());  // ID de la réservation

            // Ajouter à la liste de réservations si l'objet n'est pas déjà présent
            if (!reservationList.contains(s)) {
                reservationList.add(s);
            }
        }

        return reservationList;
    }

    @FXML
    void affichMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
