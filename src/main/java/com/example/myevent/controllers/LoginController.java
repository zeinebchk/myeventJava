package com.example.myevent.controllers;

import com.example.myevent.entities.User;
import com.example.myevent.entities.UserSession;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /*@FXML
    private ImageView image;*/
    @FXML
    private TextField email;

    @FXML
    private PasswordField mdp;

    @FXML
    private Label emailError;

    @FXML
    private Label mdpError;

    public Button btnCon;
    public static User UserConnected;
    public String key = "ThisIsASecretKey";
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* File brandingFile = new File("images/login.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        image.setImage(brandingImage);*/
        email.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'adresse e-mail est valide
            boolean isValidEmail = isValidEmail(newValue);

            // Mettre à jour le style en fonction de la validité de l'adresse e-mail
            if (isValidEmail) {
                email.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                emailError.setText("");
            } else {
                email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                emailError.setText("Veuillez saisir un email valide");
            }
        });


        email.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'utilisateur a terminé de saisir l'adresse e-mail
            if (!newValue) {
                // Si l'adresse e-mail est vide, appliquer le style rouge
                if (email.getText().isEmpty()) {
                    email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    emailError.setText("Veuillez saisir un email valide");
                } else {
                    // Sinon, vérifier à nouveau la validité de l'adresse e-mail
                    boolean isValidEmail = isValidEmail(email.getText());
                    if (isValidEmail) {
                        email.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                        emailError.setText("");
                    } else {
                        email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                        emailError.setText("Veuillez saisir un email valide");
                    }
                }
            }
        });
        mdp.textProperty().addListener((observable, oldValue, newValue) -> {
            // Validation du mot de passe
            boolean isValidPassword = isValidPassword(newValue);
            updateTextFieldStyle(mdp, isValidPassword);
        });

    }

    private void updateTextFieldStyle(TextField textField, boolean isValid) {
        // Met à jour le style du TextField en fonction de la validité du champ
        if (isValid) {
            textField.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
            mdpError.setText("");
        } else {
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            mdpError.setText("Veuillez saisir un mot de passe valide");

        }
    }
    private boolean isValidPassword(String password) {
        // Logique de validation du mot de passe (par exemple, longueur minimale)
        // Retourne true si le mot de passe est valide, sinon false
        return password.length() >= 8; // Exemple : longueur minimale de 8 caractères
    }
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier si l'email est valide
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Vérifier si l'email correspond à l'expression régulière
        return email.matches(emailRegex);
    }
    @FXML
    public void login(ActionEvent event) throws SQLException, IOException, Exception{
        FXMLLoader loader;
        if(email.getText().isEmpty()){
            email.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(email).play();}
         else if (!isValidEmail(email.getText())) {
            emailError.setText("Veuillez saisir un email valide");
            email.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            new animatefx.animation.Shake(email).play();
        }
         else{
             email.setStyle(null);
             emailError.setText("");
        }

        if(mdp.getText().isEmpty()){
            mdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
        new animatefx.animation.Shake(mdp).play();}
        else if(mdp.getText().length()<8){
            mdp.setStyle("-fx-border-color:red;-fx-border-width:1.5px;");
            mdpError.setText("le mot de passe doit etre superiere à 8");
            new animatefx.animation.Shake(mdp).play();
        }
        else{
            mdp.setStyle(null);
            mdpError.setText("");
            st = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            st.setString(1, email.getText());
            rs = st.executeQuery();
            if (rs.next()) {
                   String newHash = rs.getString("password").replaceFirst("^\\$2y\\$", "\\$2a\\$");
                   System.out.println(newHash);
                   if(BCrypt.checkpw(mdp.getText(),newHash)){
                    System.out.println("Password Matched!");
                    // Add your code here for successful password verification
                    UserConnected = new User();
                    UserConnected.setId(BigInteger.valueOf(rs.getInt("id")));
                    UserConnected.setNom(rs.getString("nom"));
                    UserConnected.setPrenom(rs.getString("prenom"));
                    UserConnected.setGenre(rs.getString("genre"));
                    UserConnected.setEmail(rs.getString("email"));
                    UserConnected.setNumTel(rs.getInt("numTel"));
                    UserConnected.setImage(rs.getString("image"));
                    UserSession.getInstance().setUser(UserConnected);

                    if (UserConnected.getGenre().equals("particulier")){
                        System.out.println("aaaaaaaa");
                       loader = new FXMLLoader(getClass().getResource("/Fxml/dashboardUser.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                    }
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    showAlert("Mot de passe incorrecte");
                }
            }
        else {
                showAlert("Utilisateur non trouvé");
            }
        }

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.show();
    }

    @FXML
    private void InscriptionPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

/*package com.example.myevent.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    public TextField email;
    public TextField mdp;
    @FXML
    public void login(ActionEvent event){
        System.out.println("aaaaaaaaaaaaaaa");
        if(email.getText().isEmpty()){
            email.setStyle("-fx-border-color:red;-fx-border-width:2px;");
            showAlert("Champ Email Vide", "Veuillez saisir votre email.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}*/
