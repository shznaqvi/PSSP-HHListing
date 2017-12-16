package edu.aku.hassannaqvi.willows_hhlisting.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class MwraContract {

    public String ID;
    public String user;
    public String UUID;
    public String UID;
    public String mwDT;
    public String mwDistrictCode;
    public String mwPSUNo;
    public String tagID;
    public String appversion;
    public String name;
    public String agey;
    public String deviceid;

    public String mwraID;
    public String structureNo;

    public MwraContract() {
    }

    public MwraContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getString(MwraEntry.COLUMN_ID);
        this.user = jsonObject.getString(MwraEntry.COLUMN_USER_NAME);
        this.UUID = jsonObject.getString(MwraEntry.MWRA_UUID);
        this.UID = jsonObject.getString(MwraEntry.MWRA_UID);
        this.mwDT = jsonObject.getString(MwraEntry.MWRA_MWDT);
        this.mwDistrictCode = jsonObject.getString(MwraEntry.MWRA_DISTRICT_CODE);
        this.mwPSUNo = jsonObject.getString(MwraEntry.MWRA_PSU_CODE);
        this.tagID = jsonObject.getString(MwraEntry.COLUMN_TAGID);
        this.appversion = jsonObject.getString(MwraEntry.COLUMN_APPVERSION);
        this.name = jsonObject.getString(MwraEntry.MWRA_NAME);
        this.agey = jsonObject.getString(MwraEntry.MWRA_AGE_Y);
        this.deviceid = jsonObject.getString(MwraEntry.COLUMN_DEVICEID);
        this.mwraID = jsonObject.getString(MwraEntry.MWRA_ID);
        this.structureNo = jsonObject.getString(MwraEntry.MWRA_STRUCTURE_NO);

        return this;
    }

    public MwraContract Hydrate(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_ID));
        this.user = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_USER_NAME));
        this.UUID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_UID));
        this.mwDT = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MWDT));
        this.mwDistrictCode = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_DISTRICT_CODE));
        this.mwPSUNo = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_PSU_CODE));
        this.tagID = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_TAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_APPVERSION));
        this.name = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_NAME));
        this.agey = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_AGE_Y));
        this.deviceid = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_DEVICEID));
        this.mwraID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_ID));
        this.structureNo = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_STRUCTURE_NO));

        return this;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getMwDistrictCode() {
        return mwDistrictCode;
    }

    public void setMwDistrictCode(String mwDistrictCode) {
        this.mwDistrictCode = mwDistrictCode;
    }

    public String getMwPSUNo() {
        return mwPSUNo;
    }

    public void setMwPSUNo(String mwPSUNo) {
        this.mwPSUNo = mwPSUNo;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgey() {
        return agey;
    }

    public void setAgey(String agey) {
        this.agey = agey;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getMwraID() {
        return mwraID;
    }

    public void setMwraID(String mwraID) {
        this.mwraID = mwraID;
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(MwraEntry.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(MwraEntry.COLUMN_USER_NAME, this.user == null ? JSONObject.NULL : this.user);
        json.put(MwraEntry.MWRA_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(MwraEntry.MWRA_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(MwraEntry.MWRA_MWDT, this.mwDT == null ? JSONObject.NULL : this.mwDT);
        json.put(MwraEntry.MWRA_DISTRICT_CODE, this.mwDistrictCode == null ? JSONObject.NULL : this.mwDistrictCode);
        json.put(MwraEntry.MWRA_PSU_CODE, this.mwPSUNo == null ? JSONObject.NULL : this.mwPSUNo);
        json.put(MwraEntry.COLUMN_TAGID, this.tagID == null ? JSONObject.NULL : this.tagID);
        json.put(MwraEntry.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);
        json.put(MwraEntry.MWRA_NAME, this.name == null ? JSONObject.NULL : this.name);
        json.put(MwraEntry.MWRA_AGE_Y, this.agey == null ? JSONObject.NULL : this.agey);
        json.put(MwraEntry.COLUMN_DEVICEID, this.deviceid == null ? JSONObject.NULL : this.deviceid);
        json.put(MwraEntry.MWRA_ID, this.mwraID == null ? JSONObject.NULL : this.mwraID);
        json.put(MwraEntry.MWRA_STRUCTURE_NO, this.structureNo == null ? JSONObject.NULL : this.structureNo);

        return json;

    }

    public static abstract class MwraEntry implements BaseColumns {

        public static final String TABLE_NAME = "mwra";
        public static final String MWRA_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_NAME = "username";
        public static final String MWRA_UUID = "uuid";
        public static final String MWRA_UID = "uid";
        public static final String MWRA_MWDT = "mwdt";
        public static final String MWRA_DISTRICT_CODE = "district_code";
        public static final String MWRA_PSU_CODE = "psu_code";
        public static final String COLUMN_TAGID = "tagid";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String MWRA_NAME = "mwname";
        public static final String MWRA_AGE_Y = "mwagey";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String MWRA_ID = "sno";
        public static final String MWRA_STRUCTURE_NO = "mwstructureno";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

        public static String _URL = "mwras.php";
    }
}
