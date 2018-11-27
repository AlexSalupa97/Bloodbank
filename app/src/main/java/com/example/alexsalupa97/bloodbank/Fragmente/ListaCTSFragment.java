package com.example.alexsalupa97.bloodbank.Fragmente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alexsalupa97.bloodbank.Adaptoare.AdaptorLVCTS;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

public class ListaCTSFragment extends Fragment {
    View rootView;

    AdaptorLVCTS adaptor;
    ListView listView;

    public ListaCTSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cts_all, container, false);
        adaptor = new AdaptorLVCTS(getActivity(), Utile.CTS);
        listView = (ListView) rootView.findViewById(R.id.lvCentreAll);
        listView.setAdapter(adaptor);
        return rootView;
    }
}
