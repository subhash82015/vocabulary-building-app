package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.demo.collegeerp.R;
import com.demo.collegeerp.adapter.BusListAdapter;
import com.demo.collegeerp.adapter.FeesListAdapter;
import com.demo.collegeerp.databinding.FragmentAddBusBinding;
import com.demo.collegeerp.databinding.FragmentAddFeesBinding;
import com.demo.collegeerp.databinding.FragmentFeesListBinding;
import com.demo.collegeerp.models.UsersResponse;
import com.demo.collegeerp.models.post.AddFees;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class FeesListFragment extends Fragment {
    private String TAG = "AddFeesFragment";

    FragmentFeesListBinding binding;
    private FirebaseFirestore firebaseFirestore;
    FeesListAdapter feesListAdapter;
    CustomProgressDialog customProgressDialog;
    String amount = "", payType = "", remark = "", sName;
    Long sId;
    private SharedPreferenceUtil sharedPreferenceUtil;
    List<UsersResponse> modelList = new ArrayList<>();
    List<UsersResponse> newModelList = new ArrayList<>();
    List<String> sListName = new ArrayList<>();
    List<Long> sListId = new ArrayList<>();


    List<AddFees> feesModelList = new ArrayList<>();
    List<AddFees> feesNewModelList = new ArrayList<>();

    Boolean isFilter = false;


    public FeesListFragment() {
        // Required empty public constructor
    }

    public static FeesListFragment newInstance(String param1, String param2) {
        FeesListFragment fragment = new FeesListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeesListBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        getUserList();
        // getFeesList();
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


    private void getFeesList() {
        feesModelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.FEES_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            AddFees model = documentSnapshot.toObject(AddFees.class);
                            feesModelList.add(model);
                            setFeesAdapter();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });


    }

    private void getFilterFeesList() {
        feesModelList.clear();
        customProgressDialog.show();
        Query collectionRef = firebaseFirestore.collection(Constants.FEES_COLLECTION_NAME).whereEqualTo("userId",sId); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    customProgressDialog.dismiss();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            AddFees model = documentSnapshot.toObject(AddFees.class);
                            feesModelList.add(model);
                        }
                    }
                    setFeesAdapter();

                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });


    }



    private void getNewFilterFeesList() {
        feesNewModelList.clear();
        for (int i = 0; i < feesModelList.size(); i++) {
            if (feesModelList.get(i).getUserId().equals(sId)) {
                feesNewModelList.addAll(feesModelList);
            }
        }

        setAdapter();

    }

    private void setAdapter() {
        feesListAdapter = new FeesListAdapter(requireActivity(), feesNewModelList);
        binding.rvUserList.setAdapter(feesListAdapter);
    }

    private void setFeesAdapter() {
        feesListAdapter = new FeesListAdapter(requireActivity(), feesModelList);
        binding.rvUserList.setAdapter(feesListAdapter);
    }

    private void setSpinnerData() {
        modelList.add(0, new UsersResponse(0L, "0", "", "", "", "", "Select Student", "", "3", ""));
        for (int i = 0; i < modelList.size(); i++) {
            if (modelList.get(i).getUsertype().equals("3")) {
                newModelList.addAll(modelList);
                sListName.add(modelList.get(i).getFull_name());
                sListId.add(modelList.get(i).getUserid());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, sListName);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        binding.spUsers.setAdapter(adapter);
        setOnItemSelectedListener();

    }

    private void setOnItemSelectedListener() {
        binding.spUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sId = sListId.get(i);
                sName = sListName.get(i);
                //Tools.showToast(requireActivity(), "" + sName);
                if (sName.equalsIgnoreCase("Select Student")) {
                    isFilter = false;
                    getFeesList();
                } else {
                    isFilter = true;
                    getFilterFeesList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}