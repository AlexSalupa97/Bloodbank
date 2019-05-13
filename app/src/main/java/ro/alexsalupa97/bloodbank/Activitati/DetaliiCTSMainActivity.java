package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteCTSRV;
import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiCTSMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    ArrayList<CantitatiCTS> listaCantitatiCTS;
    Map<CTS, ArrayList<CantitatiCTS>> mapCantitatiPerCTS;

    RecyclerView rvAlerte;
    ArrayList<SectionModelAlerte> sectiuni;

    SwipeRefreshLayout swiperefreshRVSituatieSanguinaCTS;

    Gson gson;

    Button btnSituatieCTS;

    AdaptorAlerteCTSRV adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cts_main);

        final CTS ctsActual = Utile.preluareCTSLogin(getApplicationContext());


        getSupportActionBar().setTitle(ctsActual.getNumeCTS());
        rvAlerte = (RecyclerView) findViewById(R.id.rvSituatieSanguinaCTS);


        sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerView = navigationView.getHeaderView(0);
        tvNavDrawer = (TextView) headerView.findViewById(R.id.nav_header_textView);


        tvNavDrawer.setText(ctsActual.getNumeCTS());

        getSupportActionBar().setElevation(0);

        swiperefreshRVSituatieSanguinaCTS = (SwipeRefreshLayout) findViewById(R.id.swiperefreshRVSituatieSanguinaCTS);
        swiperefreshRVSituatieSanguinaCTS.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swiperefreshRVSituatieSanguinaCTS.setRefreshing(true);
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DetaliiCTSMainActivity.this);
                    RequestFuture<JSONArray> future = RequestFuture.newFuture();
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.limitects/cts/" + ctsActual.getEmailCTS(), null, future, future);
                    RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                    JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricintraricts/cts/" + ctsActual.getEmailCTS(), null, future1, future1);
                    RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                    JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts/cts/" + ctsActual.getEmailCTS(), null, future2, future2);
                    RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                    JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                    requestQueue.add(request);
                    requestQueue.add(request1);
                    requestQueue.add(request2);
                    requestQueue.add(request3);

                    JSONArray response = future.get();
                    JSONArray response1 = future1.get();
                    JSONArray response2 = future2.get();
                    JSONArray response3 = future3.get();

                    gson=new Gson();

                    Utile.listaLimiteCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), LimiteCTS[].class)));
                    Utile.listaIntrariCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IntrariCTS[].class)));
                    Utile.listaIesiriCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response2.toString(), IesiriCTS[].class)));
                    Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));

                    mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil_particular(ctsActual));

                    mapLimitePerCTSPerGrupa = new HashMap<>();


                    Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                    try {
                        for (LimiteCTS limite : Utile.listaLimiteCTS)
                            mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                    } catch (Exception ex) {

                    }
                    mapLimitePerCTSPerGrupa.put(ctsActual, mapIntermediar);


                    mapCantitatiPerCTS = new HashMap<>();

                    for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                        String deAfisat = "\n\n";


                        Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                        Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


                        deAfisat += "\t" + cts.getNumeCTS();

                        listaCantitatiCTS = new ArrayList<>();

                        for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine) {

                            try {
                                CantitatiCTS cantitateCTSCurent = new CantitatiCTS();
                                cantitateCTSCurent.setCts(cts);
                                cantitateCTSCurent.setGrupaSanguina(grupeSanguine);
                                cantitateCTSCurent.setCantitateDisponibilaML(mapCantitatiDisponibile.get(grupeSanguine));
                                cantitateCTSCurent.setCantitateLimitaML(mapLimite.get(grupeSanguine));
                                listaCantitatiCTS.add(cantitateCTSCurent);
                            } catch (Exception ex) {

                            }

                        }

                        mapCantitatiPerCTS.put(cts, listaCantitatiCTS);
                    }

                    sectiuni = new ArrayList<>();

                    ArrayList<CTS> listaCTS = new ArrayList<>(mapCantitatiPerCTS.keySet());
                    Collections.sort(listaCTS);


                    for (CTS cts : listaCTS) {
                        SectionModelAlerte dm = new SectionModelAlerte();

                        dm.setTitlu("Situatia cantitatilor de sange");

                        ArrayList<ItemModelAlerte> itemeInSectiune = new ArrayList<>();
                        for (CantitatiCTS cantitatiCTS : mapCantitatiPerCTS.get(cts)) {
                            itemeInSectiune.add(new ItemModelAlerte(cantitatiCTS));
                        }

                        dm.setItemeInSectiune(itemeInSectiune);

                        sectiuni.add(dm);
                    }


                    rvAlerte.setHasFixedSize(true);

                    adapter = new AdaptorAlerteCTSRV(getApplicationContext(), sectiuni);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvAlerte.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            rvAlerte.setAdapter(adapter);
                            swiperefreshRVSituatieSanguinaCTS.setRefreshing(false);
                        }
                    });

                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }
        });
        thread.start();



        swiperefreshRVSituatieSanguinaCTS.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(DetaliiCTSMainActivity.this);
                            RequestFuture<JSONArray> future = RequestFuture.newFuture();
                            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.limitects/cts/" + ctsActual.getEmailCTS(), null, future, future);
                            RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                            JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricintraricts/cts/" + ctsActual.getEmailCTS(), null, future1, future1);
                            RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts/cts/" + ctsActual.getEmailCTS(), null, future2, future2);
                            RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                            JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                            requestQueue.add(request);
                            requestQueue.add(request1);
                            requestQueue.add(request2);
                            requestQueue.add(request3);

                            JSONArray response = future.get();
                            JSONArray response1 = future1.get();
                            JSONArray response2 = future2.get();
                            JSONArray response3 = future3.get();

                            gson=new Gson();

                            Utile.listaLimiteCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), LimiteCTS[].class)));
                            Utile.listaIntrariCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IntrariCTS[].class)));
                            Utile.listaIesiriCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response2.toString(), IesiriCTS[].class)));
                            Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));

                            mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil_particular(ctsActual));

                            mapLimitePerCTSPerGrupa = new HashMap<>();


                            Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                            try {
                                for (LimiteCTS limite : Utile.listaLimiteCTS)
                                    mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                            } catch (Exception ex) {

                            }
                            mapLimitePerCTSPerGrupa.put(ctsActual, mapIntermediar);


                            mapCantitatiPerCTS = new HashMap<>();

                            for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                                String deAfisat = "\n\n";


                                Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                                Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


                                deAfisat += "\t" + cts.getNumeCTS();

                                listaCantitatiCTS = new ArrayList<>();

                                for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine) {

                                    try {
                                        CantitatiCTS cantitateCTSCurent = new CantitatiCTS();
                                        cantitateCTSCurent.setCts(cts);
                                        cantitateCTSCurent.setGrupaSanguina(grupeSanguine);
                                        cantitateCTSCurent.setCantitateDisponibilaML(mapCantitatiDisponibile.get(grupeSanguine));
                                        cantitateCTSCurent.setCantitateLimitaML(mapLimite.get(grupeSanguine));
                                        listaCantitatiCTS.add(cantitateCTSCurent);
                                    } catch (Exception ex) {

                                    }

                                }

                                mapCantitatiPerCTS.put(cts, listaCantitatiCTS);
                            }

                            sectiuni = new ArrayList<>();

                            ArrayList<CTS> listaCTS = new ArrayList<>(mapCantitatiPerCTS.keySet());
                            Collections.sort(listaCTS);


                            for (CTS cts : listaCTS) {
                                SectionModelAlerte dm = new SectionModelAlerte();

                                dm.setTitlu("Situatia cantitatilor de sange");

                                ArrayList<ItemModelAlerte> itemeInSectiune = new ArrayList<>();
                                for (CantitatiCTS cantitatiCTS : mapCantitatiPerCTS.get(cts)) {
                                    itemeInSectiune.add(new ItemModelAlerte(cantitatiCTS));
                                }

                                dm.setItemeInSectiune(itemeInSectiune);

                                sectiuni.add(dm);
                            }


                            rvAlerte.setHasFixedSize(true);

                            adapter = new AdaptorAlerteCTSRV(getApplicationContext(), sectiuni);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvAlerte.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                    rvAlerte.setAdapter(adapter);
                                    swiperefreshRVSituatieSanguinaCTS.setRefreshing(false);
                                }
                            });
                        } catch (Exception e) {
                            Log.e("SplashScreenActivity", e.getMessage());
                        }
                    }
                };

                t.start();

            }
        });

        btnSituatieCTS = (Button) findViewById(R.id.btnSituatieCTS);
        btnSituatieCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticiCTSActivity.class);
                startActivity(intent);
            }
        });

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
        if (id == R.id.setari) {
            Intent intent = new Intent(getApplicationContext(), SetariActivity.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
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


}
