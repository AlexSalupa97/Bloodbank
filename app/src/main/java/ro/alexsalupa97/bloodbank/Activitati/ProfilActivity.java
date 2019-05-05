package ro.alexsalupa97.bloodbank.Activitati;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricDonatiiRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProfilActivity extends AppCompatActivity {

    ArrayList<SectionModelIstoric> sectiuni;
    RecyclerView rvIstoric;
    AdaptorIstoricDonatiiRV adapter;

    TextView tvNume;
    TextView tvTelefon;
    TextView tvEmail;
    TextView tvGrupaSanguina;
    TextView tvOras;
    ImageView ivStareAnalize;

    Dialog dialogInfo;
    Button btnOK;

    SwipeRefreshLayout srlProfil;

    String fisier = "SharedPreferences";

    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getSupportActionBar().setElevation(0);

        dialogInfo = new Dialog(ProfilActivity.this);
        dialogInfo.setContentView(R.layout.dialog_info);

        btnOK = (Button) dialogInfo.findViewById(R.id.btnDialogOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo.dismiss();
            }
        });


        tvNume = (TextView) findViewById(R.id.tvNume);
        tvTelefon = (TextView) findViewById(R.id.tvTelefon);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvGrupaSanguina = (TextView) findViewById(R.id.tvGrupaSanguina);
        tvOras = (TextView) findViewById(R.id.tvOras);
        ivStareAnalize = (ImageView) findViewById(R.id.ivStareAnalize);

        tvNume.setText(Utile.preluareUsername(getApplicationContext()));
        tvTelefon.setText(Utile.preluareTelefon(getApplicationContext()));
        tvEmail.setText(Utile.preluareEmail(getApplicationContext()));
        tvGrupaSanguina.setText(Utile.preluareGrupaSanguina(getApplicationContext()));
        tvOras.setText(Utile.preluareOras(getApplicationContext()));

        if (Utile.preluareStareAnalize(getApplicationContext()).equals("ok"))
            ivStareAnalize.setImageResource(R.drawable.good);
        else if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok"))
            ivStareAnalize.setImageResource(R.drawable.bad);
        else if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate"))
            ivStareAnalize.setImageResource(R.drawable.not_done);

        sectiuni = new ArrayList<>();

        SectionModelIstoric dm = new SectionModelIstoric();

        dm.setTitlu("Istoric donatii");

        Collections.sort(Utile.listaIstoricDonatii);

        ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
        for (IstoricDonatii id : Utile.listaIstoricDonatii) {
            int index = id.getDataDonatie().indexOf("T");
            String substring = id.getDataDonatie().substring(0, index);
            itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitateDonataML() + "ml"));
        }

        dm.setItemeInSectiune(itemeInSectiune);

        sectiuni.add(dm);

        rvIstoric = (RecyclerView) findViewById(R.id.rvIstoric);

        rvIstoric.setHasFixedSize(true);

        adapter = new AdaptorIstoricDonatiiRV(this, sectiuni);
        rvIstoric.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIstoric.setAdapter(adapter);


        srlProfil=(SwipeRefreshLayout)findViewById(R.id.srlProfil);
        srlProfil.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        srlProfil.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            gson = new Gson();

                            RequestQueue requestQueue = Volley.newRequestQueue(ProfilActivity.this);
                            RequestFuture<JSONObject> future = RequestFuture.newFuture();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.stareanalize/" + Utile.preluareEmail(getApplicationContext()), null, future, future);
                            RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                            JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricdonatii/donator/" + Utile.preluareEmail(getApplicationContext()), null, future1, future1);

                            requestQueue.add(request);
                            requestQueue.add(request1);

                            JSONObject response=future.get();
                            JSONArray response1=future1.get();

                            SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("stareAnalize", response.getString("stareanalize"));
                            editor.commit();



                            Utile.listaIstoricDonatii = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IstoricDonatii[].class)));


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (Utile.preluareStareAnalize(getApplicationContext()).equals("ok"))
                                        ivStareAnalize.setImageResource(R.drawable.good);
                                    else if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok"))
                                        ivStareAnalize.setImageResource(R.drawable.bad);
                                    else if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate"))
                                        ivStareAnalize.setImageResource(R.drawable.not_done);

                                    sectiuni = new ArrayList<>();

                                    SectionModelIstoric dm = new SectionModelIstoric();

                                    dm.setTitlu("Istoric donatii");

                                    Collections.sort(Utile.listaIstoricDonatii);

                                    ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
                                    for (IstoricDonatii id : Utile.listaIstoricDonatii) {
                                        int index = id.getDataDonatie().indexOf("T");
                                        String substring = id.getDataDonatie().substring(0, index);
                                        itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitateDonataML() + "ml"));
                                    }

                                    dm.setItemeInSectiune(itemeInSectiune);

                                    sectiuni.add(dm);

                                    rvIstoric = (RecyclerView) findViewById(R.id.rvIstoric);

                                    rvIstoric.setHasFixedSize(true);

                                    adapter = new AdaptorIstoricDonatiiRV(getApplicationContext(), sectiuni);
                                    rvIstoric.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                    rvIstoric.setAdapter(adapter);
                                    srlProfil.setRefreshing(false);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meniu_info_donator, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_info)
            dialogInfo.show();
        return super.onOptionsItemSelected(item);
    }
}
