package ro.alexsalupa97.bloodbank.Activitati;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteStatisticiReceiver;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiSaptamanaleReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiZilniceReceiverFragment;
import ro.alexsalupa97.bloodbank.R;

public class StatisticiReceiverActivity extends AppCompatActivity {

    public static TextView tvStatistici;

    ViewPager vpStatistici;
    TabLayout tlStatistici;
    AdaptorFragmenteStatisticiReceiver adaptor;

    public static boolean dejaAdaugatZilnic=false;
    public static boolean dejaAdaugatSaptamanal=false;
    public static boolean dejaAdaugatLunar=false;
    public static boolean dejaAdaugatAnual=false;

    public static ArrayList<String> listaStatistici=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistici_receiver);

        getSupportActionBar().setElevation(0);

        StatisticiReceiverActivity.tvStatistici=(TextView)findViewById(R.id.tvStatistici);

        adaptor = new AdaptorFragmenteStatisticiReceiver(getSupportFragmentManager());
        vpStatistici = (ViewPager) findViewById(R.id.vpStatisticiReceiver);
        vpStatistici.setAdapter(adaptor);

        tlStatistici = (TabLayout) findViewById(R.id.tlStatisticiReceiver);
        tlStatistici.setupWithViewPager(vpStatistici);



//        if (StatisticiZilniceReceiverFragment.valoareData != null) {
//            listaStatistici.add("In aceasta zi, ora cea mai \"bogata\" in donatii a fost " + StatisticiZilniceReceiverFragment.valoareData + " ,cu o cantitate de sange primita de " + StatisticiZilniceReceiverFragment.valoareCantitateML + "ml.");
//        }
//
//        if (StatisticiSaptamanaleReceiverFragment.valoareData != null) {
//            listaStatistici.add("In aceasta saptamana, cele mai multe donatii s-au inregistrat in data de " + StatisticiSaptamanaleReceiverFragment.valoareData + " ,inregistrandu-se " + StatisticiZilniceReceiverFragment.valoareCantitateML + "ml primiti.");
//        }
//
//        Collections.shuffle(listaStatistici);
//

//        tvStatistici.setText(listaStatistici.get(0));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
