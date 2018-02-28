package edu.aku.hassannaqvi.kmc_hhlisting.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.kmc_hhlisting.R;
import edu.aku.hassannaqvi.kmc_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.kmc_hhlisting.core.FormsDBHelper;

public class FamilyListingActivity extends Activity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtFamilyListing)
    TextView txtFamilyListing;
    @BindView(R.id.hh08)
    EditText hh08;
    @BindView(R.id.hh09)
    EditText hh09;
    @BindView(R.id.hh09a)
    Switch hh09a;
    @BindView(R.id.hh09b)
    EditText hh09b;
    @BindView(R.id.btnContNextQ)
    Button btnContNextQ;
    @BindView(R.id.btnAddMWRA)
    Button btnAddMWRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_listing);
        ButterKnife.bind(this);


        txtFamilyListing.setText("Family Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt);

        /*if (AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }
*/
        hh09a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh09b.setVisibility(View.VISIBLE);
                    hh09b.requestFocus();
                } else {
                    hh09b.setVisibility(View.INVISIBLE);
                    hh09b.setText(null);
                }
            }
        });

        hh09b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Integer.valueOf(s.toString()) > 0) {
                    Toast.makeText(FamilyListingActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    btnAddMWRA.setVisibility(View.VISIBLE);
                    btnContNextQ.setVisibility(View.GONE);
                } else {
                    btnAddMWRA.setVisibility(View.GONE);
                    btnContNextQ.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btnContNextQ)
    void onBtnContNextQClick() {
        if (formValidation()) {
            SaveDraft();
            if (UpdateDB()) {
                /*AppMain.fCount = 0;
                AppMain.fTotal = 0;
                AppMain.cCount = 0;
                AppMain.cTotal = 0;
                AppMain.mwraCount = 0;
                AppMain.mwraTotal = 0;*/
                Intent closeA = new Intent(this, HouseholdInfoActivity.class);
                startActivity(closeA);
            } else {
                Toast.makeText(this, "Saving Draft... Failed!", Toast.LENGTH_LONG).show();
            }
        }
    }


    @OnClick(R.id.btnAddMWRA)
    void onBtnAddMWRAClick() {

        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.mwraTotal = Integer.parseInt(hh09b.getText().toString());
                AppMain.mwraCount = 1;
                Toast.makeText(this, AppMain.mwraCount + ":" + AppMain.mwraTotal + ":" + AppMain.fCount + ":" + AppMain.fTotal, Toast.LENGTH_SHORT).show();
                Intent mwraA = new Intent(this, AddMarriedWomenActivity.class);
                startActivity(mwraA);
            } else {
                Toast.makeText(this, "Saving Draft... Failed!", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void SaveDraft() {

        AppMain.lc.setHh08(hh08.getText().toString());
        AppMain.lc.setHh09(hh09.getText().toString());
        AppMain.lc.setHh09a(hh09a.isChecked() ? "1" : "2");
        AppMain.lc.setHh09b(hh09b.getText().toString().isEmpty() ? "0" : hh09b.getText().toString());

        setGPS();


        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());

    }

    private boolean formValidation() {

        if (hh08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            hh08.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            hh08.setError(null);
        }

        if (hh09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_LONG).show();
            hh09.setError("Please enter contact number");
            Log.i(TAG, "Please enter contact number");
            return false;
        } else {
            hh09.setError(null);
        }

        if (hh09a.isChecked() && hh09b.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter child count", Toast.LENGTH_LONG).show();
            hh09b.setError("Please enter child count");
            Log.i(TAG, "Please enter child count");
            return false;
        } else {
            hh09b.setError(null);
        }

        if (!hh09b.getText().toString().isEmpty() && Integer.valueOf(hh09b.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_LONG).show();
            hh09.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh09b.setError(null);
        }

        return true;
    }


    /*@OnClick(R.id.btnAddFamily)
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
    }*/

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        Long rowId;

        rowId = db.addForm(AppMain.lc);

        AppMain.lc.setID(String.valueOf(rowId));


        if (rowId != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.lc.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.lc.getID()));
            Toast.makeText(this, "Current Form No: " + AppMain.lc.getUID(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

        // CONVERTING GPS TIMESTAMP TO DATETIME FORMAT
        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        AppMain.lc.setGPSLat(GPSPref.getString("Latitude", "0"));
        AppMain.lc.setGPSLng(GPSPref.getString("Longitude", "0"));
        AppMain.lc.setGPSAcc(GPSPref.getString("Accuracy", "0"));
        AppMain.lc.setGPSTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above

        Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
    }
    
    
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }


}
