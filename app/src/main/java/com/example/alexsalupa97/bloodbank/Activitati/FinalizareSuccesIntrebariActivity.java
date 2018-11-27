package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FinalizareSuccesIntrebariActivity extends AppCompatActivity {

    Gson gsonCTS;
    List<CTS> CTSlist;

    Button btnCentreRecoltare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizare_succes_intrebari);

        btnCentreRecoltare = (Button) findViewById(R.id.btnCentreRecoltare);
        btnCentreRecoltare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnCentreRecoltare.setEnabled(false);

                String url = Utile.URL + "domain.cts";

                final RequestQueue requestQueue = Volley.newRequestQueue(FinalizareSuccesIntrebariActivity.this);


                if (Utile.preluareStareAnalize(getApplicationContext()).equals("ok")) {
                    JsonArrayRequest objectRequest = new JsonArrayRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONArray>() {

                                @Override
                                public void onResponse(JSONArray response) {
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                    gsonCTS = gsonBuilder.create();


                                    CTSlist = Arrays.asList(gsonCTS.fromJson(response.toString(), CTS[].class));
                                    Utile.CTS = new ArrayList<>();
                                    Utile.orase = new HashSet<>();
                                    for (CTS c : CTSlist) {
                                        Utile.CTS.add(c);
                                        Utile.orase.add(c.getOras());
                                    }


                                    Intent intent = new Intent(getApplicationContext(), ListaCentreActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("RestResponse", error.toString());
                                }
                            }

                    );

                    requestQueue.add(objectRequest);


                }
            }
        });

    }
}
