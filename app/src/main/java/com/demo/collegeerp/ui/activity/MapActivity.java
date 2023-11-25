package com.demo.collegeerp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.ActivityMapBinding;
import com.demo.collegeerp.ui.fragment.RouteFragment;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    ActivityMapBinding binding;
    private GoogleMap mMap;
    String lat_s = "", lan_s = "", lan_l = "", lat_l = "";

    private Marker sourceMarker;
    private Marker destinationMarker;

    double lastLatitude = 0;
    double lastLongitude = 0;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    private static final LatLngBounds INDIA_BOUNDS = new LatLngBounds(
            new LatLng(8.0883, 68.3295), // Southwest bounds of India
            new LatLng(37.0841, 97.3956) // Northeast bounds of India
    );

    private static final int RADIUS_IN_METERS = 20000; // 20 km in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(Double.parseDouble(lat_l), Double.parseDouble(lan_l));
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


        // Set source and destination coordinates (for example)
        LatLng sourceLatLng = new LatLng(Double.parseDouble(lat_l), Double.parseDouble(lan_l)); // Replace with source coordinates
        //LatLng destinationLatLng = new LatLng(lastLatitude, lastLongitude); // Replace with destination coordinates
        LatLng destinationLatLng = new LatLng(28.648354,77.294483); // Replace with destination coordinates


        mMap.addMarker(new MarkerOptions().position(sourceLatLng).title("Bus"));
        mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("My Location"));


        // Create a bounds with an additional 20 km around India's boundaries
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(INDIA_BOUNDS.northeast);
        builder.include(INDIA_BOUNDS.southwest);

        // Extend the bounds by 20 km in each direction
        LatLngBounds indiaWith20KmBounds = builder.build();
        LatLng center = indiaWith20KmBounds.getCenter();

        double radiusDegrees = metersToDegrees(RADIUS_IN_METERS);
        LatLngBounds finalBounds = new LatLngBounds(
                new LatLng(center.latitude - radiusDegrees, center.longitude - radiusDegrees),
                new LatLng(center.latitude + radiusDegrees, center.longitude + radiusDegrees)
        );

        // Move the camera to focus on the extended bounds around India
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(finalBounds, 0));

       //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private double metersToDegrees(int meters) {
        return meters / 111319.9; // Approximate degrees per meter (111,319.9 meters is approximately 1 degree)
    }

    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getBundleData();
    }

    private void getBundleData() {
        Intent intent=getIntent();
        lat_s = intent.getStringExtra("lat_s");
        lan_s = intent.getStringExtra("lan_s");
        lan_l = intent.getStringExtra("lan_l");
        lat_l = intent.getStringExtra("lat_l");

    }

    private void handleLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkMyPermission(this)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            getLastLocation();
        } else {
            permissions(this);
        }
    }

    public static boolean checkMyPermission(Context mContext) {
        int permission1 = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permission1 == 0 && permission2 == 0;
    }

    public static void permissions(Context mContext) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.System.canWrite(mContext)) {
                ((Activity) mContext).requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,

                }, PERMISSION_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                getLastLocation();

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                Tools.showToast(MapActivity.this, "Permission Denied");

            }
        } else {
            Log.e("value", "Permission Denied, You cannot use local drive .     1 " + requestCode);
        }
    }

    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations, this can be null.
                        if (location != null) {
                            lastLatitude = location.getLatitude();
                            lastLongitude = location.getLongitude();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleLocation();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLatitude = location.getLatitude();
        lastLongitude = location.getLongitude();
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }


}