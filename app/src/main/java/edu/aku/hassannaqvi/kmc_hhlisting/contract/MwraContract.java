package edu.aku.hassannaqvi.kmc_hhlisting.contract;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hasan on 1/7/2017.
 */

public class MwraContract {

    public Long ID;
    public String MWRAID;
    public String UUID;
    public String UID;
    public String mwDT;
    public String mwVillageCode;
    public String mwStructureNo;
    public String mw01;
    public String mw02;
    public String mw03;
    public String mw04;
    public String mw05;

    private String synced = "";
    private String synced_date = "";

    public MwraContract() {
    }

    public MwraContract(String MWRAID) {
        this.MWRAID = MWRAID;
    }

    public MwraContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getLong(MwraEntry.COLUMN_ID);
        this.MWRAID = jsonObject.getString(MwraEntry.MWRA_ID);
        this.UUID = jsonObject.getString(MwraEntry.MWRA_UUID);
        this.UID = jsonObject.getString(MwraEntry.MWRA_UID);
        this.mwDT = jsonObject.getString(MwraEntry.MWRA_MWDT);
        this.mwVillageCode = jsonObject.getString(MwraEntry.MWRA_MWVILLAGECODE);
        this.mwStructureNo = jsonObject.getString(MwraEntry.MWRA_MWSTRUCTURENO);
        this.mw01 = jsonObject.getString(MwraEntry.MWRA_MW01);
        this.mw02 = jsonObject.getString(MwraEntry.MWRA_MW02);
        this.mw03 = jsonObject.getString(MwraEntry.MWRA_MW03);
        this.mw04 = jsonObject.getString(MwraEntry.MWRA_MW04);
        this.mw05 = jsonObject.getString(MwraEntry.MWRA_MW05);

/*        this.synced = jsonObject.getString(MwraEntry.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(MwraEntry.COLUMN_SYNCED_DATE);*/

        return this;
    }

    public MwraContract Hydrate(Cursor cursor) {
        this.ID = cursor.getLong(cursor.getColumnIndex(MwraEntry.COLUMN_ID));
        this.MWRAID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_ID));
        this.UUID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_UUID));
        this.UID = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_UID));
        this.mwDT = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MWDT));
        this.mwVillageCode = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MWVILLAGECODE));
        this.mwStructureNo = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MWSTRUCTURENO));
        this.mw01 = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MW01));
        this.mw02 = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MW02));
        this.mw03 = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MW03));
        this.mw04 = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MW04));
        this.mw05 = cursor.getString(cursor.getColumnIndex(MwraEntry.MWRA_MW05));

/*        this.synced = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_SYNCED));
        this.synced_date = cursor.getString(cursor.getColumnIndex(MwraEntry.COLUMN_SYNCED_DATE));*/

        return this;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getMWRAID() {
        return MWRAID;
    }

    public void setMWRAID(String MWRAID) {
        this.MWRAID = MWRAID;
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

    public String getMw04() {
        return mw04;
    }

    public void setMw04(String mw04) {
        this.mw04 = mw04;
    }

    public String getMw05() {
        return mw05;
    }

    public void setMw05(String mw05) {
        this.mw05 = mw05;
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

        json.put(MwraEntry.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(MwraEntry.MWRA_ID, this.MWRAID == null ? JSONObject.NULL : this.MWRAID);
        json.put(MwraEntry.MWRA_UUID, this.UUID == null ? JSONObject.NULL : this.UUID);
        json.put(MwraEntry.MWRA_UID, this.UID == null ? JSONObject.NULL : this.UID);
        json.put(MwraEntry.MWRA_MWDT, this.mwDT == null ? JSONObject.NULL : this.mwDT);
        json.put(MwraEntry.MWRA_MWVILLAGECODE, this.mwVillageCode == null ? JSONObject.NULL : this.mwVillageCode);
        json.put(MwraEntry.MWRA_MWSTRUCTURENO, this.mwStructureNo == null ? JSONObject.NULL : this.mwStructureNo);
        json.put(MwraEntry.MWRA_MW01, this.mw01 == null ? JSONObject.NULL : this.mw01);
        json.put(MwraEntry.MWRA_MW02, this.mw02 == null ? JSONObject.NULL : this.mw02);
        json.put(MwraEntry.MWRA_MW03, this.mw03 == null ? JSONObject.NULL : this.mw03);
        json.put(MwraEntry.MWRA_MW04, this.mw04 == null ? JSONObject.NULL : this.mw04);
        json.put(MwraEntry.MWRA_MW05, this.mw05 == null ? JSONObject.NULL : this.mw05);

/*        json.put(MwraEntry.COLUMN_SYNCED, this.synced == null ? JSONObject.NULL : this.synced);
        json.put(MwraEntry.COLUMN_SYNCED_DATE, this.synced_date == null ? JSONObject.NULL : this.synced_date);*/

        return json;

    }

    public static abstract class MwraEntry implements BaseColumns {

        public static final String TABLE_NAME = "mwra";
        public static final String MWRA_NULLABLE = "NULLHACK";
        public static final String COLUMN_ID = "_id";
        public static final String MWRA_ID = "sno";
        public static final String MWRA_UUID = "uuid";
        public static final String MWRA_UID = "uid";
        public static final String MWRA_MWDT = "mwdt";
        public static final String MWRA_MWVILLAGECODE = "mwvillagecode";
        public static final String MWRA_MWSTRUCTURENO = "mwstructureno";
        public static final String MWRA_MW01 = "mw01";
        public static final String MWRA_MW02 = "mw02";
        public static final String MWRA_MW03 = "mw03";
        public static final String MWRA_MW04 = "mw04";
        public static final String MWRA_MW05 = "mw05";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

        public static String _URL = "mwras.php";
    }
}
