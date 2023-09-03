package com.example.hutsadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.MessegeDetailActivity;
import com.example.hutsadmin.R;
import com.example.hutsadmin.models.UsersDetail;

import java.util.ArrayList;

public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.MyHolder> {
    private Context context;
    private ArrayList<UsersDetail> usersDetailArrayList;

    public UserAdapter2(Context context, ArrayList<UsersDetail> usersDetailArrayList) {
        this.context = context;
        this.usersDetailArrayList = usersDetailArrayList;
    }

    @NonNull
    @Override
    public UserAdapter2.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter2.MyHolder holder, int position) {

        UsersDetail usersDetail = usersDetailArrayList.get(position);

        holder.name.setText(String.valueOf(usersDetail.getName()));
        holder.email.setText(usersDetail.getEmail());



        String nm = usersDetail.getNumber();




        String userId = usersDetail.getUserId();
        String userToken  = usersDetail.getUserFcmToken();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName12);
            email = itemView.findViewById(R.id.textViewEmail12);
        }
    }

    public void setFilteredUsers(ArrayList<UsersDetail> filteredUsers) {

        usersDetailArrayList = filteredUsers;
        notifyDataSetChanged();
    }
}
