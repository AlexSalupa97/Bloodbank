package ro.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Fragmente.ListaCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.ListaCTSInApropiereFragment;
import ro.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class AdaptorFragmenteCTS extends FragmentPagerAdapter {

    private Context mContext;

    public AdaptorFragmenteCTS(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {

            return "In "+Utile.preluareOras(mContext);
        }
        else if (position == 1)
            return "Toate centrele";
        else
            return "Maps";
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new ListaCTSInApropiereFragment();
        else if (position == 1)
            return new ListaCTSFragment();
        else
            return new MapsCTSFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}

