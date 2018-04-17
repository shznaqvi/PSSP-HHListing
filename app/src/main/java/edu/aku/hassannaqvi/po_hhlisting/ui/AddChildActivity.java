package edu.aku.hassannaqvi.po_hhlisting.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.po_hhlisting.R;
import edu.aku.hassannaqvi.po_hhlisting.contract.ChildContract;
import edu.aku.hassannaqvi.po_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.po_hhlisting.core.FormsDBHelper;
import io.blackbox_vision.datetimepickeredittext.view.DatePickerInputEditText;

public class AddChildActivity extends AppCompatActivity {

    public static String TAG = "ChildListingActivity";


    @BindView(R.id.activity_add_child)
    ScrollView activityAddChild;

    @BindView(R.id.txtChildListing)
    TextView txtChildListing;


    @BindView(R.id.ch06)
    EditText ch06;

    @BindView(R.id.ch07)
    EditText ch07;

    @BindView(R.id.ch08)
    RadioGroup ch08;
    @BindView(R.id.ch08a)
    RadioButton ch08a;
    @BindView(R.id.ch08b)
    RadioButton ch08b;

    @BindView(R.id.ch09)
    DatePickerInputEditText ch09;

    @BindView(R.id.ch0999)
    CheckBox ch0999;

    @BindView(R.id.ch10d)
    EditText ch10d;

    @BindView(R.id.ch10m)
    EditText ch10m;

    @BindView(R.id.ch10y)
    EditText ch10y;

    @BindView(R.id.ch11)
    RadioGroup ch11;

    @BindView(R.id.ch11a)
    RadioButton ch11a;
    @BindView(R.id.ch11b)
    RadioButton ch11b;
    @BindView(R.id.ch11c)
    RadioButton ch11c;
    @BindView(R.id.ch11d)
    RadioButton ch11d;
    @BindView(R.id.ch11e)
    RadioButton ch11e;
    @BindView(R.id.ch11f)
    RadioButton ch11f;
    @BindView(R.id.ch11g)
    RadioButton ch11g;
    @BindView(R.id.ch11h)
    RadioButton ch11h;

    @BindView(R.id.ch1188)
    EditText ch1188;


    @BindView(R.id.txtCounter)
    TextView txtCounter;

    @BindView(R.id.txtCounter1)
    TextView txtCounter1;

    @BindView(R.id.btnAddChild)
    Button btnAddChild;
    @BindView(R.id.btnAddFamily)
    Button btnAddFamilty;
    @BindView(R.id.btnAddHousehold)
    Button btnAddHousehold;


    static int count_2 = 1;
    static int count_59 = 1;
    static int additionalCount = 0;

    static int total2Months = 0;
    static int total59Months = 0;
    static Boolean flag59 = false;
    Boolean flag2 = false;


    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        ButterKnife.bind(this);

