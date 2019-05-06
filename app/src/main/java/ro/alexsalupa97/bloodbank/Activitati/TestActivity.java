package ro.alexsalupa97.bloodbank.Activitati;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import ro.alexsalupa97.bloodbank.Adaptoare.AdaptorExpandableLV;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.ViewCustom.BulletTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    TextView tvBullet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvBullet=(TextView)findViewById(R.id.tvBullet);
        CharSequence bulletedList = BulletTextView.makeBulletList(100,new String[]{"First line", "Second line", "Really long third line that will wrap and indent properly."});
        tvBullet.setText(bulletedList);
    }
}
