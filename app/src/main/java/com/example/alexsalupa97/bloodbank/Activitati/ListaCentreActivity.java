package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteCTS;
import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteSignup;
import com.example.alexsalupa97.bloodbank.R;

public class ListaCentreActivity extends AppCompatActivity {

    AdaptorFragmenteCTS adaptor;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_centre);

        adaptor = new AdaptorFragmenteCTS(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.listaCTSViewPager);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.listaCTSTab);
        tabLayout.setupWithViewPager(viewPager);
    }
}
