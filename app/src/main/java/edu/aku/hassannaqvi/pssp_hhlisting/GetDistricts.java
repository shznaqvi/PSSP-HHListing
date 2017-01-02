package edu.aku.hassannaqvi.pssp_hhlisting;

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

/**
 * Created by hassan.naqvi on 4/28/2016.
 */
public class GetDistricts extends AsyncTask<String, String, String> {

    private final String TAG = "GetUsers()";
    HttpURLConnection urlConnection;
    private Context mContext;
    private ProgressDialog pd;


    public GetDistricts(Context context) {
        mContext = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Getting Districts");
        pd.setMessage("Preparing...");
        pd.show();

    }


    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(AppMain._IP + "/linelisting/getdistrictll.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //pd.show();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, "District In: " + line);
                    result.append(line);
                }
            } else {
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
            ArrayList<DistrictsContract> districtArrayList;
            FormsDBHelper db = new FormsDBHelper(mContext);
            try {
                districtArrayList = new ArrayList<DistrictsContract>();
                //JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = new JSONArray(json);
                db.syncDistrict(jsonArray);

                pd.setMessage("Received: " + jsonArray.length() + " Districts");
                pd.setTitle("Done... Synced Districts");

            } catch (JSONException e) {
                e.printStackTrace();
                pd.setMessage("Received: 0 Districts");
                pd.setTitle("Error... Syncing Districts");
            }
            db.getAllDistricts();
            pd.show();
        }
    }
}