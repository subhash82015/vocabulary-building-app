package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.FragmentAddUserBinding;
import com.demo.collegeerp.databinding.FragmentRouteBinding;
import com.demo.collegeerp.models.BusesResponse;
import com.demo.collegeerp.ui.activity.LoginActivity;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.LocationHelper;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RouteFragment extends Fragment {

    private String TAG = "RouteFragment ";

    FragmentRouteBinding binding;

    CustomProgressDialog customProgressDialog;

    private FirebaseFirestore firebaseFirestore;

    List<BusesResponse> modelList = new ArrayList<>();
    List<String> busNumberListName = new ArrayList<>();
    List<Long> busListId = new ArrayList<>();

    BusesResponse busesResponse;

    private String bus_number = "", bus_id = "", driver_name = "";

    private SharedPreferenceUtil sharedPreferenceUtil;

    public RouteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRouteBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(requireActivity());
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        getBusList();
        handleVisibility();
    }

    private void handleVisibility() {
        if (!sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.ADMIN)) {
            binding.spBuses.setVisibility(View.GONE);
            getUserInfo();
        } else {
            binding.spBuses.setVisibility(View.VISIBLE);
        }
    }


    public void getUserInfo() {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("userid", sharedPreferenceUtil.getUserId());
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        getUserInfo(document);
                    }
                } else {
                    Tools.showToast(requireActivity(), "No user found with provided Mobile and password");
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    private void getUserInfo(QueryDocumentSnapshot document) {
        Tools.showToast(requireActivity(), "Login Success");
        Tools.logs(TAG, "User Details  " + document.getData());
        Map<String, Object> documentData = document.getData();
        if (documentData != null) {
            if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.DRIVER)) {
                final Long userid = (Long) documentData.get("userid");
                getBusInfoByDriverId(String.valueOf(userid));
            } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.STUDENT)) {
                final String busId = (String) documentData.get("bus_id");
                getBusInfo(busId);
            } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.PARENT)) {
                final String busId = (String) documentData.get("bus_id");
                getBusInfo(busId);
            }

        }
    }

    public void getBusInfo(String busId) {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("id", Long.parseLong(busId));
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        getBusInfoDetails(document);
                    }
                } else {
                    Tools.showToast(requireActivity(), "No user found with provided Mobile and password");
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    public void getBusInfoByDriverId(String userid) {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("driver_id", userid);
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        getBusInfoDetails(document);
                    }
                } else {
                    Tools.showToast(requireActivity(), "No user found with provided Mobile and password");
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }


    private void getBusInfoDetails(QueryDocumentSnapshot document) {
        // Tools.showToast(requireActivity(), "Login Success");
        Tools.logs(TAG, "Bus Details  " + document.getData());
        Map<String, Object> documentData = document.getData();
        if (documentData != null) {
            final String driver_name = (String) documentData.get("driver_name");
            final String bus_number = (String) documentData.get("bus_number");
            final String source_lan = (String) documentData.get("source_lan");
            final String source_lat = (String) documentData.get("source_lat");
            final String destination_lan = (String) documentData.get("destination_lan");
            final String destination_lat = (String) documentData.get("destination_lat");
            final String last_lan = (String) documentData.get("last_lan");
            final String last_lat = (String) documentData.get("last_lat");
            binding.tvDriverName.setText(driver_name);
            binding.tvBusNumber.setText(bus_number);
            binding.tvSourceAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(source_lat), Double.parseDouble(source_lan)));
            binding.tvDestinationAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(destination_lat), Double.parseDouble(destination_lan)));
            binding.tvLastAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(last_lat), Double.parseDouble(last_lan)));

        }
    }


    private void getBusList() {
        // modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            BusesResponse model = documentSnapshot.toObject(BusesResponse.class);
                            modelList.add(model);
                        }
                    }
                    setSpinnerData();
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });


    }

    private void setSpinnerData() {
        for (int i = 0; i < modelList.size(); i++) {
            busNumberListName.add(modelList.get(i).getBus_number());
            busListId.add(modelList.get(i).getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, busNumberListName);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.spBuses.setAdapter(adapter);
        setOnItemSelectedListener();

    }


    private void setOnItemSelectedListener() {
        binding.spBuses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bus_id = String.valueOf(busListId.get(i));
                bus_number = busNumberListName.get(i);
                busesResponse = modelList.get(i);
                setBusData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setBusData() {
        binding.tvDriverName.setText(busesResponse.getDriver_name());
        binding.tvBusNumber.setText(busesResponse.getBus_number());
        binding.tvSourceAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(busesResponse.getSource_lat()), Double.parseDouble(busesResponse.getSource_lan())));
        binding.tvDestinationAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(busesResponse.getDestination_lat()), Double.parseDouble(busesResponse.getDestination_lan())));
        binding.tvLastAddress.setText(LocationHelper.getAddressFromLatLng(requireActivity(), Double.parseDouble(busesResponse.getLast_lat()), Double.parseDouble(busesResponse.getLast_lan())));
    }
}