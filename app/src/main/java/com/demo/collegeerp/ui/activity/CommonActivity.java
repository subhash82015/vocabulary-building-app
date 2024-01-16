package com.demo.collegeerp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.ActivityCommonBinding;
import com.demo.collegeerp.databinding.ActivityDashboardBinding;
import com.demo.collegeerp.ui.fragment.AddFeesFragment;
import com.demo.collegeerp.ui.fragment.FeesListFragment;
import com.demo.collegeerp.ui.fragment.NotificationFragment;
import com.demo.collegeerp.utils.Constants;

public class CommonActivity extends AppCompatActivity {
    private ActivityCommonBinding binding;
    String screen_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        getBundleData();
        handleClickListener();
    }

    private void handleClickListener() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getBundleData() {
        screen_name = getIntent().getExtras().getString(Constants.SCREEN_NAME);
        if (screen_name.equals(Constants.NOTIFICATION)) {
            binding.tvCommonTitle.setText("Notifications");
            NotificationFragment notificationFragment = new NotificationFragment();
            openFragment(notificationFragment);
        } else if (screen_name.equals(Constants.FEES_SCREEEN)) {
            binding.tvCommonTitle.setText("Fees");
            AddFeesFragment addFeesFragment = new AddFeesFragment();
            openFragment(addFeesFragment);
        } else if (screen_name.equals(Constants.ADMIN_FEES_SCREEEN)) {
            binding.tvCommonTitle.setText(getString(R.string.analytics));
            FeesListFragment feesListFragment = new FeesListFragment();
            openFragment(feesListFragment);
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.commonFrameLayout, fragment);
        transaction.commit();
    }

}