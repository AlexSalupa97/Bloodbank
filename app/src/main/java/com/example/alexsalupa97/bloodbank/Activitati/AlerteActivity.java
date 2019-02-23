package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import com.example.alexsalupa97.bloodbank.Clase.LimiteCTS;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlerteActivity extends AppCompatActivity {

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    TextView tvDetaliiLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerte);

        tvDetaliiLimite=(TextView)findViewById(R.id.tvDetaliiLimite);
        tvDetaliiLimite.setText("");

        try {
            mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil());
            mapLimitePerCTSPerGrupa = new HashMap<>();


            for (CTS cts : Utile.CTS) {
                Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                for (LimiteCTS limite : Utile.listaLimiteCTS)
                    if (limite.getCts().getNumeCTS().equals(cts.getNumeCTS()))
                        mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                mapLimitePerCTSPerGrupa.put(cts, mapIntermediar);
            }

            for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                String deAfisat = "";

                if (cts.getOras().getOras().equals(Utile.preluareOras(getApplicationContext()))) {
                    Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                    Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


                    deAfisat += cts.getNumeCTS();

                    for (GrupeSanguine grupaSanguina : mapLimite.keySet())
                        if (mapCantitatiDisponibile.get(grupaSanguina) < mapLimite.get(grupaSanguina))
                            deAfisat += "\n probleme cu " + grupaSanguina.getGrupaSanguina()+ " limita: " +mapLimite.get(grupaSanguina)+ " disponibil: "+mapCantitatiDisponibile.get(grupaSanguina);
                }

                tvDetaliiLimite.setText(tvDetaliiLimite.getText()+deAfisat);





            }

        } catch (Exception ex) {

        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
