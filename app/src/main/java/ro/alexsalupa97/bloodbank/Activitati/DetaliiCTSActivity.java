package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;

public class DetaliiCTSActivity extends AppCompatActivity {

    CTS ctsCurent;
    Button btnEmail;
    Button btnTelefon;
    TextView tvNumeCTS;
    TextView tvAdresa;
    TextView tvEmail;
    TextView tvTelefon;

    ActionBar actionBar;

    Button btnProgramare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_cts);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradient_red_vertical));

        ctsCurent= getIntent().getExtras().getParcelable("cts");

        tvNumeCTS=(TextView)findViewById(R.id.tvNumeCTS);
        tvAdresa=(TextView)findViewById(R.id.tvAdresa);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvTelefon=(TextView)findViewById(R.id.tvTelefon);

        tvNumeCTS.setText(ctsCurent.getNumeCTS());
        tvAdresa.setText(tvAdresa.getText()+ctsCurent.getAdresaCTS());
        tvEmail.setText(tvEmail.getText()+ctsCurent.getEmailCTS());
        tvTelefon.setText(tvTelefon.getText()+ctsCurent.getTelefonCTS());

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

        btnTelefon=(Button)findViewById(R.id.btnTelefon);

        btnTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ctsCurent.getTelefonCTS()));
                startActivity(intent);
            }
        });

        btnProgramare=(Button)findViewById(R.id.btnProgramare);
        btnProgramare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProgramareActivity.class);
                intent.putExtra("cts",ctsCurent);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
