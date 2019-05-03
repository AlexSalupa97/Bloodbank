package ro.alexsalupa97.bloodbank.Activitati;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ro.alexsalupa97.bloodbank.R;

public class AlegereLoginActivity extends AppCompatActivity {

    Button btnContNou;
    Button btnContExistent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alegere_login);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        btnContExistent=(Button)findViewById(R.id.btnContExistent);
        btnContNou=(Button)findViewById(R.id.btnContNou);

        btnContNou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnContExistent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(intent);
                finish();
//                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==100)
        {
            finish();
        }
    }

}
