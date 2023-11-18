package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.collegeerp.adapter.BusListAdapter;
import com.demo.collegeerp.adapter.UserListAdapter;
import com.demo.collegeerp.databinding.FragmentBusListBinding;
import com.demo.collegeerp.models.BusesResponse;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.OnItemClickListener;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class BusListFragment extends Fragment implements OnItemClickListener {
    FragmentBusListBinding binding;

    private String TAG = "BusListFragment";

    private SharedPreferenceUtil sharedPreferenceUtil;
    private BusListAdapter userListAdapter;
    private FirebaseFirestore firebaseFirestore;
    List<BusesResponse> modelList = new ArrayList<>();
    CustomProgressDialog customProgressDialog;

    public BusListFragment() {
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
        binding = FragmentBusListBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }


    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(requireActivity());
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");

        getUserList();
    }

    private void getUserList() {
        modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            BusesResponse model = documentSnapshot.toObject(BusesResponse.class);
                            modelList.add(model);
                            setAdapter();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });

    }

    private void setAdapter() {
        userListAdapter = new BusListAdapter(requireActivity(), modelList, this);
        binding.rvBusList.setAdapter(userListAdapter);
    }

    @Override
    public void getSelectedValue(String name, int id) {
        deleteUser(id);
    }

    private void deleteUser(int id) {
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME); // Replace with your collection name
        Query query = collectionRef.whereEqualTo("id", id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Delete each document found
                        firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME).document(document.getId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document successfully deleted
                                        customProgressDialog.dismiss();
                                        Tools.logs(TAG, "Bus successfully deleted!");
                                        Tools.showToast(requireActivity(), "Bus successfully deleted!");
                                        getUserList();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        customProgressDialog.dismiss();
                                        // Handle any errors
                                        Tools.logs(TAG, "Error deleting document " + e.getMessage());
                                        Tools.showToast(requireActivity(), "Error deleting document " + e.getMessage());
                                    }
                                });
                    }
                } else {
                    customProgressDialog.dismiss();
                    // Handle any errors while querying
                    Tools.logs(TAG, "Error getting documents: ");
                }
            }
        });

    }
}