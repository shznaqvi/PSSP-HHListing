package edu.aku.hassannaqvi.pssp_hhlisting;

/**
 * Created by hassan.naqvi on 10/19/2016.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by hassan.naqvi on 7/26/2016.
 */
public class SyncForms extends AsyncTask<Void, Void, String> {

    private static final String TAG = "SyncForms";
    private Context mContext;
    private ProgressDialog pd;


    public SyncForms(Context context) {
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
        pd.setTitle("Please wait... Processing Forms");
        pd.show();

    }


    @Override
    protected String doInBackground(Void... params) {

        String line = "No Response";

        HttpURLConnection connection = null;
        try {
            String request = AppMain._IP + "/enrich/forms/";
            //String request = "http://10.1.42.30:3000/forms";
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
            for (ListingContract lc : listings) {

                jsonSync.put(lc.toJSONObject());
                //wr.writeBytes(jsonParam.toString().replace("\uFEFF", "") + "\n");

            }
            wr.writeBytes(jsonSync.toString().replace("\uFEFF", "") + "\n");
            //longInfo(jsonSync.toString().replace("\uFEFF", "") + "\n");
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pd.setMessage("Server Response: " + result);
        pd.setTitle("Done!... Synced Forms");
        //pd.show();
    }
}