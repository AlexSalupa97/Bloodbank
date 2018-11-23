package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexsalupa97.bloodbank.R;

public class FinalizareSuccesIntrebariActivity extends AppCompatActivity {

    Button btnCentreRecoltare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizare_succes_intrebari);

        btnCentreRecoltare=(Button)findViewById(R.id.btnCentreRecoltare);
        btnCentreRecoltare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnCentreRecoltare.setEnabled(false);
                Intent intent=new Intent(getApplicationContext(),ListaCentreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
