package com.example.mg.tryappkillan.logic;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mg on 11/09/16.
 */
public class Localizations {

    private int idLocalization;
    private double longitude;
    private double latitude;
    private double altitude;
    private String date;
    private String time;

    public void addLocalization(Location location)
    {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        altitude=location.getAltitude();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        date = sdf.format(new Date());
        time= String.valueOf(location.getTime());


    }
}
