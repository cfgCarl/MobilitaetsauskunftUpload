package com.dabbility.auskunft.COMs;

import com.dabbility.auskunft.model.Ort;
import com.dabbility.auskunft.model.TeilVerbindung;
import com.dabbility.auskunft.model.Verbindung;
import com.dabbility.auskunft.model.VerkehrsmittelFolder.Auto;
import com.dabbility.auskunft.model.VerkehrsmittelFolder.nextBike;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.PointList;
import com.sothawo.mapjfx.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//Klasse zur Kommunikation mit GraphHopper

public class ghCOM {
    GraphHopper hopper;
    private static ghCOM ourInstance = new ghCOM();

    public static ghCOM getInstance() {
        return ourInstance;
    }

    private ghCOM() {
        hopper = new GraphHopperOSM().forServer();
        hopper.setGraphHopperLocation("res/graphHopperData");
        hopper.setDataReaderFile("karlsruhe-regbez-latest.osm.pbf");
        hopper.setEncodingManager(new EncodingManager("car,bike,foot"));
        hopper.importOrLoad();
    }

    public List<Ort> orteInNaeheFinden(int anzahlErgebnisOrte, List<Ort> eingabeOrte, Ort zentrum, double radius){
        List<Ort> endergebnis = new ArrayList<>();
        List<Ort> zwischen1 = new ArrayList<>();
        List<Ort> zwischen2 = new ArrayList<>();
        zwischen1 = orteImRadius(eingabeOrte, zentrum, radius);
        zwischen2 = orteSortierenEntfernung(zwischen1);
        endergebnis = orteReduzierenAnzahl(zwischen2, anzahlErgebnisOrte);
        return endergebnis;
    }

    public List<Ort> orteBeiAnderenOrten(List<Ort> sortierOrte, List<Ort> bindeOrte, double bindeRadius) {
        List<Ort> orteMitOrten = new ArrayList<>();
        for(Ort o: sortierOrte){
            List<Ort> naheHst = new ArrayList<>();
            for(Ort h: bindeOrte) {
                double distance = 9999999;
                double dx = (111.3 * Math.cos((o.getPosition().getLatitude() + h.getPosition().getLatitude()) / 2)) * (o.getPosition().getLongitude() - h.getPosition().getLongitude());
                double dy = 111.3 * (o.getPosition().getLatitude() - h.getPosition().getLatitude());
                distance = Math.sqrt((dx*dx) + (dy*dy));
                if(distance < bindeRadius) {naheHst.add(h);}
            }
            if(!naheHst.isEmpty()) {orteMitOrten.add(o);}
        }
        return orteMitOrten;
    }

    private List<Ort> orteImRadius(List<Ort> eingabeOrte, Ort zentrum, double radius) {
        List<Ort> ergOrte = new ArrayList<>();
        List<Ort> zOrte = new ArrayList<>();
        for(Ort oz:eingabeOrte) {
            double dx = (111.3 * Math.cos((zentrum.getPosition().getLatitude() + oz.getPosition().getLatitude()) / 2)) * (zentrum.getPosition().getLongitude() - oz.getPosition().getLongitude());
            double dy = 111.3 * (zentrum.getPosition().getLatitude() - oz.getPosition().getLatitude());
            double dis = Math.sqrt((dx * dx) + (dy * dy));
            if (Math.abs(dis) < radius) {
                zOrte.add(oz);
            }
        }
            for(Ort o:zOrte) {
                PathWrapper bestRoute;
                try {
                    bestRoute = graphHopper_COM.getInstance().bikeRouting(o.getPosition().getLatitude(), o.getPosition().getLongitude(), zentrum.getPosition().getLatitude(), zentrum.getPosition().getLongitude()).getBest();
                } catch (NullPointerException e) {
                    System.out.println(e.toString());
                    bestRoute = null;
                }
                if (bestRoute != null) {
                    o.setEntfernung(bestRoute.getDistance() / 1000);
                    o.setDauer(bestRoute.getTime()); //milisekunden
                    PointList points = bestRoute.getPoints();
                    List<Coordinate> ponts = new ArrayList<>();
                    for (int i = 0; i < points.size(); i++) {
                        Coordinate paar = new Coordinate(points.getLat(i), points.getLon(i));
                        ponts.add(paar);
                    }
                    o.setRoute(ponts);
                    System.out.println("Von: " + o.getName() + " Nach: " + zentrum.getName() + " Entfernung: " + o.getEntfernung() + " Dauer: " + o.getDauer());
                    if (o.getEntfernung() < radius) {
                        ergOrte.add(o);
                    }
                }
            }




        return ergOrte;
    }

    private List<Ort> orteSortierenEntfernung(List<Ort> eingabeOrte) {
        Collections.sort(eingabeOrte);
        return eingabeOrte;
    }

    private List<Ort> orteReduzierenAnzahl(List<Ort> eingabeOrte, int anzahl) {
        List<Ort> redListe = new ArrayList<>();
        if(eingabeOrte.size() - 1 >= anzahl) {
            redListe.addAll(eingabeOrte.subList(0, anzahl - 1));
        } else {redListe = eingabeOrte;}
        return redListe;
    }

    public TeilVerbindung bikeRouting(Ort start, Ort ziel) {
        PathWrapper bestRoute;
        bestRoute = graphHopper_COM.getInstance().bikeRouting(start.getPosition().getLatitude(), start.getPosition().getLongitude(), ziel.getPosition().getLatitude(), ziel.getPosition().getLongitude()).getBest();

        PointList points = bestRoute.getPoints();
        List<Coordinate> ponts = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Coordinate paar = new Coordinate(points.getLat(i), points.getLon(i));
            ponts.add(paar);
        }
        TeilVerbindung vb = new TeilVerbindung(start, ziel, bestRoute.getTime(), new nextBike(), ponts);
        vb.setEntfernung(bestRoute.getDistance()/1000);
        return vb;
    }
    public TeilVerbindung carRouting(Ort start, Ort ziel) {
        PathWrapper bestRoute;
        bestRoute = graphHopper_COM.getInstance().carRouting(start.getPosition().getLatitude(), start.getPosition().getLongitude(), ziel.getPosition().getLatitude(), ziel.getPosition().getLongitude()).getBest();

        PointList points = bestRoute.getPoints();
        List<Coordinate> ponts = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Coordinate paar = new Coordinate(points.getLat(i), points.getLon(i));
            ponts.add(paar);
        }
        TeilVerbindung vb = new TeilVerbindung(start, ziel, bestRoute.getTime(), new Auto(), ponts);
        vb.setEntfernung(bestRoute.getDistance()/1000);
        return vb;
    }

}
