package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteCTSRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiCTSActivity extends AppCompatActivity {

    CTS ctsCurent;
    Button btnEmail;
    Button btnTelefon;
    TextView tvNumeCTS;
    TextView tvAdresa;
    TextView tvEmail;
    TextView tvTelefon;

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    ArrayList<CantitatiCTS> listaCantitatiCTS;
    Map<CTS, ArrayList<CantitatiCTS>> mapCantitatiPerCTS;

    RecyclerView rvAlerte;
    ArrayList<SectionModelAlerte> sectiuni;

    SwipeRefreshLayout swiperefreshRVSituatieSanguinaCTS;

    Gson gson;
    AdaptorAlerteCTSRV adapter;

    Button btnProgramare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cts);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        rvAlerte = (RecyclerView) findViewById(R.id.rvSituatieSanguinaCTS);

        ctsCurent= getIntent().getExtras().getParcelable("cts");

        tvNumeCTS=(TextView)findViewById(R.id.tvNumeCTS);
        tvAdresa=(TextView)findViewById(R.id.tvAdresa);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvTelefon=(TextView)findViewById(R.id.tvTelefon);

        tvNumeCTS.setText(ctsCurent.getNumeCTS());
        tvAdresa.setText(tvAdresa.getText()+ctsCurent.getAdresaCTS());
        tvEmail.setText(tvEmail.getText()+ctsCurent.getEmailCTS());
        tvTelefon.setText(tvTelefon.getText()+ctsCurent.getTelefonCTS());

        btnEmail=(Button)findViewById(R.id.btnEmail);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailto = "mailto:" + ctsCurent.getEmailCTS()+
                        "?cc=" + "alex.salupa@yahoo.com" +
                        "&subject=" + Uri.encode("Programare donatie") +
                        "&body=" + Uri.encode("Programare");
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mailto));
                startActivity(intent);
            }
        });

        btnTelefon=(Button)findViewById(R.id.btnTelefon);

        btnTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ctsCurent.getTelefonCTS()));
                startActivity(intent);
            }
        });

        btnProgramare=(Button)findViewById(R.id.btnProgramare);
        btnProgramare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProgramareActivity.class);
                intent.putExtra("cts",ctsCurent);
                startActivity(intent);
            }
        });

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
                    RequestQueue requestQueue = Volley.newRequestQueue(DetaliiCTSActivity.this);
                    RequestFuture<JSONArray> future = RequestFuture.newFuture();
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.limitects/cts/" + ctsCurent.getEmailCTS(), null, future, future);
                    RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                    JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricintraricts/cts/" + ctsCurent.getEmailCTS(), null, future1, future1);
                    RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                    JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts/cts/" + ctsCurent.getEmailCTS(), null, future2, future2);
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

                    mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil_particular(ctsCurent));

                    mapLimitePerCTSPerGrupa = new HashMap<>();


                    Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                    try {
                        for (LimiteCTS limite : Utile.listaLimiteCTS)
                            mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                    } catch (Exception ex) {

                    }
                    mapLimitePerCTSPerGrupa.put(ctsCurent, mapIntermediar);


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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
