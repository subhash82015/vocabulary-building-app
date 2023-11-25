package com.demo.collegeerp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.ActivityRouteBinding;
import com.demo.collegeerp.ui.fragment.AddUserFragment;
import com.demo.collegeerp.ui.fragment.RouteFragment;
import com.demo.collegeerp.ui.fragment.UserListFragment;

public class RouteActivity extends AppCompatActivity  implements RouteFragment.OnDataPassedListener {
    ActivityRouteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRouteBinding.inflate(getLayoutInflater());
        init();
        setContentView(binding.getRoot());
    }

    private void init() {
        handleClickListener();
        loadInitFragment();
    }

    private void loadInitFragment() {
        RouteFragment routeFragment = new RouteFragment();
        openFragment(routeFragment);
    }

    private void handleClickListener() {
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

    @Override
    public void onDataPassed(Bundle data) {
       String lat_s = data.getString("lat_s");
        String lan_s = data.getString("lan_s");
        String lan_l = data.getString("lan_l");
        String lat_l = data.getString("lat_l");

        Intent intent = new Intent(RouteActivity.this, MapActivity.class);
        intent.putExtra("lat_s", lat_s);
        intent.putExtra("lan_s", lan_s);
        intent.putExtra("lan_l", lan_l);
        intent.putExtra("lat_l", lat_l);
        startActivity(intent);
    }
}