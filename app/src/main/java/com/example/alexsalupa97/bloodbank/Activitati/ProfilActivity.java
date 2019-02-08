package com.example.alexsalupa97.bloodbank.Activitati;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricQuizuriRV;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModel;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModel;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    ArrayList<SectionModel> sectiuni;
    RecyclerView rvIstoric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        sectiuni=new ArrayList<>();

        SectionModel dm = new SectionModel();

        dm.setTitlu("Donari de sange");

        ArrayList<ItemModel> itemeInSectiune = new ArrayList<ItemModel>();
        for (int i = 0; i < 10; i++) {
            itemeInSectiune.add(new ItemModel("Data donare " + i));
        }

        dm.setItemeInSectiune(itemeInSectiune);

        SectionModel dm1 = new SectionModel();

        dm1.setTitlu("Analize efectuate");

        ArrayList<ItemModel> itemeInSectiune1 = new ArrayList<ItemModel>();
        for (int i = 0; i < 10; i++) {
            itemeInSectiune1.add(new ItemModel("Data analiza " + i));
        }

        dm1.setItemeInSectiune(itemeInSectiune1);

        sectiuni.add(dm);
        sectiuni.add(dm1);

        rvIstoric = (RecyclerView) findViewById(R.id.rvIstoric);

        rvIstoric.setHasFixedSize(true);

        AdaptorIstoricQuizuriRV adapter = new AdaptorIstoricQuizuriRV(this, sectiuni);
        rvIstoric.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIstoric.setAdapter(adapter);
    }
}
