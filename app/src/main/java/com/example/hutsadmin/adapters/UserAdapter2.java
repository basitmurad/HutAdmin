package com.example.hutsadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.MessegeDetailActivity;

import com.example.hutsadmin.R;
import com.example.hutsadmin.models.Senders;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.MyHolder> {
    private Context context;
    private ArrayList<Senders> usersDetailArrayList;

    private DatabaseReference databaseReference;

    public UserAdapter2(Context context, ArrayList<Senders> usersDetailArrayList) {
        this.context = context;
        this.usersDetailArrayList = usersDetailArrayList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Sender");
    }

    @NonNull
    @Override
    public UserAdapter2.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_layout2,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter2.MyHolder holder, int position) {

        Senders usersDetail = usersDetailArrayList.get(position);

        holder.name.setText(String.valueOf(usersDetail.getName()));
        holder.email.setText(usersDetail.getEmail());



        String nm = usersDetail.getNumber();
        Date date = new Date(usersDetail.getTimeSender());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());


        String formattedTime = sdf.format(date);


        holder.textViewTime.setText(String.valueOf(formattedTime));

//        Toast.makeText(context, "time is"+formattedTime, Toast.LENGTH_SHORT).show();

        final boolean[] isRead = {usersDetail.isRead()};
        if (isRead[0] ==true)
        {
            holder.imageView.setVisibility(View.VISIBLE);
        }
        if (isRead[0] ==false)
        {
            holder.imageView.setVisibility(View.INVISIBLE);
        }




        String userId = usersDetail.getUserId();
        String userToken  = usersDetail.getFcmToken();
        String userName  =usersDetail.getName();


//        Toast.makeText(context, ""+userToken, Toast.LENGTH_SHORT).show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                isRead[0] = false;


                databaseReference.child(userId).child("read").setValue(false); // Update the "read" status in Firebase

                Intent intent = new Intent(context, MessegeDetailActivity.class);

                intent.putExtra("id" , userId);
                intent.putExtra("token", userToken);
                intent.putExtra("no", nm);
                intent.putExtra("name",userName);



                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersDetailArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name , email , textViewTime;
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName121);
            email = itemView.findViewById(R.id.textViewEmail121);
            imageView = itemView.findViewById(R.id.notifcatio);
            textViewTime = itemView.findViewById(R.id.sendertime);
        }
    }

    public void setFilteredUsers(ArrayList<Senders> filteredUsers) {

        usersDetailArrayList = filteredUsers;
        notifyDataSetChanged();
    }
}
