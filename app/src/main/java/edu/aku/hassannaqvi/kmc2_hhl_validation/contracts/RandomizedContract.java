package edu.aku.hassannaqvi.kmc2_hhl_validation.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class RandomizedContract {

    public String id;
    public String lv_uid;
    public String lv_rnddt;
    public String hh02;
    public String hh03;
    public String lv_hhdt;
    public String hh08;
    public String hh09;

    public RandomizedContract() {
    }

    public RandomizedContract sync(JSONObject jsonObject) throws JSONException {

        this.lv_uid = jsonObject.getString(RandomizedEntry.COLUMN_UID);
        this.lv_rnddt = jsonObject.getString(RandomizedEntry.COLUMN_RANDDT);
        this.hh02 = jsonObject.getString(RandomizedEntry.COLUMN_HH02);
        this.hh03 = jsonObject.getString(RandomizedEntry.COLUMN_HH03);
        this.lv_hhdt = jsonObject.getString(RandomizedEntry.COLUMN_HHDT);
        this.hh08 = jsonObject.getString(RandomizedEntry.COLUMN_HH08);
        this.hh09 = jsonObject.getString(RandomizedEntry.COLUMN_HH09);

        return this;
    }

    public RandomizedContract hydrate(Cursor cursor) {

        this.id = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_ID));
        this.lv_uid = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_UID));
        this.lv_rnddt = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_RANDDT));
        this.hh02 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_HH02));
        this.hh03 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_HH03));
        this.lv_hhdt = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_HHDT));
        this.hh08 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_HH08));
        this.hh09 = cursor.getString(cursor.getColumnIndex(RandomizedEntry.COLUMN_HH09));

        return this;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLv_uid() {
        return lv_uid;
    }

    public void setLv_uid(String lv_uid) {
        this.lv_uid = lv_uid;
    }


    public String getLv_rnddt() {
        return lv_rnddt;
    }

    public void setLv_rnddt(String lv_rnddt) {
        this.lv_rnddt = lv_rnddt;
    }


    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
    }

    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
    }

    public String getLv_hhdt() {
        return lv_hhdt;
    }

    public void setLv_hhdt(String lv_hhdt) {
        this.lv_hhdt = lv_hhdt;
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

        json.put(RandomizedEntry.COLUMN_ID, this.id == null ? JSONObject.NULL : this.id);
        json.put(RandomizedEntry.COLUMN_UID, this.lv_uid == null ? JSONObject.NULL : this.lv_uid);
        json.put(RandomizedEntry.COLUMN_RANDDT, this.lv_rnddt == null ? JSONObject.NULL : this.lv_rnddt);
        json.put(RandomizedEntry.COLUMN_HH02, this.hh02 == null ? JSONObject.NULL : this.hh02);
        json.put(RandomizedEntry.COLUMN_HH03, this.hh03 == null ? JSONObject.NULL : this.hh03);
        json.put(RandomizedEntry.COLUMN_HHDT, this.lv_hhdt == null ? JSONObject.NULL : this.lv_hhdt);
        json.put(RandomizedEntry.COLUMN_HH08, this.hh08 == null ? JSONObject.NULL : this.hh08);
        json.put(RandomizedEntry.COLUMN_HH09, this.hh09 == null ? JSONObject.NULL : this.hh09);

        return json;

    }

    public static abstract class RandomizedEntry implements BaseColumns {

        public static final String TABLE_NAME = "lv_random";
        public static final String MWRA_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "UID";
        public static final String COLUMN_RANDDT = "randDT";
        public static final String COLUMN_HH02 = "hh02";
        public static final String COLUMN_HH03 = "hh03";
        public static final String COLUMN_HHDT = "hhdt";
        public static final String COLUMN_HH08 = "hh08";
        public static final String COLUMN_HH09 = "hh09";

        public static String _URL = "lv_randomised.php";
    }
}