package com.example.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.R;

public class DetaliiCTSActivity extends AppCompatActivity {

    CTS ctsCurent;
    Button btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cts);

        ctsCurent= getIntent().getExtras().getParcelable("cts");

        btnEmail=(Button)findViewById(R.id.btnEmail);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailto = "mailto:" + ctsCurent.getEmailCTS()+
                        "?cc=" + "alex.salupa@yahoo.com" +
                        "&subject=" + Uri.encode("Programare donatie") +
                        "&body=" + Uri.encode("Programare");
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mailto));
                startActivity(intent);
            }
        });


    }
}
