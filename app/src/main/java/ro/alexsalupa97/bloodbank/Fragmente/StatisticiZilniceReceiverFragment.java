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
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import ro.alexsalupa97.bloodbank.Clase.IstoricReceiver;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticiZilniceReceiverFragment extends Fragment {


    public StatisticiZilniceReceiverFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_statistici_zilnice_receiver, container, false);

        GraphView graph = (GraphView) rootView.findViewById(R.id.gvStatistici);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
        ArrayList<String> arrayValoriPeX = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = new Date();

        int[] listaCantitatiPerOre=new int[24];

        for (int i = 0; i < 24; i++) {
            if (i < 10)
                arrayValoriPeX.add("0" + String.valueOf(i) + ":00");
            else
                arrayValoriPeX.add(String.valueOf(i) + ":00");
            for (IstoricReceiver istoricReceiver : Utile.listaIstoricReceiver) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(getDateFromString(istoricReceiver));
                cal2.setTime(date);
                boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
                if (sameDay&&i==cal1.get(Calendar.HOUR_OF_DAY)) {
                    listaCantitatiPerOre[cal1.get(Calendar.HOUR_OF_DAY)]+=listaCantitatiPerOre[cal1.get(Calendar.HOUR_OF_DAY)]+istoricReceiver.getCantitatePrimitaML();
                }
            }
            series.appendData(new DataPoint(i, listaCantitatiPerOre[i]), false, 30);
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


        series.setSpacing(10);
        graph.getGridLabelRenderer().setLabelsSpace(20);
        graph.setTitle(dateFormat.format(date));
        graph.setTitleTextSize(60);
        graph.addSeries(series);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getContext(),String.valueOf(dataPoint.getY()+" ml la ora "+(int)dataPoint.getX()+":00"),Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public Date getDateFromString(IstoricReceiver istoricReceiver) {
        Instant instant=Instant.parse(istoricReceiver.getDataPrimire());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(instant.getMillis());
        return calendar.getTime();
    }

}
