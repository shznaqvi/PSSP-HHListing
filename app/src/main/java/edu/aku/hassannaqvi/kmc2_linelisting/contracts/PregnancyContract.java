package edu.aku.hassannaqvi.kmc2_linelisting.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class PregnancyContract {

    private String ID;
    private String hh01;
    private String hh02;
    private String hh03;
    private String hh07;
    private String uuid;
    private String uid;
    private String hhDT;
    private String user;
    private String deviceid;
    private String devicetagid;
    private String app_ver;
    private String synced;
    private String sync_date;
    private String hh16;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHhDT() {
        return hhDT;
    }

    public void setHhDT(String hhDT) {
        this.hhDT = hhDT;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetagid() {
        return devicetagid;
    }

    public void setDevicetagid(String devicetagid) {
        this.devicetagid = devicetagid;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSync_date() {
        return sync_date;
    }

    public void setSync_date(String sync_date) {
        this.sync_date = sync_date;
    }

    public String getHh16() {
        return hh16;
    }

    public void setHh16(String hh16) {
        this.hh16 = hh16;
    }

    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
    }

    public String getHh07() {
        return hh07;
    }

    public void setHh07(String hh07) {
        this.hh07 = hh07;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(singlePREG.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(singlePREG.COLUMN_HH01, this.hh01 == null ? JSONObject.NULL : this.hh01);
        json.put(singlePREG.COLUMN_HH02, this.hh02 == null ? JSONObject.NULL : this.hh02);
        json.put(singlePREG.COLUMN_HH03, this.hh03 == null ? JSONObject.NULL : this.hh03);
        json.put(singlePREG.COLUMN_HH07, this.hh07 == null ? JSONObject.NULL : this.hh07);
        json.put(singlePREG.COLUMN_UUID, this.uuid == null ? JSONObject.NULL : this.uuid);
        json.put(singlePREG.COLUMN_UID, this.uid == null ? JSONObject.NULL : this.uid);
        json.put(singlePREG.COLUMN_HHDT, this.hhDT == null ? JSONObject.NULL : this.hhDT);
        json.put(singlePREG.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(singlePREG.COLUMN_DEVICEID, this.deviceid == null ? JSONObject.NULL : this.deviceid);
        json.put(singlePREG.COLUMN_DEVICETAGID, this.devicetagid == null ? JSONObject.NULL : this.devicetagid);
        json.put(singlePREG.COLUMN_APP_VER, this.app_ver == null ? JSONObject.NULL : this.app_ver);
        json.put(singlePREG.COLUMN_HH16, this.hh16 == null ? JSONObject.NULL : this.hh16);

        json.put(singlePREG.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(singlePREG.COLUMN_SYNC_DATE, this.sync_date == null ? JSONObject.NULL : this.sync_date);

        return json;
    }

    public PregnancyContract hydrate(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_ID));
        this.hh01 = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HH01));
        this.hh02 = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HH02));
        this.hh03 = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HH03));
        this.hh07 = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HH07));
        this.uuid = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_UUID));
        this.uid = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_UID));
        this.hhDT = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HHDT));
        this.user = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_USER));
        this.deviceid = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_DEVICEID));
        this.devicetagid = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_DEVICETAGID));
        this.app_ver = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_APP_VER));
        this.synced = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_SYNCED));
        this.sync_date = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_SYNC_DATE));
        this.hh16 = cursor.getString(cursor.getColumnIndex(singlePREG.COLUMN_HH16));

        return this;
    }

    public static abstract class singlePREG implements BaseColumns {

        public static final String TABLE_NAME = "pregnancy";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_HH01 = "hh01";
        public static final String COLUMN_HH02 = "hh02";
        public static final String COLUMN_HH03 = "hh03";
        public static final String COLUMN_HH07 = "hh07";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_HHDT = "hhdt";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_APP_VER = "app_ver";
        public static final String COLUMN_HH16 = "hh16";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNC_DATE = "sync_date";

        public static final String _URL = "pregnancy.php";
    }
}