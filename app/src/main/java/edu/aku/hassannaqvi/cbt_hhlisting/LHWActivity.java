package edu.aku.hassannaqvi.cbt_hhlisting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LHWActivity extends Activity {

    private static final String TAG = LHWActivity.class.getName();
    @BindView(R.id.household_identification)
    ScrollView householdIdentification;
    @BindView(R.id.txtChildListing)
    TextView txtChildListing;
    @BindView(R.id.lhwe)
    EditText lhwe;
    @BindView(R.id.lhwf)
    EditText lhwf;
    @BindView(R.id.lhwg)
    EditText lhwg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhw);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.next)
    void onNextClick() {
        //TODO implement

        if (formValidation()) {

            SaveDraft();
            if (UpdateDB()) {
                startActivity(new Intent(this, setupActivity.class));
            }

        }

    }

    private boolean UpdateDB() {
        /*FormsDBHelper db = new FormsDBHelper(this);
        long updcount = db.addForm(AppMain.lc);

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.lc.setHhChildNm(null);
            AppMain.lc.setHh12d(null);
            AppMain.lc.setHh12m(null);
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }*/
        return true;
    }

    private void SaveDraft() {
        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure ");
    }

    public boolean formValidation() {

        if (lhwe.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwe.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwe: Data req");
            return false;
        } else {
            lhwe.setError(null);
        }

        if (lhwf.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwf.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwf: Data req");
            return false;
        } else {
            lhwf.setError(null);

            if (Integer.parseInt(lhwf.getText().toString()) < 1) {
                Toast.makeText(this, "Invalid(Error):Greater then 0", Toast.LENGTH_LONG).show();
                lhwf.setError("Invalid(Error):Greater then 0");
                Log.i(TAG, "lhwf: Greater then 0");
                return false;
            } else {
                lhwf.setError(null);
            }
        }

        if (lhwg.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid(Error):Data required", Toast.LENGTH_LONG).show();
            lhwg.setError("Invalid(Error):Data required");
            Log.i(TAG, "lhwg: Data req");
            return false;
        } else {
            lhwg.setError(null);

            if (Integer.parseInt(lhwg.getText().toString()) < 1) {
                Toast.makeText(this, "Invalid(Error):Greater then 0", Toast.LENGTH_LONG).show();
                lhwg.setError("Invalid(Error):Greater then 0");
                Log.i(TAG, "lhwg: Greater then 0");
                return false;
            } else {
                lhwg.setError(null);
            }
        }
        return true;
    }

}
