package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.List;

import ro.alexsalupa97.bloodbank.Clase.CTS;
import ro.alexsalupa97.bloodbank.Clase.GrupeSanguine;
import ro.alexsalupa97.bloodbank.Clase.Receiveri;
import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

public class DetaliiReceiverActivity extends AppCompatActivity {

    ShareButton fbShareBtn;
    LinearLayout twitterShareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_receiver);

        Receiveri receiver;
        receiver=getIntent().getParcelableExtra("receiver");

        if(receiver==null) {
            receiver=new Receiveri();
            receiver.setNumeReceiver(Utile.preluareUsername(getApplicationContext()));
            receiver.setTelefonReceiver(Utile.preluareTelefon(getApplicationContext()));
            receiver.setEmailReceiver(Utile.preluareEmail(getApplicationContext()));
            for(CTS cts:Utile.CTS)
                if(cts.getNumeCTS().equals((Utile.preluareCTS(getApplicationContext()))))
                    receiver.setCts(cts);

            for(GrupeSanguine grupeSanguine:Utile.listaGrupeSanguine)
                if(grupeSanguine.getGrupaSanguina().equals((Utile.preluareGrupaSanguina(getApplicationContext()))))
                    receiver.setGrupaSanguina(grupeSanguine);

        }

        final Receiveri receiverFinal=receiver;

        fbShareBtn = (ShareButton) findViewById(R.id.fbShareBtn);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote("Doneaza pentru "+receiverFinal.getNumeReceiver())
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/developer?id=AlexSalupa97"))
                .build();

        fbShareBtn.setShareContent(content);


        twitterShareBtn = (LinearLayout) findViewById(R.id.twitterShareBtn);
        twitterShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = getShareIntent("twitter", "subject", "Doneaza pentru "+receiverFinal.getNumeReceiver() + "\nhttps://play.google.com/store/apps/developer?id=AlexSalupa97");
                if (twitterIntent != null)
                    startActivity(twitterIntent);

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

    private Intent getShareIntent(String type, String subject, String text) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getApplicationContext().getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, subject);
                    share.putExtra(Intent.EXTRA_TEXT, text);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
    }
}