package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

import java.util.Date;
import java.util.List;

//Verbindungsobjekt

public class Verbindung {
    Ort startPunkt;
    Ort zielPunkt;
    long dauer;//millisekunden
    Date abfahrtsZeit;
    Date ankunftsZeit;
    Verkehrsmittel verkehrsmittel;
    List<Coordinate> coordListe;
    double entfernung;
    String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Emissionen getEmmissionen() {
        Emissionen emi;

        emi = new Emissionen(verkehrsmittel.emissionen.getTreibhausgase() * entfernung, verkehrsmittel.emissionen.getKohlenmonoxid() * entfernung, verkehrsmittel.emissionen.getKohlenwasserstoffe() * entfernung, verkehrsmittel.emissionen.getStickoxid() * entfernung, verkehrsmittel.emissionen.getFeinstaub() * entfernung);

        return emi;
    }

    public double getEntfernung() {
        return entfernung;
    }

    public void setEntfernung(double entfernung) {
        this.entfernung = entfernung;
    }
    public void setEntfernung(List<Coordinate> coordListe) {
        double entf = 0;
        for(int i = 0; i< (coordListe.size() - 1); i++) {
            double distance = 9999999;
            double dx = (111.3 * Math.cos((coordListe.get(i).getLatitude() + coordListe.get(i+1).getLatitude()) / 2)) * (coordListe.get(i).getLongitude() -  coordListe.get(i+1).getLongitude());
            double dy = 111.3 * (coordListe.get(i).getLatitude() -  coordListe.get(i+1).getLatitude());
            distance = Math.sqrt((dx * dx) + (dy * dy));
            entf = entf + distance;
        }
        this.entfernung = entf;
    }

    public List<Coordinate> getCoordListe() {
        return coordListe;
    }

    public void setCoordListe(List<Coordinate> coordListe) {
        setEntfernung(coordListe);
        this.coordListe = coordListe;
    }

    public Verkehrsmittel getVerkehrsmittel() {
        return verkehrsmittel;
    }

    public void setVerkehrsmittel(Verkehrsmittel verkehrsmittel) {
        this.verkehrsmittel = verkehrsmittel;
    }

    public Ort getStartPunkt() {
        return startPunkt;
    }

    public void setStartPunkt(Ort startPunkt) {
        this.startPunkt = startPunkt;
    }

    public Ort getZielPunkt() {
        return zielPunkt;
    }

    public void setZielPunkt(Ort zielPunkt) {
        this.zielPunkt = zielPunkt;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }

    public Date getAbfahrtsZeit() {
        return abfahrtsZeit;
    }

    public void setAbfahrtsZeit(Date abfahrtsZeit) {
        this.abfahrtsZeit = abfahrtsZeit;
    }

    public Date getAnkunftsZeit() {
        return ankunftsZeit;
    }

    public void setAnkunftsZeit(Date ankunftsZeit) {
        this.ankunftsZeit = ankunftsZeit;
    }

    public Emissionen emissionenBerechnen(Emissionen vmEmi, double km) {
        vmEmi.setFeinstaub(vmEmi.getFeinstaub() * km);
        vmEmi.setKohlenmonoxid(vmEmi.getKohlenmonoxid() * km);
        vmEmi.setKohlenwasserstoffe(vmEmi.getKohlenwasserstoffe() * km);
        vmEmi.setStickoxid(vmEmi.getStickoxid() * km);
        vmEmi.setTreibhausgase(vmEmi.getTreibhausgase() * km);
        return vmEmi;
    }
}
