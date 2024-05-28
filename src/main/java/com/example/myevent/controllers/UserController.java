package com.example.myevent.controllers;

import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    private Connection connection;

    public void initialize() {
        // Récupérer la connexion depuis MyConnection
        connection = Connexion.getInstance().getCnx();
    }

    @FXML
    void handleLogin(ActionEvent event) {
        if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error Message", "Please fill in all fields.");
            return;
        }

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement prepare = connection.prepareStatement(sql)) {
            prepare.setString(1, emailField.getText());
            prepare.setString(2, passwordField.getText());

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    // Authentification réussie, charger la nouvelle scène
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dashborda/dashbord.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error Message", "Invalid username or password.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error Message", "An error occurred while logging in.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void loginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signupA.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
