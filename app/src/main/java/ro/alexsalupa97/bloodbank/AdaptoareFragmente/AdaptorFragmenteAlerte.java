package ro.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ro.alexsalupa97.bloodbank.Fragmente.ListaAlerteFragment;
import ro.alexsalupa97.bloodbank.Fragmente.ListaAlerteInApropiereFragment;

public class AdaptorFragmenteAlerte extends FragmentPagerAdapter {
    private Context mContext;

    public AdaptorFragmenteAlerte(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "In apropiere";
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