        String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        String minDate2years = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTimeInMillis() - ((AppMain.MILLISECONDS_IN_2_YEARS) + (AppMain.MILLISECONDS_IN_DAY)));


        ch09.setManager(getSupportFragmentManager());
        ch09.setMaxDate(dateToday);
        ch09.setMinDate(minDate2years);


        total2Months = Integer.valueOf(AppMain.cCount2m);
        total59Months = Integer.valueOf(AppMain.cCount59m);


        txtCounter.setText("Child 0 to 59 months");
        txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months);


        btnAddChild.setVisibility(View.VISIBLE);
        //btnAddHousehold.setVisibility(View.VISIBLE);
        //btnAddFamilty.setVisibility(View.VISIBLE);

        ch11.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (ch11h.isChecked()) {
                    ch1188.setVisibility(View.VISIBLE);
                } else {
                    ch1188.setText(null);
                    ch1188.setVisibility(View.GONE);
                }
            }
        });


        ch0999.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (ch0999.isChecked()) {
                    ch09.setText(null);
                    ch09.setVisibility(View.GONE);

                    ch10d.setVisibility(View.VISIBLE);
                    ch10m.setVisibility(View.VISIBLE);
                    ch10y.setVisibility(View.VISIBLE);
                } else {
                    ch09.setVisibility(View.VISIBLE);

                    ch10d.setText(null);
                    ch10m.setText(null);
                    ch10y.setText(null);

                    ch10d.setVisibility(View.GONE);
                    ch10m.setVisibility(View.GONE);
                    ch10y.setVisibility(View.GONE);
                }
            }
        });


        if (AppMain.cCount2m < AddChildActivity.count_2) {

            if (AppMain.fTotal == 1) {
                btnAddHousehold.setVisibility(View.VISIBLE);
                btnAddFamilty.setVisibility(View.GONE);
            } else {

                if (AppMain.fTotal > AppMain.fCount) {
                    btnAddHousehold.setVisibility(View.GONE);
                    btnAddFamilty.setVisibility(View.VISIBLE);
                } else {
                    btnAddHousehold.setVisibility(View.VISIBLE);
                    btnAddFamilty.setVisibility(View.GONE);
                    btnAddChild.setVisibility(View.VISIBLE);
                }
            }


            txtCounter.setText("Child 0 to 59 months");

            if (AddChildActivity.count_2 == AppMain.cCount2m) {
                txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months);
            }


            if (AddChildActivity.count_2 > AppMain.cCount2m) {
                additionalCount++;
                txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months + " - (" + additionalCount + ")");
            }


            /*AppMain.cCount2m++;
            AddChildActivity.count_2++;*/


        } else {

            btnAddChild.setVisibility(View.VISIBLE);
            btnAddFamilty.setVisibility(View.GONE);
            btnAddHousehold.setVisibility(View.GONE);

            /*txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months);
            AddChildActivity.count_2++;            */
        }


    }

    @OnClick(R.id.btnAddChild)
    void onBtnAddChildClick() {
        if (formValidation()) {

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (UpdateDB()) {


                if (AppMain.cCount2m < AddChildActivity.count_2) {

                    if (AppMain.fTotal == 1) {
                        btnAddHousehold.setVisibility(View.VISIBLE);
                        btnAddFamilty.setVisibility(View.GONE);

                        //IsChildExists();

                    } else {

                        if (AppMain.fTotal > AppMain.fCount) {
                            btnAddHousehold.setVisibility(View.GONE);
                            btnAddFamilty.setVisibility(View.VISIBLE);
                        } else {
                            btnAddHousehold.setVisibility(View.VISIBLE);
                            btnAddFamilty.setVisibility(View.GONE);
                            btnAddChild.setVisibility(View.VISIBLE);
                        }


                        //IsChildExists();

                    }


                    AddChildActivity.count_2++;


                    txtCounter.setText("Child 0 to 59 months");

                    if (AddChildActivity.count_2 == AppMain.cCount2m) {
                        txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months);
                    }


                    //AppMain.cCount2m++;


                    /*ClearFields();
                    activityAddChild.setScrollY(0);
                    ch06.requestFocus();*/

                    startActivity(new Intent(this, AddChildActivity.class));

                } else {

                    AddChildActivity.count_2++;

                    txtCounter1.setText(AddChildActivity.count_2 + " out of " + total2Months);

                    /*ClearFields();
                    activityAddChild.setScrollY(0);
                    ch06.requestFocus();*/

                    startActivity(new Intent(this, AddChildActivity.class));
                }

            }

        }
    }


    private boolean UpdateDB() {
        FormsDBHelper db = new FormsDBHelper(this);

        long updcount = db.addChild(AppMain.childContract);

        AppMain.childContract.setID(String.valueOf(updcount));

        if (updcount != 0) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            AppMain.childContract.setUID(AppMain.lc.getDeviceID() + AppMain.childContract.getID());
            db.updateChild();
            //AppMain.lc.setHh04Village(null);
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }


        //return true;
    }

    private void SaveDraft() throws JSONException {

        //AppMain.lc.setHh04Village(ch06.getText().toString());

        AppMain.childContract = new ChildContract();

        /*cc.setChDT(dtToday);
        cc.setCHILDID(String.valueOf(count_2));
        cc.setCh01(AppMain.hh01txt);
        cc.setCh02(AppMain.hh02txt);
        cc.setCh03(String.valueOf(AppMain.hh03txt));
        cc.setCh04(AppMain.hh04txt);
        cc.setCh05(AppMain.lhwCode);

        cc.setUID(AppMain.lc.getDeviceID() + AppMain.lc.getID());
        cc.setUUID(AppMain.lc.getUID());
        cc.setHhno(AppMain.hhno);*/


        AppMain.childContract.chDT = dtToday;
        AppMain.childContract.CHILDID = String.valueOf(count_2);

        AppMain.childContract.ch01 = AppMain.hh01txt;
        AppMain.childContract.ch02 = AppMain.hh02txt;
        AppMain.childContract.ch03 = String.valueOf(AppMain.hh03txt);
        AppMain.childContract.ch04 = AppMain.hh04txt;
        AppMain.childContract.ch05 = AppMain.lhwCode;

        AppMain.childContract.UUID = AppMain.lc.getUID();
        AppMain.childContract.hhno = AppMain.hhno;
        AppMain.childContract.deviceID = AppMain.lc.getDeviceID();


        JSONObject js = new JSONObject();

        js.put("ch06", ch06.getText().toString());
        js.put("ch07", ch07.getText().toString());
        js.put("ch08", ch08a.isChecked() ? "1" : ch08b.isChecked() ? "2" : "0");
        js.put("ch09", ch09.getText().toString());
        js.put("ch10d", ch10d.getText().toString());
        js.put("ch10m", ch10m.getText().toString());
        js.put("ch10y", ch10y.getText().toString());
        js.put("ch11", ch11a.isChecked() ? "1"
                : ch11b.isChecked() ? "2"
                : ch11c.isChecked() ? "3"
                : ch11d.isChecked() ? "4"
                : ch11e.isChecked() ? "5"
                : ch11f.isChecked() ? "6"
                : ch11g.isChecked() ? "7"
                : ch11h.isChecked() ? "8"
                : "0");

        js.put("ch1188", ch1188.getText().toString());


        //cc.setChild(String.valueOf(js));

        AppMain.childContract.setChild(String.valueOf(js));

        Toast.makeText(this, "Saving Draft... Successful!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SaveDraft: Structure " + AppMain.lc.getHh03().toString());
    }

    private boolean formValidation() {

        if (ch06.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            ch06.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            ch06.setError(null);
        }


        if (ch07.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            ch07.setError("Please enter name");
            Log.i(TAG, "Please enter name");
            return false;
        } else {
            ch07.setError(null);
        }


        if (ch08.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.ch08), Toast.LENGTH_SHORT).show();
            ch08b.setError("This Data is required");
            Log.d(TAG, "crg01 : This Data is required");
            return false;
        } else {
            ch08b.setError(null);
        }


        if (!ch0999.isChecked()) {

            if (ch09.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter date of birth", Toast.LENGTH_LONG).show();
                ch09.setError("Please enter date of birth");
                Log.i(TAG, "Please enter date of birth");
                return false;
            } else {
                ch09.setError(null);
            }
        } else {

            if (ch10d.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter days", Toast.LENGTH_LONG).show();
                ch10d.setError("Please enter days");
                Log.i(TAG, "Please enter days");
                return false;
            } else {
                ch10d.setError(null);
            }


            if (Integer.valueOf(ch10d.getText().toString()) < 0 || Integer.valueOf(ch10d.getText().toString()) > 29) {
                Toast.makeText(this, "Days range is 0 - 29", Toast.LENGTH_LONG).show();
                ch10d.setError("Days range is 0 - 29");
                Log.i(TAG, "Days range is 0 - 29");
                return false;
            } else {
                ch10d.setError(null);
            }


            if (ch10m.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter month", Toast.LENGTH_LONG).show();
                ch10m.setError("Please enter month");
                Log.i(TAG, "Please enter month");
                return false;
            } else {
                ch10m.setError(null);
            }


            if (Integer.valueOf(ch10m.getText().toString()) < 0 || Integer.valueOf(ch10m.getText().toString()) > 11) {
                Toast.makeText(this, "Month range is 0 - 11", Toast.LENGTH_LONG).show();
                ch10m.setError("Month range is 0 - 11");
                Log.i(TAG, "Month range is 0 - 11");
                return false;
            } else {
                ch10m.setError(null);
            }


            if (ch10y.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter year", Toast.LENGTH_LONG).show();
                ch10y.setError("Please enter year");
                Log.i(TAG, "Please enter year");
                return false;
            } else {
                ch10y.setError(null);
            }


            if (Integer.valueOf(ch10y.getText().toString()) < 0 || Integer.valueOf(ch10y.getText().toString()) > 4) {
                Toast.makeText(this, "Year range is 0 - 4", Toast.LENGTH_LONG).show();
                ch10y.setError("Year range is 0 - 4");
                Log.i(TAG, "Year range is 0 - 4");
                return false;
            } else {
                ch10y.setError(null);
            }

        }


        if (ch11.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Empty)" + getString(R.string.ch11), Toast.LENGTH_SHORT).show();
            ch11h.setError("This Data is required");
            Log.d(TAG, "crg01 : This Data is required");
            return false;
        } else {
            ch11h.setError(null);
        }


        if (ch11h.isChecked()) {

            if (ch1188.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter other", Toast.LENGTH_LONG).show();
                ch1188.setError("Please enter other");
                Log.i(TAG, "Please enter other");
                return false;
            } else {
                ch1188.setError(null);
            }
        }


        return true;
    }


    @OnClick(R.id.btnAddFamily)
    void onBtnAddFamilyClick() {
        //if (formValidation()) {

        /*try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        //if (UpdateDB()) {

        AppMain.cCount = 0;
        AppMain.cTotal = 0;
        AddChildActivity.count_2 = 1;
        additionalCount = 0;

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

        //}
        //}
    }


    public void IsChildExists() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent oF = new Intent(AddChildActivity.this, setupActivity.class);
                        startActivity(oF);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to add more child ?");
        builder.setMessage("Add More Child").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }


    @OnClick(R.id.btnAddHousehold)
    void onBtnAddHouseholdClick() {
        //if (formValidation()) {

        if (AppMain.cCount2m > count_2) {
            Toast.makeText(getApplicationContext(), "Please Insert the data of the remaining children", Toast.LENGTH_SHORT).show();
            return;
        }

            /*try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        //if (UpdateDB()) {

        if (AppMain.cCount2m > count_2) {
        } else {
            AppMain.fCount = 0;
            AppMain.fTotal = 0;
            AppMain.cCount = 0;
            AppMain.cTotal = 0;
            AppMain.cCount2m = 0;


            //Intent fA = new Intent(this, setupActivity.class);
            Intent childActivity = new Intent(this, setupActivity.class);
            startActivity(childActivity);
        }

        //}
        //}
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();

    }
}