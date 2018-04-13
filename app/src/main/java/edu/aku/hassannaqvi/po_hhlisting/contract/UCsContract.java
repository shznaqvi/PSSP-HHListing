package edu.aku.hassannaqvi.po_hhlisting.contract;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class UCsContract {

    private static String TAG = "";

    private String ucCode;
    private String ucName;
    private String districtCode;

    public UCsContract() {
    }

    public UCsContract sync(JSONObject jsonObject) throws JSONException {

        this.ucCode = jsonObject.getString(singleUC.COLUMN_UC_CODE);
        this.ucName = jsonObject.getString(singleUC.COLUMN_UC_NAME);
        this.districtCode = jsonObject.getString(singleUC.COLUMN_DISTRICT_CODE);

        return this;
    }

    public UCsContract hydrate(Cursor cursor) {
        this.ucCode = cursor.getString(cursor.getColumnIndex(singleUC.COLUMN_UC_CODE));
        this.ucName = cursor.getString(cursor.getColumnIndex(singleUC.COLUMN_UC_NAME));
        this.districtCode = cursor.getString(cursor.getColumnIndex(singleUC.COLUMN_DISTRICT_CODE));

        return this;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }

    public String getUcName() {
        return ucName;
    }

    public void setUcName(String ucName) {
        this.ucName = ucName;
    }

    public static abstract class singleUC implements BaseColumns {

        public static final String TABLE_NAME = "UCs";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_UC_CODE = "uc_code";
        public static final String COLUMN_UC_NAME = "uc_name";
        public static final String COLUMN_DISTRICT_CODE = "taluka_code";

        public static final String _URI = "ucs.php";
    }

}