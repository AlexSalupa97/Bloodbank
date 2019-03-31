package ro.alexsalupa97.bloodbank.Fragmente;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.Collections;

import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.Orase;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupDonatorFragment extends Fragment {

    View rootView;

    EditText etNume;
    EditText etPrenume;
    EditText etEmail;
    EditText etTelefon;
    Spinner spOras;
    Spinner spGrupaSanguina;
    Button btnSignup;

    ArrayAdapter<String> adaptorSpOrase;
    ArrayAdapter<String> adaptorSpGrupeSanguine;

    public SignupDonatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup_donator, container, false);

        etNume = (EditText) rootView.findViewById(R.id.etNume);
        etPrenume = (EditText) rootView.findViewById(R.id.etPrenume);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etTelefon = (EditText) rootView.findViewById(R.id.etTelefon);
        spGrupaSanguina = (Spinner) rootView.findViewById(R.id.spGrupaSanguina);
        spOras = (Spinner) rootView.findViewById(R.id.spOras);
        btnSignup = (Button) rootView.findViewById(R.id.btnSignup);


        ArrayList<String> listaOrase = new ArrayList<>();
        for (Orase orase : Utile.orase)
            listaOrase.add(orase.getOras());
        Collections.sort(listaOrase);
        adaptorSpOrase = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaOrase) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        spOras.setAdapter(adaptorSpOrase);

        ArrayList<String> listaGrupeSanguine = new ArrayList<>();
        for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine)
            listaGrupeSanguine.add(grupeSanguine.getGrupaSanguina());
        Collections.sort(listaGrupeSanguine);
        adaptorSpGrupeSanguine = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaGrupeSanguine) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        spGrupaSanguina.setAdapter(adaptorSpGrupeSanguine);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

}
