package com.example.alexsalupa97.bloodbank.Activitati;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteSignup;
import com.example.alexsalupa97.bloodbank.R;

public class SignupActivity extends AppCompatActivity {

    AdaptorFragmenteSignup adaptor;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        adaptor = new AdaptorFragmenteSignup(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.loginViewpager);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.loginTab);
        tabLayout.setupWithViewPager(viewPager);
    }
}
