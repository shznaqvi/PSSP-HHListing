package edu.aku.hassannaqvi.willows_hhlisting.Core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.aku.hassannaqvi.willows_hhlisting.Contracts.AreasContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.AreasContract.singleAreas;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.DistrictsContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.DistrictsContract.singleDistrict;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.ListingContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.ListingContract.ListingEntry;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.MwraContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.MwraContract.MwraEntry;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.PSUsContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.PSUsContract.singlePSU;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.UsersContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.UsersContract.singleUser;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.VerticesContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.VerticesContract.singleVertices;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.VerticesUCContract;
import edu.aku.hassannaqvi.willows_hhlisting.Contracts.VerticesUCContract.singleVerticesUC;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // The name of database.
    public static final String DATABASE_NAME = "willows-hhl.db";
    public static final String DB_NAME = "willows-hhl_copy.db";
    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 6;
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;


    // Create a table to hold Listings.
    final String SQL_CREATE_LISTING_TABLE = "CREATE TABLE " + ListingEntry.TABLE_NAME + " (" +
            ListingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ListingEntry.COLUMN_NAME_UID + " TEXT, " +
            ListingEntry.COLUMN_NAME_HHDATETIME + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH01 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH02 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH03 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH04 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH04x + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH05 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH06 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH07 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH07n + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH08 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH09 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH10 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH11 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH11x + " TEXT, " +
            ListingEntry.COLUMN_NAME_HHADD + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH12 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH12y + " TEXT, " +
            ListingEntry.COLUMN_NAME_WOMEN_NAME + " TEXT, " +
            ListingEntry.COLUMN_USERNAME + " TEXT, " +
            ListingEntry.COLUMN_STATUS + " TEXT, " +
            ListingEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
            ListingEntry.COLUMN_TAGID + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSLat + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSLng + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSTime + " TEXT, " +
            ListingEntry.COLUMN_NAME_ROUND + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSAccuracy + " TEXT, " +
            ListingEntry.COLUMN_SYNCED + " TEXT, " +
            ListingEntry.COLUMN_SYNCED_DATE + " TEXT " +
            " );";

    final String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + singleDistrict.TABLE_NAME + " (" +
            singleDistrict._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleDistrict.COLUMN_DISTRICT_CODE + " TEXT, " +
            singleDistrict.COLUMN_DISTRICT_NAME + " TEXT " +
            ");";

    final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + singlePSU.TABLE_NAME + " (" +
            singlePSU._ID + " TEXT," +
            singlePSU.COLUMN_PSU_CODE + " TEXT, " +
            singlePSU.COLUMN_PSU_NAME + " TEXT, " +
            singlePSU.COLUMN_DISTRICT_CODE + " TEXT, " +
            singlePSU.COLUMN_TYPE + " TEXT " +
            ");";

    private static final String SQL_ALTER_PSU_TABLE = "ALTER TABLE " +
            singlePSU.TABLE_NAME + " ADD COLUMN " +
            singlePSU.COLUMN_TYPE + " TEXT;";

    final String SQL_CREATE_VERTICES_TABLE = "CREATE TABLE " + singleVertices.TABLE_NAME + " (" +
            singleVertices._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleVertices.COLUMN_CLUSTER_CODE + " TEXT," +
            singleVertices.COLUMN_POLY_LAT + " TEXT, " +
            singleVertices.COLUMN_POLY_LANG + " TEXT, " +
            singleVertices.COLUMN_POLY_SEQ + " TEXT " +
            ");";

    final String SQL_CREATE_VERTICESUC_TABLE = "CREATE TABLE " + singleVerticesUC.TABLE_NAME + " (" +
            singleVerticesUC._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleVerticesUC.COLUMN_UC_CODE + " TEXT," +
            singleVerticesUC.COLUMN_POLY_LAT + " TEXT, " +
            singleVerticesUC.COLUMN_POLY_LANG + " TEXT, " +
            singleVerticesUC.COLUMN_POLY_SEQ + " TEXT " +
            ");";

    final String SQL_CREATE_AREA_TABLE = "CREATE TABLE " + singleAreas.TABLE_NAME + " (" +
            singleAreas.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleAreas.COLUMN_DEVICEID + " TEXT," +
            singleAreas.COLUMN_UID + " TEXT," +
            singleAreas.COLUMN_FORMDATE + " TEXT," +
            singleAreas.COLUMN_USERNAME + " TEXT," +
            singleAreas.COLUMN_DISTRICT_CODE + " TEXT," +
            singleAreas.COLUMN_PSU_CODE + " TEXT," +
            singleAreas.COLUMN_AREANAME + " TEXT," +
            singleAreas.COLUMN_TAGID + " TEXT," +
            singleAreas.COLUMN_APPVERSION + " TEXT," +
            singleAreas.COLUMN_SYNCED + " TEXT, " +
            singleAreas.COLUMN_SYNCED_DATE + " TEXT " +
            ");";

    final String SQL_CREATE_USERS = "CREATE TABLE " + singleUser.TABLE_NAME + "("
            + singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleUser.ROW_USERNAME + " TEXT,"
            + singleUser.ROW_PASSWORD + " TEXT );";

    final String SQL_CREATE_MWRA_TABLE = "CREATE TABLE " + MwraEntry.TABLE_NAME + " (" +
            MwraEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MwraEntry.COLUMN_USER_NAME + " TEXT," +
            MwraEntry.MWRA_ID + " TEXT," +
            MwraEntry.MWRA_UID + " TEXT," +
            MwraEntry.MWRA_UUID + " TEXT," +
            MwraEntry.COLUMN_DEVICEID + " TEXT," +
            MwraEntry.MWRA_MWDT + " TEXT," +
            MwraEntry.MWRA_DISTRICT_CODE + " TEXT," +
            MwraEntry.MWRA_PSU_CODE + " TEXT," +
            MwraEntry.MWRA_STRUCTURE_NO + " TEXT," +
            MwraEntry.COLUMN_TAGID + " TEXT," +
            MwraEntry.COLUMN_APPVERSION + " TEXT," +
            MwraEntry.MWRA_NAME + " TEXT," +
            MwraEntry.MWRA_AGE_Y + " TEXT," +
            MwraEntry.COLUMN_SYNCED + " TEXT, " +
            MwraEntry.COLUMN_SYNCED_DATE + " TEXT " +
            " );";

    public FormsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_LISTING_TABLE);
        db.execSQL(SQL_CREATE_DISTRICT_TABLE);
        db.execSQL(SQL_CREATE_PSU_TABLE);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_AREA_TABLE);
        db.execSQL(SQL_CREATE_MWRA_TABLE);
        db.execSQL(SQL_CREATE_VERTICES_TABLE);
        db.execSQL(SQL_CREATE_VERTICESUC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            case 4:
                db.execSQL(SQL_CREATE_VERTICES_TABLE);
                db.execSQL(SQL_CREATE_VERTICESUC_TABLE);
            case 5:
                db.execSQL(SQL_ALTER_PSU_TABLE);
        }

        // Simply discard all old data and start over when upgrading.
        /*db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleDistrict.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singlePSU.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleUser.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleAreas.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MwraEntry.TABLE_NAME);
        onCreate(db);*/
    }

    public Long lastInsertId() {
        Long id = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String qry = "SELECT last_insert_rowid()";
        Cursor c = db.rawQuery(qry, null);
        if (c != null && c.moveToFirst()) {
            id = Long.valueOf(c.getString(0));
            c.close();
        } else {
            id = Long.valueOf(-1);
        }

        return id;
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + singleUser.TABLE_NAME + " WHERE " + singleUser.ROW_USERNAME + "=? AND " + singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_SYNCED, true);
        values.put(ListingEntry.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ListingEntry._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                ListingEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedAreas(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(singleAreas.COLUMN_SYNCED, true);
        values.put(singleAreas.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = singleAreas.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                singleAreas.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedMWRA(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MwraEntry.COLUMN_SYNCED, true);
        values.put(MwraEntry.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MwraEntry.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MwraEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void syncUser(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                String userName = jsonObjectUser.getString(singleUser.ROW_USERNAME);
                String password = jsonObjectUser.getString(singleUser.ROW_PASSWORD);


                ContentValues values = new ContentValues();

                values.put(singleUser.ROW_USERNAME, userName);
                values.put(singleUser.ROW_PASSWORD, password);
                db.insert(singleUser.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {
        }
    }

    public ArrayList<UsersContract> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UsersContract> userList = null;
        try {
            userList = new ArrayList<UsersContract>();
            String QUERY = "SELECT * FROM " + singleUser.TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            int num = cursor.getCount();
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    UsersContract user = new UsersContract();
                    user.setId(cursor.getInt(0));
                    user.setUserName(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    userList.add(user);
                }
            }
            db.close();
        } catch (Exception e) {
        }
        return userList;
    }

    public Long addForm(ListingContract lc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, lc.getUID());
        values.put(ListingEntry.COLUMN_NAME_HHDATETIME, lc.getHhDT());
        values.put(ListingEntry.COLUMN_NAME_HH01, lc.getHh01());
        values.put(ListingEntry.COLUMN_NAME_HH02, lc.getHh02());
        values.put(ListingEntry.COLUMN_NAME_HH03, lc.getHh03());

        AppMain.updatePSU(lc.getHh02(), lc.getHh03());
        Log.d(TAG, "PSUExist (Test): " + AppMain.sharedPref.getString(lc.getHh02(), "0"));

        values.put(ListingEntry.COLUMN_NAME_HH04, lc.getHh04());
        values.put(ListingEntry.COLUMN_NAME_HH04x, lc.getHh04x());
        values.put(ListingEntry.COLUMN_NAME_HH05, lc.getHh05());
        values.put(ListingEntry.COLUMN_NAME_HH06, lc.getHh06());
        values.put(ListingEntry.COLUMN_NAME_HH07, lc.getHh07());
        values.put(ListingEntry.COLUMN_NAME_HH07n, lc.getHh07n());
        values.put(ListingEntry.COLUMN_NAME_HH08, lc.getHh08());
        values.put(ListingEntry.COLUMN_NAME_HH09, lc.getHh09());
        values.put(ListingEntry.COLUMN_NAME_HH10, lc.getHh10());
        values.put(ListingEntry.COLUMN_NAME_HH11, lc.getHh11());
        values.put(ListingEntry.COLUMN_NAME_HH11x, lc.getHh11x());
        values.put(ListingEntry.COLUMN_NAME_HHADD, lc.getHhadd());
        values.put(ListingEntry.COLUMN_NAME_HH12, lc.getHh12());
        values.put(ListingEntry.COLUMN_NAME_HH12y, lc.getHh12y());
        values.put(ListingEntry.COLUMN_NAME_WOMEN_NAME, lc.getHhWomenNm());
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_USERNAME, lc.getUsername());
        values.put(ListingEntry.COLUMN_STATUS, lc.getStatus());
        values.put(ListingEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingEntry.COLUMN_NAME_ROUND, lc.getRound());
        values.put(ListingEntry.COLUMN_TAGID, lc.getTagId());

        long newRowId;
        newRowId = db.insert(
                ListingEntry.TABLE_NAME,
                ListingEntry.COLUMN_NAME_NULLABLE,
                values);
        DB_FORM_ID = String.valueOf(newRowId);

        return newRowId;
    }

    public Long addAreas(AreasContract chw) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(singleAreas.COLUMN_ID, chw.getID());
        values.put(singleAreas.COLUMN_DEVICEID, chw.getDeviceid());
        values.put(singleAreas.COLUMN_UID, chw.getUid());
        values.put(singleAreas.COLUMN_FORMDATE, chw.getFormdate());
        values.put(singleAreas.COLUMN_AREANAME, chw.getAreaname());
        values.put(singleAreas.COLUMN_USERNAME, chw.getUsername());
        values.put(singleAreas.COLUMN_DISTRICT_CODE, chw.getDistrict_code());
        values.put(singleAreas.COLUMN_PSU_CODE, chw.getPsu_code());
        values.put(singleAreas.COLUMN_TAGID, chw.getTagid());
        values.put(singleAreas.COLUMN_APPVERSION, chw.getAppversion());

        AppMain.updatePSU(chw.getPsu_code(), "0");
        AppMain.hh03txt = 0;

        long newRowId;
        newRowId = db.insert(
                singleAreas.TABLE_NAME,
                singleAreas.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
    }

    public Long addMwra(MwraContract mwra) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(MwraEntry.COLUMN_ID, mwra.getID());
        values.put(MwraEntry.COLUMN_USER_NAME, mwra.getUser());
        values.put(MwraEntry.MWRA_UUID, mwra.getUUID());
        values.put(MwraEntry.MWRA_UID, mwra.getUID());
        values.put(MwraEntry.MWRA_MWDT, mwra.getMwDT());
        values.put(MwraEntry.MWRA_DISTRICT_CODE, mwra.getMwDistrictCode());
        values.put(MwraEntry.MWRA_PSU_CODE, mwra.getMwPSUNo());
        values.put(MwraEntry.COLUMN_TAGID, mwra.getTagID());
        values.put(MwraEntry.COLUMN_APPVERSION, mwra.getAppversion());
        values.put(MwraEntry.MWRA_NAME, mwra.getName());
        values.put(MwraEntry.MWRA_AGE_Y, mwra.getAgey());
        values.put(MwraEntry.COLUMN_DEVICEID, mwra.getDeviceid());
        values.put(MwraEntry.MWRA_ID, mwra.getMwraID());
        values.put(MwraEntry.MWRA_STRUCTURE_NO, mwra.getStructureNo());

        long newRowId;
        newRowId = db.insert(
                MwraEntry.TABLE_NAME,
                MwraEntry.MWRA_NULLABLE,
                values);

        return newRowId;
    }

    public void updateListingUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, AppMain.lc.getUID());

