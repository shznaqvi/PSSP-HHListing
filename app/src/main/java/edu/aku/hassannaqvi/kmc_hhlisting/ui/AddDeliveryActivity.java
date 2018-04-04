package edu.aku.hassannaqvi.kmc_hhlisting.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.aku.hassannaqvi.kmc_hhlisting.R;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.DeliveryContract;
import edu.aku.hassannaqvi.kmc_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.kmc_hhlisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc_hhlisting.databinding.ActivityAddDeliveryBinding;
import edu.aku.hassannaqvi.kmc_hhlisting.validation.validatorClass;

public class AddDeliveryActivity extends AppCompatActivity {

    public static String TAG = "ChildListingActivity";

    ActivityAddDeliveryBinding binding;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_delivery);
//        Assigning data to UI binding
        binding.setCallback(this);

//        Setup Date
        binding.mwdl01.setFocusable(false);
        binding.mwdl01.setManager(getSupportFragmentManager());
        binding.mwdl01.setMaxDate(new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis()));
        binding.mwdl01.setMinDate(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - (AppMain.MILLISECONDS_IN_5Years)));

//        Setup Buttons
        AddMarriedWomenActivity.dCount++;
        binding.txtDeliveryListing.setText("Delivery Listing: " + AppMain.hh03txt + "-" + AppMain.hh07txt + " \n(" + AddMarriedWomenActivity.dCount + " of " + AddMarriedWomenActivity.dTotal + ")");

        if (AddMarriedWomenActivity.dCount < AddMarriedWomenActivity.dTotal) {
            binding.btnAddDelivery.setVisibility(View.VISIBLE);
            binding.btnHouseholdInfo.setVisibility(View.GONE);
            binding.btnAddWRA.setVisibility(View.GONE);
        } else if (AddMarriedWomenActivity.dCount == AddMarriedWomenActivity.dTotal && AppMain.mwraCount < AppMain.mwraTotal) {
            binding.btnAddWRA.setVisibility(View.VISIBLE);
            binding.btnHouseholdInfo.setVisibility(View.GONE);
            binding.btnAddDelivery.setVisibility(View.GONE);
        } else {
            binding.btnAddWRA.setVisibility(View.GONE);
            binding.btnHouseholdInfo.setVisibility(View.VISIBLE);
            binding.btnAddDelivery.setVisibility(View.GONE);
        }

    }

    private boolean UpdateDB() {

        //Long rowId;
        FormsDBHelper db = new FormsDBHelper(this);

        Long updcount = db.addDeliveryForm(AppMain.dc);
        AppMain.dc.set_ID(String.valueOf(updcount));

        if (updcount != 0) {

            AppMain.dc.setUID(
                    (AppMain.dc.getDeviceID() + AppMain.dc.get_ID()));
            db.updateFormDeliveryID();

            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {

        AppMain.dc = new DeliveryContract();

//        AppMain.cc.setDevicetagID(AppMain.getTagName(this));
        AppMain.dc.setFormDate(dtToday);
        AppMain.dc.setUser(AppMain.username);
        AppMain.dc.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        AppMain.dc.setAppversion(AppMain.versionName + "." + AppMain.versionCode);
        AppMain.dc.setUUID(AppMain.mwra.getUID());
        AppMain.dc.setd1SerialNo(String.valueOf(AddMarriedWomenActivity.dCount));

        JSONObject sD1 = new JSONObject();
        sD1.put("mwd_villagecode", AppMain.lc.getHh04Village());
        sD1.put("mwd_hhno", AppMain.lc.getHh03() + "-" + AppMain.lc.getHh07());
        sD1.put("mwd_wserial_no", AppMain.mwra.getMWRAID());


        sD1.put("mwdl01", binding.mwdl01.getText().toString());
        sD1.put("mwdl02", binding.mwdl02a.isChecked() ? "1"
                : binding.mwdl02b.isChecked() ? "2"
                : binding.mwdl02c.isChecked() ? "3"
                : binding.mwdl02d.isChecked() ? "4"
                : binding.mwdl02e.isChecked() ? "5"
                : binding.mwdl02f.isChecked() ? "6"
                : "0");

        AppMain.dc.setsD1(String.valueOf(sD1));

        Toast.makeText(this, "Saving Draft... Sudcessful!", Toast.LENGTH_SHORT).show();
    }

    private boolean formValidation() {

//        mwdl01
        if (!validatorClass.EmptyTextBox(this, binding.mwdl01, getString(R.string.mwdl01))) {
            return false;
        }
//        mwdl02
        return validatorClass.EmptyRadioButton(this, binding.mwdl02, binding.mwdl02f, getString(R.string.mwdl02));
    }

    public void onBtnAddDeliveryClick() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                Intent fA = new Intent(this, AddDeliveryActivity.class);
                startActivity(fA);
            }

        }
    }

    public void onBtnAddWRAClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                AppMain.mwraCount++;
                Intent mwraA = new Intent(this, AddMarriedWomenActivity.class);
                startActivity(mwraA);
            }
        }
    }


    public void onBtnHouseholdInfoClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Intent fA = new Intent(this, HouseholdInfoActivity.class);
                startActivity(fA);

            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}
