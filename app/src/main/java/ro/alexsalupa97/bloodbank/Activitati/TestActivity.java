package ro.alexsalupa97.bloodbank.Activitati;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorExpandableLV;
import ro.alexsalupa97.bloodbank.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private static final int INTERVAL = 15;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    private TimePicker picker; // set in onCreate
    private NumberPicker minutePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new AdaptorExpandableLV(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);

        picker=(TimePicker)findViewById(R.id.tpProgramare);
        picker.setIs24HourView(true);

        setMinutePicker();

        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(),hourOfDay+":"+getMinute(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMinutePicker() {
        int numValues = 60 / INTERVAL;
        String[] displayedValues = new String[numValues];
        for (int i = 0; i < numValues; i++) {
            displayedValues[i] = FORMATTER.format(i * INTERVAL);
        }

        View minute = picker.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        if ((minute != null) && (minute instanceof NumberPicker)) {
            minutePicker = (NumberPicker) minute;
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(numValues - 1);
            minutePicker.setDisplayedValues(displayedValues);
        }
    }

    public int getMinute() {
        if (minutePicker != null) {
            return (minutePicker.getValue() * INTERVAL);
        } else {
            return picker.getCurrentMinute();
        }
    }
}
