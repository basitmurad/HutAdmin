package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding   = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}