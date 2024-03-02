package com.app.vocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.app.vocabulary.databinding.ActivityMainBinding;
import com.app.vocabulary.ui.activity.DashboardActivity;
import com.app.vocabulary.ui.activity.LoginActivity;
import com.app.vocabulary.utils.SharedPreferenceUtil;

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
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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