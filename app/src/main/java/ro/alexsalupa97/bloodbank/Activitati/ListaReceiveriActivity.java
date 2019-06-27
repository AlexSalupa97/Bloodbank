package ro.alexsalupa97.bloodbank.Activitati;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorReceiveriLV;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;


public class ListaReceiveriActivity extends AppCompatActivity {

    ListView lvReceiveri;
    AdaptorReceiveriLV adaptor;
    ArrayList<Receiveri> listaReceiveri;
    SearchView searchView;

//    ArrayList<String> listaReceiveri;
//    ArrayAdapter<String> adaptor;

    SwipeRefreshLayout srlListaReceiveri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_receiveri);

        srlListaReceiveri = (SwipeRefreshLayout) findViewById(R.id.srlListaReceiveri);
        srlListaReceiveri.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        srlListaReceiveri.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    srlListaReceiveri.setRefreshing(false);
                                }
                            });
                        } catch (Exception e) {
                            Log.e("SplashScreenActivity", e.getMessage());
                        }
                    }
                };

                t.start();
            }
        });

        lvReceiveri = (ListView) findViewById(R.id.lvReceiveri);
        listaReceiveri = new ArrayList<>();
        for (Receiveri receiver : Utile.listaReceiveri)
            if (receiver.getCts().getOras().getOras().equals(Utile.preluareOras(getApplicationContext())))
                listaReceiveri.add(receiver);
        Collections.sort(listaReceiveri);
        adaptor = new AdaptorReceiveriLV(getApplicationContext(), listaReceiveri);
        lvReceiveri.setAdapter(adaptor);

//        listaReceiveri=new ArrayList<>();
//        for(Receiveri receiver: Utile.listaReceiveri)
//            listaReceiveri.add(receiver.getNumeReceiver());
//
//        lvReceiveri=(ListView)findViewById(R.id.lvReceiveri);
//
//        adaptor=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listaReceiveri);
//        lvReceiveri.setAdapter(adaptor);

//        lvReceiveri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String numePreluat=lvReceiveri.getItemAtPosition(position).toString();
//                for(Receiveri receiver:Utile.listaReceiveri)
//                    if(receiver.getNumeReceiver().equals(numePreluat))
//                    {
//
//                        Intent intent=new Intent(getApplicationContext(),DetaliiReceiverActivity.class);
//                        intent.putExtra("receiver",receiver);
//                        startActivity(intent);
//                    }
//            }
//        });

        getSupportActionBar().setElevation(0);
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
        getMenuInflater().inflate(R.menu.meniu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.svActionbar);
        searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adaptor.filter("",listaReceiveri);
                    adaptor=new AdaptorReceiveriLV(getApplicationContext(),listaReceiveri);
                    lvReceiveri.setAdapter(adaptor);
                    lvReceiveri.clearTextFilter();
                }
                else {
                    ArrayList<Receiveri> newList=adaptor.filter(s,listaReceiveri);
                    adaptor=new AdaptorReceiveriLV(getApplicationContext(),newList);
                    lvReceiveri.setAdapter(adaptor);
                }
                return true;
            }
        });
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String cautare = searchView.getQuery().toString();
//        searchView.setQuery(cautare, false);
//    }
}
