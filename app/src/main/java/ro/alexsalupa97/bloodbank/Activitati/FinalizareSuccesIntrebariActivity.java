package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FinalizareSuccesIntrebariActivity extends AppCompatActivity {


    Button btnCentreRecoltare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizare_succes_intrebari);

        btnCentreRecoltare = (Button) findViewById(R.id.btnCentreRecoltare);
        btnCentreRecoltare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                Intent intent = new Intent(getApplicationContext(), ListaCentreActivity.class);
                startActivity(intent);

            }
        });

    }
}
