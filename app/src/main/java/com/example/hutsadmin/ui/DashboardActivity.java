package com.example.hutsadmin.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.example.hutsadmin.fragments.adapters.MyFragmentAdapter;

import com.google.android.material.tabs.TabLayout;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;


    private MyFragmentAdapter myFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding   = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




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














    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}