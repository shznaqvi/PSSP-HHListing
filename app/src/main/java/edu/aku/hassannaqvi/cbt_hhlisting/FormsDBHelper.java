package edu.aku.hassannaqvi.cbt_hhlisting;

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

import edu.aku.hassannaqvi.cbt_hhlisting.HFacilitiesContract.HFacilityTable;
import edu.aku.hassannaqvi.cbt_hhlisting.LHWsContract.LHWTable;
import edu.aku.hassannaqvi.cbt_hhlisting.ListingContract.ListingEntry;
import edu.aku.hassannaqvi.cbt_hhlisting.PWContract.PwTable;
import edu.aku.hassannaqvi.cbt_hhlisting.TehsilsContract.TehsilTable;
import edu.aku.hassannaqvi.cbt_hhlisting.UCsContract.UcTable;
import edu.aku.hassannaqvi.cbt_hhlisting.UsersContract.singleUser;
import edu.aku.hassannaqvi.cbt_hhlisting.VillagesContract.VillageTable;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    // The name of database.
    private static final String DATABASE_NAME = "cbt-hhl.db";
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;
    public static String DB_PW_ID;


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
                ListingEntry.COLUMN_NAME_HHADD + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH03 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH04 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH04x + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH05 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH06 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH07 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH07n + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH08 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH09 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH09A + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH09B + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH10 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH11 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH12 + " TEXT, " +
                ListingEntry.COLUMN_NAME_HH13 + " TEXT, " +
                ListingEntry.COLUMN_NAME_CHILD_NAME + " TEXT, " +
                ListingEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLat + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLng + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSTime + " TEXT, " +
                ListingEntry.COLUMN_NAME_ROUND + " TEXT, " +
                ListingEntry.COLUMN_NAME_FORMSTATUS + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSAccuracy + " TEXT " +
                " );";

        final String SQL_CREATE_PW_TABLE = "CREATE TABLE " + PwTable.TABLE_NAME + " (" +
                PwTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PwTable.PW_UUID + " TEXT," +
                PwTable.PW_UID + " TEXT," +
                PwTable.PW_PWDT + " TEXT," +
                PwTable.PW_PWVILLAGECODE + " TEXT," +
                PwTable.PW_PWSTRUCTURENO + " TEXT," +
                PwTable.PW_PW01 + " TEXT," +
                PwTable.PW_PW02 + " TEXT," +
                PwTable.PW_PW03 + " TEXT," +
                PwTable.PW_DEVICE_ID + " TEXT," +
                PwTable.PW_LHW_CODE + " TEXT," +
                PwTable.PW_HOUSEHOLD + " TEXT," +
                PwTable.PW_SYNCED + " TEXT," +
                PwTable.PW_SYNCED_DATE + " TEXT" +
                " );";

        final String SQL_CREATE_TEHSIL_TABLE = "CREATE TABLE " + TehsilTable.TABLE_NAME + " (" +
                TehsilTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TehsilTable.COLUMN_TEHSIL_CODE + " TEXT, " +
                TehsilTable.COLUMN_TEHSIL_NAME + " TEXT " +
                ");";
        final String SQL_CREATE_H_FACILIY_TABLE = "CREATE TABLE " + HFacilityTable.TABLE_NAME + " (" +
                HFacilityTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                HFacilityTable.COLUMN_HFACILITY_CODE + " TEXT, " +
                HFacilityTable.COLUMN_TEHSIL_CODE + " TEXT, " +
                HFacilityTable.COLUMN_HFACILITY_NAME + " TEXT " +
                ");";

        final String SQL_CREATE_UC_TABLE = "CREATE TABLE " + UcTable.TABLE_NAME + " (" +
                UcTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UcTable.COLUMN_TEHSIL_CODE + " TEXT, " +
                UcTable.COLUMN_UC_NAME + " TEXT, " +
                UcTable.COLUMN_UC_CODE + " TEXT " +
                ");";

        final String SQL_CREATE_VILLAGE_TABLE = "CREATE TABLE " + VillageTable.TABLE_NAME + " (" +
                VillageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                VillageTable.COLUMN_VILLAGE_CODE + " TEXT, " +
                VillageTable.COLUMN_VILLAGE_NAME + " TEXT, " +
                VillageTable.COLUMN_UC_CODE + " TEXT " +
                ");";

        final String SQL_CREATE_LHW_TABLE = "CREATE TABLE " + LHWTable.TABLE_NAME + " (" +
                LHWTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LHWTable.COLUMN_LHW_CODE + " TEXT, " +
                LHWTable.COLUMN_LHW_NAME + " TEXT, " +
                LHWTable.COLUMN_AREA_TYPE + " TEXT, " +
                LHWTable.COLUMN_STATUS + " TEXT, " +
                LHWTable.COLUMN_HF_CODE + " TEXT " +
                ");";

        final String SQL_CREATE_USERS = "CREATE TABLE " + singleUser.TABLE_NAME + "("
                + singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + singleUser.ROW_USERNAME + " TEXT,"
                + singleUser.ROW_PASSWORD + " TEXT );";

        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_LISTING_TABLE);
        db.execSQL(SQL_CREATE_PW_TABLE);
        db.execSQL(SQL_CREATE_TEHSIL_TABLE);
        db.execSQL(SQL_CREATE_UC_TABLE);
        db.execSQL(SQL_CREATE_VILLAGE_TABLE);
        db.execSQL(SQL_CREATE_H_FACILIY_TABLE);
        db.execSQL(SQL_CREATE_LHW_TABLE);
        db.execSQL(SQL_CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PwTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TehsilTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UcTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VillageTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HFacilityTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LHWTable.TABLE_NAME);
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

        values.put(ListingEntry.COLUMN_NAME_HH04, lc.getHh04());
        values.put(ListingEntry.COLUMN_NAME_HH04x, lc.getHh04x());
        values.put(ListingEntry.COLUMN_NAME_HH05, lc.getHh05());
        values.put(ListingEntry.COLUMN_NAME_HH06, lc.getHh06());
        values.put(ListingEntry.COLUMN_NAME_HH07, lc.getHh07());
        values.put(ListingEntry.COLUMN_NAME_HH07n, lc.getHh07n());
        values.put(ListingEntry.COLUMN_NAME_HH08, lc.getHh08());
        values.put(ListingEntry.COLUMN_NAME_HH09, lc.getHh09());
        values.put(ListingEntry.COLUMN_NAME_HH09A, lc.getHh09a());
        values.put(ListingEntry.COLUMN_NAME_HH09B, lc.getHh09b());
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

    public Long addPw(PWContract pw) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(PwTable.PW_ID, pw.getID());
        values.put(PwTable.PW_UUID, pw.getUUID());
        values.put(PwTable.PW_UID, pw.getUID());
        values.put(PwTable.PW_PWDT, pw.getMwDT());
        values.put(PwTable.PW_PWVILLAGECODE, pw.getMwVillageCode());
        values.put(PwTable.PW_PWSTRUCTURENO, pw.getMwStructureNo());
        values.put(PwTable.PW_PW01, pw.getMw01());
        values.put(PwTable.PW_PW02, pw.getMw02());
        values.put(PwTable.PW_PW03, pw.getMw03());
        values.put(PwTable.PW_DEVICE_ID, pw.getDeviceId());
        values.put(PwTable.PW_LHW_CODE, pw.getLhwCode());
        values.put(PwTable.PW_HOUSEHOLD, pw.getHousehold());
        values.put(PwTable.PW_SYNCED, pw.getSynced());
        values.put(PwTable.PW_SYNCED_DATE, pw.getSyncedDate());

        long newRowId;
        newRowId = db.insert(
                PwTable.TABLE_NAME,
                PwTable.PW_NULLABLE,
                values);
        DB_PW_ID = String.valueOf(newRowId);

        return newRowId;
    }

    public Long addTehsil(TehsilsContract dc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TehsilTable.COLUMN_TEHSIL_CODE, dc.getTehsil_code());
        values.put(TehsilTable.COLUMN_TEHSIL_NAME, dc.getTehsil_name());

        long newRowId;
        newRowId = db.insert(
                TehsilTable.TABLE_NAME,
                TehsilTable.COLUMN_NAME_NULLABLE,
                values);

        return newRowId;
    }

    public int updatePw() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(PwTable.PW_UID, AppMain.pw.getUID());


