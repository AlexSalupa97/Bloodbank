package ro.alexsalupa97.bloodbank.Notificari;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class NotificariBroadcast extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    ArrayList<CantitatiCTS> listaCantitatiCTS;
    Map<CTS, ArrayList<CantitatiCTS>> mapCantitatiPerCTS;

    Gson gson;

    @Override
    public void onReceive(Context context, Intent intent) {
        Thread thread = new Thread(() -> {

            try {
                gson = new Gson();

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricintraricts", null, future, future);
                RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts", null, future1, future1);
                RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.limitects", null, future2, future2);
                RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.compatibilitati/" + Utile.preluareGrupaSanguina(context), null, future3, future3);
                RequestFuture<JSONArray> future4 = RequestFuture.newFuture();
                JsonArrayRequest request4 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.cts", null, future4, future4);
                RequestFuture<JSONArray> future5 = RequestFuture.newFuture();
                JsonArrayRequest request5 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.grupesanguine", null, future5, future5);

//                RequestFuture<String> future6 = RequestFuture.newFuture();
//                StringRequest request6 = new StringRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts/count", future6, future6);

                requestQueue.add(request);
                requestQueue.add(request1);
                requestQueue.add(request2);
                requestQueue.add(request3);
                requestQueue.add(request4);
                requestQueue.add(request5);
//                requestQueue.add(request6);


                JSONArray response = future.get();
                JSONArray response1 = future1.get();
                JSONArray response2 = future2.get();
                JSONArray response3 = future3.get();
                JSONArray response4 = future4.get();
                JSONArray response5 = future5.get();

//                String response6 = future6.get();

                Utile.listaIntrariCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IntrariCTS[].class)));
                Utile.listaIesiriCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IesiriCTS[].class)));
                Utile.listaLimiteCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response2.toString(), LimiteCTS[].class)));
                Utile.compatibilitati = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), Compatibilitati[].class)));
                Utile.CTS = new ArrayList<>(Arrays.asList(gson.fromJson(response4.toString(), CTS[].class)));
                Utile.orase = new HashSet<>();
                for (CTS cts : Utile.CTS)
                    Utile.orase.add(cts.getOras());
                Utile.listaGrupeSanguine = new ArrayList<>(Arrays.asList(gson.fromJson(response5.toString(), GrupeSanguine[].class)));

                ArrayList<String> listaAlerte = new ArrayList<>();


                mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil());

                mapLimitePerCTSPerGrupa = new HashMap<>();


                for (CTS cts : Utile.CTS) {
                    Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                    for (LimiteCTS limite : Utile.listaLimiteCTS)
                        if (limite.getCts().getNumeCTS().equals(cts.getNumeCTS()))
                            mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                    mapLimitePerCTSPerGrupa.put(cts, mapIntermediar);
                }

                mapCantitatiPerCTS = new HashMap<>();


                for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {


                    if (cts.getOras().getOras().equals(Utile.preluareOras(context))) {
                        listaCantitatiCTS = new ArrayList<>();
                        Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                        Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);



                        for (Compatibilitati grupaSanguinaDonator : Utile.compatibilitati) {
                            if (Utile.preluareGrupaSanguina(context).equals(grupaSanguinaDonator.getGrupaSanguinaDonatoare().getGrupaSanguina()))
                                try {
                                    CantitatiCTS cantitateCTSCurent = new CantitatiCTS();
                                    cantitateCTSCurent.setCts(cts);
                                    cantitateCTSCurent.setGrupaSanguina(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                                    cantitateCTSCurent.setCantitateDisponibilaML(mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                                    cantitateCTSCurent.setCantitateLimitaML(mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                                    listaCantitatiCTS.add(cantitateCTSCurent);

                                    if (mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) < mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()))
                                        listaAlerte.add(cts.getNumeCTS() + "\n\n\t\tprobleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + "\n\t\tlimita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + "\n\t\tdisponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + "\n");
//                                else
//                                    deAfisat+="\n\t\t nu sunt probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                                } catch (Exception ex) {

                                }
                        }

                        mapCantitatiPerCTS.put(cts, listaCantitatiCTS);


                        if (listaAlerte.size()>0) {
                            NotificationManager notificationManager = (NotificationManager) context.getSystemService
                                    (Context.NOTIFICATION_SERVICE);

                            Notification notification = intent.getParcelableExtra(NOTIFICATION);
                            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
                            notificationManager.notify(id, notification);
                        }
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });
        thread.start();


    }
}
