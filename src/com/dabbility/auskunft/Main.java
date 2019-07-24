package com.dabbility.auskunft;

import com.dabbility.auskunft.COMs.graphHopper_COM;
import com.dabbility.auskunft.COMs.nextBikeCOM;
import com.dabbility.auskunft.COMs.triasCOM;
import com.dabbility.auskunft.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

//Hauptklasse zum starten

public class Main extends Application{

    static DateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    static Date date = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        primaryStage.setTitle("Mobilitaetsauskunft");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch();
        try {
            date = formater.parse("2019-05-25T15:50:50Z");
        } catch (
                ParseException pe) {
            System.out.println(pe);
        }
        graphHopper_COM.getInstance();
        System.out.println("Hi");
        triasCOM tc = triasCOM.getInstance();
    }


}

