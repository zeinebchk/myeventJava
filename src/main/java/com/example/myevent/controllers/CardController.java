package com.example.myevent.controllers;

import com.example.myevent.Services.EventService;
import com.example.myevent.Services.OffreService;
import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.OffreSession;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;

public class CardController implements Initializable {
    @FXML
    private Label addresse;

    @FXML
    private ImageView imageOffre;

    @FXML
    private Label invites;

    @FXML
    private Label prix;

    @FXML
    private Label titre;

    public static SalleFete salle;

    @FXML
    private ImageView group;
    OffreService offreService=new OffreService();
    EventService eventService=new EventService();
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();

    public void setData(SalleFete s){
        this.salle = s;
        addresse.setText(s.getAdresseExacte());
        titre.setText(s.getTitre());
        prix.setText("A partir de "+s.getPrixInitial()+ "DT");
        invites.setText(""+s.getCapacitePersonne());
        imageOffre.setImage(s.getImage());

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("images/group.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        group.setImage(brandingImage);
    }
    public Button btnCon;
    SalleFete s=new SalleFete();
    @FXML
    void afficherDetailOffre(ActionEvent event) throws IOException, SQLException {
        s=offreService.getSalleFeteByTitle(titre.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailCardOffre.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            DetailOffreController detail = loader.getController();
            detail.setInformation(s);


    }
    @FXML
    void verifierDispo(ActionEvent event) throws IOException, SQLException {
        SalleFete f =offreService.getSalleFeteByTitle(titre.getText());
        System.out.println("card controller"+f.toString());
        OffreSession.getInstance().setSalle(f);
        Set<Evennement> events=eventService.getEventsByClIent_id();
        if(events.isEmpty()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/eventForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popupEventExistant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }


    }
}
