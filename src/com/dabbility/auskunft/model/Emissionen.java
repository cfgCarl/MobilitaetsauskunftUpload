package com.dabbility.auskunft.model;

//Emissionen Objekt

public class Emissionen {
    private double treibhausgase;
    private double kohlenmonoxid;
    private double kohlenwasserstoffe;
    private double stickoxid;
    private double feinstaub;

    public double getTreibhausgase() {
        return treibhausgase;
    }

    public void setTreibhausgase(double treibhausgase) {
        this.treibhausgase = treibhausgase;
    }

    public double getKohlenmonoxid() {
        return kohlenmonoxid;
    }

    public void setKohlenmonoxid(double kohlenmonoxid) {
        this.kohlenmonoxid = kohlenmonoxid;
    }

    public double getKohlenwasserstoffe() {
        return kohlenwasserstoffe;
    }

    public void setKohlenwasserstoffe(double kohlenwasserstoffe) {
        this.kohlenwasserstoffe = kohlenwasserstoffe;
    }

    public double getStickoxid() {
        return stickoxid;
    }

    public void setStickoxid(double stickoxid) {
        this.stickoxid = stickoxid;
    }

    public double getFeinstaub() {
        return feinstaub;
    }

    public void setFeinstaub(double feinstaub) {
        this.feinstaub = feinstaub;
    }

    public Emissionen(double treibhausgase, double kohlenmonoxid, double kohlenwasserstoffe, double stickoxid, double feinstaub) {
        this.treibhausgase = treibhausgase;
        this.kohlenmonoxid = kohlenmonoxid;
        this.kohlenwasserstoffe = kohlenwasserstoffe;
        this.stickoxid = stickoxid;
        this.feinstaub = feinstaub;
    }


}
