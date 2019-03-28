package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.R;

import java.util.ArrayList;

public class AdaptorIstoricDonatiiLV extends ArrayAdapter<IstoricDonatii> {

    private Context context;

    public AdaptorIstoricDonatiiLV(Context context, ArrayList<IstoricDonatii> lista) {
        super(context, 0,lista);
        this.context=context;
    }

    static class ViewHolder {
        public TextView tvDataDonare;
        public TextView tvCantitateDonata;
        public CardView cvIstoric;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View viewRefolosibil = convertView;
        final IstoricDonatii curent = getItem(position);

        if (viewRefolosibil == null) {
            viewRefolosibil = LayoutInflater.from(getContext()).inflate(R.layout.structura_elem_lv_istoric, parent, false);



            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvDataDonare = (TextView) viewRefolosibil.findViewById(R.id.tvDataDonare);
            viewHolder.tvCantitateDonata = (TextView) viewRefolosibil.findViewById(R.id.tvCantitateDonata);
            viewHolder.cvIstoric=(CardView)viewRefolosibil.findViewById(R.id.cvIstoric);


            viewRefolosibil.setTag(viewHolder);
        }


        ViewHolder holder = (ViewHolder) viewRefolosibil.getTag();

        int index=curent.getDataDonatie().indexOf("T");
        String substring=curent.getDataDonatie().substring(0,index);


        holder.tvDataDonare.setText("Data: "+substring);
        holder.tvCantitateDonata.setText("Cantitatea donata: "+curent.getCantitateDonataML()+"ml");
        holder.cvIstoric.setBackground(context.getResources().getDrawable(R.drawable.gradient_ok));




        return viewRefolosibil;


    }
}

