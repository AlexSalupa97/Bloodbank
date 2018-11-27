package com.example.alexsalupa97.bloodbank.AdaptoareFragmente;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alexsalupa97.bloodbank.Fragmente.ListaCTSFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.ListaCTSInApropiereFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.MapsCTSFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.SignupCTSFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.SignupDonatorFragment;
import com.example.alexsalupa97.bloodbank.Fragmente.SignupReceiverFragment;

public class AdaptorFragmenteCTS extends FragmentPagerAdapter {

    private Context mContext;

    public AdaptorFragmenteCTS(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "In apropiere";
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
