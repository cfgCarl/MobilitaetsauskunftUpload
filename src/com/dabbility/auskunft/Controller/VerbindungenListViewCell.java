package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.GesamtVerbindung;
import com.dabbility.auskunft.model.TeilVerbindung;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Controller für eine Zeile der Verbindungsübersicht

public class VerbindungenListViewCell extends ListCell<GesamtVerbindung> {

    @FXML
    Label lblStartZeit;

    @FXML
    Label lblZielZeit;

    @FXML
    Label lblDauer;

    @FXML
    Label lblUmstiege;

    @FXML
    AnchorPane aPane;

    @FXML
    Label lblDirekt;

    @FXML
    Label lblVBX;

    FXMLLoader mLLoader;

    @Override
    protected void updateItem(GesamtVerbindung tv, boolean empty) {
        super.updateItem(tv, empty);

        if(empty || tv == null){
            setText(null);
            setGraphic(null);
        } else{
            if(mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/verbZeile.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            DateFormat df = new SimpleDateFormat("HH:mm");
            lblStartZeit.setText(df.format(tv.getAbfahrtsZeit()));
            lblZielZeit.setText(df.format(tv.getAnkunftsZeit()));
            lblDauer.setText(String.valueOf((int) tv.getDauer()/(1000*60)) + " Minuten");
            if (tv.isDirektverbindung()) {
                lblDirekt.setText("reine Bahnverbindung");
                setStyle("-fx-background-color: cyan;");
            } else {
                lblDirekt.setText("gemischte Verbindung");
            }
            if(tv.getName() != null) {
                lblDirekt.setText(tv.getName());
            }
            lblVBX.setText(tv.getName());
            lblUmstiege.setText(String.valueOf((int) (tv.getVerbindungsAbfolge().size() / 2)) + " Umstiege");
            setText(null);
            setGraphic(aPane);

        }
    }
}
