package com.mathieu.paf;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {


    private PhoneInfo phoneInfo;
    private TeleListener phone; //=phoneInfo.getTelelistener();

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
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Initialisation des listeners pour que les informations soient dynamiques
        TeleListener signals, calls, cellInfo;
        telephonyManager.listen(signals = new TeleListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        telephonyManager.listen(calls = new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);
        telephonyManager.listen(cellInfo = new TeleListener(), PhoneStateListener.LISTEN_CELL_INFO);

        //Inscrire le texte sur Interface Graphique
        imei =(TextView) findViewById(R.id.IMEI);
        imei.setText(telephonyManager.getDeviceId()+ "/" + telephonyManager.getDeviceSoftwareVersion());

        plmn =(TextView) findViewById(R.id.PLMN);
        plmn.setText(telephonyManager.getNetworkOperator());

        operateur =(TextView) findViewById(R.id.Operateur);
        operateur.setText(telephonyManager.getNetworkOperatorName());

        roaming =(TextView) findViewById(R.id.Roamming);
        roaming.setText(Boolean.toString(telephonyManager.isNetworkRoaming()));

        voix =(TextView) findViewById(R.id.voix);
        int type= telephonyManager.getNetworkType();
        cellInfo.setNetworkVoix(type);
        voix.setText(cellInfo.getNetworkVoix());

        callState =(TextView) findViewById(R.id.CallState);
        int state=telephonyManager.getCallState();
        calls.setCallState(state);
        callState.setText(calls.getCallState());

        /*asu =(TextView) findViewById(R.id.ASU);
        asu.setText(phone.getSignalStrength() + "dBm");

        lac =(TextView) findViewById(R.id.LAC);
        //il manque un getter pour le LAC!*/

        List<NeighboringCellInfo> neighborCells = telephonyManager.getNeighboringCellInfo();

        int r1 = 2 * neighborCells.get(0).getRssi() - 113, c1= neighborCells.get(0).getCid(), l1= neighborCells.get(0).getLac();
        int r2 = 2 * neighborCells.get(1).getRssi() - 113, c2= neighborCells.get(1).getCid(), l2= neighborCells.get(1).getLac();
        int r3 = 2 * neighborCells.get(2).getRssi() - 113, c3= neighborCells.get(2).getCid(), l3= neighborCells.get(2).getLac();
        int r4 = 2 * neighborCells.get(3).getRssi() - 113, c4= neighborCells.get(3).getCid(), l4= neighborCells.get(3).getLac();
        int r5 = 2 * neighborCells.get(4).getRssi() - 113, c5= neighborCells.get(4).getCid(), l5= neighborCells.get(4).getLac();
        int r6 = 2 * neighborCells.get(5).getRssi() - 113, c6= neighborCells.get(5).getCid(), l6= neighborCells.get(5).getLac();

        /*for (int k=0; k<6; k++) {
            if(neighborCells.get(k)!=null){
                int rssi = 2 * neighborCells.get(k).getRssi() - 113; // --> dBm
                int cid = neighborCells.get(k).getCid();
                int lac = neighborCells.get(k).getCid();
                rssiValues[k]=Integer.toString(rssi) + "dBm";
                cidValues[k]= Integer.toString(cid);
                lacValues[k]= Integer.toString(lac);
            }
            //faire quelque chose ici pour afficher N/A après
            else {
                rssiValues[k]="--";
                //cidValues[k]= "--";
                //lacValues[k] ="--";
            }
        }*/
        //cellInfo.setNeighborCells(neighborCells);

        rssi1 =(TextView) findViewById(R.id.RSSI1);
        rssi1.setText(Integer.toString(r1));

        rssi2 =(TextView) findViewById(R.id.RSSI2);
        rssi2.setText(Integer.toString(r2));

        rssi3 =(TextView) findViewById(R.id.RSSI3);
        rssi3.setText(Integer.toString(r3));

        rssi4 =(TextView) findViewById(R.id.RSSI4);
        rssi4.setText(Integer.toString(r4));

        rssi5 =(TextView) findViewById(R.id.RSSI5);
        rssi5.setText(Integer.toString(r5));

        rssi6 =(TextView) findViewById(R.id.RSSI6);
        rssi6.setText(Integer.toString(r6));

        cid1 =(TextView) findViewById(R.id.CID1);
        cid1.setText(Integer.toString(c1));

        cid2 =(TextView) findViewById(R.id.CID2);
        cid2.setText(Integer.toString(c2));

        cid3 =(TextView) findViewById(R.id.CID3);
        cid3.setText(Integer.toString(c3));

        cid4 =(TextView) findViewById(R.id.CID4);
        cid4.setText(Integer.toString(c4));

        cid5 =(TextView) findViewById(R.id.CID5);
        cid5.setText(Integer.toString(c5));

        cid6 =(TextView) findViewById(R.id.CID6);
        cid6.setText(Integer.toString(c6));

        lac1 =(TextView) findViewById(R.id.LAC1);
        lac1.setText(Integer.toString(l1));

        lac2 =(TextView) findViewById(R.id.LAC2);
        lac2.setText(Integer.toString(l2));

        lac3 =(TextView) findViewById(R.id.LAC3);
        lac3.setText(Integer.toString(l3));

        lac4 =(TextView) findViewById(R.id.LAC4);
        lac4.setText(Integer.toString(l4));

        lac5 =(TextView) findViewById(R.id.LAC5);
        lac5.setText(Integer.toString(l5));

        lac6 =(TextView) findViewById(R.id.LAC6);
        lac6.setText(Integer.toString(l6));
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
