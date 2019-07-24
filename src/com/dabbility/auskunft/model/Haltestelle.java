package com.dabbility.auskunft.model;

import com.sothawo.mapjfx.Coordinate;

import java.util.List;

//Haltestellen Objekt

public class Haltestelle extends Ort {

    private String KVVID;
    private double[] KVVposition;
    private List<Integer> productClasses;

    public String getKVVID() {
        return KVVID;
    }

    public void setKVVID(String KVVID) {
        this.KVVID = KVVID;
    }

    public double[] getKVVposition() {
        return KVVposition;
    }

    public void setKVVposition(double[] KVVposition) {
        this.KVVposition = KVVposition;
    }


    public List<Integer> getProductClasses() {
        return productClasses;
    }

    public void setProductClasses(List<Integer> productClasses) {
        this.productClasses = productClasses;
    }

    public Haltestelle(String name, String adresse, Coordinate position, String KVVID, double[] KVVposition, List<Integer> productClasses) {
        super(name, adresse, position);
        this.KVVID = KVVID;
        this.KVVposition = KVVposition;
        this.productClasses = productClasses;
    }

}
