package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hutsadmin.ui.MessegerActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.example.hutsadmin.MessegeDetailActivity;
import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.example.hutsadmin.fragments.adapters.MyFragmentAdapter;

import com.example.hutsadmin.utils.InternetChecker;
import com.example.hutsadmin.utils.NetworkChanger;
import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;


    private MyFragmentAdapter myFragmentAdapter;
    private BroadcastReceiver broadcastReceiver;
    private NetworkChanger networkChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



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
}