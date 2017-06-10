package edu.aku.hassannaqvi.cbt_hhlisting.sync;

/**
 * Created by hassan.naqvi on 10/19/2016.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import edu.aku.hassannaqvi.cbt_hhlisting.AppMain;
import edu.aku.hassannaqvi.cbt_hhlisting.ChildrenContract;
import edu.aku.hassannaqvi.cbt_hhlisting.ChildrenContract;
import edu.aku.hassannaqvi.cbt_hhlisting.FormsDBHelper;

/**
 * Created by hassan.naqvi on 7/26/2016.
 */
public class SyncChildren extends AsyncTask<Void, Void, String> {

    private static final String TAG = "SyncChildren";
    private Context mContext;
    private ProgressDialog pd;


    public SyncChildren(Context context) {
        mContext = context;
    }

    public static void longInfo(String str) {
        if (str.length() > 4000) {
            Log.i(TAG, str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("TAG: ", str);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please wait... Processing Children");
        pd.show();

    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = AppMain.PROJECT_URI + ChildrenContract.ChildTable.URI;
            Log.d(TAG, "doInBackground: URL " + url);
            return downloadUrl(url);
        } catch (IOException e) {
            return "Unable to upload data. Server may be down.";
        }
    }

    private String downloadUrl(String myurl) throws IOException {
        String line = "No Response";

        FormsDBHelper db = new FormsDBHelper(mContext);
        Collection<ChildrenContract> Children = db.getAllChildren();
        Log.d(TAG, String.valueOf(Children.size()));

        if (Children.size() > 0) {

            HttpURLConnection connection = null;
            try {
                String request = myurl;
                //String request = "http://10.1.42.30:3000/Children";

                URL url = new URL(request);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                connection.setUseCaches(false);
                connection.connect();


                JSONArray jsonSync = new JSONArray();

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

//            pd.setMessage("Total Children: " );

                for (ChildrenContract fc : Children) {
                    //if (fc.getIstatus().equals("1")) {
                    jsonSync.put(fc.toJSONObject());
                    //}
                }
                wr.writeBytes(jsonSync.toString().replace("\uFEFF", "") + "\n");
                longInfo(jsonSync.toString().replace("\uFEFF", "") + "\n");
                wr.flush();
                int HttpResult = connection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            connection.getInputStream(), "utf-8"));
                    StringBuffer sb = new StringBuffer();

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("" + sb.toString());
                    return sb.toString();
                } else {
                    System.out.println(connection.getResponseMessage());
                    return connection.getResponseMessage();
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
            }
        } else {
            return "No new records to sync";
        }
        return line;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        int sSynced = 0;
        String sSyncedError = "";
        JSONArray json = null;
        try {
            json = new JSONArray(result);
            FormsDBHelper db = new FormsDBHelper(mContext);
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonObject = new JSONObject(json.getString(i));
                if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {
//                if (jsonObject.getString("status").equals("1")) {
                    db.updateSyncedChildren(jsonObject.getString("id"));
                    sSynced++;
                }
                else {
                    sSyncedError += jsonObject.getString("message").toString() + "\n";
                }
            }
            Toast.makeText(mContext, sSynced + " Children synced." + String.valueOf(json.length() - sSynced) + " Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();

            pd.setMessage(sSynced + " Children synced." + String.valueOf(json.length() - sSynced) + " Errors: " + sSyncedError);
            pd.setTitle("Done uploading Children data");
            pd.show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed Sync " + result, Toast.LENGTH_SHORT).show();

            pd.setMessage(result);
            pd.setTitle("Children Sync Failed");
            pd.show();


        }
    }
}