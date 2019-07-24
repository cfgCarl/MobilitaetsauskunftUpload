package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.*;
import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.offline.OfflineCache;
import com.sun.org.apache.bcel.internal.classfile.Unknown;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

//Controller der VerbDetails

public class Verbindungsdetails implements Initializable {

    @FXML
    ListView<Verbindungsabschnitt> lvDetails;
    ObservableList<Verbindungsabschnitt> teilVerbindungsList;
    GesamtVerbindung gvb = null;
    Marker m;
    Marker z;
    List<CoordinateLine> lines = new ArrayList<>();

    @FXML Label lbltreibhausgaseWert;
    @FXML Label lblkohlenmonoxidWert;
    @FXML Label lblkohlenwasserstoffeWert;
    @FXML Label lblstickoxidWert;
    @FXML Label lblfeinstaubWert;

    @FXML
    Button btnGVA;

    Projection projection = Projection.WGS_84;

    /** some coordinates from around town. */
    private static final Coordinate coordKarlsruheHarbour = new Coordinate(49.015511, 8.323497);

    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 14;

    @FXML
    private MapView mapView;

    @FXML
    TableColumn<String, String> clmBez;

    @FXML
    TableColumn<String, String> clmWert;


    public Verbindungsdetails() {
        teilVerbindungsList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        lvDetails.setItems(teilVerbindungsList);
        lvDetails.setCellFactory(teilVerbindungListView -> new teilVerbindungListViewCell());
        mapINIT();

        lvDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Verbindungsabschnitt>() {

            @Override
            public void changed(ObservableValue<? extends Verbindungsabschnitt> observable, Verbindungsabschnitt oldValue, Verbindungsabschnitt newValue) {

                try {
                    mapView.setExtent(Extent.forCoordinates(newValue.getTeilverb().getCoordListe()));
                    emiFuellen(newValue.getTeilverb().getEmmissionen());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    public void initData(GesamtVerbindung gv) {
        gvb = gv;
        teilVerbindungsList.addAll(gv.getVerbindungsAbfolge());
        m = Marker.createProvided(Marker.Provided.BLUE).setPosition(teilVerbindungsList.get(0).getTeilverb().getCoordListe().get(0)).setVisible(true);
        m.attachLabel(new MapLabel("Start"));
        z = Marker.createProvided(Marker.Provided.BLUE).setPosition(teilVerbindungsList.get(teilVerbindungsList.size() - 1).getTeilverb().getCoordListe().get(teilVerbindungsList.get(teilVerbindungsList.size() - 1).getTeilverb().getCoordListe().size() - 1)).setVisible(true);
        z.attachLabel(new MapLabel("Ziel"));
        for(Verbindungsabschnitt vba:teilVerbindungsList) {
            List<Coordinate> cs = vba.getTeilverb().getCoordListe();
            if(cs.size()>1) {
                CoordinateLine cl = new CoordinateLine(cs);
                cl.setVisible(true);
                cl.setColor(vba.getTeilverb().getVerkehrsmittel().getColor());
                lines.add(cl);

            }
        }
        emiFuellen(gv.getEmmissionen());

    }

    public void toExtent(Extent e) {
        mapView.setExtent(e);
    }

    private void afterMapIsInitialized() {
        mapView.setZoom(ZOOM_DEFAULT);
        mapView.setCenter(coordKarlsruheHarbour);
        mapView.setExtent(Extent.forCoordinates(gvb.getCoord()));
        for(CoordinateLine l:lines){
            mapView.addCoordinateLine(l);
        }
        mapView.addMarker(m);
        mapView.addMarker(z);
    }

    private void mapINIT() {
        final OfflineCache offlineCache = mapView.getOfflineCache();
        final String cacheDir = System.getProperty("java.io.tmpdir") + "/mapjfx-cache";
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });
        mapView.setMapType(MapType.OSM);

        // finally initialize the map view
        mapView.initialize();
    }

    protected void emiFuellen(Emissionen emi) {
        lbltreibhausgaseWert.setText(Double.toString(emi.getTreibhausgase()));
        lblkohlenmonoxidWert.setText(Double.toString(emi.getKohlenmonoxid()));
        lblkohlenwasserstoffeWert.setText(Double.toString(emi.getKohlenwasserstoffe()));
        lblstickoxidWert.setText(Double.toString(emi.getStickoxid()));
        lblfeinstaubWert.setText(Double.toString(emi.getFeinstaub()));
    }

    @FXML
    protected void gvAnzeigen(){
        emiFuellen(gvb.getEmmissionen());
        mapView.setExtent(Extent.forCoordinates(gvb.getCoord()));
    }

}
