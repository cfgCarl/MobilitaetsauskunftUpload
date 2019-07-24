package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.GesamtVerbindung;
import com.dabbility.auskunft.model.Ort;
import com.dabbility.auskunft.model.TeilVerbindung;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

//Controller der VerbUebersicht

public class Verbindungsuebersicht implements Initializable {

    @FXML
    ListView<GesamtVerbindung> lvDetails;

    @FXML
    ListView<String> myListView;

    ObservableList<GesamtVerbindung> verbindungsList;

    @FXML
    Button btnEmi;




    public Verbindungsuebersicht() {
        verbindungsList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        lvDetails.setItems(verbindungsList);
        lvDetails.setCellFactory(VerbindungenListView -> new VerbindungenListViewCell());
        lvDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GesamtVerbindung>() {

            @Override
            public void changed(ObservableValue<? extends GesamtVerbindung> observable, GesamtVerbindung oldValue, GesamtVerbindung newValue) {
                Verbindungsdetails vDet;

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/Verbindungsdetails.fxml"));
                    Stage secondaryStage = new Stage();
                    secondaryStage.setScene(new Scene(loader.load()));
                    vDet = loader.getController();
                    vDet.initData(observable.getValue());
                    secondaryStage.setTitle("Verbindungsdetails");
                    secondaryStage.show();
                } catch (Exception e) {
                    vDet = null;
                    System.out.println(e);
                }
            }
        });



    }

    public void initData(List<GesamtVerbindung> gesamtVerbindungen) {
        List<GesamtVerbindung> direkt = new ArrayList<>();
        for(GesamtVerbindung g:gesamtVerbindungen) {
            if(g.isDirektverbindung() == true) {
                direkt.add(g);
            }
        }
        gesamtVerbindungen.removeAll(direkt);
        List<GesamtVerbindung> sortiert = new ArrayList<>();
        GesamtVerbindung schnell;
        while(gesamtVerbindungen.size() > 0) {
            schnell = gesamtVerbindungen.get(0);
            for(GesamtVerbindung g:gesamtVerbindungen) {
                if(g.getDauer()<schnell.getDauer()) {
                    schnell = g;
                }
            }
            sortiert.add(schnell);
            gesamtVerbindungen.remove(schnell);
        }
sortiert.addAll(direkt);
        for(int i = 1; i <= sortiert.size(); i++) {
            if(sortiert.get(i-1).getName() == null) {
                sortiert.get(i - 1).setName("Verbindung " + i);
            }
        }
        verbindungsList.addAll(sortiert);
    }

    @FXML
    public void emiStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/emissionsVergleich.fxml"));
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(new Scene(loader.load()));
            EmissionsVergleich emv = loader.getController();
            emv.initData(verbindungsList);
            secondaryStage.setTitle("Emissionsvergleich");
            secondaryStage.show();
        } catch (Exception e) {

            System.out.println(e);
        }
    }

}
