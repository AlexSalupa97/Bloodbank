package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import ro.alexsalupa97.bloodbank.R;
import ro.alexsalupa97.bloodbank.Utile.Utile;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.List;

public class AnalizeNotOkActivity extends AppCompatActivity {

    ShareButton fbShareBtn;
    LinearLayout twitterShareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analize_not_ok);
        fbShareBtn = (ShareButton) findViewById(R.id.fbShareBtn);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote("mesaj generic facebook de la "+ Utile.preluareUsername(getApplicationContext())+" din Bloodbank")
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/developer?id=AlexSalupa97"))
                .build();

        fbShareBtn.setShareContent(content);


        twitterShareBtn = (LinearLayout) findViewById(R.id.twitterShareBtn);
        twitterShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = getShareIntent("twitter", "subject", "mesaj generic twitter de la "+
                        Utile.preluareUsername(getApplicationContext())+" din Bloodbank" +
                        "\nhttps://play.google.com/store/apps/developer?id=AlexSalupa97");
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
