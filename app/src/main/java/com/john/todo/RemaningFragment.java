package com.john.todo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RemaningFragment extends Fragment {



    public RemaningFragment() {
        // Required empty public constructor
    }


    public static RemaningFragment newInstance(String param1, String param2) {
        RemaningFragment fragment = new RemaningFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remaning, container, false);
    }
}