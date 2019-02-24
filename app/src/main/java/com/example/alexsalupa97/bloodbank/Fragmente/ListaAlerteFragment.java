package com.example.alexsalupa97.bloodbank.Fragmente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import com.example.alexsalupa97.bloodbank.Clase.LimiteCTS;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAlerteFragment extends Fragment {

    View rootView;

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    TextView tvDetaliiLimite;


    public ListaAlerteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_alerte, container, false);

        tvDetaliiLimite = (TextView) rootView.findViewById(R.id.tvDetaliiLimite);
        tvDetaliiLimite.setText("");


        try {
            mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil());
            mapLimitePerCTSPerGrupa = new HashMap<>();


            for (CTS cts : Utile.CTS) {
                Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                for (LimiteCTS limite : Utile.listaLimiteCTS)
                    if (limite.getCts().getNumeCTS().equals(cts.getNumeCTS()))
                        mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                mapLimitePerCTSPerGrupa.put(cts, mapIntermediar);
            }

            for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                String deAfisat = "\n\n";


                Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


                deAfisat += "\t"+cts.getNumeCTS();

                for (Compatibilitati grupaSanguinaDonator : Utile.compatibilitati) {
                    if (Utile.preluareGrupaSanguina(getActivity()).equals(grupaSanguinaDonator.getGrupaSanguinaDonatoare().getGrupaSanguina()))
                        try {
                            if (mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) < mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()))
                                deAfisat += "\n\t\t probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                            else
                                deAfisat += "\n\t\t nu sunt probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                        } catch (Exception ex) {

                        }

                }

                tvDetaliiLimite.setText(tvDetaliiLimite.getText() + deAfisat);


            }

        } catch (Exception ex) {

        }
        return rootView;
    }

}
