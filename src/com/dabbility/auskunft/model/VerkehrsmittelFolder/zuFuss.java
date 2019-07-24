package com.dabbility.auskunft.model.VerkehrsmittelFolder;

import com.dabbility.auskunft.model.Emissionen;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Verkehrsmittel;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class zuFuss extends Verkehrsmittel{
        Einstellungen s = Einstellungen.getInstance();
        public zuFuss() {
            setGeschwindigkeit(5);
            setKalorienVerbrauch(0);
            setSitzplaetze(0);
            setStehplaetze(1);
            setTitle("Zu Fuß");
            setTopSpeed(7);
            setID(99);
            setEmissionen(new Emissionen(0,0,0,0,0));
            setColor(Color.DIMGRAY);
            setImage(new Image("/fuss.png"));
        }


}
