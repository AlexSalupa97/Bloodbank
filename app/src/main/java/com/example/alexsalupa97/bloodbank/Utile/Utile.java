package com.example.alexsalupa97.bloodbank.Utile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.R;

import java.util.ArrayList;

public class Utile {

    public static String fisier = "SharedPreferences";
    public static String URL="http://986ee3c2.ngrok.io/ProiectLicentaBloodBank/webresources/";

    public static ArrayList<Intrebari> intrebari;


    public static String preluareUsername(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("grupaSanguina", defaultName);
        return dePreluat;
    }

    public static void updateMenuItem(Context context, Menu menu)
    {
        String name=preluareUsername(context);
        MenuItem item1 = menu.findItem(R.id.item_meniuGrupaSanguina);
        Log.i("Verificare gasire item", "da");
        item1.setTitle(name);
        Log.i("Verificare setare item", name);
    }
}
