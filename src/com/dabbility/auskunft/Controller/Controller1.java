package com.dabbility.auskunft.Controller;

import com.dabbility.auskunft.COMs.ghCOM;
import com.dabbility.auskunft.model.*;
import com.dabbility.auskunft.COMs.nextBikeCOM;
import com.dabbility.auskunft.COMs.triasCOM;
import com.dabbility.auskunft.model.VerkehrsmittelFolder.nextBike;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

//Hauptcontroller (Stratscreen) mit Abläufen

public class Controller1 {
    @FXML
    TextField tvStart;
    @FXML
    TextField tvZiel;
    @FXML
    TextField tvTime;
    @FXML
    DatePicker dpDatePicker;
    @FXML
    ToggleButton tglBtnAnk;
    @FXML
    ToggleButton tglBtnAbf;
    @FXML
    CheckBox cbxIVOEV;
    @FXML
    CheckBox cbxOEVIV;
    @FXML
    Button btnGO;
    @FXML
    TextArea taAusgabe;
    @FXML
    ImageView ivVM;

    Date date;
    String arrdep;
    Ort start;
    Ort ziel;
    Einstellungen einst = Einstellungen.getInstance();

    @FXML
    public void initialize() {
        ToggleGroup tg = new ToggleGroup();
        tglBtnAnk.setToggleGroup(tg);
        tglBtnAbf.setToggleGroup(tg);
        tglBtnAbf.setSelected(true);
        dpDatePicker.setValue(LocalDate.now());
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        tvTime.setText(df.format(d));
    }

    @FXML
    public void btnGO_Start() throws Exception{
        taAusgabe.setText("Los Gehts!");

        if(tglBtnAbf.isSelected()) {
            arrdep = "dep";
        }
        if(tglBtnAnk.isSelected()) {
            arrdep = "arr";
        }

        date = eingabeParsen(dpDatePicker.getValue(), tvTime.getText());
        taAusgabe.setText(taAusgabe.getText() + "\n" + date.toString());

        //Erkenne Start Eingabe
        List<Ort> startOrte = triasCOM.getInstance().locSuche(tvStart.getText());
        String za = "Gefundene Orte Startort:";
        for(Ort o : startOrte){
            za = za + "\n" + o.getName() + " | " + o.getPosition().getLatitude() + "/" + o.getPosition().getLongitude();
        }
        taAusgabe.setText(taAusgabe.getText() + "\n" + za);
        //start = startOrte.get(0);
        start = ortAuswaehlen(startOrte);
        tvStart.setEditable(false);
        tvStart.setText(start.getName());

        //Erkenne Zieleingabe
        List<Ort> zielOrte = triasCOM.getInstance().locSuche(tvZiel.getText());
        String zza = "Gefundene Orte Zielort:";
        for(Ort o : zielOrte){
            zza = zza + "\n" + o.getName() + " | " + o.getPosition().getLatitude() + "/" + o.getPosition().getLongitude();
        }
        taAusgabe.setText(taAusgabe.getText() + "\n" + zza);
        ziel = ortAuswaehlen(zielOrte);
        tvZiel.setEditable(false);
        tvZiel.setText(ziel.getName());



        List<GesamtVerbindung> verbindungen = new ArrayList<>();
        if(einst.getNextbikeAktiv()) {
            if (einst.getOviv()) {
                verbindungen.addAll(AblaufOEvIV());
            }
            if (einst.getIvov()) {
                verbindungen.addAll(AblaufIVOEv());
            }
        }

        //Direktverbindungen
        GesamtVerbindung dv = direktVerbindung(start, ziel);
        GesamtVerbindung dvBike = direktVerbindungBike(start,ziel);
        GesamtVerbindung dvCar = direktVerbindungCar(start,ziel);

        verbindungen = potenzielleVerbindungenDauerEntscheiden(verbindungen, dv);
        verbindungen.add(dv);
        verbindungen.add(dvBike);
        verbindungen.add(dvCar);

        for(GesamtVerbindung vb : verbindungen) {
            String ortFolge = "";
            Iterator<Verbindungsabschnitt> vbAbschnitte = vb.getVerbindungsAbfolge().iterator();
            ortFolge = ortFolge + vb.getVerbindungsAbfolge().get(0).getTeilverb().getStartPunkt().getName();
            while (vbAbschnitte.hasNext()) {
                Verbindungsabschnitt vba = vbAbschnitte.next();
                ortFolge = ortFolge +  " <--(" + vba.getTeilverb().getVerkehrsmittel() + ")[" + vba.getTeilverb().getDauer() + "sec]-->" + vba.getTeilverb().getZielPunkt().getName();
            }
            taAusgabe.setText(taAusgabe.getText() + "\n" + "Verbindung " + verbindungen.indexOf(vb) + " " + ortFolge + ":");
            taAusgabe.setText(taAusgabe.getText() + "\n" + "Dauer: " + String.valueOf(vb.getDauer() / 60));
        }

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/Verbindungsuebersicht.fxml"));
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(new Scene((Pane) loader.load()));
        Verbindungsuebersicht vue = loader.<Verbindungsuebersicht>getController();
        vue.initData(verbindungen);
        secondaryStage.setTitle("Verbindungen");
        secondaryStage.show();

        tvStart.setEditable(true);
        tvZiel.setEditable(true);
    }


