package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.FragmentAddFeesBinding;
import com.demo.collegeerp.databinding.FragmentAddUserBinding;
import com.demo.collegeerp.models.post.AddFees;
import com.demo.collegeerp.models.post.AddUsers;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.CustomProgressDialog;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class AddFeesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFeesFragment() {
        // Required empty public constructor
    }

    private String TAG = "AddFeesFragment";

    FragmentAddFeesBinding binding;
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;
    String amount = "", payType = "", remark = "";
    private SharedPreferenceUtil sharedPreferenceUtil;

    public static AddFeesFragment newInstance(String param1, String param2) {
        AddFeesFragment fragment = new AddFeesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddFeesBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(requireActivity());
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        handleClickListener();
    }

    private void handleClickListener() {
        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }


    private void checkValidation() {
        amount = binding.etAmount.getText().toString();
        remark = binding.etRemark.getText().toString();
        payType = binding.spPaymentType.getSelectedItem().toString();
        if (amount.equals("")) {
            Tools.showToast(requireActivity(), "Please enter amount");
        } else if (payType.equals("Select Payment Type")) {
            Tools.showToast(requireActivity(), "Select Payment Type");
        } else if (remark.equals("")) {
            Tools.showToast(requireActivity(), "Select Remark");
        } else {
            addPayment();
        }
    }

    private void addPayment() {
        customProgressDialog.show();
        DocumentReference docRef = firebaseFirestore.collection(Constants.FEES_COLLECTION_NAME).document(sharedPreferenceUtil.getUserDetails(Constants.MOBILE)); // Firestore database reference
        // Create a new user object
        AddFees addFees = new AddFees(sharedPreferenceUtil.getUserId(), amount, remark, payType);

        // Adding user information to Firestore
        docRef.set(addFees).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                binding.etAmount.setText("");
                binding.etRemark.setText("");
                customProgressDialog.dismiss();
                // User information added successfully
                Tools.logs(TAG, "User information added successfully");
                Tools.showToast(requireActivity(), "User information added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customProgressDialog.dismiss();

                // Handle any errors that may occur
                Tools.logs(TAG, "Error adding user information" + e);
                Tools.showToast(requireActivity(), "Error adding user information" + e);

            }
        });
    }

}