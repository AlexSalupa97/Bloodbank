package com.example.alexsalupa97.bloodbank.Activitati;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import com.example.alexsalupa97.bloodbank.Notificari.NotificariBroadcast;
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
import java.util.HashSet;
import java.util.List;

public class PrimaPaginaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;

    Button btnVreauSaDonez;
    Button btnListaCTS;
    Button btnAlerte;
    Button btnNotificari;

    Gson gsonIntrebari;
    Gson gsonCompatibilitati;
    Gson gsonIstoricDonatii;
    List<Intrebari> intrebariList;
    List<Compatibilitati> compatibilitatiList;
    List<IstoricDonatii> istoricDonatiiList;

    Gson gsonCTS;
    List<CTS> CTSlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prima_pagina);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            int x = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    x);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            int x = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    x);
        }

        statusCheck();


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

        String stare = Utile.preluareStareAnalize(getApplicationContext());


        btnVreauSaDonez = (Button) findViewById(R.id.btnVreauSaDonez);
        btnVreauSaDonez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertMessageVreauSaDonez();

            }
        });

        btnListaCTS = (Button) findViewById(R.id.btnListaCTS);
        btnListaCTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Utile.URL + "domain.cts";

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

        btnAlerte = (Button) findViewById(R.id.btnAlerte);
        btnAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AlerteActivity.class);
                startActivity(intent);
            }
        });


        btnNotificari = (Button) findViewById(R.id.btnNotificari);
        btnNotificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleNotification(triggerNotification(),3000);

//                triggerNotification();

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
        else if (id == R.id.compatibilitati) {
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

        } else if (id == R.id.profil) {
            String url = Utile.URL + "domain.istoricdonatii/donator/" + Utile.preluareEmail(getApplicationContext());

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
                            gsonIstoricDonatii = gsonBuilder.create();


                            istoricDonatiiList = Arrays.asList(gsonIstoricDonatii.fromJson(response.toString(), IstoricDonatii[].class));
                            Utile.listaIstoricDonatii = new ArrayList<>(istoricDonatiiList);


                            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);

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


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS inactiv, activati serviciile de localizare?")
                .setCancelable(false)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageVreauSaDonez() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Urmeaza un set de 10 intrebari pentru a va informa de conditiile necesare pentru a putea dona, doriti sa le abordati? (1 minut)")
                .setCancelable(false)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
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

                                            intrebariList = Arrays.asList(gsonIntrebari.fromJson(response.toString(), Intrebari[].class));
                                            Utile.intrebari = new ArrayList<>(intrebariList);

                                            Intent intent = new Intent(getApplicationContext(), IntrebariActivity.class);
                                            //intent.putParcelableArrayListExtra("listaIntrebari", Utile.intrebari);

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


                        }

                        if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok")) {
                            Intent intent = new Intent(getApplicationContext(), AnalizeNotOkActivity.class);
                            startActivity(intent);
                        }
                        if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate")) {
                            Intent intent = new Intent(getApplicationContext(), AnalizeNeefectuateActivity.class);
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        String url = Utile.URL + "domain.cts";

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
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private Notification triggerNotification()
    {
        Intent resultIntent = new Intent(getApplicationContext(), AlerteActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent backIntent = new Intent(this, PrimaPaginaActivity.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        PendingIntent resultPendingIntent = PendingIntent.getActivities(getApplicationContext(),
                0 /* Request code */, new Intent[] {backIntent,resultIntent},
                PendingIntent.FLAG_ONE_SHOT);

//        PendingIntent resultPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
//                0 /* Request code */, resultIntent,
//                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "test")
                        .setSmallIcon(R.drawable.blood)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentTitle("Alerta de sange")
                        .setContentText("Vezi situatia actuala")
                        .setChannelId("test")
                        .setAutoCancel(true);

        mBuilder.setContentIntent(resultPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("test", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            assert mNotificationManager != null;
            mBuilder.setChannelId("test");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;

//        mNotificationManager.notify(1, mBuilder.build());

        return mBuilder.build();

    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificariBroadcast.class);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}
