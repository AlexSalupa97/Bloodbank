package ro.alexsalupa97.bloodbank.Activitati;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.shockwave.pdfium.PdfDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ro.alexsalupa97.bloodbank.Clase.Donatori;
import ro.alexsalupa97.bloodbank.Clase.IstoricDonatii;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.RealPath;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class ActualizareStareAnalizeActivity extends AppCompatActivity {

    PDFView pdfStareAnalize;
    Button btnPDF;
    Button btnActualizare;
    FloatingActionButton btnQR;

    int pagina = 0;

    String parsedText = "";
    String emailDonator = "";
    String stareAnalize = "";

    String fisier = "SharedPreferences";

    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizare_stare_analize);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            int x = 2;
            ActivityCompat.requestPermissions(ActualizareStareAnalizeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    x);
        }

        btnPDF = (Button) findViewById(R.id.btnPDF);
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnActualizare.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 30);
            }
        });

        btnActualizare = (Button) findViewById(R.id.btnActualizare);
        btnActualizare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringReader input = new StringReader(parsedText);
                BufferedReader lineReader = new BufferedReader(input);
                String line;

                while (true) {
                    try {
                        if ((line = lineReader.readLine()) == null) break;
                        else {
                            if (line.contains("EmailDonator")) {
                                int indexStart = line.lastIndexOf(":");
                                emailDonator = line.substring(indexStart + 1);
                            }
                            if (line.contains("Stare")) {
                                int indexStart = line.lastIndexOf(":");
                                stareAnalize = line.substring(indexStart + 1);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                Donatori donator = Utile.preluareDonator(getApplicationContext());
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActualizareStareAnalizeActivity.this);
                builder.setMessage("Verificati validitatea datelor preluate din fisierul PDF: \nEmail: " + emailDonator + "\nStare analize: " + stareAnalize)
                        .setCancelable(true)
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {

                                    String url = Utile.URL + "domain.stareanalize/" + Utile.preluareIDAnalize(getApplicationContext());

                                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                                    final JSONObject jsonDonator = new JSONObject();
                                    final JSONObject jsonOras = new JSONObject();
                                    final JSONObject jsonGrupaSanguina = new JSONObject();
                                    final JSONObject jsonStareAnalize=new JSONObject();

                                    try {

                                        jsonGrupaSanguina.put("idgrupasanguina", donator.getGrupaSanguina().getGrupaSanguina());

                                        jsonOras.put("idoras", donator.getOrasDonator().getIdOras());
                                        jsonOras.put("judet", donator.getOrasDonator().getJudet());
                                        jsonOras.put("numeoras", donator.getOrasDonator().getOras());

                                        jsonDonator.put("emaildonator", donator.getEmailDonator());
                                        jsonDonator.put("iddonator", donator.getIdDonator());
                                        jsonDonator.put("idgrupasanguina", jsonGrupaSanguina);
                                        jsonDonator.put("idoras", jsonOras);
                                        jsonDonator.put("numedonator", donator.getNumeDonator());
                                        jsonDonator.put("telefondonator", donator.getTelefonDonator());

                                        jsonStareAnalize.put("dataefectuareanaliza",Utile.preluareDataAnalize(getApplicationContext()));
                                        jsonStareAnalize.put("iddonator",jsonDonator);
                                        jsonStareAnalize.put("idstareanaliza",Utile.preluareIDAnalize(getApplicationContext()));
                                        jsonStareAnalize.put("stareanalize",stareAnalize);

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                    }


                                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                                            url, jsonStareAnalize,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(fisier, Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                                    editor.putString("stareAnalize", stareAnalize);

                                                    editor.commit();

                                                    createSnackBarSucces();

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                    if (error.toString().contains("ServerError")) {
                                                        Toast.makeText(getApplicationContext(), "Eroare de server", Toast.LENGTH_LONG).show();
                                                        Log.d("restresponse", error.toString());
                                                        System.out.println(error.toString());
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

                        });
                if (donator.getEmailDonator().equals(emailDonator)) {
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Fisierul selectat este necorespunzator", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }
            }


        });

        btnQR=(FloatingActionButton)findViewById(R.id.btnQR);
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    int x = 3;
                    ActivityCompat.requestPermissions(ActualizareStareAnalizeActivity.this, new String[]{Manifest.permission.CAMERA},
                            x);

                }
                else
                    startActivityForResult(new Intent(getApplicationContext(),QRActivity.class),1947);

            }
        });


        pdfStareAnalize = (PDFView) findViewById(R.id.pdfStareAnalize);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 30 && data != null) {

            try {
                parsedText = "";
                String path = RealPath.getRealPath(getApplicationContext(), data.getData());
                PdfReader reader = new PdfReader(path);
                int n = reader.getNumberOfPages();
                for (int i = 0; i < n; i++) {
                    parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n";
                }
                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }


            pdfStareAnalize.fromUri(data.getData())
                    .defaultPage(pagina)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {
                            pagina = page;
                        }
                    })
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            PdfDocument.Meta meta = pdfStareAnalize.getDocumentMeta();
                            printBookmarksTree(pdfStareAnalize.getTableOfContents(), "-");
                        }
                    })

                    .load();

            emailDonator="";
            stareAnalize="";
            btnActualizare.setVisibility(View.VISIBLE);
        }

        if(requestCode==1947&&resultCode==1){
            createSnackBarSucces();

        }

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==3&&grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(getApplicationContext(),QRActivity.class));
        }
    }

    private void createSnackBarSucces(){
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "Actualizare facuta cu succes", Snackbar.LENGTH_LONG)
                .setAction("PROFIL", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = Utile.URL + "domain.istoricdonatii/donator/" + Utile.preluareEmail(getApplicationContext());

                        final RequestQueue requestQueue = Volley.newRequestQueue(ActualizareStareAnalizeActivity.this);


                        JsonArrayRequest objectRequest = new JsonArrayRequest(
                                Request.Method.GET,
                                url,
                                null,
                                new Response.Listener<JSONArray>() {

                                    @Override
                                    public void onResponse(JSONArray response) {
                                        GsonBuilder gsonBuilder = new GsonBuilder();
                                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                                        gson = gsonBuilder.create();


                                        Utile.listaIstoricDonatii = new ArrayList<>(Arrays.asList(gson.fromJson(response.toString(),IstoricDonatii[].class)));


                                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                                        startActivity(intent);


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
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }
}
