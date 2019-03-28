package ro.alexsalupa97.bloodbank.Adaptoare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import ro.alexsalupa97.bloodbank.Clase.CantitatiCTS;
import ro.alexsalupa97.bloodbank.R;
import com.github.anastr.speedviewlib.SpeedView;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

public class AdaptorAlerteLV extends ArrayAdapter<CantitatiCTS> {

    private Context context;

    public AdaptorAlerteLV(Context context, ArrayList<CantitatiCTS> lista) {
        super(context, 0,lista);
        this.context=context;
    }

    static class ViewHolder {
        public CircleProgressView cpvGrafic;
        public SpeedView svGrafic;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View viewRefolosibil = convertView;
        final CantitatiCTS curent = getItem(position);

        if (viewRefolosibil == null) {
            viewRefolosibil = LayoutInflater.from(getContext()).inflate(R.layout.structura_item_alerta, parent, false);



            ViewHolder viewHolder = new ViewHolder();

//            viewHolder.cpvGrafic = (CircleProgressView) viewRefolosibil.findViewById(R.id.cpvGrafic);
            viewHolder.svGrafic=(SpeedView)viewRefolosibil.findViewById(R.id.svGrafic);


            viewRefolosibil.setTag(viewHolder);
        }


        ViewHolder holder = (ViewHolder) viewRefolosibil.getTag();




        holder.cpvGrafic.setText(curent.getGrupaSanguina().getGrupaSanguina());
        holder.cpvGrafic.setMaxValue(curent.getCantitateLimitaML()*3);
        holder.cpvGrafic.setValue(curent.getCantitateDisponibilaML());

        holder.svGrafic.setUnit(curent.getGrupaSanguina().getGrupaSanguina());
        holder.svGrafic.setMinMaxSpeed(0,curent.getCantitateLimitaML()*3);
        holder.svGrafic.setSpeedAt(curent.getCantitateDisponibilaML());






        return viewRefolosibil;


    }
}
