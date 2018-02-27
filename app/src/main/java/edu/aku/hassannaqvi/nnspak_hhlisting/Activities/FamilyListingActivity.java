package edu.aku.hassannaqvi.nnspak_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.nnspak_hhlisting.R;

public class FamilyListingActivity extends Activity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtFamilyListing)
    TextView txtFamilyListing;
    @BindView(R.id.txtTeamNoWithFam)
    TextView txtTeamNoWithFam;
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
    Switch hh14;
    @BindView(R.id.hh15)
    EditText hh15;
    @BindView(R.id.hh16)
    EditText hh16;


    @BindView(R.id.btnAddChild)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @BindViews({R.id.hh10, R.id.hh12, R.id.hh14})
    List<Switch> hh10_12;

    //@BindView(R.id.fldGrpHH14)
    //LinearLayout fldGrpHH14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_listing);
        ButterKnife.bind(this);


        txtFamilyListing.setText("Household Listing");
        txtTeamNoWithFam.setText(String.format("%03d", AppMain.hh01txt) + "/" + AppMain.hh03txt + "-" + AppMain.hh07txt);

        if (AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }

        // =================== Q 10 12 Skip Pattern ================
        for (Switch sw : hh10_12) {
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (hh10.isChecked()) {
                        hh11.setVisibility(View.VISIBLE);
                        hh11.requestFocus();
                    } else {
                        hh11.setVisibility(View.INVISIBLE);
                        hh11.setText(null);
                    }

                    if (hh12.isChecked()) {
                        hh13.setVisibility(View.VISIBLE);
                        hh13.requestFocus();
                    } else {
                        hh13.setVisibility(View.INVISIBLE);
                        hh13.setText(null);
                    }


                    if (hh14.isChecked()) {
                        hh15.setVisibility(View.VISIBLE);
                        hh15.requestFocus();
                    } else {
                        hh15.setVisibility(View.INVISIBLE);
                        hh15.setText(null);
                    }


                    /*if (hh10.isChecked() || hh12.isChecked()){
                        fldGrpHH14.setVisibility(View.VISIBLE);
                    }else {
                        fldGrpHH14.setVisibility(View.GONE);
                        hh14.setText(null);
                    }*/

                }
            });
        }

/*        hh10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh11.setVisibility(View.VISIBLE);
                    hh11.requestFocus();
                } else {
                    hh11.setVisibility(View.GONE);
                    hh11.setText(null);
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
                    hh13.setVisibility(View.GONE);
                    hh13.setText(null);
                }
            }
        });*/


        /*hh11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Integer.valueOf(s.toString()) > 0) {
                    Toast.makeText(FamilyListingActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    btnAddChild.setVisibility(View.VISIBLE);
                    btnAddFamilty.setVisibility(View.GONE);
                    btnAddHousehold.setVisibility(View.GONE);
                } else {
                    btnAddChild.setVisibility(View.GONE);
                    if (AppMain.fCount < AppMain.fTotal) {
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
        });*/

    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {

        if (formValidation()) {

            SaveDraft();
            AppMain.cTotal = Integer.parseInt(hh11.getText().toString());
            AppMain.cCount++;
            Toast.makeText(this, AppMain.cCount + ":" + AppMain.cTotal + ":" + AppMain.fCount + ":" + AppMain.fTotal, Toast.LENGTH_SHORT).show();
            finish();
            Intent fA = new Intent(this, AddChildActivity.class);
            startActivity(fA);
        }

    }

    private void SaveDraft() {

        AppMain.lc.setHh08(hh08.getText().toString());
        AppMain.lc.setHh09(hh09.getText().toString());
        AppMain.lc.setHh10(hh10.isChecked() ? "1" : "2");
        AppMain.lc.setHh11(hh11.getText().toString().isEmpty() ? "0" : hh11.getText().toString());
        AppMain.lc.setHh12(hh12.isChecked() ? "1" : "2");
        AppMain.lc.setHh13(hh13.getText().toString().isEmpty() ? "0" : hh13.getText().toString());
        AppMain.lc.setHh14(hh14.isChecked() ? "1" : "2");
        AppMain.lc.setHh15(hh15.getText().toString().isEmpty() ? "0" : hh15.getText().toString());
        AppMain.lc.setHh16(hh16.getText().toString());

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());

    }

    private boolean formValidation() {

        if (hh08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            hh08.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            hh08.setError(null);
        }

        if (hh09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_SHORT).show();
            hh09.setError("Please enter contact number");
            Log.i(TAG, "Please enter contact number");
            return false;
        } else {
            hh09.setError(null);
        }

        if (hh16.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.hh16), Toast.LENGTH_SHORT).show();
            hh16.setError("ERROR(empty): " + getString(R.string.hh16));
            Log.i(TAG, "ERROR(empty): " + getString(R.string.hh16));
            return false;
        } else {
            hh16.setError(null);
        }

        if (hh10.isChecked() && hh11.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter adolescents count", Toast.LENGTH_SHORT).show();
            hh11.setError("Please enter adolescents count");
            Log.i(TAG, "Please enter adolescents count");
            return false;
        } else {
            hh11.setError(null);
        }

        if (!hh11.getText().toString().isEmpty() && Integer.valueOf(hh11.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_SHORT).show();
            hh11.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh11.setError(null);
        }

        if (hh12.isChecked() && hh13.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter children count", Toast.LENGTH_SHORT).show();
            hh13.setError("Please enter children count");
            Log.i(TAG, "Please enter children count");
            return false;
        } else {
            hh13.setError(null);
        }

        if (!hh13.getText().toString().isEmpty() && Integer.valueOf(hh13.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_SHORT).show();
            hh13.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh13.setError(null);
        }


        if (hh14.isChecked() && hh15.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter married woman count", Toast.LENGTH_SHORT).show();
            hh14.setError("Please enter married woman count");
            Log.i(TAG, "Please enter married woman count");
            return false;
        } else {
            hh14.setError(null);
        }

        if (!hh15.getText().toString().isEmpty() && Integer.valueOf(hh15.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_SHORT).show();
            hh15.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh15.setError(null);
        }

        if (Integer.valueOf(hh16.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_SHORT).show();
            hh16.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh16.setError(null);
        }


        if (Integer.valueOf(hh16.getText().toString()) <
                (Integer.valueOf(hh11.getText().toString().isEmpty() ? "0" : hh11.getText().toString()) +
                        Integer.valueOf(hh13.getText().toString().isEmpty() ? "0" : hh13.getText().toString()) +
                        Integer.valueOf(hh15.getText().toString().isEmpty() ? "0" : hh15.getText().toString()))) {
            Toast.makeText(this, "Invalid Count!", Toast.LENGTH_SHORT).show();
            hh16.setError("Invalid Count!");
            Log.i(TAG, "(hh16): Invalid Count! ");
            return false;
        } else {
            hh16.setError(null);
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
                AppMain.lc.setHh07(AppMain.hh07txt.toString());
                AppMain.fCount++;

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

    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addForm(AppMain.lc);

        AppMain.lc.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            AppMain.lc.setUID(
                    (AppMain.lc.getDeviceID() + AppMain.lc.getID()));

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
