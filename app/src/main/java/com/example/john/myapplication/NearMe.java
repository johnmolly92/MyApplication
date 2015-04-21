package com.example.john.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class NearMe extends Activity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);


    }

    @Override
    protected void onResume() {
        super.onResume();

        setMap();
    }

    public void setMap(){

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();



        if (map!=null){
            System.out.println("MAP NOT NULL");
            map.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if(location != null) {
                // Getting latitude of the current location
                double latitude = location.getLatitude();

                // Getting longitude of the current location
                double longitude = location.getLongitude();

                // Creating a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);

                // myPosition = new LatLng(latitude, longitude);
                       /* Marker event = map.addMarker(new MarkerOptions()
                                        .position(eventLatLng)
                                        .title(venue)
                                        .snippet(name)
                        );*/
                      /*  Marker kiel = map.addMarker(new MarkerOptions()
                                .position(KIEL)
                                .title("Kiel")
                                .snippet("Kiel is cool")
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.ic_launcher)));*/
                // Move the camera instantly to hamburg with a zoom of 15.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
            else{
                LatLng latLng = new LatLng(53.385572,-6.258543);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
            // Zoom in, animating the camera.
            //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }

    public void openHomeActivity(View v)
    {
        Intent i = new Intent(NearMe.this, Home.class);
        startActivity(i);
    }

    public void openSearchActivity(View v)
    {
        Intent i = new Intent(NearMe.this, Search.class);
        startActivity(i);
    }

    public void openWelcomeActivity(View v)
    {
        Intent i = new Intent(NearMe.this, Welcome.class);
        startActivity(i);
    }

    public void openNearMeActivity(View v)
    {
        Intent i = new Intent(NearMe.this, NearMe.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
