package edu.aku.hassannaqvi.cbt_hhlisting.contract;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by isd on 30/12/2016.
 */

public class VillageContract implements BaseColumns {
    private static final String TAG = "village_contract";

    String COLUMN_VCODE;
    String COLUMN_VNAME;
    String COLUMN_UCNAME;


    public VillageContract() {
        // Default Constructor
    }

    public VillageContract(String vcode, String vname, String ucname) {
        this.COLUMN_VCODE = vcode;
        this.COLUMN_VNAME = vname;
        this.COLUMN_UCNAME = ucname;
    }

    public VillageContract sync(JSONObject jsonObject) throws JSONException {
        this.COLUMN_VCODE = jsonObject.getString(VillageContract.VillageEntry.COLUMN_VCODE);
        this.COLUMN_VNAME = jsonObject.getString(VillageContract.VillageEntry.COLUMN_VNAME);
        this.COLUMN_UCNAME = jsonObject.getString(VillageContract.VillageEntry.COLUMN_UCNAME);

        return this;
    }

    public VillageContract hydrate(Cursor cursor) {
        this.COLUMN_VCODE = cursor.getString(cursor.getColumnIndex(VillageContract.VillageEntry.COLUMN_VCODE));
        this.COLUMN_VNAME = cursor.getString(cursor.getColumnIndex(VillageContract.VillageEntry.COLUMN_VNAME));
        this.COLUMN_UCNAME = cursor.getString(cursor.getColumnIndex(VillageContract.VillageEntry.COLUMN_UCNAME));

        return this;
    }

    public String getROW_UCNAME() {
        return this.COLUMN_UCNAME;
    }

    public void setROW_UCNAME(String COLUMN_UCNAME) {
        this.COLUMN_UCNAME = COLUMN_UCNAME;
    }

    public String getROW_VCODE() {
        return this.COLUMN_VCODE;
    }

    public void setROW_VCODE(String COLUMN_VCODE) {
        this.COLUMN_VCODE = COLUMN_VCODE;
    }

    public String getROW_VNAME() {
        return this.COLUMN_VNAME;
    }

    public void setROW_VNAME(String COLUMN_VNAME) {
        this.COLUMN_VNAME = COLUMN_VNAME;
    }


    public static abstract class VillageEntry implements BaseColumns {

        public static final String TABLE_NAME = "villages";
        public static final String COLUMN_VCODE = "vcode";
        public static final String COLUMN_VNAME = "vname";
        public static final String COLUMN_UCNAME = "ucname";
    }
}