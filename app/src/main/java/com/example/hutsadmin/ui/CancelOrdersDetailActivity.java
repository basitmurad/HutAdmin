package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.databinding.ActivityCancelOrdersDetailBinding;
import com.example.hutsadmin.fragments.adapters.CancelAdpter;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelOrdersDetailActivity extends AppCompatActivity {

    private ActivityCancelOrdersDetailBinding binding;
    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;

    private CancelAdpter cancelAdpter;

    private ProgressDialog progressDialog;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCancelOrdersDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching cancel order ");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String userId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");


        binding.textView7.setText(name);
        binding.count.setText("0");

        ordersRef = FirebaseDatabase.getInstance().getReference("CancelOrders").child(userId);


        activeOrdersList = new ArrayList<>();
        orderDetailsArrayList   =new ArrayList<>();


        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                {
                    activeOrdersList.clear();
                    orderDetailsArrayList.clear();

                    progressDialog.dismiss();
                    Toast.makeText(CancelOrdersDetailActivity.this, "data is exist", Toast.LENGTH_SHORT).show();

                    for (DataSnapshot snapshot1 :snapshot.getChildren())
                    {

                        OrderData orderData = snapshot1.getValue(OrderData.class);
                        activeOrdersList.add(orderData);
                    }


                    cancelAdpter = new CancelAdpter(CancelOrdersDetailActivity.this,activeOrdersList);
                    binding.cancelRecycerl.setAdapter(cancelAdpter);
                    binding.cancelRecycerl.setLayoutManager(new LinearLayoutManager(CancelOrdersDetailActivity.this));
                    cancelAdpter.notifyDataSetChanged();

                    int itemCount = activeOrdersList.size();
                    binding.count.setText(String.valueOf(itemCount));
//
                }
                else {
                    progressDialog.dismiss();


                    Toast.makeText(CancelOrdersDetailActivity.this, " no data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
                Log.d("Exception" , "error");
            }
        });

        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
//
//        cancelOrdersRef = FirebaseDatabase.getInstance().getReference("CancelOrders").child(userId);
//
//        cancelOrdersList = new ArrayList<>();
//
//        cancelOrdersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists())
//                {
//                    cancelOrdersList.clear();
//
//
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
//                    {
//                        OrderData orderData = dataSnapshot.getValue(OrderData.class);
//
//                        cancelOrdersList.add(orderData);
//                    }
//
//
//                    cancelAdapter = new CancelAdapter(CancelOrdersDetailActivity.this, cancelOrdersList);
//                    binding.cancelRecycerl.setAdapter(cancelAdapter);
//                    binding.cancelRecycerl.setLayoutManager(new LinearLayoutManager(CancelOrdersDetailActivity.this));
//
//                    int itemCount = cancelOrdersList.size();
//
//                    binding.count.setText(String.valueOf(itemCount));
//                    progressDialog.dismiss();
//                    progressDialog.dismiss();
////                    Toast.makeText(CancelOrdersDetailActivity.this, "exist", Toast.LENGTH_SHORT).show();
//                }
//                else {
//
//                    Toast.makeText(CancelOrdersDetailActivity.this, "No Cancel order ", Toast.LENGTH_SHORT).show();
//
//                }
//
//                if (cancelOrdersList.isEmpty())
//                {
//                    progressDialog.dismiss();
//                    binding.count.setText("0");
//                }
//
//                // Now you have the list of cancel orders for the user, you can display it
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                // Handle error
//                Toast.makeText(CancelOrdersDetailActivity.this, "error " +error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
}