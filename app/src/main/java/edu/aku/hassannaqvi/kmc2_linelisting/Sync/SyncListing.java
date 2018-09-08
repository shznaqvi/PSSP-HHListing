package edu.aku.hassannaqvi.kmc2_linelisting.Sync;

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

import edu.aku.hassannaqvi.kmc2_linelisting.contracts.ListingContract;
import edu.aku.hassannaqvi.kmc2_linelisting.core.FormsDBHelper;
import edu.aku.hassannaqvi.kmc2_linelisting.core.MainApp;

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

  /*  public static void longInfo(String str) {
       *//* if (str.length() > 4000) {
            Log.i("TAG: ", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("TAG: ", str);*//*
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please wait... Processing Forms");
        pd.show();

    }

    private String downloadUrl(String myurl) throws IOException {
        String line = "No Response";

        HttpURLConnection connection = null;
        try {
            String request = myurl;
            //String request = "http://10.1.42.30:3000/forms";
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
//            pd.setMessage("Total Forms: " );
            if (listings.size()!=0) {

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
            return downloadUrl(MainApp._HOST_URL + ListingContract.ListingEntry._URL);
        } catch (IOException e) {
            return "Unable to upload data. Server may be down.";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pd.setMessage("Server Response: " + result);
        pd.setTitle("Done!... Synced Forms");
        //pd.show();
    }*/


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please wait... Processing Forms");
        pd.show();

    }


    @Override
    protected String doInBackground(Void... params) {

        String line = "No Response";
        try {
            String url = MainApp._HOST_URL + ListingContract.ListingEntry._URL;
            Log.d(TAG, "doInBackground: URL " + url);
            return downloadUrl(url);
        } catch (IOException e) {
            return "Unable to upload data. Server may be down.";
        }
    }


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
//                    db.updateForms(jsonObject.getString("id"));
                    sSynced++;
                } else {
                    sSyncedError += jsonObject.getString("message").toString() + "\n";
                }
            }

            Toast.makeText(mContext, sSynced + " Listing's synced." + String.valueOf(json.length() - sSynced) + " Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();

            pd.setMessage(sSynced + " Listing's synced." + String.valueOf(json.length() - sSynced) + " Errors: " + sSyncedError);
            pd.setTitle("Done uploading Listing's data");
            pd.show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Failed Sync " + result, Toast.LENGTH_SHORT).show();

            pd.setMessage(result);
            pd.setTitle("Listing's Sync Failed");
            pd.show();
        }
    }

    private String downloadUrl(String myurl) {
        String line = "No Response";
        // Only display the first 500 characters of the retrieved
        // web page content.
        //  int len = 500;
        FormsDBHelper db = new FormsDBHelper(mContext);
        Collection<ListingContract> forms = db.getAllListings();
        Log.d(TAG, String.valueOf(forms.size()));
        if (forms.size() > 0) {
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(20000 /* milliseconds */);
                conn.setConnectTimeout(30000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "utf-8");
                conn.setUseCaches(false);
                // Starts the query
                conn.connect();
                JSONArray jsonSync = new JSONArray();
                try {
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

                    for (ListingContract fc : forms) {

                        jsonSync.put(fc.toJSONObject());

                    }
                    wr.writeBytes(jsonSync.toString().replace("\uFEFF", "") + "\n");
                    //longInfo(jsonSync.toString().replace("\uFEFF", "") + "\n");
                    wr.flush();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

/*===================================================================*/
                int HttpResult = conn.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(), "utf-8"));
                    StringBuffer sb = new StringBuffer();

                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("" + sb.toString());
                    return sb.toString();
                } else {
                    System.out.println(conn.getResponseMessage());
                    return conn.getResponseMessage();
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            return "No new records to sync";
        }
        return line;
            /*===================================================================*/

    }
            
            
}