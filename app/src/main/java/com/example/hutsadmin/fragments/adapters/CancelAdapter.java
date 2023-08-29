package com.example.hutsadmin.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.models.OrderData;
import com.example.hutsadmin.models.OrderDetails;

import java.util.ArrayList;

public class CancelAdapter extends RecyclerView.Adapter<CancelAdapter.MyHolder> {
    private Context context;
    private ArrayList<OrderData> orderDataArrayList;
    private String name;

    public CancelAdapter(Context context, ArrayList<OrderData> orderDataArrayList) {
        this.context = context;
        this.orderDataArrayList = orderDataArrayList;
    }

    @NonNull
    @Override
    public CancelAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancelitem_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelAdapter.MyHolder holder, int position) {
        OrderData orderData1 = orderDataArrayList.get(position);

        holder.t3.setText("Huts : " + orderData1.getHutName());
        holder.t2.setText("Total Price : " + String.valueOf(orderData1.getTotalPrice()));
//

        for (OrderDetails orderDetails : orderData1.getOrderDetailsList()) {


            name += orderDetails.getName() + " , ";

        }
        holder.t1.setText(name);
        name = "";

    }

    @Override
    public int getItemCount() {
        return orderDataArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView t1, t2, t3;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.cancelItemName);
            t2 = itemView.findViewById(R.id.cancelItemHutName);
            t3 = itemView.findViewById(R.id.cancelItemPrice);
        }
    }
}
