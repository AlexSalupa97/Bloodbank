package com.example.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alexsalupa97.bloodbank.Fragmente.ListaAlerteFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.ListaAlerteInApropiereFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.ListaCTSFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.ListaCTSInApropiereFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

public class AdaptorFragmenteAlerte extends FragmentPagerAdapter {
    private Context mContext;

    public AdaptorFragmenteAlerte(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "In "+ Utile.preluareOras(mContext);
        else
            return "Toate centrele";
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new ListaAlerteInApropiereFragment();
        else
            return new ListaAlerteFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }
}
