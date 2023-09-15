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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        if (messegeDetails.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
        { holder.layoutRight.setBackground(context.getResources().getDrawable(R.drawable.back_sender));
            holder.layoutRight.setVisibility(View.VISIBLE);

            holder.layoutLeft.setVisibility(View.GONE);
            holder.rightMess.setText(messegeDetails.getMessege());
            Date date = new Date(messegeDetails.getTimestamp());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());


        String formattedTime = sdf.format(date);

        holder.rightTime.setText(formattedTime);

        }
        else {
            holder.layoutLeft.setBackground(context.getResources().getDrawable(R.drawable.back_receiver));
            holder.layoutLeft.setVisibility(View.VISIBLE);
            holder.layoutRight.setVisibility(View.GONE);
            holder.leftmess.setText(messegeDetails.getMessege());
            Date date = new Date(messegeDetails.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());


            String formattedTime = sdf.format(date);

            holder.leftTime.setText(formattedTime);
        }

    }

    @Override
    public int getItemCount() {
        return messegeDetailsArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView leftmess , leftTime , rightMess, rightTime;
        LinearLayout layoutLeft,layoutRight ;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            layoutLeft = itemView.findViewById(R.id.leftSide);
            layoutRight = itemView.findViewById(R.id.rightSide);
            leftmess = itemView.findViewById(R.id.userSendLeft);
            leftTime = itemView.findViewById(R.id.timeLeft);
            rightMess = itemView.findViewById(R.id.userSendRight);
            rightTime = itemView.findViewById(R.id.timeRight);


        }
    }
}
