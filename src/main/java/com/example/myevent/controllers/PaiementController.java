package com.example.myevent.controllers;

import com.example.myevent.entities.OffreAPayer;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class PaiementController {

    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    @FXML
    private PasswordField mdp;

    @FXML
    private Label mdpError;

    @FXML
    private TextField mnt;

    @FXML
    private Label mntError;

    @FXML
    private TextField nomCarte;

    @FXML
    private Label nomError;

    @FXML
    private TextField numCarte;

    @FXML
    private Label numError;

    @FXML
    void payer(ActionEvent event) throws SQLException, IOException {
        if(nomCarte.getText().isEmpty()){
            nomError.setText("le nom de la carte est obligatoire");
            nomCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(nomCarte).play();
        }else{
            nomCarte.setStyle(null);

        }
        if(numCarte.getText().isEmpty()){
            numError.setText("le numero de la carte est obligatoire");
            numCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(numCarte).play();
        }else if (!numCarte.getText().matches("\\d*")){
            numError.setText("le numero de la carte doit etre des chiffres");
            numCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(numCarte).play();
        }else if ((numCarte.getText()).length()!=16){
            numError.setText("le numero de la carte doit etre de 16 chiffes");
            numCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(numCarte).play();
        }
        if((mdp.getText()).isEmpty()){
            mdpError.setText("le mot d epasse de la carte est obligatoire");
            mdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(mdp).play();
        }
        else if((mdp.getText()).length()!=4){
            mdpError.setText("le mot d epasse de la carte doit etre de 4 chiffes");
            mdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(mdp).play();
        }
        if(mnt.getText().isEmpty()){
            mntError.setText("le montant est obligatoire");
            numCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(numCarte).play();
        }
        else if(Double.parseDouble(mnt.getText()) < OffreAPayer.getInstance().getReservation().getOffre_id().getPrixInitial()*0.2){
            mntError.setText("l'avance a payer doit etre supereur ou égale au 20% de prix de l'offre "+OffreAPayer.getInstance().getReservation().getOffre_id().getPrixInitial()*0.2+ "DT");
            numCarte.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(numCarte).play();
        }
        if(!mnt.getText().isEmpty() && !mdp.getText().isEmpty() && (mdp.getText()).length()==4 &&
         Double.parseDouble(mnt.getText()) >= OffreAPayer.getInstance().getReservation().getOffre_id().getPrixInitial()*0.2
         && !numCarte.getText().isEmpty() && (numCarte.getText()).length()==16 && !nomCarte.getText().isEmpty()) {
            System.out.println("aaaaaaaa");
            String req = "UPDATE reservations SET status=? , avanceClient=? WHERE id=? ";
            PreparedStatement stmt = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, "confirme");
            stmt.setDouble(2, Double.parseDouble(mnt.getText()));
            stmt.setBigDecimal(3, new BigDecimal(OffreAPayer.getInstance().getReservation().getId()));
            int result = stmt.executeUpdate();
            if (result > 0) {
                System.out.println("bbbbb");
                ResultSet rs = stmt.getGeneratedKeys();
                System.out.println(result + " enregistrement ajouté.");
                showAlert("votre demande a ete confirme avec succées.");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detailEvennement.fxml"));

                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }


    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }

}
