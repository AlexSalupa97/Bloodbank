package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ro.alexsalupa97.bloodbank.Activitati.DetaliiSuplimentareIstoricActivity;
import ro.alexsalupa97.bloodbank.Activitati.StatisticiReceiverActivity;
import ro.alexsalupa97.bloodbank.Activitati.TransparentReceiverActivity;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.SectionModelIstoric;

public class AdaptorIstoricReceiverRV extends RecyclerView.Adapter<AdaptorIstoricReceiverRV.ItemRowHolder> {

    private ArrayList<SectionModelIstoric> listaSectiuni;
    private Context context;

    public AdaptorIstoricReceiverRV(Context context, ArrayList<SectionModelIstoric> listaSectiuni) {
        this.listaSectiuni = listaSectiuni;
        this.context = context;
    }

    @Override
    public AdaptorIstoricReceiverRV.ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.structura_sectiuni_istoric, null);
        AdaptorIstoricReceiverRV.ItemRowHolder mh = new AdaptorIstoricReceiverRV.ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(AdaptorIstoricReceiverRV.ItemRowHolder itemRowHolder, int position) {


        final String numeSectiune = listaSectiuni.get(position).getTitlu();

        ArrayList itemeSectiune = listaSectiuni.get(position).getItemeInSectiune();

        itemRowHolder.itemTitle.setText(numeSectiune);


        AdaptorListaItemeSectiuneRV itemListDataAdapter = new AdaptorListaItemeSectiuneRV(context, itemeSectiune);

        itemRowHolder.recycler_view_list.setBackgroundColor(Color.parseColor("#ececec"));
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), StatisticiReceiverActivity.class);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return (null != listaSectiuni ? listaSectiuni.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;



        public ItemRowHolder(View view) {
            super(view);

            RelativeLayout rl=(RelativeLayout)view.findViewById(R.id.rl);
            rl.setBackgroundColor(Color.parseColor("#ececec"));

            this.itemTitle = (TextView) view.findViewById(R.id.titluSectiune);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMaiMult);

            btnMore.setText("Statistici");
            btnMore.setBackgroundColor(Color.parseColor("#ececec"));




        }

    }

}

