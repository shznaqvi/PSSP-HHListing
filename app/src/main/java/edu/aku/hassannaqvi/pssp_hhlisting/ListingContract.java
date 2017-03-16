package edu.aku.hassannaqvi.pssp_hhlisting;

import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class ListingContract {

    public static String userName;
    public String ID;
    public String UID;
    public String hhDT;
    public String hh01;
    public String hh02;
    public String hh03;
    public String hhadd;
    public String hh04;
    public String hh04x;
    public String hh05;
    public String hh06;
    public String hh07;
    public String hh07n;
    public String hh08;
    public String hh09;
    public String hh09a;
    public String hh09b;
    public String hh10;
    public String hh11;
    public String hh12d;

    public String getHh12d() {
        return hh12d;
    }

    public void setHh12d(String hh12d) {
        this.hh12d = hh12d;
    }

    public String getHh12m() {
        return hh12m;
    }

    public void setHh12m(String hh12m) {
        this.hh12m = hh12m;
    }

    public String hh12m;
    public String hh12;
    public String hh13;
    public String hhChildNm;
    public String DeviceID;
    public String GPSLat;
    public String GPSLng;
    public String GPSTime;
    public String GPSAcc;
    public String Round = "1";
    public String fStatus = "3";


    public ListingContract() {
    }

    public ListingContract(String ID) {
        this.ID = ID;
    }

    public ListingContract(String hh01, String hh02, String hh03, String hh04, String hh04x, String hh05, String hh06, String hh07, String DeviceID, String GPSLat, String GPSLng, String GPSTime, String GPSAcc) {
        this.hh01 = hh01;
        this.hh02 = hh02;
        this.hh03 = hh03;
        this.hh04 = hh04;
        this.hh04x = hh04x;
        this.hh05 = hh05;
        this.hh06 = hh06;
        this.hh07 = hh07;
        this.DeviceID = DeviceID;
        this.GPSLat = GPSLat;
        this.GPSLng = GPSLng;
        this.GPSTime = GPSTime;
        this.GPSAcc = GPSAcc;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHhDT() {
        return hhDT;
    }

    public void setHhDT(String hhDT) {
        this.hhDT = hhDT;
    }

    public String getHh01() {
        return hh01;
    }

    public void setHh01(String hh01) {
        this.hh01 = hh01;
    }

    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
    }

    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
    }

    public String getHhadd() {
        return hhadd;
    }

    public void setHhadd(String hhadd) {
        this.hhadd = hhadd;
    }

    public String getHh04() {
        return hh04;
    }

    public void setHh04(String hh04) {
        this.hh04 = hh04;
    }

    public String getHh04x() {
        return hh04x;
    }

    public void setHh04x(String hh04x) {
        this.hh04x = hh04x;
    }

    public String getHh05() {
        return hh05;
    }

    public void setHh05(String hh05) {
        this.hh05 = hh05;
    }

    public String getHh06() {
        return hh06;
    }

    public void setHh06(String hh06) {
        this.hh06 = hh06;
    }

    public String getHh07() {
        return hh07;
    }

    public void setHh07(String hh07) {
        this.hh07 = hh07;
    }

    public String getHh07n() {
        return hh07n;
    }

    public void setHh07n(String hh07n) {
        this.hh07n = hh07n;
    }

    public String getHh08() {
        return hh08;
    }

    public void setHh08(String hh08) {
        this.hh08 = hh08;
    }

    public String getHh09() {
        return hh09;
    }

    public void setHh09(String hh09) {
        this.hh09 = hh09;
    }

    public String getHh09a() {
        return hh09a;
    }

    public void setHh09a(String hh09a) {
        this.hh09a = hh09a;
    }

    public String getHh09b() {
        return hh09b;
    }

    public void setHh09b(String hh09b) {
        this.hh09b = hh09b;
    }

    public String getHh10() {
        return hh10;
    }

    public void setHh10(String hh10) {
        this.hh10 = hh10;
    }

    public String getHh11() {
        return hh11;
    }

    public void setHh11(String hh11) {
        this.hh11 = hh11;
    }

    public String getHh12() {
        return hh12;
    }

    public void setHh12(String hh12) {
        this.hh12 = hh12;
    }

    public String getHh13() {
        return hh13;
    }

    public void setHh13(String hh13) {
        this.hh13 = hh13;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHhChildNm() {
        return hhChildNm;
    }

    public void setHhChildNm(String hhChildNm) {
        this.hhChildNm = hhChildNm;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getGPSLat() {
        return GPSLat;
    }

    public void setGPSLat(String GPSLat) {
        this.GPSLat = GPSLat;
    }

    public String getGPSLng() {
        return GPSLng;
    }

    public void setGPSLng(String GPSLng) {
        this.GPSLng = GPSLng;
    }

    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
    }

    public String getGPSAcc() {
        return GPSAcc;
    }

    public void setGPSAcc(String GPSAcc) {
        this.GPSAcc = GPSAcc;
    }

    public String getRound() {
        return Round;
    }

    public void setRound(String round) {
        Round = round;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        ListingContract.userName = userName;
    }

    public String getfStatus() {
        return fStatus;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(ListingEntry._ID, this.ID);
        json.put(ListingEntry.COLUMN_NAME_UID, this.UID);
        json.put(ListingEntry.COLUMN_NAME_HHDATETIME, this.hhDT);
        json.put(ListingEntry.COLUMN_NAME_HH01, this.hh01);
        json.put(ListingEntry.COLUMN_NAME_HH02, this.hh02);
        json.put(ListingEntry.COLUMN_NAME_HH03, this.hh03);
        json.put(ListingEntry.COLUMN_NAME_HHADD, this.hhadd);
        json.put(ListingEntry.COLUMN_NAME_HH04, this.hh04);
        json.put(ListingEntry.COLUMN_NAME_HH04x, this.hh04x);
        json.put(ListingEntry.COLUMN_NAME_HH05, this.hh05);
        json.put(ListingEntry.COLUMN_NAME_HH06, this.hh06);
        json.put(ListingEntry.COLUMN_NAME_HH07, this.hh07);
        json.put(ListingEntry.COLUMN_NAME_HH07n, this.hh07n);
        json.put(ListingEntry.COLUMN_NAME_HH08, this.hh08);
        json.put(ListingEntry.COLUMN_NAME_HH09, this.hh09);
        json.put(ListingEntry.COLUMN_NAME_HH09A, this.hh09a);
        json.put(ListingEntry.COLUMN_NAME_HH09B, this.hh09b);
        json.put(ListingEntry.COLUMN_NAME_HH10, this.hh10);
        json.put(ListingEntry.COLUMN_NAME_HH11, this.hh11);
        json.put(ListingEntry.COLUMN_NAME_HH12, this.hh12);
        json.put(ListingEntry.COLUMN_NAME_HH13, this.hh13);

        json.put(ListingEntry.COLUMN_NAME_CHILD_NAME, this.hhChildNm);
        json.put(ListingEntry.COLUMN_NAME_DEVICEID, this.DeviceID);
        json.put(ListingEntry.COLUMN_NAME_GPSLat, this.GPSLat);
        json.put(ListingEntry.COLUMN_NAME_GPSLng, this.GPSLng);
        json.put(ListingEntry.COLUMN_NAME_GPSTime, this.GPSTime);
        json.put(ListingEntry.COLUMN_NAME_GPSAccuracy, this.GPSAcc);
        json.put(ListingEntry.COLUMN_NAME_ROUND, this.Round);
        json.put(ListingEntry.COLUMN_NAME_USERNAME, userName);
        json.put(ListingEntry.COLUMN_NAME_FORMSTATUS, fStatus);

        return json;
    }


    public static abstract class ListingEntry implements BaseColumns {

        public static final String TABLE_NAME = "listings";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_HHDATETIME = "hhdt";
        public static final String COLUMN_NAME_HH01 = "hh01";
        public static final String COLUMN_NAME_HH02 = "hh02";
        public static final String COLUMN_NAME_HH03 = "hh03";
        public static final String COLUMN_NAME_HHADD = "hhadd";
        public static final String COLUMN_NAME_HH04 = "hh04";
        public static final String COLUMN_NAME_HH04x = "hh04x";
        public static final String COLUMN_NAME_HH05 = "hh05";
        public static final String COLUMN_NAME_HH06 = "hh06";
        public static final String COLUMN_NAME_HH07 = "hh07";
        public static final String COLUMN_NAME_HH07n = "hh07n";
        public static final String COLUMN_NAME_HH08 = "hh08";
        public static final String COLUMN_NAME_HH09 = "hh09";
        public static final String COLUMN_NAME_HH09A = "hh09a";
        public static final String COLUMN_NAME_HH09B = "hh09b";
        public static final String COLUMN_NAME_HH10 = "hh10";
        public static final String COLUMN_NAME_HH11 = "hh11";
        public static final String COLUMN_NAME_HH12 = "hh12";
        public static final String COLUMN_NAME_HH13 = "hh13";

        public static final String COLUMN_NAME_CHILD_NAME = "child_name";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
        public static final String COLUMN_NAME_GPSLat = "gpslat";
        public static final String COLUMN_NAME_GPSLng = "gpslng";
        public static final String COLUMN_NAME_GPSTime = "gpstime";
        public static final String COLUMN_NAME_GPSAccuracy = "gpsacc";
        public static final String COLUMN_NAME_ROUND = "round";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FORMSTATUS = "status";

    }
}