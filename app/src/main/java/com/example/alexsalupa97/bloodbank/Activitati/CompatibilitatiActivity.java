package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;

public class CompatibilitatiActivity extends AppCompatActivity {

    ListView lvDoneazaLa;
    ListView lvPrimesteDeLa;

    ArrayAdapter<String> adaptorDoneazaLa;
    ArrayAdapter<String> adaptorPrimesteDeLa;

    ArrayList<String> listaDoneazaLa = new ArrayList<>();
    ArrayList<String> listaPrimesteDeLa = new ArrayList<>();

    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibilitati);

        tvInfo=(TextView)findViewById(R.id.tvCompatibilitatiGrupaTa);
        tvInfo.setText("Grupa ta, "+Utile.preluareGrupaSanguina(getApplicationContext()));

        lvDoneazaLa = (ListView) findViewById(R.id.lvDoneazaLa);
        lvPrimesteDeLa=(ListView)findViewById(R.id.lvPrimesteDeLa);

        for (Compatibilitati c : Utile.compatibilitati) {
            if(c.getGrupaSanguinaDonatoare().getGrupaSanguina().equals(Utile.preluareGrupaSanguina(getApplicationContext()))&&c.getGrupaSanguinaReceiver().getGrupaSanguina().equals(Utile.preluareGrupaSanguina(getApplicationContext())))
            {
                listaDoneazaLa.add(c.getGrupaSanguinaDonatoare().getGrupaSanguina());
                listaPrimesteDeLa.add(c.getGrupaSanguinaDonatoare().getGrupaSanguina());
            }
            else
            if(c.getGrupaSanguinaDonatoare().getGrupaSanguina().equals(Utile.preluareGrupaSanguina(getApplicationContext())))
                listaDoneazaLa.add(c.getGrupaSanguinaReceiver().getGrupaSanguina());
            else
                listaPrimesteDeLa.add(c.getGrupaSanguinaDonatoare().getGrupaSanguina());
        }

        adaptorDoneazaLa=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listaDoneazaLa);
        adaptorPrimesteDeLa=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,listaPrimesteDeLa);

        lvDoneazaLa.setAdapter(adaptorDoneazaLa);
        lvPrimesteDeLa.setAdapter(adaptorPrimesteDeLa);

    }
}
