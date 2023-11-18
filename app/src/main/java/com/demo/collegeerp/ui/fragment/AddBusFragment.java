package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.demo.collegeerp.R;
import com.demo.collegeerp.adapter.UserListAdapter;
import com.demo.collegeerp.databinding.FragmentAddBusBinding;
import com.demo.collegeerp.models.UsersResponse;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class AddBusFragment extends Fragment {
    private String TAG = "AddBusFragment ";
    FragmentAddBusBinding binding;
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;

    public AddBusFragment() {
        // Required empty public constructor
    }

    List<UsersResponse> modelList = new ArrayList<>();
    List<UsersResponse> newModelList = new ArrayList<>();
    List<String> driverListName = new ArrayList<>();
    List<Long> driverListId = new ArrayList<>();
    Long driverId=0L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBusBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        getUserList();
    }

    private void getUserList() {
        // modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            UsersResponse model = documentSnapshot.toObject(UsersResponse.class);
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
            if (modelList.get(i).getUsertype().equals("4")) {
                newModelList.addAll(modelList);
                driverListName.add(modelList.get(i).getFull_name());
                driverListId.add(modelList.get(i).getUserid());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, driverListName);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.spDriver.setAdapter(adapter);
        setOnItemSelectedListener();

    }

    private void setOnItemSelectedListener() {
        binding.spDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                driverId = driverListId.get(i);
                //Tools.showToast(requireActivity(), ""+driverId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}