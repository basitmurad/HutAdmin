package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityCancelOrdersDetailBinding;
import com.example.hutsadmin.fragments.adapters.CancelAdapter;
import com.example.hutsadmin.models.OrderData;
import com.google.android.gms.common.FirstPartyScopes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CancelOrdersDetailActivity extends AppCompatActivity {

    private ActivityCancelOrdersDetailBinding binding;
    private DatabaseReference cancelOrdersRef;
    private ArrayList<OrderData> cancelOrdersList;
    private CancelAdapter cancelAdapter;
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
//        Toast.makeText(this, "" + userId  + name, Toast.LENGTH_SHORT).show();

        binding.textView7.setText(name);


        cancelOrdersRef = FirebaseDatabase.getInstance().getReference("CancelOrders").child(userId);

        cancelOrdersList = new ArrayList<>();

        cancelOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    cancelOrdersList.clear();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        OrderData orderData = dataSnapshot.getValue(OrderData.class);

                        cancelOrdersList.add(orderData);
                    }


                    cancelAdapter = new CancelAdapter(CancelOrdersDetailActivity.this, cancelOrdersList);
                    binding.cancelRecycerl.setAdapter(cancelAdapter);
                    binding.cancelRecycerl.setLayoutManager(new LinearLayoutManager(CancelOrdersDetailActivity.this));

                    int itemCount = cancelOrdersList.size();

                    binding.count.setText(String.valueOf(itemCount));
                    progressDialog.dismiss();
                    progressDialog.dismiss();
//                    Toast.makeText(CancelOrdersDetailActivity.this, "exist", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(CancelOrdersDetailActivity.this, "No Cancel order ", Toast.LENGTH_SHORT).show();

                }

                if (cancelOrdersList.isEmpty())
                {
                    progressDialog.dismiss();
                    binding.count.setText("0");
                }

                // Now you have the list of cancel orders for the user, you can display it
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // Handle error
                Toast.makeText(CancelOrdersDetailActivity.this, "error " +error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}