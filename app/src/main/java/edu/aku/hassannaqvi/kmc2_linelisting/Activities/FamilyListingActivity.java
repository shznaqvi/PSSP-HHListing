package edu.aku.hassannaqvi.kmc2_linelisting.Activities;

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
import edu.aku.hassannaqvi.kmc2_linelisting.R;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

public class FamilyListingActivity extends Activity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtFamilyListing)
    TextView txtFamilyListing;
    @BindView(R.id.hh08)
    EditText hh08;
    @BindView(R.id.hh09)
    EditText hh09;
    @BindView(R.id.hh10)
    EditText hh10;
    @BindView(R.id.hh11)
    EditText hh11;
    @BindView(R.id.hh12)
    EditText hh12;
    @BindView(R.id.hh13)
    EditText hh13;
    @BindView(R.id.hh14)
    Switch hh14;
    @BindView(R.id.hh15)
    EditText hh15;
    @BindView(R.id.btnAddPregnancy)
    Button btnAddPregnancy;
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

        hh14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh15.setVisibility(View.VISIBLE);
                    hh15.requestFocus();
                } else {
                    hh15.setVisibility(View.GONE);
                    hh15.setText(null);
                }
            }
        });

        hh15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Integer.valueOf(s.toString()) > 0) {
                    btnAddPregnancy.setVisibility(View.VISIBLE);
                    btnAddFamilty.setVisibility(View.GONE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    btnAddPregnancy.setVisibility(View.GONE);
                    if (MainApp.fCount < MainApp.fTotal) {
                        btnAddFamilty.setVisibility(View.VISIBLE);
                        btnAddHousehold.setVisibility(View.GONE);
                    } else {
                        btnAddFamilty.setVisibility(View.GONE);
                        btnAddHousehold.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btnAddPregnancy)
    void onBtnAddChildClick() {

        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                Toast.makeText(this, MainApp.cCount + ":" + MainApp.cTotal + ":" + MainApp.fCount + ":" + MainApp.fTotal, Toast.LENGTH_SHORT).show();
                Intent fA = new Intent(this, AddPregnancyActivity.class)
                        .putExtra("cCount", Integer.parseInt(hh15.getText().toString()));
                startActivity(fA);
            }
        }

    }

    private void SaveDraft() {

        MainApp.lc.setHh08(hh08.getText().toString());
        MainApp.lc.setHh09(hh09.getText().toString());
        MainApp.lc.setHh10(hh10.getText().toString());
        MainApp.lc.setHh11(hh11.getText().toString());
        MainApp.lc.setHh12(hh12.getText().toString());
        MainApp.lc.setHh13(hh13.getText().toString());
        MainApp.lc.setHh14(hh14.isChecked() ? "1" : "2");
        MainApp.lc.setHh15(hh15.getText().toString());
        Log.d(TAG, "SaveDraft: Structure " + MainApp.lc.getHh03().toString());

    }

    private boolean formValidation() {

        if (hh08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh08), Toast.LENGTH_LONG).show();
            hh08.setError("Data required");
            Log.i(TAG, "hh08: This data is required.");
            return false;
        } else {
            hh08.setError(null);
        }

        if (hh09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh09), Toast.LENGTH_LONG).show();
            hh09.setError("Data required");
            Log.i(TAG, "hh09: This data is required.");
            return false;
        } else {
            hh09.setError(null);
        }

        if (Integer.valueOf(hh09.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid (Value)" + getString(R.string.hh09), Toast.LENGTH_LONG).show();
            hh09.setError("Total members greater than 0!!");
            Log.i(TAG, "hh09: Total members greater than 0!!");
            return false;
        } else {
            hh09.setError(null);
        }

        if (hh10.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh10), Toast.LENGTH_LONG).show();
            hh10.setError("Data required");
            Log.i(TAG, "hh10: This data is required.");
            return false;
        } else {
            hh10.setError(null);
        }

        if (hh11.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh11), Toast.LENGTH_LONG).show();
            hh11.setError("Data required");
            Log.i(TAG, "hh11: This data is required.");
            return false;
        } else {
            hh11.setError(null);
        }

        if (hh12.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh12), Toast.LENGTH_LONG).show();
            hh12.setError("Data required");
            Log.i(TAG, "hh12: This data is required.");
            return false;
        } else {
            hh12.setError(null);
        }

        if (hh13.getText().toString().isEmpty()) {
            Toast.makeText(this, "Error(Empty):" + getString(R.string.hh13), Toast.LENGTH_LONG).show();
            hh13.setError("Data required");
            Log.i(TAG, "hh13: This data is required.");
            return false;
        } else {
            hh13.setError(null);
        }

        if (hh14.isChecked()) {
            if (Integer.valueOf(hh15.getText().toString()) < 1) {
                Toast.makeText(this, "Invalid (Value)" + getString(R.string.hh15), Toast.LENGTH_LONG).show();
                hh15.setError("Greater then 0!!");
                Log.i(TAG, "hh15: Greater than 0!!");
                return false;
            } else {
                hh15.setError(null);
            }

            if (Integer.valueOf(hh09.getText().toString()) < Integer.valueOf(hh10.getText().toString())) {
                Toast.makeText(this, "Invalid (Value)" + getString(R.string.hh15), Toast.LENGTH_LONG).show();
                hh15.setError("Lesser than or equal to " + (Integer.valueOf(hh10.getText().toString())) + "!!");
                Log.i(TAG, "hh15: Lesser than or equal to " + (Integer.valueOf(hh10.getText().toString())) + "!!");
                return false;
            } else {
                hh15.setError(null);
            }
        }

        if (Integer.valueOf(hh09.getText().toString()) < (Integer.valueOf(hh10.getText().toString()) + Integer.valueOf(hh11.getText().toString()))) {
            Toast.makeText(this, "Invalid (Value)" + getString(R.string.hh09), Toast.LENGTH_LONG).show();
            hh09.setError("Greater than or equal to " + (Integer.valueOf(hh10.getText().toString()) + Integer.valueOf(hh11.getText().toString())) + "!!");
            Log.i(TAG, "hh09: Greater than or equal to " + (Integer.valueOf(hh10.getText().toString()) + Integer.valueOf(hh11.getText().toString())) + "!!");
            return false;
        } else {
            hh09.setError(null);
        }


        return true;
    }


    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
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
