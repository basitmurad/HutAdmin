package com.example.hutsadmin.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAssignedNumbers;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.adapters.UserAdapter;
import com.example.hutsadmin.models.ActiveOrderUsers;
import com.example.hutsadmin.models.UsersDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ActiveOrdersFragment extends Fragment {


    private DatabaseReference databaseReference;
    private ArrayList<ActiveOrderUsers> usersDetailArrayList;
    private ArrayList<ActiveOrderUsers> filteredArraylist;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    private UserAdapter userAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_active_orders, container, false);

        recyclerView = view.findViewById(R.id.userRecycler);
        searchView = view.findViewById(R.id.serachView);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching user.....");
        progressDialog.setTitle("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();


        usersDetailArrayList = new ArrayList<>();
        filteredArraylist = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("ActiveOrdersUser");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    usersDetailArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        UsersDetail usersDetail = dataSnapshot.getValue(UsersDetail.class);


                        ActiveOrderUsers activeOrderUsers = dataSnapshot.getValue(ActiveOrderUsers.class);


                        if (activeOrderUsers.getHasOrder().equals(true)) {
                            usersDetailArrayList.add(activeOrderUsers);


                        }

                    }

                    userAdapter = new UserAdapter(requireContext(), usersDetailArrayList, 1);

                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                    userAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();


                } else {

                    Toast.makeText(getActivity(), "No user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                Toast.makeText(getActivity(), "database error" + error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


        setupSearchView();

        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        for (ActiveOrderUsers user : usersDetailArrayList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredArraylist.add(user);
            }
        }


        userAdapter.setUsersDetailArrayList(filteredArraylist);

    }


}