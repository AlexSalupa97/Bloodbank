package ro.alexsalupa97.bloodbank.Fragmente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorLVCTS;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;

public class ListaCTSInApropiereFragment extends Fragment {

    View rootView;

    AdaptorLVCTS adaptor;
    ListView listView;



    public ListaCTSInApropiereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cts_apropiate, container, false);

        ArrayList<CTS> listaCTSInApropiere=new ArrayList<>();

        for(CTS c:Utile.CTS)
            if(c.getOras().getOras().equals(Utile.preluareOras(getActivity())))
                listaCTSInApropiere.add(c);

        adaptor = new AdaptorLVCTS(getActivity(), listaCTSInApropiere);
        listView = (ListView) rootView.findViewById(R.id.lvCentreApropiate);
        listView.setAdapter(adaptor);


        return rootView;
    }
}
