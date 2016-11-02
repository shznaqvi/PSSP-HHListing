package edu.aku.hassannaqvi.pssp_hhlisting;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

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
public class GetPSUs extends AsyncTask<String, String, String> {

    private final String TAG = "GetUsers()";
    HttpURLConnection urlConnection;
    private Context mContext;

    public GetPSUs(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("http://192.168.1.10:3000/psus/");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                Log.i(TAG, "PSUs In: " + line);
                result.append(line);
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

        String json = result;
        //json = json.replaceAll("\\[", "").replaceAll("\\]","");
        Log.d(TAG, result);
        ArrayList<PSUsContract> PSUArrayList;
        FormsDBHelper db = new FormsDBHelper(mContext);
        try {
            PSUArrayList = new ArrayList<PSUsContract>();
            //JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(json);
            db.syncPSU(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.getAllDistricts();
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