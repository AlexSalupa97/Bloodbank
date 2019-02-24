package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

public class SplashScreenActivity extends AppCompatActivity {

    String fisier = "SharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash_screen);


        Thread t = new Thread() {
            @Override
            public void run() {
                try {

                    sleep(1500);
                } catch (InterruptedException e) {
                    Log.e("SplashScreenActivity", e.getMessage());
                } finally {

                    SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
                    String defaultName = "";

//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("login_name", "");
//                    editor.commit();

                    String name = sharedPreferences.getString("login_name", defaultName);



                    if (name.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), AlegereLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        Intent mainIntent = new Intent(getApplicationContext(), PrimaPaginaActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
            }
        };
        t.start();
    }
}