    private List<GesamtVerbindung> AblaufOEvIV() throws Exception{
        List<Ort> haltestellen = triasCOM.getInstance().alleHSTKVV();

        //List<Ort> alleFahrradStationen = new nextBikeCOM().alleNextBike();
        List<Ort> alleFahrradStationen = new nextBikeCOM().nextBikeStationen();

        //Fahrradstationen am Ziel
        List<Ort> fahrraederBeiHST = ghCOM.getInstance().orteBeiAnderenOrten(alleFahrradStationen, haltestellen, 0.2);
        List<Ort> zielFahrradStationen = ghCOM.getInstance().orteInNaeheFinden(200, fahrraederBeiHST, ziel, 5);

        //zuerstOEVVerbindungenHinzufügen
        return zuerstOEV(zielFahrradStationen, start, ziel);

    }

    private List<GesamtVerbindung> AblaufIVOEv() throws Exception{
        List<Ort> haltestellen = triasCOM.getInstance().alleHSTKVV();

        //List<Ort> alleFahrradStationen = new nextBikeCOM().alleNextBike();
        List<Ort> alleFahrradStationen = new nextBikeCOM().nextBikeStationen();

        //Haltestellen beim Start (Fahrrad)
        List<Ort> potFahraeder = ghCOM.getInstance().orteInNaeheFinden(1, alleFahrradStationen, start, 0.5);
        if(potFahraeder.size() == 0) {
            return new ArrayList<GesamtVerbindung>();
        }
        Ort fahrrad = potFahraeder.get(0); // nähestes Bike

        //FußwegZuFahrrad
        //...

        //Welche Haltestellen liegen in erreichbarer Umgebung zum Fahrrad
        List<Ort> naheHaltestellen = ghCOM.getInstance().orteInNaeheFinden(200, haltestellen, fahrrad, 3);

        //zuerstIVVerbindungenHinzufügen
        return zuerstFahrrad(naheHaltestellen, start, ziel);
    }

