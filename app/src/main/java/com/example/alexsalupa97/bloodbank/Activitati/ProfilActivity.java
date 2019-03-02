package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricDonatiiRV;
import com.example.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    ArrayList<SectionModelIstoric> sectiuni;
    RecyclerView rvIstoric;

    TextView tvNume;
    TextView tvTelefon;
    TextView tvEmail;
    TextView tvGrupaSanguina;
    TextView tvOras;
    ImageView ivStareAnalize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        tvNume=(TextView)findViewById(R.id.tvNume);
        tvTelefon=(TextView)findViewById(R.id.tvTelefon);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvGrupaSanguina=(TextView)findViewById(R.id.tvGrupaSanguina);
        tvOras=(TextView)findViewById(R.id.tvOras);
        ivStareAnalize=(ImageView)findViewById(R.id.ivStareAnalize);

        tvNume.setText(Utile.preluareUsername(getApplicationContext()));
        tvTelefon.setText(Utile.preluareTelefon(getApplicationContext()));
        tvEmail.setText(Utile.preluareEmail(getApplicationContext()));
        tvGrupaSanguina.setText(Utile.preluareGrupaSanguina(getApplicationContext()));
        tvOras.setText(Utile.preluareOras(getApplicationContext()));

        if(Utile.preluareStareAnalize(getApplicationContext()).equals("ok"))
            ivStareAnalize.setImageResource(R.drawable.good);
        else
            if(Utile.preluareStareAnalize(getApplicationContext()).equals("!ok"))
                ivStareAnalize.setImageResource(R.drawable.bad);
            else
                if(Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate"))
                    ivStareAnalize.setImageResource(R.drawable.not_done);

        sectiuni=new ArrayList<>();

        SectionModelIstoric dm = new SectionModelIstoric();

        dm.setTitlu("Istoric donatii");

        ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
        for (IstoricDonatii id:Utile.listaIstoricDonatii) {
            int index=id.getDataDonatie().indexOf("T");
            String substring=id.getDataDonatie().substring(0,index);
            itemeInSectiune.add(new ItemModelIstoric(substring,id.getCantitateDonataML()+"ml"));
        }

        dm.setItemeInSectiune(itemeInSectiune);

//        SectionModelIstoric dm1 = new SectionModelIstoric();
//
//        dm1.setTitlu("Analize efectuate");
//
//        ArrayList<ItemModelIstoric> itemeInSectiune1 = new ArrayList<ItemModelIstoric>();
//        for (int i = 0; i < 10; i++) {
//            itemeInSectiune1.add(new ItemModelIstoric("Data analiza " + i));
//        }
//
//        dm1.setItemeInSectiune(itemeInSectiune1);

        sectiuni.add(dm);
//        sectiuni.add(dm1);

        rvIstoric = (RecyclerView) findViewById(R.id.rvIstoric);

        rvIstoric.setHasFixedSize(true);

        AdaptorIstoricDonatiiRV adapter = new AdaptorIstoricDonatiiRV(this, sectiuni);
        rvIstoric.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIstoric.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
