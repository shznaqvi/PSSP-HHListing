package edu.aku.hassannaqvi.tmk_hhlisting.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class pssphhlDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pssp-hhl.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = pssphhlDBHelper.class.getSimpleName();

    public pssphhlDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS listings (_id INTEGER PRIMARY KEY,uid TEXT NOT NULL, hhdt TEXT NOT NULL, hh01 TEXT NOT NULL, hh02 TEXT NOT NULL, hh03 TEXT NOT NULL, hh04 TEXT NOT NULL, hh04x TEXT NOT NULL, hh05 TEXT NOT NULL, hh06 TEXT NOT NULL, hh07 TEXT NOT NULL, hh08 TEXT NOT NULL, hh09 TEXT NOT NULL, hh10 TEXT NOT NULL, hh11 TEXT NOT NULL, child_name TEXT NOT NULL, deviceid TEXT NOT NULL  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Update database from version  " + oldVersion
                + " to " + newVersion + ", which remove all old records");
        onCreate(db);
    }

}