package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Activitati.DetaliiCTSActivity;
import ro.alexsalupa97.bloodbank.Activitati.ListaCentreActivity;
import ro.alexsalupa97.bloodbank.Activitati.PrimaPaginaActivity;
import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.R;

import java.util.ArrayList;

public class AdaptorLVCTS extends ArrayAdapter<CTS> {

    private Context context;

    public AdaptorLVCTS(Context context, ArrayList<CTS> lista) {
        super(context, 0, lista);
        this.context = context;
    }

    static class ViewHolder {
        public TextView numeCTS;
        public TextView adresaCTS;
        public TextView telefonCTS;
        public Button btnApelare;
        public ImageView ivRecomandat;
        public CardView cvCTS;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View viewRefolosibil = convertView;
        final CTS curent = getItem(position);

        if (viewRefolosibil == null) {
            viewRefolosibil = LayoutInflater.from(getContext()).inflate(R.layout.structura_elem_lv_cts, parent, false);


            ViewHolder viewHolder = new ViewHolder();

            viewHolder.numeCTS = (TextView) viewRefolosibil.findViewById(R.id.lvNumeCTS);
            viewHolder.adresaCTS = (TextView) viewRefolosibil.findViewById(R.id.lvAdresaCTS);
            viewHolder.telefonCTS = (TextView) viewRefolosibil.findViewById(R.id.lvTelefonCTS);
            viewHolder.btnApelare = (Button) viewRefolosibil.findViewById(R.id.btnApelare);
            viewHolder.ivRecomandat = (ImageView) viewRefolosibil.findViewById(R.id.ivRecomandat);
            viewHolder.cvCTS=(CardView)viewRefolosibil.findViewById(R.id.cvCTS);


            viewRefolosibil.setTag(viewHolder);
        }


        ViewHolder holder = (ViewHolder) viewRefolosibil.getTag();


        holder.numeCTS.setText(curent.getNumeCTS());
        holder.adresaCTS.setText(curent.getAdresaCTS());
        holder.telefonCTS.setText(curent.getTelefonCTS());

        try {
            if (curent.getNumeCTS().equals(ListaCentreActivity.closestCTS.getNumeCTS())) {
                holder.ivRecomandat.setImageResource(R.drawable.recomandat);
                holder.ivRecomandat.setPadding(20, 0, 0, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    holder.cvCTS.setOutlineSpotShadowColor(Color.parseColor("#B10000"));
                }
            } else {
                holder.ivRecomandat.setVisibility(View.GONE);
            }
        }catch (Exception ex){

        }


        holder.btnApelare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + curent.getTelefonCTS()));
                context.startActivity(intent);
            }
        });

        viewRefolosibil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaliiCTSActivity.class);
                intent.putExtra("cts", curent);
                context.startActivity(intent);
            }
        });


        return viewRefolosibil;


    }
}
