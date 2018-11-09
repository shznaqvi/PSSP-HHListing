package edu.aku.hassannaqvi.kmc2_hhl_validation.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class RandomizedContract {

    public String id;
    public String muid;
    public String duid;
    public String hh02;
    public String hhno;
    public String sno;
    public String wname;
    public String dlvr_date;
    public String hh08;
    public String hh09;


    public RandomizedContract() {
    }

    public RandomizedContract(String muid) {
        this.muid = muid;
    }

    public RandomizedContract sync(JSONObject jsonObject) throws JSONException {

        this.muid = jsonObject.getString(RandomizedEntry.MWRA_MUID);
        this.duid = jsonObject.getString(RandomizedEntry.MWRA_DUID);
        this.hh02 = jsonObject.getString(RandomizedEntry.MWRA_HH02);
        this.hhno = jsonObject.getString(RandomizedEntry.MWRA_HHNO);
        this.sno = jsonObject.getString(RandomizedEntry.MWRA_SNO);
        this.wname = jsonObject.getString(RandomizedEntry.MWRA_WNAME);
        this.dlvr_date = jsonObject.getString(RandomizedEntry.MWRA_DLVRDATE);
        this.hh08 = jsonObject.getString(RandomizedEntry.MWRA_HH08);
        this.hh09 = jsonObject.getString(RandomizedEntry.MWRA_HH09);

        return this;
    }

    public RandomizedContract hydrate(Cursor cursor) {

        this.id = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_ID));
        this.muid = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_MUID));
        this.duid = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_DUID));
        this.hh02 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_HH02));
        this.hhno = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_HHNO));
        this.sno = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_SNO));
        this.wname = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_WNAME));
        this.dlvr_date = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_DLVRDATE));
        this.hh08 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_HH08));
        this.hh09 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.MWRA_HH09));

        return this;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMuid() {
        return muid;
    }

    public void setMuid(String muid) {
        this.muid = muid;
    }


    public String getDuid() {
        return duid;
    }

    public void setDuid(String duid) {
        this.duid = duid;
    }


    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }


    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }


    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }


    public String getDlvr_date() {
        return dlvr_date;
    }

    public void setDlvr_date(String dlvr_date) {
        this.dlvr_date = dlvr_date;
    }


    public String getHh08() {
        return hh08;
    }

    public void setHh08(String hh08) {
        this.hh08 = hh08;
    }


    public String getHh09() {
        return hh09;
    }

    public void setHh09(String hh09) {
        this.hh09 = hh09;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(RandomizedEntry.MWRA_ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(RandomizedEntry.MWRA_MUID, this.muid == null ? JSONObject.NULL : this.muid);
        json.put(RandomizedEntry.MWRA_DUID, this.duid == null ? JSONObject.NULL : this.duid);
        json.put(RandomizedEntry.MWRA_HH02, this.hh02 == null ? JSONObject.NULL : this.hh02);
        json.put(RandomizedEntry.MWRA_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(RandomizedEntry.MWRA_SNO, this.sno == null ? JSONObject.NULL : this.sno);
        json.put(RandomizedEntry.MWRA_WNAME, this.wname == null ? JSONObject.NULL : this.wname);
        json.put(RandomizedEntry.MWRA_DLVRDATE, this.dlvr_date == null ? JSONObject.NULL : this.dlvr_date);
        json.put(RandomizedEntry.MWRA_HH08, this.hh08 == null ? JSONObject.NULL : this.hh08);
        json.put(RandomizedEntry.MWRA_HH09, this.hh09 == null ? JSONObject.NULL : this.hh09);

        return json;

    }

    public static abstract class RandomizedEntry implements BaseColumns {

        public static final String TABLE_NAME = "mwra";
        public static final String MWRA_NULLABLE = "NULLHACK";
        public static final String MWRA_ID = "_id";
        public static final String MWRA_MUID = "muid";
        public static final String MWRA_DUID = "duid";
        public static final String MWRA_HH02 = "hh02";
        public static final String MWRA_HHNO = "hhno";
        public static final String MWRA_SNO = "sno";
        public static final String MWRA_WNAME = "wname";
        public static final String MWRA_DLVRDATE = "dlvr_date";
        public static final String MWRA_HH08 = "hh08";
        public static final String MWRA_HH09 = "hh09";

        public static String _URL = "lv_randomised.php";
    }
}