package ro.alexsalupa97.bloodbank.Activitati;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.List;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.RealPath;

public class ActualizareStareAnalizeActivity extends AppCompatActivity {

    PDFView pdfStareAnalize;
    Button btnPDF;
    Button btnActualizare;

    int pagina=0;

    String parsedText="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizare_stare_analize);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            int x = 2;
            ActivityCompat.requestPermissions(ActualizareStareAnalizeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    x);
        }

        btnPDF=(Button)findViewById(R.id.btnPDF);
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnActualizare.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, 30);
            }
        });

        btnActualizare=(Button)findViewById(R.id.btnActualizare);


        pdfStareAnalize=(PDFView)findViewById(R.id.pdfStareAnalize);
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
        if(requestCode==30&&data!=null){

            try {
                parsedText="";
                String path= RealPath.getRealPath(getApplicationContext(),data.getData());
                PdfReader reader = new PdfReader(path);
                int n = reader.getNumberOfPages();
                for (int i = 0; i <n ; i++) {
                    parsedText   = parsedText + PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n";
                }
                System.out.println(parsedText);
                reader.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            Toast.makeText(getApplicationContext(),parsedText,Toast.LENGTH_SHORT).show();



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
            btnActualizare.setVisibility(View.VISIBLE);
        }

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


}
