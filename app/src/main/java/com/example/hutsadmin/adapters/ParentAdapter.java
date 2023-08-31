package com.example.hutsadmin.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hutsadmin.R;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.MyHolder> {


    private Context context;
    private ArrayList<OrderData> orderDataArrayList;
    private ArrayList<OrderDetails> orderDetailsArrayList;
    private boolean[] isChildVisible;
    private DatabaseReference databaseReferenceActive, databaseReferenceCancel,databaseReferenceDelivered;

    public ParentAdapter(Context context, ArrayList<OrderData> orderDataArrayList) {
        this.context = context;
        this.orderDataArrayList = orderDataArrayList;

        isChildVisible = new boolean[orderDataArrayList.size()];
        this.databaseReferenceActive = FirebaseDatabase.getInstance().getReference("ActiveOrders");
        this.databaseReferenceCancel = FirebaseDatabase.getInstance().getReference("CancelOrders");
        this.databaseReferenceDelivered = FirebaseDatabase.getInstance().getReference("DeliverdOrders");
    }


    @NonNull
    @Override
    public ParentAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, parent, false);

        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ParentAdapter.MyHolder holder, int position) {


        OrderData orderData = orderDataArrayList.get(position);

        holder.orderId.setText(orderData.getOrderId());
        holder.hutName.setText(orderData.getHutName());
        holder.totalPrice.setText("Total Rs. " + String.valueOf(orderData.getTotalPrice()));

        orderData.getOrderDetailsList();


        ChildAdapter adapter = new ChildAdapter(context, orderData.getOrderDetailsList());


        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChildVisible[position] = !isChildVisible[position]; // Toggle the flag for this position

                if (isChildVisible[position]) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.recyclerView.setVisibility(View.GONE);
                }
            }
        });


        holder.recyclerView.setVisibility(isChildVisible[position] ? View.VISIBLE : View.GONE);



//        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final int clickedPosition = holder.getAdapterPosition();
//
//                if (clickedPosition != RecyclerView.NO_POSITION) {
//                    final OrderData orderData = orderDataArrayList.get(clickedPosition);
//                    orderData.setActive(false);
//
//                    databaseReferenceActive.child(orderData.getUserId()).child(orderData.getPushId())
//                            .removeValue()
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    // Move the item to the delivered section
//                                    databaseReferenceDelivered.child(orderData.getUserId())
//                                            .child(orderData.getPushId())
//                                            .setValue(orderData)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    // Remove the item locally
//                                                    orderDataArrayList.remove(clickedPosition);
//                                                    notifyItemRemoved(clickedPosition);
//
//                                                    // Show toast and potentially update the UI
//
//                                                    // Now, remove the item from the user's orders
//                                                    databaseReferenceDelivered.child(orderData.getUserId())
//                                                            .child(orderData.getPushId())
//                                                            .removeValue()
//                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                @Override
//                                                                public void onSuccess(Void unused) {
//                                                                    Toast.makeText(context, "Item deleted from user's side", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            })
//                                                            .addOnFailureListener(new OnFailureListener() {
//                                                                @Override
//                                                                public void onFailure(@NonNull Exception e) {
//                                                                    Toast.makeText(context, "Failed to delete item from user's side: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            });
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Toast.makeText(context, "Error moving item: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(context, "Error deleting item: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                } else {
//                    Log.e("Adapter", "Invalid position for click event");
//                }
//            }
//        });


        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position1  =holder.getAdapterPosition();
                orderData.setActive(false);


                orderDataArrayList.remove(position1);
                notifyItemRemoved(position1);

                databaseReferenceActive.child(orderData.getUserId()).child(orderData.getPushId())
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                databaseReferenceDelivered.child(orderData.getUserId())
                                        .child(orderData.getPushId())
                                        .setValue(orderData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {


                                                // Now, remove the item from the user's orders
                                                databaseReferenceActive.child(orderData.getUserId())
                                                        .child(orderData.getPushId())
                                                        .removeValue()
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(context, "Item deleted from user's side", Toast.LENGTH_SHORT).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(context, "Failed to delete item from user's side: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDataArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView orderId, hutName, totalPrice, btnComplete;
        RecyclerView recyclerView;
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            hutName = itemView.findViewById(R.id.hutName);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            imageView = itemView.findViewById(R.id.btnOpen);
            btnComplete = itemView.findViewById(R.id.btnCancelOrders);

            recyclerView = itemView.findViewById(R.id.bnm);
        }
    }



}
