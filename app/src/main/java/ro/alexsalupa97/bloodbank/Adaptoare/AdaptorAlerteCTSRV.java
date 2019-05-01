package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelAlerte;

import java.util.ArrayList;

public class AdaptorAlerteCTSRV extends RecyclerView.Adapter<AdaptorAlerteCTSRV.ItemRowHolder> {

    private ArrayList<SectionModelAlerte> listaSectiuni;
    private Context context;

    public AdaptorAlerteCTSRV(Context context, ArrayList<SectionModelAlerte> listaSectiuni) {
        this.listaSectiuni = listaSectiuni;
        this.context = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.structura_sectiuni_alerte_cts, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int position) {

        final String numeSectiune = listaSectiuni.get(position).getTitlu();

        ArrayList itemeSectiune = listaSectiuni.get(position).getItemeInSectiune();

        itemRowHolder.itemTitle.setText(numeSectiune);

        AdaptorListaAlerteInSectiuneRV itemListDataAdapter = new AdaptorListaAlerteInSectiuneRV(context, itemeSectiune);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);




    }

    @Override
    public int getItemCount() {
        return (null != listaSectiuni ? listaSectiuni.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;


        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.titluSectiune);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);


        }

    }

}
