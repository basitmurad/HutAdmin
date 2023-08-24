package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {


    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}