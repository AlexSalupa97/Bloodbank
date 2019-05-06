package ro.alexsalupa97.bloodbank.Activitati;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.Programari;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class ProgramareActivity extends AppCompatActivity {

    private static final int INTERVAL = 15;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    DatePicker dpProgramare;
    private TimePicker tpProgramare;
    private NumberPicker minutePicker;

    Button btnVerificareDisponibilitate;
    Button btnEfectuareProgramare;
    Button tvVerificareDisponibilitate;

    CTS ctsCurent;

    String data;
    String dataSelectata;
    String oraSelectata;
    String luna;
    String zi;
    String ora;
    String minut;

    static Gson gson;

    int idProgramare;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programare);

        getSupportActionBar().setElevation(0);

        tvVerificareDisponibilitate = (Button) findViewById(R.id.tvVerificareDisponibilitate);

        ctsCurent = getIntent().getExtras().getParcelable("cts");

        dpProgramare = (DatePicker) findViewById(R.id.dpProgramare);
        Calendar calendar = Calendar.getInstance();
        dpProgramare.setMinDate(calendar.getTimeInMillis());


        calendar.setTimeInMillis(System.currentTimeMillis());
        if (calendar.get(Calendar.MONTH) < 10)
            luna = "0" + calendar.get(Calendar.MONTH);
        else
            luna = String.valueOf(calendar.get(Calendar.MONTH));

        if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
            zi = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        else
            zi = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));


        dataSelectata = zi + luna + calendar.get(Calendar.YEAR);
//        Toast.makeText(getApplicationContext(),dataSelectata,Toast.LENGTH_SHORT).show();

        dpProgramare.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (monthOfYear < 10)
                    luna = "0" + (monthOfYear + 1);
                else
                    luna = String.valueOf(monthOfYear + 1);

                if (dayOfMonth < 10)
                    zi = "0" + dayOfMonth;
                else
                    zi = String.valueOf(dayOfMonth);

                dataSelectata = zi + luna + year;
                data = oraSelectata + dataSelectata;
                tvVerificareDisponibilitate.setVisibility(View.GONE);
                btnVerificareDisponibilitate.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), dataSelectata, Toast.LENGTH_SHORT).show();
            }
        });

        tpProgramare = (TimePicker) findViewById(R.id.tpProgramare);

        tpProgramare.setIs24HourView(true);
        tpProgramare.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));


        setMinutePicker();


        if (calendar.get(Calendar.HOUR_OF_DAY) < 10)
            ora = "0" + calendar.get(Calendar.HOUR_OF_DAY);
        else
            ora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

        if (calendar.get(Calendar.MINUTE) < 10)
            minut = "0" + getMinute();
        else
            minut = String.valueOf(getMinute());

        oraSelectata = ora + minut;
