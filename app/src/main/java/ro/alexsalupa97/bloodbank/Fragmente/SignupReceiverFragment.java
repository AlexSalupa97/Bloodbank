package ro.alexsalupa97.bloodbank.Fragmente;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import java.util.ArrayList;
import java.util.Collections;

import ro.alexsalupa97.bloodbank.Activitati.DetaliiReceiverActivity;
import ro.alexsalupa97.bloodbank.Activitati.DetaliiReceiverMainActivity;
import ro.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.Orase;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupReceiverFragment extends Fragment {

    View rootView;

    EditText etNume;
    EditText etPrenume;
    EditText etEmail;
    EditText etTelefon;
    EditText etPass1;
    EditText etPass2;
    Spinner spCTS;
    Spinner spGrupaSanguina;
    Button btnSignup;

    ArrayAdapter<String> adaptorSpCTS;
    ArrayAdapter<String> adaptorSpGrupeSanguine;

    String fisier = "SharedPreferences";

    public SignupReceiverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup_receiver, container, false);

        etNume = (EditText) rootView.findViewById(R.id.etNume);
        etPrenume = (EditText) rootView.findViewById(R.id.etPrenume);
        etEmail = (EditText) rootView.findViewById(R.id.etEmail);
        etTelefon = (EditText) rootView.findViewById(R.id.etTelefon);
        etPass1 = (EditText) rootView.findViewById(R.id.etPass1);
        etPass2 = (EditText) rootView.findViewById(R.id.etPass2);
        spGrupaSanguina = (Spinner) rootView.findViewById(R.id.spGrupaSanguina);
        spCTS = (Spinner) rootView.findViewById(R.id.spCTS);
        btnSignup = (Button) rootView.findViewById(R.id.btnSignup);


        ArrayList<String> listaCTS = new ArrayList<>();
        for (CTS cts : Utile.CTS)
            listaCTS.add(cts.getNumeCTS());
        Collections.sort(listaCTS);
        adaptorSpCTS = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listaCTS) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };
        spCTS.setAdapter(adaptorSpCTS);

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
                if (etNume.getText().toString().length() == 0 || etPrenume.getText().toString().length() == 0) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Numele este necorespunzator", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!isEmailValid(etEmail.getText().toString())) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Email-ul este necorespunzator", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (!etTelefon.getText().toString().matches("[0-9]+") || etTelefon.getText().toString().length() != 10) {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Telefonul este necorespunzator", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (etPass1.getText().toString().equals(etPass2.getText().toString()) && etPass1.getText().toString().length() > 2) {
                    final Receiveri receiver = new Receiveri();
                    receiver.setEmailReceiver(etEmail.getText().toString());
                    for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine) {
                        if (grupeSanguine.getGrupaSanguina().equals(spGrupaSanguina.getSelectedItem().toString()))
                            receiver.setGrupaSanguina(grupeSanguine);
                    }
                    //donator.setGrupaSanguina(new GrupeSanguine(spGrupaSanguina.getSelectedItem().toString()));
                    receiver.setTelefonReceiver(etTelefon.getText().toString());
                    for (CTS cts : Utile.CTS) {
                        if (cts.getNumeCTS().equals(spCTS.getSelectedItem().toString()))
                            receiver.setCts(cts);
                    }
                    receiver.setNumeReceiver(etNume.getText().toString() + " " + etPrenume.getText().toString());
                    receiver.setStareReceiver(2);
                    receiver.setParolaReceiver(etPass1.getText().toString());

                    String url = Utile.URL + "domain.receiveri";

                    final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                    final JSONObject jsonReceiver = new JSONObject();
                    final JSONObject jsonCTS = new JSONObject();
                    final JSONObject jsonGrupaSanguina = new JSONObject();
                    final JSONObject jsonOras = new JSONObject();

                    try {

                        jsonGrupaSanguina.put("idgrupasanguina", receiver.getGrupaSanguina().getGrupaSanguina());

                        jsonOras.put("idoras", receiver.getCts().getOras().getIdOras());
                        jsonOras.put("judet", receiver.getCts().getOras().getJudet());
                        jsonOras.put("numeoras", receiver.getCts().getOras().getOras());


                        jsonCTS.put("adresacts", receiver.getCts().getAdresaCTS());
                        jsonCTS.put("coordonataxcts", receiver.getCts().getCoordonataYCTS());
                        jsonCTS.put("coordonataycts", receiver.getCts().getCoordonataYCTS());
                        jsonCTS.put("emailcts", receiver.getCts().getEmailCTS());
                        jsonCTS.put("idcts", receiver.getCts().getIdCTS());
                        jsonCTS.put("idoras", jsonOras);
                        jsonCTS.put("numects", receiver.getCts().getNumeCTS());
                        jsonCTS.put("starects", receiver.getCts().getStareCTS());
                        jsonCTS.put("telefoncts", receiver.getCts().getTelefonCTS());


                        jsonReceiver.put("emailreceiver", receiver.getEmailReceiver());
                        jsonReceiver.put("idcts", jsonCTS);
                        jsonReceiver.put("idgrupasanguina", jsonGrupaSanguina);
                        jsonReceiver.put("idreceiver", "");
                        jsonReceiver.put("numereceiver", receiver.getNumeReceiver());
                        jsonReceiver.put("parolareceiver", receiver.getParolaReceiver());
                        jsonReceiver.put("starereceiver", 2);
                        jsonReceiver.put("telefonreceiver", receiver.getTelefonReceiver());

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            url, jsonReceiver,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("login_name", receiver.getNumeReceiver());
                                    editor.putString("tip_user", "receiver");
                                    editor.putString("grupaSanguina", receiver.getGrupaSanguina().getGrupaSanguina());
                                    editor.putString("cts", receiver.getCts().getNumeCTS());
                                    editor.putString("email", receiver.getEmailReceiver());
                                    editor.putString("telefon", receiver.getTelefonReceiver());
                                    editor.putString("id", "");

                                    editor.commit();

                                    Toast.makeText(getActivity(), "Inregistrare facuta cu succes", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getActivity(), DetaliiReceiverMainActivity.class);
                                    intent.putExtra("receiver", receiver);
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

                } else {
                    View parentLayout = getActivity().findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Parolele nu corespund", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }
        });


        return rootView;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
