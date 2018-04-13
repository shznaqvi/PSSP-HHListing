package edu.aku.hassannaqvi.po_hhlisting.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.po_hhlisting.R;
import edu.aku.hassannaqvi.po_hhlisting.contract.ChildContract;
import edu.aku.hassannaqvi.po_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.po_hhlisting.core.FormsDBHelper;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

public class AddChildActivity extends AppCompatActivity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.activity_add_child)
    ScrollView activityAddChild;

    @BindView(R.id.txtChildListing)
    TextView txtChildListing;

    @BindView(R.id.ch06)
    EditText ch06;

    @BindView(R.id.ch07)
    EditText ch07;

    @BindView(R.id.ch08)
    RadioGroup ch08;
    @BindView(R.id.ch08a)
    RadioButton ch08a;
    @BindView(R.id.ch08b)
    RadioButton ch08b;

    @BindView(R.id.ch09)
    DatePickerInputEditText ch09;

    @BindView(R.id.ch10d)
    EditText ch10d;

    @BindView(R.id.ch10m)
    EditText ch10m;

    @BindView(R.id.ch10y)
    EditText ch10y;

    @BindView(R.id.ch11)
    RadioGroup ch11;

    @BindView(R.id.ch11a)
    RadioButton ch11a;
    @BindView(R.id.ch11b)
    RadioButton ch11b;
    @BindView(R.id.ch11c)
    RadioButton ch11c;
    @BindView(R.id.ch11d)
    RadioButton ch11d;
    @BindView(R.id.ch11e)
    RadioButton ch11e;
    @BindView(R.id.ch11f)
    RadioButton ch11f;
    @BindView(R.id.ch11g)
    RadioButton ch11g;
    @BindView(R.id.ch11h)
    RadioButton ch11h;

    @BindView(R.id.ch1188)
    EditText ch1188;


    @BindView(R.id.txtCounter)
    TextView txtCounter;

    @BindView(R.id.btnAddChild)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    static int count = 0;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);

        String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String minDate2years = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - ((AppMain.MILLISECONDS_IN_2_YEARS) + (AppMain.MILLISECONDS_IN_DAY)));


        ch09.setManager(getSupportFragmentManager());
        ch09.setMaxDate(dateToday);
        ch09.setMinDate(minDate2years);

        count = 1;

        int total2Months = Integer.valueOf(AppMain.cCount2m) + Integer.valueOf(AppMain.cCount2f);
        int total59Months = Integer.valueOf(AppMain.cCount59m) + Integer.valueOf(AppMain.cCount59f);

        txtCounter.setText(count + " out of " + total2Months);

        btnAddChild.setVisibility(View.VISIBLE);

        /*txtChildListing.setText("Child Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " (" + AppMain.cCount + " of " + AppMain.cTotal + ")");
        if (AppMain.cCount < AppMain.cTotal) {
            btnAddChild.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddFamilty.setVisibility(View.GONE);
        } else if (AppMain.cCount >= AppMain.cTotal && AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddChild.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
            btnAddChild.setVisibility(View.GONE);
        }*/

        ch11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (ch11h.isChecked()) {
                    ch1188.setVisibility(View.VISIBLE);
                } else {
                    ch1188.setText(null);
                    ch1188.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (UpdateDB()) {

                //AppMain.cCount++;
                // AppMain.lc.setHh04Village(null);
                //Intent fA = new Intent(this, AddChildActivity.class);
                //startActivity(fA);
            }

        }
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addChild(AppMain.childContract);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            //AppMain.lc.setHh04Village(null);

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }

        updcount = db.updateChild();

        return true;
    }

    private void SaveDraft() throws JSONException {

        //AppMain.lc.setHh04Village(ch06.getText().toString());

        AppMain.childContract = new ChildContract();

        AppMain.childContract.chDT = dtToday;
        AppMain.childContract.ch01 = AppMain.hh01txt;
        AppMain.childContract.ch02 = AppMain.hh02txt;
        AppMain.childContract.ch03 = String.valueOf(AppMain.hh03txt);
        AppMain.childContract.ch04 = AppMain.hh04txt;
        AppMain.childContract.UUID = AppMain.lc.getUID();

        JSONObject js = new JSONObject();

        js.put("ch06", ch06.getText().toString());
        js.put("ch07", ch07.getText().toString());
        js.put("ch08", ch08a.isChecked() ? "1" : ch08b.isChecked() ? "2" : "0");
        js.put("ch09", ch09.getText().toString());
        js.put("ch10d", ch10d.getText().toString());
        js.put("ch10m", ch10m.getText().toString());
        js.put("ch10y", ch10y.getText().toString());
        js.put("ch11", ch11a.isChecked() ? "1"
                : ch11b.isChecked() ? "2"
                : ch11c.isChecked() ? "3"
                : ch11d.isChecked() ? "4"
                : ch11e.isChecked() ? "5"
                : ch11f.isChecked() ? "6"
                : ch11g.isChecked() ? "7"
                : ch11h.isChecked() ? "8"
                : "0");

        js.put("ch1188", ch1188.getText().toString());

        AppMain.childContract.setChild(String.valueOf(js));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());
    }

    private boolean formValidation() {

        if (ch06.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            ch06.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            ch06.setError(null);
        }


        if (ch07.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            ch07.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            ch07.setError(null);
        }


        if (ch08.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.ch08), Toast.LENGTH_SHORT).show();
            ch08b.setError("This Data is required");
            Log.d(TAG, "crg01 : This Data is required");
            return false;
        } else {
            ch08b.setError(null);
        }


        if (ch09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter date of birth", Toast.LENGTH_LONG).show();
            ch09.setError("Please enter date of birth");
            Log.i(TAG, "Please enter date of birth");
            return false;
        } else {
            ch09.setError(null);
        }


        if (ch10d.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter days", Toast.LENGTH_LONG).show();
            ch10d.setError("Please enter days");
            Log.i(TAG, "Please enter days");
            return false;
        } else {
            ch10d.setError(null);
        }


        if (Integer.valueOf(ch10d.getText().toString()) < 0 || Integer.valueOf(ch10d.getText().toString()) > 29) {
            Toast.makeText(this, "Invalid days 0 - 29", Toast.LENGTH_LONG).show();
            ch10d.setError("Invalid days 0 - 29");
            Log.i(TAG, "Invalid days 0 - 29");
            return false;
        } else {
            ch10d.setError(null);
        }


        if (ch10m.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter month", Toast.LENGTH_LONG).show();
            ch10m.setError("Please enter month");
            Log.i(TAG, "Please enter month");
            return false;
        } else {
            ch10m.setError(null);
        }


        if (Integer.valueOf(ch10m.getText().toString()) < 0 || Integer.valueOf(ch10m.getText().toString()) > 11) {
            Toast.makeText(this, "Invalid month 0 - 11", Toast.LENGTH_LONG).show();
            ch10m.setError("Invalid month 0 - 11");
            Log.i(TAG, "Invalid month 0 - 11");
            return false;
        } else {
            ch10m.setError(null);
        }


        if (ch10y.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter year", Toast.LENGTH_LONG).show();
            ch10y.setError("Please enter year");
            Log.i(TAG, "Please enter year");
            return false;
        } else {
            ch10y.setError(null);
        }


        if (Integer.valueOf(ch10y.getText().toString()) < 0 || Integer.valueOf(ch10y.getText().toString()) > 2) {
            Toast.makeText(this, "Invalid year 0 - 2", Toast.LENGTH_LONG).show();
            ch10y.setError("Invalid year 0 - 2");
            Log.i(TAG, "Invalid year 0 - 2");
            return false;
        } else {
            ch10y.setError(null);
        }


        if (ch11.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.ch11), Toast.LENGTH_SHORT).show();
            ch11h.setError("This Data is required");
            Log.d(TAG, "crg01 : This Data is required");
            return false;
        } else {
            ch11h.setError(null);
        }


        if (ch11h.isChecked()) {

            if (ch1188.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter other", Toast.LENGTH_LONG).show();
                ch1188.setError("Please enter other");
                Log.i(TAG, "Please enter other");
                return false;
            } else {
                ch1188.setError(null);
            }
        }


        return true;
    }


    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                AppMain.fCount = 0;
                AppMain.fTotal = 0;
                AppMain.cCount = 0;
                AppMain.cTotal = 0;

                //Intent fA = new Intent(this, setupActivity.class);
                Intent childActivity = new Intent(this, AddChildActivity.class);
                startActivity(childActivity);

            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}