package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}