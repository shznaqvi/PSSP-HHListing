package edu.aku.hassannaqvi.cbt_hhlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LHWActivity extends Activity {

    private static final String TAG = LHWActivity.class.getName();
    @BindView(R.id.household_identification)
    ScrollView householdIdentification;
    @BindView(R.id.txtChildListing)
    TextView txtChildListing;
    @BindView(R.id.lhwe)
    EditText lhwe;
    @BindView(R.id.lhwf)
    EditText lhwf;
    @BindView(R.id.lhwg)
    EditText lhwg;

    String dtToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhw);
        ButterKnife.bind(this);

        dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    }

    @OnClick(R.id.next)
    void onNextClick() {
        //TODO implement

        if (formValidation()) {

            SaveDraft();
//            if (UpdateDB()) {

            AppMain.ClusterActivityFlag = true;

            finish();
            startActivity(new Intent(this, setupActivity.class));
//            }

        }

    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        long updcount = db.addCluster(AppMain.clc);

        AppMain.clc.setID(updcount);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.clc.setUID(AppMain.clc.getDeviceId() + AppMain.clc.getID());

            db.updateClc();


        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft() {
        AppMain.clc = new ClusterContract();

        AppMain.clc.setClDT(dtToday);
        AppMain.clc.setUserName(AppMain.userName);
        AppMain.clc.setLhwPh(lhwe.getText().toString());
        AppMain.clc.setNoHH(lhwf.getText().toString());
        AppMain.clc.setNoBISP(lhwg.getText().toString());
        AppMain.clc.setDeviceId(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        AppMain.clc.setLhwCode(AppMain.hh02txt);

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure ");
    }

    public boolean formValidation() {

        if (lhwe.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwe.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwe: Data req");
            return false;
        } else {
            lhwe.setError(null);
        }

        if (lhwf.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwf.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwf: Data req");
            return false;
        } else {
            lhwf.setError(null);

            if (Integer.parseInt(lhwf.getText().toString()) < 1) {
                Toast.makeText(this, "Invalid(Error):Greater then 0", Toast.LENGTH_LONG).show();
                lhwf.setError("Invalid(Error):Greater then 0");
                Log.i(TAG, "lhwf: Greater then 0");
                return false;
            } else {
                lhwf.setError(null);
            }
        }

        if (lhwg.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwg.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwg: Data req");
            return false;
        } else {
            lhwg.setError(null);

            if (Integer.parseInt(lhwg.getText().toString()) < 1 || Integer.parseInt(lhwg.getText().toString()) > Integer.parseInt(lhwf.getText().toString())) {
                Toast.makeText(this, "Error(Invalid)", Toast.LENGTH_LONG).show();
                lhwg.setError("Error(Invalid)");
                Log.i(TAG, "lhwg: Error(Invalid)");
                return false;
            } else {
                lhwg.setError(null);
            }
        }
        return true;
    }

}
