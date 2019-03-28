package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelAlerte;
import com.github.anastr.speedviewlib.SpeedView;

import java.util.ArrayList;

public class AdaptorListaAlerteInSectiuneRV extends RecyclerView.Adapter<AdaptorListaAlerteInSectiuneRV.SingleItemRowHolder> {

    private ArrayList<ItemModelAlerte> listaIteme;
    private Context context;

    public AdaptorListaAlerteInSectiuneRV(Context context, ArrayList<ItemModelAlerte> listaIteme) {
        this.listaIteme = listaIteme;
        this.context = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.structura_item_alerta, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {

        ItemModelAlerte curent = listaIteme.get(position);

        holder.svGrafic.setUnit(curent.getCantitatiCTS().getGrupaSanguina().getGrupaSanguina());
        holder.svGrafic.setMinMaxSpeed(0,curent.getCantitatiCTS().getCantitateLimitaML()*3);
        holder.svGrafic.setSpeedAt(curent.getCantitatiCTS().getCantitateDisponibilaML());
    }

    @Override
    public int getItemCount() {
        return (null != listaIteme ? listaIteme.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected SpeedView svGrafic;


        public SingleItemRowHolder(View view) {
            super(view);

            this.svGrafic=(SpeedView)view.findViewById(R.id.svGrafic);


//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), tvCantitateDonata.getText(), Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
        }
    }
}