package ro.alexsalupa97.bloodbank.Fragmente;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import ro.alexsalupa97.bloodbank.R;

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
        for (int i = 0; i < 24; i++) {
            if (i < 10)
                arrayValoriPeX.add("0" + String.valueOf(i) + ":00");
            else
                arrayValoriPeX.add(String.valueOf(i) + ":00");
            series.appendData(new DataPoint(i, 10000 * Math.random()), false, 30);
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = new Date();

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

        return rootView;
    }

}
