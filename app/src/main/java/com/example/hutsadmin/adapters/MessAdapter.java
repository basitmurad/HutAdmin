package com.example.hutsadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.models.MessegeDetails;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.MyHolder> {
    private Context context;
    private ArrayList<MessegeDetails > messegeDetailsArrayList;

    public MessAdapter(Context context, ArrayList<MessegeDetails> messegeDetailsArrayList) {
        this.context = context;
        this.messegeDetailsArrayList = messegeDetailsArrayList;
    }

    public void clear()
    {
        messegeDetailsArrayList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MessAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout,parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessAdapter.MyHolder holder, int position) {

        MessegeDetails messegeDetails = messegeDetailsArrayList.get(position);

        holder.textView.setText(messegeDetails.getMessege());

        if (messegeDetails.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
        {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.textView.setTextColor(context.getResources().getColor(R.color.white));
        }

        else {
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));


        }


    }

    @Override
    public int getItemCount() {
        return messegeDetailsArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;
        LinearLayout layout ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.messege123);
            layout = itemView.findViewById(R.id.linear123);
        }
    }
}
