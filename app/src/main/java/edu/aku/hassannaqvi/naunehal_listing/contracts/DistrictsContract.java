package edu.aku.hassannaqvi.naunehal_listing.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class DistrictsContract {

    private String districtCode;
    private String districtName;

    public DistrictsContract() {
    }

    public DistrictsContract sync(JSONObject jsonObject) throws JSONException {
        this.districtCode = jsonObject.getString(TableDistricts.COLUMN_DISTRICT_CODE);
        this.districtName = jsonObject.getString(TableDistricts.COLUMN_DISTRICT_NAME);

        return this;
    }

    public DistrictsContract hydrate(Cursor cursor) {
        this.districtCode = cursor.getString(cursor.getColumnIndex(TableDistricts.COLUMN_DISTRICT_CODE));
        this.districtName = cursor.getString(cursor.getColumnIndex(TableDistricts.COLUMN_DISTRICT_NAME));

        return this;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public static abstract class TableDistricts implements BaseColumns {

        public static final String TABLE_NAME = "Districts";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_DISTRICT_CODE = "districtCode";
        public static final String COLUMN_DISTRICT_NAME = "districtName";

    }

}
