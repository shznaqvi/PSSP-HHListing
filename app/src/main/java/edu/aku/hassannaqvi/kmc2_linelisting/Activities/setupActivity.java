package edu.aku.hassannaqvi.kmc2_linelisting.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.kmc2_linelisting.R;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.ListingContract;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

public class setupActivity extends Activity {

    private static String deviceId;
    @BindView(R.id.activity_household_listing)
    ScrollView activityHouseholdListing;
    /*@BindView(R.id.hh01)
    TextView hh01;*/
    @BindView(R.id.hh02)
    EditText hh02;
    @BindView(R.id.hh03)
    TextView hh03;
    @BindView(R.id.hh04)
    RadioGroup hh04;
    @BindView(R.id.hh04a)
    RadioButton hh04a;
    @BindView(R.id.hh04b)
    RadioButton hh04b;
    /*@BindView(R.id.hh04c)
    RadioButton hh04c;
    @BindView(R.id.hh04d)
    RadioButton hh04d;
    @BindView(R.id.hh04e)
    RadioButton hh04e;
    @BindView(R.id.hh04f)
    RadioButton hh04f;*/
    @BindView(R.id.hh04g)
    RadioButton hh04g;
    /*@BindView(R.id.hh04x)
    RadioButton hh04x;
    @BindView(R.id.hh04x88)
    EditText hh04x88;*/
    @BindView(R.id.hh05)
    RadioGroup hh05;
    @BindView(R.id.hh05a)
    RadioButton hh05a;
    @BindView(R.id.hh05b)
    RadioButton hh05b;
    @BindView(R.id.hh06)
    EditText hh06;
    @BindView(R.id.hh07)
    TextView hh07;
    @BindView(R.id.fldGrpHH04)
    LinearLayout fldGrpHH04;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;
    @BindView(R.id.btnChangePSU)
    Button btnChangPSU;
    @BindView(R.id.villageName)
    TextView villageName;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    private String TAG = "Setup Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);

