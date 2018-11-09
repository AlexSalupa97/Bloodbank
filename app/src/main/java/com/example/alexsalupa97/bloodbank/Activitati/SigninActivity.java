package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexsalupa97.bloodbank.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tipUseri.add("Donator");
        tipUseri.add("Receiver");
        tipUseri.add("CTS");

        adaptorSpinner = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tipUseri);

        spSignin = (Spinner) findViewById(R.id.spSignin);
        spSignin.setAdapter(adaptorSpinner);

        btnSignin = (Button) findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                if (spSignin.getSelectedItem().toString().toLowerCase().equals("donator")) {

                    String url = "http://4e266df6.ngrok.io/ProiectLicentaBloodBank/webresources/domain.donatori/" + username;

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
                                    String DBNume=null;
                                    try {
                                        DBUsername = response.getString("emaildonator");
                                        DBParola = response.getString("paroladonator");
                                        DBNume = response.getString("numedonator");

                                        if (DBUsername.equals(etUsername.getText().toString()) && DBParola.equals(etPassword.getText().toString())) {


                                            SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();

                                            editor.putString("login_name", DBNume);
                                            editor.putString("tip_user", spSignin.getSelectedItem().toString().toLowerCase());


                                            editor.commit();

                                            Intent intent = new Intent(getApplicationContext(), PrimaPaginaActivity.class);
                                            setResult(1);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
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
                }
            }
        });
    }
}

