package edu.aku.hassannaqvi.kmc2_linelisting.Activities;

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
import edu.aku.hassannaqvi.kmc2_linelisting.R;
import edu.aku.hassannaqvi.kmc2_linelisting.Sync.SyncAllData;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.ListingContract;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.PregnancyContract;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.TalukasContract;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.UCsContract;
import edu.aku.hassannaqvi.kmc2_linelisting.contracts.VillagesContract;
import edu.aku.hassannaqvi.kmc2_linelisting.core.AndroidDatabaseManager;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

public class MainActivity extends Activity {

    public static String TAG = "MainActivity";

    private static String ipAddress = "192.168.1.10";
    private static String port = "3000";

    public List<String> psuCode;

    ArrayList<String> lablesTalukas;
    Collection<TalukasContract> TalukasList;
    Map<String, String> talukasMap;

    ArrayList<String> lablesUCs;
    Collection<UCsContract> UcsList;
    Map<String, String> ucsMap;

    //ArrayList<String> lablesAreas;
    //Collection<AreasContract> AreasList;
    //Map<String, String> AreasMap;

    ArrayList<String> lablesVillages;
    Collection<VillagesContract> VillagesList;
    Map<String, VillagesContract> villagesMap;

    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    @BindView(R.id.MN00)
    Spinner mN00;
    @BindView(R.id.MN01)
    Spinner mN01;
    @BindView(R.id.MN03)
    Spinner mN03;
    @BindView(R.id.talukaN)
    TextView talukaN;
    @BindView(R.id.ucN)
    TextView ucN;
    @BindView(R.id.villagesN)
    TextView villageN;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder builder;
    String m_Text = "";
    private Boolean exit = false;
    FormsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainApp.lc = null;

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

        spinnersFill(this);

    }

    public void spinnersFill(Context context) {
        final Context mContext = context;

        // Populate Talukas list
        TalukasList = db.getAllTalukas();

        lablesTalukas = new ArrayList<>();
        talukasMap = new HashMap<>();

        lablesTalukas.add("Select Taluka..");

        for (TalukasContract taluka : TalukasList) {
            lablesTalukas.add(taluka.getTaluka());

            talukasMap.put(taluka.getTaluka(), taluka.getTalukacode());
        }

        mN00.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, lablesTalukas));

        mN00.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Populate UCs list

                if (mN00.getSelectedItemPosition() != 0) {
                    MainApp.talukaCode = Integer.valueOf(talukasMap.get(mN00.getSelectedItem().toString()));

                    talukaN.setText("Taluka: " + mN00.getSelectedItem().toString());
                } else {
                    MainApp.talukaCode = 0;
                }

                lablesUCs = new ArrayList<>();
                ucsMap = new HashMap<>();

                lablesUCs.add("Select UC..");

                UcsList = db.getAllUCs(String.valueOf(MainApp.talukaCode));
                for (UCsContract ucs : UcsList) {
                    lablesUCs.add(ucs.getUcs());
                    ucsMap.put(ucs.getUcs(), ucs.getUccode());
                }

                mN01.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, lablesUCs));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mN01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (mN01.getSelectedItemPosition() != 0) {
                    MainApp.ucCode = Integer.valueOf(ucsMap.get(mN01.getSelectedItem().toString()));

                    ucN.setText("UC: " + mN01.getSelectedItem().toString());
                } else {
                    MainApp.ucCode = 0;
                }

                lablesVillages = new ArrayList<>();
                villagesMap = new HashMap<>();

                lablesVillages.add("Select Village..");

                VillagesList = db.getAllVillage(String.valueOf(MainApp.ucCode));
                for (VillagesContract villages : VillagesList) {
                    lablesVillages.add(villages.getVillagename());
                    villagesMap.put(villages.getVillagename(), new VillagesContract(villages));
                }

                mN03.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, lablesVillages));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*mN02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (mN02.getSelectedItemPosition() != 0) {
                    MainApp.areaCode = Integer.valueOf(AreasMap.get(mN02.getSelectedItem().toString()));
                } else {
                    MainApp.areaCode = 0;
                }

                lablesVillages = new ArrayList<>();
                villagesMap = new HashMap<>();

                lablesVillages.add("Select Village Code..");

                VillagesList = db.getAllVillage(String.valueOf(MainApp.areaCode));
                for (VillagesContract villages : VillagesList) {
                    lablesVillages.add(villages.getVillagename());
                    villagesMap.put(villages.getVillagename(), new VillagesContract(villages));
                }

                areaN.setText(mN02.getSelectedItem().toString());

                mN03.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, lablesVillages));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        mN03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mN03.getSelectedItemPosition() != 0) {
                    MainApp.villageCode = villagesMap.get(mN03.getSelectedItem().toString()).getVillagecode();
                    MainApp.villageName = mN03.getSelectedItem().toString();
                    villageN.setText("Village: " + MainApp.villageName + " | " + villagesMap.get(mN03.getSelectedItem().toString()).getVillagecode());
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

        if (sharedPref.getString("tagName", null) != "" && sharedPref.getString("tagName", null) != null) {
            NextSetupActivity(setupActivity.class);
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

                        NextSetupActivity(setupActivity.class);
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

    public void openMap(View view) {

        if (sharedPref.getString("tagName", null) != "" && sharedPref.getString("tagName", null) != null) {
            NextSetupActivity(MapsActivity.class);
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

                        NextSetupActivity(MapsActivity.class);
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

    public void NextSetupActivity(Class activity) {
        Intent oF = new Intent(this, activity);
        if (mN00.getSelectedItemPosition() != 0 && mN01.getSelectedItemPosition() != 0 && mN03.getSelectedItemPosition() != 0) {

            if (activity.getName().equals(MapsActivity.class.getName())) {
                startActivity(oF);
                return;
            }

            if (MainApp.PSUExist(MainApp.villageCode)) {
                Toast.makeText(MainActivity.this, "PSU data exist!", Toast.LENGTH_LONG).show();
                alertPSU();
            } else {
                startActivity(oF);
            }

        } else {
            Toast.makeText(this, "Please Sync Data or select values from drop down!", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getApplicationContext(), "Syncing Listing", Toast.LENGTH_SHORT).show();
                    new SyncAllData(
                            mContext,
                            "Listing",
                            "updateSyncedForms",
                            ListingContract.class,
                            MainApp._HOST_URL + ListingContract.ListingEntry._URL,
                            db.getAllListings()
                    ).execute();

                    Toast.makeText(getApplicationContext(), "Syncing Pregnancy", Toast.LENGTH_SHORT).show();
                    new SyncAllData(
                            mContext,
                            "Pregnancy",
                            "updateSyncedPregnancy",
                            PregnancyContract.class,
                            MainApp._HOST_URL + PregnancyContract.singlePREG._URL,
                            db.getAllPregnancy()
                    ).execute();

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    spinnersFill(mContext);
                }
            }, 1200);
        }
    }


}
