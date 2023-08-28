package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {


    private ActivitySplashBinding binding;
    private FirebaseUser firebaseUser;

    private FirebaseAuth firebaseAuth;
    private SessionManager sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseUser==null)
                {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this,DashboardActivity.class));

                }
            }
        },700);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}