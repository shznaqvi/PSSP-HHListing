package edu.aku.hassannaqvi.pssp_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by javed.khan on 12/31/2016.
 */

public class TehsilContract implements BaseColumns {

    String COLUMN_TEHSIL_CODE;
    String COLUMN_TEHSIL_NAME;


    public TehsilContract sync(JSONObject jsonObject) throws JSONException {
        this.COLUMN_TEHSIL_CODE = jsonObject.getString(TehsilContract.TehsilEntry.COLUMN_TEHSIL_CODE);
        this.COLUMN_TEHSIL_NAME = jsonObject.getString(TehsilContract.TehsilEntry.COLUMN_TEHSIL_NAME);

        return this;
    }

    public TehsilContract hydrate(Cursor cursor) {
        this.COLUMN_TEHSIL_CODE = cursor.getString(cursor.getColumnIndex(TehsilContract.TehsilEntry.COLUMN_TEHSIL_CODE));
        this.COLUMN_TEHSIL_NAME = cursor.getString(cursor.getColumnIndex(TehsilContract.TehsilEntry.COLUMN_TEHSIL_NAME));

        return this;
    }


    public String getTehsilCode() {
        return this.COLUMN_TEHSIL_CODE;
    }

    public void setTehsilCode(String COLUMN_TEHSIL_CODE) {
        this.COLUMN_TEHSIL_CODE = COLUMN_TEHSIL_CODE;
    }

    public String getTehsilName() {
        return COLUMN_TEHSIL_NAME;
    }

    public void setTehsilName(String COLUMN_TEHSIL_NAME) {
        this.COLUMN_TEHSIL_NAME = COLUMN_TEHSIL_NAME;
    }


    public abstract class TehsilEntry implements BaseColumns {

        public static final String TABLE_NAME = "tehsil";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_TEHSIL_CODE = "tehsil_code";
        public static final String COLUMN_TEHSIL_NAME = "tehsil_name";
    }
}