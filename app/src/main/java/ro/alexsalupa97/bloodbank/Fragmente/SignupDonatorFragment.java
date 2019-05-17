package ro.alexsalupa97.bloodbank.Fragmente;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.SecurityPermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ro.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
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

    String fisier = "SharedPreferences";

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

                final Donatori donator = new Donatori();
                donator.setEmailDonator(etEmail.getText().toString());
                for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine) {
                    if (grupeSanguine.getGrupaSanguina().equals(spGrupaSanguina.getSelectedItem().toString()))
                        donator.setGrupaSanguina(grupeSanguine);
                }
                //donator.setGrupaSanguina(new GrupeSanguine(spGrupaSanguina.getSelectedItem().toString()));
                donator.setTelefonDonator(etTelefon.getText().toString());
                for (Orase oras : Utile.orase) {
                    if (oras.getOras().equals(spOras.getSelectedItem().toString()))
                        donator.setOrasDonator(oras);
                }
                donator.setNumeDonator(etNume.getText().toString() + " " + etPrenume.getText().toString());

                String url = Utile.URL + "domain.donatori/";

                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                final JSONObject jsonDonator = new JSONObject();
                final JSONObject jsonOras = new JSONObject();
                final JSONObject jsonGrupaSanguina = new JSONObject();
                try {

                    jsonGrupaSanguina.put("idgrupasanguina", donator.getGrupaSanguina().getGrupaSanguina());

                    jsonOras.put("idoras", donator.getOrasDonator().getIdOras());
                    jsonOras.put("judet", donator.getOrasDonator().getJudet());
                    jsonOras.put("numeoras", donator.getOrasDonator().getOras());

                    jsonDonator.put("emaildonator", donator.getEmailDonator());
                    jsonDonator.put("iddonator", "");
                    jsonDonator.put("idgrupasanguina", jsonGrupaSanguina);
                    jsonDonator.put("idoras", jsonOras);
                    jsonDonator.put("numedonator", donator.getNumeDonator());
                    jsonDonator.put("telefondonator", donator.getTelefonDonator());

                } catch (JSONException e) {

                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, jsonDonator,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("login_name", donator.getNumeDonator());
                                editor.putString("tip_user", "donator");
                                editor.putString("grupaSanguina", donator.getGrupaSanguina().getGrupaSanguina());
                                editor.putString("stareAnalize", "neefectuate");
                                editor.putString("orasUser", donator.getOrasDonator().getOras());
                                editor.putString("judetUser", donator.getOrasDonator().getJudet());
                                editor.putString("email", donator.getEmailDonator());
                                editor.putString("telefon", donator.getTelefonDonator());

                                editor.commit();

                                Utile.firstTimeDonator=true;

                                Intent intent = new Intent(getActivity(), PrimaPaginaActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error.toString().contains("ServerError")) {
                                    Toast.makeText(getActivity(), "Eroare de server", Toast.LENGTH_LONG).show();
                                    Log.d("restresponse", error.toString());
                                }


                            }
                        }) {

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                        try {
                            String json = new String(
                                    response.data,
                                    "UTF-8"
                            );

                            if (json.length() == 0) {
                                return Response.success(
                                        null,
                                        HttpHeaderParser.parseCacheHeaders(response)
                                );
                            } else {
                                return super.parseNetworkResponse(response);
                            }
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        }


                    }
                };
                requestQueue.add(jsonObjReq);

            }


        });

        return rootView;
    }

}
