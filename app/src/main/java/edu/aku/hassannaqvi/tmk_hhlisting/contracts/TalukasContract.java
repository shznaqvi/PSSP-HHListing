package edu.aku.hassannaqvi.tmk_hhlisting.contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class TalukasContract {

    private static final String TAG = "Talukas_CONTRACT";
    String ID;
    String taluka;

    public TalukasContract() {
        // Default Constructor
    }

    public TalukasContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getString(singleTalukas.COLUMN_ID);
        this.taluka = jsonObject.getString(singleTalukas.COLUMN_TALUKA);
        return this;
    }

    public TalukasContract HydrateTalukas(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(singleTalukas.COLUMN_ID));
        this.taluka = cursor.getString(cursor.getColumnIndex(singleTalukas.COLUMN_TALUKA));
        return this;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(singleTalukas.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(singleTalukas.COLUMN_TALUKA, this.taluka == null ? JSONObject.NULL : this.taluka);
        return json;
    }


    public static abstract class singleTalukas implements BaseColumns {

        public static final String TABLE_NAME = "talukas";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TALUKA = "taluka";

        public static final String _URI = "talukas.php";
    }
}