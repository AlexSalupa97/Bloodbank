package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexsalupa97.bloodbank.R;

public class AnalizeNeefectuateActivity extends AppCompatActivity {

    Button btnCentreAnalize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analize_neefectuate);

        btnCentreAnalize=(Button)findViewById(R.id.btnCentreAnalize);
        btnCentreAnalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ListaCentreActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
