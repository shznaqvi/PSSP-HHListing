package edu.aku.hassannaqvi.naunehal_listing.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class UCsContract {

    private String ucCode;
    private String ucName;
    private String districtCode;

    public UCsContract() {
    }

    public UCsContract sync(JSONObject jsonObject) throws JSONException {
        this.ucCode = jsonObject.getString(TableUCs.COLUMN_UC_CODE);
        this.ucName = jsonObject.getString(TableUCs.COLUMN_UC_NAME);
        this.districtCode = jsonObject.getString(TableUCs.COLUMN_DISTRICT_CODE);

        return this;
    }

    public UCsContract hydrate(Cursor cursor) {
        this.ucCode = cursor.getString(cursor.getColumnIndex(TableUCs.COLUMN_UC_CODE));
        this.ucName = cursor.getString(cursor.getColumnIndex(TableUCs.COLUMN_UC_NAME));
        this.districtCode = cursor.getString(cursor.getColumnIndex(TableUCs.COLUMN_DISTRICT_CODE));

        return this;
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

    public String getDistrictCode() {
        return districtCode;
    }

    public UCsContract setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
        return this;
    }

    public static abstract class TableUCs implements BaseColumns {

        public static final String TABLE_NAME = "UCs";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_UC_CODE = "ucCode";
        public static final String COLUMN_UC_NAME = "ucName";
        public static final String COLUMN_DISTRICT_CODE = "districtCode";

    }

}
