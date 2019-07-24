package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//GesamtVerbindungs Objekt

public class GesamtVerbindung extends Verbindung {
    List<Verbindungsabschnitt> verbindungsAbfolge;
    private boolean direktverbindung = false;

    public boolean isDirektverbindung() {
        return direktverbindung;
    }

    public void setDirektverbindung(boolean direktverbindung) {
        this.direktverbindung = direktverbindung;
    }


    public GesamtVerbindung(Ort start, Ort ziel) {
        this.setZielPunkt(ziel);
        this.setStartPunkt(start);
    }

    public GesamtVerbindung(Ort start, Ort ziel, List<Verbindungsabschnitt> verbindungsAbfolge) {
        this.verbindungsAbfolge = verbindungsAbfolge;
        this.setZielPunkt(ziel);
        this.setStartPunkt(start);
        setAbfahrtsZeit(verbindungsAbfolge.get(0).teilverb.abfahrtsZeit);
        setAnkunftsZeit(verbindungsAbfolge.get(verbindungsAbfolge.size()-1).teilverb.ankunftsZeit);
        long diffInMillies = Math.abs(getAnkunftsZeit().getTime() - getAbfahrtsZeit().getTime());
        setDauer(diffInMillies);
    }

    public List<Verbindungsabschnitt> getVerbindungsAbfolge() {
        return verbindungsAbfolge;
    }

    public void setVerbindungsAbfolge(List<Verbindungsabschnitt> verbindungsAbfolge) {
        this.verbindungsAbfolge = verbindungsAbfolge;
        setAbfahrtsZeit(verbindungsAbfolge.get(0).teilverb.abfahrtsZeit);
        setAnkunftsZeit(verbindungsAbfolge.get(verbindungsAbfolge.size()-1).teilverb.ankunftsZeit);
        long diffInMillies = Math.abs(getAnkunftsZeit().getTime() - getAbfahrtsZeit().getTime());
        setDauer(diffInMillies);
    }

    public List<Coordinate> getCoord() {
        List<Coordinate> list = new ArrayList<>();
        for(Verbindungsabschnitt vba:this.getVerbindungsAbfolge()) {
            list.addAll(vba.teilverb.coordListe);
        }
        return list;
    }

    @Override
    public Emissionen getEmmissionen() {
        double treibhausgase = 0;
        double kohlenmonoxid = 0;
        double kohlenwasserstoffe = 0;
        double stickoxid = 0;
        double feinstaub = 0;
        for(Verbindungsabschnitt vba:verbindungsAbfolge) {
            treibhausgase += vba.getTeilverb().getEmmissionen().getTreibhausgase();
            kohlenmonoxid += vba.getTeilverb().getEmmissionen().getKohlenmonoxid();
            kohlenwasserstoffe += vba.getTeilverb().getEmmissionen().getKohlenwasserstoffe();
            stickoxid += vba.getTeilverb().getEmmissionen().getStickoxid();
            feinstaub += vba.getTeilverb().getEmmissionen().getFeinstaub();
        }
        return new Emissionen(treibhausgase, kohlenmonoxid, kohlenwasserstoffe, stickoxid, feinstaub);
    }
}
