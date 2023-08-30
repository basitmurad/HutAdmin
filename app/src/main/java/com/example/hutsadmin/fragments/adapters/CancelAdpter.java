package com.example.hutsadmin.fragments.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.adapters.ChildAdapter;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CancelAdpter extends RecyclerView.Adapter<CancelAdpter.MyHolder> {
    private Context context;
    private ArrayList<OrderData> orderDataArrayList;
    private ArrayList<OrderDetails> orderDetailsArrayList;
    private DatabaseReference databaseReferenceCancel;
    private boolean[] isChildVisible;
    public CancelAdpter(Context context, ArrayList<OrderData> orderDataArrayList) {
        this.context = context;
        this.orderDataArrayList = orderDataArrayList;

        isChildVisible = new boolean[orderDataArrayList.size()];
        databaseReferenceCancel = FirebaseDatabase.getInstance().getReference("CancelOrders");
    }

    @NonNull
    @Override
    public CancelAdpter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cancelitem_layout,parent,false);

        return new MyHolder(view);
    }

    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull CancelAdpter.MyHolder holder,  int position) {

        OrderData orderData = orderDataArrayList.get(position);

        holder.orderId.setText(String.valueOf(orderData.getOrderId()));
        holder.hutName.setText(orderData.getHutName());
        holder.totalPrice.setText(String.valueOf(orderData.getTotalPrice()));
        orderData.getOrderDetailsList();


        ChildAdapter adapter = new ChildAdapter(context, orderData.getOrderDetailsList());


        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        holder.btnOpenCancel.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return orderDataArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView orderId  , hutName, totalPrice;
        ImageView btnOpenCancel;
        RecyclerView recyclerView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);


            orderId = itemView.findViewById(R.id.orderCancelId);
            hutName =itemView.findViewById(R.id.hutCancelName);
            totalPrice = itemView.findViewById(R.id.totalCancelPrice);
            btnOpenCancel = itemView.findViewById(R.id.btnOpenCancel);
            recyclerView = itemView.findViewById(R.id.bnm);

        }
    }
}
