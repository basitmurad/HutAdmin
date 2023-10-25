package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityDeliveredOrdersBinding;

public class DeliveredOrdersDetailActivity extends AppCompatActivity {

    private ActivityDeliveredOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeliveredOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}