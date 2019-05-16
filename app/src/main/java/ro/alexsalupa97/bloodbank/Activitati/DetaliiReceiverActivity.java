package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorIstoricReceiverRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiReceiverActivity extends AppCompatActivity {

    ShareButton fbShareBtn;
    LinearLayout twitterShareBtn;

    RecyclerView rvIstoricReceiver;
    ArrayList<SectionModelIstoric> sectiuni;

    ImageButton btnEmail;
    ImageButton btnTelefon;
    TextView tvNumeReceiver;
    TextView tvAdresa;
    TextView tvEmail;
    TextView tvTelefon;

    SwipeRefreshLayout swiperefreshRVSituatieSanguinaReceiver;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_receiver);

        final Receiveri receiver;
        receiver=getIntent().getParcelableExtra("receiver");

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        tvNumeReceiver = (TextView) findViewById(R.id.tvNumeReceiver);
        tvAdresa = (TextView) findViewById(R.id.tvAdresa);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvTelefon = (TextView) findViewById(R.id.tvTelefon);

        tvNumeReceiver.setText(receiver.getNumeReceiver());
        tvEmail.setText(tvEmail.getText() + " " + receiver.getEmailReceiver());
        tvTelefon.setText(tvTelefon.getText() + " " + receiver.getTelefonReceiver());
        tvAdresa.setText(tvAdresa.getText() + " " + receiver.getCts().getAdresaCTS()+" ("+receiver.getCts().getNumeCTS()+")");

        rvIstoricReceiver = (RecyclerView) findViewById(R.id.rvIstoricReceiver);

        btnEmail=(ImageButton)findViewById(R.id.btnEmail);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailto = "mailto:" + receiver.getEmailReceiver()+
                        "?cc=" + "alex.salupa@yahoo.com" +
                        "&subject=" + Uri.encode("Mail catre receiver") +
                        "&body=" + Uri.encode("Salut");
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse(mailto));
                startActivity(intent);
            }
        });

        btnTelefon=(ImageButton)findViewById(R.id.btnTelefon);

        btnTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+receiver.getTelefonReceiver()));
                startActivity(intent);
            }
        });

        swiperefreshRVSituatieSanguinaReceiver=(SwipeRefreshLayout)findViewById(R.id.swiperefreshRVSituatieSanguinaReceiver);
        swiperefreshRVSituatieSanguinaReceiver.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swiperefreshRVSituatieSanguinaReceiver.setRefreshing(true);
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverActivity.this);
                    RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                    JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricreceiveri/receiver/" + receiver.getEmailReceiver(), null, future1, future1);
                    RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                    JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                    requestQueue.add(request1);
                    requestQueue.add(request3);

                    JSONArray response1 = future1.get();
                    JSONArray response3 = future3.get();

                    gson = new Gson();


                    Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));
                    Utile.listaIstoricReceiver = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IstoricReceiver[].class)));

                    sectiuni = new ArrayList<>();

                    SectionModelIstoric dm = new SectionModelIstoric();

                    dm.setTitlu("Donatii primite");

                    ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
                    for (IstoricReceiver id : Utile.listaIstoricReceiver) {
                        int index = id.getDataPrimire().indexOf("T");
                        String substring = id.getDataPrimire().substring(0, index);
                        itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitatePrimitaML() + "ml"));
                    }

                    Collections.sort(itemeInSectiune);
                    dm.setItemeInSectiune(itemeInSectiune);

                    sectiuni.add(dm);

                    rvIstoricReceiver.setHasFixedSize(true);

                    final AdaptorIstoricReceiverRV adaptor = new AdaptorIstoricReceiverRV(DetaliiReceiverActivity.this, sectiuni,false);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rvIstoricReceiver.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                            rvIstoricReceiver.setAdapter(adaptor);
                            swiperefreshRVSituatieSanguinaReceiver.setRefreshing(false);
                        }
                    });

                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
            }
        });
        thread.start();

        swiperefreshRVSituatieSanguinaReceiver.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(DetaliiReceiverActivity.this);
                            RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                            JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricreceiveri/receiver/" + receiver.getEmailReceiver(), null, future1, future1);
                            RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                            JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future3, future3);

                            requestQueue.add(request1);
                            requestQueue.add(request3);

                            JSONArray response1 = future1.get();
                            JSONArray response3 = future3.get();

                            gson = new Gson();


                            Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), GrupeSanguine[].class)));
                            Utile.listaIstoricReceiver = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IstoricReceiver[].class)));

                            sectiuni = new ArrayList<>();

                            SectionModelIstoric dm = new SectionModelIstoric();

                            dm.setTitlu("Donatii primite");

                            ArrayList<ItemModelIstoric> itemeInSectiune = new ArrayList<ItemModelIstoric>();
                            for (IstoricReceiver id : Utile.listaIstoricReceiver) {
                                int index = id.getDataPrimire().indexOf("T");
                                String substring = id.getDataPrimire().substring(0, index);
                                itemeInSectiune.add(new ItemModelIstoric(substring, id.getCantitatePrimitaML() + "ml"));
                            }

                            Collections.sort(itemeInSectiune);
                            dm.setItemeInSectiune(itemeInSectiune);

                            sectiuni.add(dm);

                            rvIstoricReceiver.setHasFixedSize(true);

                            final AdaptorIstoricReceiverRV adaptor = new AdaptorIstoricReceiverRV(DetaliiReceiverActivity.this, sectiuni,false);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rvIstoricReceiver.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                    rvIstoricReceiver.setAdapter(adaptor);
                                    swiperefreshRVSituatieSanguinaReceiver.setRefreshing(false);
                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                };

                t.start();

            }
        });

        final Receiveri receiverFinal=receiver;

        fbShareBtn = (ShareButton) findViewById(R.id.fbShareBtn);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote("Doneaza pentru "+receiverFinal.getNumeReceiver())
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/developer?id=AlexSalupa97"))
                .build();

        fbShareBtn.setShareContent(content);


        twitterShareBtn = (LinearLayout) findViewById(R.id.twitterShareBtn);
        twitterShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = getShareIntent("twitter", "subject", "Doneaza pentru "+receiverFinal.getNumeReceiver() + "\nhttps://play.google.com/store/apps/developer?id=AlexSalupa97");
                if (twitterIntent != null)
                    startActivity(twitterIntent);

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

    private Intent getShareIntent(String type, String subject, String text) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getApplicationContext().getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, subject);
                    share.putExtra(Intent.EXTRA_TEXT, text);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
    }
}
