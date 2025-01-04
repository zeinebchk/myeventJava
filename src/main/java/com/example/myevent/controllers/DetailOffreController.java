package com.example.myevent.controllers;

import com.example.myevent.entities.SalleFete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailOffreController implements Initializable {
    @FXML
    private Label adresse;
    @FXML
    private Label ville;
    @FXML
    private Label description;

    @FXML
    private Label gouvernerat;

    @FXML
    private Label nbInvites;

    @FXML
    private Label options;

    @FXML
    private Label surface;

    @FXML
    private Label titre;

    @FXML
    private Label titre1;

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    public void setInformation(SalleFete s){
       titre.setText(s.getTitre());
       titre1.setText(s.getTitre());
       description.setText(s.getDescription());
       surface.setText(s.getSurface()+"m");
       nbInvites.setText(s.getCapacitePersonne()+"personnes");
       options.setText(s.getOptionInclus());
       gouvernerat.setText(s.getGouvernerat());
       ville.setText(s.getVille());
       adresse.setText(s.getAdresseExacte());
        Image image = new Image(getClass().getResourceAsStream("/images/salle.jpg"));
        img.setImage(image);

    }
    @FXML
    void retourListeOffre(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboardUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
