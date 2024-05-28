package com.example.myevent.Services;


import com.example.myevent.entities.Evennement;
import com.example.myevent.entities.Event;
import com.example.myevent.tools.Connexion;

import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventService implements iservice<Evennement> {

    private static Connection connection;

    public EventService() {
        connection = Connexion.getInstance().getCnx();
    }

    // Method to add a user
   public void ajouter(Evennement event) throws SQLException {
    /*     String sql = "INSERT INTO evennements (titre, dateEvent, heuredebutEvent, heureFinEvent, nbInvites, gouvernerat, ville, adresseExacte) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, event.getTitre());
        preparedStatement.setDate(2, Date.valueOf(event.getDateEvent())); // Conversion de LocalDate en java.sql.Date
        preparedStatement.setTime(3, event.getHeureDebutEvent());
        preparedStatement.setTime(4, event.getHeureFinEvent());
        preparedStatement.setInt(5, event.getNbInvites());
        preparedStatement.setString(6, event.getGouvernerat());
        preparedStatement.setString(7, event.getVille());
        preparedStatement.setString(8, event.getAdresseExacte());

        preparedStatement.executeUpdate();*/
    }

    @Override
    public void modifier(Evennement event) throws SQLException {

    }

    @Override
    public void supprimer(Evennement event) throws SQLException {

    }


    public List<Evennement> afficher() throws SQLException {
        String sql = "SELECT id, titre, dateEvent, heuredebutEvent, heureFinEvent, nbInvites, gouvernerat, ville, adresseExacte FROM evennements";
        List<Evennement> eventsList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

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
        }

        return eventsList;
    }

}