    private List<GesamtVerbindung> zuerstOEV(List<Ort> fahrradStationen, Ort start, Ort ziel) {
        triasCOM tc = triasCOM.getInstance();
        List<GesamtVerbindung> verbindungen = new ArrayList<>();
        try {
            for (Ort o : fahrradStationen) {
                List<List<Verbindungsabschnitt>> listenListe = tc.tripRequest(start.getPosition(), o.getPosition(), date, arrdep);
                for (List<Verbindungsabschnitt> verbAbschListe : listenListe) {
                    Date leztteTeilAnkunft = verbAbschListe.get(verbAbschListe.size()-1).getTeilverb().getAnkunftsZeit();
                    Date letzteAnkunft = new Date(leztteTeilAnkunft.getTime() + o.getDauer());
                    TeilVerbindung tva = new TeilVerbindung(o, ziel, o.getDauer(), new nextBike(), leztteTeilAnkunft, letzteAnkunft, o.getRoute());
                    tva.setEntfernung(o.getEntfernung());
                    verbAbschListe.add(new Verbindungsabschnitt(tva));
                    GesamtVerbindung gv = new GesamtVerbindung(start, ziel, verbAbschListe);
                    verbindungen.add(gv);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return verbindungen;
    }
    private List<GesamtVerbindung> zuerstFahrrad(List<Ort> haltestellen, Ort start, Ort ziel) {

        triasCOM tc = triasCOM.getInstance();
        List<GesamtVerbindung> verbindungen = new ArrayList<>();
        try {
            for (Ort o : haltestellen) {
                List<List<Verbindungsabschnitt>> listenListe = tc.tripRequest(o.getPosition(), ziel.getPosition(), date, arrdep);
                for (List<Verbindungsabschnitt> verbAbschListe : listenListe) {
                    Date ersteTeilAbfahrt = verbAbschListe.get(0).getTeilverb().getAbfahrtsZeit();
                    Date FahrradAbfahrt = new Date(ersteTeilAbfahrt.getTime() - o.getDauer());
                    List<Verbindungsabschnitt> neueListe = new ArrayList<>();

                    //@TODO: Fußverbindung zum Fahrrad mit einbinden
                    TeilVerbindung tva = new TeilVerbindung(start, o, o.getDauer(), new nextBike(), FahrradAbfahrt, ersteTeilAbfahrt, o.getRoute());
                    tva.setEntfernung(o.getEntfernung());
                    neueListe.add(new Verbindungsabschnitt(tva));
                    neueListe.addAll(verbAbschListe);

                    GesamtVerbindung gv = new GesamtVerbindung(start, ziel, neueListe);
                    verbindungen.add(gv);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return verbindungen;
    }
    private void TripAusgabe(List <GesamtVerbindung> verbindungen) {
        //Ausgabe:
        for (GesamtVerbindung vb:verbindungen) {
            String ortFolge = "";
            Iterator<Verbindungsabschnitt> vbAbschnitte = vb.getVerbindungsAbfolge().iterator();
            ortFolge = ortFolge + vb.getVerbindungsAbfolge().get(0).getTeilverb().getStartPunkt().getName();
            while (vbAbschnitte.hasNext()) {
                Verbindungsabschnitt vba = vbAbschnitte.next();
                ortFolge = ortFolge +  " <--(" + vba.getTeilverb().getVerkehrsmittel() + ")[" + vba.getTeilverb().getDauer()/1000 + "sec]-->" + vba.getTeilverb().getZielPunkt().getName();
            }
            System.out.println("Verbindung " + verbindungen.indexOf(vb) + " " + ortFolge + ":");
            System.out.println("Dauer: " + String.valueOf(vb.getDauer() / 60));
            System.out.println();
        }
    }
    private GesamtVerbindung direktVerbindung(Ort start,Ort ziel){
        triasCOM tc = triasCOM.getInstance();
        GesamtVerbindung direktOEV = null;
        try {
            List<List<Verbindungsabschnitt>> listenListeDIREKT = tc.tripRequest(start.getPosition(), ziel.getPosition(), date, arrdep);
            direktOEV = new GesamtVerbindung(start, ziel, listenListeDIREKT.get(0));
            String ortFolge = "";
            Iterator<Verbindungsabschnitt> vbAbschnitte = direktOEV.getVerbindungsAbfolge().iterator();
            ortFolge = ortFolge + direktOEV.getVerbindungsAbfolge().get(0).getTeilverb().getStartPunkt().getName();
            while (vbAbschnitte.hasNext()) {
                Verbindungsabschnitt vba = vbAbschnitte.next();
                ortFolge = ortFolge +  " - " + vba.getTeilverb().getZielPunkt().getName();
            }
            System.out.println("Direktverbindung " + ortFolge + ":");
            System.out.println("Dauer: " + String.valueOf(direktOEV.getDauer() / (1000*60)));
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        direktOEV.setDirektverbindung(true);
        direktOEV.setName("Direkte Bahnverbindung");
        return direktOEV;
    }

    private GesamtVerbindung direktVerbindungBike(Ort start,Ort ziel){

        GesamtVerbindung direktBike;
        TeilVerbindung tv = ghCOM.getInstance().bikeRouting(start, ziel);
        if(arrdep == "arr") {
            tv.setAnkunftsZeit(date);
            tv.setAbfahrtsZeit(new Date(date.getTime()-tv.getDauer()));
        } else {
            tv.setAbfahrtsZeit(date);
            tv.setAnkunftsZeit(new Date(date.getTime() + tv.getDauer()));
        }
        List<Verbindungsabschnitt> vbas = new ArrayList<Verbindungsabschnitt>();
        vbas.add(new Verbindungsabschnitt(tv));
        direktBike = new GesamtVerbindung(start, ziel, vbas);
        direktBike.setDirektverbindung(true);
        direktBike.setName("Direkte Fahrradverbindung");
        return direktBike;
    }

    private GesamtVerbindung direktVerbindungCar(Ort start,Ort ziel){

        GesamtVerbindung direktCar;
        TeilVerbindung tv = ghCOM.getInstance().carRouting(start, ziel);
        if(arrdep == "arr") {
            tv.setAnkunftsZeit(date);
            tv.setAbfahrtsZeit(new Date(date.getTime()-tv.getDauer()));
        } else {
            tv.setAbfahrtsZeit(date);
            tv.setAnkunftsZeit(new Date(date.getTime() + tv.getDauer()));
        }
        List<Verbindungsabschnitt> vbas = new ArrayList<Verbindungsabschnitt>();
        vbas.add(new Verbindungsabschnitt(tv));
        direktCar = new GesamtVerbindung(start, ziel, vbas);
        direktCar.setDirektverbindung(true);
        direktCar.setName("Direkte Autoverbindung");
        return direktCar;
    }

    private List<GesamtVerbindung> potenzielleVerbindungenDauerEntscheiden(List<GesamtVerbindung> oevivVerbindungen, GesamtVerbindung oevVerbindung) {
        List<GesamtVerbindung> potVerbindungenDauer = new ArrayList<>();
        for(GesamtVerbindung gv:oevivVerbindungen){
            if(gv.getDauer() < oevVerbindung.getDauer()){
                potVerbindungenDauer.add(gv);
            }
        }
        return potVerbindungenDauer;
    }

    private Date eingabeParsen(LocalDate ld, String zeitEingabe) {
        int Stunde = Integer.valueOf(zeitEingabe.substring(0,2));
        int Minute = Integer.valueOf(zeitEingabe.substring(3,5));
        Calendar c =  Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(),Stunde, Minute,0);
        Date d = c.getTime();
        return d;
    }

    private Ort ortAuswaehlen(List<Ort> orte) {
        Ort gewaehlt;
        Ortauswahl oAus;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/Ortauswahl.fxml"));
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(new Scene((SplitPane) loader.load()));
            oAus = loader.getController();
            oAus.initData(orte);
            secondaryStage.setTitle("Verbindungen");
            secondaryStage.showAndWait();
        } catch (Exception e) {
            oAus = null;
            System.out.println(e);
        }
        gewaehlt = oAus.gewaehlt;
        return gewaehlt;
    }

    @FXML
    protected void openSettings() throws Exception{

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/com/dabbility/auskunft/FXML/settings.fxml"));
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(new Scene((Pane) loader.load()));
        secondaryStage.setTitle("Einstellungen");
        secondaryStage.show();
    }
}
