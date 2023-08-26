package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding   = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Toast.makeText(this, ""+ FirebaseAuth.getInstance().getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}