// Which row to update, based on the ID
        String selection = PwTable._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(AppMain.pw.getID())};

        int count = db.update(PwTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateForm() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, AppMain.lc.getUID());
        values.put(ListingEntry.COLUMN_NAME_HH10, AppMain.lc.getHh10());
        values.put(ListingEntry.COLUMN_NAME_HH11, AppMain.lc.getHh11());
        values.put(ListingEntry.COLUMN_NAME_HH12, AppMain.lc.getHh12());
        values.put(ListingEntry.COLUMN_NAME_HH13, AppMain.lc.getHh13());
        values.put(ListingEntry.COLUMN_NAME_FORMSTATUS, "1");


// Which row to update, based on the ID
        String selection = ListingEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(AppMain.lc.getID())};

        int count = db.update(ListingEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
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
                ListingEntry.COLUMN_NAME_HH09A,
                ListingEntry.COLUMN_NAME_HH09B,
                ListingEntry.COLUMN_NAME_HH10,
                ListingEntry.COLUMN_NAME_HH11,
                ListingEntry.COLUMN_NAME_HH12,
                ListingEntry.COLUMN_NAME_HH13,
                ListingEntry.COLUMN_NAME_CHILD_NAME,
                ListingEntry.COLUMN_NAME_DEVICEID,
                ListingEntry.COLUMN_NAME_GPSLat,
                ListingEntry.COLUMN_NAME_GPSLng,
                ListingEntry.COLUMN_NAME_GPSTime,
                ListingEntry.COLUMN_NAME_GPSAccuracy,
                ListingEntry.COLUMN_NAME_ROUND,
                ListingEntry.COLUMN_NAME_FORMSTATUS
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

    public Collection<PWContract> getAllPws() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PwTable.PW_ID,
                PwTable.PW_UUID,
                PwTable.PW_UID,
                PwTable.PW_PWDT,
                PwTable.PW_PWVILLAGECODE,
                PwTable.PW_PWSTRUCTURENO,
                PwTable.PW_PW01,
                PwTable.PW_PW02,
                PwTable.PW_PW03,
                PwTable.PW_DEVICE_ID,
                PwTable.PW_LHW_CODE,
                PwTable.PW_HOUSEHOLD,
                PwTable.PW_SYNCED,
                PwTable.PW_SYNCED_DATE

        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                PwTable._ID + " ASC";

        Collection<PWContract> allPW = new ArrayList<PWContract>();
        try {
            c = db.query(
                    PwTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                PWContract pw = new PWContract();
                allPW.add(pw.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPW;
    }


    public Collection<UCsContract> getAllUcs() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                UcTable._ID,
                UcTable.COLUMN_UC_CODE,
                UcTable.COLUMN_UC_NAME,
                UcTable.COLUMN_TEHSIL_CODE,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                UcTable._ID + " ASC";

        Collection<UCsContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    UcTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );

            UCsContract uc1 = new UCsContract();
            allDC.add(uc1.setDefaultVal("", "..."));

            while (c.moveToNext()) {
                UCsContract dc = new UCsContract();
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

    public Collection<UCsContract> getAllUCsByTehsil(String tehsil_code) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                UcTable._ID,
                UcTable.COLUMN_UC_NAME,
                UcTable.COLUMN_UC_CODE,
                UcTable.COLUMN_TEHSIL_CODE,
        };

        String whereClause = UcTable.COLUMN_TEHSIL_CODE + " = ?";
        String[] whereArgs = {tehsil_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                UcTable._ID + " ASC";

        Collection<UCsContract> allUCsC = new ArrayList<>();
        try {
            c = db.query(
                    UcTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );

            UCsContract uc1 = new UCsContract();
            allUCsC.add(uc1.setDefaultVal("", "..."));

            while (c.moveToNext()) {
                UCsContract ucsc = new UCsContract();
                allUCsC.add(ucsc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allUCsC;
    }

    public Collection<HFacilitiesContract> getAllHFacilities() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                HFacilityTable._ID,
                HFacilityTable.COLUMN_HFACILITY_CODE,
                HFacilityTable.COLUMN_HFACILITY_NAME,
                HFacilityTable.COLUMN_TEHSIL_CODE,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                HFacilityTable._ID + " ASC";

        Collection<HFacilitiesContract> allHFC = new ArrayList<>();
        try {
            c = db.query(
                    HFacilityTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFacilitiesContract hfc = new HFacilitiesContract();
                allHFC.add(hfc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHFC;
    }

    public Collection<HFacilitiesContract> getAllHFacilitiesByTehsil(String tehsil_code) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                HFacilityTable._ID,
                HFacilityTable.COLUMN_HFACILITY_CODE,
                HFacilityTable.COLUMN_HFACILITY_NAME,
                HFacilityTable.COLUMN_TEHSIL_CODE,
        };

        String whereClause = HFacilityTable.COLUMN_TEHSIL_CODE + " = ?";
        String[] whereArgs = {tehsil_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFacilityTable._ID + " ASC";

        Collection<HFacilitiesContract> allHFC = new ArrayList<>();
        try {
            c = db.query(
                    HFacilityTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFacilitiesContract hfc = new HFacilitiesContract();
                allHFC.add(hfc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHFC;
    }

    public Collection<VillagesContract> getAllVillagesByUc(String uc_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VillageTable._ID,
                VillageTable.COLUMN_VILLAGE_CODE,
                VillageTable.COLUMN_VILLAGE_NAME,
                VillageTable.COLUMN_UC_CODE
        };

        String whereClause = VillageTable.COLUMN_UC_CODE + " = ?";
        String[] whereArgs = {uc_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                VillageTable.COLUMN_VILLAGE_CODE + " ASC";

        Collection<VillagesContract> allPC = new ArrayList<VillagesContract>();
        try {
            c = db.query(
                    VillageTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );

            VillagesContract uc1 = new VillagesContract();
            allPC.add(uc1.setDefaultVal("", "..."));

            while (c.moveToNext()) {
                VillagesContract pc = new VillagesContract();
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

    public Collection<LHWsContract> getAllLhwsByHf(String hf_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                LHWTable._ID,
                LHWTable.COLUMN_LHW_CODE,
                LHWTable.COLUMN_LHW_NAME,
                LHWTable.COLUMN_HF_CODE,
                LHWTable.COLUMN_AREA_TYPE,
                LHWTable.COLUMN_STATUS
        };

        String whereClause = LHWTable.COLUMN_HF_CODE + " = ?";
        String[] whereArgs = {hf_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                LHWTable.COLUMN_LHW_CODE + " ASC";

        Collection<LHWsContract> allLhwC = new ArrayList<LHWsContract>();
        try {
            c = db.query(
                    LHWTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                LHWsContract lhwc = new LHWsContract();
                allLhwC.add(lhwc.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allLhwC;
    }


    public Collection<TehsilsContract> getAllTehsil() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                TehsilTable._ID,
                TehsilTable.COLUMN_TEHSIL_CODE,
                TehsilTable.COLUMN_TEHSIL_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TehsilTable._ID + " ASC";

        Collection<TehsilsContract> allDC = new ArrayList<TehsilsContract>();
        try {
            c = db.query(
                    TehsilTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                TehsilsContract dc = new TehsilsContract();
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


    public Collection<VillagesContract> getAllVillage() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VillageTable._ID,
                VillageTable.COLUMN_VILLAGE_CODE,
                VillageTable.COLUMN_VILLAGE_NAME,
                VillageTable.COLUMN_UC_CODE
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                VillageTable._ID + " ASC";

        Collection<VillagesContract> allDC = new ArrayList<VillagesContract>();
        try {
            c = db.query(
                    VillageTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                VillagesContract vc = new VillagesContract();
                allDC.add(vc.hydrate(c));
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
        values.put(ListingEntry.COLUMN_NAME_HH09A, lc.getHh09a());
        values.put(ListingEntry.COLUMN_NAME_HH09B, lc.getHh09b());
        values.put(ListingEntry.COLUMN_NAME_HH10, lc.getHh10());
        values.put(ListingEntry.COLUMN_NAME_HH11, lc.getHh11());
        values.put(ListingEntry.COLUMN_NAME_HH12, lc.getHh12());
        values.put(ListingEntry.COLUMN_NAME_HH13, lc.getHh13());
        values.put(ListingEntry.COLUMN_NAME_CHILD_NAME, lc.getHhChildNm());
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_NAME_GPSLat, lc.getGPSLat());
        values.put(ListingEntry.COLUMN_NAME_GPSLng, lc.getGPSLng());
        values.put(ListingEntry.COLUMN_NAME_GPSTime, lc.getGPSTime());
        values.put(ListingEntry.COLUMN_NAME_GPSAccuracy, lc.getGPSAcc());
        values.put(ListingEntry.COLUMN_NAME_ROUND, lc.getRound());
        values.put(ListingEntry.COLUMN_NAME_FORMSTATUS, lc.getfStatus());

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
        lc.setHh09a(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH09A))));
        lc.setHh09b(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH09B))));
        lc.setHh10(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH10))));
        lc.setHh11(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH11))));
        lc.setHh12(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH12))));
        lc.setHh13(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH13))));
        lc.setHhChildNm(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_CHILD_NAME))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_DEVICEID))));
        lc.setGPSLat(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLat))));
        lc.setGPSLng(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSLng))));
        lc.setGPSTime(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSTime))));
        lc.setGPSAcc(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_GPSAccuracy))));
        lc.setRound(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_ROUND))));
        lc.setfStatus(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_FORMSTATUS))));

        return lc;
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + "=? AND " + UsersContract.singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
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

    public void syncUser(JSONArray userlist) {
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


    public void syncUc(JSONArray ucList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UcTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = ucList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectUc = jsonArray.getJSONObject(i);

                UCsContract dc = new UCsContract();
                dc.sync(jsonObjectUc);

                ContentValues values = new ContentValues();

                values.put(UcTable.COLUMN_UC_CODE, dc.getUcCode());
                values.put(UcTable.COLUMN_UC_NAME, dc.getUcName());
                values.put(UcTable.COLUMN_TEHSIL_CODE, dc.getTehsilCode());

                db.insert(UcTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {
            Log.e(TAG, "syncUc: "+e.toString() );
        }
    }


    public void syncTehsil(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TehsilTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectTehsil = jsonArray.getJSONObject(i);

                TehsilsContract pc = new TehsilsContract();
                pc.sync(jsonObjectTehsil);

                ContentValues values = new ContentValues();

                values.put(TehsilTable.COLUMN_TEHSIL_CODE, pc.getTehsil_code());
                values.put(TehsilTable.COLUMN_TEHSIL_NAME, pc.getTehsil_name());

                db.insert(TehsilTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncHFacility(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HFacilityTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectHFacility = jsonArray.getJSONObject(i);

                HFacilitiesContract hc = new HFacilitiesContract();
                hc.sync(jsonObjectHFacility);

                ContentValues values = new ContentValues();

                values.put(HFacilityTable.COLUMN_HFACILITY_CODE, hc.gethFacilityCode());
                values.put(HFacilityTable.COLUMN_HFACILITY_NAME, hc.gethFacilityName());
                values.put(HFacilityTable.COLUMN_TEHSIL_CODE, hc.getTehsilCode());

                db.insert(HFacilityTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


    public void syncVillages(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VillageTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectVillage = jsonArray.getJSONObject(i);

                VillagesContract vc = new VillagesContract();
                vc.sync(jsonObjectVillage);

                ContentValues values = new ContentValues();

                values.put(VillageTable.COLUMN_VILLAGE_CODE, vc.getVillageCode());
                values.put(VillageTable.COLUMN_VILLAGE_NAME, vc.getVillageName());
                values.put(VillageTable.COLUMN_UC_CODE, vc.getUcCode());

                db.insert(VillageTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public void syncLHW(JSONArray lcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LHWsContract.LHWTable.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = lcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectLHW = jsonArray.getJSONObject(i);

                LHWsContract lc = new LHWsContract();
                lc.sync(jsonObjectLHW);

                ContentValues values = new ContentValues();

                values.put(LHWTable.COLUMN_LHW_CODE, lc.getLHWCode());
                values.put(LHWTable.COLUMN_LHW_NAME, lc.getLHWName());
                values.put(LHWTable.COLUMN_AREA_TYPE, lc.getAreaType());
                values.put(LHWTable.COLUMN_HF_CODE, lc.getHfCode());
                values.put(LHWTable.COLUMN_STATUS, lc.getStatus());

                db.insert(LHWTable.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


}
