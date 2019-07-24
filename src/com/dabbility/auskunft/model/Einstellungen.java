package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

//EinstellungsSingleton

public class Einstellungen {
    private static Einstellungen ourInstance = new Einstellungen();

    public static Einstellungen getInstance() {
        return ourInstance;
    }

    private Einstellungen() {
    }
    private double nextBikeGeschwindigkeit;
    private double nextBikeTopSpeed;
    private double bikeGeschwindigkeit;
    private double bikeTopSpeed;
    private double carGeschwindigkeit;
    private double carTopSpeed;
    private double groesse; //m
    private double gewicht; //kg
    private double kalorienverbrauch;
    private String name;
    private Ort eigenesFahrrad;
    private Boolean eigenesFahrradAktiv = false;
    private Boolean nextbikeAktiv = true;
    private Boolean stadtmobilAktiv = false;
    private Boolean ivov = true;
    private Boolean oviv = true;

    public Ort getEigenesFahrrad() {
        return eigenesFahrrad;
    }

    public void setEigenesFahrrad(Ort eigenesFahrrad) {
        this.eigenesFahrrad = eigenesFahrrad;
    }

    public Boolean getEigenesFahrradAktiv() {
        return eigenesFahrradAktiv;
    }

    public void setEigenesFahrradAktiv(Boolean eigenesFahrradAktiv) {
        this.eigenesFahrradAktiv = eigenesFahrradAktiv;
    }

    public Boolean getNextbikeAktiv() {
        return nextbikeAktiv;
    }

    public void setNextbikeAktiv(Boolean nextbikeAktiv) {
        this.nextbikeAktiv = nextbikeAktiv;
    }

    public Boolean getStadtmobilAktiv() {
        return stadtmobilAktiv;
    }

    public void setStadtmobilAktiv(Boolean stadtmobilAktiv) {
        this.stadtmobilAktiv = stadtmobilAktiv;
    }

    public Boolean getIvov() {
        return ivov;
    }

    public void setIvov(Boolean ivov) {
        this.ivov = ivov;
    }

    public Boolean getOviv() {
        return oviv;
    }

    public void setOviv(Boolean oviv) {
        this.oviv = oviv;
    }

    public double getNextBikeGeschwindigkeit() {
        return nextBikeGeschwindigkeit;
    }

    public void setNextBikeGeschwindigkeit(double nextBikeGeschwindigkeit) {
        this.nextBikeGeschwindigkeit = nextBikeGeschwindigkeit;
    }

    public double getNextBikeTopSpeed() {
        return nextBikeTopSpeed;
    }

    public void setNextBikeTopSpeed(double nextBikeTopSpeed) {
        this.nextBikeTopSpeed = nextBikeTopSpeed;
    }

    public double getBikeGeschwindigkeit() {
        return bikeGeschwindigkeit;
    }

    public void setBikeGeschwindigkeit(double bikeGeschwindigkeit) {
        this.bikeGeschwindigkeit = bikeGeschwindigkeit;
    }

    public double getBikeTopSpeed() {
        return bikeTopSpeed;
    }

    public void setBikeTopSpeed(double bikeTopSpeed) {
        this.bikeTopSpeed = bikeTopSpeed;
    }

    public double getCarGeschwindigkeit() {
        return carGeschwindigkeit;
    }

    public void setCarGeschwindigkeit(double carGeschwindigkeit) {
        this.carGeschwindigkeit = carGeschwindigkeit;
    }

    public double getCarTopSpeed() {
        return carTopSpeed;
    }

    public void setCarTopSpeed(double carTopSpeed) {
        this.carTopSpeed = carTopSpeed;
    }

    public double getGroesse() {
        return groesse;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public double getKalorienverbrauch() {
        return kalorienverbrauch;
    }

    public void setKalorienverbrauch(double kalorienverbrauch) {
        this.kalorienverbrauch = kalorienverbrauch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
