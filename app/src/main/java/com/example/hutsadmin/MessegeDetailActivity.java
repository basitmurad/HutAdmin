package com.example.hutsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hutsadmin.adapters.MessAdapter;
import com.example.hutsadmin.adapters.MessegeAdapter;
import com.example.hutsadmin.databinding.ActivityMessegeDetailBinding;
import com.example.hutsadmin.databinding.ActivityMessegerBinding;
import com.example.hutsadmin.models.MessegeDetails;
import com.example.hutsadmin.ui.MessegerActivity;
import com.example.hutsadmin.utils.InternetChecker;
import com.example.hutsadmin.utils.NetworkChanger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessegeDetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference ,databaseReference1;
    String senderRoom, recieverRoom;

    private SessionManager sessionManager;
    private BroadcastReceiver broadcastReceiver;
    String token, adminId, userId;
    String messege;
    String userNumber;
    private ArrayList<MessegeDetails> messegeDetailsArrayList;
    private FirebaseDatabase firebaseDatabase;
    private MessegeAdapter messegeAdapter;
    private MessAdapter messAdapter;

    private ActivityMessegeDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessegeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();

        databaseReference1 =FirebaseDatabase.getInstance().getReference("Sender");
      // Update the "read" status in Firebase

        databaseReference = FirebaseDatabase.getInstance().getReference("chats");

        sessionManager = new SessionManager(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        messegeDetailsArrayList = new ArrayList<>();


        String idd = getIntent().getStringExtra("id");
        String userToken = getIntent().getStringExtra("token");
        userNumber = getIntent().getStringExtra("no");

        databaseReference1.child(idd).child("read").setValue(false);
        token = sessionManager.getAdminFcmToken();

        userId = FirebaseAuth.getInstance().getUid();


        senderRoom = userId + idd;

        recieverRoom = idd + userId;


//        Toast.makeText(this, "" + userToken, Toast.LENGTH_SHORT).show();
        databaseReference.child(senderRoom)
                .child("messeges").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            messegeDetailsArrayList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                MessegeDetails messegeDetails = dataSnapshot.getValue(MessegeDetails.class);
                                messegeDetailsArrayList.add(messegeDetails);
//                                Toast.makeText(MessegeDetailActivity.this, ""+messegeDetails.getMessege(), Toast.LENGTH_SHORT).show();
                            }

                            messAdapter = new MessAdapter(MessegeDetailActivity.this, messegeDetailsArrayList);

                            binding.recyclerMess.setAdapter(messAdapter);
                            binding.recyclerMess.setLayoutManager(new LinearLayoutManager(MessegeDetailActivity.this));
                            messAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(MessegeDetailActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(MessegeDetailActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messege = binding.editText.getText().toString();
                Date date = new Date();
//                long timeStm = date.getTime();
                String randomKey = firebaseDatabase.getReference().push().getKey();
                binding.editText.setText("");


                MessegeDetails messegeDetails1 = new MessegeDetails(messege, userId, randomKey, date.getTime());
                databaseReference.child(senderRoom).child("messeges").child(randomKey).setValue(messegeDetails1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                databaseReference.child(recieverRoom)
                                        .child("messeges")
                                        .child(randomKey)
                                        .setValue(messegeDetails1)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                onSendNotification(userToken, "Admin ", messege);

                                                Toast.makeText(MessegeDetailActivity.this, "message send", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MessegeDetailActivity.this, " " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                            }
                        });
                binding.btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userNumber));
                        startActivity(intent);
                    }
                });

            }
        });


        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userNumber));
                startActivity(intent);
            }
        });

    }


    private void onSendNotification(String token, String name, String order) {

        try {

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("title", name);
            jsonObject.put("body", order);


            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("notification", jsonObject);
            jsonObject1.put("to", token);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonObject1,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            //     Toast.makeText(PaymentActivity.this, "notifications send  " + response.toString(), Toast.LENGTH_SHORT).show();

                            Log.d("Notification", "sent notification");
                        }

                    }, error -> {

                Log.d("Notification", "sent not notification");
                Toast.makeText(this, "not send " + error.networkResponse, Toast.LENGTH_SHORT).show();
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    String key = "key=AAAAbLbshB4:APA91bGnUYsH35Uw2Knc0bABVFfAPkQZGg5F2YyUbgFsh9xe6bQb5uSm3QI_nH0alHATCT8mRkPNkweJsk5BoaB344dz6sgiFjKtTPBPo6pSuhsbZ-CdpccR5SuXSBuc5yvmOcetmxY9";
                    map.put("Content-type", "application/json");
                    map.put("Authorization", key);


                    return map;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(MessegeDetailActivity.this);
        if (!internetChecker.isConnected()) {

            internetChecker.showInternetDialog();
        }
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterNetworkChangeReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChangeReceiver();
    }

}