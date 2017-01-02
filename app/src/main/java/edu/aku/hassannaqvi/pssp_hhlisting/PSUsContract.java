package edu.aku.hassannaqvi.pssp_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class PSUsContract {

    private String psuCode;
    private String psuName;
    private String tehsilName;


    public PSUsContract() {
    }

    public PSUsContract sync(JSONObject jsonObject) throws JSONException {
        this.psuCode = jsonObject.getString(singlePSU.COLUMN_PSU_CODE);
        this.psuName = jsonObject.getString(singlePSU.COLUMN_PSU_NAME);
        this.tehsilName = jsonObject.getString(singlePSU.COLUMN_TEHSIL_NAME);


        return this;
    }

    public PSUsContract hydrate(Cursor cursor) {
        this.psuCode = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_PSU_CODE));
        this.psuName = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_PSU_NAME));
        this.tehsilName = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_TEHSIL_NAME));

        return this;
    }

    public String getPSUCode() {
        return psuCode;
    }

    public void setPSUCode(String psuCode) {
        this.psuCode = psuCode;
    }

    public String getPSUName() {
        return psuName;
    }

    public void setPSUName(String psuName) {
        this.psuName = psuName;
    }

    public String getTehsilName() {
        return this.tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }

    public static abstract class singlePSU implements BaseColumns {

        public static final String TABLE_NAME = "PSUs";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_PSU_CODE = "psu_code";
        public static final String COLUMN_PSU_NAME = "psu_name";
        public static final String COLUMN_TEHSIL_NAME = "tehsil_name";

    }

}
