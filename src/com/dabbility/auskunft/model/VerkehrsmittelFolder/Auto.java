package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Auto extends Verkehrsmittel{
        Einstellungen s = Einstellungen.getInstance();
        public Auto() {
            setGeschwindigkeit(80);
            setKalorienVerbrauch(0);
            setSitzplaetze(40);
            setStehplaetze(20);
            setTitle("Auto");
            setTopSpeed(100);
            setID(5);
            setEmissionen(new Emissionen(139, 0.60, 0.14, 0.34, 0.004));
            setColor(Color.DARKVIOLET);
            setImage(new Image("/auto.png"));
        }


}
