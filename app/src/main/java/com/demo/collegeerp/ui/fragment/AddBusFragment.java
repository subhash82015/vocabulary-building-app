package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.demo.collegeerp.models.post.AddBus;
import com.demo.collegeerp.models.post.AddUsers;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    Long driverId = 0L, bus_id;

    String busNumber = "", sourceLat = "", sourceLon = "", destinationLat = "", destinationLon = "",driver_name="";

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
        handleClickListener();
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
                driver_name = driverListName.get(i);
                //Tools.showToast(requireActivity(), ""+driverId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleClickListener() {
        binding.btnAddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busNumber = binding.etBusNumber.getText().toString();
                sourceLat = binding.etSourceLat.getText().toString();
                sourceLon = binding.etSourceLan.getText().toString();
                destinationLat = binding.etSourceLat.getText().toString();
                destinationLon = binding.etDestinationLan.getText().toString();
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        if (busNumber.equals("")) {
            Tools.showToast(requireActivity(), "Please Bus number");
        } else if (sourceLat.equals("")) {
            Tools.showToast(requireActivity(), "Select Source latitude");
        } else if (sourceLon.equals("")) {
            Tools.showToast(requireActivity(), "Please Source longitude");
        } else if (destinationLat.equals("")) {
            Tools.showToast(requireActivity(), "Select Destination latitude");
        } else if (destinationLon.equals("")) {
            Tools.showToast(requireActivity(), "Please Destination longitude");
        } else {
            checkBusNumber();
        }
    }

    public void checkBusNumber() {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("bus_number", busNumber);
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Tools.showToast(requireActivity(), "Bus Number already added");
                        customProgressDialog.dismiss();
                    }
                } else {
                    getLastBusInfo();
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    private void getLastBusInfo() {
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME);


// Create a query to order the collection in descending order and limit to 1 document
        Query query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1);

// Perform the query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Access the last document
                        // String lastDocumentId = document.getId();
                        // Now you can work with the data in 'document'


                        Tools.logs(TAG, "Bus Details  " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        if (documentData != null) {
                            // Access 'userid' and 'usertype' fields
                            bus_id = (Long) documentData.get("id");
                            bus_id = bus_id + 1L;
                            addBusAccount();
                        }
                    }
                } else {
                    // Handle any errors
                    Tools.showToast(requireActivity(), "Error");
                }
            }
        });

    }

    private void addBusAccount() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME).document(busNumber); // Firestore database reference
        // Create a new user object
        AddBus addBus = new AddBus(bus_id, busNumber, destinationLon, destinationLat, String.valueOf(driverId), sourceLon, sourceLat,driver_name);

        // Adding user information to Firestore
        docRef.set(addBus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                customProgressDialog.dismiss();
                // User information added successfully
                Tools.logs(TAG, "Bus added successfully");
                clearFields();
                Tools.showToast(requireActivity(), "Bus added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customProgressDialog.dismiss();

                // Handle any errors that may occur
                Tools.logs(TAG, "Error adding Bus information" + e);
                Tools.showToast(requireActivity(), "Error adding Bus information" + e);

            }
        });
    }

    private void clearFields() {
        binding.etBusNumber.setText("");
        binding.etSourceLat.setText("");
        binding.etSourceLan.setText("");
        binding.etSourceLat.setText("");
        binding.etDestinationLan.setText("");
        binding.etDestinationLat.setText("");
    }


}