package edu.aku.hassannaqvi.naunehal_listing.ui;

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
import edu.aku.hassannaqvi.naunehal_listing.R;
import edu.aku.hassannaqvi.naunehal_listing.core.MainApp;
import edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper;

public class FamilyListingActivity extends Activity {

    public static String TAG = "FamilyListingActivity";

    @BindView(R.id.txtFamilyListing)
    TextView txtFamilyListing;
    @BindView(R.id.hl08)
    EditText hl08;
    @BindView(R.id.hl09)
    EditText hl09;
    @BindView(R.id.hl10)
    Switch hl10;
    @BindView(R.id.hl11)
    EditText hl11;
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


        txtFamilyListing.setText("Family Listing: " + MainApp.hl03txt + "-" + MainApp.hl07txt);

        if (MainApp.fCount < MainApp.fTotal) {
            btnAddFamilty.setVisibility(View.VISIBLE);
            btnAddHousehold.setVisibility(View.GONE);
        } else {
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.VISIBLE);
        }

        hl10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hl11.setVisibility(View.VISIBLE);
                    hl11.requestFocus();
                } else {
                    hl11.setVisibility(View.INVISIBLE);
                    hl11.setText(null);
                }
            }
        });

        hl11.addTextChangedListener(new TextWatcher() {
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

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {

        if (formValidation()) {

            SaveDraft();
            MainApp.cTotal = Integer.parseInt(hl11.getText().toString());
            MainApp.cCount++;
            Toast.makeText(this, MainApp.cCount + ":" + MainApp.cTotal + ":" + MainApp.fCount + ":" + MainApp.fTotal, Toast.LENGTH_SHORT).show();
            Intent fA = new Intent(this, AddChildActivity.class);
            startActivity(fA);
        }

    }

    private void SaveDraft() {

        MainApp.listing.setHl08(hl08.getText().toString());
        MainApp.listing.setHl09(hl09.getText().toString());
        MainApp.listing.setHl10(hl10.isChecked() ? "1" : "2");
        MainApp.listing.setHl11(hl11.getText().toString().isEmpty() ? "0" : hl11.getText().toString());
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + MainApp.listing.getHl03());

    }

    private boolean formValidation() {

        if (hl08.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            hl08.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            hl08.setError(null);
        }

        if (hl09.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter contact number", Toast.LENGTH_LONG).show();
            hl09.setError("Please enter contact number");
            Log.i(TAG, "Please enter contact number");
            return false;
        } else {
            hl09.setError(null);
        }

        if (hl10.isChecked() && hl11.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter child count", Toast.LENGTH_LONG).show();
            hl11.setError("Please enter child count");
            Log.i(TAG, "Please enter child count");
            return false;
        } else {
            hl11.setError(null);
        }

        if (!hl11.getText().toString().isEmpty() && Integer.valueOf(hl11.getText().toString()) < 1) {
            Toast.makeText(this, "Invalid Value!", Toast.LENGTH_LONG).show();
            hl11.setError("Invalid Value!");
            Log.i(TAG, "Invalid Value!");
            return false;
        } else {
            hl11.setError(null);
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
                MainApp.hl07txt = String.valueOf((char) (MainApp.hl07txt.charAt(0) + 1));
                MainApp.listing.setHl07(MainApp.hl07txt);
                MainApp.fCount++;

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
                Intent fA = new Intent(this, SetupActivity.class);
                startActivity(fA);

            }
        }

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        long updcount = db.addForm(MainApp.listing);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
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
