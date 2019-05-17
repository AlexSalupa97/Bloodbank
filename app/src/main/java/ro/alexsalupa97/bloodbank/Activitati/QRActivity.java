package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class QRActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    private String emailDonator;
    private String stareAnalize;
    String fisier = "SharedPreferences";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        StringReader input = new StringReader(rawResult.getContents());
        BufferedReader lineReader = new BufferedReader(input);
        String line;

        emailDonator="";
        stareAnalize="";

        while (true) {
            try {
                if ((line = lineReader.readLine()) == null) break;
                else {
                    emailDonator+=line;
                    stareAnalize+=lineReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Donatori donator = Utile.preluareDonator(getApplicationContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
        builder.setMessage("Verificati validitatea datelor preluate din fisierul PDF: \nEmail: " + emailDonator + "\nStare analize: " + stareAnalize);
        builder.setCancelable(true);
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mScannerView.resumeCameraPreview(QRActivity.this::handleResult);
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, final int id) {
                String url = Utile.URL + "domain.stareanalize/" + Utile.preluareIDAnalize(getApplicationContext());

                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                final JSONObject jsonDonator = new JSONObject();
                final JSONObject jsonOras = new JSONObject();
                final JSONObject jsonGrupaSanguina = new JSONObject();
                final JSONObject jsonStareAnalize = new JSONObject();

                try {

                    jsonGrupaSanguina.put("idgrupasanguina", donator.getGrupaSanguina().getGrupaSanguina());

                    jsonOras.put("idoras", donator.getOrasDonator().getIdOras());
                    jsonOras.put("judet", donator.getOrasDonator().getJudet());
                    jsonOras.put("numeoras", donator.getOrasDonator().getOras());

                    jsonDonator.put("emaildonator", donator.getEmailDonator());
                    jsonDonator.put("iddonator", donator.getIdDonator());
                    jsonDonator.put("idgrupasanguina", jsonGrupaSanguina);
                    jsonDonator.put("idoras", jsonOras);
                    jsonDonator.put("numedonator", donator.getNumeDonator());
                    jsonDonator.put("telefondonator", donator.getTelefonDonator());

                    jsonStareAnalize.put("dataefectuareanaliza", Utile.preluareDataAnalize(getApplicationContext()));
                    jsonStareAnalize.put("iddonator", jsonDonator);
                    jsonStareAnalize.put("idstareanaliza", Utile.preluareIDAnalize(getApplicationContext()));
                    jsonStareAnalize.put("stareanalize", stareAnalize);

                } catch (JSONException e) {

                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                        url, jsonStareAnalize,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("stareAnalize", stareAnalize);

                                editor.commit();



                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                if (error.toString().contains("ServerError")) {
                                    Toast.makeText(getApplicationContext(), "Eroare de server", Toast.LENGTH_LONG).show();
                                    Log.d("restresponse", error.toString());
                                    System.out.println(error.toString());
                                }


                            }
                        }) {

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                        try {
                            String json = new String(
                                    response.data,
                                    "UTF-8"
                            );

                            if (json.length() == 0) {
                                return Response.success(
                                        null,
                                        HttpHeaderParser.parseCacheHeaders(response)
                                );
                            } else {
                                return super.parseNetworkResponse(response);
                            }
                        } catch (UnsupportedEncodingException e) {
                            return Response.error(new ParseError(e));
                        }


                    }
                };
                requestQueue.add(jsonObjReq);
                setResult(1);
                finish();

            }
        });
        if(donator.getEmailDonator().equals(emailDonator)) {
            final AlertDialog alert = builder.create();
            alert.show();
        }else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Codul este necorespunzator", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
                    mScannerView.resumeCameraPreview(this);
        }
    }


    // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
}

