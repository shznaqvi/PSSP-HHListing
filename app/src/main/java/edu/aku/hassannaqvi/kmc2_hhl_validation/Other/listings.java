package edu.aku.hassannaqvi.kmc2_hhl_validation.Other;

import android.content.Context;

import java.util.ArrayList;

import edu.aku.hassannaqvi.kmc2_hhl_validation.core.pssphhlDBHelperController;

public class listings extends pssphhlDBHelperController {
    public static final String _id = "_id";
    public static final String uid = "uid";
    public static final String hhdt = "hhdt";
    public static final String hh01 = "hh01";
    public static final String hh02 = "hh02";
    public static final String hh03 = "hh03";
    public static final String hh04 = "hh04";
    public static final String hh04x = "hh04x";
    public static final String hh05 = "hh05";
    public static final String hh06 = "hh06";
    public static final String hh07 = "hh07";
    public static final String hh08 = "hh08";
    public static final String hh09 = "hh09";
    public static final String hh10 = "hh10";
    public static final String hh11 = "hh11";
    public static final String child_name = "child_name";
    public static final String deviceid = "deviceid";
    private final String TABLE_NAME = "listings";
    private Context context;

    public listings(Context context) {
        this.context = context;
    }

    public void insert(Integer _id, String uid, String hhdt, String hh01, String hh02, String hh03, String hh04, String hh04x, String hh05, String hh06, String hh07, String hh08, String hh09, String hh10, String hh11, String child_name, String deviceid) {
        uid = uid != null ? "\"" + uid + "\"" : null;
        hhdt = hhdt != null ? "\"" + hhdt + "\"" : null;
        hh01 = hh01 != null ? "\"" + hh01 + "\"" : null;
        hh02 = hh02 != null ? "\"" + hh02 + "\"" : null;
        hh03 = hh03 != null ? "\"" + hh03 + "\"" : null;
        hh04 = hh04 != null ? "\"" + hh04 + "\"" : null;
        hh04x = hh04x != null ? "\"" + hh04x + "\"" : null;
        hh05 = hh05 != null ? "\"" + hh05 + "\"" : null;
        hh06 = hh06 != null ? "\"" + hh06 + "\"" : null;
        hh07 = hh07 != null ? "\"" + hh07 + "\"" : null;
        hh08 = hh08 != null ? "\"" + hh08 + "\"" : null;
        hh09 = hh09 != null ? "\"" + hh09 + "\"" : null;
        hh10 = hh10 != null ? "\"" + hh10 + "\"" : null;
        hh11 = hh11 != null ? "\"" + hh11 + "\"" : null;
        child_name = child_name != null ? "\"" + child_name + "\"" : null;
        deviceid = deviceid != null ? "\"" + deviceid + "\"" : null;

        Object[] values_ar = {_id, uid, hhdt, hh01, hh02, hh03, hh04, hh04x, hh05, hh06, hh07, hh08, hh09, hh10, hh11, child_name, deviceid};
        String[] fields_ar = {listings._id, listings.uid, listings.hhdt, listings.hh01, listings.hh02, listings.hh03, listings.hh04, listings.hh04x, listings.hh05, listings.hh06, listings.hh07, listings.hh08, listings.hh09, listings.hh10, listings.hh11, listings.child_name, listings.deviceid};
        String values = "", fields = "";
        for (int i = 0; i < values_ar.length; i++) {
            if (values_ar[i] != null) {
                values += values_ar[i] + ", ";
                fields += fields_ar[i] + ", ";
            }
        }
        if (!values.isEmpty()) {
            values = values.substring(0, values.length() - 2);
            fields = fields.substring(0, fields.length() - 2);
            super.execute(context, "INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
        }
    }

    public void delete(String whatField, String whatValue) {
        super.delete(context, TABLE_NAME, whatField + " = " + whatValue);
    }

    public void update(String whatField, String whatValue, String whereField, String whereValue) {
        super.execute(context, "UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
    }

    public ArrayList<ArrayList<String>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
        String query = "SELECT ";
        query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
        query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
        query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
        return super.executeQuery(context, query);
    }

    public ArrayList<ArrayList<String>> getExecuteResult(String query) {
        return super.executeQuery(context, query);
    }

    public void execute(String query) {
        super.execute(context, query);
    }

}