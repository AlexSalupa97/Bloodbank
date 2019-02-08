package com.example.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexsalupa97.bloodbank.R;
import com.example.alexsalupa97.bloodbank.RecyclerViewOrizontal.ItemModel;

import java.util.ArrayList;

public class AdaptorListaItemeSectiuneRV extends RecyclerView.Adapter<AdaptorListaItemeSectiuneRV.SingleItemRowHolder> {

    private ArrayList<ItemModel> listaIteme;
    private Context context;

    public AdaptorListaItemeSectiuneRV(Context context, ArrayList<ItemModel> listaIteme) {
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

        ItemModel singleItem = listaIteme.get(position);

        holder.tvTitle.setText(singleItem.getText());
        holder.rbPunctajIstoric.setRating(holder.rbPunctajIstoric.getNumStars() * 0.8f);
    }

    @Override
    public int getItemCount() {
        return (null != listaIteme ? listaIteme.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected RatingBar rbPunctajIstoric;
        protected ImageView ivPozaIstoric;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.rbPunctajIstoric = (RatingBar) view.findViewById(R.id.rbPunctajIstoric);
            this.ivPozaIstoric = (ImageView) view.findViewById(R.id.ivPozaIstoric);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();


                }
            });
        }
    }
}