package ro.alexsalupa97.bloodbank.Activitati;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

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

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

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

        adaptorDoneazaLa=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaDoneazaLa){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                text.setGravity(Gravity.CENTER);
                return view;
            }
        };
        adaptorPrimesteDeLa=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaPrimesteDeLa){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setGravity(Gravity.CENTER);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };

        lvDoneazaLa.setAdapter(adaptorDoneazaLa);
        lvPrimesteDeLa.setAdapter(adaptorPrimesteDeLa);

        lvDoneazaLa.setDividerHeight(0);
        lvPrimesteDeLa.setDividerHeight(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
