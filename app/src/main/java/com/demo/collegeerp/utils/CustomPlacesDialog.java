package com.demo.collegeerp.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.demo.collegeerp.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class CustomPlacesDialog extends Dialog {
    TextView tv_message;
    private final Context mContext;
    private final String message;

    private final FragmentManager mFragmentManager;

    public CustomPlacesDialog(Context mContext, String message, FragmentManager mFragmentManager) {
        super(mContext);
        this.mContext = mContext;
        this.message = message;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.places_dialoge_layout);


        //api must billing
        Places.initialize(mContext, "AIzaSyApIEvt80CbiC2R6IcGOVqEzyNYw6mlxjg"); // Initialize with your API key


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment) mFragmentManager.findFragmentById(R.id.autocompleteFragment);


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the selected place
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Handle the selected place
                Log.i("", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle error
                Log.i("", "An error occurred: " + status);
            }
        });


        setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());

        lp.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.90);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    public void setMessage(String message) {
        tv_message.setText(message);
    }
}
