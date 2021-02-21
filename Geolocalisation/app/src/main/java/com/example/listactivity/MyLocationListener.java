package com.example.listactivity;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyLocationListener implements LocationListener {
    public String longitude, latitude;

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onLocationChanged(@NonNull Location location) {

        String longitude = "" + location.getLongitude();
        Log.v("TAG", longitude);
        String latitude = "" + location.getLatitude();
        Log.v("TAG", latitude);
        String coordonnees = String.format("Latitude : %f - Longitude : %f\n", location.getLatitude(), location.getLongitude());
        Log.d("GPS", "coordonnees : " + coordonnees);


    }



}
