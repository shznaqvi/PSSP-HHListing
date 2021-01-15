package edu.aku.hassannaqvi.naunehal_listing.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.naunehal_listing.CONSTANTS;
import edu.aku.hassannaqvi.naunehal_listing.R;
import edu.aku.hassannaqvi.naunehal_listing.adapters.SyncListAdapter;
import edu.aku.hassannaqvi.naunehal_listing.contracts.ClustersContract.TableClusters;
import edu.aku.hassannaqvi.naunehal_listing.contracts.DistrictsContract.TableDistricts;
import edu.aku.hassannaqvi.naunehal_listing.contracts.UCsContract.TableUCs;
import edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.naunehal_listing.databinding.ActivitySyncBinding;
import edu.aku.hassannaqvi.naunehal_listing.models.SyncModel;
import edu.aku.hassannaqvi.naunehal_listing.workers.DataDownWorkerALL;

import static edu.aku.hassannaqvi.naunehal_listing.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper.DATABASE_NAME;


public class SyncActivity extends AppCompatActivity {
    private static final String TAG = "SyncActivity";
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    String DirectoryName;
    DatabaseHelper db;
    SyncListAdapter syncListAdapter;
    ActivitySyncBinding bi;
    List<SyncModel> uploadTables;
    List<SyncModel> downloadTables;
    Boolean listActivityCreated;
    Boolean uploadlistActivityCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_sync);
        bi.setCallback(this);
        uploadTables = new ArrayList<>();
        downloadTables = new ArrayList<>();

        // Set tables to DOWNLOAD
        downloadTables.add(new SyncModel(TableDistricts.TABLE_NAME));
        downloadTables.add(new SyncModel(TableUCs.TABLE_NAME));
        downloadTables.add(new SyncModel(TableClusters.TABLE_NAME));
        //downloadTables.add(new SyncModel(UsersContract.TableUsers.TABLE_NAME));

        // Set tables to UPLOAD
        uploadTables.add(new SyncModel("Forms"));

        //bi.noItem.setVisibility(View.VISIBLE);
        bi.noDataItem.setVisibility(View.VISIBLE);
        listActivityCreated = true;
        uploadlistActivityCreated = true;
        sharedPref = getSharedPreferences("src", MODE_PRIVATE);
        editor = sharedPref.edit();
        db = new DatabaseHelper(this);
        dbBackup();

        boolean sync_flag = getIntent().getBooleanExtra(CONSTANTS.SYNC_LOGIN, false);