//        Toast.makeText(getApplicationContext(),oraSelectata,Toast.LENGTH_SHORT).show();


        tpProgramare.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 10)
                    ora = "0" + hourOfDay;
                else
                    ora = String.valueOf(hourOfDay);

                if (getMinute() < 10)
                    minut = "0" + getMinute();
                else
                    minut = String.valueOf(getMinute());
                oraSelectata = ora + minut;
                data = oraSelectata + dataSelectata;
                tvVerificareDisponibilitate.setVisibility(View.GONE);
                btnVerificareDisponibilitate.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), oraSelectata, Toast.LENGTH_SHORT).show();
            }
        });

        data = oraSelectata + dataSelectata;


        btnVerificareDisponibilitate = (Button) findViewById(R.id.btnVerificareDisponibilitate);
        btnVerificareDisponibilitate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Utile.URL + "domain.programari/cts/" + ctsCurent.getIdCTS() + "/programare/" + data;

                final RequestQueue requestQueue = Volley.newRequestQueue(ProgramareActivity.this);


                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                gson = gsonBuilder.create();
                                if (response.length() == 0) {
                                    btnVerificareDisponibilitate.setVisibility(View.GONE);
                                    tvVerificareDisponibilitate.setVisibility(View.VISIBLE);
                                    tvVerificareDisponibilitate.setBackgroundColor(Color.parseColor("#32CD32"));
                                    tvVerificareDisponibilitate.setText("Disponibil");
                                } else {
                                    btnVerificareDisponibilitate.setVisibility(View.GONE);
                                    tvVerificareDisponibilitate.setVisibility(View.VISIBLE);
                                    tvVerificareDisponibilitate.setBackgroundColor(Color.RED);
                                    tvVerificareDisponibilitate.setTextColor(Color.WHITE);
                                    tvVerificareDisponibilitate.setText("Indisponibil");
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("RestResponse", error.toString());
                            }
                        }

                );

                requestQueue.add(objectRequest);
            }


        });

        final Donatori donator = Utile.preluareDonator(getApplicationContext());


        btnEfectuareProgramare = (Button) findViewById(R.id.btnEfectuareProgramare);
        btnEfectuareProgramare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvVerificareDisponibilitate.getVisibility() == View.VISIBLE) {

                    String url = Utile.URL + "domain.programari/count";
                    final RequestQueue requestQueue = Volley.newRequestQueue(ProgramareActivity.this);

                    StringRequest objectRequest = new StringRequest(
                            Request.Method.GET,
                            url,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(final String response) {
                                    idProgramare = Integer.parseInt(response);


                                    String url = Utile.URL + "domain.programari/";

                                    final JSONObject jsonProgramare = new JSONObject();
                                    final JSONObject jsonDonator = new JSONObject();
                                    final JSONObject jsonCTS = new JSONObject();
                                    final JSONObject jsonOrasDonator = new JSONObject();
                                    final JSONObject jsonGrupaSanguina = new JSONObject();
                                    final JSONObject jsonOras = new JSONObject();

                                    try {
                                        jsonGrupaSanguina.put("idgrupasanguina", donator.getGrupaSanguina().getGrupaSanguina());

                                        jsonOrasDonator.put("idoras", donator.getOrasDonator().getIdOras());
                                        jsonOrasDonator.put("judet", donator.getOrasDonator().getJudet());
                                        jsonOrasDonator.put("numeoras", donator.getOrasDonator().getOras());

                                        jsonDonator.put("emaildonator", donator.getEmailDonator());
                                        jsonDonator.put("iddonator", donator.getIdDonator());
                                        jsonDonator.put("idgrupasanguina", jsonGrupaSanguina);
                                        jsonDonator.put("idoras", jsonOrasDonator);
                                        jsonDonator.put("numedonator", donator.getNumeDonator());
                                        jsonDonator.put("telefondonator", donator.getTelefonDonator());

                                        jsonOras.put("idoras", ctsCurent.getOras().getIdOras());
                                        jsonOras.put("judet", ctsCurent.getOras().getJudet());
                                        jsonOras.put("numeoras", ctsCurent.getOras().getOras());

                                        jsonCTS.put("adresacts", ctsCurent.getAdresaCTS());
                                        jsonCTS.put("coordonataxcts", ctsCurent.getCoordonataYCTS());
                                        jsonCTS.put("coordonataycts", ctsCurent.getCoordonataYCTS());
                                        jsonCTS.put("emailcts", ctsCurent.getEmailCTS());
                                        jsonCTS.put("idcts", ctsCurent.getIdCTS());
                                        jsonCTS.put("idoras", jsonOras);
                                        jsonCTS.put("numects", ctsCurent.getNumeCTS());
                                        jsonCTS.put("starects", ctsCurent.getStareCTS());
                                        jsonCTS.put("telefoncts", ctsCurent.getTelefonCTS());

                                        jsonProgramare.put("dataProgramare", data);
                                        jsonProgramare.put("idcts", jsonCTS);
                                        jsonProgramare.put("iddonator", jsonDonator);
                                        jsonProgramare.put("idprogramare", idProgramare + 1);
                                    } catch (JSONException e) {

                                    }

                                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                            url, jsonProgramare,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {


                                                    Toast.makeText(getApplicationContext(), "Programare facuta cu succes", Toast.LENGTH_LONG).show();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                    if (error.toString().contains("ServerError")) {
                                                        Toast.makeText(getApplicationContext(), "Eroare de server", Toast.LENGTH_LONG).show();
                                                        Log.d("restresponse", error.toString());
                                                    }


                                                }
                                            }) {

                                        @Override
                                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {


                                            try {
                                                String json = new String(
                                                        response.data,
                                                        "UTF-8"
                                                );

                                                if (json.length() == 0) {
                                                    return Response.success(
                                                            null,
                                                            HttpHeaderParser.parseCacheHeaders(response)
                                                    );
                                                } else {
                                                    return super.parseNetworkResponse(response);
                                                }
                                            } catch (UnsupportedEncodingException e) {
                                                return Response.error(new ParseError(e));
                                            }


                                        }
                                    };
                                    requestQueue.add(jsonObjReq);

                                }

                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("RestResponse", error.toString());
                                }
                            }

                    );

                    requestQueue.add(objectRequest);


                }


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public int setMinutePicker() {
        int treceDeOra = 0;
        int numValues = 60 / INTERVAL;
        String[] displayedValues;

        displayedValues = new String[]{"00", "15", "30", "45"};

        View minute = tpProgramare.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        if ((minute != null) && (minute instanceof NumberPicker)) {
            minutePicker = (NumberPicker) minute;
            Calendar calendar=Calendar.getInstance();
            if (calendar.get(Calendar.MINUTE) >= 45 && calendar.get(Calendar.MINUTE) <= 59)
                minutePicker.setValue(0);
            else if (calendar.get(Calendar.MINUTE) >= 30)
                minutePicker.setValue(3);
            else if (calendar.get(Calendar.MINUTE) >= 15)
                minutePicker.setValue(2);
            else if (calendar.get(Calendar.MINUTE) > 0)
                minutePicker.setValue(1);
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(numValues - 1);
            minutePicker.setDisplayedValues(displayedValues);

            if(minutePicker.getValue()==0)
                tpProgramare.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY)+1);

        }

        return treceDeOra;
    }

    public int getMinute() {
        if (minutePicker != null) {
            return (minutePicker.getValue() * INTERVAL);
        } else {
            return tpProgramare.getCurrentMinute();
        }
    }
}
