package edu.aku.hassannaqvi.willows_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.AreasContract;
import edu.aku.hassannaqvi.willows_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.willows_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.willows_hhlisting.R;

public class ClusterListingActivity extends Activity {

    private static final String TAG = ClusterListingActivity.class.getName();
    @BindView(R.id.activity_household_listing)
    ScrollView activityHouseholdListing;

    @BindView(R.id.as01)
    EditText as01;
    @BindView(R.id.as02)
    EditText as02;
    @BindView(R.id.as03)
    EditText as03;
    @BindView(R.id.ae01)
    EditText ae01;
    @BindView(R.id.ae02)
    EditText ae02;
    @BindView(R.id.ae03)
    EditText ae03;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cluster_listing);
        ButterKnife.bind(this);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    @OnClick(R.id.btnAddHH)
    void onBtnAddHHClick() {
        //TODO implement

        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Intent fA = new Intent(this, setupActivity.class);
                startActivity(fA);
            }
        }
    }

    private void SaveDraft() throws JSONException {
        SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        AppMain.ac = new AreasContract();
        AppMain.ac.setTagid(sharedPref.getString("tagName", null));
        AppMain.ac.setFormdate(dtToday);
        AppMain.ac.setDeviceid(deviceId);
        AppMain.ac.setUsername(AppMain.userEmail);
        AppMain.ac.setDistrict_code(AppMain.hh01txt);
        AppMain.ac.setPsu_code(AppMain.hh02txt);
        AppMain.ac.setAppversion(AppMain.versionName + "." + AppMain.versionCode);

        JSONObject sC = new JSONObject();

        sC.put("as01", as01.getText().toString());
        sC.put("as02", as02.getText().toString());
        sC.put("as03", as03.getText().toString());
        sC.put("ae01", ae01.getText().toString());
        sC.put("ae02", ae02.getText().toString());
        sC.put("ae03", ae03.getText().toString());

        AppMain.ac.setAreaname(String.valueOf(sC));
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addAreas(AppMain.ac);

        AppMain.ac.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.ac.setUid(
                    (AppMain.ac.getDeviceid() + AppMain.ac.getID()));

            db.updateAreaUID();

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean formValidation() {

        if (as01.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Neighbourhood", Toast.LENGTH_LONG).show();
            as01.setError("ERROR(Empty) data required");
            Log.i(TAG, "as01: data required");
            return false;
        } else {
            as01.setError(null);
        }

        if (as02.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Street Name and Number", Toast.LENGTH_LONG).show();
            as02.setError("ERROR(Empty) data required");
            Log.i(TAG, "as02: data required");
            return false;
        } else {
            as02.setError(null);
        }

        if (as03.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Landmark", Toast.LENGTH_LONG).show();
            as03.setError("ERROR(Empty) data required");
            Log.i(TAG, "as03: data required");
            return false;
        } else {
            as03.setError(null);
        }

        if (ae01.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Neighbourhood", Toast.LENGTH_LONG).show();
            ae01.setError("ERROR(Empty) data required");
            Log.i(TAG, "ae01: data required");
            return false;
        } else {
            ae01.setError(null);
        }

        if (ae02.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Street Name and Number", Toast.LENGTH_LONG).show();
            ae02.setError("ERROR(Empty) data required");
            Log.i(TAG, "ae02: data required");
            return false;
        } else {
            ae02.setError(null);
        }

        if (ae03.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(Empty) Landmark", Toast.LENGTH_LONG).show();
            ae03.setError("ERROR(Empty) data required");
            Log.i(TAG, "ae03: data required");
            return false;
        } else {
            ae03.setError(null);
        }

        return true;
    }

}
