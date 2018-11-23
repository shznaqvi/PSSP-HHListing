package edu.aku.hassannaqvi.nnspak_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.nnspak_hhlisting.R;
import edu.aku.hassannaqvi.nnspak_hhlisting.utils.JsonUtils;
import edu.aku.hassannaqvi.nnspak_hhlisting.validation.validatorClass;

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
    RadioGroup hh10;
    @BindView(R.id.hh10a)
    RadioButton hh10a;
    @BindView(R.id.hh10b)
    RadioButton hh10b;
    @BindView(R.id.hh11)
    EditText hh11;

    @BindView(R.id.hh12)
    RadioGroup hh12;
    @BindView(R.id.hh12a)
    RadioButton hh12a;
    @BindView(R.id.hh12b)
    RadioButton hh12b;
    @BindView(R.id.hh13)
    EditText hh13;


    @BindView(R.id.hh14)
    RadioGroup hh14;
    @BindView(R.id.hh14a)
    RadioButton hh14a;
    @BindView(R.id.hh14b)
    RadioButton hh14b;
    @BindView(R.id.hh15)
    EditText hh15;

    @BindView(R.id.hh16)
    EditText hh16;
    @BindView(R.id.hh17)
    Switch hh17;

    @BindView(R.id.hh08a1)
    RadioGroup hh08a1;
    @BindView(R.id.hh08a1a)
    RadioButton hh08a1a;
    @BindView(R.id.hh08a1b)
    RadioButton hh08a1b;
    @BindView(R.id.hh08a1c)
    RadioButton hh08a1c;

    @BindView(R.id.btnAddNewHousehold)
    Button btnAddNewHousehold;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @BindView(R.id.hh18)
    EditText hh18;
    @BindView(R.id.hh19)
    EditText hh19;
    @BindView(R.id.hh20)
    EditText hh20;
    @BindView(R.id.hh21)
    EditText hh21;
    @BindView(R.id.hh22)
    EditText hh22;
    @BindView(R.id.hh23)
    EditText hh23;
    @BindView(R.id.hh24)
    EditText hh24;
    @BindView(R.id.hh25)
    EditText hh25;
    @BindView(R.id.hh26)
    EditText hh26;

    @BindViews({R.id.hh10, R.id.hh12, R.id.hh14})
    List<RadioGroup> hh10_12;

    static Boolean familyFlag = false;

    //@BindView(R.id.fldGrpHH14)
    //LinearLayout fldGrpHH14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_listing);
        ButterKnife.bind(this);

        txtFamilyListing.setText("Household Information");
        txtTeamNoWithFam.setText("NNS-S" + String.format("%04d", AppMain.hh03txt) + "-H" + String.format("%03d", Integer.valueOf(AppMain.hh07txt)));

        setupButtons();

        // =================== Q 10 12 Skip Pattern ================
        for (RadioGroup sw : hh10_12) {
            sw.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (hh10a.isChecked()) {
                        hh11.setVisibility(View.VISIBLE);
                        hh11.requestFocus();
                    } else {
                        hh11.setVisibility(View.GONE);
                        hh11.setText(null);
                    }

                    if (hh12a.isChecked()) {
                        hh13.setVisibility(View.VISIBLE);
                        hh13.requestFocus();
                    } else {
                        hh13.setVisibility(View.GONE);
                        hh13.setText(null);
                    }

                    if (hh14a.isChecked()) {
                        hh15.setVisibility(View.VISIBLE);
                        hh15.requestFocus();
                    } else {
                        hh15.setVisibility(View.GONE);
                        hh15.setText(null);
                    }
                }
            });
            /*
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (hh10.isChecked()) {
                        hh11.setVisibility(View.VISIBLE);
                        hh11.requestFocus();
                    } else {
                        hh11.setVisibility(View.GONE);
                        hh11.setText(null);
                    }

                    if (hh12.isChecked()) {
                        hh13.setVisibility(View.VISIBLE);
                        hh13.requestFocus();
                    } else {
                        hh13.setVisibility(View.GONE);
                        hh13.setText(null);
                    }

                    if (hh14.isChecked()) {
                        hh15.setVisibility(View.VISIBLE);
                        hh15.requestFocus();
                    } else {
                        hh15.setVisibility(View.GONE);
                        hh15.setText(null);
                    }
                }
            });*/
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

        hh17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    btnAddNewHousehold.setVisibility(View.VISIBLE);
                    btnAddHousehold.setVisibility(View.GONE);

                    if (AppMain.hh07txt.equals("1")) {
                        AppMain.hh07txt = "1";
                    }

                } else {
                    btnAddNewHousehold.setVisibility(View.GONE);
                    setupButtons();

                    if (AppMain.fTotal == 0) {
                        if (AppMain.hh07txt.equals("1")) {
                            AppMain.hh07txt = "1";
                        }
                    }

                }

                txtTeamNoWithFam.setText(String.format("%04d", AppMain.hh03txt) + "-H" + String.format("%03d", Integer.valueOf(AppMain.hh07txt)));
            }
        });

        /*hh08a1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.hh08a1a) {
                    hh08.setText(null);
                    hh08.setEnabled(true);

                    hh09.setText(null);
                    hh09.setEnabled(true);

                    hh16.setText(null);
                    hh16.setEnabled(true);

                    hh10.clearCheck();
                    hh10a.setEnabled(true);
                    hh10b.setEnabled(true);

                    hh14.clearCheck();
                    hh14a.setEnabled(true);
                    hh14b.setEnabled(true);

                } else {
                    hh08.setText(null);
                    hh08.setEnabled(false);

                    hh09.setText(null);
                    hh09.setEnabled(false);

                    hh16.setText(null);
                    hh16.setEnabled(false);

                    hh10.clearCheck();
                    hh10a.setEnabled(false);
                    hh10b.setEnabled(false);

                    hh14.clearCheck();
                    hh14a.setEnabled(false);
                    hh14b.setEnabled(false);
                }
            }
        });*/

    }

    public void setupButtons() {
        if (AppMain.fCount < AppMain.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);

            hh17.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);

            hh17.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.btnAddNewHousehold)
    void onBtnAddNewHouseHoldClick() {

        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                if (familyFlag) {
                    AppMain.hh07txt = String.valueOf(Integer.valueOf(AppMain.hh07txt) + 1);
                } else {
                    /*if (AppMain.hh07txt.equals("X")) {
                        AppMain.hh07txt = "B";
                    } else {
                        AppMain.hh07txt = String.valueOf((char) (AppMain.hh07txt.charAt(0) + 1));
                    }*/

                    AppMain.hh07txt = String.valueOf(Integer.valueOf(AppMain.hh07txt) + 1);

                    familyFlag = true;
                }
                AppMain.lc.setHh07(AppMain.hh07txt);
                AppMain.fCount++;

                finish();
                Intent fA = new Intent(this, FamilyListingActivity.class);
                startActivity(fA);
            }
        }

    }

    private void SaveDraft() throws JSONException {

        AppMain.lc.setHh07(AppMain.hh07txt);
        AppMain.lc.setHh08a1("1");
        AppMain.lc.setHh08(hh08.getText().toString());
        AppMain.lc.setHh09(hh09.getText().toString());
        AppMain.lc.setHh10(hh10a.isChecked() ? "1" : hh10b.isChecked() ? "2" : "0");
        AppMain.lc.setHh11(hh11.getText().toString().isEmpty() ? "0" : hh11.getText().toString());
        AppMain.lc.setHh12(hh12a.isChecked() ? "1" : hh12b.isChecked() ? "2" : "0");
        AppMain.lc.setHh13(hh13.getText().toString().isEmpty() ? "0" : hh13.getText().toString());
        AppMain.lc.setHh14(hh14a.isChecked() ? "1" : hh14b.isChecked() ? "2" : "0");
        AppMain.lc.setHh15(hh15.getText().toString().isEmpty() ? "0" : hh15.getText().toString());
        AppMain.lc.setHh16(hh16.getText().toString());
        AppMain.lc.setIsNewHH(hh17.isChecked() ? "1" : "2");

        JSONObject sB = new JSONObject();
        sB.put("hh18", hh18.getText().toString());
        sB.put("hh19", hh19.getText().toString());
        sB.put("hh20", hh20.getText().toString());
        sB.put("hh21", hh21.getText().toString());
        sB.put("hh22", hh22.getText().toString());
        sB.put("hh23", hh23.getText().toString());
        sB.put("hh24", hh24.getText().toString());
        sB.put("hh25", hh25.getText().toString());
        sB.put("hh26", hh26.getText().toString());

        JSONObject sMerge = JsonUtils.mergeJSONObjects(setupActivity.IMEI, sB);

        AppMain.lc.setCounter(String.valueOf(sMerge));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03());

    }

    private boolean formValidation() {

        //if (hh08a1a.isChecked()) {

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


        if (!validatorClass.EmptyRadioButton(this, hh10, hh10a, getString(R.string.hh10))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, hh10, hh10a, hh11, "Adolescents count")) {
            return false;
        }
        if (hh10a.isChecked()) {
            if (!validatorClass.RangeTextBox(this, hh11, 1, 99, getString(R.string.hh10), "for Adolescents")) {
                return false;
            }
        }
