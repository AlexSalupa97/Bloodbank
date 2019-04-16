package ro.alexsalupa97.bloodbank.Activitati;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteStatisticiReceiver;
import ro.alexsalupa97.bloodbank.R;

public class StatisticiReceiverActivity extends AppCompatActivity {

    ViewPager vpStatistici;
    TabLayout tlStatistici;
    AdaptorFragmenteStatisticiReceiver adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistici_receiver);

        adaptor = new AdaptorFragmenteStatisticiReceiver(getSupportFragmentManager());
        vpStatistici = (ViewPager) findViewById(R.id.vpStatisticiReceiver);
        vpStatistici.setAdapter(adaptor);

        tlStatistici = (TabLayout) findViewById(R.id.tlStatisticiReceiver);
        tlStatistici.setupWithViewPager(vpStatistici);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
