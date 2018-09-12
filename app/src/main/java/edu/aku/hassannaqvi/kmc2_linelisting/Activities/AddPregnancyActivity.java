package edu.aku.hassannaqvi.kmc2_linelisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.kmc2_linelisting.R;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

public class AddPregnancyActivity extends Activity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.activity_add_pregnancy)
    LinearLayout activityAddChild;
    @BindView(R.id.txtChildListing)
    TextView txtChildListing;


    @BindView(R.id.btnAddPregnancy)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pregnancy);
        ButterKnife.bind(this);
        txtChildListing.setText("Child Listing: " + MainApp.hh03txt + "-" + MainApp.hh07txt + " (" + MainApp.cCount + " of " + MainApp.cTotal + ")");
        if (MainApp.cCount < MainApp.cTotal) {
            btnAddChild.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddFamilty.setVisibility(View.GONE);
        } else if (MainApp.cCount >= MainApp.cTotal && MainApp.fCount < MainApp.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddChild.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
            btnAddChild.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btnAddPregnancy)
    void onBtnAddChildClick() {
        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                MainApp.cCount++;
                Intent fA = new Intent(this, AddPregnancyActivity.class);
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
            MainApp.lc.setHh13(null);
            MainApp.lc.setHh12(null);

            MainApp.lc.setUID(
                    (MainApp.lc.getDeviceID() + MainApp.lc.getID()));

            db.updateListingUID();
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft() {
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + MainApp.lc.getHh03().toString());
    }

    private boolean formValidation() {


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

                Intent fA = new Intent(this, FamilyListingActivity.class);
                startActivity(fA);
                try {
                    Log.d(TAG, "onBtnAddFamilyClick: " + MainApp.lc.toJSONObject().toString());
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
                MainApp.fCount = 0;
                MainApp.fTotal = 0;
                MainApp.cCount = 0;
                MainApp.cTotal = 0;
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
