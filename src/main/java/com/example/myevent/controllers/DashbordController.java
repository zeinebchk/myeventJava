package com.example.myevent.controllers;


import com.example.myevent.Models.client;
import com.example.myevent.Models.entrepreneur;
import com.example.myevent.tools.Connexion;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import java.sql.Connection;
import javafx.scene.chart.XYChart;

public class DashbordController {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public void initialize() {
        // Initialisation du graphique de ligne
        xAxis.setLabel("Mois");
        yAxis.setLabel("Nombre");
        lineChart.setTitle("Statistiques Clients et Entrepreneurs");

        // Ajout des données pour les clients
        XYChart.Series<Number, Number> clientsSeries = new XYChart.Series<>();
        clientsSeries.setName("Clients"); // Au lieu de clientsSeries.setNom("Clients");

        clientsSeries.getData().add(new XYChart.Data<>(1, 100)); // Exemple : 100 clients au mois 1
        clientsSeries.getData().add(new XYChart.Data<>(2, 150)); // Exemple : 150 clients au mois 2
        // Ajouter plus de données pour les clients ici

        // Ajout des données pour les entrepreneurs
        XYChart.Series<Number, Number> entrepreneursSeries = new XYChart.Series<>();
        entrepreneursSeries.setName("Entrepreneurs");
        entrepreneursSeries.getData().add(new XYChart.Data<>(1, 50)); // Exemple : 50 entrepreneurs au mois 1
        entrepreneursSeries.getData().add(new XYChart.Data<>(2, 80)); // Exemple : 80 entrepreneurs au mois 2
        // Ajouter plus de données pour les entrepreneurs ici

        // Ajout des séries au graphique
        lineChart.getData().addAll(clientsSeries, entrepreneursSeries);
    }
}
