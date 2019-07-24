package com.dabbility.auskunft.model;

import com.dabbility.auskunft.model.VerkehrsmittelFolder.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//Verkehrsmittelobjekt

public class Verkehrsmittel {
    public double getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(double geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public int getStehplaetze() {
        return stehplaetze;
    }

    public void setStehplaetze(int stehplaetze) {
        this.stehplaetze = stehplaetze;
    }

    public int getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public double getKalorienVerbrauch() {
        return kalorienVerbrauch;
    }

    public void setKalorienVerbrauch(double kalorienVerbrauch) {
        this.kalorienVerbrauch = kalorienVerbrauch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    double geschwindigkeit; //km/h
    int stehplaetze;
    int sitzplaetze;
    double kalorienVerbrauch;
    String title;
    double topSpeed;
    int ID;
    Emissionen emissionen;
    Color color = Color.BLACK;
    Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Emissionen getEmissionen() {
        return emissionen;
    }

    public void setEmissionen(Emissionen emissionen) {
        this.emissionen = emissionen;
    }

    public Verkehrsmittel() {

    }

    static public Verkehrsmittel Verkehrsmittel(int id) {
        switch(id) {
            case 3 | 11:
                return new Stadtbahn();
            case 0 | 4:
                return new Tram();
            case 3 | 5:
                return new Bus();
            case 51:
                return new nextBike();
            case 99:
                return new zuFuss();
            default:
                return new oevVM("ID: " + id);
        }

    }
}
