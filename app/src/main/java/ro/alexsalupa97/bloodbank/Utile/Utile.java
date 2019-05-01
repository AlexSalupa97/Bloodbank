package ro.alexsalupa97.bloodbank.Utile;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.Intrebari;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.Clase.Orase;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utile {

    public static String fisier = "SharedPreferences";
    public static String URL = "http://caad76c3.ngrok.io/ProiectLicentaBloodbank/webresources/";

    public static ArrayList<Intrebari> intrebari;
    public static ArrayList<Compatibilitati> compatibilitati;
    public static ArrayList<CTS> CTS;
    public static Set<Orase> orase;
    public static ArrayList<IstoricDonatii> listaIstoricDonatii;
    public static ArrayList<IstoricReceiver> listaIstoricReceiver;
    public static ArrayList<IntrariCTS> listaIntrariCTS;
    public static ArrayList<IesiriCTS> listaIesiriCTS;
    public static ArrayList<LimiteCTS> listaLimiteCTS;
    public static ArrayList<GrupeSanguine> listaGrupeSanguine;
    public static ArrayList<Receiveri> listaReceiveri;

    public static int idDonator;
    public static int idReceiver;

    public static boolean firstTimeReceiver;

    static Gson gson;

    public static Donatori preluareDonator(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        String dePreluat = sharedPreferences.getString("donator", defaultName);
        return gson.fromJson(dePreluat, Donatori.class);
    }

    public static CTS preluareCTSLogin(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        String dePreluat = sharedPreferences.getString("login_name", defaultName);
        return gson.fromJson(dePreluat, CTS.class);
    }

    public static String preluareDonatorString(Context context){
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("donator", defaultName);
        return dePreluat;
    }

    public static String preluareTipUser(Context context){
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("tip_user", defaultName);
        return dePreluat;
    }

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

    public static String preluareCTS(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("cts", defaultName);
        return dePreluat;
    }

    public static String preluareIDReceiver(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("id", defaultName);
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


    public static void REST_GET_grupeSanguine(final Activity activity) {
        String url = Utile.URL + "domain.grupesanguine";

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

                        Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), GrupeSanguine[].class)));


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

                        Utile.listaIntrariCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IntrariCTS[].class)));


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

                        Utile.listaLimiteCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), LimiteCTS[].class)));

                        int x = 1;


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

                        Utile.listaIesiriCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IesiriCTS[].class)));


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

    public static void REST_GET_listaCTS(final Activity activity) {
        String url = Utile.URL + "domain.cts";

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


                        Utile.CTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), CTS[].class)));
                        Utile.orase = new HashSet<>();

                        for (CTS cts : Utile.CTS)
                            orase.add(cts.getOras());


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

    public static void REST_GET_listaCompatibilitati(final Activity activity) {
        String url = Utile.URL + "domain.compatibilitati/" + Utile.preluareGrupaSanguina(activity);

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


                        Utile.compatibilitati = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), Compatibilitati[].class)));


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

    public static Map<CTS, Map<GrupeSanguine, Integer>> incarcareMapDisponibil() {


        Map<CTS, Map<GrupeSanguine, Integer>> listaDisponibil = new HashMap<>();

        for (CTS c : CTS) {
            Map<GrupeSanguine, Integer> listaIntermediara = new HashMap<>();
            for (GrupeSanguine g : listaGrupeSanguine) {
                int cantitateIntrata = 0;
                for (IntrariCTS in : listaIntrariCTS) {
                    if (in.getCts().getNumeCTS().equals(c.getNumeCTS()) && in.getGrupaSanguina().getGrupaSanguina().equals(g.getGrupaSanguina())) {
                        cantitateIntrata += in.getCantitatePrimitaML();
                    }
                }
                int cantitateIesita = 0;
                for (IesiriCTS out : listaIesiriCTS) {
                    if (out.getCts().getNumeCTS().equals(c.getNumeCTS()) && out.getGrupaSanguina().getGrupaSanguina().equals(g.getGrupaSanguina())) {
                        cantitateIesita += out.getCantitateIesitaML();
                    }
                }

                listaIntermediara.put(g, cantitateIntrata - cantitateIesita);

            }
            listaDisponibil.put(c, listaIntermediara);
        }

        return listaDisponibil;
    }

    public static void REST_GET_countDonatori(final Activity activity) {
        String url = Utile.URL + "domain.donatori/count";

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        idDonator = Integer.parseInt(response);

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

    public static void REST_GET_countReceiveri(final Activity activity) {
        String url = Utile.URL + "domain.receiveri/count";

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);


        StringRequest objectRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        idReceiver = Integer.parseInt(response);

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

    public static void REST_GET_Receiveri(final Activity activity, final Class clasa) {
        String url = Utile.URL + "domain.receiveri";

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

                        Utile.listaReceiveri = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), Receiveri[].class)));

                        Intent intent=new Intent(activity,clasa);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.getApplicationContext().startActivity(intent);

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

    public static void REST_GET_istoricReceiver(final Activity activity, final String idReceiverCurent){
        String url = Utile.URL + "domain.istoricreceiveri/receiver/"+idReceiverCurent;

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

                        Utile.listaIstoricReceiver = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IstoricReceiver[].class)));


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
