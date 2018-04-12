package edu.aku.hassannaqvi.pssp_hhlisting.Sync;

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

import edu.aku.hassannaqvi.pssp_hhlisting.Contracts.ListingContract;
import edu.aku.hassannaqvi.pssp_hhlisting.Core.AppMain;
import edu.aku.hassannaqvi.pssp_hhlisting.Core.FormsDBHelper;

/**
 * Created by hassan.naqvi on 7/26/2016.
 */
public class SyncListing extends AsyncTask<Void, Void, String> {

    private static final String TAG = "SyncListing";
    private Context mContext;
    private ProgressDialog pd;


    public SyncListing(Context context) {
        mContext = context;
    }

    public static void longInfo(String str) {
       /* if (str.length() > 4000) {
            Log.i("TAG: ", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("TAG: ", str);*/
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please wait... Processing Listings");
        pd.show();

    }

    private String downloadUrl(String myurl) {
        String line = "No Response";

        HttpURLConnection connection = null;
        try {
            String request = myurl;
            //String request = "http://10.1.42.30:3000/Listings";
            pd.setTitle("Connecting to... " + request);
            //pd.show();
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
            FormsDBHelper db = new FormsDBHelper(mContext);
            Collection<ListingContract> listings = db.getAllListings();
            Log.d(TAG, String.valueOf(listings.size()));
//            pd.setMessage("Total Listings: " );
            for (ListingContract lc : listings) {

                jsonSync.put(lc.toJSONObject());
                //wr.writeBytes(jsonParam.toString().replace("\uFEFF", "") + "\n");

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

                    //pd.show();
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
        return line;
    }


    @Override
    protected String doInBackground(Void... params) {
        try {
            return downloadUrl(AppMain._HOST_URL + "listings.php");
        } catch (IOException e) {
            return "Unable to upload data. Server may be down.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        int sSynced = 0;
        int sDuplicate = 0;
        String sSyncedError = "";
        JSONArray json = null;
        try {
            json = new JSONArray(result);

            //DatabaseHelper db = new DatabaseHelper(mContext); // Database Helper
            for (int i = 0; i < json.length(); i++) {
                JSONObject jsonObject = new JSONObject(json.getString(i));
                if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {

                    // db.updateSyncedListings(jsonObject.getString("id"));  // UPDATE SYNCED
                    sSynced++;
                } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                    //db.updateSyncedListings(jsonObject.getString("id")); // UPDATE DUPLICATES
                    sDuplicate++;
                } else {
                    sSyncedError += "\nError: " + jsonObject.getString("message");
                }
            }
            Toast.makeText(mContext, " Listings synced: " + sSynced + "\r\n\r\n Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();

            pd.setMessage(" Listings synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
            pd.setTitle("Done uploading Listings data");
            pd.show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed Sync " + result, Toast.LENGTH_SHORT).show();

            pd.setMessage(result);
            pd.setTitle("Listings Sync Failed");
            pd.show();


        }
    }
}
