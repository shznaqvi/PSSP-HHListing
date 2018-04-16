package edu.aku.hassannaqvi.po_hhlisting.ui;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.po_hhlisting.R;
import edu.aku.hassannaqvi.po_hhlisting.contract.LHWContract;
import edu.aku.hassannaqvi.po_hhlisting.contract.TalukasContract;
import edu.aku.hassannaqvi.po_hhlisting.contract.UCsContract;
import edu.aku.hassannaqvi.po_hhlisting.contract.VillagesContract;
import edu.aku.hassannaqvi.po_hhlisting.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.po_hhlisting.core.AppMain;
import edu.aku.hassannaqvi.po_hhlisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.po_hhlisting.get.GetLHW;
import edu.aku.hassannaqvi.po_hhlisting.get.GetTalukas;
import edu.aku.hassannaqvi.po_hhlisting.get.GetUCs;
import edu.aku.hassannaqvi.po_hhlisting.get.GetUsers;
import edu.aku.hassannaqvi.po_hhlisting.get.GetVillages;
import edu.aku.hassannaqvi.po_hhlisting.sync.SyncListing;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";

    public List<String> psuName, districtNames, villageNames, lhwNames;
    public List<String> psuCode, districtCodes, villageCodes, lhwCodes;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.MN00)
    Spinner mN00;
    @BindView(R.id.MN01)
    Spinner mN01;
    @BindView(R.id.MN02)
    Spinner mN02;

    @BindView(R.id.MN03)
    Spinner MN03;

    @BindView(R.id.districtN)
    TextView districtN;
    @BindView(R.id.ucN)
    TextView ucN;
    @BindView(R.id.psuN)
    TextView psuN;
    @BindView(R.id.villageCode)
    EditText villageCode;

    FormsDBHelper db;
    private Boolean exit = false;

//    Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppMain.lc = null;

        // database handler
        db = new FormsDBHelper(getApplicationContext());

        populateSpinner(this);

/*        villageCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                flag = false;

                districtN.setText(null);
                ucN.setText(null);
                psuN.setText(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

    }

    public void populateSpinner(final Context context) {
        // Spinner Drop down elements
        districtNames = new ArrayList<>();
        districtCodes = new ArrayList<>();

        districtNames.add("....");
        districtCodes.add("....");

        Collection<TalukasContract> dc = db.getAllDistricts();
        Log.d(TAG, "onCreate: " + dc.size());
        for (TalukasContract d : dc) {
            districtNames.add(d.getDistrictName());
            districtCodes.add(d.getDistrictCode());
        }

       /* // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, districtNames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        mN00.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, districtNames));

        // attaching data adapter to spinner
        //mN00.setAdapter(dataAdapter);

        mN00.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh01txt = districtCodes.get(position);

                psuCode = new ArrayList<>();
                psuName = new ArrayList<>();


                psuCode.add("....");
                psuName.add("....");

                Collection<UCsContract> pc = db.getAllUCsbyTaluka(districtCodes.get(position));
                for (UCsContract p : pc) {
                    psuCode.add(p.getUcCode());
                    psuName.add(p.getUcName());
                }

                mN01.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, psuName));

                /*ArrayAdapter<String> psuAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_dropdown_item, psuName);

                psuAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mN01.setAdapter(psuAdapter);*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh02txt = psuCode.get(position);

                villageCodes = new ArrayList<>();
                villageNames = new ArrayList<>();

                lhwCodes = new ArrayList<>();
                lhwNames = new ArrayList<>();

                final List<String> villageNames1 = new ArrayList<>();

                villageCodes.add("....");
                villageNames.add("....");
                villageNames1.add("....");

                Collection<VillagesContract> pc = db.getAllPSUsByDistrict(AppMain.hh01txt, AppMain.hh02txt);
                for (VillagesContract p : pc) {
                    villageCodes.add(p.getVillagecode());
                    villageNames.add(p.getVillagename());
                    //villageNames1.add(p.getVillagename().split("\\|")[2]);
                }

                mN02.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, villageNames));


                lhwCodes.add("....");
                lhwNames.add("....");

                Collection<LHWContract> lhw = db.getAllLHWsByDistrict(AppMain.hh01txt, AppMain.hh02txt);
                for (LHWContract p : lhw) {
                    lhwCodes.add(p.getLhwcode());
                    lhwNames.add(p.getLhwname());
                    //villageNames1.add(p.getVillagename().split("\\|")[2]);
                }

                MN03.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, lhwNames));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        MN03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (MN03.getSelectedItemPosition() != 0) {
                    AppMain.lhwCode = lhwCodes.get(i);
                    AppMain.lhwName = lhwNames.get(i);

                    //String[] st = villageNames.get(i).split("\\|");

                    /*districtN.setText(AppMain.hh01txt);
                    ucN.setText(AppMain.hh02txt);
                    psuN.setText(AppMain.lhwName);*/

                    //districtN.setText(st[0]);
                    //ucN.setText(st[1]);
                    //psuN.setText(st[2]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (mN02.getSelectedItemPosition() != 0) {
                    AppMain.hh04txt = villageCodes.get(i);
                    AppMain.villageCode = villageCodes.get(i);
                    AppMain.villageName = villageNames.get(i);

                    //String[] st = villageNames.get(i).split("\\|");

                    //districtN.setText(st[0]);
                    //ucN.setText(st[1]);
                    //psuN.setText(st[2]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

