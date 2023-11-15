package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.collegeerp.R;
import com.demo.collegeerp.adapter.NotificationsListAdapter;
import com.demo.collegeerp.adapter.UserListAdapter;
import com.demo.collegeerp.databinding.FragmentUserListBinding;
import com.demo.collegeerp.models.NotificationResponse;
import com.demo.collegeerp.models.UsersResponse;
import com.demo.collegeerp.ui.activity.LoginActivity;
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


public class UserListFragment extends Fragment implements OnItemClickListener {
    private String TAG = "UserListFragment";
    FragmentUserListBinding binding;

    private SharedPreferenceUtil sharedPreferenceUtil;
    private UserListAdapter userListAdapter;
    private FirebaseFirestore firebaseFirestore;
    List<UsersResponse> modelList = new ArrayList<>();
    CustomProgressDialog customProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserListBinding.inflate(getLayoutInflater(), container, false);
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
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            UsersResponse model = documentSnapshot.toObject(UsersResponse.class);
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
        userListAdapter = new UserListAdapter(requireActivity(), modelList, this);
        binding.rvUserList.setAdapter(userListAdapter);
    }

    @Override
    public void getSelectedValue(String name, int id) {
        deleteUser(id);
    }

    private void deleteUser(int id) {
        customProgressDialog.show();
        // Get the Firestore instance

// Define the collection and the user ID you want to delete
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME); // Replace with your collection name

// Create a query to find documents with the specific user ID
        Query query = collectionRef.whereEqualTo("userid", id);

// Execute the query and delete the documents found
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Delete each document found
                        firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME).document(document.getId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Document successfully deleted
                                        customProgressDialog.dismiss();
                                        Tools.logs(TAG, "User successfully deleted!");
                                        Tools.showToast(requireActivity(), "User successfully deleted!");
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