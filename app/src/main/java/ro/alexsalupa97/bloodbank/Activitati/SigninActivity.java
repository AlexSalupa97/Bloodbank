package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SigninActivity extends AppCompatActivity {

    String fisier = "SharedPreferences";

    Spinner spSignin;
    ArrayList<String> tipUseri = new ArrayList<>();
    ArrayAdapter<String> adaptorSpinner;
    Button btnSignin;
    EditText etUsername;
    EditText etPassword;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tipUseri.add("Donator");
        tipUseri.add("Receiver");
        tipUseri.add("CTS");

        adaptorSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tipUseri) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;
            }
        };

        spSignin = (Spinner) findViewById(R.id.spSignin);
        spSignin.setAdapter(adaptorSpinner);

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                if (spSignin.getSelectedItem().toString().toLowerCase().equals("donator")) {

                    String url = Utile.URL + "domain.stareanalize/" + username;

                    final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {


                                    String DBUsername = null;
                                    String DBParola = null;
                                    String DBNume = null;
                                    String DBGrupaSanguina = null;
                                    String DBStareAnalize = null;
                                    String DBOras = null;
                                    String DBJudet = null;
                                    String DBTelefon = null;
                                    try {
                                        JSONObject jsonDonator = response.getJSONObject("iddonator");
                                        JSONObject jsonGrupaSanguina = jsonDonator.getJSONObject("idgrupasanguina");
                                        DBTelefon = jsonDonator.getString("telefondonator");
                                        JSONObject jsonOras = jsonDonator.getJSONObject("idoras");
                                        DBUsername = jsonDonator.getString("emaildonator");
                                        DBNume = jsonDonator.getString("numedonator");
                                        DBGrupaSanguina = jsonGrupaSanguina.getString("idgrupasanguina");
                                        DBStareAnalize = response.getString("stareanalize");
                                        DBJudet = jsonOras.getString("judet");
                                        DBOras = jsonOras.getString("numeoras");


                                        if (DBUsername.equals(etUsername.getText().toString())) {


                                            SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString("login_name", DBNume);
                                            editor.putString("tip_user", spSignin.getSelectedItem().toString().toLowerCase());
                                            editor.putString("grupaSanguina", DBGrupaSanguina);
                                            editor.putString("stareAnalize", DBStareAnalize);
                                            editor.putString("orasUser", DBOras);
                                            editor.putString("judetUser", DBJudet);
                                            editor.putString("email", DBUsername);
                                            editor.putString("telefon", DBTelefon);
                                            editor.putString("donator", jsonDonator.toString());

                                            editor.commit();


                                            Intent intent = new Intent(getApplicationContext(), PrimaPaginaActivity.class);
                                            setResult(100);
                                            startActivity(intent);
                                            finish();

                                        } else
                                            Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {

                                        Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();


                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();

                                }
                            });
                    requestQueue.add(objectRequest);
                } else if (spSignin.getSelectedItem().toString().toLowerCase().equals("receiver")) {
                    String url = Utile.URL + "domain.receiveri/email/" + username;

                    final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {


                                    String DBUsername = null;
                                    String DBParola = null;
                                    String DBNume = null;
                                    String DBGrupaSanguina = null;
                                    String DBCTS = null;
                                    String DBTelefon = null;
                                    String DBID = null;
                                    try {
                                        JSONObject jsonGrupaSanguina = response.getJSONObject("idgrupasanguina");
                                        DBGrupaSanguina = jsonGrupaSanguina.getString("idgrupasanguina");
                                        DBTelefon = response.getString("telefonreceiver");
                                        JSONObject jsonCTS = response.getJSONObject("idcts");
                                        DBCTS = jsonCTS.getString("numects");
                                        DBUsername = response.getString("emailreceiver");
                                        DBNume = response.getString("numereceiver");
                                        DBID = response.getString("idreceiver");


                                        if (DBUsername.equals(etUsername.getText().toString())) {


                                            SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString("login_name", DBNume);
                                            editor.putString("tip_user", spSignin.getSelectedItem().toString().toLowerCase());
                                            editor.putString("grupaSanguina", DBGrupaSanguina);
                                            editor.putString("cts", DBCTS);
                                            editor.putString("email", DBUsername);
                                            editor.putString("telefon", DBTelefon);
                                            editor.putString("id", DBID);

                                            editor.commit();


                                            Intent intent = new Intent(getApplicationContext(), DetaliiReceiverMainActivity.class);
                                            setResult(100);
                                            startActivity(intent);
                                            finish();

                                        } else
                                            Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {

                                        Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();


                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();

                                }
                            });
                    requestQueue.add(objectRequest);

                } else {
                    String url = Utile.URL + "domain.cts/email/" + username;


                    final RequestQueue requestQueue = Volley.newRequestQueue(SigninActivity.this);

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if (response.length() != 0) {


                                        SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString("login_name", response.toString());


                                        editor.commit();


                                        Intent intent = new Intent(getApplicationContext(), DetaliiCTSMainActivity.class);
                                        setResult(100);
                                        startActivity(intent);
                                        finish();

                                    } else
                                        Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();


                                }


                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getApplicationContext(), "Introduceti credentialele corecte", Toast.LENGTH_LONG).show();

                                }
                            });
                    requestQueue.add(objectRequest);


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
        startActivity(intent);
        finish();
    }
}

