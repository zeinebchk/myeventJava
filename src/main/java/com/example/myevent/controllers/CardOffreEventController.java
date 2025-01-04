package com.example.myevent.controllers;

import com.example.myevent.entities.OffreAPayer;
import com.example.myevent.entities.Reservation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CardOffreEventController {

    @FXML
    private Text description;

    @FXML
    private ImageView img;

    @FXML
    private Label titre;
    @FXML
    private Label idRes;
    @FXML
    private Button payment;
    Reservation s=new Reservation();
    public void setData(Reservation s ){
        this.s = s;
        idRes.setText(s.getId().toString());
        titre.setText(s.getOffre_id().getTitre());
        Image image = new Image(getClass().getResourceAsStream("/images/salle.jpg"));
        img.setImage(image);
        if(s.getStatus().equals("enAttente")) {
            description.setText("Votre demande est en cours de traitement en attendant la confirmation ,merci de restez joignable");
        }
        else if(s.getStatus().equals("enCours")){
            payment.setVisible(true);
            description.setText("Votre demande a été confirmé,pour valider votre demande vous devez payer une avance !");
        }
        else if(s.getStatus().equals("confirme")){
            description.setText("Votre demande a été confirmé ,vous pouvez appeler l'entrepreneur via 25 094 874 pour discuter les details !");
        }
        else{
            description.setText("Malheureusement votre demande a ete refusé !");
        }

    }
    @FXML
    public void paiement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paiement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        OffreAPayer.getInstance().setReservation(s);
    }
}
