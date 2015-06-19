package com.mathieu.paf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private TeleListener phone;

    private Button carte = null;
    private TextView roaming, imei, plmn, operateur, callState, voix, asu, lac, rssi1, rssi2, rssi3, rssi4, rssi5, rssi6, cid1, cid2, cid3, cid4, cid5, cid6, lac1, lac2, lac3, lac4, lac5, lac6;

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //la carte
        carte = (Button) findViewById(R.id.button);
        carte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carteActivite = new Intent(MainActivity.this, Carte.class);
                startActivity(carteActivite);
            }
        });

        //avoir accès aux informations concernant la téléphonie
        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Inscrire le texte sur Interface Graphique
        imei = (TextView) findViewById(R.id.IMEI);
        imei.setText(telephonyManager.getDeviceId() + "/" + telephonyManager.getDeviceSoftwareVersion());

        /*asu =(TextView) findViewById(R.id.ASU);
        asu.setText(phone.getSignalStrength() + "dBm");

        lac =(TextView) findViewById(R.id.LAC);
        //il manque un getter pour le LAC!*/

        //Initialisation des listeners pour que les informations soient dynamiques
        TeleListener stateListener = new TeleListener();
        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            // Appelée quand est déclenché l'évènement LISTEN_CALL_STATE
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        // CALL_STATE_IDLE;
                        callState = (TextView) findViewById(R.id.CallState);
                        callState.setText("Idle");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        // CALL_STATE_OFFHOOK;
                        callState = (TextView) findViewById(R.id.CallState);
                        callState.setText("Offhook");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        // CALL_STATE_RINGING
                        callState = (TextView) findViewById(R.id.CallState);
                        callState.setText("Ringing");
                        break;
                    default:
                        break;
                }

            }

            // Appelée quand est déclenché l'évènement LISTEN_SIGNAL_STRENGTHS
            //@Override //fait chier
            public void onSignalStrengthChanged(SignalStrength signalStrength) {
                //modifier le TextView des la RSSI ici
            }

            // Appelée quand est déclenché l'évènement LISTEN_CELL_LOCATION
            @Override
            public void onCellLocationChanged(CellLocation cellLocation) {
                //caster le CellLocation en GsmCellLocation
                //Modifier les TextView en utilisant les getLac(), getCid(), et getRssi() de GSM cellLocation
                //attention à mettre un if pour toutes les conditions (si CID= 65535, RSSI, ou LAC=0 unknown)
                //gérer le NullPointerException qui arrive là pour l'instant...
                List<NeighboringCellInfo> neighborCells = telephonyManager.getNeighboringCellInfo();

                int r1 = 2 * neighborCells.get(0).getRssi() - 113, c1= neighborCells.get(0).getCid(), l1= neighborCells.get(0).getLac();
                int r2 = 2 * neighborCells.get(1).getRssi() - 113, c2= neighborCells.get(1).getCid(), l2= neighborCells.get(1).getLac();
                int r3 = 2 * neighborCells.get(2).getRssi() - 113, c3= neighborCells.get(2).getCid(), l3= neighborCells.get(2).getLac();
                int r4 = 2 * neighborCells.get(3).getRssi() - 113, c4= neighborCells.get(3).getCid(), l4= neighborCells.get(3).getLac();
                /*int r5 = 2 * neighborCells.get(4).getRssi() - 113, c5= neighborCells.get(4).getCid(), l5= neighborCells.get(4).getLac();
                int r6 = 2 * neighborCells.get(5).getRssi() - 113, c6= neighborCells.get(5).getCid(), l6= neighborCells.get(5).getLac();
                */
                rssi1 =(TextView) findViewById(R.id.RSSI1);
                rssi1.setText(Integer.toString(r1));

                rssi2 =(TextView) findViewById(R.id.RSSI2);
                rssi2.setText(Integer.toString(r2));

                rssi3 =(TextView) findViewById(R.id.RSSI3);
                rssi3.setText(Integer.toString(r3));

                rssi4 =(TextView) findViewById(R.id.RSSI4);
                rssi4.setText(Integer.toString(r4));

                /*rssi5 =(TextView) findViewById(R.id.RSSI5);
                rssi5.setText(Integer.toString(r5));

                rssi6 =(TextView) findViewById(R.id.RSSI6);
                rssi6.setText(Integer.toString(r6));*/

                cid1 =(TextView) findViewById(R.id.CID1);
                cid1.setText(Integer.toString(c1));

                cid2 =(TextView) findViewById(R.id.CID2);
                cid2.setText(Integer.toString(c2));

                cid3 =(TextView) findViewById(R.id.CID3);
                cid3.setText(Integer.toString(c3));

                cid4 =(TextView) findViewById(R.id.CID4);
                cid4.setText(Integer.toString(c4));

                /*cid5 =(TextView) findViewById(R.id.CID5);
                cid5.setText(Integer.toString(c5));

                cid6 =(TextView) findViewById(R.id.CID6);
                cid6.setText(Integer.toString(c6));*/

                lac1 =(TextView) findViewById(R.id.LAC1);
                lac1.setText(Integer.toString(l1));

                lac2 =(TextView) findViewById(R.id.LAC2);
                lac2.setText(Integer.toString(l2));

                lac3 =(TextView) findViewById(R.id.LAC3);
                lac3.setText(Integer.toString(l3));

                lac4 =(TextView) findViewById(R.id.LAC4);
                lac4.setText(Integer.toString(l4));

                /*lac5 =(TextView) findViewById(R.id.LAC5);
                lac5.setText(Integer.toString(l5));

                lac6 =(TextView) findViewById(R.id.LAC6);
                lac6.setText(Integer.toString(l6));*/
            }

            // Appelée quand est déclenché l'évènement LISTEN_SERVICE_STATE
            @Override
            public void onServiceStateChanged(ServiceState serviceState) {
                //changer ici le Roaming en utilisant getRoaming de ServiceState
                roaming = (TextView) findViewById(R.id.Roamming);
                roaming.setText(Boolean.toString(serviceState.getRoaming()));

                //changer ici l'opérateur en utilisant getOperatorAlphaLong() de ServiceState
                operateur = (TextView) findViewById(R.id.Operateur);
                operateur.setText(serviceState.getOperatorAlphaLong());

                //Changer ici le PLMN en utilisant getOperatorNumeric() de ServiceState
                plmn = (TextView) findViewById(R.id.PLMN);
                plmn.setText(serviceState.getOperatorNumeric());

            }

            // Appelée quand est déclenché l'évènement LISTEN_SERVICE_STATE
            @Override
            public void onDataConnectionStateChanged(int state, int networkType) {
                //changer ici le networkType en utilisant le case sur netWorkType et getNetworkType() de telephonyManager ??
                voix = (TextView) findViewById(R.id.voix);
                switch (networkType) {
                    //On peut rajouter d'autres types de réseau dans le case
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        voix.setText("Edge");
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        voix.setText("LTE");
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        voix.setText("UMTS");
                        break;
                    default:
                        voix.setText("N/A");
                        break;
                }

            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_LOCATION | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE | PhoneStateListener.LISTEN_SERVICE_STATE | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    public void updateNeighbors(TelephonyManager telephonyManager){
        //what?
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
