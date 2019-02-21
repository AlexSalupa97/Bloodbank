package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.HashMap;
import java.util.Map;

public class AlerteActivity extends AppCompatActivity {

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerte);

        mapCantitatiDisponibilePerCTSPerGrupa= new HashMap<>(Utile.incarcareMapDisponibil());


    }
}