/*
        if (hh10a.isChecked() && hh11.getText().toString().isEmpty()) {
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
        }*/

        if (false) {
            if (!validatorClass.EmptyRadioButton(this, hh12, hh12a, getString(R.string.hh12))) {
                return false;
            }
            if (!validatorClass.EmptyRadioButton(this, hh12, hh12a, hh13, "Children count")) {
                return false;
            }
            if (hh12a.isChecked()) {
                if (!validatorClass.RangeTextBox(this, hh13, 1, 99, getString(R.string.hh12), "for Childrens")) {
                    return false;
                }
            }
        }

        /*if (hh12a.isChecked() && hh13.getText().toString().isEmpty()) {
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
        }*/


        if (!validatorClass.EmptyRadioButton(this, hh14, hh14a, getString(R.string.hh14))) {
            return false;
        }
        if (!validatorClass.EmptyRadioButton(this, hh14, hh14a, hh15, "Married Woman count")) {
            return false;
        }
        if (hh14a.isChecked()) {
            if (!validatorClass.RangeTextBox(this, hh15, 1, 99, getString(R.string.hh14), "for Married Woman")) {
                return false;
            }
        }

        /*if (hh14a.isChecked() && hh15.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter married woman count", Toast.LENGTH_SHORT).show();
            hh14b.setError("Please enter married woman count");
            Log.i(TAG, "Please enter married woman count");
            return false;
        } else {
            hh14b.setError(null);
        }

        if (!hh15.getText().toString().isEmpty() && Integer.valueOf(hh15.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_SHORT).show();
            hh15.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hh15.setError(null);
        }*/

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


        if (!validatorClass.EmptyTextBox(this, hh18, getString(R.string.hh18))) {
            return false;
        }

        /*if (!validatorClass.RangeTextBox(this, hh18, (hh10a.isChecked() ? Integer.valueOf(hh11.getText().toString()) : 0), 99, getString(R.string.hh18), "")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh19, getString(R.string.hh19))) {
            return false;
        }

        /*if (!validatorClass.RangeTextBox(this, hh19, 0, 99, getString(R.string.hh19), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh20, getString(R.string.hh20))) {
            return false;
        }

        /*if (!validatorClass.RangeTextBox(this, hh20, 0, 99, getString(R.string.hh20), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh21, getString(R.string.hh21))) {
            return false;
        }

        /*if (!validatorClass.RangeTextBox(this, hh21, 0, 99, getString(R.string.hh21), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh22, getString(R.string.hh22))) {
            return false;
        }

       /* if (!validatorClass.RangeTextBox(this, hh22, 0, 99, getString(R.string.hh22), "Deaths")) {
            return false;
        }*/

        if (!validatorClass.EmptyTextBox(this, hh24, getString(R.string.hh24))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh24, 0, 9, getString(R.string.hh24), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh25, getString(R.string.hh25))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh25, 0, 9, getString(R.string.hh25), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh26, getString(R.string.hh26))) {
            return false;
        }

        if (!validatorClass.RangeTextBox(this, hh26, 0, 9, getString(R.string.hh26), "Deaths")) {
            return false;
        }

        if (!validatorClass.EmptyTextBox(this, hh23, getString(R.string.hh23))) {
            return false;
        }

        return validatorClass.RangeTextBox(this, hh23, 0, 99, getString(R.string.hh23), "Deaths");
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
                /*AppMain.cCount = 0;
                AppMain.cTotal = 0;*/
                AppMain.hh07txt = String.valueOf(Integer.valueOf(AppMain.hh07txt) + 1);
                AppMain.lc.setHh07(AppMain.hh07txt);
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
                familyFlag = false;
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
