package edu.aku.hassannaqvi.kmc2_linelisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.kmc2_linelisting.R;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.PregnancyContract;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

public class AddPregnancyActivity extends Activity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.activity_add_pregnancy)
    LinearLayout activityAddChild;
    @BindView(R.id.txtChildListing)
    TextView txtChildListing;

    @BindView(R.id.ll_preg)
    LinearLayout ll_preg;

    @BindView(R.id.btnAddFamily)
    Button btnAddFamily;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;

    int cCount = 0;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pregnancy);
        ButterKnife.bind(this);

        this.setTitle("PREGNANCY LISTING");

        txtChildListing.setText("FAMILY : " + MainApp.hh03txt + "-" + MainApp.hh07txt);

        /*if (MainApp.cCount < MainApp.cTotal) {
            btnAddPreg.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddFamily.setVisibility(View.GONE);
        } else if (MainApp.cCount >= MainApp.cTotal && MainApp.fCount < MainApp.fTotal) {
            btnAddFamily.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
            btnAddPreg.setVisibility(View.GONE);
        } else {
            btnAddFamily.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
            btnAddPreg.setVisibility(View.GONE);
        }*/

        cCount = getIntent().getIntExtra("cCount", 0);

        if (MainApp.fCount < MainApp.fTotal) {
            btnAddFamily.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamily.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }

        AddDynamicLayout(cCount);

    }

    public void AddDynamicLayout(int count) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 30, 0, 0);

        for (int i = 1; i <= count; i++) {

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(params);

            TextView txt = new TextView(this);
            txt.setTextAppearance(this, R.style.textView);

            if (Build.VERSION.SDK_INT < 23) {
                txt.setTextAppearance(this, R.style.textView);
            } else {
                txt.setTextAppearance(R.style.textView);
            }

            txt.setText(i + ": " + getString(R.string.hh16));

            EditText editTxt = new EditText(this);
            editTxt.setHint(i + ": " + getString(R.string.hh16));
            editTxt.setLayoutParams(params);
            editTxt.setId(i);
            editTxt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editTxt.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

            ll.addView(txt);
            ll.addView(editTxt);

            ll_preg.addView(ll);

        }

    }

    private boolean UpdateDB(PregnancyContract pg) {
        FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addPregnancy(pg);

        pg.setID(String.valueOf(updcount));

        if (updcount != 0) {
            pg.setUid(pg.getDeviceid() + pg.getID());
            db.updatePregnancyUID(pg);
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void SaveDraft(int count) {

        for (int i = 1; i <= count; i++) {
            PregnancyContract pg = new PregnancyContract();

            pg.setDevicetagid(MainApp.lc.getTagId());
            pg.setHhDT(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
            pg.setUser(MainApp.lc.getUsername());
            pg.setDeviceid(MainApp.lc.getDeviceID());
            pg.setApp_ver(MainApp.lc.getApp_ver());
            pg.setUuid(MainApp.lc.getUID());
            pg.setHh01(MainApp.lc.getHh01());
            pg.setHh02(MainApp.lc.getHh02());
            pg.setHh03(MainApp.lc.getHh03());
            pg.setHh16(((EditText) findViewById(i)).getText().toString());

            flag = UpdateDB(pg);

            if (!flag) {
                return;
            }

        }


        Log.d(TAG, "SaveDraft: Structure " + MainApp.lc.getHh03().toString());
    }

    private boolean formValidation(int count) {

        for (int i = 1; i <= count; i++) {

            EditText txt = findViewById(i);

            if (txt.getText().toString().isEmpty()) {
                Toast.makeText(this, "Error(Empty):" + getString(R.string.hh16), Toast.LENGTH_LONG).show();
                txt.setError("Data required");
                Log.i(TAG, "hh16_" + i + ": This data is required.");
                return false;
            } else {
                txt.setError(null);
            }

            if (Integer.valueOf(txt.getText().toString()) < 1) {
                Toast.makeText(this, "Invalid (Value)" + getString(R.string.hh16), Toast.LENGTH_LONG).show();
                txt.setError("Greater then 0!!");
                Log.i(TAG, "hh16_" + i + ": Greater than 0!!");
                return false;
            } else {
                txt.setError(null);
            }
        }


        return true;
    }

    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        if (formValidation(cCount)) {

            SaveDraft(cCount);

            if (flag) {
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
            } else {
                Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.btnAddHousehold)
    void onBtnAddHouseholdClick() {
        if (formValidation(cCount)) {

            SaveDraft(cCount);

            if (flag) {
                MainApp.fCount = 0;
                MainApp.fTotal = 0;
                Intent fA = new Intent(this, setupActivity.class);
                startActivity(fA);

            } else {
                Toast.makeText(this, "Error in updating DB", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}
