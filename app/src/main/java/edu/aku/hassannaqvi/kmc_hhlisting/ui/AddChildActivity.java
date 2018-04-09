package edu.aku.hassannaqvi.kmc_hhlisting.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.aku.hassannaqvi.kmc_hhlisting.R;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.ChildContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.MwraContract;
import edu.aku.hassannaqvi.kmc_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.kmc_hhlisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc_hhlisting.databinding.ActivityAddChildBinding;
import edu.aku.hassannaqvi.kmc_hhlisting.validation.validatorClass;

public class AddChildActivity extends AppCompatActivity {

    public static String TAG = "ChildListingActivity";

    ActivityAddChildBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    List<String> childU5;
    Map<String, MwraContract> childMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_child);
//        Assigning data to UI binding
        binding.setCallback(this);

//        Setup Date
        binding.ch03.setManager(getSupportFragmentManager());
        binding.ch03.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
        binding.ch03.setMinDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - (AppMain.MILLISECONDS_IN_5Years)));

        binding.ch04.setManager(getSupportFragmentManager());
        binding.ch04.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));

//        Spinner setup
        childU5 = new ArrayList<>();
        childMap = new HashMap<>();

        childU5.add("....");

        childU5.add("N/A");
        childMap.put("N/A", new MwraContract("00"));

        for (MwraContract fmc : AppMain.mwraList) {
            childMap.put(fmc.getMw01(), fmc);
            childU5.add(fmc.getMw01());
        }

        binding.ch01.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, childU5));

//        Setup Buttons
        AppMain.cCount++;
        binding.txtChildListing.setText("Child Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " \n(" + AppMain.cCount + " of " + AppMain.cTotal + ")");
        if (AppMain.cCount < AppMain.cTotal) {
            binding.btnAddChild.setVisibility(View.VISIBLE);
            binding.btnAddHousehold.setVisibility(View.GONE);
            binding.btnAddFamily.setVisibility(View.GONE);
        } else if (AppMain.cCount >= AppMain.cTotal && AppMain.fCount < AppMain.fTotal) {
            binding.btnAddFamily.setVisibility(View.VISIBLE);
            binding.btnAddHousehold.setVisibility(View.GONE);
            binding.btnAddChild.setVisibility(View.GONE);
        } else {
            binding.btnAddFamily.setVisibility(View.GONE);
            binding.btnAddHousehold.setVisibility(View.VISIBLE);
            binding.btnAddChild.setVisibility(View.GONE);
        }

//        Listener

        binding.ch03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!binding.ch03.getText().toString().isEmpty()) {
                    binding.ch04.setMinDate(AppMain.convertDateFormat(binding.ch03.getText().toString()));
                    Calendar calendar = AppMain.getCalendarDate(binding.ch03.getText().toString());
                    Calendar today = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, 28);
                    if (calendar.after(today)) {
                        binding.ch04.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
                    } else {
                        binding.ch04.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTimeInMillis()));
                    }
                } else {
                    Calendar calendar = AppMain.getCalendarDate(binding.ch03.getText().toString());
                    Calendar today = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH, 28);
                    if (calendar.after(today)) {
                        binding.ch04.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
                    } else {
                        binding.ch04.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTimeInMillis()));
                    }
                    binding.ch04.setMinDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - (AppMain.MILLISECONDS_IN_5Years)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean UpdateDB() {

        //Long rowId;
        FormsDBHelper db = new FormsDBHelper(this);

        Long updcount = db.addChildForm(AppMain.cc);
        AppMain.cc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {

            AppMain.cc.setUID(
                    (AppMain.cc.getDeviceID() + AppMain.cc.get_ID()));
            db.updateFormChildID();

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        AppMain.cc = new ChildContract();

        AppMain.cc.setDevicetagID(AppMain.lc.getDeviceTagID());
        AppMain.cc.setFormDate(AppMain.lc.getHhDT());
        AppMain.cc.setUser(AppMain.lc.getUserName());
        AppMain.cc.setDeviceID(AppMain.lc.getDeviceID());
        AppMain.cc.setAppversion(AppMain.versionName + "." + AppMain.versionCode);

        AppMain.cc.setUUID(AppMain.lc.getUID());
        AppMain.cc.setC1SerialNo(String.valueOf(AppMain.cCount));

        JSONObject sC1 = new JSONObject();

        sC1.put("ch_villageCode",AppMain.lc.getHh04Village());
        sC1.put("ch_hhno",AppMain.lc.getHh03() + "-" + AppMain.lc.getHh07());

        sC1.put("chMw01", childMap.get(binding.ch01.getSelectedItem().toString()).getMw01());
        sC1.put("chMwSerial", childMap.get(binding.ch01.getSelectedItem().toString()).getMWRAID());
        sC1.put("ch02", binding.ch02.getText().toString());
        sC1.put("ch03a", binding.ch03a.isChecked() ? "1" : "2");
        if (!binding.ch03a.isChecked()) {
            sC1.put("ch03", binding.ch03.getText().toString());
        } else {
            sC1.put("ch05", binding.ch05.getText().toString());
        }
        sC1.put("ch04", binding.ch04.getText().toString());
        sC1.put("ch06", binding.ch06.getText().toString());

        AppMain.cc.setsC1(String.valueOf(sC1));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());
    }

    private boolean formValidation() {

//        ch01
        if (!validatorClass.EmptySpinner(this, binding.ch01, getString(R.string.ch01))) {
            return false;
        }
//        ch02
        if (!validatorClass.EmptyTextBox(this, binding.ch02, getString(R.string.ch02))) {
            return false;
        }
//        ch03
        if (!binding.ch03a.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, binding.ch03, getString(R.string.ch03))) {
                return false;
            }
        }
//        ch04
        if (!validatorClass.EmptyTextBox(this, binding.ch04, getString(R.string.ch04))) {
            return false;
        }
//        ch05
        if (binding.ch03a.isChecked()) {
            if (!validatorClass.EmptyTextBox(this, binding.ch05, getString(R.string.ch05))) {
                return false;
            }
            if (!validatorClass.RangeTextBox(this, binding.ch05, 0, 28, getString(R.string.ch05), "")) {
                return false;
            }
        }
//        ch06
        return validatorClass.EmptyTextBox(this, binding.ch06, getString(R.string.ch06));
    }

    public void onBtnAddChildClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                Intent fA = new Intent(this, AddChildActivity.class);
                startActivity(fA);
            }

        }
    }

    public void onBtnAddFamilyClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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


    public void onBtnAddHouseholdClick() {
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
