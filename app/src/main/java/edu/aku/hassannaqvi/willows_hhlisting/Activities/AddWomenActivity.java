package edu.aku.hassannaqvi.willows_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.MwraContract;
import edu.aku.hassannaqvi.willows_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.willows_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.willows_hhlisting.R;

public class AddWomenActivity extends Activity {

    public static String TAG = AddWomenActivity.class.getName();


    @BindView(R.id.activity_add_women)
    ScrollView activityAddWomen;
    @BindView(R.id.txtWomenListing)
    TextView txtWomenListing;
    @BindView(R.id.icName)
    EditText icName;
    @BindView(R.id.icAgeY)
    EditText icAgeY;
/*    @BindView(R.id.icAgeD)
    EditText icAgeD;*/
    @BindView(R.id.btnAddWomen)
    Button btnAddWomen;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_women);
        ButterKnife.bind(this);
        txtWomenListing.setText("MWRA (16-44) Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " (" + AppMain.cCount + " of " + AppMain.cTotal + ")");
        if (AppMain.cCount < AppMain.cTotal) {
            btnAddWomen.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddFamilty.setVisibility(View.GONE);
        } else if (AppMain.cCount >= AppMain.cTotal && AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddWomen.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
            btnAddWomen.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btnAddWomen)
    void onBtnAddWomenClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.cCount++;
                Intent fA = new Intent(this, AddWomenActivity.class);
                startActivity(fA);
            }

        }
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);


        long updcount = db.addMwra(AppMain.mc);

        AppMain.mc.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.mc.setUID(
                    (AppMain.mc.getDeviceid() + AppMain.mc.getID()));

            db.updateMWRAUID();
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft() {
        SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);

        AppMain.mc = new MwraContract();
        AppMain.mc.setTagID(sharedPref.getString("tagName", null));
        AppMain.mc.setMwDT(AppMain.lc.getHhDT());
        AppMain.mc.setUUID(AppMain.lc.getUID());
        AppMain.mc.setDeviceid(AppMain.lc.getDeviceID());
        AppMain.mc.setUser(AppMain.userEmail);
        AppMain.mc.setMwDistrictCode(AppMain.lc.getHh01());
        AppMain.mc.setMwPSUNo(AppMain.lc.getHh02());
        AppMain.mc.setAppversion(AppMain.versionName + "." + AppMain.versionCode);
        AppMain.mc.setStructureNo(AppMain.hh03txt + "-" + AppMain.hh07txt);
        AppMain.mc.setMwraID(String.valueOf(AppMain.cCount));

        AppMain.mc.setName(icName.getText().toString());
        AppMain.mc.setAgey(icAgeY.getText().toString());
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());
    }

    private boolean formValidation() {

        if (icName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            icName.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            icName.setError(null);
        }

        if (icAgeY.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter Age year", Toast.LENGTH_LONG).show();
            icAgeY.setError("Please enter age");
            Log.i(TAG, "Please enter age");
            return false;
        }

        if (Integer.valueOf(icAgeY.getText().toString()) < 16 || Integer.valueOf(icAgeY.getText().toString()) > 44) {
            Toast.makeText(this, "Age in year Range 16 - 44 ", Toast.LENGTH_LONG).show();
            icAgeY.setError("Range 16 - 44");
            Log.i(TAG, "Age in year Range 16 - 44");
            return false;
        } else {
            icAgeY.setError(null);
        }
        return true;
    }


    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.cCount = 0;
                AppMain.cTotal = 0;
                AppMain.hh07txt = String.valueOf((char) (AppMain.hh07txt.charAt(0) + 1));
                AppMain.lc.setHh07(AppMain.hh07txt.toString());
                AppMain.fCount++;

                Intent fA = new Intent(this, FamilyListingActivity.class);
                startActivity(fA);
                try {
                    Log.d(TAG, "onBtnAddFamilyClick: " + AppMain.lc.toJSONObject().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @OnClick(R.id.btnAddHousehold)
    void onBtnAddHouseholdClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.fCount = 0;
                AppMain.fTotal = 0;
                AppMain.cCount = 0;
                AppMain.cTotal = 0;
                Intent fA = new Intent(this, setupActivity.class);
                startActivity(fA);

            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}
