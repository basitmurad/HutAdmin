package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private SessionManager sessionManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        databaseReference = FirebaseDatabase.getInstance().getReference("AdminDetail");


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginWithEmailAndPassword();
            }
        });


        binding.btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
    private void loginWithEmailAndPassword() {
        String email = binding.editTextTextEmail.getText().toString();
        String password = binding.editTextTextPassword.getText().toString();

        if (email.isEmpty()) {
            binding.editTextTextEmail.setError("Enter email");
            return;
        }

        if (password.isEmpty()) {
            binding.editTextTextPassword.setError("Enter password");
            return;
        }

        progressDialog.show();




        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                   FirebaseUser user=     firebaseAuth.getCurrentUser();


                        //Toast.makeText(this, ""+user.getEmail() +" " +user.getUid(), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));

                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {

                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}