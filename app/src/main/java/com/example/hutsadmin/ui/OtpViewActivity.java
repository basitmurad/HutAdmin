package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityOtpViewBinding;
import com.example.hutsadmin.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import in.aabhasjindal.otptextview.OTPListener;

public class OtpViewActivity extends AppCompatActivity {

    private ActivityOtpViewBinding binding;
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis;
    private long intervalInMillis;
    private String verificatonID, number, userUId, email, name, password;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in...");
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);


        databaseReference = FirebaseDatabase.getInstance().getReference();


        verificatonID = getIntent().getStringExtra("verificationID");
        number = getIntent().getStringExtra("number");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();


//        Toast.makeText(this, ""+number + name + email + randomUUId, Toast.LENGTH_SHORT).show();


        binding.textNumber.setText(name);
        totalTimeInMillis = 60000; // 60 seconds

        // Set the interval for the countdown timer in milliseconds
        intervalInMillis = 1000;


        StartCounter();

        OtpListner();

        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StartCounter();
                OtpListner();
            }
        });
    }


    private void OtpListner() {

        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {

                if (verificatonID != null) {
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificatonID, otp);

                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        progressDialog.show();

//                                        SendDataToFireBase();
                                    } else {
                                        Toast.makeText(OtpViewActivity.this, "Invalid Otp", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(OtpViewActivity.this, "Exception failure" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });
    }

    //
//    private void SendDataToFireBase() {
////        Users users = new Users(name, email, number, password, userUId);
//        databaseReference.child("AdminDetail").child(userUId).setValue(users)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                        progressDialog.dismiss();
//                        Intent intent = new Intent(OtpViewActivity.this, DashboardActivity.class);
//
//
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(OtpViewActivity.this, "Try Again...\n something went wrong", Toast.LENGTH_SHORT).show();
//
//                        Toast.makeText(OtpViewActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//    }

    private void StartCounter() {
        countDownTimer = new CountDownTimer(totalTimeInMillis, intervalInMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the TextView with the remaining time
                long secondsRemaining = millisUntilFinished / 1000;
                binding.textCounter.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                binding.btnResend.setVisibility(View.VISIBLE);
            }
        };

        countDownTimer.start();
    }
}