package edu.aku.hassannaqvi.cbt_hhlisting.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import edu.aku.hassannaqvi.cbt_hhlisting.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 3000);
    }
}
