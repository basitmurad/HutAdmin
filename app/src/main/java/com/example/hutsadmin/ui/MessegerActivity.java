package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hutsadmin.adapters.UserAdapter;
import com.example.hutsadmin.adapters.UserAdapter2;
import com.example.hutsadmin.databinding.ActivityMessegerBinding;
import com.example.hutsadmin.models.ActiveOrderUsers;
import com.example.hutsadmin.models.Senders;
import com.example.hutsadmin.models.UsersDetail;
import com.example.hutsadmin.utils.InternetChecker;
import com.example.hutsadmin.utils.NetworkChanger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class MessegerActivity extends AppCompatActivity {


    private ActivityMessegerBinding binding;
    private DatabaseReference databaseReference;


    private UserAdapter2 userAdapter;
    private ArrayList<Senders> usersDetailArrayList;
    private ArrayList<Senders> filteredArraylist;

    private ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessegerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//
        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();
        setupSearchView();
        progressDialog = new ProgressDialog(MessegerActivity.this);
        progressDialog.setMessage("Fetching user.....");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        usersDetailArrayList = new ArrayList<>();
        filteredArraylist = new ArrayList<>();
        usersDetailArrayList.clear();
        filteredArraylist.clear();
        databaseReference = FirebaseDatabase.getInstance().getReference("Sender");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressDialog.dismiss();
                    usersDetailArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Senders usersDetail = dataSnapshot.getValue(Senders.class);
                        usersDetailArrayList.add(usersDetail);
                    }


                    userAdapter = new UserAdapter2(MessegerActivity.this, usersDetailArrayList);
                    binding.messsger.setAdapter(userAdapter);
                    binding.messsger.setLayoutManager(new LinearLayoutManager(MessegerActivity.this));
                    userAdapter.notifyDataSetChanged();



                } else {

                    progressDialog.dismiss();
                    Toast.makeText(MessegerActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                progressDialog.dismiss();
                Toast.makeText(MessegerActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    private void setupSearchView() {
        binding.serachView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText); // Call method to filter users
                return true;
            }
        });
    }
    private void filterUsers(String query) {
        ArrayList<Senders> filteredList = new ArrayList<>();

        for (Senders user : usersDetailArrayList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }

        if (query.isEmpty()) {
            // If the query is empty, show all users
            userAdapter.setFilteredUsers(usersDetailArrayList);
            userAdapter.notifyDataSetChanged();
        } else {
            userAdapter.setFilteredUsers(filteredList);
            userAdapter.notifyDataSetChanged();

        }
    }



    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(MessegerActivity.this);
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