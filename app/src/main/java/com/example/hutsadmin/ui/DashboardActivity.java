package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hutsadmin.R;
import com.example.hutsadmin.ui.MessegerActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hutsadmin.MessegeDetailActivity;
import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.example.hutsadmin.fragments.adapters.MyFragmentAdapter;

import com.example.hutsadmin.utils.GetDateTime;
import com.example.hutsadmin.utils.InternetChecker;
import com.example.hutsadmin.utils.NetworkChanger;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    private ProgressDialog progressDialog;


    private MyFragmentAdapter myFragmentAdapter;
    private BroadcastReceiver broadcastReceiver;
    private NetworkChanger networkChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..\n Deleting the previous data");


        binding.naview.setNavigationItemSelectedListener(item -> {
            // Handle navigation item clicks here
            int itemId = item.getItemId();

            if (itemId == R.id.nav_chats) {

                progressDialog.show();


                DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("chats");


                chatReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Chats deleted successfully
                                progressDialog.dismiss();
//                                Toast.makeText(DashboardActivity.this, "All chats deleted", Toast.LENGTH_SHORT).show();
                                // You can also update your UI or perform any other necessary actions.
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete chats
                                Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            if (itemId == R.id.nav_DeliveredOrder) {

                progressDialog.show();


                DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("DeliverdOrders");


                chatReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Chats deleted successfully
                                progressDialog.dismiss();
//                                Toast.makeText(DashboardActivity.this, "Delivered Orders deleted", Toast.LENGTH_SHORT).show();
                                // You can also update your UI or perform any other necessary actions.
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete chats
                                Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            if (itemId == R.id.nav_LatestOrder) {
                progressDialog.show();
                DatabaseReference chatReference1 = FirebaseDatabase.getInstance().getReference("LatestOrders");

                chatReference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

            }
            if (itemId == R.id.nav_myorddersUser) {

                progressDialog.show();


                DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("ActiveOrdersUser");


                chatReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Chats deleted successfully
                                progressDialog.dismiss();
//                                Toast.makeText(DashboardActivity.this, "All Users deleted", Toast.LENGTH_SHORT).show();
                                // You can also update your UI or perform any other necessary actions.
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete chats
                                Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            if (itemId == R.id.nav_CancelOrder) {

                progressDialog.show();


                DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("CancelOrders");


                chatReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Chats deleted successfully
                                progressDialog.dismiss();
//                                Toast.makeText(DashboardActivity.this, "All Users deleted", Toast.LENGTH_SHORT).show();
                                // You can also update your UI or perform any other necessary actions.
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to delete chats
                                Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            if (itemId == R.id.nav_Senders) {
                progressDialog.show();
                DatabaseReference chatReference1 = FirebaseDatabase.getInstance().getReference("Sender");

                chatReference1.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(DashboardActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }


            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


        broadcastReceiver = new NetworkChanger();
        registerNetworkChangeReceiver();


        myFragmentAdapter = new MyFragmentAdapter(DashboardActivity.this);
        binding.viewPager.setAdapter(myFragmentAdapter);
        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tablayout.selectTab(binding.tablayout.getTabAt(position));
            }
        });


        binding.btnOpenChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashboardActivity.this, MessegerActivity.class));

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();

        InternetChecker internetChecker = new InternetChecker(DashboardActivity.this);
        if (!internetChecker.isConnected()) {

            internetChecker.showInternetDialog();
        }
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterNetworkChangeReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChangeReceiver();
    }

    private void deleteAllChats() {
        DatabaseReference chatReference = FirebaseDatabase.getInstance().getReference("ActiveOrdersUser");
        DatabaseReference chat = FirebaseDatabase.getInstance().getReference("chats");

        chat.removeValue();

        chatReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Chats deleted successfully
                        Toast.makeText(DashboardActivity.this, "All Order deleted", Toast.LENGTH_SHORT).show();
                        // You can also update your UI or perform any other necessary actions.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete chats
                        Toast.makeText(DashboardActivity.this, "Failed to delete chats", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}