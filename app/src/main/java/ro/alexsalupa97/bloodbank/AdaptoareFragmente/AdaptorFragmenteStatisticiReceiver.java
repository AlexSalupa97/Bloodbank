package ro.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import ro.alexsalupa97.bloodbank.Fragmente.StatisticiAnualeReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiLunareReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiSaptamanaleReceiverFragment;
import ro.alexsalupa97.bloodbank.Fragmente.StatisticiZilniceReceiverFragment;

public class AdaptorFragmenteStatisticiReceiver extends FragmentStatePagerAdapter {
    public AdaptorFragmenteStatisticiReceiver(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new StatisticiZilniceReceiverFragment();
        else if (position == 1)
            return new StatisticiSaptamanaleReceiverFragment();
        else if (position == 2)
            return new StatisticiLunareReceiverFragment();
        else
            return new StatisticiAnualeReceiverFragment();
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
