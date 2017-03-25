package edu.aku.hassannaqvi.cbt_hhlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPWomenActivity extends Activity {

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.activity_add_married_women)
    ScrollView activityAddMarriedWomen;
    @BindView(R.id.pw01)
    EditText pw01;
    @BindView(R.id.txtMwraListing)
    TextView txtMwraListing;
    @BindView(R.id.pw02)
    EditText pw02;
    @BindView(R.id.pw03)
    DatePicker pw03;

    @BindView(R.id.btnContNextQ)
    Button btnContNextQ;
    @BindView(R.id.btnAddMWRA)
    Button btnAddMWRA;

    private String TAG = "AddPWomenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_p_women);
        ButterKnife.bind(this);
        txtMwraListing.setText("PW Listing: " + AppMain.household + " (" + AppMain.pwCount + " of " + AppMain.pwTotal + ")");

        pw03.setMinDate(System.currentTimeMillis() - 1000);
        pw03.setMaxDate(new Date().getTime() + AppMain.MILLISECONDS_IN_DAY * 30 * 9);


        if (AppMain.pwCount < AppMain.pwTotal) {
            btnAddMWRA.setVisibility(View.VISIBLE);
            btnContNextQ.setVisibility(View.GONE);
        } else {
            btnAddMWRA.setVisibility(View.GONE);
            btnContNextQ.setVisibility(View.VISIBLE);
        }

    }


    @OnClick(R.id.btnContNextQ)
    void onBtnContNextQClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                Intent closeA = new Intent(this, ClosingActivity.class);
                startActivity(closeA);
            }
        }
    }


    @OnClick(R.id.btnAddMWRA)
    void onBtnAddMWRAClick() {

        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.pwCount++;
                Toast.makeText(this, AppMain.pwCount + ":" + AppMain.pwTotal + ":" + AppMain.fCount + ":" + AppMain.fTotal, Toast.LENGTH_SHORT).show();
                Intent mwraA = new Intent(this, AddPWomenActivity.class);
                startActivity(mwraA);
            }

        }

    }


    private void SaveDraft() {

        AppMain.pw = new PWContract();

        AppMain.pw.setUUID(AppMain.lc.getUID());
        AppMain.pw.setMwDT(dtToday);
        AppMain.pw.setMwVillageCode(AppMain.lc.getHh02());
        Log.d(TAG, "SaveDraft-hh02: " + AppMain.lc.getHh02());
        AppMain.pw.setMwStructureNo(AppMain.lc.getHh03());
        Log.d(TAG, "SaveDraft-hh07: " + AppMain.lc.getHh07());

        AppMain.pw.setPw01(pw01.getText().toString());
        AppMain.pw.setPw02(pw02.getText().toString());

        Calendar cal = Calendar.getInstance();
        cal.set(pw03.getYear(), pw03.getMonth(), pw03.getDayOfMonth());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        AppMain.pw.setPw03(dateFormat.format(cal.getTime()));

        AppMain.pw.setDeviceId(AppMain.lc.getDeviceID());
        AppMain.pw.setLhwCode(AppMain.hh02txt );
        AppMain.pw.setHousehold(AppMain.household);

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {

        if (pw01.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty(noHH):" + getResources().getString(R.string.pw01), Toast.LENGTH_LONG).show();
            pw01.setError("Cannot be empty");
            Log.i(TAG, "noHH not given");
            return false;
        } else {
            pw01.setError(null);
        }

        if (pw02.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty(mw04):" + getResources().getString(R.string.pw02), Toast.LENGTH_LONG).show();
            pw02.setError("Cannot be empty");
            Log.i(TAG, "Cannot be empty");
            return false;

        } else {
            pw02.setError(null);

            if (Integer.parseInt(pw02.getText().toString()) < 1 || Integer.parseInt(pw02.getText().toString()) > 49) {
                Toast.makeText(this, "Invalid(mw04):" + getResources().getString(R.string.pw02), Toast.LENGTH_LONG).show();
                pw02.setError("Invalid:Range 1 - 49");
                Log.i(TAG, "Invalid(mw04):Range 1 - 49");
                return false;

            } else {
                pw02.setError(null);
            }
        }


        return true;
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        Long rowId;

        rowId = db.addPw(AppMain.pw);

        AppMain.pw.setID(rowId);

        if (rowId != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.pw.setUID(
                    (AppMain.pw.getDeviceId() + AppMain.pw.getID()));
            db.updatePw();
            Toast.makeText(this, "Current Form No: " + AppMain.lc.getUID(), Toast.LENGTH_SHORT).show();
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
