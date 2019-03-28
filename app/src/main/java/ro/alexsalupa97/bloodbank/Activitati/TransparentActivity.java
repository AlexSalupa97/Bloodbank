
package ro.alexsalupa97.bloodbank.Activitati;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import static java.lang.Thread.sleep;

public class TransparentActivity extends AppCompatActivity {

    ProgressDialog pd;

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
                    Utile.REST_GET_istoricIntrariCTS(TransparentActivity.this);
                    Utile.REST_GET_istoricIesiriCTS(TransparentActivity.this);
                    Utile.REST_GET_limiteCTS(TransparentActivity.this);
                    Utile.REST_GET_grupeSanguine(TransparentActivity.this);
                    Utile.REST_GET_listaCTS(TransparentActivity.this);
                    Utile.REST_GET_listaCompatibilitati(TransparentActivity.this);
                    sleep(1000);

                } catch (InterruptedException e) {
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
