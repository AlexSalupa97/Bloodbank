package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteCTS;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.CalculDistante;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class ListaCentreActivity extends AppCompatActivity {

    AdaptorFragmenteCTS adaptor;
    ViewPager viewPager;

    public static CTS closestCTS;
    public static HashMap<CTS,Double> mapListaDistante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_centre);

        getSupportActionBar().setElevation(0);

        mapListaDistante=new HashMap<>();
        try {
            MapsCTSFragment.locatieCurenta = CalculDistante.getMyLocation(ListaCentreActivity.this);
            for (CTS cts : Utile.CTS) {
                mapListaDistante.put(cts, CalculDistante.distanceBetweenTwoCoordinates(MapsCTSFragment.locatieCurenta.getLatitude(), MapsCTSFragment.locatieCurenta.getLongitude(), cts.getCoordonataXCTS(), cts.getCoordonataYCTS()));
            }
        }catch (Exception ex){

        }

        double minDistance=Double.MAX_VALUE;
        for(CTS cts:mapListaDistante.keySet())
            if(mapListaDistante.get(cts)<minDistance){
                minDistance=mapListaDistante.get(cts);
                closestCTS=cts;
            }

        adaptor = new AdaptorFragmenteCTS(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.listaCTSViewPager);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.listaCTSTab);
        tabLayout.setupWithViewPager(viewPager);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),PrimaPaginaActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(getApplicationContext(),PrimaPaginaActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
