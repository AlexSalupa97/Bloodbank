package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.lang.reflect.Field;

import ro.alexsalupa97.bloodbank.AdaptoareFragmente.AdaptorFragmenteIntrebari;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;
import ro.alexsalupa97.bloodbank.ViewCustom.ViewPagerNoSwipe;

public class IntrebariActivity extends AppCompatActivity {

    AdaptorFragmenteIntrebari adaptorIntrebari;
    ViewPagerNoSwipe viewPager;

    Button btnDa;
    Button btnNu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intrebari);


        getSupportActionBar().setElevation(0);
        getSupportActionBar().setCustomView(R.layout.bloodbank_logo_actionbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);



        adaptorIntrebari = new AdaptorFragmenteIntrebari(getSupportFragmentManager());
        viewPager = (ViewPagerNoSwipe) findViewById(R.id.viewPagerIntrebari);
        viewPager.setAdapter(adaptorIntrebari);

        btnDa = (Button) findViewById(R.id.btnIntrebareDA);
        btnNu = (Button) findViewById(R.id.btnIntrebareNU);

        btnDa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == (Utile.intrebari.size() - 1) && Utile.intrebari.get(viewPager.getCurrentItem()).getRaspunsIntrebare().toLowerCase().equals(btnDa.getText().toString().toLowerCase())) {
                    Intent intent = new Intent(getApplicationContext(), FinalizareSuccesIntrebariActivity.class);
                    startActivity(intent);
                    finish();
                } else if (Utile.intrebari.get(viewPager.getCurrentItem()).getRaspunsIntrebare().toLowerCase().equals(btnDa.getText().toString().toLowerCase()))
                    viewPager.arrowScroll(View.FOCUS_RIGHT);
                else {
                    Intent intent = new Intent(getApplicationContext(), FinalizareFailIntrebariActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

        });

        btnNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == (Utile.intrebari.size() - 1) && Utile.intrebari.get(viewPager.getCurrentItem()).getRaspunsIntrebare().toLowerCase().equals(btnNu.getText().toString().toLowerCase())) {
                    Intent intent = new Intent(getApplicationContext(), FinalizareSuccesIntrebariActivity.class);
                    startActivity(intent);
                    finish();
                } else if (Utile.intrebari.get(viewPager.getCurrentItem()).getRaspunsIntrebare().toLowerCase().equals(btnNu.getText().toString().toLowerCase()))
                    viewPager.arrowScroll(View.FOCUS_RIGHT);
                else {
                    Intent intent = new Intent(getApplicationContext(), FinalizareFailIntrebariActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
