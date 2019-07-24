package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.paint.Color;

public class oevVM extends Verkehrsmittel {
    Einstellungen s = Einstellungen.getInstance();
    public oevVM() {
        setGeschwindigkeit(50);
        setKalorienVerbrauch(0);
        setSitzplaetze(50);
        setStehplaetze(50);
        setTitle("Allg. ÖV Verkehrsmittel");
        setTopSpeed(100);
        setID(20);
        setEmissionen(new Emissionen(65, 0.04, 0.00, 0.06, 0.000));
        setColor(Color.BLACK);
    }
    public oevVM(String n) {
        setGeschwindigkeit(50);
        setKalorienVerbrauch(0);
        setSitzplaetze(50);
        setStehplaetze(50);
        setTitle("Allg. ÖV Verkehrsmittel " + n);
        setTopSpeed(100);
        setID(20);
        setEmissionen(new Emissionen(65, 0.04, 0.00, 0.06, 0.000));
        setColor(Color.BLACK);
    }
}
