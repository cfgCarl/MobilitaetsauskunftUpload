package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.COMs.triasCOM;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Ort;
import com.sothawo.mapjfx.Coordinate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

//Controller zum Einstellungsscreen

public class settings {
    @FXML
    CheckBox cbxIVOEV;
    @FXML
    CheckBox cbxOEVIV;
    @FXML
    CheckBox cbxNextbike;
    @FXML
    CheckBox cbxsMobil;
    @FXML
    CheckBox cbxEBike;
    @FXML
    TextField tvEBike;
    @FXML
    Button btnValid;

    Ort eigFahrrad = null;

    Einstellungen einst;

    public settings() {

    }

    @FXML
    protected void initialize() {
        einst = Einstellungen.getInstance();
        cbxEBike.setSelected(einst.getEigenesFahrradAktiv());
        cbxNextbike.setSelected(einst.getNextbikeAktiv());
        cbxIVOEV.setSelected(einst.getIvov());
        cbxOEVIV.setSelected(einst.getOviv());
        if(cbxEBike.isSelected()) {
            tvEBike.setDisable(false);
            btnValid.setDisable(false);
        } else {
            tvEBike.setDisable(true);
            btnValid.setDisable(true);
        }
        try {
            tvEBike.setText(einst.getEigenesFahrrad().getAdresse());
            eigFahrrad = einst.getEigenesFahrrad();
        } catch (NullPointerException e) {}
    }

    @FXML
    protected void adresseValidieren() throws Exception{
        String eingabe = tvEBike.getText();
        List<Ort> moeglicheOrte = triasCOM.getInstance().locSuche(eingabe);
        eigFahrrad = ortAuswaehlen(moeglicheOrte);
        eigFahrrad.setName("Eigenes Fahrrad");
    }

    private Ort ortAuswaehlen(List<Ort> orte) {
        Ort gewaehlt;
        Ortauswahl oAus;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/Ortauswahl.fxml"));
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(new Scene((SplitPane) loader.load()));
            oAus = loader.getController();
            oAus.initData(orte);
            secondaryStage.setTitle("Verbindungen");
            secondaryStage.showAndWait();
        } catch (Exception e) {
            oAus = null;
            System.out.println(e);
        }
        gewaehlt = oAus.gewaehlt;
        return gewaehlt;
    }

    @FXML
    protected void ebikeAction() {
        if(cbxEBike.isSelected()) {
            tvEBike.setDisable(false);
            btnValid.setDisable(false);
        } else {
            tvEBike.setDisable(true);
            btnValid.setDisable(true);
        }
    }

    @FXML
    protected void submit() {
        einst.setIvov(cbxIVOEV.isSelected());
        einst.setOviv(cbxOEVIV.isSelected());
        einst.setNextbikeAktiv(cbxNextbike.isSelected());
        einst.setEigenesFahrradAktiv(cbxEBike.isSelected());
        if(cbxEBike.isSelected()){
            if(eigFahrrad == null) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Eingabefehler");
                a.setContentText("Wenn sie ein eigenes Fahrrad verwenden möchten, müssen sie auch einen Ort angeben!");
                a.showAndWait();
                return;
            } else {
                einst.setEigenesFahrrad(eigFahrrad);
            }
        } else {}
        Stage stage = (Stage) cbxEBike.getScene().getWindow();
        stage.close();
    }
}
