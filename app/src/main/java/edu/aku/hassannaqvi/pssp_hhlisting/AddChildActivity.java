package edu.aku.hassannaqvi.pssp_hhlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildActivity extends Activity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.activity_add_child)
    LinearLayout activityAddChild;
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
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;
    @BindView(R.id.icGender)
    RadioGroup icGender;
    @BindView(R.id.icGendera)
    RadioButton icGendera;
    @BindView(R.id.icGenderb)
    RadioButton icGenderb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);
        txtChildListing.setText("Child Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " (" + AppMain.cCount + " of " + AppMain.cTotal + ")");
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
        }

    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.cCount++;

                AppMain.lc.setHhChildNm(null);

                finish();
                Intent fA = new Intent(this, AddChildActivity.class);
                startActivity(fA);
            }

        }
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);
        try {
            Log.d(TAG, "onBtnAddFamilyClick: " + AppMain.lc.toJSONObject().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long updcount = db.addForm(AppMain.lc);

        if (updcount > 0) {

            AppMain.lc.setID(String.valueOf(updcount));

            AppMain.lc.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.lc.getID()));

            // Update UID of Last Inserted Form
            db.updateListingUID();

            return true;

        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    private void SaveDraft() {
        AppMain.lc.setHhChildNm(icName.getText().toString());
        AppMain.lc.setHh12d(icAgeD.getText().toString());
        AppMain.lc.setHh12m(icAgeM.getText().toString());
        AppMain.lc.setHh13(icGendera.isChecked() ? "1" : icGenderb.isChecked() ? "2" : "0");
        AppMain.lc.setChildSno(String.valueOf(AppMain.cCount));
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03());
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

        if (icAgeM.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter Age Months", Toast.LENGTH_LONG).show();
            icAgeM.setError("Please enter age");
            Log.i(TAG, "Please enter age");
            return false;
        } else if (Integer.valueOf(icAgeM.getText().toString()) > 59) {
            Toast.makeText(this, "Invalid Age Months", Toast.LENGTH_LONG).show();
            icAgeM.setError("Invalid enter age");
            Log.i(TAG, "Please enter age");
            return false;
        } else {
            icAgeM.setError(null);
        }
        if (icAgeD.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter Age Days", Toast.LENGTH_LONG).show();
            icAgeD.setError("Please enter age");
            Log.i(TAG, "Please enter age");
            return false;
        } else if (Integer.valueOf(icAgeD.getText().toString()) > 29) {
            Toast.makeText(this, "Invalid Age Days", Toast.LENGTH_LONG).show();
            icAgeD.setError("Invalid enter age");
            Log.i(TAG, "Please enter age");
            return false;
        } else {
            icAgeD.setError(null);
        }
        if (Integer.valueOf(icAgeD.getText().toString()) == 0 && Integer.valueOf(icAgeM.getText().toString()) == 0) {
            Toast.makeText(this, "Invalid Age ", Toast.LENGTH_LONG).show();
            icAgeD.setError("Invalid age");
            icAgeM.setError("Invalid age");
            Log.i(TAG, "Please enter age");
            return false;
        } else {
            icAgeD.setError(null);
            icAgeM.setError(null);
        }

        if (icGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select gender option", Toast.LENGTH_LONG).show();
            icGenderb.setError("Please one option");
            Log.i(TAG, "Please one option");
            return false;
        } else {
            icGenderb.setError(null);
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
                AppMain.lc.setHh07(AppMain.hh07txt);
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
