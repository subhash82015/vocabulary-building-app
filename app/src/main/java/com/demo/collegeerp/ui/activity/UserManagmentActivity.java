package com.demo.collegeerp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.ActivityUserManagmentBinding;
import com.demo.collegeerp.ui.fragment.AddUserFragment;
import com.demo.collegeerp.ui.fragment.NotificationFragment;

public class UserManagmentActivity extends AppCompatActivity {
    ActivityUserManagmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserManagmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        handleClickListener();
    }

    private void handleClickListener() {
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserFragment addUserFragment = new AddUserFragment();
                openFragment(addUserFragment);
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