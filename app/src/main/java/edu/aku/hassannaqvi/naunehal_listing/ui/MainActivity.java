package edu.aku.hassannaqvi.naunehal_listing.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.naunehal_listing.R;
import edu.aku.hassannaqvi.naunehal_listing.contracts.ClustersContract;
import edu.aku.hassannaqvi.naunehal_listing.contracts.DistrictsContract;
import edu.aku.hassannaqvi.naunehal_listing.contracts.UCsContract;
import edu.aku.hassannaqvi.naunehal_listing.core.MainApp;
import edu.aku.hassannaqvi.naunehal_listing.core.coreutils.AndroidDatabaseManager;
import edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.naunehal_listing.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";
    public List<String> ucCode;
    ActivityMainBinding bi;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle(R.string.app_name);
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setVm(this);

        MainApp.listing = null;

        // database handler
        db = new DatabaseHelper(getApplicationContext());


       /* // Spinner Drop down elements
        List<String> districtNames = new ArrayList<String>();
        final List<String> districtCodes = new ArrayList<String>();
        Collection<DistrictsContract> dc = db.getAllDistricts();
        Log.d(TAG, "onCreate: " + dc.size());
        for (DistrictsContract d : dc) {
            districtNames.add(d.getDistrictName());
            districtCodes.add(d.getDistrictCode());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, MainApp.districts);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        bi.MN01.setAdapter(dataAdapter);

        bi.MN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainApp.hl01txt = districtCodes.get(position);

                ucCode = new ArrayList<String>();
                Collection<UCsContract> uc = db.getAllUCByDistrict(districtCodes.get(position));
                for (UCsContract u : uc) {
                    ucCode.add(u.getUcCode());
                }
                ArrayAdapter<String> ucAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, ucCode);

                ucAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bi.MN02.setAdapter(ucAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bi.MN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainApp.hl02txt = ucCode.get(position);
                Collection<ClustersContract> pc = db.getAllClustersByDistrict(MainApp.hl01txt);
                for (ClustersContract p : pc) {
                    Log.d(TAG, "onItemSelected: " + p.getClusterCode() + " -" + MainApp.hl02txt);

                    if (p.getClusterCode().equals(MainApp.hl02txt)) {
                        Log.d(TAG, "onItemSelected: " + p.getClustername());
                        String[] clusterNameS = p.getClustername().toString().split("\\|");
                        bi.districtN.setText(clusterNameS[0]);
                        Log.d(TAG, "onItemSelected: " + clusterNameS[0]);
                        bi.ucN.setText(clusterNameS[1]);
                        Log.d(TAG, "onItemSelected: " + clusterNameS[1]);
                        bi.clusterN.setText(clusterNameS[2]);
                        Log.d(TAG, "onItemSelected: " + clusterNameS[2]);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        populateSpinner();
    }

    public void alertCluster() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent oF = new Intent(MainActivity.this, SetupActivity.class);
                        startActivity(oF);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }

        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to continue?");
        builder.setMessage("Cluster data already exist.").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public void openForm(View view) {

        Intent oF = new Intent(this, SetupActivity.class);

        if (MainApp.ClusterExist(MainApp.clusterCode)) {
            Toast.makeText(MainActivity.this, "Cluster data exist!", Toast.LENGTH_LONG).show();
            alertCluster();
        } else {
            startActivity(oF);
        }
    }

    public void openDB(View view) {
        Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    public void syncFunction(View view) {

        startActivity(new Intent(this, SyncActivity.class));

        if (isNetworkAvailable()) {


          /*  SyncForms ff = new SyncForms(this);
            Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();
            ff.execute();


            GetDistricts gd = new GetDistricts(this);
            Toast.makeText(getApplicationContext(), "Syncing Districts", Toast.LENGTH_SHORT).show();
            gd.execute();
            GetClusters gp = new GetClusters(this);
            Toast.makeText(getApplicationContext(), "Syncing Clusters", Toast.LENGTH_SHORT).show();
            gp.execute();


            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastSyncDB", dtToday);

            editor.apply();*/
        } /*else {
            Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
        }*/
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Log.d(TAG, "isConnected: ");
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void openDBManager(View v) {
        Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


    public void populateSpinner() {
        // Spinner Drop down elements

        List<String> districtCode = new ArrayList<>();
        List<String> districtName = new ArrayList<>();

        districtCode.add("...");
        districtName.add("....");

        Collection<DistrictsContract> dc = db.getAllDistricts();
        for (DistrictsContract d : dc) {
            districtCode.add(d.getDistrictCode());
            districtName.add(d.getDistrictName());
        }
        ArrayAdapter<String> DistrictAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, districtName);

        DistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bi.hl01.setAdapter(DistrictAdapter);


        bi.hl01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MainApp.districtsCode = districtCode.get(position);
                List<String> ucCode = new ArrayList<>();
                List<String> ucName = new ArrayList<>();

                ucCode.add("...");
                ucName.add("...");

                Collection<UCsContract> uc = db.getAllUCByDistrict(districtCode.get(position));
                for (UCsContract u : uc) {
                    ucCode.add(u.getUcCode());
                    ucName.add(u.getUcName());
                }
                ArrayAdapter<String> ucAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, ucName);

                ucAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bi.hl02.setAdapter(ucAdapter);


                bi.hl02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        MainApp.ucCode = ucCode.get(position);
                        List<String> clusterCode = new ArrayList<>();
                        Collection<ClustersContract> clusters = db.getAllClustersByUC(ucCode.get(position));
                        if (clusters.size() < 1) {
                            Toast.makeText(MainActivity.this, "No Clusters Found.", Toast.LENGTH_SHORT).show();
                        }
                        for (ClustersContract cluster : clusters) {

                            clusterCode.add(cluster.getClusterCode());
                            Log.d(TAG, "onItemSelected: Cluster: " + cluster.getClusterCode());
                            // ucName.add(cluster.getUcName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, clusterCode);
                        bi.hl03.setAdapter(adapter);
                        bi.hl03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                MainApp.clusterCode = bi.hl03.getText().toString();

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
