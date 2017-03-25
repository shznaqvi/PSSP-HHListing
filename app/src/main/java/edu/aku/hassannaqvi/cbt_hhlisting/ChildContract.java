package edu.aku.hassannaqvi.cbt_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class ChildContract {

    public Long ID;
    public String UUID;
    public String UID;
    public String cDT;
    public String userName; // username
    public String childName; // child name
    public String hh12d; // Days
    public String hh12m; // Months
    public String deviceId;
    public String lhwCode;
    public String household; //Structure no + Ext
    public String synced;
    public String syncedDate;


    public ChildContract() {
    }

    public ChildContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(ChildTable._ID);
        this.UUID = jsonObject.getString(ChildTable.C_UUID);
        this.UID = jsonObject.getString(ChildTable.C_UID);
        this.cDT = jsonObject.getString(ChildTable.C_CDT);
        this.userName = jsonObject.getString(ChildTable.C_USERNAME);
        this.childName = jsonObject.getString(ChildTable.C_CHILDNAME);
        this.hh12d = jsonObject.getString(ChildTable.C_CCHH12D);
        this.hh12m = jsonObject.getString(ChildTable.C_CCHH12M);
        this.deviceId = jsonObject.getString(ChildTable.C_DEVICE_ID);
        this.lhwCode = jsonObject.getString(ChildTable.C_LHW_CODE);
        this.household = jsonObject.getString(ChildTable.C_HOUSEHOLD);
        this.synced = jsonObject.getString(ChildTable.C_SYNCED);
        this.syncedDate = jsonObject.getString(ChildTable.C_SYNCED_DATE);

        return this;
    }

    public ChildContract Hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(ChildTable._ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(ChildTable.C_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(ChildTable.C_UID));
        this.cDT = cursor.getString(cursor.getColumnIndex(ChildTable.C_CDT));
        this.userName = cursor.getString(cursor.getColumnIndex(ChildTable.C_USERNAME));
        this.childName = cursor.getString(cursor.getColumnIndex(ChildTable.C_CHILDNAME));
        this.hh12d = cursor.getString(cursor.getColumnIndex(ChildTable.C_CCHH12D));
        this.hh12m = cursor.getString(cursor.getColumnIndex(ChildTable.C_CCHH12M));
        this.deviceId = cursor.getString(cursor.getColumnIndex(ChildTable.C_DEVICE_ID));
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

    public String getHh12d() {
        return hh12d;
    }

    public void setHh12d(String hh12d) {
        this.hh12d = hh12d;
    }

    public String getHh12m() {
        return hh12m;
    }

    public void setHh12m(String hh12m) {
        this.hh12m = hh12m;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
        json.put(ChildTable.C_CCHH12D, this.hh12d == null ? JSONObject.NULL : this.hh12d);
        json.put(ChildTable.C_CCHH12M, this.hh12m == null ? JSONObject.NULL : this.hh12m);
        json.put(ChildTable.C_DEVICE_ID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(ChildTable.C_LHW_CODE, this.lhwCode == null ? JSONObject.NULL : this.lhwCode);
        json.put(ChildTable.C_HOUSEHOLD, this.household == null ? JSONObject.NULL : this.household);
        json.put(ChildTable.C_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(ChildTable.C_SYNCED_DATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);

        return json;

    }

    public static abstract class ChildTable implements BaseColumns {

        public static final String TABLE_NAME = "cw";
        public static final String C_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String C_UUID = "uuid";
        public static final String C_UID = "uid";
        public static final String C_CDT = "cDT";
        public static final String C_USERNAME = "hh10";
        public static final String C_CHILDNAME = "hh11";
        public static final String C_CCHH12D = "hh11d";
        public static final String C_CCHH12M = "hh11m";
        public static final String C_DEVICE_ID = "deviceid";
        public static final String C_LHW_CODE = "lhw_code";
        public static final String C_HOUSEHOLD = "household";
        public static final String C_SYNCED = "synced";
        public static final String C_SYNCED_DATE = "synced_date";
    }
}
