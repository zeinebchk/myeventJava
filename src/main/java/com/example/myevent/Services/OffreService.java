package com.example.myevent.Services;
import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.SalleFete;
import com.example.myevent.tools.Connexion;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OffreService  {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();
    int surface;
     int capacitePersonne;
    String gouvernerat;
    String ville;
    Evennement offre;
    String optionInclus;
    String adresseExacte;
    public SalleFete getSalleFeteByTitle(String title) throws IOException, SQLException {
        System.out.println("getSalleFeteByTitle"+title);



        SalleFete s = new SalleFete(surface, capacitePersonne, gouvernerat, ville, adresseExacte, optionInclus, offre.getId());
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
}
