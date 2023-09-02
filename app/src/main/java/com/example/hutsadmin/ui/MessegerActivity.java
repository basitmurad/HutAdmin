package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hutsadmin.adapters.UserAdapter;
import com.example.hutsadmin.adapters.UserAdapter2;
import com.example.hutsadmin.databinding.ActivityMessegerBinding;
import com.example.hutsadmin.models.ActiveOrderUsers;
import com.example.hutsadmin.models.UsersDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessegerActivity extends AppCompatActivity {


    private ActivityMessegerBinding binding;
    private DatabaseReference databaseReference;





    private UserAdapter2 userAdapter ;
    private ArrayList<UsersDetail> usersDetailArrayList;
    private ArrayList<UsersDetail> filteredArraylist;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessegerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(MessegerActivity.this);
        progressDialog.setMessage("Fetching user.....");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();



        usersDetailArrayList = new ArrayList<>();
        filteredArraylist = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("UsersDetail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usersDetailArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UsersDetail usersDetail = dataSnapshot.getValue(UsersDetail.class);
                        usersDetailArrayList.add(usersDetail);
                    }



                    userAdapter = new UserAdapter2(MessegerActivity.this,usersDetailArrayList);
                    binding.messsger.setAdapter(userAdapter);
                    binding.messsger.setLayoutManager(new LinearLayoutManager(MessegerActivity.this));
                    userAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();


                } else {

                    Toast.makeText(MessegerActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(MessegerActivity.this, "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupSearchView();


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

        filteredArraylist.clear();
        for (UsersDetail user : usersDetailArrayList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredArraylist.add(user);
            }
        }


userAdapter.setFilteredUsers(filteredArraylist);



    }

}