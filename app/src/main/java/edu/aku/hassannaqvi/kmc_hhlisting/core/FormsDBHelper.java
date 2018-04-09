package edu.aku.hassannaqvi.kmc_hhlisting.core;

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

import edu.aku.hassannaqvi.kmc_hhlisting.contract.ChildContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.ChildContract.ChildTable;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.DeliveryContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.DeliveryContract.DeliveryTable;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.DistrictsContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.DistrictsContract.singleDistrict;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.ListingContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.ListingContract.ListingEntry;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.MwraContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.MwraContract.MwraEntry;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.UCsContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.UCsContract.singleUC;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.UsersContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.UsersContract.singleUser;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.VillagesContract;
import edu.aku.hassannaqvi.kmc_hhlisting.contract.VillagesContract.singleVillage;


/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class FormsDBHelper extends SQLiteOpenHelper {

    // Change this when you change the database schema.
    private static final int DATABASE_VERSION = 1;
    // The name of database.
    private static final String DATABASE_NAME = "kmc-hhl.db";
    public static String TAG = "FormsDBHelper";
    public static String DB_FORM_ID;
    public static String DB_MWRA_ID;


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
                ListingEntry.COLUMN_NAME_HH04_VILLAGE + " TEXT, " +
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
                ListingEntry.COLUMN_NAME_DEVICEID + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLat + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSLng + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSTime + " TEXT, " +
                ListingEntry.COLUMN_NAME_ROUND + " TEXT, " +
                ListingEntry.COLUMN_NAME_FORMSTATUS + " TEXT, " +
                ListingEntry.COLUMN_NAME_GPSAccuracy + " TEXT, " +
                ListingEntry.COLUMN_SYNCED + " TEXT, " +
                ListingEntry.COLUMN_SYNCED_DATE + " TEXT " +
                " );";

        final String SQL_CREATE_MWRA_TABLE = "CREATE TABLE " + MwraEntry.TABLE_NAME + " (" +
                MwraEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MwraEntry.MWRA_ID + " TEXT," +
                MwraEntry.MWRA_UUID + " TEXT," +
                MwraEntry.MWRA_UID + " TEXT," +
                MwraEntry.MWRA_USERNAME + " TEXT," +
                MwraEntry.MWRA_MWDT + " TEXT," +
                MwraEntry.MWRA_MWVILLAGECODE + " TEXT," +
                MwraEntry.MWRA_MWSTRUCTURENO + " TEXT," +
                MwraEntry.MWRA_MW01 + " TEXT," +
                MwraEntry.MWRA_MW02 + " TEXT," +
                MwraEntry.MWRA_MW03 + " TEXT," +
                MwraEntry.MWRA_MW04 + " TEXT," +
                MwraEntry.MWRA_MW05 + " TEXT," +
                MwraEntry.COLUMN_SYNCED + " TEXT, " +
                MwraEntry.COLUMN_SYNCED_DATE + " TEXT " +
                " );";

        final String SQL_CREATE_DISTRICT_TABLE = "CREATE TABLE " + singleDistrict.TABLE_NAME + " (" +
                singleDistrict._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                singleDistrict.COLUMN_DISTRICT_CODE + " TEXT, " +
                singleDistrict.COLUMN_DISTRICT_NAME + " TEXT " +
                ");";

        final String SQL_CREATE_PSU_TABLE = "CREATE TABLE " + singleVillage.TABLE_NAME + " (" +
                singleVillage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                singleVillage.COLUMN_VILLAGE_CODE + " TEXT, " +
                singleVillage.COLUMN_VILLAGE_NAME + " TEXT, " +
                singleVillage.COLUMN_DISTRICT_CODE + " TEXT, " +
                singleVillage.COLUMN_UC_CODE + " TEXT " +
                ");";

        final String SQL_CREATE_USERS = "CREATE TABLE " + singleUser.TABLE_NAME + "("
                + singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + singleUser.ROW_USERNAME + " TEXT,"
                + singleUser.ROW_PASSWORD + " TEXT );";

        final String SQL_CREATE_UCS = "CREATE TABLE " + singleUC.TABLE_NAME + "("
                + singleUC._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + singleUC.COLUMN_UC_NAME + " TEXT,"
                + singleUC.COLUMN_DISTRICT_CODE + " TEXT,"
                + singleUC.COLUMN_UC_CODE + " TEXT );";

        final String SQL_CREATE_CHILD_FORMS = "CREATE TABLE "
                + ChildTable.TABLE_NAME + "("
                + ChildTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ChildTable.COLUMN_PROJECTNAME + " TEXT," +
                ChildTable.COLUMN_UID + " TEXT," +
                ChildTable.COLUMN_UUID + " TEXT," +
                ChildTable.COLUMN_FORMDATE + " TEXT," +
                ChildTable.COLUMN_USER + " TEXT," +
                ChildTable.COLUMN_C1SERIALNO + " TEXT," +
                ChildTable.COLUMN_SC1 + " TEXT," +
/*                ChildTable.COLUMN_SC2 + " TEXT," +
                ChildTable.COLUMN_SC3 + " TEXT," +
                ChildTable.COLUMN_SC4 + " TEXT," +
                ChildTable.COLUMN_SC5 + " TEXT," +*/
                ChildTable.COLUMN_DEVICEID + " TEXT," +
                ChildTable.COLUMN_DEVICETAGID + " TEXT," +
                ChildTable.COLUMN_SYNCED + " TEXT," +
                ChildTable.COLUMN_SYNCED_DATE + " TEXT," +
                ChildTable.COLUMN_APPVERSION + " TEXT " + " );";

        final String SQL_CREATE_DELIVERY_FORMS = "CREATE TABLE "
                + DeliveryTable.TABLE_NAME + "("
                + DeliveryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DeliveryTable.COLUMN_PROJECTNAME + " TEXT," +
                DeliveryTable.COLUMN_UID + " TEXT," +
                DeliveryTable.COLUMN_UUID + " TEXT," +
                DeliveryTable.COLUMN_FORMDATE + " TEXT," +
                DeliveryTable.COLUMN_USER + " TEXT," +
                DeliveryTable.COLUMN_D1SerialNo + " TEXT," +
                DeliveryTable.COLUMN_SD1 + " TEXT," +
                DeliveryTable.COLUMN_DEVICEID + " TEXT," +
                DeliveryTable.COLUMN_DEVICETAGID + " TEXT," +
                DeliveryTable.COLUMN_SYNCED + " TEXT," +
                DeliveryTable.COLUMN_SYNCED_DATE + " TEXT," +
                DeliveryTable.COLUMN_APPVERSION + " TEXT " + " );";

        // Do the creating of the databases.
        db.execSQL(SQL_CREATE_LISTING_TABLE);
        db.execSQL(SQL_CREATE_MWRA_TABLE);
        db.execSQL(SQL_CREATE_DISTRICT_TABLE);
        db.execSQL(SQL_CREATE_PSU_TABLE);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_UCS);
        db.execSQL(SQL_CREATE_DELIVERY_FORMS);

        db.execSQL(SQL_CREATE_CHILD_FORMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MwraEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleDistrict.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleVillage.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleUser.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + singleUC.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ChildTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DeliveryTable.TABLE_NAME);
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
        values.put(ListingEntry.COLUMN_NAME_HH04_VILLAGE, lc.getHh04Village());

        AppMain.updatePSU(lc.getHh04Village(), lc.getHh03());

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
        values.put(ListingEntry.COLUMN_NAME_DEVICEID, lc.getDeviceID());
        values.put(ListingEntry.COLUMN_NAME_DEVICETAGID, lc.getDeviceTagID());
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

    public Long addMwra(MwraContract mwra) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(MwraEntry.COLUMN_ID, mwra.getID());
        values.put(MwraEntry.MWRA_ID, mwra.getMWRAID());
        values.put(MwraEntry.MWRA_UUID, mwra.getUUID());
        values.put(MwraEntry.MWRA_UID, mwra.getUID());
        values.put(MwraEntry.MWRA_USERNAME, mwra.getMwUSERNAME());
        values.put(MwraEntry.MWRA_MWDT, mwra.getMwDT());
        values.put(MwraEntry.MWRA_MWVILLAGECODE, mwra.getMwVillageCode());
        values.put(MwraEntry.MWRA_MWSTRUCTURENO, mwra.getMwStructureNo());
        values.put(MwraEntry.MWRA_MW01, mwra.getMw01());
        values.put(MwraEntry.MWRA_MW02, mwra.getMw02());
        values.put(MwraEntry.MWRA_MW03, mwra.getMw03());
        values.put(MwraEntry.MWRA_MW04, mwra.getMw04());
        values.put(MwraEntry.MWRA_MW05, mwra.getMw05());
        values.put(MwraEntry.DEVICE_TAGID, mwra.getDeviceTagID());
        values.put(MwraEntry.APP_VERSION, mwra.getAppVersion());

        long newRowId;
        newRowId = db.insert(
                MwraEntry.TABLE_NAME,
                MwraEntry.MWRA_NULLABLE,
                values);
        DB_MWRA_ID = String.valueOf(newRowId);

        return newRowId;
    }

    public Long addChildForm(ChildContract cc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_PROJECTNAME, cc.getProjectName());
        values.put(ChildTable.COLUMN_UID, cc.getUID());
        values.put(ChildTable.COLUMN_UUID, cc.getUUID());
        values.put(ChildTable.COLUMN_FORMDATE, cc.getFormDate());
        values.put(ChildTable.COLUMN_USER, cc.getUser());
        values.put(ChildTable.COLUMN_C1SERIALNO, cc.getC1SerialNo());
        values.put(ChildTable.COLUMN_SC1, cc.getsC1());
