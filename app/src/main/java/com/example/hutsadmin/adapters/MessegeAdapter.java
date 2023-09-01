package com.example.hutsadmin.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.ReceiverLayoutBinding;
import com.example.hutsadmin.databinding.SenderLayoutBinding;
import com.example.hutsadmin.models.MessegeDetails;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessegeAdapter extends RecyclerView.Adapter {

    private Context context;
    ArrayList<MessegeDetails> list;
    final int sendItem = 1;
    final int receiveitem = 2;
    String senderRoom;
    String receiverRoom;


    DatabaseReference databaseReference;



    public MessegeAdapter(Context context, ArrayList<MessegeDetails> list, String senderRoom, String receiverRoom) {
        this.context = context;
        this.list = list;
        this.senderRoom = senderRoom;
        this.receiverRoom = receiverRoom;
        databaseReference = FirebaseDatabase.getInstance().getReference("chats");



    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == sendItem) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new SentViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_layout, parent, false);
            return new ReceiceViewHolder(view);
        }

    }


    @Override
    public int getItemViewType(int position) {


        MessegeDetails messegeDetails = list.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(messegeDetails.getSenderId())) {
            return sendItem;
        } else {
            return receiveitem;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessegeDetails messegeDetails = list.get(position);





        databaseReference
                .child(senderRoom)
                .child("messeges")
                .child(messegeDetails.getPushId()).setValue(messegeDetails);


        databaseReference
                .child(receiverRoom)
                .child("messeges")
                .child(messegeDetails.getPushId()).setValue(messegeDetails);







    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {


        SenderLayoutBinding binding;


        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SenderLayoutBinding.bind(itemView);
        }
    }

    public class ReceiceViewHolder extends RecyclerView.ViewHolder {

        ReceiverLayoutBinding binding;

        public ReceiceViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReceiverLayoutBinding.bind(itemView);
        }
    }
}
