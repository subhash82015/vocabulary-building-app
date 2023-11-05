package com.demo.collegeerp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Tools {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public static void makePhoneCall(Context context) {
        String phoneNumber = "7800750614";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(callIntent);
    }

    public static void logs(String tag, String message) {
        Log.d(tag, "logs: Subhash Rathour " + message);
    }
}
