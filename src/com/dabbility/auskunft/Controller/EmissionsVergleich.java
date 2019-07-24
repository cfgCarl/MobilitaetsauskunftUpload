package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.GesamtVerbindung;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

//Controller für die Emissionen

public class EmissionsVergleich {
    List<GesamtVerbindung> gesamtVerbindungen;
    List<Emissionen> emis = new ArrayList<>();

    @FXML
    BarChart<String, Number> diagrammA;
    @FXML
    BarChart<String, Number> diagrammB;
    @FXML
    Label lblQuelle;
    @FXML
    AnchorPane anchorPane;
    @FXML
    TabPane tabPane;

    List<XYChart.Data<String, Number>> treibhausgaseDIREKT;

    public void initData(List<GesamtVerbindung> gvs){
        gesamtVerbindungen = gvs;
        for(GesamtVerbindung gv:gesamtVerbindungen){
            emis.add(gv.getEmmissionen());
        }

        initChart();
    }

    public EmissionsVergleich() {

    }
    @FXML
    public void initialize() {
        lblQuelle.setText("Die angegebenen Daten wurde mit Hilfe von Daten des Umweltbundesamtes zu Emissionen im Personenverkehr berechnet. Alle Werte sind in Gramm pro Persoenenkilometer angegeben und aus dem Bezugsjahr 2017. \n Quelle: Umweltbundesamt / TREMOD 5.82 / 13.11.2018");
        anchorPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setPrefWidth(anchorPane.getWidth());
            tabPane.setPrefHeight(anchorPane.getHeight());
            tabPane.setTabMaxWidth(anchorPane.getWidth()/2);
            diagrammA.setPrefHeight(anchorPane.getHeight());
            diagrammA.setPrefWidth(anchorPane.getWidth());
            diagrammB.setPrefHeight(anchorPane.getHeight());
            diagrammB.setPrefWidth(anchorPane.getWidth());
        });
        anchorPane.heightProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setPrefWidth(anchorPane.getWidth());
            tabPane.setPrefHeight(anchorPane.getHeight());
            tabPane.setTabMaxWidth(anchorPane.getWidth()/2);
            diagrammA.setPrefHeight(anchorPane.getHeight());
            diagrammA.setPrefWidth(anchorPane.getWidth());
            diagrammB.setPrefHeight(anchorPane.getHeight());
            diagrammB.setPrefWidth(anchorPane.getWidth());
        });
    }

    public void initChart() {

        XYChart.Series<String, Number> treibhausgase = new XYChart.Series<String, Number>();
        treibhausgase.setName("Treibhausgase");

        XYChart.Series<String, Number> kohlenmonoxid = new XYChart.Series<String, Number>();
        kohlenmonoxid.setName("kohlenmonoxid");

        XYChart.Series<String, Number> kohlenwasserstoffe = new XYChart.Series<String, Number>();
        kohlenwasserstoffe.setName("kohlenwasserstoffe");

        XYChart.Series<String, Number> stickoxid = new XYChart.Series<String, Number>();
        stickoxid.setName("stickoxid");

        XYChart.Series<String, Number> feinstaub = new XYChart.Series<String, Number>();
        feinstaub.setName("feinstaub");
        for(int i = 0; i < emis.size(); i++) {

            if(gesamtVerbindungen.get(i).getName() == null) {
                treibhausgase.getData().add(new XYChart.Data<>("Verbindung " + (i+1), emis.get(i).getTreibhausgase()));
            } else {
                treibhausgase.getData().add(new XYChart.Data<>(gesamtVerbindungen.get(i).getName(), emis.get(i).getTreibhausgase()));
            }

            if(gesamtVerbindungen.get(i).getName() == null) {
                kohlenmonoxid.getData().add(new XYChart.Data<>("Verbindung " + (i+1), emis.get(i).getKohlenmonoxid()));
            } else {
                kohlenmonoxid.getData().add(new XYChart.Data<>(gesamtVerbindungen.get(i).getName(), emis.get(i).getKohlenmonoxid()));
            }

            if(gesamtVerbindungen.get(i).getName() == null) {
                kohlenwasserstoffe.getData().add(new XYChart.Data<>("Verbindung " + (i+1), emis.get(i).getKohlenwasserstoffe()));
            } else {
                kohlenwasserstoffe.getData().add(new XYChart.Data<>(gesamtVerbindungen.get(i).getName(), emis.get(i).getKohlenwasserstoffe()));
            }

            if(gesamtVerbindungen.get(i).getName() == null) {
                stickoxid.getData().add(new XYChart.Data<>("Verbindung " + (i+1), emis.get(i).getStickoxid()));
            } else {
                stickoxid.getData().add(new XYChart.Data<>(gesamtVerbindungen.get(i).getName(), emis.get(i).getStickoxid()));
            }

            if(gesamtVerbindungen.get(i).getName() == null) {
                feinstaub.getData().add(new XYChart.Data<>("Verbindung " + (i+1), emis.get(i).getFeinstaub()));
            } else {
                feinstaub.getData().add(new XYChart.Data<>(gesamtVerbindungen.get(i).getName(), emis.get(i).getFeinstaub()));
            }
        }

        diagrammA.getData().addAll(treibhausgase);
        diagrammA.setTitle("Emissionsvergleich Treibhausgase");

        diagrammB.getData().addAll(kohlenmonoxid, kohlenwasserstoffe, stickoxid, feinstaub);
        diagrammB.setTitle("Emissionsvergleich (zweite Seite)");
    }

}
