package edu.aku.hassannaqvi.kmc_hhlisting2.ui;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.hassannaqvi.kmc_hhlisting2.R;
import edu.aku.hassannaqvi.kmc_hhlisting2.contract.DistrictsContract;
import edu.aku.hassannaqvi.kmc_hhlisting2.contract.VillagesContract;
import edu.aku.hassannaqvi.kmc_hhlisting2.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.kmc_hhlisting2.core.AppMain;
import edu.aku.hassannaqvi.kmc_hhlisting2.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc_hhlisting2.get.GetDistricts;
import edu.aku.hassannaqvi.kmc_hhlisting2.get.GetUsers;
import edu.aku.hassannaqvi.kmc_hhlisting2.get.GetVillages;
import edu.aku.hassannaqvi.kmc_hhlisting2.sync.SyncListing;
import edu.aku.hassannaqvi.kmc_hhlisting2.sync.SyncMwras;

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

    FormsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppMain.lc = null;

        // database handler
        db = new FormsDBHelper(getApplicationContext());

        populateSpinner(this);
    }

    public void populateSpinner(final Context context) {
        // Spinner Drop down elements
        List<String> districtNames = new ArrayList<>();
        final List<String> districtCodes = new ArrayList<>();
        Collection<DistrictsContract> dc = db.getAllDistricts();
        Log.d(TAG, "onCreate: " + dc.size());
        for (DistrictsContract d : dc) {
            districtNames.add(d.getDistrictName());
            districtCodes.add(d.getDistrictCode());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, districtNames);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mN01.setAdapter(dataAdapter);

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh01txt = districtCodes.get(position);

                psuCode = new ArrayList<>();
                Collection<VillagesContract> pc = db.getAllPSUsByDistrict(districtCodes.get(position));
                for (VillagesContract p : pc) {
                    psuCode.add(p.getVillageCode());
                    Collections.sort(psuCode);
                }
                ArrayAdapter<String> psuAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_item, psuCode);

                psuAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mN02.setAdapter(psuAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh02txt = psuCode.get(position);
                Collection<VillagesContract> pc = db.getAllPSUsByDistrict(AppMain.hh01txt);
                for (VillagesContract p : pc) {
                    Log.d(TAG, "onItemSelected: " + p.getVillageCode() + " -" + AppMain.hh02txt);

                    if (p.getVillageCode().equals(AppMain.hh02txt)) {
                        Log.d(TAG, "onItemSelected: " + p.getVillageName());
                        String[] psuNameS = p.getVillageName().toString().split("\\|");
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
        });
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


        if (mN01.getSelectedItem() != null && mN02.getSelectedItem() != null) {

            Intent oF = new Intent(this, setupActivity.class);

            if (AppMain.PSUExist(AppMain.hh02txt)) {
                Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                startActivity(oF);
            }
        } else {

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

                    Toast.makeText(getApplicationContext(), "Syncing Mwras", Toast.LENGTH_SHORT).show();
                    new SyncMwras(mContext).execute();

                    GetUsers u = new GetUsers(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Users", Toast.LENGTH_SHORT).show();
                    u.execute();

                    GetDistricts gd = new GetDistricts(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Districts", Toast.LENGTH_SHORT).show();
                    gd.execute();

                    GetVillages gp = new GetVillages(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing UCs", Toast.LENGTH_SHORT).show();
                    gp.execute();
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
}
