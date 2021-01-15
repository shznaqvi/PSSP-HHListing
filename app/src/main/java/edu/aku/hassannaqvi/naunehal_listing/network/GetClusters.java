package edu.aku.hassannaqvi.naunehal_listing.network;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import edu.aku.hassannaqvi.naunehal_listing.contracts.ClustersContract;
import edu.aku.hassannaqvi.naunehal_listing.core.MainApp;
import edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper;

/**
 * Created by hassan.naqvi on 4/28/2016.
 */
public class GetClusters extends AsyncTask<String, String, String> {

    private final String TAG = "GetClusters()";
    HttpURLConnection urlConnection;
    private Context mContext;
    private ProgressDialog pd;

    public GetClusters(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Getting Clusters");
        pd.setMessage("Preparing...");
        pd.show();

    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(MainApp._IP + "/clusters/");
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                pd.setMessage("Connected to Server");
                //pd.show();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                pd.setMessage("Receiving Data...");
                //pd.show();
                Log.i(TAG, "Clusters In: " + line);
                result.append(line);
            }
            } else {
                pd.setMessage("URL not found");
                result.append("URL not found");

            }
        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            urlConnection.disconnect();

        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Do something with the JSON string
        if (result != "URL not found") {

            String json = result;
            //json = json.replaceAll("\\[", "").replaceAll("\\]","");
            Log.d(TAG, result);
            ArrayList<ClustersContract> ClusterArrayList;
            DatabaseHelper db = new DatabaseHelper(mContext);
            try {
                ClusterArrayList = new ArrayList<ClustersContract>();
                //JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = new JSONArray(json);
                pd.setMessage("Received: " + jsonArray.length() + " Clusters");
                pd.setTitle("Done... Synced Clusters");
                db.syncCluster(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                pd.setMessage("Received: 0 Clusters");
                pd.setTitle("Error... Syncing Clusters");
            }
        db.getAllDistricts();
            pd.show();
    }



/*        try {
            JSONObject obj = new JSONObject(json);
            Log.d("My App", obj.toString());
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }*/

//        ArrayList<String> listdata = new ArrayList<String>();
//        JSONArray jArray = (JSONArray)jsonObject;
//        if (jArray != null) {
//            for (int i=0;i<jArray.length();i++){
//                listdata.add(jArray.get(i).toString());
//            }
//        }

    }
}