        villageName.setText("Sub Village: " + MainApp.villageName);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (MainApp.villageCode == null) {
            MainApp.hh03txt = 1;
        } else {
            MainApp.hh03txt++;
            //MainApp.lc.setHh03(String.valueOf(MainApp.hh03txt));
            hh02.setText(MainApp.villageCode);
            hh02.setEnabled(false);

        }
        MainApp.hh07txt = "X";
        //hh01.setText(getString(R.string.hh01) + ": " + MainApp.hh01txt);
        hh03.setText(hh02.getText().toString()
                + "-" + String.valueOf(MainApp.hh03txt));
        hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);


        hh04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (hh04a.isChecked()) {
                    //Moved to Add next Family button: MainApp.hh07txt = String.valueOf((char) MainApp.hh07txt.charAt(0) + 1);
                    MainApp.hh07txt = "X";


                } else if (!hh04a.isChecked()) {
                    MainApp.hh07txt = null;
                }

                hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);
                if (hh04a.isChecked()) {
                    fldGrpHH04.setVisibility(View.VISIBLE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    fldGrpHH04.setVisibility(View.GONE);
//                    hh05.setChecked(false);
                    hh05.clearCheck();
                    hh06.setText(null);
                    btnAddHousehold.setVisibility(View.VISIBLE);
                }
                if (hh04g.isChecked()) {
                    btnAddHousehold.setVisibility(View.GONE);
                    btnChangPSU.setVisibility(View.VISIBLE);
                } else {
                    btnChangPSU.setVisibility(View.GONE);

                }
                /*if (hh04x.isChecked()) {
                    hh04x88.setVisibility(View.VISIBLE);
                } else {
                    hh04x88.setVisibility(View.GONE);
                    hh04x88.setText(null);
                }*/
            }
        });
        /*hh05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainApp.hh07txt = "A";
                    hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);
                    hh06.setVisibility(View.VISIBLE);
                    hh06.requestFocus();

                } else {
                    MainApp.hh07txt = "X";
                    hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);
                    hh06.setVisibility(View.INVISIBLE);
                    hh06.setText(null);
                }
            }
        });*/

        hh05.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.hh05a) {
                    MainApp.hh07txt = "A";
                    hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);
                    hh06.setVisibility(View.VISIBLE);
                    hh06.requestFocus();

                } else {
                    MainApp.hh07txt = "X";
                    hh07.setText(getString(R.string.hh07) + ": " + MainApp.hh07txt);
                    hh06.setVisibility(View.GONE);
                    hh06.setText(null);
                }
            }
        });


    }

    @OnClick(R.id.btnAddPregnancy)
    void onBtnAddChildClick() {

        if (MainApp.hh02txt == null) {
            MainApp.hh02txt = hh02.getText().toString();
        }
        if (formValidation()) {
            SaveDraft();

            MainApp.fCount++;

            finish();

            Intent fA = new Intent(this, FamilyListingActivity.class);
            startActivity(fA);
        }

    }

    @OnClick(R.id.btnChangePSU)
    void onBtnChangePSUClick() {

        MainApp.villageCode = null;

        finish();

        Intent fA = new Intent(this, MainActivity.class);
        startActivity(fA);

    }


    private void SaveDraft() {

        MainApp.lc = new ListingContract();
        SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        MainApp.lc.setTagId(sharedPref.getString("tagName", null));
        MainApp.lc.setHhDT(dtToday);
        MainApp.lc.setHh01(String.valueOf(MainApp.ucCode));    //UC Code
        MainApp.lc.setHh02(MainApp.villageCode);    //Village Code
        MainApp.lc.setHh03(String.valueOf(MainApp.hh03txt));
        switch (hh04.getCheckedRadioButtonId()) {
            case R.id.hh04a:
                MainApp.lc.setHh04("1");
                break;
            case R.id.hh04b:
                MainApp.lc.setHh04("2");
                break;
            /*case R.id.hh04c:
                MainApp.lc.setHh04("3");
                break;
            case R.id.hh04d:
                MainApp.lc.setHh04("4");
                break;
            case R.id.hh04e:
                MainApp.lc.setHh04("5");
                break;
            case R.id.hh04f:
                MainApp.lc.setHh04("6");
                break;*/
            case R.id.hh04g:
                MainApp.lc.setHh04("7");
                break;
           /* case R.id.hh04x:
                MainApp.lc.setHh04("88");
                break;
            default:
                MainApp.lc.setHh04("xx");
                break;*/
        }
        MainApp.lc.setUsername(MainApp.userEmail);
        MainApp.lc.setHh05(hh05a.isChecked() ? "1" : hh05b.isChecked() ? "2" : "0");
        MainApp.lc.setHh06(hh06.getText().toString());
        MainApp.lc.setHh07(MainApp.hh07txt);
        MainApp.lc.setDeviceID(deviceId);
        MainApp.lc.setApp_ver(MainApp.versionName + "." + MainApp.versionCode);

        setGPS();

        MainApp.fTotal = hh06.getText().toString().isEmpty() ? 0 : Integer.parseInt(hh06.getText().toString());

        Log.d(TAG, "SaveDraft: " + MainApp.lc.getHh03().toString());

    }

    public void setGPS() {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

//        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String dt = GPSPref.getString("Time", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            MainApp.lc.setGPSLat(GPSPref.getString("Latitude", "0"));
            MainApp.lc.setGPSLng(GPSPref.getString("Longitude", "0"));
            MainApp.lc.setGPSAcc(GPSPref.getString("Accuracy", "0"));
//            MainApp.fc.setGpsTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above
            MainApp.lc.setGPSTime(date); // Timestamp is converted to date above

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }


    private boolean formValidation() {
        if (MainApp.villageCode == null) {
            Toast.makeText(this, "Please enter PSU", Toast.LENGTH_LONG).show();
            hh02.setError("Please enter PSU");
            Log.i(TAG, "PSU not given");
            return false;
        } else {
            hh02.setError(null);
        }
        if (hh04.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select one option", Toast.LENGTH_LONG).show();
            hh04g.setError("Please select one option");
            Log.i(TAG, "Please select one option");
            return false;
        } else {
            hh04g.setError(null);
        }

        /*if (hh04x.isChecked() && hh04x88.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter others", Toast.LENGTH_LONG).show();
            hh04x88.setError("Please enter others");
            Log.i(TAG, "Please enter others");
            return false;
        } else {
            hh04x88.setError(null);
        }*/

        if (hh04a.isChecked()) {
            if (hh05.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select one option", Toast.LENGTH_LONG).show();
                hh05b.setError("Please select one option");
                Log.i(TAG, "Please select one option");
                return false;
            } else {
                hh05b.setError(null);
            }

            if (hh05a.isChecked() && hh06.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter number", Toast.LENGTH_LONG).show();
                hh06.setError("Please enter number");
                Log.i(TAG, "Please enter number");
                return false;
            } else {
                hh06.setError(null);
            }

            if (!hh06.getText().toString().isEmpty() && Integer.valueOf(hh06.getText().toString()) <= 1) {
                Toast.makeText(this, "Greater then 1!", Toast.LENGTH_LONG).show();
                hh06.setError("Greater then 1!");
                Log.i(TAG, "hh06:Greater then 1!");
                return false;
            } else {
                hh06.setError(null);
            }
        }

        return true;
    }

    @OnClick(R.id.btnAddHousehold)
    void onBtnAddHouseholdClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                MainApp.fCount = 0;
                MainApp.fTotal = 0;
                MainApp.cCount = 0;
                MainApp.cTotal = 0;

                finish();

                Intent fA = new Intent(this, setupActivity.class);
                startActivity(fA);

            }
        }
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        Log.d(TAG, "UpdateDB: Structure" + MainApp.lc.getHh03().toString());

        long updcount = db.addForm(MainApp.lc);

        MainApp.lc.setID(String.valueOf(updcount));

        if (updcount != 0) {

            MainApp.lc.setUID(
                    (MainApp.lc.getDeviceID() + MainApp.lc.getID()));

            db.updateListingUID();

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}


