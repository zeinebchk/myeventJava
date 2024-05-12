package com.example.myevent.controllers;

import com.example.myevent.entities.Entrepreneur;
import com.example.myevent.entities.Offre;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardUserController implements Initializable {
    @FXML
    private DatePicker dateReservation;

    @FXML
    private ImageView filter2;

    @FXML
    private Pane filtragePanel;

    @FXML
    private ComboBox<String> gouvs;

    @FXML
    private GridPane grid;

    @FXML
    private Slider maxBudget;

    @FXML
    private Slider minBudget;

    @FXML
    private TextField nbInvites;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ImageView search;

    @FXML
    private ChoiceBox<String> villes;


    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();

    private List<SalleFete> salles=new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            salles.addAll(getData());
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < salles.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/card.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    CardController itemController = fxmlLoader.getController();
                    itemController.setData(salles.get(i));

                    if (column == 2) {
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

                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        gouvs.getItems().add("Sfax");
        gouvs.getItems().add("Tunis");
        gouvs.getItems().add("Monastir");
        gouvs.getItems().add("Sousse");
        gouvs.setValue("Monastir");
        villes.getItems().addAll("Monastir", "Moknine", "Sahline");
        villes.setValue("Monastir");
        gouvs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
        File brandingFile = new File("images/search.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        search.setImage(brandingImage);



        File brandingFile3 = new File("images/filter.png");
        Image brandingImage3 = new Image(brandingFile3.toURI().toString());
        filter2.setImage(brandingImage3);

    }

    public List<SalleFete> getData() throws SQLException {
        System.out.println("hhhhhh");
         if(nbInvites.getText().isEmpty()){
             nbInvites.setText("20");
         }
         if(dateReservation.getValue()==null){
             dateReservation.setValue(LocalDate.now());
         }
         if(gouvs.getValue()==null){
             gouvs.setValue("Monastir");
         }
        if(villes.getValue()==null){
            villes.setValue("Monastir");
        }
         List<SalleFete> salles=new ArrayList<>();
         SalleFete salle;

         System.out.println(gouvs.getValue());
        st = con.prepareStatement("SELECT * from offre o " +
                  "JOIN sallefete f ON o.id = f.offre_id " +
                "JOIN image i ON o.id = i.offre_id " +
                 "WHERE f.gouvernerat = ? " +
                "AND f.ville = ? " +
                "AND f.capacitePersonne >= ? " +
                "AND o.prixInitial BETWEEN ? AND ? " +
             "AND o.id NOT IN (SELECT o.id FROM offre o " +
                "JOIN sallefete f ON o.id = f.offre_id " +
                "JOIN reservations r ON o.id = r.offre_id " +
                "JOIN image i ON o.id = i.offre_id " +
                "WHERE f.gouvernerat = ? " +
                "AND f.ville = ? " +
                "AND f.capacitePersonne >= ? " +
                "AND o.prixInitial BETWEEN ? AND ? " +
                "AND r.status = 'confirme' " +
                "AND r.dateReservation = ?) " );
           st.setString(1, gouvs.getValue());
            st.setString(2, villes.getValue());
             st.setInt(3, Integer.parseInt(nbInvites.getText()));
            st.setDouble(4, minBudget.getValue());
            st.setDouble(5, maxBudget.getValue());
           st.setString(6, gouvs.getValue());
            st.setString(7, villes.getValue());
            st.setInt(8, Integer.parseInt(nbInvites.getText()));
            st.setDouble(9, minBudget.getValue());
            st.setDouble(10, maxBudget.getValue());
            st.setDate(11,java.sql.Date.valueOf(dateReservation.getValue()));
            rs = st.executeQuery();
        int rowCount = 0;
          while (rs.next()) {
              rowCount++;
              SalleFete s=new SalleFete();
             s.setTitre(rs.getString("titre"));
             s.setAdresseExacte(rs.getString("adresseExacte"));
             s.setDescription(rs.getString("description"));
             s.setPrixInitial(rs.getDouble("prixInitial"));
             s.setCapacitePersonne(rs.getInt("capacitePersonne"));
             s.setSurface(rs.getInt("surface"));
             s.setGouvernerat(rs.getString("gouvernerat"));
             s.setVille(rs.getString("ville"));
             s.setDescription(rs.getString("description"));
             s.setAdresseExacte(rs.getString("adresseExacte"));
             /* Entrepreneur entrepreneur = getEntrepreneurFromResultSet(rs.getBigDecimal("entrepreneur_id"));
              s.setEntrepreneur_id(entrepreneur);*/
              if (!salles.contains(s)) {
                  salles.add(s);
              }
              System.out.println("hhhhhh");
           }
        System.out.println("Nombre de lignes retournées : " + rowCount);
          for(int i=0;i<salles.size();i++){
              System.out.println(salles.get(i).toString());
          }
        return salles;
    }

    private Entrepreneur getEntrepreneurFromResultSet(BigDecimal id) throws SQLException {
        System.out.println("gggg");
        // Assuming Entrepreneur has an empty constructor and setter methods for each property
        Entrepreneur entrepreneur = new Entrepreneur();
        st = con.prepareStatement("SELECT * FROM entrepreneurs join users on entrepreneurs.user_id=users.id where entrepreneurs.id=?" );
        st.setBigDecimal(1, id);
        rs = st.executeQuery();
        if(rs.next()){
            System.out.println(rs.getString("nom"));
            entrepreneur.setId(new BigInteger(rs.getString("id")));
            entrepreneur.setNomProjet(rs.getString("nomProjet"));
            entrepreneur.setCategorie(rs.getString("categorie"));
            entrepreneur.setGouvernerat(rs.getString("gouvernerat"));
            entrepreneur.setVille(rs.getString("ville"));
            entrepreneur.setAdresseExacte(rs.getString("adresseExacte"));
            entrepreneur.setNumTelPro(rs.getInt("numTelPro"));
            entrepreneur.setLatitude(rs.getDouble("latitude"));
            entrepreneur.setLatitude(rs.getDouble("longitude"));
            entrepreneur.setNom(rs.getString("nom"));
            entrepreneur.setPrenom(rs.getString("prenom"));
            entrepreneur.setEmail(rs.getString("email"));
            entrepreneur.setNumTel(rs.getInt("numTel"));
            entrepreneur.setImage(rs.getString("image"));
            entrepreneur.setGenre(rs.getString("genre"));
            entrepreneur.setPassword(rs.getString(("password")));
        }

        return entrepreneur;
    }
}
