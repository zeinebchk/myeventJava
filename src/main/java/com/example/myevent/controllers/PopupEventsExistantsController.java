package com.example.myevent.controllers;

import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.entities.UserSession;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PopupEventsExistantsController implements Initializable {
    @FXML
    private GridPane grid;
    @FXML
    private Label offre;
    BigInteger id = UserSession.getInstance().getUser().getId();
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    private List<Evennement> events=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            events.addAll(getData());
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < events.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/cardEvent.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    CardEventController itemController = fxmlLoader.getController();
                    itemController.setData(events.get(i));

                    if (column == 3) {
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

                    GridPane.setMargin(anchorPane, new Insets(7));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Evennement> getData() throws SQLException {
        List<Evennement> events=new ArrayList<>();
        st = con.prepareStatement("SELECT * from evennements where client_id=?");
        st.setLong(1, id.longValue());
        rs = st.executeQuery();
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            Evennement s=new Evennement();
            s.setTitre(rs.getString("titre"));
            s.setDateEvent(rs.getDate("dateEvent"));
            s.setHeuredebutEvent(rs.getTime("heureDebutEvent"));
            s.setHeureFinEvent(rs.getTime("heureFinEvent"));
            s.setNbInvites(rs.getInt("nbInvites"));
            s.setAdresseExacte(rs.getString("adresseExacte"));
            s.setGouvernerat(rs.getString("gouvernerat"));
            s.setVille(rs.getString("ville"));
            s.setAdresseExacte(rs.getString("adresseExacte"));
            if (!events.contains(s)) {
                events.add(s);
            }
            System.out.println("hhhhhh");
        }
        System.out.println("Nombre de lignes retournées : " + rowCount);
        for(int i=0;i<events.size();i++){
            System.out.println(events.get(i).toString());
        }
        return events;
    }
    public void setOffre(SalleFete f){

        offre.setText(String.valueOf(f.getId()));
    }
    @FXML
    void afficherFormEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
