package edu.aku.hassannaqvi.cbt_hhlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildActivity extends Activity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.txtChildListing)
    TextView txtChildListing;
    @BindView(R.id.icName)
    EditText icName;
    @BindView(R.id.icAgeM)
    EditText icAgeM;
    @BindView(R.id.icAgeD)
    EditText icAgeD;
    @BindView(R.id.btnAddChild)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.icdobage)
    RadioGroup icdobage;
    @BindView(R.id.icdob01)
    RadioButton icdob01;
    @BindView(R.id.icAge02)
    RadioButton icAge02;
    @BindView(R.id.icage)
    LinearLayout icage;
    @BindView(R.id.icdob)
    LinearLayout icdob;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;
    @BindView(R.id.hhdob)
    DatePicker hhdob;
    FormsDBHelper db;
    String dtToday;

    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH);
    int day = now.get(Calendar.DATE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);
        hhdob.setMaxDate(new Date().getTime());
        hhdob.setMinDate(new Date().getTime() - ((AppMain.MILLISECONDS_IN_DAY * 30) * 7));
        dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

//        AppMain.cCount++;

        txtChildListing.setText("Child Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " (" + (AppMain.cCount + 1) + " of " + AppMain.cTotal + ")");
        if (AppMain.cCount < (AppMain.cTotal-1)) {
            btnAddChild.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddFamilty.setVisibility(View.GONE);
//        } else if (AppMain.cCount >= AppMain.cTotal && AppMain.fCount < AppMain.fTotal) {
        }
        else if (AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddChild.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
            btnAddChild.setVisibility(View.GONE);
        }

        db = new FormsDBHelper(this);

        icdobage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (icdob01.isChecked()){
                    icdob.setVisibility(View.VISIBLE);
                    icage.setVisibility(View.GONE);
                    icAgeM.setText(null);
                    icAgeD.setText(null);
                }else {
                    icdob.setVisibility(View.GONE);
                    icage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.cCount++;

                finish();
                Intent fA = new Intent(this, AddChildActivity.class);
                startActivity(fA);
            }

        }
    }

    private boolean UpdateDB() {
        long updcount = db.addChild(AppMain.cc);

        AppMain.cc.setID(updcount);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.cc.setUID(
                    (AppMain.cc.getDeviceId() + AppMain.cc.getID()));
            db.updateCc();
            Toast.makeText(this, "Current Form No: " + AppMain.cc.getUID(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft() {

        AppMain.cc = new ChildrenContract();
        String DOB = new SimpleDateFormat("dd-MM-yyyy").format(hhdob.getCalendarView().getDate());


        AppMain.cc.setUUID(AppMain.lc.getUID());
        AppMain.cc.setcDT(dtToday);
        AppMain.cc.setUserName(AppMain.userName);
        AppMain.cc.setChildName(icName.getText().toString());
        if (icAge02.isChecked()) {
            AppMain.cc.setCc12d(icAgeD.getText().toString());
            AppMain.cc.setCc12m(icAgeM.getText().toString());
        }else {

            Calendar cal = Calendar.getInstance();
            cal.set(hhdob.getYear(), hhdob.getMonth(), hhdob.getDayOfMonth());
            Date date1 = new Date();
            Date date2 = cal.getTime();
            long diff = date1.getTime() - date2.getTime();
            long ageInYears = (diff / (24*60*60*1000)) / 365;
            long currentAge = (diff / (24 * 60 * 60 * 1000))/30;
            double ageindays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            ageindays = Math.round(ageindays % 30.475);

            AppMain.cc.setCc12d(String.valueOf(ageindays));
            AppMain.cc.setCc12m(String.valueOf(currentAge));
        }
        AppMain.cc.setCcDob(DOB);
        AppMain.cc.setDeviceId(AppMain.lc.getDeviceID());
        AppMain.cc.setTehsil(AppMain.tehsilCode);
        AppMain.cc.setHh01(AppMain.lc.getHh01());
        AppMain.cc.setHh02(AppMain.lc.getHh02());
        AppMain.cc.setLhwCode(AppMain.hh02txt );
        AppMain.cc.setHousehold(AppMain.household);
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());


    }

    private boolean formValidation() {

        if (icName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Please enter name", Toast.LENGTH_LONG).show();
            icName.setError("Invalid(Error):Please enter name");
            Log.i(TAG, "Invalid(Error):Please enter name");
            return false;
        } else {
            icName.setError(null);
        }

        if (icAge02.isChecked()) {

            if (icAgeM.getText().toString().isEmpty()) {
                Toast.makeText(this, "Invalid(Error):Please enter Age Months", Toast.LENGTH_LONG).show();
                icAgeM.setError("Invalid(Error):Please enter age");
                Log.i(TAG, "Invalid(Error):Please enter age");
                return false;
            }
            if (Integer.valueOf(icAgeM.getText().toString()) < 0 || Integer.valueOf(icAgeM.getText().toString()) > 6) {
                Toast.makeText(this, "Invalid(Error):Invalid Age Months", Toast.LENGTH_LONG).show();
                icAgeM.setError("Invalid(Error):Invalid enter age");
                Log.i(TAG, "Invalid(Error):Please enter age");
                return false;
            } else {
                icAgeM.setError(null);
            }
            if (icAgeD.getText().toString().isEmpty()) {
                Toast.makeText(this, "Invalid(Error):Please enter Age Days", Toast.LENGTH_LONG).show();
                icAgeD.setError("Invalid(Error):Please enter age");
                Log.i(TAG, "Invalid(Error):Please enter age");
                return false;
            }

            if (Integer.valueOf(icAgeM.getText().toString()) != 0) {
                if (Integer.valueOf(icAgeD.getText().toString()) < 0 || Integer.valueOf(icAgeD.getText().toString()) > 29) {
                    Toast.makeText(this, "Invalid(Error):Invalid Age Days", Toast.LENGTH_LONG).show();
                    icAgeD.setError("Invalid(Error):Invalid enter age");
                    Log.i(TAG, "Invalid(Error):Please enter age");
                    return false;
                } else {
                    icAgeD.setError(null);
                }
            } else {
                if (Integer.valueOf(icAgeD.getText().toString()) < 1 || Integer.valueOf(icAgeD.getText().toString()) > 29) {
                    Toast.makeText(this, "Invalid(Error):Invalid Age Days", Toast.LENGTH_LONG).show();
                    icAgeD.setError("Invalid(Error):Invalid enter age");
                    Log.i(TAG, "Invalid(Error):Please enter age");
                    return false;
                } else {
                    icAgeD.setError(null);
                }
            }
        }
//        if (Integer.valueOf(icAgeD.getText().toString()) == 0 && Integer.valueOf(icAgeM.getText().toString()) == 0) {
//            Toast.makeText(this, "Invalid Age ", Toast.LENGTH_LONG).show();
//            icAgeD.setError("Invalid age");
//            icAgeM.setError("Invalid age");
//            Log.i(TAG, "Please enter age");
//            return false;
//        } else {
//            icAgeD.setError(null);
//            icAgeM.setError(null);
//        }
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

                finish();
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

                finish();

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
