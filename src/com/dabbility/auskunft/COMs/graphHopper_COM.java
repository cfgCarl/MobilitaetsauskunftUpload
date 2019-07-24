package com.dabbility.auskunft.COMs;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

import java.util.Locale;

//Klasse zur Kommunikation mit GraphHopper (via ghCOM)

public class graphHopper_COM {
    GraphHopper hopper;
    private static graphHopper_COM ourInstance = new graphHopper_COM();

    public static graphHopper_COM getInstance() {
        return ourInstance;
    }

    private graphHopper_COM() {
        hopper = new GraphHopperOSM().forServer();
        hopper.setGraphHopperLocation("res/graphHopperData");
        hopper.setDataReaderFile("karlsruhe-regbez-latest.osm.pbf");
        hopper.setEncodingManager(new EncodingManager("car,bike,foot"));

        hopper.importOrLoad();
    }

    public GHResponse bikeRouting(double latFrom, double lonFrom, double latTo, double lonTo){
        GHRequest req = new GHRequest(latFrom, lonFrom, latTo, lonTo).
                setWeighting("fastest").
                setVehicle("bike").
                setLocale(Locale.GERMANY);
        GHResponse rsp = hopper.route(req);

        if(rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return null;
        }

        PathWrapper path = rsp.getBest();

        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        InstructionList il = path.getInstructions();

        return rsp;
    }
    public GHResponse carRouting(double latFrom, double lonFrom, double latTo, double lonTo){
        GHRequest req = new GHRequest(latFrom, lonFrom, latTo, lonTo).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.GERMANY);
        GHResponse rsp = hopper.route(req);

        if(rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return null;
        }

        PathWrapper path = rsp.getBest();

        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        InstructionList il = path.getInstructions();

        return rsp;
    }
    public GHResponse footRouting(double latFrom, double lonFrom, double latTo, double lonTo){
        GHRequest req = new GHRequest(latFrom, lonFrom, latTo, lonTo).
                setWeighting("fastest").
                setVehicle("foot").
                setLocale(Locale.GERMANY);
        GHResponse rsp = hopper.route(req);

        if(rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return null;
        }

        PathWrapper path = rsp.getBest();

        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        InstructionList il = path.getInstructions();

        return rsp;
    }
}
