package edu.aku.hassannaqvi.cbt_hhlisting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import edu.aku.hassannaqvi.cbt_hhlisting.sync.SyncChildren;
import edu.aku.hassannaqvi.cbt_hhlisting.sync.SyncClusterInfo;
import edu.aku.hassannaqvi.cbt_hhlisting.sync.SyncListings;
import edu.aku.hassannaqvi.cbt_hhlisting.sync.SyncPW;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";

    public List<String> lhwName;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.MN01)
    Spinner mN01;
    @BindView(R.id.MN02)
    Spinner mN02;
    @BindView(R.id.MN03)
    Spinner mN03;
//    @BindView(R.id.ucN)
//    TextView ucN;
//    @BindView(R.id.psuN)
//    TextView psuN;

    Map<String,String> tehsils,lhws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppMain.lc = null;

        // database handler
        final FormsDBHelper db = new FormsDBHelper(getApplicationContext());

        // Spinner Drop down elements
        tehsils = new HashMap<>();
        final List<String> Tname = new ArrayList<>();
        Collection<TehsilsContract> Tc = db.getAllTehsil();
        Log.d(TAG, "onCreate: " + Tc.size());
        for (TehsilsContract hf : Tc) {
            tehsils.put(hf.getTehsil_name(),hf.getTehsil_code());
            Tname.add(hf.getTehsil_name());
        }

        mN01.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,Tname));

        final List<String> hfCodes =new ArrayList<>();

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Spinner Drop down elements
                List<String> hfNames = new ArrayList<>();

                AppMain.tehsilCode = tehsils.get(Tname.get(position));

                Collection<HFacilitiesContract> hfc = db.getAllHFacilitiesByTehsil(AppMain.tehsilCode);
                Log.d(TAG, "onCreate: " + hfc.size());
                for (HFacilitiesContract hf : hfc) {
                    hfNames.add(hf.gethFacilityName());
                    hfCodes.add(hf.gethFacilityCode());
                }

                // attaching data adapter to spinner
                mN02.setAdapter(new ArrayAdapter<>(getBaseContext(),
                        android.R.layout.simple_spinner_dropdown_item, hfNames));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh01txt = hfCodes.get(position);

                lhwName = new ArrayList<String>();
                lhws = new HashMap<String, String>();
                Collection<LHWsContract> lhwc = db.getAllLhwsByHf(hfCodes.get(position));
                for (LHWsContract lhw : lhwc) {
                    lhws.put(""+(lhw.getLHWName()+" ("+lhw.getLHWCode()+")"),lhw.getLHWCode());
                    lhwName.add(lhw.getLHWName()+" ("+lhw.getLHWCode()+")");
                }
                ArrayAdapter<String> psuAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, lhwName);

                psuAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mN03.setAdapter(psuAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mN03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppMain.hh02txt = lhws.get(lhwName.get(position));
                /*Collection<LHWsContract> lhwc = db.getAllLhwsByHf(AppMain.hh01txt);
                for (LHWsContract l : lhwc) {
                    Log.d(TAG, "onItemSelected: " + l.getLHWCode() + " -" + AppMain.hh02txt);

                    if (l.getLHWCode().equals(AppMain.hh02txt)) {
                        Log.d(TAG, "onItemSelected: " + l.getLHWName());
                        String[] psuNameS = l.getLHWName().toString().split("\\|");
                        districtN.setText(psuNameS[0]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[0]);
                        ucN.setText(psuNameS[1]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[1]);
                        psuN.setText(psuNameS[2]);
                        Log.d(TAG, "onItemSelected: " + psuNameS[2]);

                    }
                }*/
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


        if (mN02.getSelectedItem() != null && mN03.getSelectedItem() != null) {

//            Intent oF = new Intent(this, setupActivity.class);

            if (AppMain.PSUExist(AppMain.hh02txt)) {
                Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                startActivity(new Intent(this,LHWActivity.class));
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

            SyncListings ff = new SyncListings(this);
            Toast.makeText(getApplicationContext(), "Syncing Listing", Toast.LENGTH_SHORT).show();
            ff.execute();

            SyncPW pm = new SyncPW(this);
            Toast.makeText(getApplicationContext(), "Syncing PW", Toast.LENGTH_SHORT).show();
            pm.execute();

            SyncChildren cc = new SyncChildren(this);
            Toast.makeText(getApplicationContext(), "Syncing Children", Toast.LENGTH_SHORT).show();
            cc.execute();

            SyncClusterInfo ci = new SyncClusterInfo(this);
            Toast.makeText(getApplicationContext(), "Syncing ClusterInfo", Toast.LENGTH_SHORT).show();
            ci.execute();

            GetUsers u = new GetUsers(this);
            Toast.makeText(getApplicationContext(), "Syncing Users", Toast.LENGTH_SHORT).show();
            u.execute();

            GetTehsil gt = new GetTehsil(this);
            Toast.makeText(getApplicationContext(), "Syncing Tehsils", Toast.LENGTH_SHORT).show();
            gt.execute();

            GetVillages gv = new GetVillages(this);
            Toast.makeText(getApplicationContext(), "Syncing Villages", Toast.LENGTH_SHORT).show();
            gv.execute();

            GetUCs gu = new GetUCs(this);
            Toast.makeText(getApplicationContext(), "Syncing Ucs", Toast.LENGTH_SHORT).show();
            gu.execute();

            GetHFacilities gh = new GetHFacilities(this);
            Toast.makeText(getApplicationContext(), "Syncing Health Facilities", Toast.LENGTH_SHORT).show();
            gh.execute();

            GetLHWs gp = new GetLHWs(this);
            Toast.makeText(getApplicationContext(), "Syncing LHWs", Toast.LENGTH_SHORT).show();
            gp.execute();

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastSyncDB", dtToday);

            editor.apply();

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(), "Back Button NOT Allowed!", Toast.LENGTH_SHORT).show();
//
//    }
}
