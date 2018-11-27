package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.Utile.Utile;
import com.example.alexsalupa97.bloodbank.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimaPaginaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;

    Button btnVreauSaDonez;
    Button btnVeziCompatibilitati;

    Gson gsonIntrebari;
    Gson gsonCompatibilitati;
    List<Intrebari> intrebariList;
    List<Compatibilitati> compatibilitatiList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prima_pagina);


        sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        Field mDragger = null;
        try {
            mDragger = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mDragger
                    .get(drawerLayout);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField(
                    "mEdgeSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);
        int edge = 0;
        try {
            edge = mEdgeSize.getInt(draggerObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            mEdgeSize.setInt(draggerObj, edge * 15);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerView = navigationView.getHeaderView(0);
        tvNavDrawer = (TextView) headerView.findViewById(R.id.nav_header_textView);

        String nume = Utile.preluareUsername(getApplicationContext());

        tvNavDrawer.setText(nume);

        String stare=Utile.preluareStareAnalize(getApplicationContext());
        int x=1;


        btnVreauSaDonez = (Button) findViewById(R.id.btnVreauSaDonez);
        btnVreauSaDonez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVreauSaDonez.setEnabled(false);
                String url = Utile.URL + "domain.intrebari";

                final RequestQueue requestQueue = Volley.newRequestQueue(PrimaPaginaActivity.this);


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
                                    gsonIntrebari = gsonBuilder.create();


                                    {
                                        intrebariList = Arrays.asList(gsonIntrebari.fromJson(response.toString(), Intrebari[].class));
                                        Utile.intrebari = new ArrayList<>(intrebariList);

                                        Intent intent = new Intent(getApplicationContext(), IntrebariActivity.class);
                                        //intent.putParcelableArrayListExtra("listaIntrebari", Utile.intrebari);

                                        startActivity(intent);
                                    }


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


//                JsonObjectRequest objectRequest = new JsonObjectRequest(
//                        Request.Method.GET,
//                        url,
//                        null,
//                        new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                GsonBuilder gsonBuilder = new GsonBuilder();
//                                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                                gsonIntrebari = gsonBuilder.create();
//
//                                try {
//                                    JSONArray array = response.getJSONArray("intrebari");
//
//
//                                    intrebariList = Arrays.asList(gsonIntrebari.fromJson(array.toString(), Intrebari[].class));
//                                    Utile.intrebari = new ArrayList<>(intrebariList);
//
//                                    Intent intent = new Intent(getApplicationContext(), IntrebariActivity.class);
//                                    //intent.putParcelableArrayListExtra("listaIntrebari", Utile.intrebari);
//
//                                    startActivity(intent);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("RestResponse", error.toString());
//                            }
//                        }
//
//                );
//
//                requestQueue.add(objectRequest);


                }

                if(Utile.preluareStareAnalize(getApplicationContext()).equals("!ok"))
                {
                    Intent intent=new Intent(getApplicationContext(),AnalizeNotOkActivity.class);
                    startActivity(intent);
                }
                if(Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate"))
                {
                    Intent intent=new Intent(getApplicationContext(),AnalizeNeefectuateActivity.class);
                    startActivity(intent);
                }

            }
        });



        btnVeziCompatibilitati = (Button) findViewById(R.id.btnVeziCompatibilitati);
        btnVeziCompatibilitati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnVeziCompatibilitati.setEnabled(false);

                String url = Utile.URL + "domain.compatibilitati/" + Utile.preluareGrupaSanguina(getApplicationContext());

                final RequestQueue requestQueue = Volley.newRequestQueue(PrimaPaginaActivity.this);


                JsonArrayRequest objectRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                gsonCompatibilitati = gsonBuilder.create();


                                {
                                    compatibilitatiList = Arrays.asList(gsonCompatibilitati.fromJson(response.toString(), Compatibilitati[].class));
                                    Utile.compatibilitati = new ArrayList<>(compatibilitatiList);


                                    Intent intent = new Intent(getApplicationContext(), CompatibilitatiActivity.class);

                                    startActivity(intent);
                                }





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


//                JsonObjectRequest objectRequest = new JsonObjectRequest(
//                        Request.Method.GET,
//                        url,
//                        null,
//                        new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                GsonBuilder gsonBuilder = new GsonBuilder();
//                                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                                gsonCompatibilitati = gsonBuilder.create();
//
//                                try {
//                                    JSONArray array = response.getJSONArray("compatibilitati");
//                                    compatibilitatiList = Arrays.asList(gsonCompatibilitati.fromJson(array.toString(), Compatibilitati[].class));
//                                    Utile.compatibilitati = new ArrayList<>(compatibilitatiList);
//                                    int x = 1;
//
//
//                                    Intent intent = new Intent(getApplicationContext(), CompatibilitatiActivity.class);
//
//                                    startActivity(intent);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("RestResponse", error.toString());
//                            }
//                        }
//
//                );
//
//                requestQueue.add(objectRequest);


            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_grupa_sanguina, menu);
        Utile.updateMenuItem(getApplicationContext(), menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.setari)
            Toast.makeText(getApplicationContext(), "Setari", Toast.LENGTH_SHORT).show();
        else if (id == R.id.profil)
            Toast.makeText(getApplicationContext(), "User", Toast.LENGTH_SHORT).show();
        else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_name", "");
            editor.putString("tip_user", "");
            editor.commit();

            Intent intentLogin = new Intent(getApplicationContext(), AlegereLoginActivity.class);
            startActivity(intentLogin);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnVreauSaDonez.setEnabled(true);
        btnVeziCompatibilitati.setEnabled(true);
    }
}
