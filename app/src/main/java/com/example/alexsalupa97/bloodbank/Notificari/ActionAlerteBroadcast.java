package com.example.alexsalupa97.bloodbank.Notificari;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.alexsalupa97.bloodbank.Activitati.AlerteActivity;
import com.example.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;
import com.example.alexsalupa97.bloodbank.Activitati.TransparentActivity;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import static java.lang.Thread.sleep;

public class ActionAlerteBroadcast extends BroadcastReceiver {

    ProgressDialog pd;
    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        intent=new Intent(context, TransparentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


}
