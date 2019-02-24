package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteAlerte;
import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteCTS;
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

    AdaptorFragmenteAlerte adaptor;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerte);

        adaptor = new AdaptorFragmenteAlerte(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.vpAlerte);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlAlerte);
        tabLayout.setupWithViewPager(viewPager);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(getApplicationContext(),PrimaPaginaActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),PrimaPaginaActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
