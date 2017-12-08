package edu.aku.hassannaqvi.src_hhlisting2.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import edu.aku.hassannaqvi.src_hhlisting2.core.AppMain;
import edu.aku.hassannaqvi.src_hhlisting2.core.FormsDBHelper;
import edu.aku.hassannaqvi.src_hhlisting2.R;
import edu.aku.hassannaqvi.src_hhlisting2.contract.MwraContract;

public class AddMarriedWomenActivity extends Activity {

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.activity_add_married_women)
    ScrollView activityAddMarriedWomen;
    @BindView(R.id.mw01)
    EditText mw01;
    @BindView(R.id.txtMwraListing)
    TextView txtMwraListing;
    @BindView(R.id.mw02)
    Switch mw02;
    @BindView(R.id.fldGrpmw03)
    LinearLayout fldGrpmw03;
    @BindView(R.id.mw03)
    RadioGroup mw03;
    @BindView(R.id.mw03a)
    RadioButton mw03a;
    @BindView(R.id.mw03b)
    RadioButton mw03b;
    @BindView(R.id.mw03c)
    RadioButton mw03c;
    @BindView(R.id.mw03d)
    RadioButton mw03d;
    @BindView(R.id.mw03e)
    RadioButton mw03e;
    @BindView(R.id.mw03f)
    RadioButton mw03f;
    @BindView(R.id.mw04)
    Switch mw04;
    @BindView(R.id.fldGrpmw05)
    LinearLayout fldGrpmw05;
    @BindView(R.id.mw05)
    EditText mw05;

    @BindView(R.id.btnContNextQ)
    Button btnContNextQ;
    @BindView(R.id.btnAddMWRA)
    Button btnAddMWRA;

    private String TAG = "AddMarriedWomenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_married_women);
        ButterKnife.bind(this);
        txtMwraListing.setText("MWRA Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " (" + AppMain.mwraCount + " of " + AppMain.mwraTotal + ")");

        if (AppMain.mwraCount < AppMain.mwraTotal) {
            btnAddMWRA.setVisibility(View.VISIBLE);
            btnContNextQ.setVisibility(View.GONE);
        } else {
            btnAddMWRA.setVisibility(View.GONE);
            btnContNextQ.setVisibility(View.VISIBLE);
        }

        mw02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fldGrpmw03.setVisibility(View.VISIBLE);
                } else {
                    fldGrpmw03.setVisibility(View.GONE);
                    mw03.clearCheck();
                }
            }
        });

        mw04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fldGrpmw05.setVisibility(View.VISIBLE);
                } else {
                    fldGrpmw05.setVisibility(View.GONE);
                    mw05.setText(null);
                }
            }
        });

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
                AppMain.mwraCount++;
                Toast.makeText(this, AppMain.mwraCount + ":" + AppMain.mwraTotal + ":" + AppMain.fCount + ":" + AppMain.fTotal, Toast.LENGTH_SHORT).show();
                Intent mwraA = new Intent(this, AddMarriedWomenActivity.class);
                startActivity(mwraA);
            }

        }

    }


    private void SaveDraft() {

        AppMain.mwra = new MwraContract();

        AppMain.mwra.setUUID(AppMain.lc.getUID());
        AppMain.mwra.setMwDT(dtToday);
        AppMain.mwra.setMwVillageCode(AppMain.lc.getHh02());
        Log.d(TAG, "SaveDraft-hh02: " + AppMain.lc.getHh02());
        AppMain.mwra.setMwStructureNo(AppMain.lc.getHh03() + "-" + AppMain.lc.getHh07());
        Log.d(TAG, "SaveDraft-hh07: " + AppMain.lc.getHh07());

        AppMain.mwra.setMw01(mw01.getText().toString());
        AppMain.mwra.setMw02(mw02.isChecked() ? "1" : "2");
        AppMain.mwra.setMw03(mw03a.isChecked() ? "1"
                : mw03b.isChecked() ? "2"
                : mw03c.isChecked() ? "3"
                : mw03d.isChecked() ? "4"
                : mw03e.isChecked() ? "5"
                : mw03f.isChecked() ? "6"
                : "default"
        );
        AppMain.mwra.setMw04(mw04.isChecked() ? "1" : "2");
        AppMain.mwra.setMw05(mw05.getText().toString());
        AppMain.mwra.setMWRAID(String.valueOf(AppMain.mwraCount));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {

        if (mw01.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty(mw01):" + getResources().getString(R.string.mw01), Toast.LENGTH_LONG).show();
            mw01.setError("Cannot be empty");
            Log.i(TAG, "mw01 not given");
            return false;
        } else {
            mw01.setError(null);
        }

        if (mw02.isChecked()) {

            if (mw03.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "NotSelected(mw04):" + getResources().getString(R.string.mw03), Toast.LENGTH_LONG).show();
                mw03f.setError("Please one option");
                Log.i(TAG, "Please one option");
                return false;
            } else {
                mw03f.setError(null);
            }
        }

        if (mw04.isChecked()) {
            if (mw05.getText().toString().isEmpty()) {
                Toast.makeText(this, "Empty(mw04):" + getResources().getString(R.string.mw05), Toast.LENGTH_LONG).show();
                mw05.setError("Cannot be empty");
                Log.i(TAG, "Cannot be empty");
                return false;

            } else {
                mw05.setError(null);
            }

        }
        return true;
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        Long rowId;

        rowId = db.addMwra(AppMain.mwra);

        AppMain.mwra.setID(rowId);


        if (rowId != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.mwra.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.mwra.getID()));
            db.updateMwra();
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
