package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteCTSRV;
import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricReceiverRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiReceiverMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;

    ShareButton fbShareBtn;
    LinearLayout twitterShareBtn;

    RecyclerView rvIstoricReceiver;
    ArrayList<SectionModelIstoric> sectiuni;

    Button btnEmail;
    Button btnTelefon;
    TextView tvNumeReceiver;
    TextView tvAdresa;
    TextView tvEmail;
    TextView tvTelefon;

    SwipeRefreshLayout swiperefreshRVSituatieSanguinaReceiver;

    Receiveri receiver;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_receiver_main);

        getSupportActionBar().setElevation(0);

        tvNumeReceiver = (TextView) findViewById(R.id.tvNumeReceiver);
        tvAdresa = (TextView) findViewById(R.id.tvAdresa);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvTelefon = (TextView) findViewById(R.id.tvTelefon);

        tvNumeReceiver.setText(Utile.preluareUsername(getApplicationContext()));
        tvEmail.setText(tvEmail.getText() + " " + Utile.preluareEmail(getApplicationContext()));
        tvTelefon.setText(tvTelefon.getText() + " " + Utile.preluareTelefon(getApplicationContext()));
        tvAdresa.setText(tvAdresa.getText() + " " + Utile.preluareCTS(getApplicationContext()));

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

        String nume = Utile.preluareUsername(getApplicationContext());

        tvNavDrawer.setText(nume);

        receiver = getIntent().getParcelableExtra("receiver");

        if (receiver == null) {
            if (Utile.preluareID(getApplicationContext()).equals("")) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            gson = new Gson();

                            RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverMainActivity.this);
                            RequestFuture<JSONObject> future = RequestFuture.newFuture();
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.receiveri/email/" + Utile.preluareEmail(getApplicationContext()), null, future, future);
                            requestQueue.add(request);
                            JSONObject response1 = future.get();
                            receiver = gson.fromJson(response1.toString(), Receiveri.class);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", String.valueOf(receiver.getIdReceiver()));
                            editor.commit();
                            final Receiveri receiverFinal = receiver;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),String.valueOf(receiver.getIdReceiver()),Toast.LENGTH_SHORT).show();
                                    fbShareBtn = (ShareButton) findViewById(R.id.fbShareBtn);
                                    ShareLinkContent content = new ShareLinkContent.Builder()
                                            .setQuote("Doneaza pentru " + receiverFinal.getNumeReceiver())
                                            .setContentUrl(Uri.parse("https://play.google.com/store/apps/developer?id=AlexSalupa97"))
                                            .build();

                                    fbShareBtn.setShareContent(content);


                                    twitterShareBtn = (LinearLayout) findViewById(R.id.twitterShareBtn);
                                    twitterShareBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent twitterIntent = getShareIntent("twitter", "subject", "Doneaza pentru " + receiverFinal.getNumeReceiver() + "\nhttps://play.google.com/store/apps/developer?id=AlexSalupa97");
                                            if (twitterIntent != null)
                                                startActivity(twitterIntent);

                                        }
                                    });
                                }
                            });

                        } catch (InterruptedException e) {
                            System.out.println(e.toString());
                        } catch (ExecutionException e) {
                            System.out.println(e.toString());
                        }


                    }
                }) ;




                t.start();
            } else {
                receiver = new Receiveri();
                receiver.setNumeReceiver(Utile.preluareUsername(getApplicationContext()));
                receiver.setTelefonReceiver(Utile.preluareTelefon(getApplicationContext()));
                receiver.setEmailReceiver(Utile.preluareEmail(getApplicationContext()));
                try {
                    for (CTS cts : Utile.CTS)
                        if (cts.getNumeCTS().equals((Utile.preluareCTS(getApplicationContext()))))
                            receiver.setCts(cts);

                    for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine)
                        if (grupeSanguine.getGrupaSanguina().equals((Utile.preluareGrupaSanguina(getApplicationContext()))))
                            receiver.setGrupaSanguina(grupeSanguine);
                } catch (Exception ex) {

                }
            }

        }

        rvIstoricReceiver = (RecyclerView) findViewById(R.id.rvIstoricReceiver);

        swiperefreshRVSituatieSanguinaReceiver = (SwipeRefreshLayout) findViewById(R.id.swiperefreshRVSituatieSanguinaReceiver);
        swiperefreshRVSituatieSanguinaReceiver.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swiperefreshRVSituatieSanguinaReceiver.setRefreshing(true);
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverMainActivity.this);
                    RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                    JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricreceiveri/receiver/" + Utile.preluareEmail(getApplicationContext()), null, future1, future1);
                    RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                    JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                    requestQueue.add(request1);
                    requestQueue.add(request3);

                    JSONArray response1 = future1.get();
                    JSONArray response3 = future3.get();

                    gson = new Gson();


                    Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));
                    Utile.listaIstoricReceiver = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IstoricReceiver[].class)));

                    sectiuni = new ArrayList<>();

                    SectionModelIstoric dm = new SectionModelIstoric();

                    dm.setTitlu("Donatii primite");

                    ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
                    for (IstoricReceiver id : Utile.listaIstoricReceiver) {
                        int index = id.getDataPrimire().indexOf("T");
                        String substring = id.getDataPrimire().substring(0, index);
                        itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitatePrimitaML() + "ml"));
                    }

                    Collections.sort(itemeInSectiune);
                    dm.setItemeInSectiune(itemeInSectiune);

                    sectiuni.add(dm);

                    rvIstoricReceiver.setHasFixedSize(true);

                    final AdaptorIstoricReceiverRV adaptor = new AdaptorIstoricReceiverRV(DetaliiReceiverMainActivity.this, sectiuni, true);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvIstoricReceiver.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            rvIstoricReceiver.setAdapter(adaptor);
                            swiperefreshRVSituatieSanguinaReceiver.setRefreshing(false);
                        }
                    });

                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }
        });
        thread.start();

        swiperefreshRVSituatieSanguinaReceiver.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {

                            RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverMainActivity.this);
                            RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                            JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricreceiveri/receiver/" + Utile.preluareEmail(getApplicationContext()), null, future1, future1);
                            RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                            JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                            requestQueue.add(request1);
                            requestQueue.add(request3);

                            JSONArray response1 = future1.get();
                            JSONArray response3 = future3.get();

                            gson = new Gson();


                            Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));
                            Utile.listaIstoricReceiver = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IstoricReceiver[].class)));

                            sectiuni = new ArrayList<>();

                            SectionModelIstoric dm = new SectionModelIstoric();

                            dm.setTitlu("Donatii primite");

                            ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
                            for (IstoricReceiver id : Utile.listaIstoricReceiver) {
                                int index = id.getDataPrimire().indexOf("T");
                                String substring = id.getDataPrimire().substring(0, index);
                                itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitatePrimitaML() + "ml"));
                            }

                            Collections.sort(itemeInSectiune);
                            dm.setItemeInSectiune(itemeInSectiune);

                            sectiuni.add(dm);

                            rvIstoricReceiver.setHasFixedSize(true);

                            final AdaptorIstoricReceiverRV adaptor = new AdaptorIstoricReceiverRV(DetaliiReceiverMainActivity.this, sectiuni, true);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvIstoricReceiver.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                    rvIstoricReceiver.setAdapter(adaptor);
                                    swiperefreshRVSituatieSanguinaReceiver.setRefreshing(false);
                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                };

                t.start();

            }
        });

            final Receiveri receiverFinal = receiver;

            fbShareBtn = (ShareButton) findViewById(R.id.fbShareBtn);
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setQuote("Doneaza pentru " + receiverFinal.getNumeReceiver())
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/developer?id=AlexSalupa97"))
                    .build();

            fbShareBtn.setShareContent(content);


            twitterShareBtn = (LinearLayout) findViewById(R.id.twitterShareBtn);
            twitterShareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent twitterIntent = getShareIntent("twitter", "subject", "Doneaza pentru " + receiverFinal.getNumeReceiver() + "\nhttps://play.google.com/store/apps/developer?id=AlexSalupa97");
                    if (twitterIntent != null)
                        startActivity(twitterIntent);

                }
            });
    }


    private Intent getShareIntent(String type, String subject, String text) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getApplicationContext().getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, subject);
                    share.putExtra(Intent.EXTRA_TEXT, text);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
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
        } else if (id == R.id.stergereCont) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sunteti sigur ca doriti dezactivarea contului dvs.?")
                    .setCancelable(true)
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {

                            String url = Utile.URL + "domain.receiveri/" + Utile.preluareID(getApplicationContext());
                            RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverMainActivity.this);
                            StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            Log.d("Response", response);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }
                            );
                            requestQueue.add(deleteRequest);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("login_name", "");
                            editor.putString("tip_user", "");
                            editor.putString("id", "");
                            editor.commit();

                            Intent intentLogin = new Intent(getApplicationContext(), AlegereLoginActivity.class);
                            startActivity(intentLogin);
                            finish();

                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } else if (id == R.id.compatibilitati) {
            if (Utile.compatibilitati == null) {
                String url = Utile.URL + "domain.compatibilitati/" + Utile.preluareGrupaSanguina(getApplicationContext());

                final RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverMainActivity.this);


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


                                Intent intent = new Intent(getApplicationContext(), CompatibilitatiActivity.class);
                                startActivity(intent);

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
            } else {
                Intent intent = new Intent(getApplicationContext(), CompatibilitatiActivity.class);
                startActivity(intent);
            }


        } else if (id == R.id.logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_name", "");
            editor.putString("tip_user", "");
            editor.putString("id", "");
            editor.commit();

            Intent intentLogin = new Intent(getApplicationContext(), AlegereLoginActivity.class);
            startActivity(intentLogin);
            finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_grupa_sanguina, menu);
        Utile.updateMenuItem(getApplicationContext(), menu);
        return true;
    }
}
