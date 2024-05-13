package com.example.myevent.controllers;

import com.example.myevent.entities.Reservation;
import com.example.myevent.entities.SalleFete;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CardOffreEventController {

    @FXML
    private Text description;

    @FXML
    private ImageView img;

    @FXML
    private Label titre;
    Reservation s=new Reservation();
    public void setData(Reservation s ){

        this.s = s;
        titre.setText(s.getOffre_id().getTitre());
        description.setText("Votre demande est en cours de traitement en attendant la confirmation ,merci de restez joignable");

    }
}
