package com.example.hutsadmin.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAssignedNumbers;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
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
        TextView textView = view.findViewById(R.id.noOrders);
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

//        databaseReference = FirebaseDatabase.getInstance().getReference("ActiveOrdersUser");

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

                    userAdapter = new UserAdapter(getContext(), usersDetailArrayList, 1);
                    recyclerView.setAdapter(userAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    userAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();



                } else {

                    progressDialog.dismiss();
//                    Toast.makeText(getActivity(), "No user", Toast.LENGTH_SHORT).show();

//                   showNoActiveUsersDialog();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


                progressDialog.dismiss();
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
    private void showNoActiveUsersDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("No Active Users");
        builder.setMessage("There are currently no active users.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();

//      new Handler().postDelayed(new Runnable() {
//          @Override
//          public void run() {
//              if (usersDetailArrayList.isEmpty()) {
//                  // If the list is empty, show a dialog
//                  showNoActiveUsersDialog();
//              }
//          }
//      },3000);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}