/*
        bi.btnSync.setOnClickListener(v -> onSyncDataClick());
        bi.btnUpload.setOnClickListener(v -> syncServer());
     */   // setAdapter();
        // setUploadAdapter();
    }


    void setAdapter(List<SyncModel> tables) {
        syncListAdapter = new SyncListAdapter(tables);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        bi.rvUploadList.setLayoutManager(mLayoutManager2);
        bi.rvUploadList.setItemAnimator(new DefaultItemAnimator());
        bi.rvUploadList.setAdapter(syncListAdapter);
        syncListAdapter.notifyDataSetChanged();
        if (syncListAdapter.getItemCount() > 0) {
            bi.noDataItem.setVisibility(View.GONE);
        } else {
            bi.noDataItem.setVisibility(View.VISIBLE);
        }
    }

    public void dbBackup() {

        if (sharedPref.getBoolean("flag", false)) {

            String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));

            if (!dt.equals(new SimpleDateFormat("dd-MM-yy").format(new Date()))) {
                editor.putString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));
                editor.apply();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + PROJECT_NAME);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                DirectoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
                folder = new File(DirectoryName);
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {

                    try {
                        File dbFile = new File(this.getDatabasePath(DATABASE_NAME).getPath());
                        FileInputStream fis = new FileInputStream(dbFile);

                        String outFileName = DirectoryName + File.separator + DatabaseHelper.DB_NAME;

                        // Open the empty db as the output stream
                        OutputStream output = new FileOutputStream(outFileName);

                        // Transfer bytes from the inputfile to the outputfile
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        // Close the streams
                        output.flush();
                        output.close();
                        fis.close();
                    } catch (IOException e) {
                        Log.e("dbBackup:", e.getMessage());
                    }

                }

            } else {
                Toast.makeText(this, "Not create folder", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }


    public void ProcessStart(View view) {

        switch (view.getId()) {

            case R.id.btnUpload:
                setAdapter(uploadTables);
                //BeginUpload();
                break;
            case R.id.btnSync:
                setAdapter(downloadTables);
                BeginDownload();
                break;

        }
    }


    private void BeginDownload() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();

        for (int i = 0; i < downloadTables.size(); i++) {
            Data data = new Data.Builder()
                    .putString("table", downloadTables.get(i).gettableName())
                    .putInt("position", i)
                    //.putString("columns", "_id, sysdate")
                    // .putString("where", where)
                    .build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DataDownWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data).build();
            workRequests.add(workRequest);

        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        // FOR WORKREQUESTS CHAIN (ONE TABLE DOWNLOADS AT A TIME)
/*        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests.get(0));
        for (int i=1; i < workRequests.size(); i++ ) {
            wc = wc.then(workRequests.get(i));
        }
        wc.enqueue();*/


        wc.getWorkInfosLiveData().observe(this, new Observer<List<WorkInfo>>() {


            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                Log.d(TAG, "workInfos: " + workInfos.size());
                for (WorkInfo workInfo : workInfos) {
                    Log.d(TAG, "workInfo: getState " + workInfo.getState());
                    Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("data"));
                    Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                    Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
                }
                for (WorkInfo workInfo : workInfos) {
                    int position = workInfo.getOutputData().getInt("position", 0);
                    String tableName = downloadTables.get(position).gettableName();

                            /*String progress = workInfo.getProgress().getString("progress");
                            bi.wmError.setText("Progress: " + progress);*/

                    if (workInfo.getState() != null &&
                            workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                        String result = workInfo.getOutputData().getString("data");
//Do something with the JSON string
                        if (result != null) {
                            if (result.length() > 0) {
                                DatabaseHelper db = new DatabaseHelper(SyncActivity.this);
                                try {
                                    JSONArray jsonArray = new JSONArray();
                                    int insertCount = 0;
                                    switch (tableName) {
                                        // case TableUsers.TABLE_NAME:
                                        //   jsonArray = new JSONArray(result);
                                        //   insertCount = db.syncUser(jsonArray);
                                        //           position = 0;
                                        //  break;
                                        //  case TableVersionApp.TABLE_NAME:
                                        //        insertCount = db.syncVersionApp(new JSONObject(result));
                                        //    if (insertCount == 1) jsonArray.put("1");
                                        //        position = 1;
                                        //     break;
                                        case TableUCs.TABLE_NAME:
                                            jsonArray = new JSONArray(result);
                                            insertCount = db.syncUCs(jsonArray);
                                            Log.d(TAG, "onChanged: " + tableName + " " + workInfo.getOutputData().getInt("position", 0));
                                            //      position = 2;
                                            break;
                                        case TableDistricts.TABLE_NAME:
                                            jsonArray = new JSONArray(result);
                                            insertCount = db.syncDistricts(jsonArray);
                                            Log.d(TAG, "onChanged: " + tableName + " " + workInfo.getOutputData().getInt("position", 0));

                                            //      position = 3;
                                            break;
                                        case TableClusters.TABLE_NAME:
                                            jsonArray = new JSONArray(result);
                                            insertCount = db.syncCluster(jsonArray);
                                            Log.d(TAG, "onChanged: " + tableName + " " + workInfo.getOutputData().getInt("position", 0));

                                            //       position = 4;
                                            break;

                                    }

                                    downloadTables.get(position).setmessage("Received: " + jsonArray.length() + ", Saved: " + insertCount);
                                    downloadTables.get(position).setstatus(insertCount == 0 ? "Unsuccessful" : "Successful");
                                    downloadTables.get(position).setstatusID(insertCount == 0 ? 1 : 3);
                                    syncListAdapter.updatesyncList(downloadTables);

//                    pd.show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    downloadTables.get(position).setstatus("Failed");
                                    downloadTables.get(position).setstatusID(1);
                                    downloadTables.get(position).setmessage(result);
                                    syncListAdapter.updatesyncList(downloadTables);
                                }
                            } else {
                                downloadTables.get(position).setmessage("Received: " + result.length() + "");
                                downloadTables.get(position).setstatus("Successful");
                                downloadTables.get(position).setstatusID(3);
                                syncListAdapter.updatesyncList(downloadTables);
//                pd.show();
                            }
                        } else {
                            downloadTables.get(position).setstatus("Failed");
                            downloadTables.get(position).setstatusID(1);
                            downloadTables.get(position).setmessage("Server not found!");
                            syncListAdapter.updatesyncList(downloadTables);
//            pd.show();
                        }
                    }
                    //mTextView1.append("\n" + workInfo.getState().name());
                    if (workInfo.getState() != null &&
                            workInfo.getState() == WorkInfo.State.FAILED) {
                        String message = workInfo.getOutputData().getString("error");
                        downloadTables.get(position).setstatus("Failed 1");
                        downloadTables.get(position).setstatusID(1);
                        downloadTables.get(position).setmessage(message);
                        syncListAdapter.updatesyncList(downloadTables);

                    }
                }
            }
        });

    }


    private void BeginUpload() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        List<OneTimeWorkRequest> workRequests = new ArrayList<>();

        for (int i = 0; i < downloadTables.size(); i++) {
            Data data = new Data.Builder()
                    .putString("table", downloadTables.get(i).gettableName())
                    .putInt("position", i)
                    //.putString("columns", "_id, sysdate")
                    // .putString("where", where)
                    .build();
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(DataDownWorkerALL.class)
                    .addTag(String.valueOf(i))
                    .setInputData(data).build();
            workRequests.add(workRequest);

        }

        // FOR SIMULTANEOUS WORKREQUESTS (ALL TABLES DOWNLOAD AT THE SAME TIME)
        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests);
        wc.enqueue();

        // FOR WORKREQUESTS CHAIN (ONE TABLE DOWNLOADS AT A TIME)
