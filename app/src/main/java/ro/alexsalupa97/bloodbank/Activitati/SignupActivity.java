package ro.alexsalupa97.bloodbank.Activitati;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteSignup;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class SignupActivity extends AppCompatActivity {

    AdaptorFragmenteSignup adaptor;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Utile.REST_GET_countDonatori(this);
        Utile.REST_GET_countReceiveri(this);


        adaptor = new AdaptorFragmenteSignup(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.loginViewpager);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.loginTab);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
