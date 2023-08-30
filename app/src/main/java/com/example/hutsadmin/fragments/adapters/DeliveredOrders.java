package com.example.hutsadmin.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.models.OrderData;

import java.util.ArrayList;

public class DeliveredOrders extends RecyclerView.Adapter<DeliveredOrders.MyHolder> {
    private Context context;
    private ArrayList<OrderData> orderDataArrayList;

    public DeliveredOrders(Context context, ArrayList<OrderData> orderDataArrayList) {
        this.context = context;
        this.orderDataArrayList = orderDataArrayList;
    }

    @NonNull
    @Override
    public DeliveredOrders.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.deliveritem_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredOrders.MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderDataArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView deliOrderId, deliOrderHut,deliOrderPrice;
        ImageView btnDeliverd;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
