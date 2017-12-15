package edu.aku.hassannaqvi.willows_hhlisting.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.willows_hhlisting.R;

public class HouseholdListingActivity extends Activity {

    @BindView(R.id.hh03)
    EditText hh03;
    @BindView(R.id.hh04)
    EditText hh04;
    @BindView(R.id.hh06)
    RadioGroup hh06;

    EditText hh06x88;
    @BindView(R.id.hh07)
    EditText hh07;
    @BindView(R.id.hh08)
    EditText hh08;
    @BindView(R.id.hh09)
    Switch hh09;
    @BindView(R.id.hh10)
    EditText hh10;
    @BindView(R.id.hh11)
    Switch hh11;
    @BindView(R.id.btnAddWomen)
    Button btnAddChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_listing);
        ButterKnife.bind(this);


        hh09.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hh10.setVisibility(View.VISIBLE);
                    hh10.requestFocus();
                } else {
                    hh10.setVisibility(View.INVISIBLE);
                    hh10.setText(null);
                }
            }
        });

        hh10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Integer.valueOf(s.toString()) > 0) {
                    btnAddChild.setVisibility(View.VISIBLE);
                } else {
                    btnAddChild.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addChild(View view) {
        Intent aC = new Intent(this, AddWomenActivity.class);
        startActivity(aC);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}
