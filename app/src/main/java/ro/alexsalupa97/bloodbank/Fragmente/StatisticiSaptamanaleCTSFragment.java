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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ro.alexsalupa97.bloodbank.Activitati.StatisticiCTSActivity;
import ro.alexsalupa97.bloodbank.Activitati.StatisticiReceiverActivity;
import ro.alexsalupa97.bloodbank.Clase.IesiriCTS;
import ro.alexsalupa97.bloodbank.Clase.IntrariCTS;
import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticiSaptamanaleCTSFragment extends Fragment {


    public StatisticiSaptamanaleCTSFragment() {
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
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -6);
        Date dateStart = cal.getTime();

        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date dateEnd = new Date();


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.SECOND,10); //procesare mai greoaie => nu se ia ultima data, mai adaugam secunde la endDate
        final ArrayList<Date> datesInRange = new ArrayList<>();
        while (cal.before(calendar)) {
            Date result = cal.getTime();
            datesInRange.add(result);
            cal.add(Calendar.DATE, 1);
        }

        Integer[] listaCantitatiIntratePerZi=new Integer[7];
        for (int i = 0; i < datesInRange.size(); i++) {
            listaCantitatiIntratePerZi[i]=0;
            for (IntrariCTS intrareCTS : Utile.listaIntrariCTS) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(intrareCTS));
                cal2.setTime(datesInRange.get(i));
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay) {
                    listaCantitatiIntratePerZi[i] += intrareCTS.getCantitatePrimitaML();
                }
            }
        }

        Integer[] listaCantitatiIesitePerZi=new Integer[7];
        for (int i = 0; i < datesInRange.size(); i++) {
            listaCantitatiIesitePerZi[i]=0;
            for (IesiriCTS iesireCTS : Utile.listaIesiriCTS) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(iesireCTS));
                cal2.setTime(datesInRange.get(i));
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay) {
                    listaCantitatiIesitePerZi[i] += iesireCTS.getCantitateIesitaML();
                }
            }
        }

        int iValoareMax=Integer.MIN_VALUE;
        final ArrayList<String> arrayValoriPeX = new ArrayList<>();
        for (int i = 0; i < datesInRange.size(); i++) {
            String dateString = dateFormat.format(datesInRange.get(i));
            int indexString = dateString.indexOf("/");
            String dateDayString = dateString.substring(0, indexString);

            arrayValoriPeX.add(dateDayString);


            series.appendData(new DataPoint(i, listaCantitatiIntratePerZi[i]), false, datesInRange.size());
            series1.appendData(new DataPoint(i, listaCantitatiIesitePerZi[i]), false, datesInRange.size());

            if(iValoareMax<listaCantitatiIntratePerZi[i]){
                iValoareMax=listaCantitatiIntratePerZi[i];
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


        graph.getGridLabelRenderer().setLabelsSpace(20); //overlapping x-y solution
        graph.setTitle(dateFormat.format(dateStart) + " - " + dateFormat.format(dateEnd));
        graph.setTitleTextSize(60);
        graph.addSeries(series);
        graph.addSeries(series1);


//        series.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                String date=dateFormat.format(datesInRange.get((int)dataPoint.getX()));
//                Toast.makeText(getContext(),String.valueOf(dataPoint.getY()+"ml intrati la data de "+date),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                String date=dateFormat.format(datesInRange.get((int)dataPoint.getX()));
//                Toast.makeText(getContext(),String.valueOf(dataPoint.getY()+"ml iesiti la data de "+date),Toast.LENGTH_SHORT).show();
//            }
//        });

        if (Integer.parseInt(StatisticiSaptamanaleCTSFragment.valoareCantitateML) != 0&&!StatisticiCTSActivity.dejaAdaugatSaptamanal) {
            StatisticiCTSActivity.listaStatistici.add("In aceasta saptamana, cele mai multe donatii s-au inregistrat in data de " + StatisticiSaptamanaleCTSFragment.valoareData + ", cu " + StatisticiSaptamanaleCTSFragment.valoareCantitateML + "ml primiti.");
            StatisticiCTSActivity.dejaAdaugatSaptamanal=true;
        }
        Collections.shuffle(StatisticiCTSActivity.listaStatistici);
        try {
            StatisticiCTSActivity.tvStatistici.setText(StatisticiCTSActivity.listaStatistici.get(0));
        }catch (Exception e){

        }


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
