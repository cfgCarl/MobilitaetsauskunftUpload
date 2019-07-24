package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Tram extends Verkehrsmittel {
    Einstellungen s = Einstellungen.getInstance();
    public Tram() {
        setGeschwindigkeit(50);
        setKalorienVerbrauch(0);
        setSitzplaetze(100);
        setStehplaetze(50);
        setTitle("Straßenbahn");
        setTopSpeed(70);
        setID(4);
        setEmissionen(new Emissionen(65, 0.04, 0.00, 0.06, 0.000));
        setColor(Color.RED);
        setImage(new Image("/tram.png"));
    }
}
