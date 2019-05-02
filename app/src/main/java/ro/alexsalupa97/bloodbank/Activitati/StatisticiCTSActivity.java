package ro.alexsalupa97.bloodbank.Activitati;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteStatisticiCTS;
import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteStatisticiReceiver;
import ro.alexsalupa97.bloodbank.R;

public class StatisticiCTSActivity extends AppCompatActivity {

    public static TextView tvStatistici;

    ViewPager vpStatistici;
    TabLayout tlStatistici;
    AdaptorFragmenteStatisticiCTS adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistici_cts);

        getSupportActionBar().setElevation(0);

        StatisticiCTSActivity.tvStatistici=(TextView)findViewById(R.id.tvStatistici);

        adaptor = new AdaptorFragmenteStatisticiCTS(getSupportFragmentManager());
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
