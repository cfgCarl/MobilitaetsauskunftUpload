package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.GesamtVerbindung;
import com.dabbility.auskunft.model.Haltestelle;
import com.dabbility.auskunft.model.Ort;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//Controller für die Ortsauswahl

public class Ortauswahl implements Initializable {

    @FXML
    ListView<String> lvHST;
    ObservableList<Ort> haltestellenList;
    ObservableList<String> hstNamenList;
    Ort gewaehlt;

    public Ortauswahl() {
        haltestellenList = FXCollections.observableArrayList();
        hstNamenList = FXCollections.observableArrayList();
    }

    public void initData(List<Ort> orte){
        haltestellenList.addAll(orte);
        for(Ort o: haltestellenList){
            hstNamenList.add(o.getName());
        }
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        lvHST.setItems(hstNamenList);
        lvHST.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    int x = hstNamenList.indexOf(ov.getValue());
                    gewaehlt = haltestellenList.get(x);
                    ( (Stage) lvHST.getScene().getWindow()).close();
                });
    }
}
