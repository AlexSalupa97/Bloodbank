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
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.joda.time.Instant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import ro.alexsalupa97.bloodbank.Activitati.StatisticiReceiverActivity;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticiZilniceCTSFragment extends Fragment {


    public StatisticiZilniceCTSFragment() {
        // Required empty public constructor
    }

    View rootView;
    public static String valoareData;
    public static String valoareCantitateML;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_statistici_zilnice_cts, container, false);



        GraphView graph = (GraphView) rootView.findViewById(R.id.gvStatistici);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        ArrayList<String> arrayValoriPeX = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = new Date();

        int[] listaCantitatiIntratePerOre = new int[24];
        int iValoareIntrariCantitateML = Integer.MIN_VALUE; // pentru mesaj tvStatistici

        for (int i = 0; i < 24; i++) {
            if (i < 10)
                arrayValoriPeX.add("0" + String.valueOf(i) + ":00");
            else
                arrayValoriPeX.add(String.valueOf(i) + ":00");
            for (IntrariCTS intrareCTS : Utile.listaIntrariCTS) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(intrareCTS));
                cal2.setTime(date);
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay && i == cal1.get(Calendar.HOUR_OF_DAY)) {
                    listaCantitatiIntratePerOre[cal1.get(Calendar.HOUR_OF_DAY)] += listaCantitatiIntratePerOre[cal1.get(Calendar.HOUR_OF_DAY)] + intrareCTS.getCantitatePrimitaML();
                }
            }
            series.appendData(new DataPoint(i, listaCantitatiIntratePerOre[i]), false, listaCantitatiIntratePerOre.length);
            if (iValoareIntrariCantitateML < listaCantitatiIntratePerOre[i]) {
                iValoareIntrariCantitateML = listaCantitatiIntratePerOre[i];
                valoareCantitateML = String.valueOf(iValoareIntrariCantitateML);
                valoareData = arrayValoriPeX.get(i);
            }
        }

        int[] listaCantitatiIesitePerOre = new int[24];
        int iValoareIesiriCantitateML = Integer.MIN_VALUE; // pentru mesaj tvStatistici

        for (int i = 0; i < 24; i++) {
            for (IesiriCTS iesireCTS : Utile.listaIesiriCTS) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(iesireCTS));
                cal2.setTime(date);
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay && i == cal1.get(Calendar.HOUR_OF_DAY)) {
                    listaCantitatiIesitePerOre[cal1.get(Calendar.HOUR_OF_DAY)] += iesireCTS.getCantitateIesitaML();
                }
            }
            series1.appendData(new DataPoint(i, listaCantitatiIesitePerOre[i]), false, listaCantitatiIesitePerOre.length);
            if (iValoareIesiriCantitateML < listaCantitatiIesitePerOre[i]) {
                iValoareIesiriCantitateML = listaCantitatiIesitePerOre[i];
//                valoareCantitateML = String.valueOf(iValoareIntrariCantitateML);
//                valoareData = arrayValoriPeX.get(i);
            }
        }


        graph.getGridLabelRenderer().setPadding(32);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Ore");
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
//        graph.getViewport().setDrawBorder(true);

        String[] valoriPeX = new String[arrayValoriPeX.size()];
        valoriPeX = arrayValoriPeX.toArray(valoriPeX);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(valoriPeX);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);

//        series.setDrawValuesOnTop(true);
//        series.setValuesOnTopColor(Color.BLACK);

        series.setColor(Color.GREEN);
        series1.setColor(Color.parseColor("#F44236"));

        series.setTitle("Intrari (ml)");
        series1.setTitle("Iesiri (ml)");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setBackgroundColor(Color.parseColor("#ececec"));
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        double xInterval = 1.0;
//        graph.getViewport().setXAxisBoundsManual(true);


//        graph.getGridLabelRenderer().setNumHorizontalLabels(23);

        graph.getViewport().setMinX(series.getLowestValueX() - (xInterval / 2.0));
        graph.getViewport().setMaxX(series.getHighestValueX() + (xInterval / 2.0));



        graph.getGridLabelRenderer().setLabelsSpace(20);
        graph.setTitle(dateFormat.format(date));
        graph.setTitleTextSize(60);
        graph.addSeries(series);
        graph.addSeries(series1);

//        series.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(getContext(), String.valueOf(dataPoint.getY() + " ml intrati la ora " + (int) dataPoint.getX() + ":00"), Toast.LENGTH_SHORT).show();
//            }
//        });
//        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(getContext(), String.valueOf(dataPoint.getY() + " ml iesiti la ora " + (int) dataPoint.getX() + ":00"), Toast.LENGTH_SHORT).show();
//            }
//        });

//        if (StatisticiZilniceReceiverFragment.valoareData != null&&!StatisticiReceiverActivity.dejaAdaugatZilnic) {
//            StatisticiReceiverActivity.listaStatistici.add("In aceasta zi, ora cea mai \"bogata\" in donatii a fost " + StatisticiZilniceReceiverFragment.valoareData + ", cu o cantitate de sange primita de " + StatisticiZilniceReceiverFragment.valoareCantitateML + "ml.");
//            StatisticiReceiverActivity.dejaAdaugatZilnic=true;
//        }
//        Collections.shuffle(StatisticiReceiverActivity.listaStatistici);
//        StatisticiReceiverActivity.tvStatistici.setText(StatisticiReceiverActivity.listaStatistici.get(0));


        return rootView;
    }

    public Date getDateFromString(IntrariCTS istoricReceiver) {
        Instant instant = Instant.parse(istoricReceiver.getDataPrimire());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.getMillis());
        return calendar.getTime();
    }

    public Date getDateFromString(IesiriCTS istoricReceiver) {
        Instant instant = Instant.parse(istoricReceiver.getDataIesire());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.getMillis());
        return calendar.getTime();
    }

}
