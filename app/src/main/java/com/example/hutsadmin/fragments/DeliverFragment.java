package com.example.hutsadmin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hutsadmin.R;

public class DeliverFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_deliver, container, false);

        Toast.makeText(getActivity(), "deliver Fragments", Toast.LENGTH_SHORT).show();

        return  view;
    }
}