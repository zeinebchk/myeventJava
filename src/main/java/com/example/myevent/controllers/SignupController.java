package com.example.myevent.controllers;

import animatefx.animation.BounceInDown;
import animatefx.animation.Shake;
import com.example.myevent.ExistUserException;
import com.example.myevent.entities.User;
import com.example.myevent.Services.GestionUser;
import com.example.myevent.tools.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class SignupController implements Initializable {
    @FXML
    private Label confirmError;
    @FXML
    private Label emailError;
    @FXML
    private Label mdpError;
    @FXML
    private Label numTelError;
    @FXML
    private TextField email;
    @FXML
    private TextField nom;
    @FXML
    private TextField numTel;
    @FXML
    private Label labelCategories;
    @FXML
    private TextField prenom;
    @FXML
    private TextField mdp;
    @FXML
    private TextField confirmMdp;
    @FXML
    private Label imageName;

    @FXML
    private Label labelGouv;

    @FXML
    private Label labelVille;

    @FXML
    private TextField adresse;

    @FXML
    private TextField nomProjet;

    @FXML
    private TextField numTelPro;
    @FXML
    private ChoiceBox<String> gouvernerats;

    @FXML
    private ChoiceBox<String> genres;

    @FXML
    private ChoiceBox<String> villes;

    @FXML
    private ChoiceBox<String> categories;

    private String imageData;
    public String key = "ThisIsASecretKey";
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    GestionUser gu=new GestionUser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour le style en fonction de la validité de l'adresse e-mail
            if (!nom.getText().isEmpty()) {
                nom.setStyle("-fx-border-color: green; -fx-border-width: 2px;");

            } else {
                nom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

            }
        });
        prenom.textProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour le style en fonction de la validité de l'adresse e-mail
            if (!prenom.getText().isEmpty()) {
                prenom.setStyle("-fx-border-color: green; -fx-border-width: 2px;");

            } else {
                prenom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

            }
        });
        nom.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'utilisateur a terminé de saisir l'adresse e-mail
            if (!newValue) {
                // Si l'adresse e-mail est vide, appliquer le style rouge
                if (nom.getText().isEmpty()) {
                    nom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                } else {
                        nom.setStyle("-fx-border-color: green; -fx-border-width: 2px;");

                    }

            }
        });
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
        numTel.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'adresse e-mail est valide
            boolean isValidNum = isValidNumero(newValue);

            // Mettre à jour le style en fonction de la validité de l'adresse e-mail
            if (isValidNum) {
                numTel.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                numTelError.setText("");
            } else {
                numTel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                numTelError.setText("Veuillez saisir un numéro valide egale à 8");
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

        confirmMdp.textProperty().addListener((observable, oldValue, newValue) -> {
            // Vérifier si l'adresse e-mail est valide
            boolean isValidPassword = isValidPassword(newValue);

            // Mettre à jour le style en fonction de la validité de l'adresse e-mail
            if (isValidPassword) {
                if(confirmMdp.getText().equals(mdp.getText())){
                confirmMdp.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                confirmError.setText("");
            } else {
                confirmMdp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                confirmError.setText("Veuillez saisir un mot de passe conforme");
            }}
                else{
                confirmMdp.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                confirmError.setText("Veuillez saisir un mot de passe valide >=8");
            }

        });

        categories.getItems().addAll("Salle des fetes","traiteur","troupe","coiffure");
        genres.getItems().add("particulier");
        genres.getItems().add("professionnelle");
        genres.setValue("particulier");
        gouvernerats.getItems().add("Sfax");
        gouvernerats.getItems().add("Tunis");
        gouvernerats.getItems().add("Monastir");
        gouvernerats.getItems().add("Sousse");
        gouvernerats.setValue("Monastir");
        villes.getItems().addAll("Monastir", "Moknine", "Sahline");
        villes.setValue("Monastir");
        gouvernerats.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Effacer les anciennes villes
            villes.getItems().clear();
            // Ajouter les nouvelles villes en fonction du gouvernorat sélectionné
            switch (newValue) {
                case "Sfax":
                    villes.getItems().addAll("Sfax Ville", "Sakiet Eddaier", "Sakiet Ezzit");
                    break;
                case "Tunis":
                    villes.getItems().addAll("Tunis", "Ariana", "Ben Arous");
                    break;
                case "Monastir":
                    villes.getItems().addAll("Monastir", "Moknine", "Sahline");
                    break;
                case "Sousse":
                    villes.getItems().addAll("Sousse", "Hammam Sousse", "Kalaa Kebira");
                    break;
                default:
                    // Par défaut, ne rien faire
                    break;
            }
        });

        genres.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
            // Rendre le TextField visible si le genre est "professionnelle", sinon le rendre invisible
            boolean isProfessionnelle = "professionnelle".equals(newValue);
            if(isProfessionnelle){
                nomProjet.setVisible(true);
                new BounceInDown(nomProjet).play();

            numTelPro.setVisible(true);
                new BounceInDown(numTelPro).play();
                categories.setVisible(true);
                new BounceInDown(categories).play();
                gouvernerats.setVisible(true);
                new BounceInDown(gouvernerats).play();
                villes.setVisible(true);
                new BounceInDown(villes).play();
                adresse.setVisible(true);
                new BounceInDown(adresse).play();
                labelCategories.setVisible(true);
                new BounceInDown(labelCategories).play();
                labelGouv.setVisible(true);
                new BounceInDown(labelGouv).play();
                labelVille.setVisible(true);
                new BounceInDown(labelVille).play();
            }else {
                nomProjet.setVisible(false);
                numTelPro.setVisible(false);
                categories.setVisible(false);
                gouvernerats.setVisible(false);
                villes.setVisible(false);
                adresse.setVisible(false);
                labelCategories.setVisible(false);
                labelGouv.setVisible(false);
                labelVille.setVisible(false);
            }
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
    private boolean isValidNumero(String numero) {
        // Logique de validation du mot de passe (par exemple, longueur minimale)
        // Retourne true si le mot de passe est valide, sinon false
        return numero.matches("\\d{8}"); // Exemple : longueur minimale de 8 caractères
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
    private String imageFileName;
    @FXML
    private void onUploadButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Path sourcePath = file.toPath();
            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
            Path destinationPath = Paths.get("D:","projects", "images", uniqueFileName);

            try {
                // Vérifier si le fichier existe déjà, et si c'est le cas, générer un nouveau nom
                while (Files.exists(destinationPath)) {
                    uniqueFileName = UUID.randomUUID().toString() + "_" + file.getName();
                    destinationPath = Paths.get("D:","projects", "images", uniqueFileName);
                }

                Files.copy(sourcePath, destinationPath);
                imageFileName = uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void loginPage(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
  @FXML
    private void inscrire(ActionEvent event) throws ExistUserException,SQLException,IOException {
      long userId = 0;
      String emailText=email.getText();
      String nomText= nom.getText();
      String prenomText = prenom.getText();
      String tel = numTel.getText();
      String password = mdp.getText();
      String confirmPass=confirmMdp.getText();
      String img=imageData;
      String genre=genres.getValue();
      String categorie=categories.getValue();
      String gouv=gouvernerats.getValue();
      String ville=villes.getValue();
      String adr=adresse.getText();
      String numPro=numTelPro.getText();
      String nomProj=nomProjet.getText();
      int latitude=0;
      int longitude=0;
      if(email.getText().isEmpty()){
          email.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(email).play();}
      else if (!isValidEmail(email.getText())) {
          emailError.setText("Veuillez saisir un email valide");
          email.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(email).play();
      }
      else{
          email.setStyle(null);
          emailError.setText("");
      }

      if(mdp.getText().isEmpty()){
          mdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(mdp).play();}
      else if(mdp.getText().length()<8){
          mdp.setStyle("-fx-border-color:red;-fx-border-width:1.5px;");
          mdpError.setText("le mot de passe doit etre superiere à 8");
          new Shake(mdp).play();
      }
      if(nom.getText().isEmpty()){
          nom.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(nom).play();}

      else{
          nom.setStyle(null);
      }

      if(prenom.getText().isEmpty()){
          prenom.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(prenom).play();}
      else {
          prenom.setStyle(null);
      }
      if(numTel.getText().isEmpty()){
          numTel.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(numTel).play();}
      else if(!numTel.getText().matches("\\d{8}")){
          numTel.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(numTel).play();
          numTelError.setText("Veuillez saisir un numero valide");
      }
      else {
          numTel.setStyle(null);
          numTelError.setText("");
      }
      if(confirmMdp.getText().isEmpty()){
          confirmMdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(confirmMdp).play();
      }
      else if(!confirmMdp.getText().equals(mdp.getText())){
          confirmMdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(confirmMdp).play();
          confirmError.setText("Veuillez saisir un mdp conforme");
      }
      else if(confirmMdp.getText().length()<8){
          confirmMdp.setStyle("-fx-border-color:red;-fx-border-width:2px;");
          new Shake(confirmMdp).play();
          confirmError.setText("Veuillez saisir un mdp conforme");
      }

      else {
          confirmMdp.setStyle(null);
          confirmError.setText("");
      }

      if(!nom.getText().isEmpty() && !prenom.getText().isEmpty() && isValidEmail(emailText) && numTelError.getText()=="" && mdpError.getText()=="" && confirmError.getText()==""){
          User user=gu.getUserByMail(email.getText());
          if (user==null) {
              String hashedPassword = BCrypt.hashpw(password,  BCrypt.gensalt(12));
              String newHash = hashedPassword.replaceFirst("^\\$2a\\$", "\\$2y\\$");
              String req = "INSERT INTO users (nom,prenom,email,numTel,genre,image,password) VALUES(?,?,?,?,?,?,?)";
              PreparedStatement stmt = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
              stmt.setString(1,nomText);
              stmt.setString(2,prenomText);
              stmt.setString(3, emailText);
              stmt.setInt(4, Integer.parseInt(tel));
              stmt.setString(5,genre);
              stmt.setString(6, imageFileName);
              stmt.setString(7, newHash);
              int result = stmt.executeUpdate();
              if (result > 0){
                  ResultSet rs = stmt.getGeneratedKeys();
                  if (rs.next()) {
                      userId = rs.getLong(1);}}
              System.out.println(result + " enregistrement ajouté.");
              showAlert("enregistrement ajouté.");
              if(genres.getValue()=="professionnelle"){
                  String req2 = "INSERT INTO entrepreneurs (nomProjet,categorie,gouvernerat,ville,adresseExacte,numTelPro,latitude,longitude,user_id) VALUES(?,?,?,?,?,?,?,?,?)";
                  PreparedStatement stmt2 = con.prepareStatement(req2);
                  stmt2.setString(1,nomProj);
                  stmt2.setString(2,categorie);
                  stmt2.setString(3, gouv);
                  stmt2.setString(4,ville);
                  stmt2.setString(5, adr);
                  stmt2.setInt(6, Integer.parseInt(numPro));
                  stmt2.setInt(7, Integer.parseInt(String.valueOf(latitude)));
                  stmt2.setInt(8, Integer.parseInt(String.valueOf(longitude)));
                  stmt2.setLong(9, userId);
                  int result1 = stmt2.executeUpdate();
                  System.out.println(result + " enregistrement ajouté.");
                  showAlert("enregistrement ajouté.");
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
                  Parent root = loader.load();
                  Scene scene = new Scene(root);
                  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                  stage.setScene(scene);
                  stage.show();

              }
          }else{
              showWarningAlert("utilisateur existe deja");
             throw new ExistUserException("utilisateur existe deja");

          }


      }

  }
    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.show();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
    }

}
