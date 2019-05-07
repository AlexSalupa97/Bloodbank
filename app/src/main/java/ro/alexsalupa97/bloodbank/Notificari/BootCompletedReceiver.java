package ro.alexsalupa97.bloodbank.Notificari;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        if (arg1.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context, "broadcast", Toast.LENGTH_LONG).show();
            context.startService(new Intent(context, NotifyingDailyService.class));
        }
    }
}
