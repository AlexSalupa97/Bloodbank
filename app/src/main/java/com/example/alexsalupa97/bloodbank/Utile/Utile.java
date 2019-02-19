package com.example.alexsalupa97.bloodbank.Utile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.Clase.Orase;
import com.example.alexsalupa97.bloodbank.R;

import java.util.ArrayList;
import java.util.Set;

public class Utile {

    public static String fisier = "SharedPreferences";
    public static String URL="http://2b2f1c58.ngrok.io/ProiectLicentaBloodbank/webresources/";

    public static ArrayList<Intrebari> intrebari;
    public static ArrayList<Compatibilitati> compatibilitati;
    public static ArrayList<CTS> CTS;
    public static Set<Orase> orase;


    public static String preluareUsername(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("login_name", defaultName);
        return dePreluat;
    }

    public static String preluareJudet(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("judetUser", defaultName);
        return dePreluat;
    }

    public static String preluareGrupaSanguina(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("grupaSanguina", defaultName);
        return dePreluat;
    }

    public static String preluareStareAnalize(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("stareAnalize", defaultName);
        return dePreluat;
    }

    public static void updateMenuItem(Context context, Menu menu)
    {
        String name=preluareGrupaSanguina(context);
        MenuItem item1 = menu.findItem(R.id.item_meniuGrupaSanguina);
        Log.i("Verificare gasire item", "da");
        item1.setTitle(name);
        Log.i("Verificare setare item", name);
    }
}
