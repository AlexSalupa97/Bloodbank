package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class SplashScreenActivity extends AppCompatActivity {

    String fisier = "SharedPreferences";
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash_screen);

        Utile.firstTimeReceiver = true;


        if(isNetworkAvailable()) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {

                        gson = new Gson();

                        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreenActivity.this);
                        RequestFuture<JSONArray> future = RequestFuture.newFuture();
                        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.cts", null, future, future);
                        RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future1, future1);

                        requestQueue.add(request);
                        requestQueue.add(request1);


                        requestQueue.add(request);
                        requestQueue.add(request1);

                        JSONArray response = future.get();
                        JSONArray response1 = future1.get();

                        Utile.CTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), CTS[].class)));
                        Utile.orase = new HashSet<>();
                        for (CTS cts : Utile.CTS)
                            Utile.orase.add(cts.getOras());

                        Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), GrupeSanguine[].class)));

                        sleep(500);

                        SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                        String defaultName = "";

//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("login_name", "");
//                    editor.commit();

                        String name = sharedPreferences.getString("login_name", defaultName);


                        if (name.equals("")) {

                            Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (Utile.preluareTipUser(getApplicationContext()).equals("donator")) {

                            RequestFuture<JSONObject> future2 = RequestFuture.newFuture();
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.stareanalize/" + Utile.preluareEmail(getApplicationContext()), null, future2, future2);

                            requestQueue.add(request2);

                            JSONObject response2 = future2.get();

                            sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("stareAnalize", response2.getString("stareanalize"));
                            editor.commit();

                            Intent mainIntent = new Intent(getApplicationContext(), PrimaPaginaActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else if (Utile.preluareTipUser(getApplicationContext()).equals("receiver")) {
                            Intent mainIntent = new Intent(getApplicationContext(), DetaliiReceiverMainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(getApplicationContext(), DetaliiCTSMainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    } catch (InterruptedException e) {
                        Log.e("SplashScreenActivity", e.getMessage());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };
            t.start();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Este necesara o conexiune la internet pentru a putea utiliza aplicatia")
                    .setCancelable(false)
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),47);
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(isNetworkAvailable()) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {

                        gson = new Gson();

                        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreenActivity.this);
                        RequestFuture<JSONArray> future = RequestFuture.newFuture();
                        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.cts", null, future, future);
                        RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future1, future1);

                        requestQueue.add(request);
                        requestQueue.add(request1);


                        requestQueue.add(request);
                        requestQueue.add(request1);

                        JSONArray response = future.get();
                        JSONArray response1 = future1.get();

                        Utile.CTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), CTS[].class)));
                        Utile.orase = new HashSet<>();
                        for (CTS cts : Utile.CTS)
                            Utile.orase.add(cts.getOras());

                        Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), GrupeSanguine[].class)));

                        sleep(500);

                        SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                        String defaultName = "";

//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("login_name", "");
//                    editor.commit();

                        String name = sharedPreferences.getString("login_name", defaultName);


                        if (name.equals("")) {

                            Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (Utile.preluareTipUser(getApplicationContext()).equals("donator")) {

                            RequestFuture<JSONObject> future2 = RequestFuture.newFuture();
                            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, Utile.URL + "domain.stareanalize/" + Utile.preluareEmail(getApplicationContext()), null, future2, future2);

                            requestQueue.add(request2);

                            JSONObject response2 = future2.get();

                            sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("stareAnalize", response2.getString("stareanalize"));
                            editor.commit();

                            Intent mainIntent = new Intent(getApplicationContext(), PrimaPaginaActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else if (Utile.preluareTipUser(getApplicationContext()).equals("receiver")) {
                            Intent mainIntent = new Intent(getApplicationContext(), DetaliiReceiverMainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(getApplicationContext(), DetaliiCTSMainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        }
                    } catch (InterruptedException e) {
                        Log.e("SplashScreenActivity", e.getMessage());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            };
            t.start();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Este necesara o conexiune la internet pentru a putea utiliza aplicatia")
                    .setCancelable(false)
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),47);
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }

    }
}


