package edu.aku.hassannaqvi.cbt_hhlisting;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class PWContract {

    public Long ID;
    public String userName;
    public String UUID;
    public String UID;
    public String mwDT; //Form Date Time
    public String mwVillageCode;
    public String mwStructureNo;
    public String pw01;
    public String pw02;
    public String pw03;
    public String deviceId;
    public String lhwCode;
    public String household;
    public String synced;
    public String syncedDate;


    public PWContract() {
    }

    public PWContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(PwTable._ID);
        this.UUID = jsonObject.getString(PwTable.PW_UUID);
        this.UID = jsonObject.getString(PwTable.PW_UID);
        this.mwDT = jsonObject.getString(PwTable.PW_PWDT);
        this.mwVillageCode = jsonObject.getString(PwTable.PW_PWVILLAGECODE);
        this.mwStructureNo = jsonObject.getString(PwTable.PW_PWSTRUCTURENO);
        this.pw01 = jsonObject.getString(PwTable.PW_PW01);
        this.pw02 = jsonObject.getString(PwTable.PW_PW02);
        this.pw03 = jsonObject.getString(PwTable.PW_PW03);
        this.deviceId = jsonObject.getString(PwTable.PW_DEVICE_ID);
        this.lhwCode = jsonObject.getString(PwTable.PW_LHW_CODE);
        this.household = jsonObject.getString(PwTable.PW_HOUSEHOLD);
        this.synced = jsonObject.getString(PwTable.PW_SYNCED);
        this.syncedDate = jsonObject.getString(PwTable.PW_SYNCED_DATE);

        return this;
    }

    public PWContract Hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(PwTable._ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(PwTable.PW_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(PwTable.PW_UID));
        this.mwDT = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWDT));
        this.mwVillageCode = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWVILLAGECODE));
        this.mwStructureNo = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWSTRUCTURENO));
        this.pw01 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW01));
        this.pw02 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW02));
        this.pw03 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW03));
        this.deviceId = cursor.getString(cursor.getColumnIndex(PwTable.PW_DEVICE_ID));
        this.lhwCode = cursor.getString(cursor.getColumnIndex(PwTable.PW_LHW_CODE));
        this.household = cursor.getString(cursor.getColumnIndex(PwTable.PW_HOUSEHOLD));
        this.synced = cursor.getString(cursor.getColumnIndex(PwTable.PW_SYNCED));
        this.syncedDate = cursor.getString(cursor.getColumnIndex(PwTable.PW_SYNCED_DATE));

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

    public String getMwDT() {
        return mwDT;
    }

    public void setMwDT(String mwDT) {
        this.mwDT = mwDT;
    }

    public String getMwVillageCode() {
        return mwVillageCode;
    }

    public void setMwVillageCode(String mwVillageCode) {
        this.mwVillageCode = mwVillageCode;
    }

    public String getMwStructureNo() {
        return mwStructureNo;
    }

    public void setMwStructureNo(String mwStructureNo) {
        this.mwStructureNo = mwStructureNo;
    }

    public String getPw01() {
        return pw01;
    }

    public void setPw01(String pw01) {
        this.pw01 = pw01;
    }

    public String getPw02() {
        return pw02;
    }

    public void setPw02(String pw02) {
        this.pw02 = pw02;
    }

    public String getPw03() {
        return pw03;
    }

    public void setPw03(String pw03) {
        this.pw03 = pw03;
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

        json.put(PwTable._ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(PwTable.PW_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(PwTable.PW_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(PwTable.PW_PWDT, this.mwDT == null ? JSONObject.NULL : this.mwDT);
        json.put(PwTable.PW_PWVILLAGECODE, this.mwVillageCode == null ? JSONObject.NULL : this.mwVillageCode);
        json.put(PwTable.PW_PWSTRUCTURENO, this.mwStructureNo == null ? JSONObject.NULL : this.mwStructureNo);
        json.put(PwTable.PW_PW01, this.pw01 == null ? JSONObject.NULL : this.pw01);
        json.put(PwTable.PW_PW02, this.pw02 == null ? JSONObject.NULL : this.pw02);
        json.put(PwTable.PW_PW03, this.pw03 == null ? JSONObject.NULL : this.pw03);
        json.put(PwTable.PW_DEVICE_ID, this.deviceId == null ? JSONObject.NULL : this.deviceId);
        json.put(PwTable.PW_LHW_CODE, this.lhwCode == null ? JSONObject.NULL : this.lhwCode);
        json.put(PwTable.PW_HOUSEHOLD, this.household == null ? JSONObject.NULL : this.household);
        json.put(PwTable.PW_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(PwTable.PW_SYNCED_DATE, this.syncedDate == null ? JSONObject.NULL : this.syncedDate);

        return json;

    }

    public static abstract class PwTable implements BaseColumns {

        public static final String TABLE_NAME = "pw";
        public static final String PW_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String PW_UUID = "uuid";
        public static final String PW_UID = "uid";
        public static final String PW_PWDT = "pwdt";
        public static final String PW_PWVILLAGECODE = "pwvillagecode";
        public static final String PW_PWSTRUCTURENO = "pwstructureno";
        public static final String PW_PW01 = "pw01";
        public static final String PW_PW02 = "pw02";
        public static final String PW_PW03 = "pw03";
        public static final String PW_DEVICE_ID = "deviceid";
        public static final String PW_LHW_CODE = "lhw_code";
        public static final String PW_HOUSEHOLD = "household";
        public static final String PW_SYNCED = "synced";
        public static final String PW_SYNCED_DATE = "synced_date";
    }
}
