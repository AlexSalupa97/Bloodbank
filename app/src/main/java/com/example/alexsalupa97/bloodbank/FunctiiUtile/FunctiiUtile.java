package com.example.alexsalupa97.bloodbank.FunctiiUtile;

import android.content.Context;
import android.content.SharedPreferences;

public class FunctiiUtile {
    static String fisier = "SharedPreferences";

    public static String preluareUsername(Context context) {
        String dePreluat;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fisier, Context.MODE_PRIVATE);
        String defaultName = "N/A";
        dePreluat = sharedPreferences.getString("login_name", defaultName);
        return dePreluat;
    }
}
