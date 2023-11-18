package com.demo.collegeerp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.collegeerp.adapter.DashboardOptionsAdapter;
import com.demo.collegeerp.databinding.ActivityDashboardBinding;
import com.demo.collegeerp.models.post.AddUsers;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.FirebaseRepo;
import com.demo.collegeerp.utils.Helper;
import com.demo.collegeerp.utils.OnItemClickListener;
import com.demo.collegeerp.utils.SharedPreferenceUtil;
import com.demo.collegeerp.utils.Tools;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity implements OnItemClickListener {
    private final String TAG = "DashboardActivity";

    private ActivityDashboardBinding binding;
    private SharedPreferenceUtil sharedPreferenceUtil;

    String usertype, full_name, course, section, branch, roll_no;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        loadServicesOptions();
        handleClickListener();
        setDataOnViews();
    }


    private void setDataOnViews() {
        binding.tvFullName.setText("Hi, " + sharedPreferenceUtil.getUserDetails(Constants.FULL_NAME));
        binding.tvCourseDetails.setText("Course- " + sharedPreferenceUtil.getUserDetails(Constants.COURSE) + " || Branch- " + sharedPreferenceUtil.getUserDetails(Constants.BRANCH));

        if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.ADMIN)) {
            binding.btnRollNo.setText("Admin");
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.PARENT)) {
            binding.btnRollNo.setText("Your Student's Roll No. " + sharedPreferenceUtil.getUserDetails(Constants.ROLL_NO));
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.STUDENT)) {
            binding.btnRollNo.setText("Roll No. " + sharedPreferenceUtil.getUserDetails(Constants.ROLL_NO));
        } else if (sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE).equals(Constants.STUDENT)) {
            binding.btnRollNo.setText("Driver");
        }
    }

    private void handleClickListener() {
        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CommonActivity.class);
                intent.putExtra(Constants.SCREEN_NAME, Constants.NOTIFICATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void loadServicesOptions() {
        String user_type = sharedPreferenceUtil.getUserDetails(Constants.USER_TYPE);
        DashboardOptionsAdapter adapter = new DashboardOptionsAdapter(getApplicationContext(), DashboardActivity.this, Helper.getServiceOptions(getApplicationContext(), user_type));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        binding.rvServicesOptions.setLayoutManager(gridLayoutManager); // Set LayoutManager to RecyclerView
        binding.rvServicesOptions.setItemAnimator(new DefaultItemAnimator());
        binding.rvServicesOptions.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getSelectedValue(String name, int id) {
        if (id == Constants.LOGOUT) {
            logout();
        } else if (id == Constants.EMERGENCY) {
            Tools.makePhoneCall(DashboardActivity.this);
        }
        else if (id == Constants.USER_MANAGEMENT) {
            Intent intent = new Intent(DashboardActivity.this, UserManagmentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if (id == Constants.BUS_MANAGEMENT) {
            Intent intent = new Intent(DashboardActivity.this, BusManagementActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void logout() {
        sharedPreferenceUtil.setUserDetails(String.valueOf(Constants.USER_TYPE), "");
        sharedPreferenceUtil.setUserId(0L);
        sharedPreferenceUtil.setLoginAlready(false);
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}