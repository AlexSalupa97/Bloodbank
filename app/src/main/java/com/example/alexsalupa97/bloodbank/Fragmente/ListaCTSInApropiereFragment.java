package com.example.alexsalupa97.bloodbank.Fragmente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexsalupa97.bloodbank.R;

public class ListaCTSInApropiereFragment extends Fragment {

    View rootView;

    public ListaCTSInApropiereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cts_apropiate, container, false);
        return rootView;
    }
}
