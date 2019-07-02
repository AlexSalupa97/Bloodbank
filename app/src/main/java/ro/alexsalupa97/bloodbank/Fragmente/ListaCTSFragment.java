package ro.alexsalupa97.bloodbank.Fragmente;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import ro.alexsalupa97.bloodbank.Activitati.ListaCentreActivity;
import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorLVCTS;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class ListaCTSFragment extends Fragment {
    View rootView;

    AdaptorLVCTS adaptor;
    ListView listView;
    ArrayList<CTS> listaCTS;

    public ListaCTSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_cts_all, container, false);
        final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N&& manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            listaCTS=new ArrayList<>();
            LinkedHashMap<CTS,Double> mapCTSsortate =
                    ListaCentreActivity.mapListaDistante.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Double::compareTo))
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            listaCTS.addAll(mapCTSsortate.keySet());
            adaptor = new AdaptorLVCTS(getActivity(), listaCTS);
        }else {
            Collections.sort(Utile.CTS);
            adaptor = new AdaptorLVCTS(getActivity(), Utile.CTS);
        }

        listView = (ListView) rootView.findViewById(R.id.lvCentreAll);
        listView.setAdapter(adaptor);


        return rootView;
    }
}
