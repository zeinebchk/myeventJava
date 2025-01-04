package com.example.myevent.Services;
import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class OffreService implements IOffreService  {
    BigInteger entrepreneur_id = new BigInteger("1");
    PreparedStatement st = null;
    ResultSet rs = null;
    private byte[] imageBytes;
    private OffreService offreDAO;
    Connection con = Connexion.getInstance().getCnx();

    public SalleFete getSalleFeteByTitle(String title) throws IOException, SQLException {
        SalleFete s=new SalleFete();
        System.out.println("getSalleFeteByTitle"+title);
        st = con.prepareStatement("SELECT * FROM offre join salleFete on offre.id=salleFete.offre_id WHERE titre = ?");
        st.setString(1, title);
        rs = st.executeQuery();
        if (rs.next()) {
            BigInteger bigId =rs.getBigDecimal("id").toBigInteger();
            s.setId(bigId);
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
            s.setOptionInclus(rs.getString("optionInclus"));
        }
        System.out.println("getSalleFeteByTitle"+s.toString());
       return s;
    }


    public void ajouterOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException, IOException {
        try {
            if (this.entrepreneur_id == null) {
                throw new SQLException("Le champ 'entrepreneur_id' ne peut pas être null");
            }

            String query = "INSERT INTO offre (titre, description, prixInitial, prixRemise, entrepreneur_id) VALUES (?, ?, ?, ?, ?)";
            st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, offre.getTitre());
            st.setString(2, offre.getDescription());
            st.setDouble(3, offre.getPrixInitial());
            st.setDouble(4, offre.getPrixRemise());
            st.setObject(5, entrepreneur_id);

            int ResultSet = st.executeUpdate();
            { ResultSet rs = st.getGeneratedKeys(); if (rs.next())
            { BigInteger offreId = rs.getBigDecimal(1).toBigInteger(); offre.setId(offreId); }
            else { throw new SQLException("Échec de la récupération de l'ID généré de l'offre.");
            } }
            // Insertion de l'image si elle est fournie
            if (image != null) {
                String imageQuery = "INSERT INTO image (offre_id, url) VALUES (?, ?)";
                PreparedStatement stmt2 = con.prepareStatement(imageQuery, Statement.RETURN_GENERATED_KEYS);
                stmt2.setInt(1, offre.getId().intValue());
                byte[] imageBytes = image.getUrl();
                stmt2.setBytes(2, imageBytes);
                stmt2.executeUpdate();
            }

            // Insertion dans la table "salle_fete" si elle est fournie
            if (salleFete != null) {
                String salleFeteQuery = "INSERT INTO sallefete (offre_id, surface, capacitePersonne, gouvernerat, ville, adresseExacte, latitude, longitude, optionInclus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement st3 = con.prepareStatement(salleFeteQuery);
                st3.setInt(1, offre.getId().intValue());
                st3.setInt(2, salleFete.getSurface());
                st3.setInt(3, salleFete.getCapacitePersonne());
                st3.setString(4, salleFete.getGouvernerat());
                st3.setString(5, salleFete.getVille());
                st3.setString(6, salleFete.getAdresseExacte());
                st3.setDouble(7, salleFete.getLatitude());
                st3.setDouble(8, salleFete.getLongitude());
                st3.setString(9, salleFete.getOptionInclus());
                st3.executeUpdate();
            }




        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'offre : " + e.getMessage());
            showAlert("Erreur", "Erreur lors de l'ajout de l'offre : " + e.getMessage());
        }

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void deleteOffre(Offre offre) throws SQLException {
        String query = "DELETE FROM offre WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, offre.getId().intValue());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateOffre(Offre offre, SalleFete salleFete, javafx.scene.image.Image image) throws SQLException, IOException {

    }

    public void updateOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException, IOException {
        String query = "UPDATE offre SET titre = ?, description = ?, prixInitial = ?, prixRemise = ?,  WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, offre.getTitre());
            statement.setString(2, offre.getDescription());
            statement.setDouble(3, offre.getPrixInitial());
            statement.setDouble(4, offre.getPrixRemise());



            statement.setInt(5, offre.getId().intValue());
            statement.executeUpdate();
        }

        if (image != null) {
            String updateImageQuery = "UPDATE image SET url = ? WHERE offre_id = ?";
            try (PreparedStatement imageStatement = con.prepareStatement(updateImageQuery)) {
                byte[] imageBytes = image.getUrl();
                imageStatement.setBytes(1, imageBytes);
                imageStatement.setInt(2, offre.getId().intValue());
                imageStatement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error updating image data: " + e.getMessage());
            }
        }

        if (salleFete != null) {
            String updateSalleFeteQuery = "UPDATE sallefete SET surface = ?, capacitePersonne = ?, gouvernerat = ?, ville = ?, adresseExacte = ?, latitude = ?, longitude = ?, optionInclus = ? WHERE offre_id = ?";
            try (PreparedStatement salleFeteStatement = con.prepareStatement(updateSalleFeteQuery)) {
                salleFeteStatement.setInt(1, salleFete.getSurface());
                salleFeteStatement.setInt(2, salleFete.getCapacitePersonne());
                salleFeteStatement.setString(3, salleFete.getGouvernerat());
                salleFeteStatement.setString(4, salleFete.getVille());
                salleFeteStatement.setString(5, salleFete.getAdresseExacte());
                salleFeteStatement.setDouble(6, salleFete.getLatitude());
                salleFeteStatement.setDouble(7, salleFete.getLongitude());
                salleFeteStatement.setString(8, salleFete.getOptionInclus());
                salleFeteStatement.setInt(9, offre.getId().intValue());
                salleFeteStatement.executeUpdate();
            }
        }
    }



    public ObservableList<Offre> getOffres() throws SQLException {
        List<Offre> offres = new ArrayList<>();
        String query = "SELECT * FROM offre";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Offre offre = new Offre(rs.getBigDecimal("id").toBigInteger(), rs.getString("titre"),rs.getString("description"),rs.getDouble("prixInitial"));
                offres.add(offre);
            }
        }
        return FXCollections.observableArrayList(offres);
    }
    public List<SalleFete> getAllRooomsAvailable(Date t,List<SalleFete>salles) throws SQLException, IOException {
        st = con.prepareStatement("SELECT * from offre o " +
                "JOIN sallefete f ON o.id = f.offre_id " +
                "JOIN (SELECT MIN(id) as id, offre_id, url FROM image GROUP BY offre_id) i ON o.id = i.offre_id " +
                "AND o.id NOT IN (SELECT o.id FROM offre o " +
                "JOIN sallefete f ON o.id = f.offre_id " +
                "JOIN reservations r ON o.id = r.offre_id " +
                "JOIN image i ON o.id = i.offre_id " +
                "AND r.status = 'confirme' " +
                "AND r.dateReservation = ?) " );
        st.setDate(1,t);
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
            Blob imageBlob = rs.getBlob("url");
            /*if (imageBlob != null) {
                InputStream imageStream = imageBlob.getBinaryStream();
                Image javafxImage = new Image(imageStream); // Convertir InputStream en Image
                s.setImage(javafxImage);
            }*/

             /* Entrepreneur entrepreneur = getEntrepreneurFromResultSet(rs.getBigDecimal("entrepreneur_id"));
              s.setEntrepreneur_id(entrepreneur);*/
            if (!salles.contains(s)) {
                salles.add(s);
            }


        }
        return salles;
    }


    public List<SalleFete> filtrerBYcriteria(String gouvernerat,String ville,Date t,int nbInvites,int minBudget,int maxBudget,List<SalleFete> salles) throws SQLException, IOException {
        salles.clear();
        getAllRooomsAvailable(t,salles);
        System.out.println(salles.size());
        if (salles != null && !salles.isEmpty()) {
            System.out.println("dddddddddddddddd");

            for (SalleFete s : salles) {
                System.out.println(s.toString());
            }
            System.out.println("size salles dans filtre "+salles.size());
        }
        else {
            System.out.println("La liste des salles est vide ou nulle.");
        }
        System.out.println(gouvernerat);
        System.out.println(ville);
        salles=salles.stream().filter(
                s->s.getGouvernerat().equals(gouvernerat)
                        && s.getVille().equals(ville)
                        && s.getCapacitePersonne()>=nbInvites
                        && s.getPrixInitial()>=minBudget && s.getPrixInitial()<=maxBudget).collect(Collectors.toList());
        for(SalleFete s:salles){

            System.out.println(s.toString());
        }
        return salles;
    }

    public Entrepreneur getEntrepreneurFromResultSet(BigDecimal id) throws SQLException {
        System.out.println("gggg");
        // Assuming Entrepreneur has an empty constructor and setter methods for each property
        Entrepreneur entrepreneur = new Entrepreneur();
        st = con.prepareStatement("SELECT * FROM entrepreneurs join users on entrepreneurs.user_id=users.id where entrepreneurs.id=?" );
        st.setBigDecimal(1, id);
        rs = st.executeQuery();
        if(rs.next()){
            System.out.println(rs.getString("nom"));
            entrepreneur.setIdEntrepreneur(new BigInteger(rs.getString("id")));
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
    @FXML
    void affichMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
