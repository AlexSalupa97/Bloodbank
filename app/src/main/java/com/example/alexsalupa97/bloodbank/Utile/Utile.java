package com.example.alexsalupa97.bloodbank.Utile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alexsalupa97.bloodbank.Clase.Intrebari;

import java.util.ArrayList;

public class Utile {

    public static String fisier = "SharedPreferences";
    public static String URL="http://c74f1532.ngrok.io/ProiectLicentaBloodBank/webresources/";

    public static ArrayList<Intrebari> intrebari;


    public static String preluareUsername(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("login_name", defaultName);
        return dePreluat;
    }
}
