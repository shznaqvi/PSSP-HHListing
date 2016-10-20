package edu.aku.hassannaqvi.pssp_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class ListingContract {

    public String ID;
    public String UID;
    public String hhDT;
    public String hh01;
    public String hh02;
    public String hh03;
    public String hh04;
    public String hh04x;
    public String hh05;
    public String hh06;
    public String hh07;
    public String hh08;
    public String hh09;
    public String hh10;
    public String hh11;
    public String hhChildNm;
    public String DeviceID;

    public ListingContract() {
    }

    public ListingContract(String ID) {
        this.ID = ID;
    }

    public ListingContract(String hh01, String hh02, String hh03, String hh04, String hh04x, String hh05, String hh06, String hh07) {
        this.hh01 = hh01;
        this.hh02 = hh02;
        this.hh03 = hh03;
        this.hh04 = hh04;
        this.hh04x = hh04x;
        this.hh05 = hh05;
        this.hh06 = hh06;
        this.hh07 = hh07;
        this.DeviceID = DeviceID;
    }

    public ListingContract(JSONObject jsonObject) throws JSONException {
        this.hhDT = jsonObject.getString("hhDT");
        this.hh01 = jsonObject.getString("hh01");
        this.hh02 = jsonObject.getString("hh02");
        this.hh03 = jsonObject.getString("hh03");
        this.hh04 = jsonObject.getString("hh04");
        this.hh04x = jsonObject.getString("hh04x");
        this.hh05 = jsonObject.getString("hh05");
        this.hh06 = jsonObject.getString("hh06");
        this.hh07 = jsonObject.getString("hh07");
        this.DeviceID = jsonObject.getString("deviceid");
    }

    public ListingContract(Cursor cursor) {
        this.hhDT = cursor.getString(cursor.getColumnIndex("hhDT"));
        this.hh01 = cursor.getString(cursor.getColumnIndex("hh01"));
        this.hh02 = cursor.getString(cursor.getColumnIndex("hh02"));
        this.hh03 = cursor.getString(cursor.getColumnIndex("hh03"));
        this.hh04 = cursor.getString(cursor.getColumnIndex("hh04"));
        this.hh04x = cursor.getString(cursor.getColumnIndex("hh04x"));
        this.hh05 = cursor.getString(cursor.getColumnIndex("hh05"));
        this.hh06 = cursor.getString(cursor.getColumnIndex("hh06"));
        this.hh07 = cursor.getString(cursor.getColumnIndex("hh07"));
        this.DeviceID = cursor.getString(cursor.getColumnIndex("deviceid"));
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHhDT() {
        return hhDT;
    }

    public void setHhDT(String hhDT) {
        this.hhDT = hhDT;
    }

    public String getHh01() {
        return hh01;
    }

    public void setHh01(String hh01) {
        this.hh01 = hh01;
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

    public String getHh04() {
        return hh04;
    }

    public void setHh04(String hh04) {
        this.hh04 = hh04;
    }

    public String getHh04x() {
        return hh04x;
    }

    public void setHh04x(String hh04x) {
        this.hh04x = hh04x;
    }

    public String getHh05() {
        return hh05;
    }

    public void setHh05(String hh05) {
        this.hh05 = hh05;
    }

    public String getHh06() {
        return hh06;
    }

    public void setHh06(String hh06) {
        this.hh06 = hh06;
    }

    public String getHh07() {
        return hh07;
    }

    public void setHh07(String hh07) {
        this.hh07 = hh07;
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

    public String getHh10() {
        return hh10;
    }

    public void setHh10(String hh10) {
        this.hh10 = hh10;
    }

    public String getHh11() {
        return hh11;
    }

    public void setHh11(String hh11) {
        this.hh11 = hh11;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHhChildNm() {
        return hhChildNm;
    }

    public void setHhChildNm(String hhChildNm) {
        this.hhChildNm = hhChildNm;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ListingEntry._ID, this.ID);
        json.put(ListingEntry.COLUMN_NAME_UID, this.UID);
        json.put(ListingEntry.COLUMN_NAME_HHDATETIME, this.hhDT);
        json.put(ListingEntry.COLUMN_NAME_HH01, this.hh01);
        json.put(ListingEntry.COLUMN_NAME_HH02, this.hh02);
        json.put(ListingEntry.COLUMN_NAME_HH03, this.hh03);
        json.put(ListingEntry.COLUMN_NAME_HH04, this.hh04);
        json.put(ListingEntry.COLUMN_NAME_HH04x, this.hh04x);
        json.put(ListingEntry.COLUMN_NAME_HH05, this.hh05);
        json.put(ListingEntry.COLUMN_NAME_HH06, this.hh06);
        json.put(ListingEntry.COLUMN_NAME_HH07, this.hh07);
        json.put(ListingEntry.COLUMN_NAME_HH08, this.hh08);
        json.put(ListingEntry.COLUMN_NAME_HH09, this.hh09);
        json.put(ListingEntry.COLUMN_NAME_HH10, this.hh10);
        json.put(ListingEntry.COLUMN_NAME_HH11, this.hh11);
        json.put(ListingEntry.COLUMN_NAME_CHILD_NAME, this.hhChildNm);
        json.put(ListingEntry.COLUMN_NAME_DEVICEID, this.DeviceID);

        return json;
    }

    public static abstract class ListingEntry implements BaseColumns {

        public static final String TABLE_NAME = "listings";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_HHDATETIME = "hhdt";
        public static final String COLUMN_NAME_HH01 = "hh01";
        public static final String COLUMN_NAME_HH02 = "hh02";
        public static final String COLUMN_NAME_HH03 = "hh03";
        public static final String COLUMN_NAME_HH04 = "hh04";
        public static final String COLUMN_NAME_HH04x = "hh04x";
        public static final String COLUMN_NAME_HH05 = "hh05";
        public static final String COLUMN_NAME_HH06 = "hh06";
        public static final String COLUMN_NAME_HH07 = "hh07";
        public static final String COLUMN_NAME_HH08 = "hh08";
        public static final String COLUMN_NAME_HH09 = "hh09";
        public static final String COLUMN_NAME_HH10 = "hh10";
        public static final String COLUMN_NAME_HH11 = "hh11";
        public static final String COLUMN_NAME_CHILD_NAME = "child_name";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
    }
}