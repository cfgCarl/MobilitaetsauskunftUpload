package com.dabbility.auskunft.COMs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;

import com.dabbility.auskunft.model.BikeStation;
import com.dabbility.auskunft.model.Einstellungen;
import com.dabbility.auskunft.model.Ort;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sothawo.mapjfx.Coordinate;

//Klasse zur Kommunikation mit nextbike

public class nextBikeCOM {
    URL nurl;

    public List<Ort> init(String entsch) {
        List<Ort> stationen = new ArrayList<>();
        try {
            nurl = new URL("https://api.nextbike.net/maps/nextbike-" + entsch + ".json?city=21");
            HttpURLConnection con = (HttpURLConnection) nurl.openConnection();
            con.setRequestMethod("GET");

            Map<String, String> parameters = new HashMap<>();

            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");

            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.flush();
            out.close();

            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));


            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(in);
            JsonNode placesNode = rootNode.findPath("places");
            Iterator<JsonNode> places = placesNode.elements();

            while(places.hasNext()){
                String name = null;
                String adresse = null;
                Coordinate position;
                int id = 0;
                int anzahlBikes = 0;

                JsonNode place = places.next();

                name = place.findPath("name").asText();
                adresse = name;
                position = new Coordinate(place.findPath("lat").asDouble(), place.findPath("lng").asDouble());
                id = place.findPath("uid").asInt();
                anzahlBikes = place.findPath("bikes").asInt();

                BikeStation station = new BikeStation(id, anzahlBikes, position, name, adresse);
                stationen.add(station);

            }

            in.close();
            con.disconnect();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return stationen;
    }
    public List<Ort> nextBikeStationen() {
        List<Ort> erg = init("official");
        if(Einstellungen.getInstance().getEigenesFahrradAktiv()) {
            Ort eigenesFahrrad = Einstellungen.getInstance().getEigenesFahrrad();
            erg.add(eigenesFahrrad);
        }
        return erg;
    }
    public List<Ort> alleNextBike() {
        return init("live");
    }
    }

