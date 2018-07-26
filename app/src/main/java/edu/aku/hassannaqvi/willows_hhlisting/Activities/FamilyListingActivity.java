package edu.aku.hassannaqvi.willows_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.willows_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.willows_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.willows_hhlisting.R;

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
    /*    @BindView(R.id.hh11)
        Switch hh11;*/
    @BindView(R.id.hh12)
    EditText hh12;

    @BindView(R.id.btnAddWomen)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @BindView(R.id.hh13)
    RadioGroup hh13;
    @BindView(R.id.hh13a)
    RadioButton hh13a;
    @BindView(R.id.hh13b)
    RadioButton hh13b;

    @BindView(R.id.fldGrpHH09)
    LinearLayout fldGrpHH09;

    @BindView(R.id.hh11)
    RadioGroup hh11;
    @BindView(R.id.hh11a)
    RadioButton hh11a;
    @BindView(R.id.hh11b)
    RadioButton hh11b;
    @BindView(R.id.hh11c)
    RadioButton hh11c;
    @BindView(R.id.hh11d)
    RadioButton hh11d;
    @BindView(R.id.hh11e)
    RadioButton hh11e;
    @BindView(R.id.hh11f)
    RadioButton hh11f;
    @BindView(R.id.hh11g)
    RadioButton hh11g;
    @BindView(R.id.hh11h)
    RadioButton hh11h;
    @BindView(R.id.hh1188)
    RadioButton hh1188;
    @BindView(R.id.hh1188x)
    EditText hh1188x;
    @BindView(R.id.hh1199)
    RadioButton hh1199;

    @BindView(R.id.hh10a)
    TextView hh10a;
    @BindView(R.id.hh12a)
    TextView hh12a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_listing);
        ButterKnife.bind(this);


        txtFamilyListing.setText("Family Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt);
        AppMain.lc.setHhWomenNm(null);

        if (AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }

        // change string on basis of type
        if (MainActivity.mwraTypeFlag) {
            hh10a.setText(hh10a.getText().toString().replace("44", "49"));
            hh12a.setText(hh12a.getText().toString().replace("44", "49"));
        }


/*        hh11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh12.setVisibility(View.VISIBLE);
                    hh12.requestFocus();
                } else {
                    hh12.setVisibility(View.GONE);
                    hh12.setText(null);
                }
            }
        });*/

        hh12.addTextChangedListener(new TextWatcher() {
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
        });

        hh13.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.hh13a) {
                    fldGrpHH09.setVisibility(View.VISIBLE);
                } else {
                    fldGrpHH09.setVisibility(View.GONE);

                    hh09.setText(null);
                    hh10.setText(null);
                    hh12.setText(null);
                    hh11.clearCheck();
                }
            }
        });

