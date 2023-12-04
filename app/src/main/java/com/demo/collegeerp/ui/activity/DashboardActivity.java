package com.demo.collegeerp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
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
import android.view.View;

import com.demo.collegeerp.adapter.DashboardOptionsAdapter;
import com.demo.collegeerp.databinding.ActivityDashboardBinding;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.Helper;
import com.demo.collegeerp.utils.OnItemClickListener;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity implements OnItemClickListener, LocationListener {
    private final String TAG = "DashboardActivity";

    private ActivityDashboardBinding binding;
    private SharedPreferenceUtil sharedPreferenceUtil;

    String usertype, full_name, course, section, branch, roll_no;
    private FirebaseFirestore firebaseFirestore;

    private static final int PERMISSION_REQUEST_CODE = 100;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;

    double lastLatitude = 0;
    double lastLongitude = 0;

    String bus_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        loadServicesOptions();
        handleClickListener();
        setDataOnViews();
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
                Tools.showToast(DashboardActivity.this, "Permission Denied");

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
                            updateLastDriverLocation();
                        }
                    }
                });
    }

    private void updateLastDriverLocation() {
        // Get a reference to the document you want to update
        DocumentReference docRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME).document(bus_number);

        Map<String, Object> updates = new HashMap<>();
        updates.put("last_lan", String.valueOf(lastLongitude));

        updates.put("last_lat", String.valueOf(lastLatitude));

        docRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Updated successfully
                        Tools.showToast(DashboardActivity.this, "Location Updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Tools.showToast(DashboardActivity.this, "Error while Location Updation");
                        // Handle any errors
                    }
                });


    }

    @Override
    public void onLocationChanged(Location location) {
        lastLatitude = location.getLatitude();
        lastLongitude = location.getLongitude();
        updateLastDriverLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.DRIVER)) {
            locationManager.removeUpdates(this); // Stop getting location updates when the app is destroyed
        }
    }

    private void setDataOnViews() {
        binding.tvFullName.setText("Hi, " + sharedPreferenceUtil.getUserDetails(Constants.FULL_NAME));
        if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.STUDENT)) {
            binding.tvCourseDetails.setText("Course- " + sharedPreferenceUtil.getUserDetails(Constants.COURSE) + " || Branch- " + sharedPreferenceUtil.getUserDetails(Constants.BRANCH));
            binding.tvCourseDetails.setVisibility(View.VISIBLE);
        } else {
            binding.tvCourseDetails.setVisibility(View.GONE);
        }

        if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.ADMIN)) {
            binding.btnRollNo.setText("Admin");
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.PARENT)) {
            binding.btnRollNo.setText("Your Student's Roll No. " + sharedPreferenceUtil.getUserDetails(Constants.ROLL_NO));
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.STUDENT)) {
            binding.btnRollNo.setText("Roll No. " + sharedPreferenceUtil.getUserDetails(Constants.ROLL_NO));
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.DRIVER)) {
            binding.btnRollNo.setText("Driver");
        }
    }

    private void handleClickListener() {
        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CommonActivity.class);
                intent.putExtra(Constants.SCREEN_NAME, Constants.NOTIFICATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void loadServicesOptions() {
        String user_type = sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE);
        DashboardOptionsAdapter adapter = new DashboardOptionsAdapter(getApplicationContext(), DashboardActivity.this, Helper.getServiceOptions(getApplicationContext(), user_type));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        binding.rvServicesOptions.setLayoutManager(gridLayoutManager); // Set LayoutManager to RecyclerView
        binding.rvServicesOptions.setItemAnimator(new DefaultItemAnimator());
        binding.rvServicesOptions.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectedValue(String name, int id) {
        if (id == Constants.LOGOUT) {
            logout();
        } else if (id == Constants.EMERGENCY) {
            Tools.makePhoneCall(DashboardActivity.this);
        } else if (id == Constants.USER_MANAGEMENT) {
            Intent intent = new Intent(DashboardActivity.this, UserManagmentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == Constants.BUS_MANAGEMENT) {
            Intent intent = new Intent(DashboardActivity.this, BusManagementActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == Constants.ROUTE_PLANNING || id == Constants.ROUTE_DETAILS || id == Constants.BUS_TRACKING) {
            Intent intent = new Intent(DashboardActivity.this, RouteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void logout() {
        sharedPreferenceUtil.setUserDetails(String.valueOf(Constants.USER_TYPE), "");
        sharedPreferenceUtil.setUserId(0L);
        sharedPreferenceUtil.setLoginAlready(false);
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void checkBus() {
        CollectionReference usersRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("driver_id", String.valueOf(sharedPreferenceUtil.getUserId()));
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        getBusInfo(document);
                    }
                } else {
                    Tools.showToast(DashboardActivity.this, "No Bus found with provided Mobile and password");
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    private void getBusInfo(QueryDocumentSnapshot document) {
        Tools.logs(TAG, "Bus Details  " + document.getData());
        Map<String, Object> documentData = document.getData();
        if (documentData != null) {
            // Access 'userid' and 'usertype' fields
            bus_number = (String) documentData.get("bus_number");
            handleLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.DRIVER)) {
            checkBus();
        }
    }
}