
package ro.alexsalupa97.bloodbank.Activitati;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import static java.lang.Thread.sleep;

public class TransparentActivityCTS extends AppCompatActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);

        getSupportActionBar().hide();

        final CTS cts=Utile.preluareCTSLogin(getApplicationContext());

        pd = ProgressDialog.show(TransparentActivityCTS.this, "Asteptati...", "Se verifica situatia actuala", true,
                false);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Utile.REST_GET_istoricIntrariCTS_particular(TransparentActivityCTS.this,cts);
                    Utile.REST_GET_istoricIesiriCTS_particular(TransparentActivityCTS.this,cts);
                    Utile.REST_GET_limiteCTS_particular(TransparentActivityCTS.this,cts);
                    Utile.REST_GET_grupeSanguine(TransparentActivityCTS.this);
                    sleep(2000);

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
            Intent intent = new Intent(TransparentActivityCTS.this, DetaliiCTSMainActivity.class);
            startActivity(intent);
            finish();

        }
    };
}
