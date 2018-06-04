package edu.aku.hassannaqvi.nnspak_hhlisting.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.BLRandomContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.EnumBlockContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.ListingContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.VerticesContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.nnspak_hhlisting.Core.FormsDBHelper;
import edu.aku.hassannaqvi.nnspak_hhlisting.R;
import edu.aku.hassannaqvi.nnspak_hhlisting.Sync.SyncAllData;
import edu.aku.hassannaqvi.nnspak_hhlisting.Sync.SyncListing;

public class MainActivity extends MenuActivity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";

    public List<String> psuCode;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.districtN)
    TextView districtN;
    @BindView(R.id.ucN)
    TextView ucN;
    @BindView(R.id.psuN)
    TextView psuN;
    @BindView(R.id.msgUpdate)
    TextView msgUpdate;
    @BindView(R.id.msgText)
    TextView msgText;
    @BindView(R.id.txtPSU)
    EditText txtPSU;
    @BindView(R.id.btnCheckPSU)
    Button btnCheckPSU;
    @BindView(R.id.chkconfirm)
    CheckBox chkconfirm;
    @BindView(R.id.openForm)
    Button openForm;

    @BindView(R.id.na101a)
    TextView na101a;
    @BindView(R.id.na101b)
    TextView na101b;
    @BindView(R.id.na101c)
    TextView na101c;
    @BindView(R.id.na101d)
    TextView na101d;
    @BindView(R.id.na101e)
    TextView na101e;

    @BindView(R.id.fldGrpna101)
    LinearLayout fldGrpna101;
    @BindView(R.id.adminBlock)
    LinearLayout adminBlock;


    SharedPreferences sharedPref;
    SharedPreferences sharedPrefInfo;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    Boolean exit = false;
    Boolean flag = false;
    FormsDBHelper db;
    ProgressDialog progressDoalog;
    private String clusterName;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dbManager = menu.findItem(R.id.menu_openDB);
