package ro.alexsalupa97.bloodbank.Fragmente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.alexsalupa97.bloodbank.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupDonatorFragment extends Fragment {

    View rootView;

    public SignupDonatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_signup_donator, container, false);
        return rootView;
    }

}
