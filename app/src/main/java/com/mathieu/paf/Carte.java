package com.mathieu.paf;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Carte extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button positionButton = null;
    private Button autoButton = null;
    private Location position = null;
    private boolean toggle = false;
    private double lat;
    private double oldLat=0;
    private double lon;
    private double oldLon=0;
    private android.os.Handler customHandler;
    private Runnable myRunnable;
    private Button save = null;
    private Button load = null;
    private ArrayList<PointGPS> listePoints = new ArrayList<PointGPS>(); //initialisation de la liste de points GPS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        setUpMapIfNeeded();

        positionButton = (Button) findViewById(R.id.button);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = mMap.getMyLocation();
                if (position != null) {
                    lat = position.getLatitude();
                    lon = position.getLongitude();
                    Toast.makeText(MainActivity.getContext(), "Position actuelle : " + lat + ", " + lon, Toast.LENGTH_SHORT).show();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 16));
                }
            }
        });

        autoButton = (Button) findViewById(R.id.auto);
        autoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    autoButton.setText("Mode auto : OFF");
                    toggle = false;
                }
                else{
                    autoButton.setText("Mode auto : ON");
                    toggle = true;
                }
            }
        });

        customHandler = new android.os.Handler();
        customHandler.postDelayed(myRunnable, 0);

        myRunnable = new Runnable() {
            @Override
            public void run() {
                // Code à éxécuter de façon périodique

                if (toggle) {
                    position = mMap.getMyLocation();
                    if (position != null) { //Permet d'éviter le crash de l'appli
                        lat = position.getLatitude();
                        lon = position.getLongitude();
                        Toast.makeText(MainActivity.getContext(), "Relevé de position : " + lat + ", " + lon, Toast.LENGTH_SHORT).show();

                        //Placement des marqueurs
                        if ((Math.abs(lat - oldLat) > Math.pow(10, -4)) || (Math.abs(lon - oldLon) > Math.pow(10, -4))) {
                            //Si la variation de position est suffisante : 10^(-4) ici soit environ 10 metres
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)));
                            listePoints.add(new PointGPS(lat, lon, 1)); //1 sera à remplacer par la valeur courante du RSSI
                        }

                        oldLat = lat;
                        oldLon = lon;
                    }
                }

                customHandler.postDelayed(this, 2000); //Délai entre deux relevés GPS
            }
        };
        myRunnable.run();

       save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Création du dossier et du fichier : on fait une boucle sur listePoints
            }
        });

        load = (Button) findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ouverture d'un explorateur pour sélectionner un fichier
            }
        });

    }

    @Override
    protected void onResume() { //Il faut aussi écrire onPause et onStop pour arrêter la boucle sur la runnable : juste avec le toggle ?
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(48.826523, 2.346354)).title("Télécom ParisTech"));

        mMap.setMyLocationEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.826523, 2.346354), 16));
    }
}
