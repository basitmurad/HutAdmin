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
import com.example.hutsadmin.models.UsersDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



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

        View view = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter2.MyHolder holder, int position) {

        Senders usersDetail = usersDetailArrayList.get(position);

        holder.name.setText(String.valueOf(usersDetail.getName()));
        holder.email.setText(usersDetail.getEmail());



        String nm = usersDetail.getNumber();

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



                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersDetailArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name , email;
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName12);
            email = itemView.findViewById(R.id.textViewEmail12);
            imageView = itemView.findViewById(R.id.notifcatio);
        }
    }

    public void setFilteredUsers(ArrayList<Senders> filteredUsers) {

        usersDetailArrayList = filteredUsers;
        notifyDataSetChanged();
    }
}
