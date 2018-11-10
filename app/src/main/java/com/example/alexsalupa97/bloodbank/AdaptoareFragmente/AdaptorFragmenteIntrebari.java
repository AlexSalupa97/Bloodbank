package com.example.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.alexsalupa97.bloodbank.Clase.Intrebari;
import com.example.alexsalupa97.bloodbank.Fragmente.IntrebareFragment;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

public class AdaptorFragmenteIntrebari extends FragmentStatePagerAdapter
{
    public AdaptorFragmenteIntrebari(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return Utile.intrebari.size();
    }


    @Override
    public Fragment getItem(int position) {
            IntrebareFragment intrebareFragment = new IntrebareFragment();
            Bundle bundle = new Bundle();
            position++;
            bundle.putParcelable("intrebare", Utile.intrebari.get(position - 1));
            intrebareFragment.setArguments(bundle);
            return intrebareFragment;
    }

}
