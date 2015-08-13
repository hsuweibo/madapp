package com.mad.openisdm.madnew.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mad.openisdm.madnew.R;
import com.mad.openisdm.madnew.app.MapFragment;
import com.mad.openisdm.madnew.app.OnLocationChangedListener;
import com.mad.openisdm.madnew.app.Shelter;
import com.mad.openisdm.madnew.app.ShelterManager;
import com.mad.openisdm.madnew.app.ShelterSourceSelector;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class SimpleExampleActivity extends AppCompatActivity implements OnLocationChangedListener, Shelter.OnShelterReceiveListener {

    MapFragment mapFragment;
    Button taipeiButton, hsinchuButton;
    ShelterManager shelterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_example);
        taipeiButton = (Button)findViewById(R.id.taipei_button);
        hsinchuButton = (Button)findViewById(R.id.hsinchu_button);

        taipeiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShelterSourceSelector(SimpleExampleActivity.this).selectShelterSource(ShelterSourceSelector.ShelterID.SHOW_TAIPEI).fetchFromSource();
            }
        });


        hsinchuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShelterSourceSelector(SimpleExampleActivity.this).selectShelterSource(ShelterSourceSelector.ShelterID.SHOW_HSINCHU).fetchFromSource();
            }
        });



        FragmentManager fm= this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mapFragment = new MapFragment();
        fragmentTransaction.add(R.id.test_frag_container, mapFragment).commit();
        shelterManager = new ShelterManager(this, this);


    }

    @Override
    public void onStart(){
        super.onStart();
        new ShelterSourceSelector(this).selectShelterSource(ShelterSourceSelector.ShelterID.SHOW_HSINCHU).fetchFromSource();
        shelterManager.connect();
    }

    @Override
    public void onStop(){
        super.onStop();
        new ShelterSourceSelector(this).selectShelterSource(ShelterSourceSelector.ShelterID.SHOW_HSINCHU).fetchFromSource();
        shelterManager.disconnect();
    }

    @Override
    public void onShelterReceive(ArrayList<Shelter> shelters){
        mapFragment.updateUIWithShelters(shelters);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    @Override
    public void onLocationChanged(GeoPoint userLocation) {
        double lat = userLocation.getLatitude();
        double  longitude = userLocation.getLongitude();
        Toast.makeText(this, "Location changed!  lat:" + lat + "long:" + longitude, Toast.LENGTH_SHORT).show();
    }
}
