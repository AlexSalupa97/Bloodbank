package ro.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import ro.alexsalupa97.bloodbank.Fragmente.StatisticiAnualeCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiAnualeReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiLunareCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiLunareReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiSaptamanaleCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiSaptamanaleReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiZilniceCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiZilniceReceiverFragment;

public class AdaptorFragmenteStatisticiCTS extends FragmentStatePagerAdapter {
    public AdaptorFragmenteStatisticiCTS(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new StatisticiZilniceCTSFragment();
        else if (position == 1)
            return new StatisticiSaptamanaleCTSFragment();
        else if (position == 2)
            return new StatisticiLunareCTSFragment();
        else
            return new StatisticiAnualeCTSFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return " Zilnice";
        else if (position == 1)
            return "Saptamanale";
        else if (position == 2)
            return "Lunare";
        else
            return "Anuale";
    }
}
