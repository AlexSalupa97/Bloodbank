package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;


public class ListaReceiveriActivity extends AppCompatActivity {

    ListView lvReceiveri;
    ArrayList<String> listaReceiveri;
    ArrayAdapter<String> adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receiveri);

        listaReceiveri=new ArrayList<>();
        for(Receiveri receiver: Utile.listaReceiveri)
            listaReceiveri.add(receiver.getNumeReceiver());

        lvReceiveri=(ListView)findViewById(R.id.lvReceiveri);

        adaptor=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaReceiveri);
        lvReceiveri.setAdapter(adaptor);

        lvReceiveri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String numePreluat=lvReceiveri.getItemAtPosition(position).toString();
                for(Receiveri receiver:Utile.listaReceiveri)
                    if(receiver.getNumeReceiver().equals(numePreluat))
                    {

                        Intent intent=new Intent(getApplicationContext(),DetaliiReceiverActivity.class);
                        intent.putExtra("receiver",receiver);
                        startActivity(intent);
                    }
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
