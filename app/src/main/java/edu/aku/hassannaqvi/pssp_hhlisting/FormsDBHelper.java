package edu.aku.hassannaqvi.pssp_hhlisting;

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

import edu.aku.hassannaqvi.pssp_hhlisting.DistrictsContract.singleDistrict;
import edu.aku.hassannaqvi.pssp_hhlisting.ListingContract.ListingEntry;
import edu.aku.hassannaqvi.pssp_hhlisting.PSUsContract.singlePSU;

import static edu.aku.hassannaqvi.pssp_hhlisting.AppMain.sharedPref;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 4;
    // The name of database.
    private static final String DATABASE_NAME = "pssp-hhl.db";
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;


    public FormsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
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
                ListingEntry.COLUMN_NAME_HH12m + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH12d + " TEXT, " +
                ListingEntry.COLUMN_NAME_CHILD_NAME + " TEXT, " +
                ListingEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLat + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLng + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSTime + " TEXT, " +
                ListingEntry.COLUMN_NAME_ROUND + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSAccuracy + " TEXT " +
                " );";

        final String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + singleDistrict.TABLE_NAME + " (" +
                singleDistrict._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                singleDistrict.COLUMN_DISTRICT_CODE + " TEXT, " +
                singleDistrict.COLUMN_DISTRICT_NAME + " TEXT " +
                ");";

        final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + singlePSU.TABLE_NAME + " (" +
                singlePSU._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                singlePSU.COLUMN_PSU_CODE + " TEXT, " +
                singlePSU.COLUMN_PSU_NAME + " TEXT, " +
                singlePSU.COLUMN_DISTRICT_CODE + " TEXT " +
                ");";


        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_LISTING_TABLE);
        db.execSQL(SQL_CREATE_DISTRICT_TABLE);
        db.execSQL(SQL_CREATE_PSU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleDistrict.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singlePSU.TABLE_NAME);
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

    public Long addForm(ListingContract lc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, lc.getUID());
        values.put(ListingEntry.COLUMN_NAME_HHDATETIME, lc.getHhDT());
        values.put(ListingEntry.COLUMN_NAME_HH01, lc.getHh01());
        values.put(ListingEntry.COLUMN_NAME_HH02, lc.getHh02());
        values.put(ListingEntry.COLUMN_NAME_HHADD, lc.getHhadd());
        values.put(ListingEntry.COLUMN_NAME_HH03, lc.getHh03());

        AppMain.updatePSU(lc.getHh02(), lc.getHh03());
        Log.d(TAG, "PSUExist (Test): " + sharedPref.getString(lc.getHh02(), "0"));

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
        values.put(ListingEntry.COLUMN_NAME_HH12m, lc.getHh12m());
        values.put(ListingEntry.COLUMN_NAME_HH12d, lc.getHh12d());
        values.put(ListingEntry.COLUMN_NAME_CHILD_NAME, lc.getHhChildNm());
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingEntry.COLUMN_NAME_ROUND, lc.getRound());

        long newRowId;
        newRowId = db.insert(
                ListingEntry.TABLE_NAME,
                ListingEntry.COLUMN_NAME_NULLABLE,
                values);
        DB_FORM_ID = String.valueOf(newRowId);

        return newRowId;
    }

    public Long addDistrict(DistrictsContract dc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(singleDistrict.COLUMN_DISTRICT_CODE, dc.getDistrictCode());
        values.put(singleDistrict.COLUMN_DISTRICT_NAME, dc.getDistrictName());

        long newRowId;
        newRowId = db.insert(
                singleDistrict.TABLE_NAME,
                singleDistrict.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
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
                ListingEntry.COLUMN_NAME_HHADD,
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
                ListingEntry.COLUMN_NAME_HH12m,
                ListingEntry.COLUMN_NAME_HH12d,
                ListingEntry.COLUMN_NAME_CHILD_NAME,
                ListingEntry.COLUMN_NAME_DEVICEID,
                ListingEntry.COLUMN_NAME_GPSLat,
                ListingEntry.COLUMN_NAME_GPSLng,
                ListingEntry.COLUMN_NAME_GPSTime,
                ListingEntry.COLUMN_NAME_GPSAccuracy,
                ListingEntry.COLUMN_NAME_ROUND
        };

        String whereClause = null;
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
                singlePSU.COLUMN_DISTRICT_CODE
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

    private ContentValues getContentValues(ListingContract lc) {
        ContentValues values = new ContentValues();
        values.put(ListingEntry._ID, lc.getID());
        values.put(ListingEntry.COLUMN_NAME_UID, lc.getUID());
        values.put(ListingEntry.COLUMN_NAME_HHDATETIME, lc.getHhDT());
        values.put(ListingEntry.COLUMN_NAME_HH01, lc.getHh01());
        values.put(ListingEntry.COLUMN_NAME_HH02, lc.getHh02());
        values.put(ListingEntry.COLUMN_NAME_HHADD, lc.getHhadd());
        values.put(ListingEntry.COLUMN_NAME_HH03, lc.getHh03());
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
        values.put(ListingEntry.COLUMN_NAME_HH12m, lc.getHh12m());
        values.put(ListingEntry.COLUMN_NAME_HH12d, lc.getHh12d());
        values.put(ListingEntry.COLUMN_NAME_CHILD_NAME, lc.getHhChildNm());
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingEntry.COLUMN_NAME_ROUND, lc.getRound());

        return values;
    }

    private ListingContract hydrate(Cursor c) {
        ListingContract lc = new ListingContract(c.getString(c.getColumnIndex(ListingEntry._ID)));
        lc.setUID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_UID))));
        lc.setHhDT(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HHDATETIME))));
        lc.setHh01(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH01))));
        lc.setHh02(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH02))));
        lc.setHhadd(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HHADD))));
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
        lc.setHh12m(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH12m))));
        lc.setHh12d(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH12d))));
        lc.setHhChildNm(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_CHILD_NAME))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_DEVICEID))));
        lc.setGPSLat(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLat))));
        lc.setGPSLng(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLng))));
        lc.setGPSTime(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSTime))));
        lc.setGPSAcc(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSAccuracy))));
        lc.setRound(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_ROUND))));

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

    public void syncDistrict(JSONArray dcList) {
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
                values.put(singlePSU.COLUMN_DISTRICT_CODE, pc.getDistrictCode());

                db.insert(singlePSU.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

}
