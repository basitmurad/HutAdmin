
package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.example.hutsadmin.databinding.ActivityDetailsBinding;
import com.example.hutsadmin.fragments.adapters.ActiveAdapter;
import com.example.hutsadmin.fragments.adapters.CancelAdapter;
import com.example.hutsadmin.models.OrderData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private DatabaseReference activeOrdersRef;
    private ArrayList<OrderData> activeOrderArrayList;
    private ActiveAdapter activeAdapter;
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


        String userId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");
//        Toast.makeText(this, "" + userId  + name, Toast.LENGTH_SHORT).show();

        binding.textView7.setText(name);


        activeOrdersRef = FirebaseDatabase.getInstance().getReference("ActiveOrders").child(userId);


        activeOrderArrayList= new ArrayList<>();


        activeOrdersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    activeOrderArrayList.clear();


                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        OrderData orderData = dataSnapshot.getValue(OrderData.class);

                        activeOrderArrayList.add(orderData);
                    }



                    activeAdapter = new ActiveAdapter(DetailsActivity.this,activeOrderArrayList);
                    binding.recyclerActive.setAdapter(activeAdapter);
                    binding.recyclerActive.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));

                    int itemCount = activeOrderArrayList.size();

                    binding.count.setText(String.valueOf(itemCount));
                    progressDialog.dismiss();
                    progressDialog.dismiss();
//                    Toast.makeText(CancelOrdersDetailActivity.this, "exist", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(DetailsActivity.this, "No Cancel order ", Toast.LENGTH_SHORT).show();

                }

                if (activeOrderArrayList.isEmpty())
                {
                    progressDialog.dismiss();
                    binding.count.setText("0");
                }

                // Now you have the list of cancel orders for the user, you can display it
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // Handle error
                Toast.makeText(DetailsActivity.this, "error " +error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}