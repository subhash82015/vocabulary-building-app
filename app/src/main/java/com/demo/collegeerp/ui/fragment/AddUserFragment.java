package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.demo.collegeerp.databinding.FragmentAddUserBinding;
import com.demo.collegeerp.models.BusesResponse;
import com.demo.collegeerp.models.UsersResponse;
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


public class AddUserFragment extends Fragment {
    CustomProgressDialog customProgressDialog;

    private String TAG = "AddUserFragment";
    FragmentAddUserBinding binding;
    private String name = "", mobile = "", course = "", branch = "", section = "", password = "", usertype = "", rollNo = "", feesAmount = "", bus_number = "", bus_id = "";
    Long userid = 0L;
    private FirebaseFirestore firebaseFirestore;

    List<BusesResponse> modelList = new ArrayList<>();
    List<String> busNumberListName = new ArrayList<>();
    List<String> userTypeList = new ArrayList<>();
    List<Long> busListId = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(getLayoutInflater(), container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(requireActivity(), "Please wait....");
        handleClickListener();
        setOnItemSelectedListenerUserType();
        getBusList();
    }

    private void getBusList() {
        // modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.BUS_ACCOUNT_COLLECTION_NAME); // Replace with your collection name

        collectionRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                customProgressDialog.dismiss();
                if (documentSnapshot.exists()) {
                    BusesResponse model = documentSnapshot.toObject(BusesResponse.class);
                    modelList.add(model);
                }
            }
            setSpinnerData();
        }).addOnFailureListener(e -> {
            customProgressDialog.dismiss();
            // Handle any errors that occur while fetching documents
            Tools.logs(TAG, "Error getting documents: " + e);
        });


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


    private void setOnItemSelectedListenerUserType() {

        binding.spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String usertype = binding.spUserType.getSelectedItem().toString();

                switch (usertype) {
                    case "Select User Type":
                    case "Student":
                        studentVisibility();
                        break;
                    case "Parent":
                    case "Driver":
                        handleOthersVisibility();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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


    private void handleClickListener() {
        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.etFName.getText().toString();
                mobile = binding.etMobile.getText().toString();
                password = binding.etPassword.getText().toString();
                rollNo = binding.etRollNo.getText().toString();
                feesAmount = binding.etFeesAmount.getText().toString();
                course = binding.spCourse.getSelectedItem().toString();
                branch = binding.spBranch.getSelectedItem().toString();
                section = binding.spSection.getSelectedItem().toString();
                usertype = binding.spUserType.getSelectedItem().toString();
                if (course.equals("Select Course")) {
                    course = "";
                }
                if (branch.equals("Select Branch")) {
                    branch = "";
                }
                if (section.equals("Select Section")) {
                    section = "";
                }
                if (usertype.equals("Select User Type")) {
                    usertype = "";
                } else if (usertype.equals("Parent")) {
                    usertype = "2";
                    handleOthersVisibility();
                } else if (usertype.equals("Student")) {
                    usertype = "3";
                    studentVisibility();
                } else if (usertype.equals("Driver")) {
                    usertype = "4";
                    handleOthersVisibility();
                }
                checkValidation();
            }
        });
    }

    private void studentVisibility() {
        binding.llBus.setVisibility(View.VISIBLE);
        binding.llFeesAmount.setVisibility(View.VISIBLE);
        binding.llSection.setVisibility(View.VISIBLE);
        binding.llBranch.setVisibility(View.VISIBLE);
        binding.llCourse.setVisibility(View.VISIBLE);
    }

    private void handleOthersVisibility() {
        binding.llBus.setVisibility(View.GONE);
        binding.llFeesAmount.setVisibility(View.GONE);
        binding.llSection.setVisibility(View.GONE);
        binding.llBranch.setVisibility(View.GONE);
        binding.llCourse.setVisibility(View.GONE);
    }


    private void checkValidation() {
        if (name.equals("")) {
            Tools.showToast(requireActivity(), "Please enter name");
        } else if (course.equals("") && usertype.equals("3")) {
            Tools.showToast(requireActivity(), "Select Course");
        } else if (branch.equals("") && usertype.equals("3")) {
            Tools.showToast(requireActivity(), "Select Branch");
        } else if (section.equals("") && usertype.equals("3")) {
            Tools.showToast(requireActivity(), "Select Section");
        } else if (usertype.equals("")) {
            Tools.showToast(requireActivity(), "Select User Type");
        } else if (mobile.equals("")) {
            Tools.showToast(requireActivity(), "Please enter mobile");
        } else if (mobile.length() != 10) {
            Tools.showToast(requireActivity(), "Please enter 10 digits mobile");
        } else if (rollNo.equals("")) {
            Tools.showToast(requireActivity(), "Please enter Roll No");
        } else {
            checkUser();
        }
    }

    public void checkUser() {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("mobile", mobile);
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Tools.showToast(requireActivity(), "user found with provided Mobile");
                        customProgressDialog.dismiss();
                    }
                } else {
                    getLastUserInfo();
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }


    private void getLastUserInfo() {
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME);


// Create a query to order the collection in descending order and limit to 1 document
        Query query = collectionRef.orderBy("userid", Query.Direction.DESCENDING).limit(1);

// Perform the query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Access the last document
                        // String lastDocumentId = document.getId();
                        // Now you can work with the data in 'document'


                        Tools.logs(TAG, "User Details  " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        if (documentData != null) {
                            // Access 'userid' and 'usertype' fields
                            userid = (Long) documentData.get("userid");
                            userid = userid + 1L;
                            addAccount();
                        }
                    }
                } else {
                    // Handle any errors
                    Tools.showToast(requireActivity(), "Error");
                }
            }
        });

    }

    private void addAccount() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME).document(mobile); // Firestore database reference
        // Create a new user object
        AddUsers newUser = new AddUsers(branch, course, name, mobile, rollNo, rollNo, section, userid, usertype, feesAmount, bus_number, bus_id);

        // Adding user information to Firestore
        docRef.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
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