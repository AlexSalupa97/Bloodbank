package ro.alexsalupa97.bloodbank.Fragmente;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.joda.time.Instant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import ro.alexsalupa97.bloodbank.Activitati.StatisticiReceiverActivity;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticiLunareReceiverFragment extends Fragment {


    public StatisticiLunareReceiverFragment() {
        // Required empty public constructor
    }

    View rootView;

    public static String valoareData;
    public static String valoareCantitateML;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_statistici_zilnice_receiver, container, false);

        GraphView graph = (GraphView) rootView.findViewById(R.id.gvStatistici);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DATE,1);
        Date dateStart = cal.getTime();

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date dateEnd = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.SECOND, 10); //procesare mai greoaie => nu se ia ultima data, mai adaugam secunde la endDate
        final ArrayList<Date> datesInRange = new ArrayList<>();
        while (cal.before(calendar)) {
            Date result = cal.getTime();
            datesInRange.add(result);
            cal.add(Calendar.DATE, 1);
        }


        Integer[] listaCantitatiPerZi = new Integer[datesInRange.size()];
        Arrays.fill(listaCantitatiPerZi,0);
        for (int i = 0; i < datesInRange.size(); i++) {
            listaCantitatiPerZi[i] = 0;
            for (IstoricReceiver istoricReceiver : Utile.listaIstoricReceiver) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(istoricReceiver));
                cal2.setTime(datesInRange.get(i));
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay) {
                    listaCantitatiPerZi[i] += istoricReceiver.getCantitatePrimitaML();
                }
            }
        }

        final ArrayList<String> arrayValoriPeX = new ArrayList<>();
        int iValoareMax=Integer.MIN_VALUE;
        for (int i = 0; i < datesInRange.size(); i++) {
            String dateString = dateFormat.format(datesInRange.get(i));
            int indexString = dateString.indexOf("/");
            String dateDayString = dateString.substring(0, indexString);

            arrayValoriPeX.add(dateDayString);


            series.appendData(new DataPoint(i, listaCantitatiPerZi[i]), false, datesInRange.size());

            if(iValoareMax<listaCantitatiPerZi[i]){
                iValoareMax=listaCantitatiPerZi[i];
                valoareCantitateML=String.valueOf(iValoareMax);
                valoareData=dateFormat.format(datesInRange.get(i));
            }

        }

        graph.getGridLabelRenderer().setPadding(32);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Zile");
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
//        graph.getViewport().setDrawBorder(true);

        String[] valoriPeX = new String[arrayValoriPeX.size()];
        valoriPeX = arrayValoriPeX.toArray(valoriPeX);
        final StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(valoriPeX);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

//        series.setDrawValuesOnTop(true);
//        series.setValuesOnTopColor(Color.BLACK);

        series.setColor(Color.parseColor("#F44236"));

        series.setTitle("Cantitati (ml)");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setBackgroundColor(Color.parseColor("#ececec"));
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        double xInterval = 1.0;
//        graph.getViewport().setXAxisBoundsManual(true);


//        graph.getGridLabelRenderer().setNumHorizontalLabels(23);

        graph.getViewport().setMinX(series.getLowestValueX() - (xInterval / 2.0));
        graph.getViewport().setMaxX(series.getHighestValueX() + (xInterval / 2.0));


        series.setSpacing(20);
        graph.getGridLabelRenderer().setLabelsSpace(20); //overlapping x-y solution
        graph.setTitle(dateFormat.format(dateStart) + " - " + dateFormat.format(dateEnd));
        graph.setTitleTextSize(60);
        graph.addSeries(series);


        getDateFromString(Utile.listaIstoricReceiver.get(0));

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String date = dateFormat.format(datesInRange.get((int) dataPoint.getX()));
                Toast.makeText(getContext(), String.valueOf(dataPoint.getY() + "ml la data de " + date), Toast.LENGTH_SHORT).show();
            }
        });

        if (StatisticiLunareReceiverFragment.valoareData != null&&!StatisticiReceiverActivity.dejaAdaugatLunar) {
            StatisticiReceiverActivity.listaStatistici.add("Luna curenta a adus cea mai mare cantitate sanguina in ziua de " + StatisticiLunareReceiverFragment.valoareData + ": " + StatisticiLunareReceiverFragment.valoareCantitateML + "ml.");
            StatisticiReceiverActivity.dejaAdaugatLunar=true;
        }
        Collections.shuffle(StatisticiReceiverActivity.listaStatistici);
        StatisticiReceiverActivity.tvStatistici.setText(StatisticiReceiverActivity.listaStatistici.get(0));

       Toast.makeText(getActivity(),StatisticiReceiverActivity.listaStatistici.size()+" ",Toast.LENGTH_SHORT).show();

        return rootView;
    }

    public Date getDateFromString(IstoricReceiver istoricReceiver) {
        Instant instant = Instant.parse(istoricReceiver.getDataPrimire());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.getMillis());
        Date date = calendar.getTime();
        return date;

    }

}
