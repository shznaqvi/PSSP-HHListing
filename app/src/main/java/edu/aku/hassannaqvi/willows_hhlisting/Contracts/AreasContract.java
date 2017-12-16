package edu.aku.hassannaqvi.willows_hhlisting.Contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class AreasContract {

    private String ID;
    private String deviceid;
    private String uid;
    private String formdate;
    private String username;
    private String district_code;
    private String psu_code;
    private String tagid;
    private String appversion;
    private String areaname;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getPsu_code() {
        return psu_code;
    }

    public void setPsu_code(String psu_code) {
        this.psu_code = psu_code;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public AreasContract() {
    }

    public AreasContract sync(JSONObject jsonObject) throws JSONException {
        this.ID= jsonObject.getString(singleAreas.COLUMN_ID);
        this.deviceid= jsonObject.getString(singleAreas.COLUMN_DEVICEID);
        this.uid= jsonObject.getString(singleAreas.COLUMN_UID);
        this.formdate= jsonObject.getString(singleAreas.COLUMN_FORMDATE);
        this.username= jsonObject.getString(singleAreas.COLUMN_USERNAME);
        this.district_code= jsonObject.getString(singleAreas.COLUMN_DISTRICT_CODE);
        this.psu_code = jsonObject.getString(singleAreas.COLUMN_PSU_CODE);
        this.tagid= jsonObject.getString(singleAreas.COLUMN_TAGID);
        this.appversion= jsonObject.getString(singleAreas.COLUMN_APPVERSION);
        this.areaname= jsonObject.getString(singleAreas.COLUMN_AREANAME);

        return this;
    }

    public AreasContract hydrate(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_ID));
        this.deviceid = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_DEVICEID));
        this.uid = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_UID));
        this.formdate = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_FORMDATE));
        this.username = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_USERNAME));
        this.district_code = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_DISTRICT_CODE));
        this.psu_code = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_PSU_CODE));
        this.tagid = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_TAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_APPVERSION));
        this.areaname = cursor.getString(cursor.getColumnIndex(singleAreas.COLUMN_AREANAME));

        return this;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(singleAreas.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(singleAreas.COLUMN_DEVICEID, this.deviceid == null ? JSONObject.NULL : this.deviceid);
        json.put(singleAreas.COLUMN_UID, this.uid == null ? JSONObject.NULL : this.uid);
        json.put(singleAreas.COLUMN_FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
        json.put(singleAreas.COLUMN_USERNAME, this.username == null ? JSONObject.NULL : this.username);
        json.put(singleAreas.COLUMN_DISTRICT_CODE, this.district_code == null ? JSONObject.NULL : this.district_code);
        json.put(singleAreas.COLUMN_PSU_CODE, this.psu_code == null ? JSONObject.NULL : this.psu_code);
        json.put(singleAreas.COLUMN_TAGID, this.tagid == null ? JSONObject.NULL : this.tagid);
        json.put(singleAreas.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);
        json.put(singleAreas.COLUMN_AREANAME, this.areaname == null ? JSONObject.NULL : this.areaname);

        return json;
    }


    public static abstract class singleAreas implements BaseColumns {

        public static final String TABLE_NAME = "Areas";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_AREANAME = "area";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_USERNAME = "user";
        public static final String COLUMN_DISTRICT_CODE = "district_code";
        public static final String COLUMN_PSU_CODE = "psu_code";
        public static final String COLUMN_TAGID = "tagid";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

        public static final String _URL = "areas.php";
    }

}
