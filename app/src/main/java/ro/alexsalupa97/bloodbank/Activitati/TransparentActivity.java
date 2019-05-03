
package ro.alexsalupa97.bloodbank.Activitati;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import static java.lang.Thread.sleep;

public class TransparentActivity extends AppCompatActivity {

    ProgressDialog pd;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        getSupportActionBar().hide();

        pd = ProgressDialog.show(TransparentActivity.this, "Asteptati...", "Se verifica situatia actuala", true,
                false);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    gson=new Gson();

                    RequestQueue requestQueue = Volley.newRequestQueue(TransparentActivity.this);
                    RequestFuture<JSONArray> future = RequestFuture.newFuture();
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoricintraricts", null, future, future);
                    RequestFuture<JSONArray> future1 = RequestFuture.newFuture();
                    JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.istoriciesiricts", null, future1, future1);
                    RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                    JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.limitects", null, future2, future2);
                    RequestFuture<JSONArray> future3 = RequestFuture.newFuture();
                    JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, Utile.URL + "domain.compatibilitati/" + Utile.preluareGrupaSanguina(getApplicationContext()), null, future3, future3);


                    requestQueue.add(request);
                    requestQueue.add(request1);
                    requestQueue.add(request2);
                    requestQueue.add(request3);


                    JSONArray response=future.get();
                    JSONArray response1=future1.get();
                    JSONArray response2=future2.get();
                    JSONArray response3=future3.get();

                    Utile.listaIntrariCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(), IntrariCTS[].class)));
                    Utile.listaIesiriCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response1.toString(), IesiriCTS[].class)));
                    Utile.listaLimiteCTS = new ArrayList<>(Arrays.asList(gson.fromJson(response2.toString(), LimiteCTS[].class)));
                    Utile.compatibilitati = new ArrayList<>(Arrays.asList(gson.fromJson(response3.toString(), Compatibilitati[].class)));




                    sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                handler.sendEmptyMessage(0);

            }
        });
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            Intent intent = new Intent(TransparentActivity.this, AlerteActivity.class);
            startActivity(intent);
            finish();

        }
    };
}
