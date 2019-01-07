package edu.aku.hassannaqvi.toic_hhlisting.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.toic_hhlisting.Contracts.PSUsContract;
import edu.aku.hassannaqvi.toic_hhlisting.Contracts.TalukasContract;
import edu.aku.hassannaqvi.toic_hhlisting.Core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.toic_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.toic_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.toic_hhlisting.R;
import edu.aku.hassannaqvi.toic_hhlisting.Sync.SyncListing;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";

    public List<String> psuCode;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.MN01)
    Spinner mN01;
    @BindView(R.id.MN02)
    Spinner mN02;
    @BindView(R.id.districtN)
    TextView districtN;
    @BindView(R.id.ucN)
    TextView ucN;
    @BindView(R.id.psuN)
    TextView psuN;
    @BindView(R.id.txtPSU)
    EditText txtPSU;
    @BindView(R.id.btnCheckPSU)
    Button btnCheckPSU;
    @BindView(R.id.msgText)
    TextView msgText;

    @BindView(R.id.testing)
    TextView testing;
    @BindView(R.id.openDBManager)
    Button openDBManager;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    Boolean exit = false;
    Boolean flag = false;
    FormsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppMain.lc = null;

        /*Tag Info Start*/
        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        editor = sharedPref.edit();

        // Checking Admin
        if (!AppMain.admin)
            openDBManager.setVisibility(View.GONE);

        builder = new AlertDialog.Builder(MainActivity.this);
        ImageView img = new ImageView(getApplicationContext());
        img.setImageResource(R.drawable.tagimg);
        img.setPadding(0, 15, 0, 15);
        builder.setCustomTitle(img);

        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if (!m_Text.equals("")) {
                    editor.putString("tagName", m_Text);
                    editor.commit();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (sharedPref.getString("tagName", null) == "" || sharedPref.getString("tagName", null) == null) {
            builder.show();
        }
        /*Tag End*/


        // database handler
        db = new FormsDBHelper(getApplicationContext());
        String[] summary = db.getListingCount();

        msgText.setText(
                new StringBuilder()

                        .append("RECORDS' SUMMARY\r\n")
                        .append("\r\n")
                        .append("Listings Table:\r\n")
                        .append("---------------\r\n")
                        .append(summary[0]).append(" records found\r\n")
                        .append(summary[1]).append(" records synced\r\n")
                        .append(summary[2]).append(" records not synced\r\n")
                        .toString()

        );
        spinnersFill();

//        Testing visibility
        if (Integer.valueOf(AppMain.versionName.split("\\.")[0]) > 0) {
            testing.setVisibility(View.GONE);
        } else {
            testing.setVisibility(View.VISIBLE);
        }

    }

    public void spinnersFill() {
        // Spinner Drop down elements
        List<String> talukaNames = new ArrayList<String>();
        final Map<String, String> taluka = new HashMap<>();
        Collection<TalukasContract> dc = db.getAllTalukas();

        talukaNames.add("....");

        Log.d(TAG, "onCreate: " + dc.size());
        for (TalukasContract d : dc) {
            taluka.put(d.getTalukaName(), d.getTalukaCode());
            talukaNames.add(d.getTalukaName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, talukaNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mN01.setAdapter(dataAdapter);

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*psuCode = new ArrayList<>();
                psuCode.add("....");

                if (mN01.getSelectedItemPosition() != 0) {

                    AppMain.hh01txt = taluka.get(mN01.getSelectedItem().toString());

                    Collection<PSUsContract> pc = db.getAllPSUsByTaluka(AppMain.hh01txt);
                    for (PSUsContract p : pc) {
                        psuCode.add(p.getPSUCode());
                    }
                }

                ArrayAdapter<String> psuAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, psuCode);

                psuAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mN02.setAdapter(psuAdapter);*/

                if (mN01.getSelectedItemPosition() != 0) {

                    AppMain.hh01txt = taluka.get(mN01.getSelectedItem().toString());

                    txtPSU.setEnabled(true);
                    btnCheckPSU.setEnabled(true);
                } else {
                    txtPSU.setText(null);
                    txtPSU.setEnabled(false);
                    btnCheckPSU.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtPSU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                flag = false;

                districtN.setText(null);
                psuN.setText(null);
                ucN.setText(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

/*
        mN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh02txt = psuCode.get(position);
                Collection<PSUsContract> pc = db.getAllPSUsByTaluka(AppMain.hh01txt);
                for (PSUsContract p : pc) {
                    Log.d(TAG, "onItemSelected: " + p.getPSUCode() + " -" + AppMain.hh02txt);

                    if (p.getPSUCode().equals(AppMain.hh02txt)) {
                        Log.d(TAG, "onItemSelected: " + p.getPSUName());
                        String[] psuNameS = p.getPSUName().toString().split("\\|");
                        districtN.setText(psuNameS[0]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[0]);
                        ucN.setText(psuNameS[1]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[1]);
                        psuN.setText(psuNameS[2]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[2]);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

    }

    @OnClick(R.id.btnCheckPSU)
    void onCheckPSUClick() {
        //TODO implement

        if (!txtPSU.getText().toString().trim().isEmpty()) {

            txtPSU.setError(null);

            Collection<PSUsContract> pc = db.getAllPSUsByTaluka(AppMain.hh01txt, txtPSU.getText().toString());
            if (pc.size() > 0) {

                flag = true;

                for (PSUsContract p : pc) {

                    AppMain.hh02txt = p.getPSUCode();

                    Log.d(TAG, "onItemSelected: " + p.getPSUCode() + " - " + AppMain.hh02txt);
                    Log.d(TAG, "onItemSelected: " + p.getPSUName());
                    String[] psuNameS = p.getPSUName().split("\\|");
                    districtN.setText(psuNameS[0]);
                    Log.d(TAG, "onItemSelected: " + psuNameS[0]);
                    ucN.setText(psuNameS[1]);
                    Log.d(TAG, "onItemSelected: " + psuNameS[1]);
                    psuN.setText(psuNameS[2]);
                    Log.d(TAG, "onItemSelected: " + psuNameS[2]);
                }
            } else {
                Toast.makeText(this, "Not found!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            txtPSU.setError("Data required!!");
            txtPSU.setFocusable(true);
        }
    }

    public void alertPSU() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent oF = new Intent(MainActivity.this, setupActivity.class);
                        startActivity(oF);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to continue?");
        builder.setMessage("PSU data already exist.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public void openForm(View view) {

        if (sharedPref.getString("tagName", null) != "" && sharedPref.getString("tagName", null) != null) {
            NextSetupActivity();
        } else {

            builder = new AlertDialog.Builder(MainActivity.this);
            ImageView img = new ImageView(getApplicationContext());
            img.setImageResource(R.drawable.tagimg);
            img.setPadding(0, 15, 0, 15);
            builder.setCustomTitle(img);

            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    if (!m_Text.equals("")) {
                        editor.putString("tagName", m_Text);
                        editor.commit();

                        NextSetupActivity();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
    }

    public void NextSetupActivity() {
        Intent oF = new Intent(this, setupActivity.class);
        if (mN01.getSelectedItemPosition() != 0) {

            if (flag) {
                if (!AppMain.teamNo.equals("")) {
                    if (AppMain.PSUExist(AppMain.hh02txt)) {
                        Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                        alertPSU();
                    } else {
                        startActivity(oF);
                    }
                } else {
                    Toast.makeText(this, "Login with different username!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Click on CHECK button!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please Sync Data!", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDB(View view) {
        Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    public void syncFunction(View view) {
        if (isNetworkAvailable()) {

            new syncData(this).execute();

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastSyncDB", dtToday);

            editor.apply();
        } /*else {
            Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
        }*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void openDBManager(View v) {
        Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    private boolean isHostAvailable() {

        if (isNetworkAvailable()) {
            try {
                SocketAddress sockaddr = new InetSocketAddress(ipAddress, 3000);
                // Create an unbound socket
                Socket sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
                return true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Server Not Available for Update", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {
            Toast.makeText(getApplicationContext(), "Network not available for Update", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity

            startActivity(new Intent(this, LoginActivity.class));

        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    public void openMap(View view) {

        if (flag) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class).putExtra("psu", txtPSU.getText().toString()));
        } else {
            Toast.makeText(this, "Please Click on Check Button", Toast.LENGTH_SHORT).show();
        }

    }

    public class syncData extends AsyncTask<String, String, String> {

        private Context mContext;

        public syncData(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    SyncListing ff = new SyncListing(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Listing", Toast.LENGTH_SHORT).show();
                    ff.execute();
                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    spinnersFill();
                }
            }, 1200);
        }
    }


}
