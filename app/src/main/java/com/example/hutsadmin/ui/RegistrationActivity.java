package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  =ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}