package edu.aku.hassannaqvi.naunehal_listing.contracts;

import android.provider.BaseColumns;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class ListingContract {

    public static String CONTENT_AUTHORITY = "edu.aku.hassannaqvi.naunehal_listing";


    public static abstract class TableListings implements BaseColumns {

        public static final String TABLE_NAME = "listings";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_HHDATETIME = "hhdt";
        public static final String COLUMN_NAME_HL01 = "hl01";
        public static final String COLUMN_NAME_HL02 = "hl02";
        public static final String COLUMN_NAME_HL03 = "hl03";
        public static final String COLUMN_NAME_HL04 = "hl04";
        public static final String COLUMN_NAME_HL05 = "hl05";
        public static final String COLUMN_NAME_HL06 = "hl06";
        public static final String COLUMN_NAME_HL07 = "hl07";
        public static final String COLUMN_NAME_HL0796X = "hl0796x";
        public static final String COLUMN_NAME_HL08 = "hl08";
        public static final String COLUMN_NAME_HL09 = "hl09";
        public static final String COLUMN_NAME_HL10 = "hl10";
        public static final String COLUMN_NAME_HL11 = "hl11";
        public static final String COLUMN_NAME_HL12M = "hl12m";
        public static final String COLUMN_NAME_HL12D = "hl12d";
        public static final String COLUMN_NAME_CHILD_NAME = "child_name";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
        public static final String COLUMN_NAME_GPSLat = "gpslat";
        public static final String COLUMN_NAME_GPSLng = "gpslng";
        public static final String COLUMN_NAME_GPSTime = "gpstime";
        public static final String COLUMN_NAME_GPSAccuracy = "gpsacc";
        public static final String COLUMN_NAME_ROUND = "round";
    }
}