/*
    @OnClick(R.id.checkVillage)
    void onCheckVillageClick() {
        //TODO implement

        if (!villageCode.getText().toString().isEmpty()) {

            Collection<VillagesContract> pc = db.getAllPSUsByDistrict(AppMain.hh01txt, AppMain.hh02txt,villageCode.getText().toString());

            if (pc.size() > 0) {

                Toast.makeText(this, "Village found", Toast.LENGTH_SHORT).show();

                for (VillagesContract p : pc) {

                    AppMain.hh04txt = p.getVillageCode();
                    String[] st = p.getVillageName().split("\\|");

                    districtN.setText(st[0]);
                    ucN.setText(st[1]);
                    psuN.setText(st[2]);
                }

                flag = true;

            } else {

                flag = false;

                districtN.setText(null);
                ucN.setText(null);
                psuN.setText(null);

                Toast.makeText(this, "Not found village", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Fill village code", Toast.LENGTH_SHORT).show();
        }

    }*/

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


        if (mN01.getSelectedItemPosition() != 0 && mN02.getSelectedItemPosition() != 0 && mN02.getSelectedItemPosition() != 0) {

            Intent oF = new Intent(this, setupActivity.class);

            if (AppMain.PSUExist(AppMain.hh04txt)) {
                Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                startActivity(oF);
            }
        } else {
            Toast.makeText(this, "Select values from dropdown", Toast.LENGTH_SHORT).show();
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
        }
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

                    Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();
                    new SyncListing(mContext).execute();

                    /*Toast.makeText(getApplicationContext(), "Syncing Mwras", Toast.LENGTH_SHORT).show();
                    new SyncMwras(mContext).execute();*/

                    GetUsers u = new GetUsers(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Users", Toast.LENGTH_SHORT).show();
                    u.execute();

                    GetLHW lhw = new GetLHW(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing LHWs", Toast.LENGTH_SHORT).show();
                    lhw.execute();

                    GetVillages gp = new GetVillages(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Villages", Toast.LENGTH_SHORT).show();
                    gp.execute();

                    GetUCs gu = new GetUCs(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing UCs", Toast.LENGTH_SHORT).show();
                    gu.execute();

                    GetTalukas gd = new GetTalukas(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Talukas", Toast.LENGTH_SHORT).show();
                    gd.execute();


                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    populateSpinner(mContext);
                }
            }, 1200);
        }
    }


    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity

            Intent ii = new Intent(this, LoginActivity.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ii);
            finish();

//            startActivity(new Intent(this, LoginActivity.class));

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

}
