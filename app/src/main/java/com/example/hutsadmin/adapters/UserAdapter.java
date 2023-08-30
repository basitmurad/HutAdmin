package com.example.hutsadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hutsadmin.R;
import com.example.hutsadmin.databinding.UserLayoutBinding;
import com.example.hutsadmin.models.UsersDetail;
import com.example.hutsadmin.ui.CancelOrdersDetailActivity;
import com.example.hutsadmin.ui.DeliveredOrdersActivity;
import com.example.hutsadmin.ui.DetailsActivity;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {
    private Context context;
    private ArrayList<UsersDetail> usersDetailArrayList;

    private int tabIdentifier;


    public UserAdapter(Context context, ArrayList<UsersDetail> usersDetailArrayList, int tabIdentifier) {
        this.context = context;
        this.usersDetailArrayList = usersDetailArrayList;
        this.tabIdentifier = tabIdentifier;
    }

    @NonNull
    @Override
    public UserAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.user_layout,parent ,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyHolder holder, int position) {
        UsersDetail usersDetail  = usersDetailArrayList.get(position);

        holder.textViewEmail.setText(usersDetail.getEmail()); // Fix the typo here


        String name = usersDetail.getName();
        holder.textViewName.setText(name);
        String userId  = usersDetail.getUserId();
//        Toast.makeText(context, ""+name, Toast.LENGTH_SHORT).show();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tabIdentifier==1)
                {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("name" , name);
                    context.startActivity(intent);
                }
                if (tabIdentifier==2)
                {
                    Intent intent = new Intent(context, CancelOrdersDetailActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("name" , name);
                    context.startActivity(intent);
                }
                if (tabIdentifier==3)
                {
                    Intent intent = new Intent(context, DeliveredOrdersActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("name" , name);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersDetailArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView textViewName ;
        private TextView textViewEmail;
        public MyHolder(@NonNull View itemView) {
            super(itemView);


            textViewName = itemView.findViewById(R.id.textViewName12);
            textViewEmail = itemView.findViewById(R.id.textViewEmail12);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
    public void setUsersDetailArrayList(ArrayList<UsersDetail> newList) {
        usersDetailArrayList = newList;
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}
