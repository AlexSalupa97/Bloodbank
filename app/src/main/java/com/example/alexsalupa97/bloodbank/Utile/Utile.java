package com.example.alexsalupa97.bloodbank.Utile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexsalupa97.bloodbank.Activitati.CompatibilitatiActivity;
import com.example.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.IesiriCTS;
import com.example.alexsalupa97.bloodbank.Clase.IntrariCTS;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import com.example.alexsalupa97.bloodbank.Clase.LimiteCTS;
import com.example.alexsalupa97.bloodbank.Clase.Orase;
import com.example.alexsalupa97.bloodbank.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Utile {

    public static String fisier = "SharedPreferences";
    public static String URL = "http://b86d1477.ngrok.io/ProiectLicentaBloodbank/webresources/";

    public static ArrayList<Intrebari> intrebari;
    public static ArrayList<Compatibilitati> compatibilitati;
    public static ArrayList<CTS> CTS;
    public static Set<Orase> orase;
    public static ArrayList<IstoricDonatii> listaIstoricDonatii;
    public static ArrayList<IntrariCTS> listaIntrariCTS;
    public static ArrayList<IesiriCTS> listaIesiriCTS;
    public static ArrayList<LimiteCTS> listaLimiteCTS;

    static Gson gson;


    public static String preluareUsername(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("login_name", defaultName);
        return dePreluat;
    }

    public static String preluareJudet(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("judetUser", defaultName);
        return dePreluat;
    }

    public static String preluareGrupaSanguina(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("grupaSanguina", defaultName);
        return dePreluat;
    }

    public static String preluareStareAnalize(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("stareAnalize", defaultName);
        return dePreluat;
    }

    public static String preluareTelefon(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("telefon", defaultName);
        return dePreluat;
    }

    public static String preluareEmail(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("email", defaultName);
        return dePreluat;
    }

    public static String preluareOras(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("orasUser", defaultName);
        return dePreluat;
    }

    public static void updateMenuItem(Context context, Menu menu) {
        String name = preluareGrupaSanguina(context);
        MenuItem item1 = menu.findItem(R.id.item_meniuGrupaSanguina);
        Log.i("Verificare gasire item", "da");
        item1.setTitle(name);
        Log.i("Verificare setare item", name);
    }

    public static void REST_GET_istoricIntrariCTS(final Activity activity) {
        String url = Utile.URL + "domain.istoricintraricts";

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);


        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        gson = gsonBuilder.create();

                        Utile.listaIntrariCTS  = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IntrariCTS[].class)));


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

    public static void REST_GET_limiteCTS(final Activity activity) {
        String url = Utile.URL + "domain.limitects";

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);


        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        gson = gsonBuilder.create();

                        Utile.listaLimiteCTS  = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), LimiteCTS[].class)));

                        int x=1;


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

    public static void REST_GET_istoricIesiriCTS(final Activity activity) {
        String url = Utile.URL + "domain.istoriciesiricts";

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);


        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        gson = gsonBuilder.create();

                        Utile.listaIesiriCTS  = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IesiriCTS[].class)));



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
