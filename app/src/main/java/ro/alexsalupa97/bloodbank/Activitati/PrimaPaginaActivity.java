package ro.alexsalupa97.bloodbank.Activitati;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.Intrebari;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import ro.alexsalupa97.bloodbank.Notificari.ActionAlerteBroadcast;
import ro.alexsalupa97.bloodbank.Notificari.ActionCentreBroadcast;
import ro.alexsalupa97.bloodbank.Notificari.NotificariBroadcast;
import ro.alexsalupa97.bloodbank.Utile.CalculDistante;
import ro.alexsalupa97.bloodbank.Utile.Utile;
import ro.alexsalupa97.bloodbank.R;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static java.lang.Thread.sleep;

public class PrimaPaginaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String fisier = "SharedPreferences";
    SharedPreferences sharedPreferences;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TextView tvNavDrawer;
    ImageView ivNavDrawer;

    private final int COD=20;

    Button btnVreauSaDonez;
    Button btnAlerte;
    Button btnNotificari;
    Button btnReceiveri;

    Gson gsonIntrebari;
    Gson gsonCompatibilitati;
    Gson gsonIstoricDonatii;
    List<Intrebari> intrebariList;
    List<Compatibilitati> compatibilitatiList;
    List<IstoricDonatii> istoricDonatiiList;

    Uri imageUri;
    Bitmap bitmap;

    Gson gsonCTS;
    List<CTS> CTSlist;

    ProgressDialog pd;


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

        MapsCTSFragment.locatieCurenta = CalculDistante.getMyLocation(PrimaPaginaActivity.this);


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
        ivNavDrawer = (ImageView) headerView.findViewById(R.id.nav_header_imageView);

        ivNavDrawer.setOnClickListener((View view)->{

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 20);
        });

        SharedPreferences myPrefrence = getSharedPreferences(fisier,Context.MODE_PRIVATE);
        String imageS = myPrefrence.getString("imagePreferance", "");
        Bitmap imageB;
        if(!imageS.equals("")) {
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


        btnNotificari = (Button)

                findViewById(R.id.btnNotificari);
        btnNotificari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scheduleNotificationWithDelay(triggerNotification(),3000);
//                scheduleNotification(triggerNotification());
                triggerBasicNotification();

            }
        });

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setVisibility(View.GONE);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(intent);

//                Intent twitterIntent = getShareIntent("twitter", "subject", "text: " + "https://play.google.com/store/apps/developer?id=AlexSalupa97");
//                if (twitterIntent != null)
//                    startActivity(twitterIntent);

//                Intent facebookIntent = getShareIntent("facebook", "", "https://play.google.com/store/apps/developer?id=AlexSalupa97");
//                if(facebookIntent != null)
//                    startActivity(facebookIntent);


            }
        });

        btnReceiveri = (Button)

                findViewById(R.id.btnReceiveri);
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
            startActivity(intent);
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
            editor.putString("imagePreferance","");
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
        } else if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok")) {
            Intent intent = new Intent(getApplicationContext(), AnalizeNotOkActivity.class);
            startActivity(intent);
        } else if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate")) {
            Intent intent = new Intent(getApplicationContext(), AnalizeNeefectuateActivity.class);
            startActivity(intent);
        }
    }

    private void triggerBasicNotification() {
        Intent resultIntent = new Intent(getApplicationContext(), ActionAlerteBroadcast.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        Intent backIntent = new Intent(this, PrimaPaginaActivity.class);


//        PendingIntent resultPendingIntent = PendingIntent.getActivities(getApplicationContext(),
//                0 /* Request code */, new Intent[]{backIntent, resultIntent},
//                PendingIntent.FLAG_ONE_SHOT);

//        Intent listaAlerteActionIntent = new Intent(getApplicationContext(), ActionCentreBroadcast.class);
        PendingIntent listaAlerteActionPendingIntent =
                PendingIntent.getBroadcast(this, 0, resultIntent, 0);


        Intent listaCentreActionIntent = new Intent(getApplicationContext(), ActionCentreBroadcast.class);
        PendingIntent listaCentreActionPendingIntent =
                PendingIntent.getBroadcast(this, 0, listaCentreActionIntent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "test")
                        .setSmallIcon(R.drawable.blood)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentTitle("Alerta de sange")
                        .setContentText("Vezi situatia actuala")
                        .setChannelId("test")
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_location, "Vezi centrele disponibile", listaCentreActionPendingIntent)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);

        mBuilder.setContentIntent(listaAlerteActionPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


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

    private Notification triggerNotification() {
        Intent resultIntent = new Intent(getApplicationContext(), ActionAlerteBroadcast.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent backIntent = new Intent(this, PrimaPaginaActivity.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent listaCentreActionIntent = new Intent(getApplicationContext(), ActionCentreBroadcast.class);
        PendingIntent listaCentreActionPendingIntent =
                PendingIntent.getBroadcast(this, 0, listaCentreActionIntent, 0);


//        PendingIntent resultPendingIntent = PendingIntent.getActivities(getApplicationContext(),
//                0 /* Request code */, new Intent[]{backIntent, resultIntent},
//                PendingIntent.FLAG_ONE_SHOT);

//        PendingIntent resultPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
//                0 /* Request code */, resultIntent,
//                PendingIntent.FLAG_ONE_SHOT);

        PendingIntent listaAlerteActionPendingIntent =
                PendingIntent.getBroadcast(this, 0, resultIntent, 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "test")
                        .setSmallIcon(R.drawable.blood)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentTitle("Alerta de sange")
                        .setContentText("Vezi situatia actuala")
                        .setChannelId("test")
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_location, "Vezi centrele disponibile", listaCentreActionPendingIntent)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);

        mBuilder.setContentIntent(listaAlerteActionPendingIntent);


        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


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

    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificariBroadcast.class);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificariBroadcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 56);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            pd.dismiss();
//            Intent intent = new Intent(getApplicationContext(), AlerteActivity.class);
//            startActivity(intent);
//
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == 20) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivNavDrawer.setImageBitmap(selectedImage);

                SharedPreferences myPrefrence = getSharedPreferences(fisier,Context.MODE_PRIVATE);
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
