package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bus extends Verkehrsmittel {
    Einstellungen s = Einstellungen.getInstance();
    public Bus() {
        setGeschwindigkeit(80);
        setKalorienVerbrauch(0);
        setSitzplaetze(40);
        setStehplaetze(20);
        setTitle("Bus");
        setTopSpeed(100);
        setID(5);
        setEmissionen(new Emissionen(75, 0.05, 0.03, 0.32, 0.002));
        setColor(Color.PURPLE);
        setImage(new Image("/bus.png"));
    }
}
