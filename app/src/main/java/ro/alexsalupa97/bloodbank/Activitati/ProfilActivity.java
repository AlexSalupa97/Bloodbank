package ro.alexsalupa97.bloodbank.Activitati;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricDonatiiRV;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;
import ro.alexsalupa97.bloodbank.Utile.Utile;

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

    Dialog dialogInfo;
    Button btnOK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getSupportActionBar().setElevation(0);

        dialogInfo = new Dialog(ProfilActivity.this);
        dialogInfo.setContentView(R.layout.dialog_info);

        btnOK = (Button) dialogInfo.findViewById(R.id.btnDialogOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo.dismiss();
            }
        });


        tvNume = (TextView) findViewById(R.id.tvNume);
        tvTelefon = (TextView) findViewById(R.id.tvTelefon);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvGrupaSanguina = (TextView) findViewById(R.id.tvGrupaSanguina);
        tvOras = (TextView) findViewById(R.id.tvOras);
        ivStareAnalize = (ImageView) findViewById(R.id.ivStareAnalize);

        tvNume.setText(Utile.preluareUsername(getApplicationContext()));
        tvTelefon.setText(Utile.preluareTelefon(getApplicationContext()));
        tvEmail.setText(Utile.preluareEmail(getApplicationContext()));
        tvGrupaSanguina.setText(Utile.preluareGrupaSanguina(getApplicationContext()));
        tvOras.setText(Utile.preluareOras(getApplicationContext()));

        if (Utile.preluareStareAnalize(getApplicationContext()).equals("ok"))
            ivStareAnalize.setImageResource(R.drawable.good);
        else if (Utile.preluareStareAnalize(getApplicationContext()).equals("!ok"))
            ivStareAnalize.setImageResource(R.drawable.bad);
        else if (Utile.preluareStareAnalize(getApplicationContext()).equals("neefectuate"))
            ivStareAnalize.setImageResource(R.drawable.not_done);

        sectiuni = new ArrayList<>();

        SectionModelIstoric dm = new SectionModelIstoric();

        dm.setTitlu("Istoric donatii");

        ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
        for (IstoricDonatii id : Utile.listaIstoricDonatii) {
            int index = id.getDataDonatie().indexOf("T");
            String substring = id.getDataDonatie().substring(0, index);
            itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitateDonataML() + "ml"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meniu_info_donator, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_info)
            dialogInfo.show();
        return super.onOptionsItemSelected(item);
    }
}
