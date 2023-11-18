package com.demo.collegeerp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.ActivityBusManagementBinding;
import com.demo.collegeerp.ui.fragment.AddBusFragment;
import com.demo.collegeerp.ui.fragment.AddUserFragment;
import com.demo.collegeerp.ui.fragment.BusListFragment;
import com.demo.collegeerp.ui.fragment.UserListFragment;

public class BusManagementActivity extends AppCompatActivity {
    ActivityBusManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }


    private void init() {
        handleClickListener();
        loadInitFragment();
    }

    private void loadInitFragment() {
        BusListFragment busListFragment = new BusListFragment();
        openFragment(busListFragment);
    }

    private void handleClickListener() {
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBusFragment addBusFragment = new AddBusFragment();
                openFragment(addBusFragment);
            }
        });
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.commonFrameLayout, fragment);
        transaction.commit();
    }
}