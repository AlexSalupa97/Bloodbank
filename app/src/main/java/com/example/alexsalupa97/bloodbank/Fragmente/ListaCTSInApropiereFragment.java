package com.example.alexsalupa97.bloodbank.Fragmente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alexsalupa97.bloodbank.Activitati.DetaliiCTSActivity;
import com.example.alexsalupa97.bloodbank.Adaptoare.AdaptorLVCTS;
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;

public class ListaCTSInApropiereFragment extends Fragment {

    View rootView;

    AdaptorLVCTS adaptor;
    ListView listView;



    public ListaCTSInApropiereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cts_apropiate, container, false);

        ArrayList<CTS> listaCTSInApropiere=new ArrayList<>();

        for(CTS c:Utile.CTS)
            if(c.getOras().getJudet().equals(Utile.preluareJudet(getActivity())))
                listaCTSInApropiere.add(c);

        adaptor = new AdaptorLVCTS(getActivity(), listaCTSInApropiere);
        listView = (ListView) rootView.findViewById(R.id.lvCentreApropiate);
        listView.setAdapter(adaptor);


        return rootView;
    }
}
