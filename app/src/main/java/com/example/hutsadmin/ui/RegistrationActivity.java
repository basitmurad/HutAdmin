package com.example.hutsadmin.ui;

import static android.content.ContentValues.TAG;

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
import com.example.hutsadmin.databinding.ActivityRegistrationBinding;

import com.example.hutsadmin.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private ProgressDialog progressDialog;
    private String email, password, number, name, userUId,adminFcmToken ;
    private FirebaseAuth firebaseAuth;
    private SessionManager sessionManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fcmToken();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckValidations();
            }
        });

        binding.btnLOgin.setOnClickListener(view -> {


            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });


    }



    private void CheckValidations() {
        if (binding.editTextTextUserName.getText().toString().isEmpty()) {
            binding.editTextTextUserName.setError("Name is empty");
        } else if (binding.editTextTextPassword.getText().toString().isEmpty()) {
            binding.editTextTextPassword.setError("Password is missing");
        } else if (binding.editTextTextEmail.getText().toString().isEmpty()) {
            binding.editTextTextEmail.setError("Email is empty");
        } else if (binding.editTextTextNumber.getText().toString().isEmpty()) {
            binding.editTextTextNumber.setError("Number is empty");
        } else {


            progressDialog.setTitle("PLease wait..");
            progressDialog.setMessage("Creating account");
            progressDialog.setCancelable(false);
            progressDialog.show();

            name = binding.editTextTextUserName.getText().toString();
            password = binding.editTextTextPassword.getText().toString();
            email = binding.editTextTextEmail.getText().toString();
            number = binding.editTextTextNumber.getText().toString();


            createAccountWithEmailAndPassword(email, password);


//            PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + number,
//                    60l,
//                    TimeUnit.SECONDS, RegistrationActivity.this,
//                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                        @Override
//                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//
//                        }
//
//                        @Override
//                        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//                            Log.d("Exception", e.getMessage());
//                            Toast.makeText(RegistrationActivity.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
//                        }
//
//                        @Override
//                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//
//
//                            progressDialog.dismiss();
//
//                            Intent intent = new Intent(RegistrationActivity.this, OtpViewActivity.class);
//                            intent.putExtra("verificationID", s);
//                            intent.putExtra("number", "+92" + number);
//                            intent.putExtra("email", email);
//                            intent.putExtra("name", name);
//                            intent.putExtra("password", password);
//
////                            sessionManager.saveCredentials(name
////                                    ,password,email,number
////                            );
//
//
//                            startActivity(intent);
//
//
//                        }
            //      });

        }
    }

    private void createAccountWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            SendDataToFireBase();
//                            sessionManager.saveCredentials(name, password, email, number);
                            Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                        } else {

                            progressDialog.dismiss();

                            Toast.makeText(RegistrationActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendDataToFireBase() {
        Users users = new Users(name, email, number, password, userUId,adminFcmToken);
            databaseReference.child("AdminDetail").child(userUId).setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);


                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Try Again...\n something went wrong", Toast.LENGTH_SHORT).show();

                        Toast.makeText(RegistrationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void fcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the FCM token
                    adminFcmToken = task.getResult();
                    sessionManager.setAdminFcmToken(adminFcmToken);

                    // Now you have the FCM token for the admin app
                    Log.d(TAG, "token" + adminFcmToken);
                    Log.d("toeken" , adminFcmToken);
                    Toast.makeText(this, ""+adminFcmToken, Toast.LENGTH_SHORT).show();
                    // Save the FCM token to your server or preferences if needed
                });
    }

}