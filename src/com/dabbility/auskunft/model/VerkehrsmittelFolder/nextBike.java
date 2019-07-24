package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class nextBike extends Verkehrsmittel {
    Einstellungen s = Einstellungen.getInstance();

    public nextBike() {
        setGeschwindigkeit(s.getNextBikeGeschwindigkeit());
        setKalorienVerbrauch(s.getKalorienverbrauch());
        setSitzplaetze(1);
        setStehplaetze(0);
        setTitle("Fahrrad (nextBike)");
        setTopSpeed(s.getNextBikeTopSpeed());
        setID(51);
        setEmissionen(new Emissionen(0, 0, 0, 0, 0));
        setColor(Color.BLUE);
        setImage(new Image("/nextbike2.jpg"));
    }
}
