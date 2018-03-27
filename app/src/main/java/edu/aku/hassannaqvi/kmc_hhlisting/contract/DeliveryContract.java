package edu.aku.hassannaqvi.kmc_hhlisting.contract;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.kmc_hhlisting.R;

/**
 * Created by javed.khan on 1/22/2018.
 */

public class DeliveryContract {


    private final String projectName = String.valueOf(R.string.app_name);
    private String _ID = "";
    private String _UID = "";
    private String _UUID = "";
    private String formDate = ""; // Date
    private String user = ""; // Interviewer
    private String d1SerialNo = "";

    private String sD1 = ""; // sB
/*    private String sC2 = ""; // sB
    private String sC3 = ""; // sB
    private String sC4 = ""; // sB
    private String sC5 = ""; // sB*/

    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion;

    public DeliveryContract() {

    }


    public DeliveryContract Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(DeliveryTable.COLUMN_ID);
        this._UID = jsonObject.getString(DeliveryTable.COLUMN_UID);
        this._UUID = jsonObject.getString(DeliveryTable.COLUMN_UUID);
        this.formDate = jsonObject.getString(DeliveryTable.COLUMN_FORMDATE);
        this.user = jsonObject.getString(DeliveryTable.COLUMN_USER);
        this.d1SerialNo = jsonObject.getString(DeliveryTable.COLUMN_D1SerialNo);
        this.sD1 = jsonObject.getString(DeliveryTable.COLUMN_SD1);
/*        this.sC2 = jsonObject.getString(DeliveryTable.COLUMN_SC2);
        this.sC3 = jsonObject.getString(DeliveryTable.COLUMN_SC3);
        this.sC4 = jsonObject.getString(DeliveryTable.COLUMN_SC4);
        this.sC5 = jsonObject.getString(DeliveryTable.COLUMN_SC5);*/
        this.deviceID = jsonObject.getString(DeliveryTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(DeliveryTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(DeliveryTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(DeliveryTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(DeliveryTable.COLUMN_APPVERSION);


        return this;

    }

    public DeliveryContract Hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_UID));
        this._UUID = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_UUID));
        this.formDate = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_FORMDATE));
        this.user = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_USER));
        this.d1SerialNo = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_D1SerialNo));
        this.sD1 = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SD1));
        /*this.sC2 = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SC2));
        this.sC3 = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SC3));
        this.sC4 = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SC4));
        this.sC5 = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SC5));*/
        this.deviceID = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_DEVICETAGID));
        this.synced = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SYNCED));
        this.synced_date = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_SYNCED_DATE));
        this.appversion = cursor.getString(cursor.getColumnIndex(DeliveryTable.COLUMN_APPVERSION));

        // TODO:

        return this;

    }


    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(DeliveryTable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
        json.put(DeliveryTable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
        json.put(DeliveryTable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
        json.put(DeliveryTable.COLUMN_FORMDATE, this.formDate == null ? JSONObject.NULL : this.formDate);
        json.put(DeliveryTable.COLUMN_USER, this.user == null ? JSONObject.NULL : this.user);
        json.put(DeliveryTable.COLUMN_D1SerialNo, this.d1SerialNo == null ? JSONObject.NULL : this.d1SerialNo);

        if (!this.sD1.equals("")) {
            json.put(DeliveryTable.COLUMN_SD1, this.sD1.equals("") ? JSONObject.NULL : new JSONObject(this.sD1));
        }

        /*if (!this.sC2.equals("")) {
            json.put(DeliveryTable.COLUMN_SC2, this.sC2 == null ? JSONObject.NULL : this.sC2);
        }

        if (!this.sC3.equals("")) {
            json.put(DeliveryTable.COLUMN_SC3, this.sC3 == null ? JSONObject.NULL : this.sC3);
        }

        if (!this.sC4.equals("")) {
            json.put(DeliveryTable.COLUMN_SC4, this.sC4 == null ? JSONObject.NULL : this.sC4);
        }

        if (!this.sC5.equals("")) {
            json.put(DeliveryTable.COLUMN_SC5, this.sC5 == null ? JSONObject.NULL : this.sC5);
        }*/

        json.put(DeliveryTable.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put(DeliveryTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
        json.put(DeliveryTable.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(DeliveryTable.COLUMN_SYNCED_DATE, this.synced_date == null ? JSONObject.NULL : this.synced_date);
        json.put(DeliveryTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);


        return json;
    }

    public String getProjectName() {
        return projectName;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getUID() {
        return _UID;
    }

    public void setUID(String _UID) {
        this._UID = _UID;
    }


    public String getUUID() {
        return _UUID;
    }

    public void setUUID(String _UUID) {
        this._UUID = _UUID;
    }


    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


    public String getD1SerialNo() {
        return d1SerialNo;
    }

    public void setd1SerialNo(String d1SerialNo) {
        this.d1SerialNo = d1SerialNo;
    }

    public String getsD1() {
        return sD1;
    }

    public void setsD1(String sD1) {
        this.sD1 = sD1;
    }

    public static abstract class DeliveryTable implements BaseColumns {

        public static final String TABLE_NAME = "delivery";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";

        public static final String COLUMN_PROJECTNAME = "projectname";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "uuid";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_D1SerialNo = "d1SerialNo";
        public static final String COLUMN_SD1 = "sd1";
        /*        public static final String COLUMN_SC2 = "sc2";
                public static final String COLUMN_SC3 = "sc3";
                public static final String COLUMN_SC4 = "sc4";
                public static final String COLUMN_SC5 = "sc5";*/
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "devicetagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";

        public static String _URL = "deliverylisting.php";
    }

}