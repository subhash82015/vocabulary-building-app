package com.demo.collegeerp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.demo.collegeerp.databinding.ActivityMainBinding;
import com.demo.collegeerp.ui.activity.DashboardActivity;
import com.demo.collegeerp.ui.activity.LoginActivity;
import com.demo.collegeerp.utils.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {
    private int SPLASH_DISPLAY_LENGTH = 3000;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        initControl();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    public void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        // FirebaseApp.initializeApp(this);
        // Companion.getDeviceToken(sharedPreferenceUtil);
    }

    public void initControl() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferenceUtil.isLoginAlready()) {
                    Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}