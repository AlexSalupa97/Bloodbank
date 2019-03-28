package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModelIstoric;

import java.util.ArrayList;

public class AdaptorListaItemeSectiuneRV extends RecyclerView.Adapter<AdaptorListaItemeSectiuneRV.SingleItemRowHolder> {

    private ArrayList<ItemModelIstoric> listaIteme;
    private Context context;

    public AdaptorListaItemeSectiuneRV(Context context, ArrayList<ItemModelIstoric> listaIteme) {
        this.listaIteme = listaIteme;
        this.context = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.structura_item_istoric, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {

        ItemModelIstoric singleItem = listaIteme.get(position);

        holder.tvDataDonare.setText(singleItem.getDataDonare());
        holder.tvCantitateDonata.setText(singleItem.getCantitateDonata());
    }

    @Override
    public int getItemCount() {
        return (null != listaIteme ? listaIteme.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvDataDonare;
        protected TextView tvCantitateDonata;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvDataDonare = (TextView) view.findViewById(R.id.tvDataDonare);
            this.tvCantitateDonata = (TextView) view.findViewById(R.id.tvCantitateDonata);


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