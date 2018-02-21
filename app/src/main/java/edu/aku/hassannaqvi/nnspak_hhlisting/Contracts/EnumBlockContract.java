package edu.aku.hassannaqvi.nnspak_hhlisting.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by javed.khan on 2/21/2018.
 */

public class EnumBlockContract {

    private static final String TAG = "EnumBlock_CONTRACT";
    String ebcode;
    String geoarea;

    public EnumBlockContract() {
        // Default Constructor
    }

    public EnumBlockContract Sync(JSONObject jsonObject) throws JSONException {
        this.ebcode = jsonObject.getString(EnumBlockTable.COLUMN_EB_CODE);
        this.geoarea = jsonObject.getString(EnumBlockTable.COLUMN_GEO_AREA);
        return this;
    }

    public EnumBlockContract HydrateTalukas(Cursor cursor) {
        this.ebcode = cursor.getString(cursor.getColumnIndex(EnumBlockTable.COLUMN_EB_CODE));
        this.geoarea = cursor.getString(cursor.getColumnIndex(EnumBlockTable.COLUMN_GEO_AREA));
        return this;
    }

    public String getEbcode() {
        return ebcode;
    }

    public void setEbcode(String ebcode) {
        this.ebcode = ebcode;
    }

    public String getTalukaName() {
        return geoarea;
    }

    public void setGeoarea(String geoarea) {
        this.geoarea = geoarea;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(EnumBlockTable.COLUMN_EB_CODE, this.ebcode == null ? JSONObject.NULL : this.ebcode);
        json.put(EnumBlockTable.COLUMN_GEO_AREA, this.geoarea == null ? JSONObject.NULL : this.geoarea);
        return json;
    }


    public static abstract class EnumBlockTable implements BaseColumns {

        public static final String TABLE_NAME = "enumblock";
        public static final String COLUMN_EB_CODE = "ebcode";
        public static final String COLUMN_GEO_AREA = "geoarea";

        public static final String _URI = "enumblock.php";
    }

}