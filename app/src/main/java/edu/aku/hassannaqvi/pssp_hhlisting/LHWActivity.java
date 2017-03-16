package edu.aku.hassannaqvi.pssp_hhlisting;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LHWActivity extends Activity  {

    @BindView(R.id.household_identification) LinearLayout householdIdentification;
    @BindView(R.id.txtChildListing) TextView txtChildListing;
    @BindView(R.id.lhwa) Spinner lhwa;
    @BindView(R.id.lhwb) Spinner lhwb;
    @BindView(R.id.lhwc) Spinner lhwc;
    @BindView(R.id.lhwd) EditText lhwd;
    @BindView(R.id.lhwe) EditText lhwe;
    @BindView(R.id.lhwf) EditText lhwf;
    @BindView(R.id.lhwg) EditText lhwg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhw);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.next) void onNextClick() {
        //TODO implement
    }


    public boolean formValidation(){



        return true;
    }

}
