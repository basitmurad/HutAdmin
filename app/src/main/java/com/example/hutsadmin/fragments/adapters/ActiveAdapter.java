package com.example.hutsadmin.fragments.adapters;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.models.OrderData;

import java.util.ArrayList;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.Myholder> {
    private Context context ;
    private ArrayList<OrderData> orderDataArrayList;

    public ActiveAdapter(Context context, ArrayList<OrderData> orderDataArrayList) {
        this.context = context;
        this.orderDataArrayList = orderDataArrayList;
    }

    @NonNull
    @Override
    public ActiveAdapter.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveAdapter.Myholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Myholder extends RecyclerView.ViewHolder {
        public Myholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
