package edu.aku.hassannaqvi.nnspak_hhlisting.Core;

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

import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.ListingContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.ListingContract.ListingEntry;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.PSUsContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.PSUsContract.singlePSU;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.TalukasContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.TalukasContract.singleTaluka;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.UsersContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.UsersContract.singleUser;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.EnumBlockContract;
import edu.aku.hassannaqvi.nnspak_hhlisting.Contracts.EnumBlockContract.EnumBlockTable;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // The name of database.
    public static final String DATABASE_NAME = "nnspak-hhl.db";
    public static final String DB_NAME = DATABASE_NAME.replace(".db", "-copy.db");
    public static final String FOLDER_NAME = "DMU-NNSPAKHHL";
    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;

    // Create a table to hold Listings.
    final String SQL_CREATE_LISTING_TABLE = "CREATE TABLE " + ListingEntry.TABLE_NAME + " (" +
            ListingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ListingEntry.COLUMN_NAME_UID + " TEXT, " +
            ListingEntry.COLUMN_NAME_HHDATETIME + " TEXT, " +
            ListingEntry.COLUMN_NAME_ENUMCODE + " TEXT, " +
            ListingEntry.COLUMN_NAME_ENUMSTR + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH01 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH02 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH03 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH04 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH05 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH06 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH07 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH07n + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH08 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH09 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH10 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH11 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH12 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH13 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH14 + " TEXT, " +
            ListingEntry.COLUMN_NAME_HH15 + " TEXT, " +
            ListingEntry.COLUMN_ADDRESS + " TEXT, " +
            ListingEntry.COLUMN_USERNAME + " TEXT, " +
            ListingEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
            ListingEntry.COLUMN_TAGID + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSLat + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSLng + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSTime + " TEXT, " +
            ListingEntry.COLUMN_APPVER + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSAccuracy + " TEXT, " +
            ListingEntry.COLUMN_NAME_GPSAltitude + " TEXT, " +
            ListingEntry.COLUMN_SYNCED + " TEXT, " +
            ListingEntry.COLUMN_SYNCED_DATE + " TEXT " +
            " );";

    final String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + singleTaluka.TABLE_NAME + " (" +
            singleTaluka._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleTaluka.COLUMN_TALUKA_CODE + " TEXT, " +
            singleTaluka.COLUMN_TALUKA_NAME + " TEXT " +
            ");";

    /*final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + singlePSU.TABLE_NAME + " (" +
            singlePSU._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singlePSU.COLUMN_PSU_CODE + " TEXT, " +
            singlePSU.COLUMN_PSU_NAME + " TEXT, " +
            singlePSU.COLUMN_TALUKA_CODE + " TEXT " +
            ");";*/

    final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + EnumBlockTable.TABLE_NAME + " (" +
            EnumBlockTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EnumBlockTable.COLUMN_EB_CODE + " TEXT, " +
            EnumBlockTable.COLUMN_GEO_AREA + " TEXT " +
            ");";

    final String SQL_CREATE_USERS = "CREATE TABLE " + singleUser.TABLE_NAME + "("
            + singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleUser.ROW_USERNAME + " TEXT,"
            + singleUser.ROW_PASSWORD + " TEXT );";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleTaluka.TABLE_NAME);
        /*db.execSQL("DROP TABLE IF EXISTS " + singlePSU.TABLE_NAME);*/
        db.execSQL("DROP TABLE IF EXISTS " + EnumBlockTable.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + singleUser.TABLE_NAME);
        onCreate(db);
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


    public void syncEnumBlocks(JSONArray Enumlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnumBlockTable.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Enumlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                EnumBlockContract Vc = new EnumBlockContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(EnumBlockTable.COLUMN_EB_CODE, Vc.getEbcode());
                values.put(EnumBlockTable.COLUMN_GEO_AREA, Vc.getTalukaName());

                db.insert(EnumBlockTable.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }

    public boolean Login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleUser.ROW_USERNAME,
                singleUser.ROW_PASSWORD,
        };

        String whereClause = singleUser.ROW_USERNAME + "=? AND " + singleUser.ROW_PASSWORD + "=?";
        String[] whereArgs = new String[]{username, password};
        String groupBy = null;
        String having = null;

        String orderBy = null;

        Collection<TalukasContract> allDC = new ArrayList<TalukasContract>();
        try {
            c = db.query(
                    singleUser.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            if (!(c.moveToFirst()) || c.getCount() > 0) {
                return true;
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    public Long addForm(ListingContract lc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, lc.getUID());
        values.put(ListingEntry.COLUMN_NAME_HHDATETIME, lc.getHhDT());

        values.put(ListingEntry.COLUMN_NAME_ENUMCODE, lc.getEnumCode());
        values.put(ListingEntry.COLUMN_NAME_ENUMSTR, lc.getEnumStr());

        values.put(ListingEntry.COLUMN_NAME_HH01, lc.getHh01());
        values.put(ListingEntry.COLUMN_NAME_HH02, lc.getHh02());
        values.put(ListingEntry.COLUMN_NAME_HH03, lc.getHh03());

        AppMain.updatePSU(lc.getHh02(), lc.getHh03());
        Log.d(TAG, "PSUExist (Test): " + AppMain.sharedPref.getString(lc.getHh02(), "0"));

        values.put(ListingEntry.COLUMN_NAME_HH04, lc.getHh04());
        values.put(ListingEntry.COLUMN_NAME_HH05, lc.getHh05());
        values.put(ListingEntry.COLUMN_NAME_HH06, lc.getHh06());
        values.put(ListingEntry.COLUMN_NAME_HH07, lc.getHh07());
        values.put(ListingEntry.COLUMN_NAME_HH07n, lc.getHh07n());
        values.put(ListingEntry.COLUMN_NAME_HH08, lc.getHh08());
        values.put(ListingEntry.COLUMN_NAME_HH09, lc.getHh09());
        values.put(ListingEntry.COLUMN_NAME_HH10, lc.getHh10());
        values.put(ListingEntry.COLUMN_NAME_HH11, lc.getHh11());
        values.put(ListingEntry.COLUMN_NAME_HH12, lc.getHh12());
        values.put(ListingEntry.COLUMN_NAME_HH13, lc.getHh13());
        values.put(ListingEntry.COLUMN_NAME_HH14, lc.getHh14());
        values.put(ListingEntry.COLUMN_NAME_HH15, lc.getHh15());
        values.put(ListingEntry.COLUMN_ADDRESS, lc.getHhadd());
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_USERNAME, lc.getUsername());
        values.put(ListingEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingEntry.COLUMN_NAME_GPSAltitude, lc.getGPSAlt());
        values.put(ListingEntry.COLUMN_APPVER, lc.getAppVer());
        values.put(ListingEntry.COLUMN_TAGID, lc.getTagId());

        long newRowId;
        newRowId = db.insert(
                ListingEntry.TABLE_NAME,
                ListingEntry.COLUMN_NAME_NULLABLE,
                values);
        DB_FORM_ID = String.valueOf(newRowId);

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

    public Collection<ListingContract> getAllListings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ListingEntry._ID,
                ListingEntry.COLUMN_NAME_UID,
                ListingEntry.COLUMN_NAME_HHDATETIME,
                ListingEntry.COLUMN_NAME_ENUMCODE,
                ListingEntry.COLUMN_NAME_ENUMSTR,
                ListingEntry.COLUMN_NAME_HH01,
                ListingEntry.COLUMN_NAME_HH02,
                ListingEntry.COLUMN_NAME_HH03,
                ListingEntry.COLUMN_NAME_HH04,
                ListingEntry.COLUMN_NAME_HH05,
                ListingEntry.COLUMN_NAME_HH06,
                ListingEntry.COLUMN_NAME_HH07,
                ListingEntry.COLUMN_NAME_HH07n,
                ListingEntry.COLUMN_NAME_HH08,
                ListingEntry.COLUMN_NAME_HH09,
                ListingEntry.COLUMN_NAME_HH10,
                ListingEntry.COLUMN_NAME_HH11,
                ListingEntry.COLUMN_NAME_HH12,
                ListingEntry.COLUMN_NAME_HH13,
                ListingEntry.COLUMN_NAME_HH14,
                ListingEntry.COLUMN_NAME_HH15,
                ListingEntry.COLUMN_ADDRESS,
                ListingEntry.COLUMN_USERNAME,
                ListingEntry.COLUMN_NAME_DEVICEID,
                ListingEntry.COLUMN_TAGID,
                ListingEntry.COLUMN_NAME_GPSLat,
                ListingEntry.COLUMN_NAME_GPSLng,
                ListingEntry.COLUMN_NAME_GPSTime,
                ListingEntry.COLUMN_NAME_GPSAccuracy,
                ListingEntry.COLUMN_NAME_GPSAltitude,
                ListingEntry.COLUMN_APPVER
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
                ListingContract listing = new ListingContract();
                allLC.add(listing.hydrate(c));
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

    public Collection<TalukasContract> getAllTalukas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleTaluka._ID,
                singleTaluka.COLUMN_TALUKA_CODE,
                singleTaluka.COLUMN_TALUKA_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                singleTaluka.COLUMN_TALUKA_NAME + " ASC";

        Collection<TalukasContract> allDC = new ArrayList<TalukasContract>();
        try {
            c = db.query(
                    singleTaluka.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                TalukasContract dc = new TalukasContract();
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

    public Collection<PSUsContract> getAllPSUsByTaluka(String taluka_code, String psu_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singlePSU._ID,
                singlePSU.COLUMN_PSU_CODE,
                singlePSU.COLUMN_PSU_NAME,
                singlePSU.COLUMN_TALUKA_CODE
        };

        String whereClause = singlePSU.COLUMN_TALUKA_CODE + " =? AND " + singlePSU.COLUMN_PSU_CODE + " =?";
        String[] whereArgs = {taluka_code, psu_code};
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

    public void syncUsers(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                String userName = jsonObjectUser.getString("username");
                String password = jsonObjectUser.getString("password");


                ContentValues values = new ContentValues();

                values.put(singleUser.ROW_USERNAME, userName);
                values.put(singleUser.ROW_PASSWORD, password);
                db.insert(singleUser.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {
        }
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

    public void syncTalukas(JSONArray dcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleTaluka.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = dcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectDistrict = jsonArray.getJSONObject(i);

                TalukasContract dc = new TalukasContract();
                dc.sync(jsonObjectDistrict);

                ContentValues values = new ContentValues();

                values.put(singleTaluka.COLUMN_TALUKA_CODE, dc.getTalukaCode());
                values.put(singleTaluka.COLUMN_TALUKA_NAME, dc.getTalukaName());

                db.insert(singleTaluka.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncPSU(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singlePSU.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPSU = jsonArray.getJSONObject(i);

                PSUsContract pc = new PSUsContract();
                pc.sync(jsonObjectPSU);

                ContentValues values = new ContentValues();

                values.put(singlePSU.COLUMN_PSU_CODE, pc.getPSUCode());
                values.put(singlePSU.COLUMN_PSU_NAME, pc.getPSUName());
                values.put(singlePSU.COLUMN_TALUKA_CODE, pc.getTalukaCode());

                db.insert(singlePSU.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public String getEnumBlock(String enumBlock) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_EB_CODE,
                EnumBlockTable.COLUMN_GEO_AREA
        };

        String whereClause = EnumBlockTable.COLUMN_EB_CODE + " =?";
        String[] whereArgs = new String[]{enumBlock};
        String groupBy = null;
        String having = null;

        String orderBy =
                EnumBlockTable._ID + " ASC";

//        Collection<EnumBlockContract> allEB = new ArrayList<>();
        String allEB = "";
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
//                EnumBlockContract eb = new EnumBlockContract();
//                allEB.add(eb.HydrateTalukas(c));

                AppMain.enumCode = c.getInt(c.getColumnIndex(EnumBlockTable.COLUMN_EB_CODE));
                AppMain.enumStr = c.getString(c.getColumnIndex(EnumBlockTable.COLUMN_GEO_AREA));

                allEB = c.getString(c.getColumnIndex(EnumBlockTable.COLUMN_GEO_AREA));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }

}