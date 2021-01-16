package edu.aku.hassannaqvi.naunehal_listing.ui;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.naunehal_listing.R;
import edu.aku.hassannaqvi.naunehal_listing.core.MainApp;
import edu.aku.hassannaqvi.naunehal_listing.databinding.ActivitySetupBinding;
import edu.aku.hassannaqvi.naunehal_listing.models.Listings;

public class SetupActivity extends AppCompatActivity {

    private static String deviceId;
    private final String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault()).format(new Date().getTime());
    private final String TAG = "Setup Activity";

    ActivitySetupBinding bi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_setup);

        MainApp.listing = new Listings();
        MainApp.listing.setHl01(MainApp.districtsCode);
        MainApp.listing.setHl02(MainApp.ucCode);
        MainApp.listing.setHl03(MainApp.clusterCode);
        MainApp.listing.setHl06(String.valueOf(++MainApp.structureNo));
        bi.setListing(MainApp.listing);
        setSupportActionBar(bi.toolbar);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);


    }


    public void addFamily(View view) {
    }
}


