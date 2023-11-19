package com.demo.collegeerp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationHelper {
    public static String getAddressFromLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String fullAddress = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                // Example: Constructing full address from individual components
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(address.getAddressLine(i)).append(", ");
                }

                stringBuilder.append(address.getLocality()).append(", ");
                stringBuilder.append(address.getPostalCode()).append(", ");
                stringBuilder.append(address.getCountryName());

                fullAddress = stringBuilder.toString();
            } else {
                fullAddress = "No address found";
            }
        } catch (IOException e) {
            e.printStackTrace();
            fullAddress = "Error: " + e.getMessage();
        }

        return fullAddress;
    }
}
