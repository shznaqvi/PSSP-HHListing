package edu.aku.hassannaqvi.kmc2_hhl_validation.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class VerticesUCContract {

    private String uc_code;
    private Double poly_lat;
    private Double poly_lng;
    private String poly_seq;


    public VerticesUCContract() {
    }

    public VerticesUCContract sync(JSONObject jsonObject) throws JSONException {
        this.uc_code = jsonObject.getString(singleVerticesUC.COLUMN_UC_CODE);
        this.poly_lat = jsonObject.getDouble(singleVerticesUC.COLUMN_POLY_LAT);
        this.poly_lng = jsonObject.getDouble(singleVerticesUC.COLUMN_POLY_LANG);
        this.poly_seq = jsonObject.getString(singleVerticesUC.COLUMN_POLY_SEQ);


        return this;
    }

    public VerticesUCContract hydrate(Cursor cursor) {
        this.uc_code = cursor.getString(cursor.getColumnIndex(singleVerticesUC.COLUMN_UC_CODE));
        this.poly_lat = cursor.getDouble(cursor.getColumnIndex(singleVerticesUC.COLUMN_POLY_LAT));
        this.poly_lng = cursor.getDouble(cursor.getColumnIndex(singleVerticesUC.COLUMN_POLY_LANG));
        this.poly_seq = cursor.getString(cursor.getColumnIndex(singleVerticesUC.COLUMN_POLY_SEQ));

        return this;
    }

    public String getUc_code() {
        return uc_code;
    }

    public void setUc_code(String uc_code) {
        this.uc_code = uc_code;
    }

    public Double getPoly_lat() {
        return poly_lat;
    }

    public void setPoly_lat(Double poly_lat) {
        this.poly_lat = poly_lat;
    }

    public Double getPoly_lng() {
        return poly_lng;
    }

    public void setPoly_lng(Double poly_lng) {
        this.poly_lng = poly_lng;
    }

    public String getPoly_seq() {
        return poly_seq;
    }

    public void setPoly_seq(String poly_seq) {
        this.poly_seq = poly_seq;
    }

    public static abstract class singleVerticesUC implements BaseColumns {

        public static final String TABLE_NAME = "VerticesUC";
        public static final String COLUMN_NAME_NULLABLE = "nullColumnHack";
        public static final String _ID = "_ID";
        public static final String COLUMN_UC_CODE = "uc_code";
        public static final String COLUMN_POLY_LAT = "poly_lat";
        public static final String COLUMN_POLY_LANG = "poly_lng";
        public static final String COLUMN_POLY_SEQ = "poly_seq";

        public static final String _URL = "verticesuc.php";
    }

}
