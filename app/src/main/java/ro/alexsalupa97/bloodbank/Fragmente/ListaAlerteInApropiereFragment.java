package ro.alexsalupa97.bloodbank.Fragmente;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorAlerteRV;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.Clase.Compatibilitati;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.LimiteCTS;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;
import ro.alexsalupa97.bloodbank.Utile.Utile;
import ro.alexsalupa97.bloodbank.ViewCustom.BulletTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAlerteInApropiereFragment extends Fragment {

    View rootView;

    Map<CTS, Map<GrupeSanguine, Integer>> mapCantitatiDisponibilePerCTSPerGrupa;
    Map<CTS, Map<GrupeSanguine, Integer>> mapLimitePerCTSPerGrupa;

    ArrayList<CantitatiCTS> listaCantitatiCTS;
    Map<CTS,ArrayList<CantitatiCTS>> mapCantitatiPerCTS;


//    ListView lvAlerte;
    RecyclerView rvAlerteApropiere;
    ArrayList<SectionModelAlerte> sectiuni;

    TextView tvAlerte;

    public ListaAlerteInApropiereFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView= inflater.inflate(R.layout.fragment_lista_alerte_in_apropiere, container, false);


        rvAlerteApropiere=(RecyclerView)rootView.findViewById(R.id.rvAlerteApropiere);

//        lvAlerte=(ListView)rootView.findViewById(R.id.lvAlerte);

        ArrayList<String> listaAlerte=new ArrayList<>();

        try {
            mapCantitatiDisponibilePerCTSPerGrupa = new HashMap<>(Utile.incarcareMapDisponibil());

            mapLimitePerCTSPerGrupa = new HashMap<>();

            Collections.sort(Utile.CTS);

            for (CTS cts : Utile.CTS) {
                Map<GrupeSanguine, Integer> mapIntermediar = new HashMap<>();
                for (LimiteCTS limite : Utile.listaLimiteCTS)
                    if (limite.getCts().getNumeCTS().equals(cts.getNumeCTS()))
                        mapIntermediar.put(limite.getGrupaSanguina(), limite.getLimitaML());
                mapLimitePerCTSPerGrupa.put(cts, mapIntermediar);
            }

            mapCantitatiPerCTS=new HashMap<>();


            for (CTS cts : mapCantitatiDisponibilePerCTSPerGrupa.keySet()) {
                String deAfisat = "";


                if (cts.getOras().getOras().equals(Utile.preluareOras(getActivity()))) {
                    listaCantitatiCTS=new ArrayList<>();
                    Map<GrupeSanguine, Integer> mapCantitatiDisponibile = mapCantitatiDisponibilePerCTSPerGrupa.get(cts);
                    Map<GrupeSanguine, Integer> mapLimite = mapLimitePerCTSPerGrupa.get(cts);


//                    deAfisat += "\n\n\t"+cts.getNumeCTS();

                    for (Compatibilitati grupaSanguinaDonator : Utile.compatibilitati) {
                        if (Utile.preluareGrupaSanguina(getActivity()).equals(grupaSanguinaDonator.getGrupaSanguinaDonatoare().getGrupaSanguina()))
                            try {
                                CantitatiCTS cantitateCTSCurent=new CantitatiCTS();
                                cantitateCTSCurent.setCts(cts);
                                cantitateCTSCurent.setGrupaSanguina(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                                cantitateCTSCurent.setCantitateDisponibilaML(mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                                cantitateCTSCurent.setCantitateLimitaML(mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()));
                                listaCantitatiCTS.add(cantitateCTSCurent);

                                if (mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) < mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()))
                                    listaAlerte.add(cts.getNumeCTS()+"\n\n\t\tprobleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + "\n\t\tlimita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + "\n\t\tdisponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver())+"\n");
//                                else
//                                    deAfisat+="\n\t\t nu sunt probleme cu " + grupaSanguinaDonator.getGrupaSanguinaReceiver().getGrupaSanguina() + " limita: " + mapLimite.get(grupaSanguinaDonator.getGrupaSanguinaReceiver()) + " disponibil: " + mapCantitatiDisponibile.get(grupaSanguinaDonator.getGrupaSanguinaReceiver());
                            }
                            catch (Exception ex)
                            {

                            }
                    }

                    mapCantitatiPerCTS.put(cts,listaCantitatiCTS);
                }

            }

            tvAlerte=(TextView)rootView.findViewById(R.id.tvAlerte);
            Collections.sort(listaAlerte);
            String[] stringList = new String[listaAlerte.size()];
            stringList = listaAlerte.toArray(stringList);
            CharSequence bulletedList = BulletTextView.makeBulletList(100,stringList);
            tvAlerte.setText(bulletedList);

            sectiuni=new ArrayList<>();
            ArrayList<CTS> listaCTS=new ArrayList<>(mapCantitatiPerCTS.keySet());
            Collections.sort(listaCTS);

            for(CTS cts:listaCTS) {
                SectionModelAlerte dm = new SectionModelAlerte();

                dm.setTitlu(cts.getNumeCTS());

                ArrayList<ItemModelAlerte> itemeInSectiune = new ArrayList<>();
                for (CantitatiCTS cantitatiCTS : mapCantitatiPerCTS.get(cts)) {
                    itemeInSectiune.add(new ItemModelAlerte(cantitatiCTS));
                }

                dm.setItemeInSectiune(itemeInSectiune);

                sectiuni.add(dm);

            }

            rvAlerteApropiere.setHasFixedSize(true);

            AdaptorAlerteRV adapter = new AdaptorAlerteRV(getActivity(), sectiuni);
            rvAlerteApropiere.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvAlerteApropiere.setAdapter(adapter);


        } catch (Exception ex) {
        }





//        AdaptorAlerteLV adaptor=new AdaptorAlerteLV(getActivity(),listaCantitatiCTS);
//        lvAlerte.setAdapter(adaptor);


        // Inflate the layout for this fragment
        return  rootView;

    }

}