//        MenuItem randomization = menu.findItem(R.id.menu_randomization);

        if (AppMain.admin) {
            dbManager.setVisible(true);
//            randomization.setVisible(true);
        } else {
            dbManager.setVisible(false);
//            randomization.setVisible(false);
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (AppMain.admin) {
            adminBlock.setVisibility(View.VISIBLE);
        } else {
            adminBlock.setVisibility(View.GONE);
        }
        AppMain.lc = null;

        /*Tag Info Start*/
        sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);
        sharedPrefInfo = getSharedPreferences("info", MODE_PRIVATE);
        editor = sharedPref.edit();

        String versionCode = sharedPrefInfo.getString("versionCode", "");
        if (versionCode.equals("")) {


        } else {
            if (Integer.valueOf(versionCode) > Integer.valueOf(AppMain.versionCode)) {
                msgUpdate.setVisibility(View.VISIBLE);
            }
        }
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

        msgText.setText(db.getListingCount() + " records found in Listings table.");
        spinnersFill();

    }

    public void spinnersFill() {
        // Spinner Drop down elements
        //List<String> teamNos = new ArrayList<String>();
        //Collection<TeamsContract> dc = db.getAllTeams();

        //teamNos.add("....");

        //Log.d(TAG, "onCreate: " + dc.size());
        /*for (TeamsContract d : dc) {
            teamNos.add(d.getTeamNo());
        }*/

        // Creating adapter for spinner
        /*mN01.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teamNos));

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mN01.getSelectedItemPosition() != 0) {

                    AppMain.hh01txt = Integer.valueOf(mN01.getSelectedItem().toString());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        txtPSU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                districtN.setText(null);
                psuN.setText(null);
                ucN.setText(null);
                fldGrpna101.setVisibility(View.GONE);
                openForm.setVisibility(View.GONE);
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

        if (!txtPSU.getText().toString().isEmpty()) {

            txtPSU.setError(null);

            FormsDBHelper db = new FormsDBHelper(this);
            EnumBlockContract enumBlockContract = db.getEnumBlock(txtPSU.getText().toString());
            if (enumBlockContract != null) {
                String selected = enumBlockContract.getGeoarea();

                if (!selected.equals("")) {

                    String[] selSplit = selected.split("\\|");

                    na101a.setText(selSplit[0]);
                    na101b.setText(selSplit[1].equals("") ? "----" : selSplit[1]);
                    na101c.setText(selSplit[2].equals("") ? "----" : selSplit[2]);
                    na101d.setText(selSplit[3]);
                    clusterName = selSplit[3];
                    na101e.setText(enumBlockContract.getEbcode());

                    fldGrpna101.setVisibility(View.VISIBLE);

                    flag = true;
                    chkconfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (chkconfirm.isChecked()) {
                                openForm.setBackgroundColor(getResources().getColor(R.color.green));
                                openForm.setVisibility(View.VISIBLE);
                                AppMain.hh01txt = 1;
                            } else {
                                openForm.setVisibility(View.GONE);

                            }
                        }
                    });

                    AppMain.hh02txt = txtPSU.getText().toString();
                    AppMain.enumCode = enumBlockContract.getEbcode();
                    AppMain.enumStr = enumBlockContract.getGeoarea();
                    AppMain.clusterCode = txtPSU.getText().toString();
                }
            } else {
                Toast.makeText(this, "Sorry not found any block", Toast.LENGTH_SHORT).show();
                flag = false;
                openForm.setVisibility(View.GONE);
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

    public void openClusterMap(View view) {

        FormsDBHelper db = new FormsDBHelper(this);
        Collection<VerticesContract> v = db.getVerticesByCluster(txtPSU.getText().toString());
        if (v.size() > 3) {
            startActivity(new Intent(this, MapsActivity.class));
        } else {
            Toast.makeText(this, "Cluster map do not exist for " + clusterName, Toast.LENGTH_SHORT).show();
        }
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

        if (flag) {
            //if (mN01.getSelectedItemPosition() != 0) {
            if (AppMain.PSUExist(AppMain.hh02txt)) {
                Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                startActivity(new Intent(this, setupActivity.class));
            }
            /*} else {
                Toast.makeText(this, "Please select Team from dropdown!", Toast.LENGTH_SHORT).show();
            }*/
        } else {
            Toast.makeText(this, "Please Click on CHECK button!", Toast.LENGTH_SHORT).show();
        }

    }

    public void openDB(View view) {
        Intent dbmanager = new Intent(this, AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }

    public void copyData(View view) {
        new CopyTask(this).execute();
    }

    public void syncFunction(View view) {
        if (isNetworkAvailable()) {

//            new syncData(this).execute();

            Toast.makeText(getApplicationContext(), "Syncing Listing", Toast.LENGTH_SHORT).show();
            new SyncAllData(
                    this,
                    "Listing",
                    "updateSyncedForms",
                    ListingContract.class,
                    AppMain._HOST_URL + ListingContract.ListingEntry._URL,
                    db.getAllListings()
            ).execute();

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastSyncDB", dtToday);

            editor.apply();
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

            Intent ii = new Intent(this, LoginActivity.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ii);
            finish();

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

    public class CopyTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog Asycdialog;
        Context mContext;

        public CopyTask(Context mContext) {
            this.mContext = mContext;
            Asycdialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setTitle("COPYING DATA");
            Asycdialog.setMessage("Loading...");
            Asycdialog.setCancelable(false);
            Asycdialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // do the task you want to do. This will be executed in background.
            try {

                File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID)
                        + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                        + "_" + ListingContract.ListingEntry.TABLE_NAME);
                FileWriter writer = new FileWriter(gpxfile);

                Collection<ListingContract> listing = db.getAllListings();
                if (listing.size() > 0) {
                    JSONArray jsonSync = new JSONArray();
                    for (ListingContract fc : listing) {
                        jsonSync.put(fc.toJSONObject());
                    }

                    writer.append(String.valueOf(jsonSync));
                    writer.flush();
                    writer.close();

                    if (listing.size() < 100) {
                        Thread.sleep(3000);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            Asycdialog.dismiss();
            Toast.makeText(mContext, "Copying done!!", Toast.LENGTH_SHORT).show();
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