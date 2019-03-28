package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import ro.alexsalupa97.bloodbank.Clase.Orase;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;


public class SetariActivity extends AppCompatActivity {

    static ListPreference listPreferenceLocatie;

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




        }
    }
}
