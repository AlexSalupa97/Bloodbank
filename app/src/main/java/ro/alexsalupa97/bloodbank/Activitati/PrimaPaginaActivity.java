package ro.alexsalupa97.bloodbank.Activitati;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
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
import com.android.volley.toolbox.Volley;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.Clase.Intrebari;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import ro.alexsalupa97.bloodbank.Notificari.ActionAlerteBroadcast;
import ro.alexsalupa97.bloodbank.Notificari.ActionCentreBroadcast;
import ro.alexsalupa97.bloodbank.Notificari.NotificariBroadcast;
import ro.alexsalupa97.bloodbank.Notificari.NotifyingDailyService;
import ro.alexsalupa97.bloodbank.Notificari.TestJobIntentService;
import ro.alexsalupa97.bloodbank.Notificari.TestJobService;
import ro.alexsalupa97.bloodbank.Utile.CalculDistante;
import ro.alexsalupa97.bloodbank.Utile.Utile;
import ro.alexsalupa97.bloodbank.R;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkmmte.view.CircularImageView;


import org.joda.time.Instant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;
import static ro.alexsalupa97.bloodbank.Utile.Utile.preluareEmail;

public class PrimaPaginaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;
    CircularImageView ivNavDrawer;

    private final int COD = 20;

    Button btnVreauSaDonez;
    Button btnAlerte;
    Button btnNotificari;
    Button btnReceiveri;

    Gson gsonIntrebari;
    Gson gsonCompatibilitati;
    Gson gsonIstoricDonatii;
    Gson gson;
    List<Intrebari> intrebariList;
    List<Compatibilitati> compatibilitatiList;
    List<IstoricDonatii> istoricDonatiiList;

    Uri imageUri;
    Bitmap bitmap;

    Gson gsonCTS;
    List<CTS> CTSlist;

    ProgressDialog pd;

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prima_pagina);

        context = getApplicationContext();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        statusCheck();

//        NotifyingDailyService mSensorService = new NotifyingDailyService();
//        Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
//        startService(mServiceIntent);
//
//        ComponentName componentName = new ComponentName(this, TestJobService.class);
//        JobInfo info = new JobInfo.Builder(123, componentName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                .setPersisted(true)
//                .build();
//
//        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        int resultCode = scheduler.schedule(info);
//        if (resultCode == JobScheduler.RESULT_SUCCESS) {
//            Log.d("jobservice", "Job scheduled");
//        } else {
//            Log.d("jobservice", "Job scheduling failed");
//        }


//        Intent serviceIntent = new Intent(this, TestJobIntentService.class);
//        TestJobIntentService.enqueueWork(this, serviceIntent);


        if (Utile.firstTimeDonator) {
            Utile.firstTimeDonator = false;
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Cont creat cu succes", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        gson = new Gson();

                        RequestQueue requestQueue = Volley.newRequestQueue(PrimaPaginaActivity.this);
                        RequestFuture<JSONObject> future = RequestFuture.newFuture();
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.stareanalize/" + preluareEmail(getApplicationContext()), null, future, future);
                        requestQueue.add(request);
                        JSONObject response1 = future.get();
                        String idStareAnalize = response1.getString("idstareanaliza");
                        String dataStareAnaliza = response1.getString("dataefectuareanaliza");
                        JSONObject jsonDonator = response1.getJSONObject("iddonator");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("donator", jsonDonator.toString());
                        editor.putString("dataanalize", dataStareAnaliza);
                        editor.putString("idanalize", idStareAnalize);
                        editor.commit();
                    } catch (Exception e) {

                    }
                }
            });
            t.start();
        }


        try {

            MapsCTSFragment.locatieCurenta = CalculDistante.getMyLocation(PrimaPaginaActivity.this);
        } catch (Exception ex) {

        }


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
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        View headerView = navigationView.getHeaderView(0);
        tvNavDrawer = (TextView) headerView.findViewById(R.id.nav_header_textView);
        ivNavDrawer = (CircularImageView) headerView.findViewById(R.id.nav_header_imageView);

        ivNavDrawer.setOnClickListener((View view) -> {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 20);
        });

        SharedPreferences myPrefrence = getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        Bitmap imageB;
        if (!imageS.equals("")) {
            imageB = decodeToBase64(imageS);
            ivNavDrawer.setImageBitmap(imageB);

        }


        String nume = Utile.preluareUsername(getApplicationContext());

        tvNavDrawer.setText(nume);

        final String stare = Utile.preluareStareAnalize(getApplicationContext());


        btnVreauSaDonez = (Button) findViewById(R.id.btnVreauSaDonez);
        btnVreauSaDonez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertMessageVreauSaDonez();

            }
        });


        btnAlerte = (Button) findViewById(R.id.btnAlerte);
        btnAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransparentActivity.class);
                startActivity(intent);


            }
        });


        btnNotificari = (Button) findViewById(R.id.btnNotificari);
        btnNotificari.setVisibility(View.GONE);
        btnNotificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scheduleNotificationWithDelay(triggerNotification(),3000);
