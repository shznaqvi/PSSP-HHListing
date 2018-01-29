package edu.aku.hassannaqvi.nnspak_hhlisting.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class TalukasContract {

    private String talukaCode;
    private String talukaName;

    public TalukasContract() {
    }

    public TalukasContract sync(JSONObject jsonObject) throws JSONException {
        this.talukaCode = jsonObject.getString(singleTaluka.COLUMN_TALUKA_CODE);
        this.talukaName = jsonObject.getString(singleTaluka.COLUMN_TALUKA_NAME);

        return this;
    }

    public TalukasContract hydrate(Cursor cursor) {
        this.talukaCode = cursor.getString(cursor.getColumnIndex(singleTaluka.COLUMN_TALUKA_CODE));
        this.talukaName = cursor.getString(cursor.getColumnIndex(singleTaluka.COLUMN_TALUKA_NAME));

        return this;
    }

    public String getTalukaCode() {
        return talukaCode;
    }

    public void setTalukaCode(String talukaCode) {
        this.talukaCode = talukaCode;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public static abstract class singleTaluka implements BaseColumns {

        public static final String TABLE_NAME = "Districts";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_TALUKA_CODE = "taluka_code";
        public static final String COLUMN_TALUKA_NAME = "taluka_name";

        public static final String _URI = "talukas.php";
    }

}