/*        hh09.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!hh09.getText().toString().trim().isEmpty()) {
                    if (Integer.valueOf(hh09.getText().toString()) > 0) {
                        hh11.setEnabled(true);
                    } else {
                        hh11.setChecked(false);
                        hh11.setEnabled(false);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        hh11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == hh1188.getId()) {
                    hh1188x.setVisibility(View.VISIBLE);
                } else {
                    hh1188x.setVisibility(View.GONE);
                    hh1188x.setText(null);
                }

            }
        });

    }

    @OnClick(R.id.btnAddWomen)
    void onBtnAddWomenClick() {

        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                AppMain.cTotal = Integer.parseInt(hh12.getText().toString());
                AppMain.cCount++;
                Toast.makeText(this, AppMain.cCount + ":" + AppMain.cTotal + ":" + AppMain.fCount + ":" + AppMain.fTotal, Toast.LENGTH_SHORT).show();
                Intent fA = new Intent(this, AddWomenActivity.class);
                startActivity(fA);
            }
        }

    }

    private void SaveDraft() {

        AppMain.lc.setHh08(hh08.getText().toString());
        AppMain.lc.setHh09(hh09.getText().toString());
        AppMain.lc.setHh10(hh10.getText().toString());

        AppMain.lc.setHh11(hh11a.isChecked() ? "1" : hh11b.isChecked() ? "2" : hh11c.isChecked() ? "3"
                : hh11d.isChecked() ? "4" : hh11e.isChecked() ? "5" : hh11f.isChecked() ? "6" :
                hh11g.isChecked() ? "7" : hh11h.isChecked() ? "8" : hh1188.isChecked() ? "88"
                        : hh1199.isChecked() ? "99" : "0");
        AppMain.lc.setHh11x(hh1188x.getText().toString());

        AppMain.lc.setHh12(hh12.getText().toString().isEmpty() ? "0" : hh12.getText().toString());
        AppMain.lc.setStatus(hh13a.isChecked() ? "1" : hh13b.isChecked() ? "2" : "0");
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());

    }

    private boolean formValidation() {

        if (hh08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            hh08.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            hh08.setError(null);
        }

        if (hh13.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.hh13), Toast.LENGTH_SHORT).show();
            hh13a.setError("This data is Required!");    // Set Error on last radio button
            hh13a.setFocusable(true);
            hh13a.setFocusableInTouchMode(true);
            hh13a.requestFocus();
            Log.i(TAG, "hh13: This data is Required!");
            return false;
        } else {
            hh13a.setError(null);
        }

        if (hh13a.isChecked()) {

            if (hh11.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.hh11), Toast.LENGTH_SHORT).show();
                hh1199.setError("This data is Required!");    // Set Error on last radio button
                hh1199.setFocusable(true);
                hh1199.setFocusableInTouchMode(true);
                hh1199.requestFocus();
                Log.i(TAG, "hh11: This data is Required!");
                return false;
            } else {
                hh1199.setError(null);
            }

            if (hh1188.isChecked() && hh1188x.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.hhother), Toast.LENGTH_LONG).show();
                hh1188x.setError("This data is Required!");    // Set Error on last radio button
                Log.i(TAG, "hh1188x: This data is Required!");
                hh1188x.requestFocus();
                return false;
            } else {
                hh1188x.setError(null);
            }

            if (hh09.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.hh09), Toast.LENGTH_LONG).show();
                hh09.setError("This data is Required!");    // Set Error on last radio button
                Log.i(TAG, "hh09: This data is Required!");
                hh09.requestFocus();
                return false;
            } else {
                hh09.setError(null);
            }

            if (Integer.valueOf(hh09.getText().toString()) > 15) {
                Toast.makeText(this, "Not greater then 15", Toast.LENGTH_LONG).show();
                hh09.setError("Not greater then 15!");    // Set Error on last radio button
                Log.i(TAG, "hh09: Not greater then 15!");
                hh09.requestFocus();
                return false;
            } else {
                hh09.setError(null);
            }

            if (hh10.getText().toString().isEmpty()) {
                Toast.makeText(this, "ERROR(empty): " + getString(R.string.hh10), Toast.LENGTH_LONG).show();
                hh10.setError("This data is Required!");    // Set Error on last radio button
                Log.i(TAG, "hh10: This data is Required!");
                hh10.requestFocus();
                return false;
            } else {
                hh10.setError(null);
            }

            if (Integer.valueOf(hh10.getText().toString()) > Integer.valueOf(hh09.getText().toString())) {
                Toast.makeText(this, "Not greater then total womens", Toast.LENGTH_LONG).show();
                hh10.setError("Not greater then total women!");    // Set Error on last radio button
                Log.i(TAG, "hh10: Not greater then total women!");
                hh10.requestFocus();
                return false;
            } else {
                hh10.setError(null);
            }

//            if (hh11.isChecked()) {
            if (hh12.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter women count", Toast.LENGTH_LONG).show();
                hh12.setError("Please enter women count");
                Log.i(TAG, "Please enter women count");
                return false;
            } else {
                hh12.setError(null);
            }

            if (Integer.valueOf(hh10.getText().toString()) < Integer.valueOf(hh12.getText().toString())) {
                Toast.makeText(this, "Invalid Value!", Toast.LENGTH_LONG).show();
                hh12.setError("Invalid Value!");
                Log.i(TAG, "Invalid Value!");
                return false;
            } else {
                hh12.setError(null);
            }
//            }
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