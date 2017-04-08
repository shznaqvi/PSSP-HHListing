package edu.aku.hassannaqvi.cbt_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class ChildrenContract {

    public Long ID;
    public String UUID;
    public String UID;
    public String cDT;
    public String userName; // username
    public String childName; // child name
    public String cc12d; // Days
    public String cc12m; // Months
    public String deviceId;
    public String tehsil;   //Tehsil
    public String hh01; // HF
    public String hh02; // UC + Village
    public String lhwCode;
    public String household; //Structure no + Ext
    public String synced;
    public String syncedDate;


    public ChildrenContract() {
    }

    public ChildrenContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(ChildTable._ID);
        this.UUID = jsonObject.getString(ChildTable.C_UUID);
        this.UID = jsonObject.getString(ChildTable.C_UID);
        this.cDT = jsonObject.getString(ChildTable.C_CDT);
        this.userName = jsonObject.getString(ChildTable.C_USERNAME);
        this.childName = jsonObject.getString(ChildTable.C_CHILDNAME);
        this.cc12d = jsonObject.getString(ChildTable.C_CCHH12D);
        this.cc12m = jsonObject.getString(ChildTable.C_CCHH12M);
        this.deviceId = jsonObject.getString(ChildTable.C_DEVICE_ID);
        this.tehsil = jsonObject.getString(ChildTable.C_NAME_TEHSIL);
        this.hh01 = jsonObject.getString(ChildTable.C_NAME_HH01);
        this.hh02 = jsonObject.getString(ChildTable.C_NAME_HH02);
        this.lhwCode = jsonObject.getString(ChildTable.C_LHW_CODE);
        this.household = jsonObject.getString(ChildTable.C_HOUSEHOLD);
        this.synced = jsonObject.getString(ChildTable.C_SYNCED);
        this.syncedDate = jsonObject.getString(ChildTable.C_SYNCED_DATE);

        return this;
    }

    public ChildrenContract hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(ChildTable._ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(ChildTable.C_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(ChildTable.C_UID));
        this.cDT = cursor.getString(cursor.getColumnIndex(ChildTable.C_CDT));
        this.userName = cursor.getString(cursor.getColumnIndex(ChildTable.C_USERNAME));
        this.childName = cursor.getString(cursor.getColumnIndex(ChildTable.C_CHILDNAME));
        this.cc12d = cursor.getString(cursor.getColumnIndex(ChildTable.C_CCHH12D));
        this.cc12m = cursor.getString(cursor.getColumnIndex(ChildTable.C_CCHH12M));
        this.deviceId = cursor.getString(cursor.getColumnIndex(ChildTable.C_DEVICE_ID));
        this.tehsil = cursor.getString(cursor.getColumnIndex(ChildTable.C_NAME_TEHSIL));
        this.hh01 = cursor.getString(cursor.getColumnIndex(ChildTable.C_NAME_HH01));
        this.hh02 = cursor.getString(cursor.getColumnIndex(ChildTable.C_NAME_HH02));
        this.lhwCode = cursor.getString(cursor.getColumnIndex(ChildTable.C_LHW_CODE));
        this.household = cursor.getString(cursor.getColumnIndex(ChildTable.C_HOUSEHOLD));
        this.synced = cursor.getString(cursor.getColumnIndex(ChildTable.C_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(ChildTable.C_SYNCED_DATE));

        return this;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getcDT() {
        return cDT;
    }

    public void setcDT(String cDT) {
        this.cDT = cDT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getCc12d() {
        return cc12d;
    }

    public void setCc12d(String cc12d) {
        this.cc12d = cc12d;
    }

    public String getCc12m() {
        return cc12m;
    }

    public void setCc12m(String cc12m) {
        this.cc12m = cc12m;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
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

    public String getLhwCode() {
        return lhwCode;
    }

    public void setLhwCode(String lhwCode) {
        this.lhwCode = lhwCode;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(String syncedDate) {
        this.syncedDate = syncedDate;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(ChildTable._ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(ChildTable.C_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(ChildTable.C_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(ChildTable.C_CDT, this.cDT == null ? JSONObject.NULL : this.cDT);
        json.put(ChildTable.C_USERNAME, this.userName == null ? JSONObject.NULL : this.userName);
        json.put(ChildTable.C_CHILDNAME, this.childName == null ? JSONObject.NULL : this.childName);
        json.put(ChildTable.C_CCHH12D, this.cc12d == null ? JSONObject.NULL : this.cc12d);
        json.put(ChildTable.C_CCHH12M, this.cc12m == null ? JSONObject.NULL : this.cc12m);
        json.put(ChildTable.C_DEVICE_ID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(ChildTable.C_NAME_TEHSIL, this.tehsil == null ? JSONObject.NULL : this.tehsil);
        json.put(ChildTable.C_NAME_HH01, this.hh01 == null ? JSONObject.NULL : this.hh01);
        json.put(ChildTable.C_NAME_HH02, this.hh02 == null ? JSONObject.NULL : this.hh02);
        json.put(ChildTable.C_LHW_CODE, this.lhwCode == null ? JSONObject.NULL : this.lhwCode);
        json.put(ChildTable.C_HOUSEHOLD, this.household == null ? JSONObject.NULL : this.household);
        json.put(ChildTable.C_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(ChildTable.C_SYNCED_DATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);

        return json;

    }

    public static abstract class ChildTable implements BaseColumns {

        public static final String URI = "/children";
        public static final String TABLE_NAME = "child";
        public static final String NULLABLE = "NULLHACK";

        public static final String _ID = "_id";
        public static final String C_UUID = "uuid";
        public static final String C_UID = "uid";
        public static final String C_CDT = "form_date";
        public static final String C_USERNAME = "username";
        public static final String C_CHILDNAME = "child_name";
        public static final String C_CCHH12D = "cc12d";
        public static final String C_CCHH12M = "cc12m";
        public static final String C_DEVICE_ID = "deviceid";
        public static final String C_NAME_TEHSIL = "tehsil";
        public static final String C_NAME_HH01 = "hfacility";
        public static final String C_NAME_HH02 = "uc_village";
        public static final String C_LHW_CODE = "lhw_code";
        public static final String C_HOUSEHOLD = "household";
        public static final String C_SYNCED = "synced";
        public static final String C_SYNCED_DATE = "synced_date";

    }
}
