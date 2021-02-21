package com.example.listactivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button listImgButt = findViewById(R.id.buttonImg);
        listImgButt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_list);
                ListView listView = findViewById(R.id.list);
                MyAdapter adap = new MyAdapter();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                LocationManager locationManager =null;
                String fournisseur = null;
                String latitude=null,longitude= null;
                if(locationManager == null) {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteres = new Criteria();

                    // la précision  : (ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision)
                    criteres.setAccuracy(Criteria.ACCURACY_FINE);

                    // l'altitude
                    criteres.setAltitudeRequired(true);

                    // la direction
                    criteres.setBearingRequired(true);

                    // la vitesse
                    criteres.setSpeedRequired(true);

                    // la consommation d'énergie demandée
                    criteres.setCostAllowed(true);
                    criteres.setPowerRequirement(Criteria.POWER_HIGH);

                     fournisseur = locationManager.getBestProvider(criteres, true);

                }
                if(fournisseur != null)
                {
                    Location localisation = locationManager.getLastKnownLocation(fournisseur);
                    LocationListener locationListener = new MyLocationListener();
                    longitude = "" + localisation.getLongitude();
                    latitude = "" + localisation.getLatitude();
                    Log.v("Debug","long ="+longitude+"lat = "+latitude);
                    if(locationListener != null)
                    {
                        locationListener.onLocationChanged(localisation);
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                }

                Log.i("Debug",locationManager.toString());

                URL url = null;
                String s = new String("https://api.flickr.com/services/rest/?" +
                        "method=flickr.photos.search" +
                        "&license=4" +
                        "&api_key=4167810fbc47cc7f1da6d151edf224d5" +
                        "&has_geo=1&lat=" + latitude +
                        "&lon=" + longitude + "&per_page=10&nojsoncallback=1&format=json"); // well formed JSON
                Log.v("Url Debug",s);
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    new AsyncFlickrJSONDataForList(findViewById(R.id.list),adap,url).execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}