// Which row to update, based on the title
        String where = ListingEntry._ID + " = ?";
        String[] whereArgs = {AppMain.lc.getID().toString()};

        int count = db.update(
                ListingEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateAreaUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(singleAreas.COLUMN_UID, AppMain.ac.getUid());

// Which row to update, based on the title
        String where = singleAreas.COLUMN_ID + " = ?";
        String[] whereArgs = {AppMain.ac.getID().toString()};

        int count = db.update(
                singleAreas.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMWRAUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MwraEntry.MWRA_UID, AppMain.mc.getUID());

// Which row to update, based on the title
        String where = MwraEntry.COLUMN_ID + " = ?";
        String[] whereArgs = {AppMain.mc.getID().toString()};

        int count = db.update(
                MwraEntry.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public Collection<ListingContract> getAllListings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ListingEntry._ID,
                ListingEntry.COLUMN_NAME_UID,
                ListingEntry.COLUMN_NAME_HHDATETIME,
                ListingEntry.COLUMN_NAME_HH01,
                ListingEntry.COLUMN_NAME_HH02,
                ListingEntry.COLUMN_NAME_HH03,
                ListingEntry.COLUMN_NAME_HH04,
                ListingEntry.COLUMN_NAME_HH04x,
                ListingEntry.COLUMN_NAME_HH05,
                ListingEntry.COLUMN_NAME_HH06,
                ListingEntry.COLUMN_NAME_HH07,
                ListingEntry.COLUMN_NAME_HH07n,
                ListingEntry.COLUMN_NAME_HH08,
                ListingEntry.COLUMN_NAME_HH09,
                ListingEntry.COLUMN_NAME_HH10,
                ListingEntry.COLUMN_NAME_HH11,
                ListingEntry.COLUMN_NAME_HH11x,
                ListingEntry.COLUMN_NAME_HHADD,
                ListingEntry.COLUMN_NAME_HH12,
                ListingEntry.COLUMN_NAME_HH12y,
                ListingEntry.COLUMN_NAME_WOMEN_NAME,
                ListingEntry.COLUMN_USERNAME,
                ListingEntry.COLUMN_STATUS,
                ListingEntry.COLUMN_NAME_DEVICEID,
                ListingEntry.COLUMN_TAGID,
                ListingEntry.COLUMN_NAME_GPSLat,
                ListingEntry.COLUMN_NAME_GPSLng,
                ListingEntry.COLUMN_NAME_GPSTime,
                ListingEntry.COLUMN_NAME_GPSAccuracy,
                ListingEntry.COLUMN_NAME_ROUND
        };

        String whereClause = ListingEntry.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                ListingEntry._ID + " ASC";

        Collection<ListingContract> allLC = new ArrayList<ListingContract>();
        try {
            c = db.query(
                    ListingEntry.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allLC.add(hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allLC;
    }

    public Collection<AreasContract> getAllAreas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleAreas.COLUMN_ID,
                singleAreas.COLUMN_DEVICEID,
                singleAreas.COLUMN_UID,
                singleAreas.COLUMN_FORMDATE,
                singleAreas.COLUMN_AREANAME,
                singleAreas.COLUMN_USERNAME,
                singleAreas.COLUMN_DISTRICT_CODE,
                singleAreas.COLUMN_PSU_CODE,
                singleAreas.COLUMN_TAGID,
                singleAreas.COLUMN_APPVERSION
        };

        String whereClause = singleAreas.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                singleAreas.COLUMN_ID + " ASC";

        Collection<AreasContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    singleAreas.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                AreasContract dc = new AreasContract();
                allDC.add(dc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public Collection<MwraContract> getAllMwras() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MwraEntry.COLUMN_ID,
                MwraEntry.COLUMN_USER_NAME,
                MwraEntry.MWRA_UUID,
                MwraEntry.MWRA_UID,
                MwraEntry.MWRA_MWDT,
                MwraEntry.MWRA_DISTRICT_CODE,
                MwraEntry.MWRA_PSU_CODE,
                MwraEntry.COLUMN_TAGID,
                MwraEntry.COLUMN_APPVERSION,
                MwraEntry.MWRA_NAME,
                MwraEntry.MWRA_AGE_Y,
                MwraEntry.COLUMN_DEVICEID,
                MwraEntry.MWRA_ID,
                MwraEntry.MWRA_STRUCTURE_NO,
        };

        String whereClause = MwraEntry.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                MwraEntry.COLUMN_ID + " ASC";

        Collection<MwraContract> allMWRA = new ArrayList<MwraContract>();
        try {
            c = db.query(
                    MwraEntry.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MwraContract mwra = new MwraContract();
                allMWRA.add(mwra.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMWRA;
    }

    public Collection<DistrictsContract> getAllDistricts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleDistrict._ID,
                singleDistrict.COLUMN_DISTRICT_CODE,
                singleDistrict.COLUMN_DISTRICT_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                singleDistrict._ID + " ASC";

        Collection<DistrictsContract> allDC = new ArrayList<DistrictsContract>();
        try {
            c = db.query(
                    singleDistrict.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                DistrictsContract dc = new DistrictsContract();
                allDC.add(dc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }

    public Collection<PSUsContract> getAllPSUsByDistrict(String district_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singlePSU._ID,
                singlePSU.COLUMN_PSU_CODE,
                singlePSU.COLUMN_PSU_NAME,
                singlePSU.COLUMN_DISTRICT_CODE,
                singlePSU.COLUMN_TYPE
        };

        String whereClause = singlePSU.COLUMN_DISTRICT_CODE + " = ?";
        String[] whereArgs = {district_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                singlePSU.COLUMN_PSU_CODE + " ASC";

        Collection<PSUsContract> allPC = new ArrayList<PSUsContract>();
        try {
            c = db.query(
                    singlePSU.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                PSUsContract pc = new PSUsContract();
                allPC.add(pc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPC;
    }

    public Collection<VerticesContract> getVerticesByCluster(String cluster_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleVertices._ID,
                singleVertices.COLUMN_CLUSTER_CODE,
                singleVertices.COLUMN_POLY_LAT,
                singleVertices.COLUMN_POLY_LANG,
                singleVertices.COLUMN_POLY_SEQ
        };

        String whereClause = singleVertices.COLUMN_CLUSTER_CODE + " = ?";
        String[] whereArgs = {cluster_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleVertices.COLUMN_POLY_SEQ + " ASC";

        Collection<VerticesContract> allVC = new ArrayList<>();
        try {
            c = db.query(
                    singleVertices.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VerticesContract vc = new VerticesContract();
                allVC.add(vc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public Collection<VerticesUCContract> getVerticesByUC(String uc_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleVerticesUC._ID,
                singleVerticesUC.COLUMN_UC_CODE,
                singleVerticesUC.COLUMN_POLY_LAT,
                singleVerticesUC.COLUMN_POLY_LANG,
                singleVerticesUC.COLUMN_POLY_SEQ
        };

        String whereClause = singleVerticesUC.COLUMN_UC_CODE + " = ?";
        String[] whereArgs = {uc_code};
        String groupBy = null;
        String having = null;

        String orderBy = "substr('0000000000' || " + singleVerticesUC.COLUMN_POLY_SEQ + ", -10, 10) ASC";

        Collection<VerticesUCContract> allVCUC = new ArrayList<>();
        try {
            c = db.query(
                    singleVerticesUC.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VerticesUCContract vc = new VerticesUCContract();
                allVCUC.add(vc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVCUC;
    }

    private ListingContract hydrate(Cursor c) {
        ListingContract lc = new ListingContract(c.getString(c.getColumnIndex(ListingEntry._ID)));
        lc.setUID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_UID))));
        lc.setHhDT(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HHDATETIME))));
        lc.setHh01(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH01))));
        lc.setHh02(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH02))));
        lc.setHh03(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH03))));
        lc.setHh04(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH04))));
        lc.setHh04x(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH04x))));
        lc.setHh05(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH05))));
        lc.setHh06(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH06))));
        lc.setHh07(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH07))));
        lc.setHh07n(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH07n))));
        lc.setHh08(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH08))));
        lc.setHh09(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH09))));
        lc.setHh10(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH10))));
        lc.setHh11(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH11))));
        lc.setHh11x(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH11x))));
        lc.setHhadd(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HHADD))));
        lc.setHh12(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH12))));
        lc.setHh12y(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH12y))));
        lc.setHhWomenNm(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_WOMEN_NAME))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_DEVICEID))));
        lc.setGPSLat(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLat))));
        lc.setGPSLng(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLng))));
        lc.setGPSTime(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSTime))));
        lc.setGPSAcc(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSAccuracy))));
        lc.setRound(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_ROUND))));
        lc.setTagId(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_TAGID))));
        lc.setUsername(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_USERNAME))));
        lc.setStatus(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_STATUS))));

        return lc;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }


    }

    public void syncDistricts(JSONArray dcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleDistrict.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = dcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectDistrict = jsonArray.getJSONObject(i);

                DistrictsContract dc = new DistrictsContract();
                dc.sync(jsonObjectDistrict);

                ContentValues values = new ContentValues();

                values.put(singleDistrict.COLUMN_DISTRICT_CODE, dc.getDistrictCode());
                values.put(singleDistrict.COLUMN_DISTRICT_NAME, dc.getDistrictName());

                db.insert(singleDistrict.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncPSUs(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singlePSU.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPSU = jsonArray.getJSONObject(i);

                PSUsContract pc = new PSUsContract();
                pc.sync(jsonObjectPSU);

                ContentValues values = new ContentValues();

                values.put(singlePSU._ID, pc.getId());
                values.put(singlePSU.COLUMN_PSU_CODE, pc.getPSUCode());
                values.put(singlePSU.COLUMN_PSU_NAME, pc.getPSUName());
                values.put(singlePSU.COLUMN_TYPE, pc.getType());
                values.put(singlePSU.COLUMN_DISTRICT_CODE, pc.getDistrictCode());

                db.insert(singlePSU.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncVertices(JSONArray vcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleVertices.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = vcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectVR = jsonArray.getJSONObject(i);

                VerticesContract vc = new VerticesContract();
                vc.sync(jsonObjectVR);

                ContentValues values = new ContentValues();

                values.put(singleVertices.COLUMN_CLUSTER_CODE, vc.getCluster_code());
                values.put(singleVertices.COLUMN_POLY_LAT, vc.getPoly_lat());
                values.put(singleVertices.COLUMN_POLY_LANG, vc.getPoly_lng());
                values.put(singleVertices.COLUMN_POLY_SEQ, vc.getPoly_seq());

                db.insert(singleVertices.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncVerticesUC(JSONArray vcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleVerticesUC.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = vcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectVRUC = jsonArray.getJSONObject(i);

                VerticesUCContract vc = new VerticesUCContract();
                vc.sync(jsonObjectVRUC);

                ContentValues values = new ContentValues();

                values.put(singleVerticesUC.COLUMN_UC_CODE, vc.getUc_code());
                values.put(singleVerticesUC.COLUMN_POLY_LAT, vc.getPoly_lat());
                values.put(singleVerticesUC.COLUMN_POLY_LANG, vc.getPoly_lng());
                values.put(singleVerticesUC.COLUMN_POLY_SEQ, vc.getPoly_seq());

                db.insert(singleVerticesUC.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

}