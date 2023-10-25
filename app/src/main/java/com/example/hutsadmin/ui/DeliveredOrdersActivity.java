package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ActivityDeliveredOrdersBinding;
import com.example.hutsadmin.fragments.adapters.CancelAdpter;
import com.example.hutsadmin.fragments.adapters.DeliveredOrdersAdapter;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;
import com.example.hutsadmin.utils.GetDateTime;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliveredOrdersActivity extends AppCompatActivity {

    private ActivityDeliveredOrdersBinding binding;
    private ArrayList<OrderData> activeOrdersList = new ArrayList<>();
    private ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    private DatabaseReference ordersRef;

    private DeliveredOrdersAdapter deliveredOrdersAdapter;

    private ProgressDialog progressDialog;
    private GetDateTime getDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveredOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching cancel order ");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getDateTime = new GetDateTime(this);


        String userId = getIntent().getStringExtra("userId");
        String name = getIntent().getStringExtra("name");


        binding.textView7.setText(name);
        binding.count.setText("0");

        ordersRef = FirebaseDatabase.getInstance().getReference("DeliverdOrders").child(userId);


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
                    Toast.makeText(DeliveredOrdersActivity.this, "data is exist", Toast.LENGTH_SHORT).show();

                    for (DataSnapshot snapshot1 :snapshot.getChildren())
                    {

                        OrderData orderData = snapshot1.getValue(OrderData.class);
                        activeOrdersList.add(orderData);
                    }



                    deliveredOrdersAdapter = new DeliveredOrdersAdapter(DeliveredOrdersActivity.this,activeOrdersList);
                    binding.cancelRecycerl.setAdapter(deliveredOrdersAdapter);
                    binding.cancelRecycerl.setLayoutManager(new LinearLayoutManager(DeliveredOrdersActivity.this));
                    deliveredOrdersAdapter.notifyDataSetChanged();

                    int itemCount = activeOrdersList.size();
                    binding.count.setText(String.valueOf(itemCount));
//
                }
                else {
                    progressDialog.dismiss();


                    Toast.makeText(DeliveredOrdersActivity.this, " no data", Toast.LENGTH_SHORT).show();
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

        getDateTime.getCurrentDateTime(new GetDateTime.TimeCallBack() {
            @Override
            public void getDateTime(String date, String time) {

                String[] timeParts = time.split(":");
                int hours = Integer.parseInt(timeParts[0]);


                if (hours == 2) {
                    deleteAllChats();
                }

            }
        });


    }

    private void deleteAllChats() {
        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("DeliverdOrders ");

        chatReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Chats deleted successfully
                        Toast.makeText(DeliveredOrdersActivity.this, "All Orders deleted", Toast.LENGTH_SHORT).show();
                        // You can also update your UI or perform any other necessary actions.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete chats
                        Toast.makeText(DeliveredOrdersActivity.this, "Failed to delete Order", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}