//                scheduleNotification(triggerNotification());
//                triggerBasicNotification();

            }
        });

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setVisibility(View.GONE);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendNotification();


            }
        });

        btnReceiveri = (Button) findViewById(R.id.btnReceiveri);
        btnReceiveri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utile.REST_GET_Receiveri(PrimaPaginaActivity.this, ListaReceiveriActivity.class);
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
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
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
        } else if (id == R.id.listaCentre) {
            Intent intent = new Intent(getApplicationContext(), ListaCentreActivity.class);
            ListaCentreActivity.mapListaDistante=new HashMap<>();
            try {
                MapsCTSFragment.locatieCurenta = CalculDistante.getMyLocation(PrimaPaginaActivity.this);
                for (CTS cts : Utile.CTS) {
                    ListaCentreActivity.mapListaDistante.put(cts, CalculDistante.distanceBetweenTwoCoordinates(MapsCTSFragment.locatieCurenta.getLatitude(), MapsCTSFragment.locatieCurenta.getLongitude(), cts.getCoordonataXCTS(), cts.getCoordonataYCTS()));
                }
            }catch (Exception ex){

            }
            double minDistance=Double.MAX_VALUE;
            for(CTS cts:ListaCentreActivity.mapListaDistante.keySet())
                if(ListaCentreActivity.mapListaDistante.get(cts)<minDistance){
                    minDistance=ListaCentreActivity.mapListaDistante.get(cts);
                    ListaCentreActivity.closestCTS=cts;
                }
            startActivity(intent);
        } else if (id == R.id.stareAnalize) {
            startActivity(new Intent(getApplicationContext(), ActualizareStareAnalizeActivity.class));
        } else if (id == R.id.compatibilitati) {
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
            String url = Utile.URL + "domain.istoricdonatii/donator/" + preluareEmail(getApplicationContext());

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
            editor.putString("imagePreferance", "");
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
        if (Utile.preluareStareAnalize(getApplicationContext()).equals("ok")) {
            final boolean[] eligibil = new boolean[1];
            final long[] perioadaRamasa = new long[1];
            Thread thread = new Thread(new Thread() {
                @Override
                public void run() {

                    try {
                        gson = new Gson();

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        RequestFuture<JSONObject> future = RequestFuture.newFuture();
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.istoricdonatii/last/donator/" + preluareEmail(context), null, future, future);
                        requestQueue.add(request);
                        JSONObject response = future.get();

                        IstoricDonatii istoricDonatie = gson.fromJson(response.toString(), IstoricDonatii.class);
                        Calendar curent = Calendar.getInstance();
                        Calendar donatie = Calendar.getInstance();
                        Instant instant = Instant.parse(istoricDonatie.getDataDonatie());
                        donatie.setTimeInMillis(instant.getMillis());

                        eligibil[0] = curent.getTimeInMillis() - donatie.getTimeInMillis() >= 1000L * 60 * 60 * 24 * 56;
                        perioadaRamasa[0] =curent.getTimeInMillis() - donatie.getTimeInMillis();
                        if (eligibil[0]) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(PrimaPaginaActivity.this);
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

                                                }
                                            })
                                            .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                                                public void onClick(final DialogInterface dialog, final int id) {


                                                    Intent intent = new Intent(getApplicationContext(), ListaCentreActivity.class);
                                                    startActivity(intent);


                                                }
                                            });
                                    final AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });

                        } else {

                            Intent intent = new Intent(getApplicationContext(), OptSaptamaniActivity.class);
                            intent.putExtra("perioadaramasa",perioadaRamasa[0]);
                            startActivity(intent);
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
            });
            thread.start();

        } else if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok")) {
            Intent intent = new Intent(getApplicationContext(), AnalizeNotOkActivity.class);
            startActivity(intent);
        } else if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate")) {
            Intent intent = new Intent(getApplicationContext(), AnalizeNeefectuateActivity.class);
            startActivity(intent);
        }
    }

    @SuppressLint("NewApi")
    public void sendNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "test")
                        .setSmallIcon(R.drawable.blood)
                        .setColor(context.getColor(R.color.colorPrimary))
                        .setContentTitle("Reminder Programare")
                        .setContentText("10:30 24/10/2020")
                        .setChannelId("test")
                        .setAutoCancel(true)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);



        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("test", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
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

        mNotificationManager.notify(2, mBuilder.build());
    }


    @SuppressLint("NewApi")
    public static void triggerBasicNotification() {
        Intent resultIntent = new Intent(context, ActionAlerteBroadcast.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        Intent backIntent = new Intent(this, PrimaPaginaActivity.class);


//        PendingIntent resultPendingIntent = PendingIntent.getActivities(getApplicationContext(),
//                0 /* Request code */, new Intent[]{backIntent, resultIntent},
//                PendingIntent.FLAG_ONE_SHOT);

//        Intent listaAlerteActionIntent = new Intent(getApplicationContext(), ActionCentreBroadcast.class);
        PendingIntent listaAlerteActionPendingIntent =
                PendingIntent.getBroadcast(context, 0, resultIntent, 0);


        Intent listaCentreActionIntent = new Intent(context, ActionCentreBroadcast.class);
        PendingIntent listaCentreActionPendingIntent =
                PendingIntent.getBroadcast(context, 0, listaCentreActionIntent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "test")
                        .setSmallIcon(R.drawable.blood)
                        .setColor(context.getColor(R.color.colorPrimary))
                        .setContentTitle("Alerta de sange")
                        .setContentText("Vezi situatia actuala")
                        .setChannelId("test")
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_location, "Vezi centrele disponibile", listaCentreActionPendingIntent)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);

        mBuilder.setContentIntent(listaAlerteActionPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("test", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
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

        mNotificationManager.notify(1, mBuilder.build());

    }

    public static Notification triggerNotification() {
        Intent resultIntent = new Intent(PrimaPaginaActivity.context, ActionAlerteBroadcast.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent listaCentreActionIntent = new Intent(PrimaPaginaActivity.context, ActionCentreBroadcast.class);
        PendingIntent listaCentreActionPendingIntent =
                PendingIntent.getBroadcast(PrimaPaginaActivity.context, 0, listaCentreActionIntent, 0);


        PendingIntent listaAlerteActionPendingIntent =
                PendingIntent.getBroadcast(PrimaPaginaActivity.context, 0, resultIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(PrimaPaginaActivity.context, "test")
                        .setSmallIcon(R.drawable.blood)
                        .setWhen(System.currentTimeMillis())
                        .setShowWhen(true)
                        .setColor(PrimaPaginaActivity.context.getResources().getColor(R.color.colorPrimary))
                        .setContentTitle("Alerta de sange")
                        .setContentText("Vezi situatia actuala")
                        .setChannelId("test")
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_location, "Vezi centrele disponibile", listaCentreActionPendingIntent)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);

        mBuilder.setContentIntent(listaAlerteActionPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) PrimaPaginaActivity.context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("test", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
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

    private void scheduleNotificationWithDelay(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificariBroadcast.class);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public static void scheduleNotification(Notification notification, boolean isActive) {

        Intent notificationIntent = new Intent(PrimaPaginaActivity.context, NotificariBroadcast.class);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PrimaPaginaActivity.context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) PrimaPaginaActivity.context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 13);
//        calendar.set(Calendar.MINUTE, 24);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);

//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        if (isActive)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), 60 * 1000, pendingIntent);

        else
            alarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20 && data != null) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                ivNavDrawer.setImageBitmap(selectedImage);


                SharedPreferences myPrefrence = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefrence.edit();
                editor.putString("imagePreferance", encodeToBase64(selectedImage));

                editor.commit();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PrimaPaginaActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }



}
