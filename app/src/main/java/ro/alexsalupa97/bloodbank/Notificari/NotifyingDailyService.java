package ro.alexsalupa97.bloodbank.Notificari;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;

public class NotifyingDailyService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                PrimaPaginaActivity.triggerBasicNotification();
                handler.postDelayed(runnable, 15000);
            }
        };

        handler.postDelayed(runnable, 5000);
        return super.onStartCommand(pIntent, flags, startId);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
        NotifyingDailyService mSensorService = new NotifyingDailyService();
        Intent mServiceIntent = new Intent(context, mSensorService.getClass());
        startService(mServiceIntent);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }


}