/*        WorkManager wm = WorkManager.getInstance();
        WorkContinuation wc = wm.beginWith(workRequests.get(0));
        for (int i=1; i < workRequests.size(); i++ ) {
            wc = wc.then(workRequests.get(i));
        }
        wc.enqueue();*/


        wc.getWorkInfosLiveData().observe(this, new Observer<List<WorkInfo>>() {


            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                Log.d(TAG, "workInfos: " + workInfos.size());
                for (WorkInfo workInfo : workInfos) {
                    Log.d(TAG, "workInfo: getState " + workInfo.getState());
                    Log.d(TAG, "workInfo: data " + workInfo.getTags());
                    Log.d(TAG, "workInfo: data " + workInfo.getOutputData().getString("data"));
                    Log.d(TAG, "workInfo: error " + workInfo.getOutputData().getString("error"));
                    Log.d(TAG, "workInfo: position " + workInfo.getOutputData().getInt("position", 0));
                }
                for (WorkInfo workInfo : workInfos) {
                    int position = workInfo.getOutputData().getInt("position", 0);
                    String tableName = downloadTables.get(position).gettableName();

                            /*String progress = workInfo.getProgress().getString("progress");
                            bi.wmError.setText("Progress: " + progress);*/

                    if (workInfo.getState() != null &&
                            workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                        String result = workInfo.getOutputData().getString("data");

                        int sSynced = 0;
                        int sDuplicate = 0;
                        StringBuilder sSyncedError = new StringBuilder();
                        JSONArray json;


                        try {
                            Log.d(TAG, "onPostExecute: " + result);
                            json = new JSONArray(result);

                            DatabaseHelper db = new DatabaseHelper(SyncActivity.this); // Database Helper

                            Method method = null;
                            for (Method method1 : db.getClass().getDeclaredMethods()) {
                                if (method1.getName().equals("insert" + tableName)) {
                                    method = method1;
                                    break;
                                }
                            }

                            for (int i = 0; i < json.length(); i++) {
                                JSONObject jsonObject = new JSONObject(json.getString(i));

                                if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {
                                    method.invoke(db, jsonObject.getString("id"));
                                    sSynced++;
                                } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                                    method.invoke(db, jsonObject.getString("id"));
                                    sDuplicate++;
                                } else {
                                    sSyncedError.append("\nError: ").append(jsonObject.getString("message"));
                                }
                            }
                            Toast.makeText(SyncActivity.this, tableName + " synced: " + sSynced + "\r\n\r\n Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();

                            if (sSyncedError.toString().equals("")) {
                                downloadTables.get(position).setmessage(tableName + " synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
                                downloadTables.get(position).setstatus("Completed");
                                downloadTables.get(position).setstatusID(3);
                                syncListAdapter.updatesyncList(downloadTables);
                            } else {
                                downloadTables.get(position).setmessage(tableName + " synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
                                downloadTables.get(position).setstatus("Failed 4");
                                downloadTables.get(position).setstatusID(1);
                                syncListAdapter.updatesyncList(downloadTables);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SyncActivity.this, "Sync Result:  " + result, Toast.LENGTH_SHORT).show();

                            if (result.equals("No new records to sync.")) {
                                downloadTables.get(position).setmessage(result /*+ " Open Forms" + String.format("%02d", unclosedForms.size())*/);
                                downloadTables.get(position).setstatus("Not processed");
                                downloadTables.get(position).setstatusID(4);
                                syncListAdapter.updatesyncList(downloadTables);
                            } else {
                                downloadTables.get(position).setmessage(result);
                                downloadTables.get(position).setstatus("Failed 3");
                                downloadTables.get(position).setstatusID(1);
                                syncListAdapter.updatesyncList(downloadTables);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            downloadTables.get(position).setstatus("Failed 2");
                            downloadTables.get(position).setstatusID(1);
                            downloadTables.get(position).setmessage(e.getMessage());
                            syncListAdapter.updatesyncList(downloadTables);
                        }
                    }
                    //mTextView1.append("\n" + workInfo.getState().name());
                    if (workInfo.getState() != null &&
                            workInfo.getState() == WorkInfo.State.FAILED) {
                        String message = workInfo.getOutputData().getString("error");
                        downloadTables.get(position).setstatus("Failed 1");
                        downloadTables.get(position).setstatusID(1);
                        downloadTables.get(position).setmessage(message);
                        syncListAdapter.updatesyncList(downloadTables);

                    }
                }
            }
        });

    }


}
