package com.dabbility.auskunft.model;

import com.dabbility.auskunft.COMs.ghCOM;
import com.dabbility.auskunft.COMs.graphHopper_COM;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.util.PointList;
import com.sothawo.mapjfx.Coordinate;

import java.util.ArrayList;
import java.util.List;

//Orts Objekt

public class Ort implements Comparable<Ort>{
    private String name;
    private String adresse;
    private Coordinate position;
    private Haltestelle hst;
    private long dauer; //milisekunden
    private List<Coordinate> route;
    private Double entfernung = 9999999.9;


    public Double getZwischenErgebnisEntfernung() {
        return entfernung;
    }

    public void setZwischenErgebnisEntfernung(double zwischenErgebnisEntfernung) {
        this.entfernung = zwischenErgebnisEntfernung;
    }

    public long getDauer() {
        return dauer;
    }

    public void setDauer(long dauer) {
        this.dauer = dauer;
    }

    public List<Coordinate> getRoute() {
        return route;
    }

    public void setRoute(List<Coordinate> route) {
        this.route = route;
    }

    public Double getEntfernung() {
        return entfernung;
    }

    public void setEntfernung(Double entfernung) {
        this.entfernung = entfernung;
    }

    public Haltestelle getHst() {
        return hst;
    }

    public void setHst(Haltestelle hst) {
        this.hst = hst;
    }

    public Ort() {

    }

    public Ort ortClone(){
        Ort copy = new Ort(getName(), getAdresse(), getPosition());
        return copy;
    }

    public Ort(String name, String adresse, Coordinate position) {
        setName(name);
        setAdresse(adresse);
        setPosition(position);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Coordinate getPosition() {
        return position;
    }
    public void setPosition(double Lat, double Lng) {
        Coordinate p = new Coordinate(Lat, Lng);
        this.position = p;
    }
    public void setPosition(Coordinate position) {
        this.position = position;
    }




    public int compareTo(Ort ort) {
        return this.getZwischenErgebnisEntfernung().compareTo(ort.getZwischenErgebnisEntfernung());
    }


}
