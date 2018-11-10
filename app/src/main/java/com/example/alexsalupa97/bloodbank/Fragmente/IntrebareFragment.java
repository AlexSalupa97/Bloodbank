package com.example.alexsalupa97.bloodbank.Fragmente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntrebareFragment extends Fragment {


    View rootView;
    TextView textIntrebare;

    public IntrebareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_intrebare, container, false);

        textIntrebare = (TextView) rootView.findViewById(R.id.tvTextIntrebare);

        final Intrebari intrebare = getArguments().getParcelable("intrebare");


        textIntrebare.setText(intrebare.getTextIntrebare());


        return rootView;
    }
}
