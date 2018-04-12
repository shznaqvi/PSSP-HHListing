package edu.aku.hassannaqvi.po_hhlisting.contract;

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
    public String hh07n; //user name
    public String hh08;
    public String hh09;
    public String hh09a;
    public String ch01;
    public String ch01m;
    public String ch01f;
    public String ch02;
    public String ch02m;
    public String ch02f;
    public String ch03;
    public String ch03m;
    public String ch03f;
    public String ch04;


    /*public String hh09b;
    public String hh10;
    public String hh11;
    public String hh12;
    public String hh13;*/
    public String hh04Village;
    public String DeviceID;
    public String GPSLat;
    public String GPSLng;
    public String GPSTime;
    public String GPSAcc;
    public String Round = "1";
    public String fStatus = "3";

    private String synced = "";
    private String synced_date = "";

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

    public String getCh01() {
        return ch01;
    }

    public void setCh01(String ch01) {
        this.ch01 = ch01;
    }

    public String getCh01m() {
        return ch01m;
    }

    public void setCh01m(String ch01m) {
        this.ch01m = ch01m;
    }

    public String getCh01f() {
        return ch01f;
    }

    public void setCh01f(String ch01f) {
        this.ch01f = ch01f;
    }


    public String getCh02() {
        return ch02;
    }

    public void setCh02(String ch02) {
        this.ch02 = ch02;
    }

    public String getCh02m() {
        return ch02m;
    }

    public void setCh02m(String ch02m) {
        this.ch02m = ch02m;
    }

    public String getCh02f() {
        return ch02f;
    }

    public void setCh02f(String ch02f) {
        this.ch02f = ch02f;
    }


    public String getCh03() {
        return ch03;
    }

    public void setCh03(String ch03) {
        this.ch03 = ch03;
    }

    public String getCh03m() {
        return ch03m;
    }

    public void setCh03m(String ch03m) {
        this.ch03m = ch03m;
    }

    public String getCh03f() {
        return ch03f;
    }

    public void setCh03f(String ch03f) {
        this.ch03f = ch03f;
    }


    public String getCh04() {
        return ch04;
    }

    public void setCh04(String ch04) {
        this.ch04 = ch04;
    }



    /*public String getHh11() {
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
    }*/

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHh04Village() {
        return hh04Village;
    }

    public void setHh04Village(String hh04Village) {
        this.hh04Village = hh04Village;
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

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
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
        json.put(ListingEntry.COLUMN_NAME_CH01, this.ch01);
        json.put(ListingEntry.COLUMN_NAME_CH01M, this.ch01m);
        json.put(ListingEntry.COLUMN_NAME_CH01F, this.ch01f);
        json.put(ListingEntry.COLUMN_NAME_CH02, this.ch02);
        json.put(ListingEntry.COLUMN_NAME_CH02M, this.ch02m);
        json.put(ListingEntry.COLUMN_NAME_CH02F, this.ch02f);
        json.put(ListingEntry.COLUMN_NAME_CH03, this.ch03);
        json.put(ListingEntry.COLUMN_NAME_CH03M, this.ch03m);
        json.put(ListingEntry.COLUMN_NAME_CH03F, this.ch03f);
        json.put(ListingEntry.COLUMN_NAME_CH04, this.ch04);


        /*json.put(ListingEntry.COLUMN_NAME_HH09B, this.hh09b);
        json.put(ListingEntry.COLUMN_NAME_HH10, this.hh10);
        json.put(ListingEntry.COLUMN_NAME_HH11, this.hh11);
        json.put(ListingEntry.COLUMN_NAME_HH12, this.hh12);
        json.put(ListingEntry.COLUMN_NAME_HH13, this.hh13);*/

        json.put(ListingEntry.COLUMN_NAME_HH04_VILLAGE, this.hh04Village);
        json.put(ListingEntry.COLUMN_NAME_DEVICEID, this.DeviceID);
        json.put(ListingEntry.COLUMN_NAME_GPSLat, this.GPSLat);
        json.put(ListingEntry.COLUMN_NAME_GPSLng, this.GPSLng);
        json.put(ListingEntry.COLUMN_NAME_GPSTime, this.GPSTime);
        json.put(ListingEntry.COLUMN_NAME_GPSAccuracy, this.GPSAcc);
        json.put(ListingEntry.COLUMN_NAME_ROUND, this.Round);
        json.put(ListingEntry.COLUMN_NAME_FORMSTATUS, fStatus);

/*        json.put(ListingEntry.COLUMN_SYNCED, synced);
        json.put(ListingEntry.COLUMN_SYNCED_DATE, synced_date);*/

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
        public static final String COLUMN_NAME_CH01 = "ch01";
        public static final String COLUMN_NAME_CH01M = "ch01m";
        public static final String COLUMN_NAME_CH01F = "ch01f";
        public static final String COLUMN_NAME_CH02 = "ch02";
        public static final String COLUMN_NAME_CH02M = "ch02m";
        public static final String COLUMN_NAME_CH02F = "ch02f";
        public static final String COLUMN_NAME_CH03 = "ch03";
        public static final String COLUMN_NAME_CH03M = "ch03m";
        public static final String COLUMN_NAME_CH03F = "ch03f";
        public static final String COLUMN_NAME_CH04 = "ch04";

        /*public static final String COLUMN_NAME_HH09A = "hh09a";
        public static final String COLUMN_NAME_HH09B = "hh09b";
        public static final String COLUMN_NAME_HH10 = "hh10";
        public static final String COLUMN_NAME_HH11 = "hh11";
        public static final String COLUMN_NAME_HH12 = "hh12";
        public static final String COLUMN_NAME_HH13 = "hh13";*/

        public static final String COLUMN_NAME_HH04_VILLAGE = "hh04Village";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
        public static final String COLUMN_NAME_GPSLat = "gpslat";
        public static final String COLUMN_NAME_GPSLng = "gpslng";
        public static final String COLUMN_NAME_GPSTime = "gpstime";
        public static final String COLUMN_NAME_GPSAccuracy = "gpsacc";
        public static final String COLUMN_NAME_ROUND = "round";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FORMSTATUS = "status";

        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";

        public static String _URL = "listings.php";

    }
}