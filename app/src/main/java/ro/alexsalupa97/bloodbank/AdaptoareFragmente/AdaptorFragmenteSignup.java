package ro.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ro.alexsalupa97.bloodbank.Fragmente.SignupCTSFragment;
import ro.alexsalupa97.bloodbank.Fragmente.SignupDonatorFragment;
import ro.alexsalupa97.bloodbank.Fragmente.SignupReceiverFragment;

public class AdaptorFragmenteSignup extends FragmentPagerAdapter {

    public AdaptorFragmenteSignup(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Donator";
        else if (position == 1)
            return "Receiver";
        else
            return "CTS";
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new SignupDonatorFragment();
        else if (position == 1)
            return new SignupReceiverFragment();
        else
            return new SignupCTSFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}