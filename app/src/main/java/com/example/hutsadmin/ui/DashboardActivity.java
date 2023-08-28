package com.example.hutsadmin.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.hutsadmin.R;
import com.example.hutsadmin.SessionManager;
import com.example.hutsadmin.databinding.ActivityDashboardBinding;
import com.example.hutsadmin.fragments.ActiveOrdersFragment;
import com.example.hutsadmin.fragments.CancelOrdersFragment;
import com.example.hutsadmin.fragments.DeliverFragment;
import com.example.hutsadmin.fragments.adapters.MyFragmentAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private MyFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding   = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);

        fragmentAdapter = new MyFragmentAdapter(this);
        viewPager.setAdapter(fragmentAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Active Orders");
                            break;
                        case 1:
                            tab.setText("Cancel Orders");
                            break;
                        case 2:
                            tab.setText("Deliver Orders");
                            break;
                    }
                }
        );
        tabLayoutMediator.attach();










    }

    private static class MyFragmentAdapter extends FragmentStateAdapter {

        MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ActiveOrdersFragment();
                case 1:
                    return new CancelOrdersFragment();
                case 2:
                    return new DeliverFragment();
                default:
                    throw new IllegalArgumentException("Invalid tab position: " + position);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}