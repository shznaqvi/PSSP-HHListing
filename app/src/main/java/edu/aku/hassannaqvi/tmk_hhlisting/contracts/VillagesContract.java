package edu.aku.hassannaqvi.tmk_hhlisting.contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VillagesContract {

    private static final String TAG = "Villages_CONTRACT";
    String ID;
    String villagename;
    String uc;
    String taluka;
    String clustercode;

    public VillagesContract() {
        // Default Constructor
    }

    public VillagesContract(VillagesContract l) {
        this.ID = l.getID();
        this.villagename = l.getVillagename();
        this.uc = l.getUc();
        this.taluka = l.getTaluka();
        this.clustercode = l.getClustercode();
    }

    public VillagesContract Sync(JSONObject jsonObject) throws JSONException {
        this.ID = jsonObject.getString(singleVillages.COLUMN_ID);
        this.villagename = jsonObject.getString(singleVillages.COLUMN_VILLAGE_NAME);
        this.uc = jsonObject.getString(singleVillages.COLUMN_UC);
        this.taluka = jsonObject.getString(singleVillages.COLUMN_TALUKA);
        this.clustercode = jsonObject.getString(singleVillages.COLUMN_CLUSTER_CODE);

        return this;

    }

    public VillagesContract HydratePara(Cursor cursor) {
        this.uc = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_UC));
        this.taluka = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_TALUKA));
        return this;
    }

    public VillagesContract HydrateVillages(Cursor cursor) {
        this.ID = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_ID));
        this.uc = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_UC));
        this.taluka = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_TALUKA));
        this.villagename = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_VILLAGE_NAME));
        this.clustercode = cursor.getString(cursor.getColumnIndex(singleVillages.COLUMN_CLUSTER_CODE));
        return this;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getVillagename() {
        return villagename;
    }

    public void setVillagename(String villagename) {
        this.villagename = villagename;
    }

    public String getUc() {
        return uc;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getClustercode() {
        return clustercode;
    }

    public void setClustercode(String clustercode) {
        this.clustercode = clustercode;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(singleVillages.COLUMN_ID, this.ID == null ? JSONObject.NULL : this.ID);
        json.put(singleVillages.COLUMN_VILLAGE_NAME, this.villagename == null ? JSONObject.NULL : this.villagename);
        json.put(singleVillages.COLUMN_UC, this.uc == null ? JSONObject.NULL : this.uc);
        json.put(singleVillages.COLUMN_TALUKA, this.taluka == null ? JSONObject.NULL : this.taluka);
        json.put(singleVillages.COLUMN_CLUSTER_CODE, this.clustercode == null ? JSONObject.NULL : this.clustercode);

        return json;
    }


    public static abstract class singleVillages implements BaseColumns {

        public static final String TABLE_NAME = "villages";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_VILLAGE_NAME = "village_name";
        public static final String COLUMN_UC = "union_council";
        public static final String COLUMN_TALUKA = "taluka";
        public static final String COLUMN_CLUSTER_CODE = "cluster_code";

        public static final String _URI = "villages.php";
    }
}