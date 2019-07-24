package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

import java.util.Date;
import java.util.List;

//TeilVerbindungsobjekt

public class TeilVerbindung extends Verbindung {

    public TeilVerbindung(Ort startPunkt, Ort zielPunkt, long dauer, Verkehrsmittel verkehrsmittel, Date abf, Date ank, List<Coordinate> coordList) {
        setStartPunkt(startPunkt);
        setZielPunkt(zielPunkt);
        setDauer(dauer);
        setVerkehrsmittel(verkehrsmittel);
        setAbfahrtsZeit(abf);
        setAnkunftsZeit(ank);
        setCoordListe(coordList);
    }

    public TeilVerbindung(Ort startPunkt, Ort zielPunkt, long dauer, Verkehrsmittel verkehrsmittel, List<Coordinate> coordList) {
        setStartPunkt(startPunkt);
        setZielPunkt(zielPunkt);
        setDauer(dauer);
        setVerkehrsmittel(verkehrsmittel);
        setCoordListe(coordList);
    }
}
