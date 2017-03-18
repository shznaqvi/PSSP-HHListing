package edu.aku.hassannaqvi.cbt_hhlisting;

/**
 * Created by Hassan.naqvi on 3/18/2017.
 */

public class HFacilitiesContract implements BaseColumns {

    String hFacilityCode;
    String hFacilityName;

    public HFacilitiesContract sync(JSONObject jsonObject) throws JSONException {
        this.hFacilityCode = jsonObject.getString(HFacilityTable.COLUMN_HFACILITY_CODE);
        this.hFacilityName = jsonObject.getString(HFacilityTable.COLUMN_HFACILITY_NAME);

        return this;
    }

    public HFacilitiesContract hydrate(Cursor cursor) {
        this.hFacilityCode = cursor.getString(cursor.getColumnIndex(HFacilityTable.COLUMN_HFACILITY_CODE));
        this.hFacilityName = cursor.getString(cursor.getColumnIndex(HFacilityTable.COLUMN_HFACILITY_NAME));

        return this;
    }

    public String gethFacilityCode() {
        return hFacilityCode;
    }

    public void sethFacilityCode(String hFacilityCode) {
        this.hFacilityCode = hFacilityCode;
    }

    public String gethFacilityName() {
        return hFacilityName;
    }

    public void sethFacilityName(String hFacilityName) {
        this.hFacilityName = hFacilityName;
    }

    public abstract class HFacilityTable implements BaseColumns {


        public static final String TABLE_NAME = "hFacilities";
        public static final String URI = "hFacilities";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";

        public static final String _ID = "_ID";
        public static final String COLUMN_HFACILITY_CODE = "hfcode";
        public static final String COLUMN_HFACILITY_NAME = "hfname";

    }
}