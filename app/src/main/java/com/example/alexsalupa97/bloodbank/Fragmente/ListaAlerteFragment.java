package com.example.alexsalupa97.bloodbank.Fragmente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteRV;
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import com.example.alexsalupa97.bloodbank.Clase.Compatibilitati;
import com.example.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import com.example.alexsalupa97.bloodbank.Clase.LimiteCTS;
import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;
import com.example.alexsalupa97.bloodbank.Utile.Utile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAlerteFragment extends Fragment {

    View rootView;

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    ArrayList<CantitatiCTS> listaCantitatiCTS;
    Map<CTS,ArrayList<CantitatiCTS>> mapCantitatiPerCTS;

    RecyclerView rvAlerte;
    ArrayList<SectionModelAlerte> sectiuni;




    public ListaAlerteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_lista_alerte, container, false);



        rvAlerte =(RecyclerView)rootView.findViewById(R.id.rvAlerte);



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

            mapCantitatiPerCTS = new HashMap<>();

            for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                String deAfisat = "\n\n";


                Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


                deAfisat += "\t" + cts.getNumeCTS();

                listaCantitatiCTS = new ArrayList<>();

                for (Compatibilitati grupaSanguinaDonator : Utile.compatibilitati) {
                    if (Utile.preluareGrupaSanguina(getActivity()).equals(grupaSanguinaDonator.getGrupaSanguinaDonatoare().getGrupaSanguina()))
                        try {
                            CantitatiCTS cantitateCTSCurent = new CantitatiCTS();
                            cantitateCTSCurent.setCts(cts);
                            cantitateCTSCurent.setGrupaSanguina(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                            cantitateCTSCurent.setCantitateDisponibilaML(mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                            cantitateCTSCurent.setCantitateLimitaML(mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                            listaCantitatiCTS.add(cantitateCTSCurent);
                            if (mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) < mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()))
                                deAfisat += "\n\t\t probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                            else
                                deAfisat += "\n\t\t nu sunt probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                        } catch (Exception ex) {

                        }

                }

                mapCantitatiPerCTS.put(cts, listaCantitatiCTS);
            }


        sectiuni=new ArrayList<>();

        for(CTS cts:mapCantitatiPerCTS.keySet()) {
            SectionModelAlerte dm = new SectionModelAlerte();

            dm.setTitlu(cts.getNumeCTS());

            ArrayList<ItemModelAlerte> itemeInSectiune = new ArrayList<>();
            for (CantitatiCTS cantitatiCTS : mapCantitatiPerCTS.get(cts)) {
                itemeInSectiune.add(new ItemModelAlerte(cantitatiCTS));
            }

            dm.setItemeInSectiune(itemeInSectiune);

            sectiuni.add(dm);
        }

            rvAlerte.setHasFixedSize(true);

            AdaptorAlerteRV adapter = new AdaptorAlerteRV(getActivity(), sectiuni);
            rvAlerte.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvAlerte.setAdapter(adapter);


        } catch (Exception ex) {

        }
        return rootView;
    }

}
