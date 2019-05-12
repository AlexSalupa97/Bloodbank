package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.Orase;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;
import java.util.Arrays;


public class SetariActivity extends AppCompatActivity {

    static ListPreference listPreferenceLocatie;
    static ListPreference listPreferenceGrupaSanguina;

    String fisier = "SharedPreferences";
    static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,new MyPreferenceFragment()).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences(fisier, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public static class MyPreferenceFragment extends PreferenceFragmentCompat
    {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            setPreferencesFromResource(R.xml.preference_screen_setari, s);

            listPreferenceLocatie=(ListPreference)findPreference("locatieCurenta");

            listPreferenceLocatie.setSummary(Utile.preluareOras(getActivity()));
            listPreferenceLocatie.setDefaultValue(Utile.preluareOras(getActivity()));

            ArrayList<String> listaOrase=new ArrayList<>();
            try {
                for (Orase oras : Utile.orase)
                    listaOrase.add(oras.getOras());
            }
            catch (Exception ex)
            {

            }


            CharSequence[] listaOraseCS=new CharSequence[listaOrase.size()];
            listaOraseCS=listaOrase.toArray(listaOraseCS);
            Arrays.sort(listaOraseCS);

            listPreferenceLocatie.setEntries(listaOraseCS);
            listPreferenceLocatie.setEntryValues(listaOraseCS);

            listPreferenceLocatie.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    listPreferenceLocatie.setSummary(o.toString());
                    editor.putString("orasUser",o.toString());
                    editor.commit();
                    listPreferenceLocatie.setDefaultValue(Utile.preluareOras(getActivity()));
                    return true;
                }
            });


            listPreferenceGrupaSanguina=(ListPreference)findPreference("grupaSanguina");

            listPreferenceGrupaSanguina.setSummary(Utile.preluareGrupaSanguina(getActivity()));
            listPreferenceGrupaSanguina.setDefaultValue(Utile.preluareGrupaSanguina(getActivity()));

            ArrayList<String> listaGrupe=new ArrayList<>();
            try {
                for (GrupeSanguine grupeSanguine : Utile.listaGrupeSanguine)
                    listaGrupe.add(grupeSanguine.getGrupaSanguina());
            }
            catch (Exception ex)
            {

            }


            CharSequence[] listaGrupeCS=new CharSequence[listaGrupe.size()];
            listaGrupeCS=listaGrupe.toArray(listaGrupeCS);
            Arrays.sort(listaGrupeCS);

            listPreferenceGrupaSanguina.setEntries(listaGrupeCS);
            listPreferenceGrupaSanguina.setEntryValues(listaGrupeCS);

            listPreferenceGrupaSanguina.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    listPreferenceGrupaSanguina.setSummary(o.toString());
                    editor.putString("grupaSanguina",o.toString());
                    editor.commit();
                    listPreferenceGrupaSanguina.setDefaultValue(Utile.preluareGrupaSanguina(getActivity()));
                    return true;
                }
            });



        }
    }
}
