package edu.aku.hassannaqvi.tmk_hhlisting.contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class UCsContract {

    private static final String TAG = "UCs_CONTRACT";
    String ID;
    String ucs;
    String taluka_code;

    public UCsContract() {
        // Default Constructor
    }

    public UCsContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getString(singleUCs.COLUMN_ID);
        this.ucs = jsonObject.getString(singleUCs.COLUMN_UCS);
        this.taluka_code = jsonObject.getString(singleUCs.COLUMN_TALUKA_CODE);
        return this;
    }

    public UCsContract HydrateUCs(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(singleUCs.COLUMN_ID));
        this.ucs = cursor.getString(cursor.getColumnIndex(singleUCs.COLUMN_UCS));
        this.taluka_code = cursor.getString(cursor.getColumnIndex(singleUCs.COLUMN_TALUKA_CODE));
        return this;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUcs() {
        return ucs;
    }

    public void setUcs(String ucs) {
        this.ucs = ucs;
    }

    public String getTaluka_code() {
        return taluka_code;
    }

    public void setTaluka_code(String taluka_code) {
        this.taluka_code = taluka_code;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(singleUCs.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(singleUCs.COLUMN_UCS, this.ucs == null ? JSONObject.NULL : this.ucs);
        json.put(singleUCs.COLUMN_TALUKA_CODE, this.taluka_code == null ? JSONObject.NULL : this.taluka_code);
        return json;
    }


    public static abstract class singleUCs implements BaseColumns {

        public static final String TABLE_NAME = "ucs";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UCS = "union_council";
        public static final String COLUMN_TALUKA_CODE = "taluka_code";

        public static final String _URI = "ucs.php";
    }
}