package com.example.myevent.Services;


import com.example.myevent.entities.*;
import com.example.myevent.tools.Connexion;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EventService implements iservice<Evennement> {
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = Connexion.getInstance().getCnx();


    public EventService() {
    }


    public Set<Evennement> getEventsByClIent_id() throws SQLException {
        String sql = "SELECT id, titre, dateEvent, heuredebutEvent, heureFinEvent, nbInvites, gouvernerat, ville, adresseExacte FROM evennements where client_id=?";
       st = con.prepareStatement(sql);
        BigInteger bigId = UserSession.getInstance().getUser().getId();
        long userId = bigId.longValue();
        st.setLong(1, userId);
        rs = st.executeQuery();
        int rowCount = 0;
        Set<Evennement> eventsList = new TreeSet<>();
        while (rs.next()) {
                BigInteger id = rs.getBigDecimal("id").toBigInteger();
                String titre = rs.getString("titre");
                Date dateEvent = rs.getDate("dateEvent"); // Utilisation de getDate() pour récupérer la date
                Time heuredebutEvent = rs.getTime("heuredebutEvent"); // Utilisation de getTime() pour récupérer l'heure
                Time heureFinEvent = rs.getTime("heureFinEvent"); // Utilisation de getTime() pour récupérer l'heure
                Integer nbInvites = rs.getInt("nbInvites");
                String gouvernerat = rs.getString("gouvernerat");
                String ville = rs.getString("ville");
                String adresseExacte = rs.getString("adresseExacte");

                Evennement event = new Evennement(id, titre, dateEvent, heuredebutEvent, heureFinEvent, nbInvites, gouvernerat, ville, adresseExacte);
                eventsList.add(event);
            }
        return eventsList;
    }
    public  boolean chercherOffreDansEvent(BigInteger idevent,BigInteger idOffre) throws SQLException {
        System.out.println("idevent"+idevent);
        long idev = idevent.longValue();
        System.out.println("idev"+idev);
        System.out.println("idoff"+idOffre);
        List<Offre> offres= new ArrayList<>();
        String sql = "SELECT * from offre_event oe join Offre o on oe.offre_id=o.id  where event_id=?";
        st = con.prepareStatement(sql);
        st.setLong(1,idev);
        rs = st.executeQuery();
        while (rs.next()) {
            Offre o= new Offre();
            o.setId(rs.getBigDecimal("offre_id").toBigInteger());
            o.setTitre(rs.getString("titre"));
            offres.add(o);
        }
        for (Offre o : offres) {
            System.out.println(o.toString());
        }
        Boolean oo = offres.stream().anyMatch(o -> o.getId().equals(idOffre)) ;

        return oo;

    }


}