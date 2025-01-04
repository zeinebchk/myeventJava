package com.example.myevent.Services;

import com.example.myevent.entities.Entrepreneur;
import com.example.myevent.entities.Offre;
import com.example.myevent.entities.SalleFete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IOffreService {

    public SalleFete getSalleFeteByTitle(String title) throws IOException, SQLException;

    public void ajouterOffre(Offre offre, SalleFete salleFete, com.example.myevent.entities.Image image) throws SQLException, IOException;

    public void deleteOffre(Offre offre) throws SQLException;

    public void updateOffre(Offre offre, SalleFete salleFete, Image image) throws SQLException, IOException;

    public ObservableList<Offre> getOffres() throws SQLException;

    public List<SalleFete> getAllRooomsAvailable(Date t, List<SalleFete>salles) throws SQLException, IOException;


    public List<SalleFete> filtrerBYcriteria(String gouvernerat,String ville,Date t,int nbInvites,int minBudget,int maxBudget,List<SalleFete> salles) throws SQLException, IOException;

    public Entrepreneur getEntrepreneurFromResultSet(BigDecimal id) throws SQLException;
}
