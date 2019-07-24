package com.dabbility.auskunft.COMs;

import com.dabbility.auskunft.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sothawo.mapjfx.Coordinate;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//Klasse zur Kommunikation mit Trias

public class triasCOM {
    private static triasCOM ourInstance = new triasCOM();

    public static triasCOM getInstance() {
        return ourInstance;
    }

    URL nurl;
    private final String USER_AGENT = "Mozilla/5.0";

    public void init() {
        //locationInformation();
    }
    private void locationInformation() {
        try {
            URL myURL = new URL("http://smartmmi.demo.mentz.net/smartmmi/XML_STOPFINDER_REQUEST?outputFormat=rapidJson&type_sf=any&name_sf=mto");
            //URL myURL = new URL("https://ptsv2.com/t/0m0d3-1557665829/post?outputFormat=rapidJson&type_sf=any&name_sf=mto");

            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            //connection.setRequestProperty("Accept", "*/*");
            //connection.setRequestProperty("Cache-Control", "no-cache");
            //connection.setRequestProperty("Host", "ptsv2.com");
            //connection.setRequestProperty("Postman-Token", "3cab2480-860f-44c7-8aef-713de1a0db34");
            //connection.setRequestProperty("User-Agent", "\tPostmanRuntime/7.11.0");
            //connection.setRequestProperty("X-Cloud-Trace-Context", "6a169caf170aa727e85d85a80049f934/4837562381445732698");
            //connection.setRequestProperty("X-Google-Apps-Metadata", "domain=gmail.com,host=ptsv2.com");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder results = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line);
            }

            connection.disconnect();
            System.out.println(results.toString());
            //String url = "http://openservice-test.vrr.de/static02/XML_STOPFINDER_REQUEST";
            String url = "https://ptsv2.com/t/0m0d3-1557665829/post?outputFormat=rapidJson&type_sf=any&name_sf=mto";
            //url += "?outputFormat=rapidJson&sessionID=0&requestID=0&language=DE";
            nurl = new URL(url);
            HttpURLConnection con = (HttpURLConnection) nurl.openConnection();

            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setRequestProperty("User-Agent", USER_AGENT);

            System.out.println("respMssg" + con.getRequestProperties());

            //con.setConnectTimeout(50000);
            //con.setReadTimeout(50000);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());

            out.flush();
            out.close();

            for(int i = 0; i < 10; i++){
                System.out.println(con.getHeaderField(i));
            }

            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            List<Haltestelle> hsts = new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(in);
            JsonNode locations = rootNode.findPath("locations");
            Iterator<JsonNode> LocS = locations.elements();

            while(LocS.hasNext()){
                String name = null;
                String adresse = null;
                Coordinate position = new Coordinate(0.1,0.1);
                String KVVid = null;
                double[] KVVposition = {0.0, 0.0};
                List<Integer> productCalsses = null;

                JsonNode loc = LocS.next();

                name = loc.findPath("name").asText();
                adresse = name;
                //position[0] = place.findPath("lat").asDouble();
                //position[1] = place.findPath("lng").asDouble();
                KVVid = loc.findPath("id").asText();
                KVVposition[0] = loc.findPath("coord").path(0).asDouble();
                KVVposition[1] = loc.findPath("coord").path(1).asDouble();

                JsonNode pCs = loc.findPath("productClasses");
                Iterator<JsonNode> pCIterator = pCs.elements();

                while(pCIterator.hasNext()){
                    JsonNode productClass = pCIterator.next();
                    productCalsses.add(productClass.asInt());
                }

                Haltestelle hst = new Haltestelle(name, adresse, position, KVVid, KVVposition, productCalsses);
                hsts.add(hst);

            }

            /*List<BikeStation> stationen = new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(in);
            JsonNode placesNode = rootNode.findPath("places");
            Iterator<JsonNode> places = placesNode.elements();

*/

            in.close();
            con.disconnect();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public List<Ort> locSuche(String eingabe) throws Exception{
        String outputFormat = "RapidJSON";
        String type_sf = "any";
        String coordOutputFormat = "WGS84%5BDD.DDDDD%5D";
        String name_sf = eingabe;
        return stopFinderRequest(outputFormat, type_sf, coordOutputFormat, name_sf);
    }

    private List<Ort> hstSuche(double lat, double lon) throws Exception{
        String outputFormat = "RapidJSON";
        String type_sf = "coord";
        String coordOutputFormat = "WGS84";
        String name_sf = String.valueOf(lon) + ":" + String.valueOf(lat) + ":" + "WGS84";
        return stopFinderRequest(outputFormat, type_sf, coordOutputFormat, name_sf);
    }

    private List<Ort> stopFinderRequest( String outputFormat, String type_sf, String coordOutputFormat, String name_sf) throws Exception{
        String urlString = "http://smartmmi.demo.mentz.net/smartmmi/XML_STOPFINDER_REQUEST?outputFormat=" + outputFormat
                + "&type_sf=" + type_sf + "&coordOutputFormat=" + coordOutputFormat + "&name_sf=" + name_sf.trim().replaceAll(" ", "%20").toLowerCase().replaceAll("ß", "%C3%9F").replaceAll("ü", "%C3%BC").replaceAll("ö", "%C3%B6").replaceAll("ä", "%C3%A4");
        //String urlString = "https://ptsv2.com/t/0m0d3-1557665829/post?outputFormat=" + outputFormat
        //        + "&type_sf=" + type_sf + "&coordOutputFormat=" + coordOutputFormat + "&name_sf=" + name_sf.trim().replaceAll(" ", "%20").replaceAll("ß", "%C3%9F");

        /*String decodedURL = URLDecoder.decode(urlString, "UTF-8");
        System.out.println(urlString);
        System.out.println(decodedURL);
        URL url = new URL(decodedURL);
        System.out.println(url);
        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery().toLowerCase().replaceAll("ß", "%C3%9F").replaceAll("ü", "%C3%BC").replaceAll("ö", "%C3%B6").replaceAll("ä", "%C3%A4"), url.getRef());
        System.out.println(uri);*/
        URL myURL = new URL(urlString);
        System.out.println(myURL);
        //URL myURL = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(reader);
        JsonNode locationsNode = rootNode.findPath("locations");
        Iterator<JsonNode> locationsIterator = locationsNode.elements();

        List<Ort> orte = new ArrayList<>();
        while(locationsIterator.hasNext()) {
            JsonNode location = locationsIterator.next();
            String x = location.path("type").asText();
            if(location.path("type").asText().contains("stop")) {
                String name = location.path("name").asText("");
                String adresse = location.path("name").asText("");
                Coordinate position;
                position = new Coordinate(location.path("coord").path(0).asDouble(), location.path("coord").path(1).asDouble());
                String KVVID = location.path("id").asText("");
                double[] KVVPos = new double[] {0.0, 0.0};
                List<Integer> productClasses = new ArrayList<>();
                Haltestelle hst = new Haltestelle(name, adresse, position, KVVID, KVVPos, productClasses);
                orte.add(hst);
            } else if(location.path("type").asText().contains("suburb")){
                String name = location.path("name").asText("");
                String adresse = location.path("name").asText("");
                Coordinate position;
                position = new Coordinate(location.path("coord").path(0).asDouble(), location.path("coord").path(1).asDouble());
                Ort o = new Ort(name, adresse, position);
                orte.add(o);
            } else if(location.path("type").asText().contains("singlehouse")){
                String name = location.path("name").asText("");
                String adresse = location.path("name").asText("");
                Coordinate position;
                position = new Coordinate(location.path("coord").path(0).asDouble(), location.path("coord").path(1).asDouble());
                Ort o = new Ort(name, adresse, position);
                orte.add(o);
            }
        }
        return orte;
    }

    public List<List<Verbindungsabschnitt>> tripRequest(Coordinate start, Coordinate ziel, Date d, String arrdep) throws Exception{
        double LAT_S = start.getLatitude();
        double LAT_Z = ziel.getLatitude();
        double LON_S = start.getLongitude();
        double LON_Z = ziel.getLongitude();
        String urlString = "http://smartmmi.demo.mentz.net/smartmmi/XML_TRIP_REQUEST2?outputFormat=rapidJSON&type_origin=coord&name_origin=" + LON_S + ":" + LAT_S + ":WGS84&type_destination=coord&name_destination=" + LON_Z + ":" + LAT_Z + ":WGS84" + "&coordOutputFormat=WGS84[DD.DDDDD]&calcNumberOfTrips=2&calcOneDirection=1&itdDate=" + dateToDatum(d) + "&itdTime=" + dateToZeit(d) + "&itdTripDateTimeDepArr=" + arrdep;
        System.out.println(urlString);
        URL myURL = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        return tripRequestLesen(reader, d, arrdep);
    }

    public void tripRequestAnkunft(Coordinate start, Coordinate ziel, Date d) throws Exception{
        double LAT_S = start.getLatitude();
        double LAT_Z = ziel.getLatitude();
        double LON_S = start.getLongitude();
        double LON_Z = ziel.getLongitude();
        String urlString = "http://smartmmi.demo.mentz.net/smartmmi/XML_TRIP_REQUEST2?outputFormat=rapidJSON&type_origin=coord&name_origin=" + LON_S + ":" + LAT_S + ":WGS84&type_destination=coord&name_destination=" + LON_Z + ":" + LAT_Z + ":WGS84" + "&coordOutputFormat=WGS84[DD.DDDDD]&calcNumberOfTrips=2&calcOneDirection=1&itdDate=" + dateToDatum(d) + "&itdTime=" + dateToZeit(d) + "&itdTripDateTimeDepArr=" + "arr";
        System.out.println(urlString);
        URL myURL = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        List<List<Verbindungsabschnitt>> l = tripRequestLesen(reader, d, "arr");
        for (List<Verbindungsabschnitt> li : l) {
            System.out.println(li.get(li.size() - 1).getTeilverb().getAnkunftsZeit());
        }
    }

    public List<List<Verbindungsabschnitt>> tripRequestLesen(BufferedReader in, Date d, String arrdep) {

        List<List<Verbindungsabschnitt>> verbindungen = new ArrayList<>();
                    List<List<Verbindungsabschnitt>> passendeVerbindungen = new ArrayList<>();
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(in);
                        JsonNode journeysNode = rootNode.findPath("journeys");
                        Iterator<JsonNode> journeyIterator = journeysNode.elements();

                        while (journeyIterator.hasNext()) {
                            List<Verbindungsabschnitt> verbindungsanschnitte = new ArrayList<>();
                            GesamtVerbindung verbindung;

                            JsonNode journey = journeyIterator.next();

                            JsonNode legsNode = journey.findPath("legs");
                            Iterator<JsonNode> legsIterator = legsNode.elements();

                            while(legsIterator.hasNext()){
                                int duration = 0;
                                Ort teilStart = null;
                                Ort teilZiel = null;
                                Date abfahrtsZeit;
                                Date ankuftsZeit;
                                Verkehrsmittel verkehrsmittel;
                                Verbindungsabschnitt verb;

                    JsonNode leg = legsIterator.next();
                    JsonNode destination = leg.path("destination");
                    JsonNode orig = leg.path("origin");

                    duration = leg.findPath("duration").asInt();
                    teilStart = new Ort(orig.path("name").asText(), "", new Coordinate(orig.path("coord").path(0).asDouble(),orig.path("coord").path(1).asDouble()));
                    teilZiel = new Ort(destination.path("name").asText(), "", new Coordinate(destination.path("coord").path(0).asDouble(),destination.path("coord").path(1).asDouble()));
                    abfahrtsZeit = uhrzeitParsenTCAntwort(orig.path("departureTimeEstimated").asText());
                    ankuftsZeit = uhrzeitParsenTCAntwort(destination.path("arrivalTimeEstimated").asText());
                    verkehrsmittel = vmLesen(leg);
                    List<Coordinate> coords = coordLesen(leg.path("coords"));

                    verb = new Verbindungsabschnitt(new TeilVerbindung(teilStart, teilZiel, duration, verkehrsmittel, abfahrtsZeit, ankuftsZeit, coords));
                    verbindungsanschnitte.add(verb);
                }
                //verbindung = new GesamtVerbindung(verbindungsanschnitte.get(0).getTeilverb().getStartPunkt(), verbindungsanschnitte.get(verbindungsanschnitte.size() - 1).getTeilverb().getZielPunkt(), verbindungsanschnitte);
                verbindungen.add(verbindungsanschnitte);
            }

            in.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        for (List<Verbindungsabschnitt> li : verbindungen) {
            if(arrdep == "arr") {
                if(d.after(li.get(li.size() - 1).getTeilverb().getAnkunftsZeit())) {
                    passendeVerbindungen.add(li);
                }
            } else if(arrdep == "dep") {
                if(d.before(li.get(0).getTeilverb().getAbfahrtsZeit())) {
                    passendeVerbindungen.add(li);
                }
            } else {
                passendeVerbindungen.add(li);
            }

        }


        return passendeVerbindungen;


    }

    public Date uhrzeitParsenTCAntwort(String eingabe) {
        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formater.setTimeZone(TimeZone.getTimeZone("UCT"));
        Date date = null;
        try {
            date = formater.parse(eingabe);
        } catch (ParseException pe) {
            System.out.println(pe);
        }
        return date;
    }

    public String dateToZeit (Date d) {
        DateFormat formater = new SimpleDateFormat("HHmm");
        String da = formater.format(d);
        return da;
    }
    public String dateToDatum (Date d) {
        DateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String da = formater.format(d);
        return da;
    }

    public List<Ort> alleHSTKVV () throws Exception{
        List<Ort> alleHST = new ArrayList<>();
        File hstfile = new File("exampleResponse/AlleHaltestellen.json");
        BufferedReader in = new BufferedReader(new FileReader(hstfile));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(in);
        JsonNode locationsNode = rootNode.findPath("locations");
        Iterator<JsonNode> locationsIterator = locationsNode.elements();

        while(locationsIterator.hasNext()) {
            Haltestelle hst;

            String name = null;
            String adresse = null;
            Coordinate position;
            String KVVid = null;
            double[] KVVposition = {0.0, 0.0};
            List<Integer> productCalsses = null;

            JsonNode hstNode = locationsIterator.next();

            name = hstNode.path("parent").path("name").asText() + ", " + hstNode.path("name").asText();
            position = new Coordinate(hstNode.path("coord").path(0).asDouble(), hstNode.path("coord").path(1).asDouble());
            KVVid = hstNode.path("id").asText();

            hst = new Haltestelle(name, adresse, position, KVVid, KVVposition, productCalsses);
            alleHST.add(hst);
        }
        System.out.println(alleHST.get(alleHST.size()-1).getName());

        return alleHST;
    }

    private Verkehrsmittel vmLesen(JsonNode node) {
        String t = node.path("transportation").path("product").path("name").asText();
        if(t.contains("footpath")) {
            return Verkehrsmittel.Verkehrsmittel(99);
        }
        try {
            int id = node.path("transportation").path("product").path("id").asInt();
            return Verkehrsmittel.Verkehrsmittel(id);
        } catch (Exception e) { }
            Verkehrsmittel vm = Verkehrsmittel.Verkehrsmittel(999);
            vm.setTitle(t);
            return vm;
    }

    private List<Coordinate> coordLesen(JsonNode coordNode) {
        List<Coordinate> coordList = new ArrayList<>();

        Iterator<JsonNode> coordIterator = coordNode.elements();

        while(coordIterator.hasNext()) {
            JsonNode coord = coordIterator.next();
            Coordinate d = new Coordinate(coord.path(0).asDouble(), coord.path(1).asDouble());
            coordList.add(d);
        }

        return coordList;
    }
}
