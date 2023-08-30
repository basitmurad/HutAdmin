package com.example.hutsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.models.OrderDetails;

import java.util.ArrayList;


public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyHolder> {
    private Context context;
    private ArrayList<OrderDetails> orderDetailsArrayList;

    public ChildAdapter(Context context, ArrayList<OrderDetails> orderDetailsArrayList) {
        this.context = context;
        this.orderDetailsArrayList = orderDetailsArrayList;
    }

    @NonNull
    @Override
    public ChildAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist_child,parent,false);

        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.MyHolder holder, int position) {
        OrderDetails orderDetails = orderDetailsArrayList.get(position);
        holder.itemName.setText(orderDetails.getName());
        holder.itemPrice.setText( "Rs " +String.valueOf(orderDetails.getNewPrice()));
        holder.itemQ.setText( "Quantity " +String.valueOf(orderDetails.getQuantity()));
//        holder.itemName.setText(orderDetails.getName());
////        holder.itemPrice.setText(orderDetails.getNewPrice());
//        holder.itemQuantity.setText(orderDetails.getNewPrice());

    }

    @Override
    public int getItemCount() {

        if (orderDetailsArrayList != null) {
            return orderDetailsArrayList.size();
        }
        return 0;    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView itemName , itemQ;
        TextView itemPrice;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.tvItemName);

            itemPrice = itemView.findViewById(R.id.tv1);
            itemQ = itemView.findViewById(R.id.tvqtychild);
        }
    }
}
