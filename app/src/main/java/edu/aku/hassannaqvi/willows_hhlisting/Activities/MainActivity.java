package edu.aku.hassannaqvi.willows_hhlisting.Activities;

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
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.DistrictsContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.PSUsContract;
import edu.aku.hassannaqvi.willows_hhlisting.Core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.willows_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.willows_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.willows_hhlisting.Get.GetDistricts;
import edu.aku.hassannaqvi.willows_hhlisting.Get.GetPSUs;
import edu.aku.hassannaqvi.willows_hhlisting.Get.GetUsers;
import edu.aku.hassannaqvi.willows_hhlisting.Get.GetVertices;
import edu.aku.hassannaqvi.willows_hhlisting.R;
import edu.aku.hassannaqvi.willows_hhlisting.Sync.SyncAreas;
import edu.aku.hassannaqvi.willows_hhlisting.Sync.SyncListing;
import edu.aku.hassannaqvi.willows_hhlisting.Sync.SyncMWRAs;

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

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    FormsDBHelper db;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AppMain.lc = null;

        /*Tag Info Start*/
        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        editor = sharedPref.edit();

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

        spinnersFill();

    }

    public void spinnersFill() {
        // Spinner Drop down elements
        List<String> districtNames = new ArrayList<String>();
        final Map<String, String> district = new HashMap<>();
        Collection<DistrictsContract> dc = db.getAllDistricts();

        districtNames.add("....");

        Log.d(TAG, "onCreate: " + dc.size());
        for (DistrictsContract d : dc) {
            district.put(d.getDistrictName(), d.getDistrictCode());
            districtNames.add(d.getDistrictName());
//            districtCodes.add(d.getDistrictCode());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, districtNames);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mN01.setAdapter(dataAdapter);

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                psuCode = new ArrayList<>();
                psuCode.add("....");

                if (mN01.getSelectedItemPosition() != 0) {

                    AppMain.hh01txt = district.get(mN01.getSelectedItem().toString());

                    Collection<PSUsContract> pc = db.getAllPSUsByDistrict(AppMain.hh01txt);
                    for (PSUsContract p : pc) {
                        psuCode.add(p.getPSUCode());
                    }
                }

                ArrayAdapter<String> psuAdapter = new ArrayAdapter<String>(MainActivity.this,
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
                Collection<PSUsContract> pc = db.getAllPSUsByDistrict(AppMain.hh01txt);
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
        });
    }

    public void alertPSU() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent oF = new Intent(MainActivity.this, setupActivity.class);
//                        oF.putExtra("new", false);
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
        if (mN01.getSelectedItemPosition() != 0 && mN02.getSelectedItemPosition() != 0) {

            if (AppMain.PSUExist(AppMain.hh02txt)) {
                Toast.makeText(MainActivity.this, "Cluster data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                Intent oF = new Intent(this, ClusterListingActivity.class);
                oF.putExtra("new", true);
                startActivity(oF);
            }
        } else {
            Toast.makeText(this, "Select values from spinners.", Toast.LENGTH_SHORT).show();
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

                    GetDistricts gd = new GetDistricts(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing UCs", Toast.LENGTH_SHORT).show();
                    gd.execute();

                    GetPSUs gp = new GetPSUs(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Psus", Toast.LENGTH_SHORT).show();
                    gp.execute();

                    Toast.makeText(getApplicationContext(), "Syncing Listing", Toast.LENGTH_SHORT).show();
                    ff.execute();

                    Toast.makeText(getApplicationContext(), "Syncing MWRAs", Toast.LENGTH_SHORT).show();
                    new SyncMWRAs(mContext).execute();

                    Toast.makeText(getApplicationContext(), "Syncing Areas", Toast.LENGTH_SHORT).show();
                    new SyncAreas(mContext).execute();

                    GetUsers gu = new GetUsers(mContext);
                    Toast.makeText(getApplicationContext(), "Syncing Users", Toast.LENGTH_SHORT).show();
                    gu.execute();

                    Toast.makeText(getApplicationContext(), "Syncing Vertices", Toast.LENGTH_SHORT).show();
                    new GetVertices(mContext).execute();

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
