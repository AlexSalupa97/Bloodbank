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

public class TransparentReceiverActivity extends AppCompatActivity {

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_receiver);

        getSupportActionBar().hide();

        pd = ProgressDialog.show(TransparentReceiverActivity.this, "Asteptati...", "Se construiesc graficele", true,
                false);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
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
            Intent intent = new Intent(TransparentReceiverActivity.this, StatisticiReceiverActivity.class);
            startActivity(intent);
            finish();

        }
    };
}

