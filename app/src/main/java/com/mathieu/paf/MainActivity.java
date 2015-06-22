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
import android.telephony.gsm.GsmCellLocation;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import java.lang.Math;


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
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                //on modifie ici ce qui concerne la cellule principale ..?

                 asu =(TextView) findViewById(R.id.ASU);
                 asu.setText(Integer.toString(signalStrength.getGsmSignalStrength()) +"/" + Integer.toString(2 * signalStrength.getGsmSignalStrength() - 113) +"dBm");

                lac =(TextView) findViewById(R.id.LAC);
                GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                String cid , l;
                if (cellLocation.getCid()!=65535){
                    cid = Integer.toString(cellLocation.getCid());
                }
                else cid = "N/A";
                if(cellLocation.getLac()==0){l ="N/A";}
                else l = Integer.toString(cellLocation.getLac());
                lac.setText(l+"/" + cid);

                //Modifier le SIR

                TextView snr = (TextView) findViewById(R.id.SNR);

                if(rssi5.getText().toString().equals("--")|rssi5.getText().toString().equals("N/A")){
                    snr.setText("N/A");
                }
                else if (rssi6.getText().toString().equals("--")|rssi6.getText().toString().equals("N/A")){
                    //avec la somme de 1 à 5
                    int a= Integer.parseInt(rssi1.getText().toString()), b= Integer.parseInt(rssi2.getText().toString()), c= Integer.parseInt(rssi3.getText().toString()), d= Integer.parseInt(rssi4.getText().toString()), e= Integer.parseInt(rssi5.getText().toString());
                    double sir = (10^(30*4))*(10^signalStrength.getGsmSignalStrength()/(10^a+10^b+10^c+10^d+10^e));
                    sir = 10*Math.log(sir);
                    snr.setText(Integer.toString(sir));
                    //Ici on charge la puissance de signal pour afficher les marqueurs
                    // Carte.puissanceSignal = -201; //200 étant le seuil à partir duquel on considère qu'on est au niveau 1
                }
                else
                {
                    //la somme de 1 à 6
                    int a= Integer.parseInt(rssi1.getText().toString()), b= Integer.parseInt(rssi2.getText().toString()), c= Integer.parseInt(rssi3.getText().toString()), d= Integer.parseInt(rssi4.getText().toString()), e= Integer.parseInt(rssi5.getText().toString()),f= Integer.parseInt(rssi5.getText().toString());
                    double sir = (10^(30*5))*(10^signalStrength.getGsmSignalStrength()/(10^a+10^b+10^c+10^d+10^e+10^f));
                    sir = 10*Math.log(sir);
                    int s = (int) sir;
                    snr.setText(Integer.toString(s) + "dB");
                    //Ici on charge la puissance de signal pour afficher les marqueurs
                    //Carte.puissanceSignal = sir;
                }

            }

            // Appelée quand est déclenché l'évènement LISTEN_CELL_LOCATION
            @Override
            public void onCellLocationChanged(CellLocation cellLocation) {

                //on gère ici les cellules voisines

                List<NeighboringCellInfo> neighborCells = telephonyManager.getNeighboringCellInfo();

                if(neighborCells.get(0)!=null){
                    int r1 = 2 * neighborCells.get(0).getRssi() - 113, c1 = neighborCells.get(0).getCid(), l1 = neighborCells.get(0).getLac();
                    rssi1 =(TextView) findViewById(R.id.RSSI1);
                    rssi1.setText(Integer.toString(r1));

                    cid1 =(TextView) findViewById(R.id.CID1);
                    if(c1==65353) cid1.setText("N/A");
                    else cid1.setText(Integer.toString(c1));

                    lac1 =(TextView) findViewById(R.id.LAC1);
                    if(l1 ==0) lac1.setText("N/A");
                    else lac1.setText(Integer.toString(l1));
                }
                if(neighborCells.get(1)!=null){
                    int r2 = 2 * neighborCells.get(1).getRssi() - 113, c2= neighborCells.get(1).getCid(), l2= neighborCells.get(1).getLac();
                    rssi2 =(TextView) findViewById(R.id.RSSI2);
                    rssi2.setText(Integer.toString(r2));

                    cid2 =(TextView) findViewById(R.id.CID2);
                    if(c2==65353) cid2.setText("N/A");
                    else cid1.setText(Integer.toString(c2));

                    lac2 =(TextView) findViewById(R.id.LAC2);
                    if(l2 ==0) lac2.setText("N/A");
                    else lac2.setText(Integer.toString(l2));
                }
                if(neighborCells.get(2)!=null) {
                    int r3 = 2 * neighborCells.get(2).getRssi() - 113, c3 = neighborCells.get(2).getCid(), l3 = neighborCells.get(2).getLac();
                    rssi3 = (TextView) findViewById(R.id.RSSI3);
                    rssi3.setText(Integer.toString(r3));

                    cid3 = (TextView) findViewById(R.id.CID3);
                    if (c3 == 65535) cid3.setText("N/A");
                    else cid3.setText(Integer.toString(c3));

                    lac3 = (TextView) findViewById(R.id.LAC3);
                    if (l3 == 0) lac3.setText("N/A");
                    else lac3.setText(Integer.toString(l3));
                }
                if(neighborCells.get(3)!=null){
                    int r4 = 2 * neighborCells.get(3).getRssi() - 113, c4= neighborCells.get(3).getCid(), l4= neighborCells.get(3).getLac();
                    rssi4 =(TextView) findViewById(R.id.RSSI4);
                    rssi4.setText(Integer.toString(r4));

                    cid4 =(TextView) findViewById(R.id.CID4);
                    if(c4==65535) cid4.setText("N/A");
                    else cid4.setText(Integer.toString(c4));

                    lac4 =(TextView) findViewById(R.id.LAC4);
                    if (l4==0) lac4.setText("N/A");
                    else lac4.setText(Integer.toString(l4));
                }
                if(neighborCells.get(4)!=null){
                    int r5 = 2 * neighborCells.get(4).getRssi() - 113, c5= neighborCells.get(4).getCid(), l5= neighborCells.get(4).getLac();
                    rssi5 =(TextView) findViewById(R.id.RSSI5);
                    rssi5.setText(Integer.toString(r5));

                    cid5 =(TextView) findViewById(R.id.CID5);
                    if(c5==65535) cid5.setText("N/A");
                    else cid5.setText(Integer.toString(c5));

                    lac5 =(TextView) findViewById(R.id.LAC5);
                    if (l5 ==0) lac5.setText("N/A");
                    lac5.setText(Integer.toString(l5));
                }
                if(neighborCells.get(5)!=null){
                    int r6 = 2 * neighborCells.get(5).getRssi() - 113, c6= neighborCells.get(5).getCid(), l6= neighborCells.get(5).getLac();

                    rssi6 =(TextView) findViewById(R.id.RSSI6);
                    rssi6.setText(Integer.toString(r6));

                    cid6 =(TextView) findViewById(R.id.CID6);
                    if(c6==65535) cid6.setText("N/A");
                    else cid6.setText(Integer.toString(c6));

                    lac6 =(TextView) findViewById(R.id.LAC6);
                    if (l6==0) lac6.setText("N/A");
                    else lac6.setText(Integer.toString(l6));
                }

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
