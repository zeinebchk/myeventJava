package com.example.myevent.controllers;


import com.example.myevent.Models.User;
import com.example.myevent.tools.Connexion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button close;

    private Connexion database = Connexion.getInstance();


    @FXML
    void handleLogin(ActionEvent event) {
        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
        Connection connect = database.getCnx();
        PreparedStatement prepare = null;
        ResultSet result = null;
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, usernameField.getText());
            prepare.setString(2, passwordField.getText());
            result = prepare.executeQuery();

            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill all blank fields");
            } else {
                if (result.next()) {
                    // Authentification réussie
                    showAlert(Alert.AlertType.INFORMATION, "Information Message", "Successfully Login!");

                    // Fermer la fenêtre de connexion
                    loginBtn.getScene().getWindow().hide();

                    // Charger le tableau de bord
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid username or password");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while logging in");
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (result != null) result.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

