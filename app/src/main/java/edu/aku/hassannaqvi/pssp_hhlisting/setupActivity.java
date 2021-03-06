package edu.aku.hassannaqvi.pssp_hhlisting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class setupActivity extends Activity {

    private static String deviceId;
    @BindView(R.id.activity_household_listing)
    ScrollView activityHouseholdListing;
    @BindView(R.id.hh01)
    TextView hh01;
    @BindView(R.id.hh02)
    EditText hh02;
    @BindView(R.id.hhadd)
    EditText hhadd;
    @BindView(R.id.hh03)
    TextView hh03;
    @BindView(R.id.hh04)
    RadioGroup hh04;
    @BindView(R.id.hh04a)
    RadioButton hh04a;
    @BindView(R.id.hh04b)
    RadioButton hh04b;
    @BindView(R.id.hh04c)
    RadioButton hh04c;
    @BindView(R.id.hh04d)
    RadioButton hh04d;
    @BindView(R.id.hh04e)
    RadioButton hh04e;
    @BindView(R.id.hh04f)
    RadioButton hh04f;
    @BindView(R.id.hh04g)
    RadioButton hh04g;
    @BindView(R.id.hh04x)
    RadioButton hh04x;
    @BindView(R.id.hh04x88)
    EditText hh04x88;
    @BindView(R.id.hh05)
    Switch hh05;
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

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    private String TAG = "Setup Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        ButterKnife.bind(this);

        deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (AppMain.hh02txt == null) {
            AppMain.hh03txt = 1;
        } else {
            AppMain.hh03txt++;
            //AppMain.lc.setHh03(String.valueOf(AppMain.hh03txt));
            hh02.setText(AppMain.hh02txt.toString());
            hh02.setEnabled(false);

        }
        AppMain.hh07txt = "X";
        hh01.setText(getString(R.string.hh01) + ": " + AppMain.hh01txt);
        hh03.setText(String.valueOf(AppMain.hh03txt));
        hh07.setText(getString(R.string.hh07) + ": " + AppMain.hh07txt);


        hh04.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (hh04a.isChecked()) {
                    //Moved to Add next Family button: AppMain.hh07txt = String.valueOf((char) AppMain.hh07txt.charAt(0) + 1);
                    AppMain.hh07txt = "X";


                } else if (!hh04a.isChecked()) {
                    AppMain.hh07txt = null;
                }

                hh07.setText(getString(R.string.hh07) + ": " + AppMain.hh07txt);
                if (hh04a.isChecked()) {
                    fldGrpHH04.setVisibility(View.VISIBLE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    fldGrpHH04.setVisibility(View.GONE);
                    hh05.setChecked(false);
                    hh06.setText(null);
                    btnAddHousehold.setVisibility(View.VISIBLE);
                }
                if (hh04g.isChecked()) {
                    btnAddHousehold.setVisibility(View.GONE);
                    btnChangPSU.setVisibility(View.VISIBLE);
                } else {
                    btnChangPSU.setVisibility(View.GONE);

                }
                if (hh04x.isChecked()) {
                    hh04x88.setVisibility(View.VISIBLE);
                } else {
                    hh04x88.setVisibility(View.GONE);
                    hh04x88.setText(null);
                }
            }
        });
        hh05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppMain.hh07txt = "A";
                    hh07.setText(getString(R.string.hh07) + ": " + AppMain.hh07txt);
                    hh06.setVisibility(View.VISIBLE);
                    hh06.requestFocus();

                } else {
                    AppMain.hh07txt = "X";
                    hh07.setText(getString(R.string.hh07) + ": " + AppMain.hh07txt);
                    hh06.setVisibility(View.INVISIBLE);
                    hh06.setText(null);
                }
            }
        });


    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {

        if (AppMain.hh02txt == null) {
            AppMain.hh02txt = hh02.getText().toString();
        }
        if (formValidation()) {
            SaveDraft();
            AppMain.fCount++;
            Intent fA = new Intent(this, FamilyListingActivity.class);
            startActivity(fA);
        }

    }

    @OnClick(R.id.btnChangePSU)
    void onBtnChangePSUClick() {

        AppMain.hh02txt = null;
        Intent fA = new Intent(this, MainActivity.class);
        startActivity(fA);

    }


    private void SaveDraft() {

        AppMain.lc = new ListingContract();
        AppMain.lc.setHhDT(dtToday);
        AppMain.lc.setHh01(AppMain.hh01txt);
        AppMain.lc.setHh02(AppMain.hh02txt);
        AppMain.lc.setHh03(String.valueOf(AppMain.hh03txt));
        AppMain.lc.setHhadd(hhadd.getText().toString());
        switch (hh04.getCheckedRadioButtonId()) {
            case R.id.hh04a:
                AppMain.lc.setHh04("1");
                break;
            case R.id.hh04b:
                AppMain.lc.setHh04("2");
                break;
            case R.id.hh04c:
                AppMain.lc.setHh04("3");
                break;
            case R.id.hh04d:
                AppMain.lc.setHh04("4");
                break;
            case R.id.hh04e:
                AppMain.lc.setHh04("5");
                break;
            case R.id.hh04f:
                AppMain.lc.setHh04("6");
                break;
            case R.id.hh04g:
                AppMain.lc.setHh04("7");
                break;
            case R.id.hh04x:
                AppMain.lc.setHh04("88");
                break;
            default:
                AppMain.lc.setHh04("xx");
                break;
        }
        AppMain.lc.setHh04x(hh04x88.getText().toString());
        AppMain.lc.setHh05(hh05.isChecked() ? "1" : "2");
        AppMain.lc.setHh06(hh06.getText().toString());
        AppMain.lc.setHh07(AppMain.hh07txt);

        AppMain.lc.setDeviceID(deviceId);

        SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        AppMain.lc.setGPSLat(sharedPref.getString("Latitude", ""));
        AppMain.lc.setGPSLat(sharedPref.getString("Longitude", ""));
        AppMain.lc.setGPSLat(sharedPref.getString("Accuracy", ""));
        AppMain.lc.setGPSLat(sharedPref.getString("Time", ""));


        AppMain.fTotal = hh06.getText().toString().isEmpty() ? 0 : Integer.parseInt(hh06.getText().toString());

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: " + AppMain.lc.getHh03().toString());


    }


    private boolean formValidation() {
        if (AppMain.hh02txt == null) {
            Toast.makeText(this, "Please enter PSU", Toast.LENGTH_LONG).show();
            hh02.setError("Please enter PSU");
            Log.i(TAG, "PSU not given");
            return false;
        } else {
            hh02.setError(null);
        }
        if (hh04.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please one option", Toast.LENGTH_LONG).show();
            hh04x.setError("Please one option");
            Log.i(TAG, "Please one option");
            return false;
        } else {
            hh04x.setError(null);
        }

        if (hh04x.isChecked() && hh04x88.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter others", Toast.LENGTH_LONG).show();
            hh04x88.setError("Please enter others");
            Log.i(TAG, "Please enter others");
            return false;
        } else {
            hh04x88.setError(null);
        }

        if (hh05.isChecked() && hh06.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter number", Toast.LENGTH_LONG).show();
            hh06.setError("Please enter number");
            Log.i(TAG, "Please enter number");
            return false;
        } else {
            hh06.setError(null);
        }

        if (!hh06.getText().toString().isEmpty() && Integer.valueOf(hh06.getText().toString()) <= 1) {
            Toast.makeText(this, "Answers do not match!", Toast.LENGTH_LONG).show();
            hh06.setError("Answers do not match!");
            Log.i(TAG, "Answers do not match!");
            return false;
        } else {
            hh06.setError(null);
        }
        return true;
    }

    @OnClick(R.id.btnAddHousehold)
    void onBtnAddHouseholdClick() {
        if (AppMain.hh02txt == null) {
            AppMain.hh02txt = hh02.getText().toString();
        }
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

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        Log.d(TAG, "UpdateDB: Structure" + AppMain.lc.getHh03().toString());

        long updcount = db.addForm(AppMain.lc);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
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


