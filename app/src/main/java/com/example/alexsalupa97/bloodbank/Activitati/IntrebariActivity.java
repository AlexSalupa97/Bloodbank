package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteIntrebari;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

public class IntrebariActivity extends AppCompatActivity {

    AdaptorFragmenteIntrebari adaptorIntrebari;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_intrebari);


        adaptorIntrebari = new AdaptorFragmenteIntrebari(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPagerIntrebari);
        viewPager.setAdapter(adaptorIntrebari);
    }
}
