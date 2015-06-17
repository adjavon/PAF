package com.mathieu.paf;

/**
 * Created by Mathieu on 17/06/2015.
 */
public class PointGPS {

    private double latitude;
    private double longitude;
    private int RSSI;

    PointGPS (double latitude, double longitude, int RSSI) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.RSSI = RSSI;
    }
}
