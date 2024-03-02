package com.app.vocabulary.ui.activity;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.vocabulary.databinding.ActivityRegisterBinding;
import com.app.vocabulary.models.AddUsers;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.FirebaseRepo;
import com.app.vocabulary.utils.Tools;
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

import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    CustomProgressDialog customProgressDialog;

    private static final String TAG = "RegisterActivity";
    Long userid = 0L;

    private FirebaseFirestore firebaseFirestore;
    String mobile = "", password = "", name = "", email = "";

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(RegisterActivity.this, "Please wait....");
        handleClickListener();
    }

    private void handleClickListener() {
        binding.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = binding.etMobile.getText().toString().trim();
                password = binding.etPassword.getText().toString().trim();
                email = binding.etEmail.getText().toString().trim();
                name = binding.etFName.getText().toString().trim();
                loginValidation();
            }
        });

    }

    private void loginValidation() {
        if (name.equals("")) {
            Tools.showToast(RegisterActivity.this, "Please enter Name");
        }
        if (mobile.equals("")) {
            Tools.showToast(RegisterActivity.this, "Please enter mobile");
        } else if (mobile.length() != 10) {
            Tools.showToast(RegisterActivity.this, "Please enter 10 digits mobile");
        } else if (password.equals("")) {
            Tools.showToast(RegisterActivity.this, "Please enter password");
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
                        Tools.showToast(RegisterActivity.this, "user found with provided Mobile");
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
                    Tools.showToast(RegisterActivity.this, "Error");
                }
            }
        });

    }

    private void addAccount() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME).document(mobile); // Firestore database reference
        // Create a new user object
        AddUsers newUser = new AddUsers(userid, name, mobile, password, email, 2L);

        // Adding user information to Firestore
        docRef.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                customProgressDialog.dismiss();
                // User information added successfully
                Tools.logs(TAG, "User information added successfully");
                Tools.showToast(RegisterActivity.this, "User information added successfully");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customProgressDialog.dismiss();

                // Handle any errors that may occur
                Tools.logs(TAG, "Error adding user information" + e);
                Tools.showToast(RegisterActivity.this, "Error adding user information" + e);

            }
        });
    }

}
