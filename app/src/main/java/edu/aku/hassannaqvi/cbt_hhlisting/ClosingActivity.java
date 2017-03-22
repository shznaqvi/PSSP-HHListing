package edu.aku.hassannaqvi.cbt_hhlisting;

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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClosingActivity extends Activity {

    @BindView(R.id.activity_add_child)
    LinearLayout activityAddChild;
    @BindView(R.id.hh10)
    Switch hh10;
    @BindView(R.id.hh11)
    EditText hh11;
    @BindView(R.id.txtHouseholdListing)
    TextView txtHouseholdListing;
    @BindView(R.id.hh12)
    Switch hh12;
    @BindView(R.id.hh13)
    EditText hh13;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;
    @BindView(R.id.fldGrpHH11)
    LinearLayout fldGrpHH11;
    @BindView(R.id.fldGrpHH13)
    LinearLayout fldGrpHH13;
    private String TAG = "ClosingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing);
        ButterKnife.bind(this);

        txtHouseholdListing.setText("Household Information "+AppMain.household);

        if (AppMain.fCount < AppMain.fTotal) {
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
                    fldGrpHH11.setVisibility(View.VISIBLE);
                    btnAddHousehold.setText("Add Child");
                } else {
                    fldGrpHH11.setVisibility(View.GONE);
                    hh11.setText(null);

                    btnAddHousehold.setText("Goto Next Household");
                }
            }
        });
        hh12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fldGrpHH13.setVisibility(View.VISIBLE);
                } else {
                    fldGrpHH13.setVisibility(View.GONE);
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

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (hh11.getText().toString().isEmpty()) {
                    hh11.setError("Invalid(Error): Data required");
                } else {
                    hh11.setError(null);
                    if (Integer.parseInt(hh11.getText().toString()) < 1) {
                        hh11.setError("Invalid(Error): Child greater then 0");
                    } else {
                        hh11.setError(null);
                    }
                }
            }
        });
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
//                AppMain.fCount = 0;
//                AppMain.fTotal = 0;
//                AppMain.cCount = 0;
//                AppMain.cTotal = Integer.parseInt(hh11.getText().toString());
//                AppMain.pwCount = 0;
//                AppMain.pwTotal = 0;

                if (!hh10.isChecked()) {
                    AppMain.fCount = 0;
                    AppMain.fTotal = 0;
                    AppMain.cCount = 0;
                    AppMain.cTotal = 0;
                    AppMain.pwCount = 0;
                    AppMain.pwTotal = 0;
                    startActivity(new Intent(this, setupActivity.class));
                } else {
                    AppMain.cTotal = Integer.parseInt(hh11.getText().toString());
                    startActivity(new Intent(this, AddChildActivity.class));
                }

            }
        }
    }

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);


        long updcount = db.updateForm();

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft() {

        AppMain.lc.setHh10(hh10.isChecked() ? "1" : "2");
        AppMain.lc.setHh11(hh11.getText().toString());
        AppMain.lc.setHh12(hh12.isChecked() ? "1" : "2");
        AppMain.lc.setHh13(hh13.getText().toString());
        AppMain.lc.setUID(AppMain.lc.getDeviceID() + AppMain.lc.getID());

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {

        if (hh10.isChecked()) {
            if (hh11.getText().toString().isEmpty()) {
                Toast.makeText(this, "Error(Invalid):Cannot be Empty", Toast.LENGTH_LONG).show();
                hh11.setError("Error(Invalid):Cannot be Empty");
                Log.i(TAG, "hh11:Cannot be Empty");
                return false;
            } else {
                hh11.setError(null);
            }
            if (Integer.parseInt(hh11.getText().toString()) < 1) {
                Toast.makeText(this, "Error(Invalid):Greater then 0", Toast.LENGTH_LONG).show();
                hh11.setError("Error(Invalid):Greater then 0");
                Log.i(TAG, "hh11:Greater then 0");
                return false;
            } else {
                hh11.setError(null);
            }
        }
        if (hh12.isChecked()) {
            if (hh13.getText().toString().isEmpty()) {
                Toast.makeText(this, "Error(Invalid):Cannot be Empty", Toast.LENGTH_LONG).show();
                hh13.setError("Error(Invalid):Cannot be Empty");
                Log.i(TAG, "hh13:Cannot be Empty");
                return false;
            } else {
                hh13.setError(null);
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}
