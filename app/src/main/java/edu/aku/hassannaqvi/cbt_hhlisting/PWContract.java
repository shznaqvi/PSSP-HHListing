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
    public String UUID;
    public String UID;
    public String mwDT;
    public String mwVillageCode;
    public String mwStructureNo;
    public String mw01;
    public String mw02;
    public String mw03;
    public String deviceId;
    public String lhwCode;
    public String household;
    public String synced;
    public String syncedDate;


    public PWContract() {
    }

    public PWContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(PwTable.PW_ID);
        this.UUID = jsonObject.getString(PwTable.PW_UUID);
        this.UID = jsonObject.getString(PwTable.PW_UID);
        this.mwDT = jsonObject.getString(PwTable.PW_PWDT);
        this.mwVillageCode = jsonObject.getString(PwTable.PW_PWVILLAGECODE);
        this.mwStructureNo = jsonObject.getString(PwTable.PW_PWSTRUCTURENO);
        this.mw01 = jsonObject.getString(PwTable.PW_PW01);
        this.mw02 = jsonObject.getString(PwTable.PW_PW02);
        this.mw03 = jsonObject.getString(PwTable.PW_PW03);
        this.deviceId = jsonObject.getString(PwTable.PW_DEVICE_ID);
        this.lhwCode = jsonObject.getString(PwTable.PW_LHW_CODE);
        this.household = jsonObject.getString(PwTable.PW_HOUSEHOLD);
        this.synced = jsonObject.getString(PwTable.PW_SYNCED);
        this.syncedDate = jsonObject.getString(PwTable.PW_SYNCED_DATE);

        return this;
    }

    public PWContract Hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(PwTable.PW_ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(PwTable.PW_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(PwTable.PW_UID));
        this.mwDT = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWDT));
        this.mwVillageCode = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWVILLAGECODE));
        this.mwStructureNo = cursor.getString(cursor.getColumnIndex(PwTable.PW_PWSTRUCTURENO));
        this.mw01 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW01));
        this.mw02 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW02));
        this.mw03 = cursor.getString(cursor.getColumnIndex(PwTable.PW_PW03));
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

    public String getMw01() {
        return mw01;
    }

    public void setMw01(String mw01) {
        this.mw01 = mw01;
    }

    public String getMw02() {
        return mw02;
    }

    public void setMw02(String mw02) {
        this.mw02 = mw02;
    }

    public String getMw03() {
        return mw03;
    }

    public void setMw03(String mw03) {
        this.mw03 = mw03;
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

        json.put(PwTable.PW_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(PwTable.PW_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(PwTable.PW_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(PwTable.PW_PWDT, this.mwDT == null ? JSONObject.NULL : this.mwDT);
        json.put(PwTable.PW_PWVILLAGECODE, this.mwVillageCode == null ? JSONObject.NULL : this.mwVillageCode);
        json.put(PwTable.PW_PWSTRUCTURENO, this.mwStructureNo == null ? JSONObject.NULL : this.mwStructureNo);
        json.put(PwTable.PW_PW01, this.mw01 == null ? JSONObject.NULL : this.mw01);
        json.put(PwTable.PW_PW02, this.mw02 == null ? JSONObject.NULL : this.mw02);
        json.put(PwTable.PW_PW03, this.mw03 == null ? JSONObject.NULL : this.mw03);
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
        public static final String PW_ID = "_id";
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
