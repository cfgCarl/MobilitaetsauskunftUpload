package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.model.TeilVerbindung;
import com.dabbility.auskunft.model.Verbindungsabschnitt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Controller für eine Zeile der Verbindungsdetails

public class teilVerbindungListViewCell extends ListCell<Verbindungsabschnitt> {

    @FXML
    Label lblStartZeit;

    @FXML
    Label lblZielZeit;

    @FXML
    Label lblStart;

    @FXML
    Label lblZiel;

    @FXML
    AnchorPane aPane;

    @FXML
    Label lblVM;

    @FXML
    ImageView ivVM;

    @FXML
    Pane imgPane;

    FXMLLoader mLLoader;

    @Override
    protected void updateItem(Verbindungsabschnitt va, boolean empty) {
        super.updateItem(va, empty);

        if(empty || va == null){
            setText(null);
            setGraphic(null);
        } else{
            if(mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/detailZeile.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            DateFormat df = new SimpleDateFormat("HH:mm");
            lblStartZeit.setText("test");
            lblStartZeit.setText(df.format(va.getTeilverb().getAbfahrtsZeit()));
            lblZielZeit.setText(df.format(va.getTeilverb().getAnkunftsZeit()));
            lblStart.setText(va.getTeilverb().getStartPunkt().getName());
            lblZiel.setText(va.getTeilverb().getZielPunkt().getName());
            ivVM.setImage(va.getTeilverb().getVerkehrsmittel().getImage());
            imgPane.setStyle("-fx-background-color: " + toRGBLightCode(va.getTeilverb().getVerkehrsmittel().getColor().brighter()) + ";");

            setText(null);
            setGraphic(aPane);

        }

    }
    public static String toRGBLightCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
}
