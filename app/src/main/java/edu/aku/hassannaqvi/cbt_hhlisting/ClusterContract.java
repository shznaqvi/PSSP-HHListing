package edu.aku.hassannaqvi.cbt_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class ClusterContract {

    public Long ID;
    public String UID;
    public String clDT;
    public String userName; // username
    public String lhwPh;
    public String noHH;
    public String noBISP;
    public String deviceId;
    public String lhwCode;
    public String synced;
    public String syncedDate;


    public ClusterContract() {
    }

    public ClusterContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(ClusterTable._ID);
        this.UID = jsonObject.getString(ClusterTable.C_UID);
        this.clDT = jsonObject.getString(ClusterTable.C_clDT);
        this.userName = jsonObject.getString(ClusterTable.C_USERNAME);
        this.lhwPh = jsonObject.getString(ClusterTable.C_LHWPH);
        this.noHH = jsonObject.getString(ClusterTable.C_NO_HH);
        this.noBISP = jsonObject.getString(ClusterTable.C_NO_BISP);
        this.deviceId = jsonObject.getString(ClusterTable.C_DEVICE_ID);
        this.lhwCode = jsonObject.getString(ClusterTable.C_LHW_CODE);
        this.synced = jsonObject.getString(ClusterTable.C_SYNCED);
        this.syncedDate = jsonObject.getString(ClusterTable.C_SYNCED_DATE);

        return this;
    }

    public ClusterContract hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(ClusterTable._ID));
        this.UID = cursor.getString(cursor.getColumnIndex(ClusterTable.C_UID));
        this.clDT = cursor.getString(cursor.getColumnIndex(ClusterTable.C_clDT));
        this.userName = cursor.getString(cursor.getColumnIndex(ClusterTable.C_USERNAME));
        this.lhwPh = cursor.getString(cursor.getColumnIndex(ClusterTable.C_LHWPH));
        this.noHH = cursor.getString(cursor.getColumnIndex(ClusterTable.C_NO_HH));
        this.noBISP = cursor.getString(cursor.getColumnIndex(ClusterTable.C_NO_BISP));
        this.deviceId = cursor.getString(cursor.getColumnIndex(ClusterTable.C_DEVICE_ID));
        this.lhwCode = cursor.getString(cursor.getColumnIndex(ClusterTable.C_LHW_CODE));
        this.synced = cursor.getString(cursor.getColumnIndex(ClusterTable.C_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(ClusterTable.C_SYNCED_DATE));

        return this;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getClDT() {
        return clDT;
    }

    public void setClDT(String clDT) {
        this.clDT = clDT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLhwPh() {
        return lhwPh;
    }

    public void setLhwPh(String lhwPh) {
        this.lhwPh = lhwPh;
    }

    public String getNoHH() {
        return noHH;
    }

    public void setNoHH(String noHH) {
        this.noHH = noHH;
    }

    public String getNoBISP() {
        return noBISP;
    }

    public void setNoBISP(String noBISP) {
        this.noBISP = noBISP;
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

        json.put(ClusterTable._ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(ClusterTable.C_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(ClusterTable.C_clDT, this.clDT == null ? JSONObject.NULL : this.clDT);
        json.put(ClusterTable.C_USERNAME, this.userName == null ? JSONObject.NULL : this.userName);
        json.put(ClusterTable.C_LHWPH, this.lhwPh == null ? JSONObject.NULL : this.lhwPh);
        json.put(ClusterTable.C_NO_HH, this.noHH == null ? JSONObject.NULL : this.noHH);
        json.put(ClusterTable.C_NO_BISP, this.noBISP == null ? JSONObject.NULL : this.noBISP);
        json.put(ClusterTable.C_DEVICE_ID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(ClusterTable.C_LHW_CODE, this.lhwCode == null ? JSONObject.NULL : this.lhwCode);
        json.put(ClusterTable.C_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(ClusterTable.C_SYNCED_DATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);

        return json;

    }

    public static abstract class ClusterTable implements BaseColumns {

        public static final String TABLE_NAME = "clc";
        public static final String C_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String C_UID = "uid";
        public static final String C_clDT = "clDT";
        public static final String C_USERNAME = "user";
        public static final String C_LHWPH = "hh11";
        public static final String C_NO_HH = "noHH";
        public static final String C_NO_BISP = "noBISP";
        public static final String C_DEVICE_ID = "deviceid";
        public static final String C_LHW_CODE = "lhw_code";
        public static final String C_SYNCED = "synced";
        public static final String C_SYNCED_DATE = "synced_date";
    }
}
