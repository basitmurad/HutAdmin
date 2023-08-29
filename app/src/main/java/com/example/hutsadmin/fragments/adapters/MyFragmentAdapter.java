package com.example.hutsadmin.fragments.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hutsadmin.fragments.ActiveOrdersFragment;
import com.example.hutsadmin.fragments.CancelOrdersFragment;
import com.example.hutsadmin.fragments.DeliverFragment;

public class MyFragmentAdapter extends FragmentStateAdapter {
    public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
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
