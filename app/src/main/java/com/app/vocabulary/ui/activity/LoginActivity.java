package com.app.vocabulary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.vocabulary.R;
import com.app.vocabulary.databinding.ActivityLoginBinding;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.SharedPreferenceUtil;
import com.app.vocabulary.utils.Tools;
import com.app.vocabulary.utils.FirebaseRepo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    String mobile = "", password = "", email = "";
    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String TAG = "LoginActivity";
    private FirebaseFirestore firebaseFirestore;
    String full_name, course, section, branch, roll_no;
    Long userid, usertype;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(LoginActivity.this, "Please wait....");
        handleClickListener();
    }

    private void handleClickListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = binding.etMobile.getText().toString().trim();
                password = binding.etPassword.getText().toString().trim();
                loginValidation();
            }
        });
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegisterActivity();
            }
        });
    }

    private void loginValidation() {
        if (mobile.equals("")) {
            Tools.showToast(LoginActivity.this, "Please enter mobile");
        } else if (mobile.length() != 10) {
            Tools.showToast(LoginActivity.this, "Please enter 10 digits mobile");
        } else if (password.equals("")) {
            Tools.showToast(LoginActivity.this, "Please enter password");
        } else {
            checkUser(mobile, password);
        }
    }


    public void checkUser(String mobile, String password) {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("mobile", mobile).whereEqualTo("password", password);
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        getUserInfo(document);
                    }
                } else {
                    Tools.showToast(LoginActivity.this, "No user found with provided Mobile and password");
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    private void getUserInfo(QueryDocumentSnapshot document) {
        Tools.showToast(LoginActivity.this, "Login Success");
        Tools.logs(TAG, "User Details  " + document.getData());
        Map<String, Object> documentData = document.getData();
        if (documentData != null) {
            // Access 'userid' and 'usertype' fields
            userid = (Long) documentData.get("userid");
            usertype = (Long) documentData.get("usertype");
            full_name = (String) documentData.get("full_name");
            mobile = (String) documentData.get("mobile");
            email = (String) documentData.get("email");
            sharedPreferenceUtil.setUserId(userid);
            sharedPreferenceUtil.setUserDetails(Constants.FULL_NAME, full_name);
            sharedPreferenceUtil.setUserType(usertype);
            sharedPreferenceUtil.setUserDetails(Constants.MOBILE, mobile);
            sharedPreferenceUtil.setUserDetails(Constants.EMAIL, email);
            sharedPreferenceUtil.setUserId(userid);
            navigateToDashboard();
        }
    }


    private void navigateToDashboard() {
        sharedPreferenceUtil.setLoginAlready(true);
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}