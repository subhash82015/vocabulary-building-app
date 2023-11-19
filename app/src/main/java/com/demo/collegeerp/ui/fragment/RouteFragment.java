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
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class RouteFragment extends Fragment {

    private String TAG = "RouteFragment ";

    FragmentRouteBinding binding;

    CustomProgressDialog customProgressDialog;

    private FirebaseFirestore firebaseFirestore;

    List<BusesResponse> modelList = new ArrayList<>();
    List<String> busNumberListName = new ArrayList<>();
    List<Long> busListId = new ArrayList<>();

    private String bus_number = "", bus_id = "";


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
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        getBusList();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}