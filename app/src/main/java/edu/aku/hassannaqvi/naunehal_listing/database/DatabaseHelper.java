package edu.aku.hassannaqvi.naunehal_listing.database;

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
import java.util.List;

import edu.aku.hassannaqvi.naunehal_listing.contracts.ClustersContract;
import edu.aku.hassannaqvi.naunehal_listing.contracts.ClustersContract.TableClusters;
import edu.aku.hassannaqvi.naunehal_listing.contracts.DistrictsContract;
import edu.aku.hassannaqvi.naunehal_listing.contracts.DistrictsContract.TableDistricts;
import edu.aku.hassannaqvi.naunehal_listing.contracts.ListingContract.TableListings;
import edu.aku.hassannaqvi.naunehal_listing.contracts.UCsContract;
import edu.aku.hassannaqvi.naunehal_listing.contracts.UCsContract.TableUCs;
import edu.aku.hassannaqvi.naunehal_listing.core.MainApp;
import edu.aku.hassannaqvi.naunehal_listing.models.Listings;

import static edu.aku.hassannaqvi.naunehal_listing.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.naunehal_listing.core.MainApp.sharedPref;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // The name of database.
    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    public static String TAG = "DatabaseHelper";
    public static String DB_FORM_ID;
    public static String DB_NAME = PROJECT_NAME + "_copy.db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold Listings.
        final String SQL_CREATE_LISTING_TABLE = "CREATE TABLE " + TableListings.TABLE_NAME + " (" +
                TableListings._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableListings.COLUMN_NAME_UID + " TEXT, " +
                TableListings.COLUMN_NAME_HHDATETIME + " TEXT, " +
                TableListings.COLUMN_NAME_HL01 + " TEXT, " +
                TableListings.COLUMN_NAME_HL02 + " TEXT, " +
                TableListings.COLUMN_NAME_HL03 + " TEXT, " +
                TableListings.COLUMN_NAME_HL04 + " TEXT, " +
                TableListings.COLUMN_NAME_HL05 + " TEXT, " +
                TableListings.COLUMN_NAME_HL06 + " TEXT, " +
                TableListings.COLUMN_NAME_HL07 + " TEXT, " +
                TableListings.COLUMN_NAME_HL0796X + " TEXT, " +
                TableListings.COLUMN_NAME_HL08 + " TEXT, " +
                TableListings.COLUMN_NAME_HL09 + " TEXT, " +
                TableListings.COLUMN_NAME_HL10 + " TEXT, " +
                TableListings.COLUMN_NAME_HL11 + " TEXT, " +
                TableListings.COLUMN_NAME_HL12M + " TEXT, " +
                TableListings.COLUMN_NAME_HL12D + " TEXT, " +
                TableListings.COLUMN_NAME_CHILD_NAME + " TEXT, " +
                TableListings.COLUMN_NAME_DEVICEID + " TEXT, " +
                TableListings.COLUMN_NAME_GPSLat + " TEXT, " +
                TableListings.COLUMN_NAME_GPSLng + " TEXT, " +
                TableListings.COLUMN_NAME_GPSTime + " TEXT, " +
                TableListings.COLUMN_NAME_ROUND + " TEXT, " +
                TableListings.COLUMN_NAME_GPSAccuracy + " TEXT " +
                " );";

        final String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + TableDistricts.TABLE_NAME + " (" +
                TableDistricts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableDistricts.COLUMN_DISTRICT_CODE + " TEXT, " +
                TableDistricts.COLUMN_DISTRICT_NAME + " TEXT " +
                ");";

        final String SQL_CREATE_CLUSTER_TABLE = "CREATE TABLE " + TableClusters.TABLE_NAME + " (" +
                TableClusters._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableClusters.COLUMN_CLUSTER_CODE + " TEXT, " +
                TableClusters.COLUMN_CLUSTER_NAME + " TEXT, " +
                TableClusters.COLUMN_UC_CODE + " TEXT " +
                ");";

        final String SQL_CREATE_UC_TABLE = "CREATE TABLE " + TableUCs.TABLE_NAME + " (" +
                TableUCs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TableUCs.COLUMN_DISTRICT_CODE + " TEXT, " +
                TableUCs.COLUMN_UC_NAME + " TEXT, " +
                TableUCs.COLUMN_UC_CODE + " TEXT " +
                ");";


        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_LISTING_TABLE);
        db.execSQL(SQL_CREATE_DISTRICT_TABLE);
        db.execSQL(SQL_CREATE_CLUSTER_TABLE);
        db.execSQL(SQL_CREATE_UC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
       /* db.execSQL("DROP TABLE IF EXISTS " + TableListings.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableDistricts.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableClusters.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableUCs.TABLE_NAME);*/
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

    public Long addForm(Listings lc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TableListings.COLUMN_NAME_UID, lc.getUID());
        values.put(TableListings.COLUMN_NAME_HHDATETIME, lc.getHhDT());
        values.put(TableListings.COLUMN_NAME_HL01, lc.getHl01());
        values.put(TableListings.COLUMN_NAME_HL02, lc.getHl02());
        values.put(TableListings.COLUMN_NAME_HL03, lc.getHl03());

        MainApp.updateCluster(lc.getHl03(), lc.getHl04());
        Log.d(TAG, "ClusterExist (Test): " + sharedPref.getString(lc.getHl03(), "0"));

        values.put(TableListings.COLUMN_NAME_HL04, lc.getHl04());
        values.put(TableListings.COLUMN_NAME_HL05, lc.getHl05());
        values.put(TableListings.COLUMN_NAME_HL06, lc.getHl06());
        values.put(TableListings.COLUMN_NAME_HL07, lc.getHl07());
        values.put(TableListings.COLUMN_NAME_HL0796X, lc.getHl0796x());
        values.put(TableListings.COLUMN_NAME_HL08, lc.getHl08());
        values.put(TableListings.COLUMN_NAME_HL09, lc.getHl09());
        values.put(TableListings.COLUMN_NAME_HL10, lc.getHl10());
        values.put(TableListings.COLUMN_NAME_HL11, lc.getHl11());
        values.put(TableListings.COLUMN_NAME_HL12M, lc.getHl12m());
        values.put(TableListings.COLUMN_NAME_HL12D, lc.getHl12d());
        values.put(TableListings.COLUMN_NAME_CHILD_NAME, lc.getHhChildNm());
        values.put(TableListings.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(TableListings.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(TableListings.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(TableListings.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(TableListings.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(TableListings.COLUMN_NAME_ROUND, lc.getRound());

        long newRowId;
        newRowId = db.insert(
                TableListings.TABLE_NAME,
                TableListings.COLUMN_NAME_NULLABLE,
                values);
        DB_FORM_ID = String.valueOf(newRowId);

        return newRowId;
    }

    public Long addDistrict(DistrictsContract dc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TableDistricts.COLUMN_DISTRICT_CODE, dc.getDistrictCode());
        values.put(TableDistricts.COLUMN_DISTRICT_NAME, dc.getDistrictName());

        long newRowId;
        newRowId = db.insert(
                TableDistricts.TABLE_NAME,
                TableDistricts.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
    }

    public Collection<Listings> getAllListings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TableListings._ID,
                TableListings.COLUMN_NAME_UID,
                TableListings.COLUMN_NAME_HHDATETIME,
                TableListings.COLUMN_NAME_HL01,
                TableListings.COLUMN_NAME_HL02,
                TableListings.COLUMN_NAME_HL03,
                TableListings.COLUMN_NAME_HL04,
                TableListings.COLUMN_NAME_HL05,
                TableListings.COLUMN_NAME_HL06,
                TableListings.COLUMN_NAME_HL07,
                TableListings.COLUMN_NAME_HL0796X,
                TableListings.COLUMN_NAME_HL08,
                TableListings.COLUMN_NAME_HL09,
                TableListings.COLUMN_NAME_HL10,
                TableListings.COLUMN_NAME_HL11,
                TableListings.COLUMN_NAME_HL12M,
                TableListings.COLUMN_NAME_HL12D,
                TableListings.COLUMN_NAME_CHILD_NAME,
                TableListings.COLUMN_NAME_DEVICEID,
                TableListings.COLUMN_NAME_GPSLat,
                TableListings.COLUMN_NAME_GPSLng,
                TableListings.COLUMN_NAME_GPSTime,
                TableListings.COLUMN_NAME_GPSAccuracy,
                TableListings.COLUMN_NAME_ROUND
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TableListings._ID + " ASC";

        Collection<Listings> allLC = new ArrayList<Listings>();
        try {
            c = db.query(
                    TableListings.TABLE_NAME,  // The table to query
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
                TableDistricts._ID,
                TableDistricts.COLUMN_DISTRICT_CODE,
                TableDistricts.COLUMN_DISTRICT_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TableDistricts._ID + " ASC";

        Collection<DistrictsContract> allDC = new ArrayList<DistrictsContract>();
        try {
            c = db.query(
                    TableDistricts.TABLE_NAME,  // The table to query
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

    public Collection<ClustersContract> getAllClustersByUC(String ucCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TableClusters._ID,
                TableClusters.COLUMN_CLUSTER_CODE,
                TableClusters.COLUMN_CLUSTER_NAME,
                TableClusters.COLUMN_UC_CODE
        };

        String whereClause = TableClusters.COLUMN_UC_CODE + " = ?";
        String[] whereArgs = {ucCode};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableClusters.COLUMN_CLUSTER_CODE + " ASC";

        Collection<ClustersContract> allPC = new ArrayList<ClustersContract>();
        try {
            c = db.query(
                    TableClusters.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ClustersContract pc = new ClustersContract();
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

    public List<UCsContract> getAllUCByDistrict(String district_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TableUCs._ID,
                TableUCs.COLUMN_UC_CODE,
                TableUCs.COLUMN_UC_NAME,
                TableUCs.COLUMN_DISTRICT_CODE
        };

        String whereClause = TableUCs.COLUMN_DISTRICT_CODE + " = ?";
        String[] whereArgs = {district_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                TableUCs.COLUMN_UC_NAME + " ASC";

        List<UCsContract> allPC = new ArrayList<UCsContract>();
        try {
            c = db.query(
                    TableUCs.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UCsContract uc = new UCsContract();
                allPC.add(uc.hydrate(c));
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

    private ContentValues getContentValues(Listings lc) {
        ContentValues values = new ContentValues();
        values.put(TableListings._ID, lc.getID());
        values.put(TableListings.COLUMN_NAME_UID, lc.getUID());
        values.put(TableListings.COLUMN_NAME_HHDATETIME, lc.getHhDT());
        values.put(TableListings.COLUMN_NAME_HL01, lc.getHl01());
        values.put(TableListings.COLUMN_NAME_HL02, lc.getHl02());
        values.put(TableListings.COLUMN_NAME_HL03, lc.getHl03());
        values.put(TableListings.COLUMN_NAME_HL04, lc.getHl04());
        values.put(TableListings.COLUMN_NAME_HL05, lc.getHl05());
        values.put(TableListings.COLUMN_NAME_HL06, lc.getHl06());
        values.put(TableListings.COLUMN_NAME_HL07, lc.getHl07());
        values.put(TableListings.COLUMN_NAME_HL0796X, lc.getHl0796x());
        values.put(TableListings.COLUMN_NAME_HL08, lc.getHl08());
        values.put(TableListings.COLUMN_NAME_HL09, lc.getHl09());
        values.put(TableListings.COLUMN_NAME_HL10, lc.getHl10());
        values.put(TableListings.COLUMN_NAME_HL11, lc.getHl11());
        values.put(TableListings.COLUMN_NAME_HL12M, lc.getHl12m());
        values.put(TableListings.COLUMN_NAME_HL12D, lc.getHl12d());
        values.put(TableListings.COLUMN_NAME_CHILD_NAME, lc.getHhChildNm());
        values.put(TableListings.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(TableListings.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(TableListings.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(TableListings.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(TableListings.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(TableListings.COLUMN_NAME_ROUND, lc.getRound());

        return values;
    }

    private Listings hydrate(Cursor c) {
        Listings lc = new Listings(c.getString(c.getColumnIndex(TableListings._ID)));
        lc.setUID(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_UID))));
        lc.setHhDT(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HHDATETIME))));
        lc.setHl01(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL01))));
        lc.setHl02(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL02))));
        lc.setHl03(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL03))));
        lc.setHl04(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL04))));
        lc.setHl05(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL05))));
        lc.setHl06(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL06))));
        lc.setHl07(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL07))));
        lc.setHl0796x(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL0796X))));
        lc.setHl08(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL08))));
        lc.setHl09(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL09))));
        lc.setHl10(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL10))));
        lc.setHl11(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL11))));
        lc.setHl12m(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL12M))));
        lc.setHl12d(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_HL12D))));
        lc.setHhChildNm(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_CHILD_NAME))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_DEVICEID))));
        lc.setGPSLat(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_GPSLat))));
        lc.setGPSLng(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_GPSLng))));
        lc.setGPSTime(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_GPSTime))));
        lc.setGPSAcc(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_GPSAccuracy))));
        lc.setRound(String.valueOf(c.getString(c.getColumnIndex(TableListings.COLUMN_NAME_ROUND))));

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

    public int syncDistricts(JSONArray Districtslist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DistrictsContract.TableDistricts.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < Districtslist.length(); i++) {
                JSONObject jsonObjectDistrict = Districtslist.getJSONObject(i);
                DistrictsContract District = new DistrictsContract();
                District.sync(jsonObjectDistrict);
                ContentValues values = new ContentValues();

                values.put(TableDistricts.COLUMN_DISTRICT_CODE, District.getDistrictCode());
                values.put(TableDistricts.COLUMN_DISTRICT_NAME, District.getDistrictName());
                long rowID = db.insert(TableDistricts.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncDistrict(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncCluster(JSONArray clusterList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableClusters.TABLE_NAME, null, null);
        int insertCount = 0;
        try {

            for (int i = 0; i < clusterList.length(); i++) {
                JSONObject jsonObjectCluster = clusterList.getJSONObject(i);
                ClustersContract cluster = new ClustersContract();
                cluster.sync(jsonObjectCluster);
                ContentValues values = new ContentValues();

                values.put(TableClusters.COLUMN_CLUSTER_CODE, cluster.getClusterCode());
                values.put(TableClusters.COLUMN_CLUSTER_NAME, cluster.getClustername());
                values.put(TableClusters.COLUMN_UC_CODE, cluster.getUcCode());

                long rowID = db.insert(TableClusters.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }
            db.close();

        } catch (Exception e) {
            Log.d(TAG, "syncCluster(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public int syncUCs(JSONArray ucList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableUCs.TABLE_NAME, null, null);
        int insertCount = 0;
        try {

            for (int i = 0; i < ucList.length(); i++) {
                JSONObject jsonObjectUc = ucList.getJSONObject(i);
                UCsContract uc = new UCsContract();
                uc.sync(jsonObjectUc);
                ContentValues values = new ContentValues();

                values.put(TableUCs.COLUMN_UC_CODE, uc.getUcCode());
                values.put(TableUCs.COLUMN_UC_NAME, uc.getUcName());
                values.put(TableUCs.COLUMN_DISTRICT_CODE, uc.getDistrictCode());

                long rowID = db.insert(TableUCs.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }
            db.close();

        } catch (Exception e) {
            Log.d(TAG, "syncUc(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public boolean checkUsers() {

        return true;
    }


}
