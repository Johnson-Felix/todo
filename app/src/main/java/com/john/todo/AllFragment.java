package com.john.todo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AllFragment extends Fragment {

    String UID;

    FloatingActionButton fab;


    public AllFragment() {

    }


    public static AllFragment newInstance(String param1, String param2) {
        AllFragment fragment = new AllFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UID = getArguments().getString("UID");
        View v= inflater.inflate(R.layout.fragment_all, container, false);
        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        return v;
    }

    public void onClick(View v) {

    }
}