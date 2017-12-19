package edu.aku.hassannaqvi.willows_hhlisting.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class PSUsContract {

    private String id;
    private String psuCode;
    private String psuName;
    private String districtCode;


    public PSUsContract() {
    }

    public PSUsContract sync(JSONObject jsonObject) throws JSONException {
        this.id  = jsonObject.getString(singlePSU._ID);
        this.psuCode = jsonObject.getString(singlePSU.COLUMN_PSU_CODE);
        this.psuName = jsonObject.getString(singlePSU.COLUMN_PSU_NAME);
        this.districtCode = jsonObject.getString(singlePSU.COLUMN_DISTRICT_CODE);


        return this;
    }

    public PSUsContract hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(singlePSU._ID));
        this.psuCode = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_PSU_CODE));
        this.psuName = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_PSU_NAME));
        this.districtCode = cursor.getString(cursor.getColumnIndex(singlePSU.COLUMN_DISTRICT_CODE));

        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public static abstract class singlePSU implements BaseColumns {

        public static final String TABLE_NAME = "PSUs";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "id";
        public static final String COLUMN_PSU_CODE = "cluster_code";
        public static final String COLUMN_PSU_NAME = "cluster_name";
        public static final String COLUMN_DISTRICT_CODE = "uc_code";

        public static final String _URI = "clusters.php";
    }

}