/*        values.put(ChildTable.COLUMN_SC2, cc.getsC2());
        values.put(ChildTable.COLUMN_SC3, cc.getsC3());
        values.put(ChildTable.COLUMN_SC4, cc.getsC4());
        values.put(ChildTable.COLUMN_SC5, cc.getsC5());*/
        values.put(ChildTable.COLUMN_DEVICEID, cc.getDeviceID());
        values.put(ChildTable.COLUMN_DEVICETAGID, cc.getDevicetagID());
        values.put(ChildTable.COLUMN_APPVERSION, cc.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ChildTable.TABLE_NAME,
                ChildTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public int updateFormChildID() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_UID, AppMain.cc.getUID());

        // Which row to update, based on the ID
        String selection = ChildTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(AppMain.cc.get_ID())};

        int count = db.update(ChildTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Long addDeliveryForm(DeliveryContract dc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DeliveryTable.COLUMN_PROJECTNAME, dc.getProjectName());
        values.put(DeliveryTable.COLUMN_UID, dc.getUID());
        values.put(DeliveryTable.COLUMN_UUID, dc.getUUID());
        values.put(DeliveryTable.COLUMN_FORMDATE, dc.getFormDate());
        values.put(DeliveryTable.COLUMN_USER, dc.getUser());
        values.put(DeliveryTable.COLUMN_D1SerialNo, dc.getD1SerialNo());
        values.put(DeliveryTable.COLUMN_SD1, dc.getsD1());
        values.put(DeliveryTable.COLUMN_DEVICEID, dc.getDeviceID());
        values.put(DeliveryTable.COLUMN_DEVICETAGID, dc.getDevicetagID());
        values.put(DeliveryTable.COLUMN_APPVERSION, dc.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                DeliveryTable.TABLE_NAME,
                DeliveryTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public int updateFormDeliveryID() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DeliveryTable.COLUMN_UID, AppMain.dc.getUID());

        // Which row to update, based on the ID
        String selection = DeliveryTable.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(AppMain.dc.get_ID())};

        int count = db.update(DeliveryTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
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

    public void updateSyncedChild(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ChildTable.COLUMN_SYNCED, true);
        values.put(ChildTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = ChildTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                ChildTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedDelivery(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(DeliveryTable.COLUMN_SYNCED, true);
        values.put(DeliveryTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = DeliveryTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                DeliveryTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateSyncedMwra(String id) {
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

    public int updateMwra() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MwraEntry.MWRA_UID, AppMain.mwra.getUID());


// Which row to update, based on the ID
        String selection = MwraEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(AppMain.mwra.getID())};

        int count = db.update(MwraEntry.TABLE_NAME,
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

    public int updateFormUID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(ListingEntry.COLUMN_NAME_UID, AppMain.lc.getUID());
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
                ListingEntry.COLUMN_NAME_HH04_VILLAGE,
                ListingEntry.COLUMN_NAME_DEVICEID,
                ListingEntry.COLUMN_NAME_GPSLat,
                ListingEntry.COLUMN_NAME_GPSLng,
                ListingEntry.COLUMN_NAME_GPSTime,
                ListingEntry.COLUMN_NAME_GPSAccuracy,
                ListingEntry.COLUMN_NAME_ROUND,
                ListingEntry.COLUMN_NAME_FORMSTATUS,
                ListingEntry.COLUMN_NAME_DEVICETAGID,
                ListingEntry.APP_VERSION
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

    public Collection<MwraContract> getAllMwras() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MwraEntry.COLUMN_ID,
                MwraEntry.MWRA_ID,
                MwraEntry.MWRA_UUID,
                MwraEntry.MWRA_UID,
                MwraEntry.MWRA_USERNAME,
                MwraEntry.MWRA_MWDT,
                MwraEntry.MWRA_MWVILLAGECODE,
                MwraEntry.MWRA_MWSTRUCTURENO,
                MwraEntry.MWRA_MW01,
                MwraEntry.MWRA_MW02,
                MwraEntry.MWRA_MW03,
                MwraEntry.MWRA_MW04,
                MwraEntry.MWRA_MW05,
                MwraEntry.DEVICE_TAGID,
                MwraEntry.APP_VERSION
/*                MwraEntry.COLUMN_SYNCED,
                MwraEntry.COLUMN_SYNCED_DATE,*/

        };

        String whereClause = MwraEntry.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                MwraEntry._ID + " ASC";

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

    public Collection<ChildContract> getAllChild() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ChildTable.COLUMN_ID,
                ChildTable.COLUMN_UID,
                ChildTable.COLUMN_UUID,
                ChildTable.COLUMN_FORMDATE,
                ChildTable.COLUMN_USER,
                ChildTable.COLUMN_C1SERIALNO,
                ChildTable.COLUMN_SC1,
                ChildTable.COLUMN_DEVICEID,
                ChildTable.COLUMN_DEVICETAGID,
                ChildTable.COLUMN_SYNCED,
                ChildTable.COLUMN_SYNCED_DATE,
                ChildTable.COLUMN_APPVERSION
        };

        String whereClause = ChildTable.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                ChildTable._ID + " ASC";

        Collection<ChildContract> allChild = new ArrayList<>();
        try {
            c = db.query(
                    ChildTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                ChildContract mwra = new ChildContract();
                allChild.add(mwra.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allChild;
    }

    public Collection<DeliveryContract> getAllDelivery() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                DeliveryTable.COLUMN_ID,
                DeliveryTable.COLUMN_UID,
                DeliveryTable.COLUMN_UUID,
                DeliveryTable.COLUMN_FORMDATE,
                DeliveryTable.COLUMN_USER,
                DeliveryTable.COLUMN_D1SerialNo,
                DeliveryTable.COLUMN_SD1,
                DeliveryTable.COLUMN_DEVICEID,
                DeliveryTable.COLUMN_DEVICETAGID,
                DeliveryTable.COLUMN_SYNCED_DATE,
                DeliveryTable.COLUMN_SYNCED,
                DeliveryTable.COLUMN_APPVERSION
        };

        String whereClause = DeliveryTable.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                DeliveryTable._ID + " ASC";

        Collection<DeliveryContract> allDelivery = new ArrayList<>();
        try {
            c = db.query(
                    DeliveryTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                DeliveryContract mwra = new DeliveryContract();
                allDelivery.add(mwra.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDelivery;
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
                singleDistrict.COLUMN_DISTRICT_NAME + " ASC";

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

    public Collection<VillagesContract> getAllPSUsByDistrict(String district_code, String uc_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleVillage._ID,
                singleVillage.COLUMN_VILLAGE_CODE,
                singleVillage.COLUMN_VILLAGE_NAME,
                singleVillage.COLUMN_DISTRICT_CODE,
                singleVillage.COLUMN_UC_CODE

        };

        String whereClause = singleVillage.COLUMN_DISTRICT_CODE + " =? AND " + singleVillage.COLUMN_UC_CODE + " =?";

        String[] whereArgs = {district_code, uc_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleVillage.COLUMN_VILLAGE_NAME + " ASC";

        Collection<VillagesContract> allPC = new ArrayList<VillagesContract>();
        try {
            c = db.query(
                    singleVillage.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
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
        values.put(ListingEntry.COLUMN_NAME_HH04_VILLAGE, lc.getHh04Village());
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
        lc.setHh04Village(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_HH04_VILLAGE))));
        lc.setDeviceID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_DEVICEID))));
        lc.setDeviceTagID(String.valueOf(c.getString(c.getColumnIndex(ListingEntry.COLUMN_NAME_DEVICETAGID))));
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


    public void syncUCs(JSONArray dcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleUC.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = dcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectDistrict = jsonArray.getJSONObject(i);

                UCsContract dc = new UCsContract();
                dc.sync(jsonObjectDistrict);

                ContentValues values = new ContentValues();

                values.put(singleUC.COLUMN_UC_CODE, dc.getUcCode());
                values.put(singleUC.COLUMN_UC_NAME, dc.getUcName());
                values.put(singleUC.COLUMN_DISTRICT_CODE, dc.getDistrictCode());

                db.insert(singleUC.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }

    public Collection<UCsContract> getAllUCsbyTaluka(String taluka_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleUC._ID,
                singleUC.COLUMN_UC_CODE,
                singleUC.COLUMN_UC_NAME,
                singleUC.COLUMN_DISTRICT_CODE
        };

        String whereClause = singleUC.COLUMN_DISTRICT_CODE + " = ?";
        String[] whereArgs = {taluka_code};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleUC.COLUMN_UC_NAME + " ASC";

        Collection<UCsContract> allPC = new ArrayList<UCsContract>();
        try {
            c = db.query(
                    singleUC.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UCsContract pc = new UCsContract();
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

    public void syncVillages(JSONArray pcList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(singleVillage.TABLE_NAME, null, null);

        try {
            JSONArray jsonArray = pcList;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPSU = jsonArray.getJSONObject(i);

                VillagesContract vc = new VillagesContract();
                vc.sync(jsonObjectPSU);
                Log.i(TAG, "syncVillages: " + jsonObjectPSU.toString());

                ContentValues values = new ContentValues();

                values.put(singleVillage.COLUMN_UC_CODE, vc.getUcCode());
                values.put(singleVillage.COLUMN_VILLAGE_CODE, vc.getVillageCode());
                values.put(singleVillage.COLUMN_VILLAGE_NAME, vc.getVillageName());
                values.put(singleVillage.COLUMN_DISTRICT_CODE, vc.getDistrictCode());

                db.insert(singleVillage.TABLE_NAME, null, values);
            }
            db.close();

        } catch (Exception e) {

        }
    }


}
