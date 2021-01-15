package edu.aku.hassannaqvi.naunehal_listing.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/31/2016.
 */

public class ClustersContract {

    private String clusterCode;
    private String clusterName;
    private String ucCode;


    public ClustersContract() {
    }

    public ClustersContract sync(JSONObject jsonObject) throws JSONException {
        this.clusterCode = jsonObject.getString(TableClusters.COLUMN_CLUSTER_CODE);
        this.clusterName = jsonObject.getString(TableClusters.COLUMN_CLUSTER_NAME);
        this.ucCode = jsonObject.getString(TableClusters.COLUMN_UC_CODE);


        return this;
    }

    public ClustersContract hydrate(Cursor cursor) {
        this.clusterCode = cursor.getString(cursor.getColumnIndex(TableClusters.COLUMN_CLUSTER_CODE));
        this.clusterName = cursor.getString(cursor.getColumnIndex(TableClusters.COLUMN_CLUSTER_NAME));
        this.ucCode = cursor.getString(cursor.getColumnIndex(TableClusters.COLUMN_UC_CODE));

        return this;
    }

    public String getClusterCode() {
        return clusterCode;
    }

    public void setClustercode(String clusterCode) {
        this.clusterCode = clusterCode;
    }

    public String getClustername() {
        return clusterName;
    }

    public void setClustername(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getUcCode() {
        return ucCode;
    }

    public void setUcCode(String ucCode) {
        this.ucCode = ucCode;
    }

    public static abstract class TableClusters implements BaseColumns {

        public static final String TABLE_NAME = "Clusters";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_id";
        public static final String COLUMN_CLUSTER_CODE = "clusterCode";
        public static final String COLUMN_CLUSTER_NAME = "clusterName";
        public static final String COLUMN_UC_CODE = "ucCode";

    }

}
