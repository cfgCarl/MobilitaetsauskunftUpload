package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

import java.util.List;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

//Objekt Fahrradstation

public class BikeStation extends Ort{
    private int anzahlBike;

    public int getAnzahlBike() {
        return anzahlBike;
    }

    public void setAnzahlBike(int anzahlBike) {
        this.anzahlBike = anzahlBike;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    private int stationID;
    public BikeStation() {

    }

    public BikeStation(int id, int anzahl, Coordinate position, String name, String adresse) {
        setAnzahlBike(anzahl);
        setStationID(id);
        setAdresse(adresse);
        setName(name);
        setPosition(position);
    }

}
