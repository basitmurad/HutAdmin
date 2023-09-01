
package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.adapters.ParentAdapter;
import com.example.hutsadmin.databinding.ActivityDetailsBinding;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private DatabaseReference activeOrdersRef;
    private ArrayList<OrderData> activeOrdersList;
    private ArrayList<OrderDetails> orderDetailsArrayList;
    private ParentAdapter parentAdapter;
    private String  number;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching cancel order ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        binding.count.setText("0");


        String userId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");
         number  = getIntent().getStringExtra("no");

        activeOrdersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders").child(userId);
        binding.textView7.setText(name);
        activeOrdersList = new ArrayList<>();
        orderDetailsArrayList = new ArrayList<>();

        activeOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {


                    if (snapshot.exists()) {
                        activeOrdersList.clear();
                        orderDetailsArrayList.clear();

                        progressDialog.dismiss();


                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            OrderData orderData = snapshot1.getValue(OrderData.class);
                            if (orderData != null && orderData.isActive()) {
                                activeOrdersList.add(orderData);

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    OrderDetails orderDetails = dataSnapshot.getValue(OrderDetails.class);

                                    orderDetailsArrayList.add(orderDetails);

                                }


                            }

                        }


                        parentAdapter = new ParentAdapter(DetailsActivity.this, activeOrdersList);
                        binding.recyclerActive.setAdapter(parentAdapter);
                        parentAdapter.notifyDataSetChanged();
                        binding.recyclerActive.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));

                        int itemCount = activeOrdersList.size();
                        binding.count.setText(String.valueOf(itemCount));

                    } else {
                        progressDialog.dismiss();
                        Log.d("Exception", "error");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    progressDialog.dismiss();
                    Log.d("Exception", "error");
                    // Toast.makeText(getActivity().getApplicationContext(), " "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Log.d("Exception", "error");
            }
        });

        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                        startActivity(intent);
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (parentAdapter != null) {
            parentAdapter.notifyDataSetChanged();
        }
    }
}