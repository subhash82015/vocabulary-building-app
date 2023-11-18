package com.demo.collegeerp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.collegeerp.R;
import com.demo.collegeerp.databinding.FragmentBusListBinding;
import com.demo.collegeerp.databinding.FragmentUserListBinding;


public class BusListFragment extends Fragment {
    FragmentBusListBinding binding;

    public BusListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBusListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}