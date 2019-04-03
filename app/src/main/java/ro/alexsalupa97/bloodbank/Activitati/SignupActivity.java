package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
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


        adaptor = new AdaptorFragmenteSignup(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.loginViewpager);
        viewPager.setAdapter(adaptor);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.loginTab);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
