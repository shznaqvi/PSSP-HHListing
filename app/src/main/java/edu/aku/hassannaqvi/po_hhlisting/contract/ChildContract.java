package edu.aku.hassannaqvi.po_hhlisting.contract;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class ChildContract {

    public String ID;
    public String CHILDID;
    public String UUID;
    public String UID;
    public String chDT;
    private String deviceID = "";

    //public String chVillageCode;
    //public String chStructureNo;

    public String hhno;
    public String extno;
    public String ch01;
    public String ch02;
    public String ch03;
    public String ch04;
    public String ch05;
    public String child;

    private String synced = "";
    private String synced_date = "";

    public ChildContract() {
    }

    public ChildContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getString(ChildContract.ChildEntry.COLUMN_ID);
        this.CHILDID = jsonObject.getString(ChildContract.ChildEntry.CHILD_ID);
        this.deviceID = jsonObject.getString(ChildContract.ChildEntry.CHILD_DEVICEID);
        this.UUID = jsonObject.getString(ChildContract.ChildEntry.CHILD_UUID);
        this.UID = jsonObject.getString(ChildContract.ChildEntry.CHILD_UID);
        this.chDT = jsonObject.getString(ChildContract.ChildEntry.CHILD_CHDT);
        //this.chVillageCode = jsonObject.getString(ChildContract.ChildEntry.CHILD_CHVILLAGECODE);
        //this.chStructureNo = jsonObject.getString(ChildContract.ChildEntry.CHILD_CHSTRUCTURENO);
        this.hhno = jsonObject.getString(ChildContract.ChildEntry.CHILD_HHNO);
        this.extno = jsonObject.getString(ChildContract.ChildEntry.CHILD_EXTNO);
        this.ch01 = jsonObject.getString(ChildContract.ChildEntry.CHILD_CH01);
        this.ch02 = jsonObject.getString(ChildContract.ChildEntry.CHILD_CH02);
        this.ch03 = jsonObject.getString(ChildContract.ChildEntry.CHILD_CH03);
        this.ch04 = jsonObject.getString(ChildContract.ChildEntry.CHILD_CH04);
        this.ch05 = jsonObject.getString(ChildContract.ChildEntry.CHILD_CH05);
        this.child = jsonObject.getString(ChildContract.ChildEntry.CHILD_CHILD);

/*        this.synced = jsonObject.getString(MwraEntry.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(MwraEntry.COLUMN_SYNCED_DATE);*/

        return this;
    }

    public ChildContract Hydrate(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.COLUMN_ID));
        this.CHILDID = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_UID));
        this.deviceID = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_DEVICEID));
        this.chDT = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CHDT));
        //this.chVillageCode = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CHVILLAGECODE));
        //this.chStructureNo = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CHSTRUCTURENO));
        this.hhno = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_HHNO));
        this.extno = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_EXTNO));
        this.ch01 = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CH01));
        this.ch02 = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CH02));
        this.ch03 = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CH03));
        this.ch04 = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CH04));
        this.ch05 = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CH05));
        this.child = cursor.getString(cursor.getColumnIndex(ChildContract.ChildEntry.CHILD_CHILD));

/*        this.synced = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_SYNCED));
        this.synced_date = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_SYNCED_DATE));*/

        return this;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCHILDID() {
        return CHILDID;
    }

    public void setCHILDID(String MWRAID) {
        this.CHILDID = CHILDID;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
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

    public String getChDT() {
        return chDT;
    }

    public void setChDT(String chDT) {
        this.chDT = chDT;
    }

    /*public String getChVillageCode() {
        return chVillageCode;
    }

    public void setChVillageCode(String chVillageCode) {
        this.chVillageCode = chVillageCode;
    }*/

    /*public String getChStructureNo() {
        return chStructureNo;
    }

    public void setChStructureNo(String chStructureNo) {
        this.chStructureNo = chStructureNo;
    }*/


    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }


    public String getCh01() {
        return ch01;
    }

    public void setCh01(String ch01) {
        this.ch01 = ch01;
    }

    public String getCh02() {
        return ch02;
    }

    public void setCh02(String ch02) {
        this.ch02 = ch02;
    }

    public String getCh03() {
        return ch03;
    }

    public void setCh03(String ch03) {
        this.ch03 = ch03;
    }

    public String getCh04() {
        return ch04;
    }

    public void setCh04(String ch04) {
        this.ch04 = ch04;
    }

    public String getCh05() {
        return ch05;
    }

    public void setCh05(String ch05) {
        this.ch05 = ch05;
    }


    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }


    public String getExtno() {
        return hhno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
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

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(ChildContract.ChildEntry.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(ChildContract.ChildEntry.CHILD_ID, this.CHILDID == null ? JSONObject.NULL : this.CHILDID);
        json.put(ChildContract.ChildEntry.CHILD_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(ChildContract.ChildEntry.CHILD_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(ChildContract.ChildEntry.CHILD_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
        json.put(ChildContract.ChildEntry.CHILD_CHDT, this.chDT == null ? JSONObject.NULL : this.chDT);
        //json.put(ChildContract.ChildEntry.CHILD_CHVILLAGECODE, this.chVillageCode == null ? JSONObject.NULL : this.chVillageCode);
        //json.put(ChildContract.ChildEntry.CHILD_CHSTRUCTURENO, this.chStructureNo == null ? JSONObject.NULL : this.chStructureNo);
        json.put(ChildContract.ChildEntry.CHILD_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
        json.put(ChildContract.ChildEntry.CHILD_EXTNO, this.extno == null ? JSONObject.NULL : this.extno);
        json.put(ChildContract.ChildEntry.CHILD_CH01, this.ch01 == null ? JSONObject.NULL : this.ch01);
        json.put(ChildContract.ChildEntry.CHILD_CH02, this.ch02 == null ? JSONObject.NULL : this.ch02);
        json.put(ChildContract.ChildEntry.CHILD_CH03, this.ch03 == null ? JSONObject.NULL : this.ch03);
        json.put(ChildContract.ChildEntry.CHILD_CH04, this.ch04 == null ? JSONObject.NULL : this.ch04);
        json.put(ChildContract.ChildEntry.CHILD_CH05, this.ch05 == null ? JSONObject.NULL : this.ch05);
        json.put(ChildContract.ChildEntry.CHILD_CHILD, this.child == null ? JSONObject.NULL : this.child);

/*        json.put(MwraEntry.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(MwraEntry.COLUMN_SYNCED_DATE, this.synced_date == null ? JSONObject.NULL : this.synced_date);*/

        return json;

    }

    public static abstract class ChildEntry implements BaseColumns {

        public static final String TABLE_NAME = "child";
        public static final String CH_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "_id";
        public static final String CHILD_ID = "sno";
        public static final String CHILD_UUID = "uuid";
        public static final String CHILD_UID = "uid";
        public static final String CHILD_DEVICEID = "deviceid";
        public static final String CHILD_CHDT = "chdt";
        //public static final String CHILD_CHVILLAGECODE = "chvillagecode";
        //public static final String CHILD_CHSTRUCTURENO = "chstructureno";
        public static final String CHILD_HHNO = "hhno";
        public static final String CHILD_EXTNO = "extno";
        public static final String CHILD_CH01 = "ch01";
        public static final String CHILD_CH02 = "ch02";
        public static final String CHILD_CH03 = "ch03";
        public static final String CHILD_CH04 = "ch04";
        public static final String CHILD_CH05 = "ch05";
        public static final String CHILD_CHILD = "child";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

        public static String _URL = "child.php";
    }

}