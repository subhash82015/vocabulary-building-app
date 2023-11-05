package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.collegeerp.R;
import com.demo.collegeerp.adapter.NotificationsListAdapter;
import com.demo.collegeerp.databinding.FragmentNotificationBinding;
import com.demo.collegeerp.models.NotificationResponse;
import com.demo.collegeerp.ui.activity.LoginActivity;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {
    private final String TAG = "NotificationFragment";

    private FragmentNotificationBinding binding;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private NotificationsListAdapter notificationsListAdapter;
    private FirebaseFirestore firebaseFirestore;
    List<NotificationResponse> modelList = new ArrayList<>();

    CustomProgressDialog customProgressDialog;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        //  args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(requireActivity());
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");

        setLayoutManager();
        getNotificationList();
    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        binding.rvNotificationList.setLayoutManager(linearLayoutManager);
        binding.rvNotificationList.setItemAnimator(new DefaultItemAnimator());
    }

    private void setAdapter() {
        notificationsListAdapter = new NotificationsListAdapter(requireActivity(), modelList);
        binding.rvNotificationList.setAdapter(notificationsListAdapter);
    }


    private void getNotificationList() {
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.NOTIFICATION_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            NotificationResponse model = documentSnapshot.toObject(NotificationResponse.class);
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

}