package edu.aku.hassannaqvi.tmk_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import edu.aku.hassannaqvi.tmk_hhlisting.core.MainApp;
import edu.aku.hassannaqvi.tmk_hhlisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.tmk_hhlisting.R;

public class FamilyListingActivity extends Activity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtFamilyListing)
    TextView txtFamilyListing;
    @BindView(R.id.hh08)
    EditText hh08;
    @BindView(R.id.hh09)
    EditText hh09;
    @BindView(R.id.hh10)
    Switch hh10;
    @BindView(R.id.hh11)
    EditText hh11;
    @BindView(R.id.hh12)
    Switch hh12;
    @BindView(R.id.hh13)
    EditText hh13;
    @BindView(R.id.hh14)
    EditText hh14;
    @BindView(R.id.btnAddChild)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_listing);
        ButterKnife.bind(this);


        txtFamilyListing.setText("Family Listing: " + MainApp.hh03txt + "-" + MainApp.hh07txt);

        if (MainApp.fCount < MainApp.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }

        hh10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh11.setVisibility(View.VISIBLE);
                    hh11.requestFocus();

                    hh12.setEnabled(true);
                } else {
                    hh11.setVisibility(View.INVISIBLE);
                    hh11.setText(null);

                    hh12.setEnabled(false);
                    hh13.setText(null);
                    hh13.setVisibility(View.INVISIBLE);
                    hh12.setChecked(false);
                }
            }
        });

        hh12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh13.setVisibility(View.VISIBLE);
                    hh13.requestFocus();
                } else {
                    hh13.setVisibility(View.INVISIBLE);
                    hh13.setText(null);
                }
            }
        });

        hh11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (s.length() > 0 && Integer.valueOf(s.toString()) > 0) {
                    Toast.makeText(FamilyListingActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    btnAddChild.setVisibility(View.VISIBLE);
                    btnAddFamilty.setVisibility(View.GONE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    btnAddChild.setVisibility(View.GONE);
                    if (MainApp.fCount < MainApp.fTotal) {
                        btnAddFamilty.setVisibility(View.VISIBLE);
                        btnAddHousehold.setVisibility(View.GONE);
                    } else {
                        btnAddFamilty.setVisibility(View.GONE);
                        btnAddHousehold.setVisibility(View.VISIBLE);
                    }
                } */

                if (MainApp.fCount < MainApp.fTotal) {
                    btnAddFamilty.setVisibility(View.VISIBLE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    btnAddFamilty.setVisibility(View.GONE);
                    btnAddHousehold.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {

        if (formValidation()) {

            SaveDraft();
            MainApp.cTotal = Integer.parseInt(hh11.getText().toString());
            MainApp.cCount++;
            Toast.makeText(this, MainApp.cCount + ":" + MainApp.cTotal + ":" + MainApp.fCount + ":" + MainApp.fTotal, Toast.LENGTH_SHORT).show();
            Intent fA = new Intent(this, AddChildActivity.class);
            startActivity(fA);
        }

    }

    private void SaveDraft() {

        MainApp.lc.setHh08(hh08.getText().toString());
        MainApp.lc.setHh09(hh09.getText().toString());
        MainApp.lc.setHh14(hh14.getText().toString());
        MainApp.lc.setHh10(hh10.isChecked() ? "1" : "2");
        MainApp.lc.setHh11(hh11.getText().toString().isEmpty() ? "0" : hh11.getText().toString());
        MainApp.lc.setHh12(hh12.isChecked() ? "1" : "2");
        MainApp.lc.setHh13(hh13.getText().toString().isEmpty() ? "0" : hh13.getText().toString());
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + MainApp.lc.getHh03().toString());

    }

    private boolean formValidation() {

        if (hh08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):"+R.string.hh08, Toast.LENGTH_LONG).show();
            hh08.setError("Data required");
            Log.i(TAG, "hh08: This data is required.");
            return false;
        } else {
            hh08.setError(null);
        }

        if (hh09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):"+R.string.hh09, Toast.LENGTH_LONG).show();
            hh09.setError("Data required");
            Log.i(TAG, "hh09: This data is required.");
            return false;
        } else {
            hh09.setError(null);
        }

        if (hh14.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):"+R.string.hh14, Toast.LENGTH_LONG).show();
            hh14.setError("Data required");
            Log.i(TAG, "hh14: This data is required.");
            return false;
        } else {
            hh14.setError(null);
        }

        if (hh10.isChecked() && hh11.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):"+R.string.hh11, Toast.LENGTH_LONG).show();
            hh11.setError("Data required");
            Log.i(TAG, "hh11: This data is required.");
            return false;
        } else {
            hh11.setError(null);
        }

        if (!hh11.getText().toString().isEmpty() && Integer.valueOf(hh11.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!"+R.string.hh11, Toast.LENGTH_LONG).show();
            hh11.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh11.setError(null);
        }

        if (hh12.isChecked() && hh13.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):"+R.string.hh13, Toast.LENGTH_LONG).show();
            hh13.setError("Data required");
            Log.i(TAG, "hh13: This data is required.");
            return false;
        } else {
            hh13.setError(null);
        }

        if (!hh13.getText().toString().isEmpty() && Integer.valueOf(hh13.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!"+R.string.hh13, Toast.LENGTH_LONG).show();
            hh13.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh13.setError(null);
        }

        if(hh10.isChecked()){
            if (Integer.parseInt(hh14.getText().toString()) <= Integer.parseInt(hh11.getText().toString())) {
                Toast.makeText(this, "Family member must be greater then child.", Toast.LENGTH_LONG).show();
                hh14.setError("Family member must be greater then child.");
                Log.i(TAG, "Family member must be greater then child.");
                return false;
            } else {
                hh14.setError(null);
            }
        }

        if (hh12.isChecked() && hh10.isChecked()) {
            if (Integer.parseInt(hh13.getText().toString()) > Integer.parseInt(hh11.getText().toString())) {
                Toast.makeText(this, "Not greater then child of age less then 5", Toast.LENGTH_LONG).show();
                hh13.setError("Not greater then child of age less then 5");
                Log.i(TAG, "Not greater then child of age less then 5");
                return false;
            } else {
                hh13.setError(null);
            }
        }


        return true;
    }


    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                MainApp.cCount = 0;
                MainApp.cTotal = 0;
                MainApp.hh07txt = String.valueOf((char) (MainApp.hh07txt.charAt(0) + 1));
                MainApp.lc.setHh07(MainApp.hh07txt.toString());
                MainApp.fCount++;

                finish();

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

        long updcount = db.addForm(MainApp.lc);

        MainApp.lc.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

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
