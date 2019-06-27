package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import ro.alexsalupa97.bloodbank.Activitati.DetaliiCTSActivity;
import ro.alexsalupa97.bloodbank.Activitati.DetaliiReceiverActivity;
import ro.alexsalupa97.bloodbank.Activitati.ListaCentreActivity;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class AdaptorReceiveriLV extends ArrayAdapter<Receiveri> {

    private Context context;
    ArrayList<Receiveri> customers, tempCustomer, suggestions;

    public AdaptorReceiveriLV(Context context, ArrayList<Receiveri> lista) {
        super(context, 0, lista);
        customers=lista;
        tempCustomer=new ArrayList<>(lista);
        suggestions=new ArrayList<>(lista);
        this.context = context;
    }

    static class ViewHolder {
        public TextView tvNume;
        public TextView tvCTS;
        public ImageView ivReceiver;
        public ImageView vwMargine;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View viewRefolosibil = convertView;
        final Receiveri curent = getItem(position);

        if (viewRefolosibil == null) {
            viewRefolosibil = LayoutInflater.from(getContext()).inflate(R.layout.structura_item_pacient, parent, false);


            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvNume = (TextView) viewRefolosibil.findViewById(R.id.tvNumePacient);
            viewHolder.tvCTS = (TextView) viewRefolosibil.findViewById(R.id.tvCTSPacient);
            viewHolder.ivReceiver = (ImageView) viewRefolosibil.findViewById(R.id.ivPacient);
            viewHolder.vwMargine = (ImageView) viewRefolosibil.findViewById(R.id.vwMargine);

            if (curent.getStareReceiver() == 1)
                viewHolder.vwMargine.setBackground(context.getResources().getDrawable(R.drawable.gradient_orange_red_vertical));
            else if (curent.getStareReceiver() == 2)
                viewHolder.vwMargine.setBackground(context.getResources().getDrawable(R.drawable.gradient_yellow_red_vertical));
            else
                viewHolder.vwMargine.setBackground(context.getResources().getDrawable(R.drawable.gradient_red_vertical));


            viewRefolosibil.setTag(viewHolder);
        }


        ViewHolder holder = (ViewHolder) viewRefolosibil.getTag();


        holder.tvNume.setText(curent.getNumeReceiver());
        holder.tvCTS.setText(curent.getCts().getNumeCTS());
//        holder.ivReceiver.(curent.getTelefonCTS());


        viewRefolosibil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Receiveri receiver : Utile.listaReceiveri)
                    if (receiver.getNumeReceiver().equals(curent.getNumeReceiver())) {

                        Intent intent = new Intent(context, DetaliiReceiverActivity.class);
                        intent.putExtra("receiver", receiver);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
            }
        });


        return viewRefolosibil;


    }

//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
//    Filter myFilter =new Filter() {
//        @Override
//        public CharSequence convertResultToString(Object resultValue) {
//            Receiveri customer =(Receiveri) resultValue ;
//            return customer.getNumeReceiver();
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            if (constraint != null) {
//                suggestions.clear();
//                for (Receiveri cust : tempCustomer) {
//                    if (cust.getNumeReceiver().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
//                        suggestions.add(cust);
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = suggestions;
//                filterResults.count = suggestions.size();
//                return filterResults;
//            } else {
//                return new FilterResults();
//            }
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            ArrayList<Receiveri> c =  (ArrayList<Receiveri> )results.values ;
//            if (results != null && results.count > 0) {
//                clear();
//                for (Receiveri cust : c) {
//                    add(cust);
//                    notifyDataSetChanged();
//                }
//            }
//            else{
//                clear();
//                notifyDataSetChanged();
//            }
//        }
//    };

    public ArrayList<Receiveri> filter(String charText, ArrayList<Receiveri> listaInitiala){
        charText = charText.toLowerCase(Locale.getDefault());
        suggestions.clear();
        if (charText.length()==0){
            suggestions.addAll(listaInitiala);
        }
        else {
            for (Receiveri model : listaInitiala){
                if (model.getNumeReceiver().toLowerCase(Locale.getDefault())
                        .startsWith(charText)){
                    suggestions.add(model);
                }
            }
        }
        return suggestions;
    }
}

