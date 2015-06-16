package com.mathieu.paf;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {


    private TeleListener phone;

    private Button carte = null;
    private TextView roaming, imei, plmn, operateur, callState, voix, asu, lac, rssi1, rssi2, rssi3, rssi4, rssi5, rssi6, cid1, cid2, cid3, cid4, cid5, cid6, lac1, lac2, lac3, lac4, lac5, lac6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carte = (Button) findViewById(R.id.button);
        carte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carteActivite = new Intent(MainActivity.this, Carte.class);
                startActivity(carteActivite);
            }
        });

        roaming =(TextView) findViewById(R.id.Roamming);
        roaming.setText(phone.getRoaming());

        imei =(TextView) findViewById(R.id.IMEI);
        imei.setText(phone.getImei());

        plmn =(TextView) findViewById(R.id.PLMN);
        plmn.setText(phone.getPlmn());

        operateur =(TextView) findViewById(R.id.Operateur);
        operateur.setText(phone.getOperateur());

        callState =(TextView) findViewById(R.id.CallState);
        callState.setText(phone.getCallState());

        voix =(TextView) findViewById(R.id.voix);
        voix.setText(phone.getNetworkType());

        asu =(TextView) findViewById(R.id.ASU);
        asu.setText(phone.getSignalStrength() + "dBm");

        lac =(TextView) findViewById(R.id.LAC);
        //il manque un getter pour le LAC!

        rssi1 =(TextView) findViewById(R.id.RSSI1);
        rssi1.setText(phone.getNeighborCells()[1][1] + "dBm");

        rssi2 =(TextView) findViewById(R.id.RSSI2);
        rssi2.setText(phone.getNeighborCells()[2][1] + "dBm");

        rssi3 =(TextView) findViewById(R.id.RSSI3);
        rssi3.setText(phone.getNeighborCells()[3][1] + "dBm");

        //il peut y avoir moins de neighbor cells! pour éviter d'avoir une erreur, on fait le test avec juste les premiers

        //rssi4 =(TextView) findViewById(R.id.RSSI4);
        //rssi4.setText(phone.getNeighborCells()[4][1] + "dBm");

        //rssi5 =(TextView) findViewById(R.id.RSSI5);
        //rssi5.setText(phone.getNeighborCells()[5][1] + "dBm");

        //rssi6 =(TextView) findViewById(R.id.RSSI6);
        //rssi6.setText(phone.getNeighborCells()[6][1] + "dBm");

        cid1 =(TextView) findViewById(R.id.CID1);
        cid1.setText(phone.getNeighborCells()[1][2] + "dBm");

        cid2 =(TextView) findViewById(R.id.CID2);
        cid2.setText(phone.getNeighborCells()[2][2] + "dBm");

        cid3 =(TextView) findViewById(R.id.CID3);
        cid3.setText(phone.getNeighborCells()[3][2] + "dBm");

        lac1 =(TextView) findViewById(R.id.LAC1);
        lac1.setText(phone.getNeighborCells()[1][3] + "dBm");

        lac2 =(TextView) findViewById(R.id.LAC2);
        lac2.setText(phone.getNeighborCells()[2][3] + "dBm");

        lac3 =(TextView) findViewById(R.id.LAC3);
        lac3.setText(phone.getNeighborCells()[3][3] + "dBm");
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
