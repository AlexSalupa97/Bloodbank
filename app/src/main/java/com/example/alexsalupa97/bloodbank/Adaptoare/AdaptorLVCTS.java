package com.example.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexsalupa97.bloodbank.Activitati.DetaliiCTSActivity;
import com.example.alexsalupa97.bloodbank.Clase.CTS;
import com.example.alexsalupa97.bloodbank.R;

import java.util.ArrayList;

public class AdaptorLVCTS extends ArrayAdapter<CTS> {

    private Context context;

    public AdaptorLVCTS(Context context, ArrayList<CTS> lista) {
        super(context, 0,lista);
        this.context=context;
    }

    static class ViewHolder {
        public TextView numeCTS;
        public TextView adresaCTS;
        public TextView telefonCTS;
//        public ImageView logo;
//        public ImageView location;
//        public ImageView phone;
        public Button btnApelare;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View viewRefolosibil = convertView;
        final CTS curent = getItem(position);

        if (viewRefolosibil == null) {
            viewRefolosibil = LayoutInflater.from(getContext()).inflate(R.layout.structura_elem_lv_cts, parent, false);

//            TextView tvNumeCTS= (TextView) viewRefolosibil.findViewById(R.id.lvNumeCTS);
//            tvNumeCTS.setText(curent.getNumeCTS());
//            TextView tvAdresaCTS = (TextView) viewRefolosibil.findViewById(R.id.lvAdresaCTS);
//            tvAdresaCTS.setText(curent.getAdresaCTS());
//            TextView tvTelefonCTS = (TextView) viewRefolosibil.findViewById(R.id.lvTelefonCTS);
//            tvTelefonCTS.setText(curent.getTelefonCTS());


            ViewHolder viewHolder = new ViewHolder();

            viewHolder.numeCTS = (TextView) viewRefolosibil.findViewById(R.id.lvNumeCTS);
            viewHolder.adresaCTS = (TextView) viewRefolosibil.findViewById(R.id.lvAdresaCTS);
            viewHolder.telefonCTS = (TextView) viewRefolosibil.findViewById(R.id.lvTelefonCTS);
            viewHolder.btnApelare=(Button)viewRefolosibil.findViewById(R.id.btnApelare);
//            viewHolder.logo=(ImageView)viewRefolosibil.findViewById(R.id.lvLogoIcon);
//            viewHolder.location=(ImageView)viewRefolosibil.findViewById(R.id.lvLocationIcon);
//            viewHolder.phone=(ImageView)viewRefolosibil.findViewById(R.id.lvPhoneIcon);

            viewRefolosibil.setTag(viewHolder);
        }


        ViewHolder holder = (ViewHolder) viewRefolosibil.getTag();


        holder.numeCTS.setText(curent.getNumeCTS());
        holder.adresaCTS.setText(curent.getAdresaCTS());
        holder.telefonCTS.setText(curent.getTelefonCTS());
//        holder.logo.setImageResource(R.mipmap.ic_bloodbank_launcher_round);
//        holder.location.setImageResource(R.drawable.ic_location);
//        holder.phone.setImageResource(R.drawable.ic_phone);


        holder.btnApelare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+curent.getTelefonCTS()));
                context.startActivity(intent);
            }
        });

        viewRefolosibil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetaliiCTSActivity.class);
                context.startActivity(intent);
            }
        });


        return viewRefolosibil;


    }
}
