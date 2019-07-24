package com.dabbility.auskunft.model;

//Verbindungsabschnittsobjekt

public class Verbindungsabschnitt {
    Ort ort;
    TeilVerbindung teilverb;

    public Ort getOrt() {
        return ort;
    }

    public void setOrt(Ort ort) {
        this.ort = ort;
    }

    public TeilVerbindung getTeilverb() {
        return teilverb;
    }

    public void setTeilverb(TeilVerbindung teilverb) {
        this.teilverb = teilverb;
    }

    public Verbindungsabschnitt(Ort ort) {
        this.ort = ort;
        this.teilverb = null;
    }

    public Verbindungsabschnitt(TeilVerbindung teilverb) {
        this.teilverb = teilverb;
        this.ort = null;
    }
}
