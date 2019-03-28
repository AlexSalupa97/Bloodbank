package ro.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricDonatiiLV;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiSuplimentareIstoricActivity extends AppCompatActivity {

    ListView lvIstoric;
    AdaptorIstoricDonatiiLV adaptorIstoricDonatiiLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_suplimentare_istoric);

        lvIstoric=(ListView)findViewById(R.id.lvIstoric);
        adaptorIstoricDonatiiLV=new AdaptorIstoricDonatiiLV(getApplicationContext(), Utile.listaIstoricDonatii);
        lvIstoric.setAdapter(adaptorIstoricDonatiiLV);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
