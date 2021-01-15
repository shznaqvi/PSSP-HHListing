package edu.aku.hassannaqvi.naunehal_listing.models;

import android.provider.BaseColumns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.naunehal_listing.BR;

/**
 * Created by hassan.naqvi on 10/18/2016.
 */
public class Listings extends BaseObservable {

    public String ID;
    public String UID;
    public String hhDT;
    public String hl01;
    public String hl02;
    public String hl03;
    public String hl04;
    public String hl04x;
    public String hl05;
    public String hl06;
    public String hl07;
    public String hl0796x;
    public String hl08;
    public String hl09;
    public String hl10;
    public String hl11;
    public String hl12m;
    public String hl12d;
    public String hhChildNm;
    public String DeviceID;
    public String GPSLat;
    public String GPSLng;
    public String GPSTime;
    public String GPSAcc;
    public String Round = "1";

    public Listings() {
    }

    public Listings(String ID) {
        this.ID = ID;
    }

    public Listings(String hl01, String hl02, String hl03, String hl04, String hl04x, String hl05, String hl06, String hl07, String DeviceID, String GPSLat, String GPSLng, String GPSTime, String GPSAcc) {
        this.hl01 = hl01;
        this.hl02 = hl02;
        this.hl03 = hl03;
        this.hl04 = hl04;
        this.hl04x = hl04x;
        this.hl05 = hl05;
        this.hl06 = hl06;
        this.hl07 = hl07;
        this.DeviceID = DeviceID;
        this.GPSLat = GPSLat;
        this.GPSLng = GPSLng;
        this.GPSTime = GPSTime;
        this.GPSAcc = GPSAcc;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(TableListings._ID, this.ID);
        json.put(TableListings.COLUMN_NAME_UID, this.UID);
        json.put(TableListings.COLUMN_NAME_HHDATETIME, this.hhDT);
        json.put(TableListings.COLUMN_NAME_hl01, this.hl01);
        json.put(TableListings.COLUMN_NAME_hl02, this.hl02);
        json.put(TableListings.COLUMN_NAME_hl03, this.hl03);
        json.put(TableListings.COLUMN_NAME_hl04, this.hl04);
        json.put(TableListings.COLUMN_NAME_hl04x, this.hl04x);
        json.put(TableListings.COLUMN_NAME_hl05, this.hl05);
        json.put(TableListings.COLUMN_NAME_hl06, this.hl06);
        json.put(TableListings.COLUMN_NAME_hl07, this.hl07);
        json.put(TableListings.COLUMN_NAME_hl07n, this.hl0796x);
        json.put(TableListings.COLUMN_NAME_hl08, this.hl08);
        json.put(TableListings.COLUMN_NAME_hl09, this.hl09);
        json.put(TableListings.COLUMN_NAME_hl10, this.hl10);
        json.put(TableListings.COLUMN_NAME_hl11, this.hl11);
        json.put(TableListings.COLUMN_NAME_hl12m, this.hl12m);
        json.put(TableListings.COLUMN_NAME_hl12d, this.hl12d);
        json.put(TableListings.COLUMN_NAME_CHILD_NAME, this.hhChildNm);
        json.put(TableListings.COLUMN_NAME_DEVICEID, this.DeviceID);
        json.put(TableListings.COLUMN_NAME_GPSLat, this.GPSLat);
        json.put(TableListings.COLUMN_NAME_GPSLng, this.GPSLng);
        json.put(TableListings.COLUMN_NAME_GPSTime, this.GPSTime);
        json.put(TableListings.COLUMN_NAME_GPSAccuracy, this.GPSAcc);
        json.put(TableListings.COLUMN_NAME_ROUND, this.Round);

        return json;
    }

    @Bindable
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Bindable
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    @Bindable
    public String getHhDT() {
        return hhDT;
    }

    public void setHhDT(String hhDT) {
        this.hhDT = hhDT;
        notifyPropertyChanged(BR.hhDT);
    }

    @Bindable
    public String getHl01() {
        return hl01;
    }

    public void setHl01(String hl01) {
        this.hl01 = hl01;
        notifyPropertyChanged(BR.hl01);
    }

    @Bindable
    public String getHl02() {
        return hl02;
    }

    public void setHl02(String hl02) {
        this.hl02 = hl02;
        notifyPropertyChanged(BR.hl02);
    }

    @Bindable
    public String getHl03() {
        return hl03;
    }

    public void setHl03(String hl03) {
        this.hl03 = hl03;
        notifyPropertyChanged(BR.hl03);
    }

    @Bindable
    public String getHl04() {
        return hl04;
    }

    public void setHl04(String hl04) {
        this.hl04 = hl04;
        notifyPropertyChanged(BR.hl04);
    }

    @Bindable
    public String getHl04x() {
        return hl04x;
    }

    public void setHl04x(String hl04x) {
        this.hl04x = hl04x;
        notifyPropertyChanged(BR.hl04x);
    }

    @Bindable
    public String getHl05() {
        return hl05;
    }

    public void setHl05(String hl05) {
        this.hl05 = hl05;
        notifyPropertyChanged(BR.hl05);
    }

    @Bindable
    public String getHl06() {
        return hl06;
    }

    public void setHl06(String hl06) {
        this.hl06 = hl06;
        notifyPropertyChanged(BR.hl06);
    }

    @Bindable
    public String getHl07() {
        return hl07;
    }

    public void setHl07(String hl07) {
        this.hl07 = hl07;
        notifyPropertyChanged(BR.hl07);
    }

    @Bindable
    public String getHl0796x() {
        return hl0796x;
    }

    public void setHl0796x(String hl0796x) {
        this.hl0796x = hl0796x;
        notifyPropertyChanged(BR.hl0796x);
    }

    @Bindable
    public String getHl08() {
        return hl08;
    }

    public void setHl08(String hl08) {
        this.hl08 = hl08;
        notifyPropertyChanged(BR.hl08);
    }

    @Bindable
    public String getHl09() {
        return hl09;
    }

    public void setHl09(String hl09) {
        this.hl09 = hl09;
        notifyPropertyChanged(BR.hl09);
    }

    @Bindable
    public String getHl10() {
        return hl10;
    }

    public void setHl10(String hl10) {
        this.hl10 = hl10;
        notifyPropertyChanged(BR.hl10);
    }

    @Bindable
    public String getHl11() {
        return hl11;
    }

    public void setHl11(String hl11) {
        this.hl11 = hl11;
        notifyPropertyChanged(BR.hl11);
    }

    @Bindable
    public String getHl12m() {
        return hl12m;
    }

    public void setHl12m(String hl12m) {
        this.hl12m = hl12m;
        notifyPropertyChanged(BR.hl12m);
    }

    @Bindable
    public String getHl12d() {
        return hl12d;
    }

    public void setHl12d(String hl12d) {
        this.hl12d = hl12d;
        notifyPropertyChanged(BR.hl12d);
    }

    @Bindable
    public String getHhChildNm() {
        return hhChildNm;
    }

    public void setHhChildNm(String hhChildNm) {
        this.hhChildNm = hhChildNm;
        notifyPropertyChanged(BR.hhChildNm);
    }

    @Bindable
    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    @Bindable
    public String getGPSLat() {
        return GPSLat;
    }

    public void setGPSLat(String GPSLat) {
        this.GPSLat = GPSLat;
    }

    @Bindable
    public String getGPSLng() {
        return GPSLng;
    }

    public void setGPSLng(String GPSLng) {
        this.GPSLng = GPSLng;
    }

    @Bindable
    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
    }

    @Bindable
    public String getGPSAcc() {
        return GPSAcc;
    }

    public void setGPSAcc(String GPSAcc) {
        this.GPSAcc = GPSAcc;
    }

    @Bindable
    public String getRound() {
        return Round;
    }

    public void setRound(String Round) {
        this.Round = Round;
    }

    public static abstract class TableListings implements BaseColumns {

        public static final String TABLE_NAME = "listings";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_HHDATETIME = "hhdt";
        public static final String COLUMN_NAME_hl01 = "hl01";
        public static final String COLUMN_NAME_hl02 = "hl02";
        public static final String COLUMN_NAME_hl03 = "hl03";
        public static final String COLUMN_NAME_hl04 = "hl04";
        public static final String COLUMN_NAME_hl04x = "hl04x";
        public static final String COLUMN_NAME_hl05 = "hl05";
        public static final String COLUMN_NAME_hl06 = "hl06";
        public static final String COLUMN_NAME_hl07 = "hl07";
        public static final String COLUMN_NAME_hl07n = "hl07n";
        public static final String COLUMN_NAME_hl08 = "hl08";
        public static final String COLUMN_NAME_hl09 = "hl09";
        public static final String COLUMN_NAME_hl10 = "hl10";
        public static final String COLUMN_NAME_hl11 = "hl11";
        public static final String COLUMN_NAME_hl12m = "hl12m";
        public static final String COLUMN_NAME_hl12d = "hl12d";
        public static final String COLUMN_NAME_CHILD_NAME = "child_name";
        public static final String COLUMN_NAME_DEVICEID = "deviceid";
        public static final String COLUMN_NAME_GPSLat = "gpslat";
        public static final String COLUMN_NAME_GPSLng = "gpslng";
        public static final String COLUMN_NAME_GPSTime = "gpstime";
        public static final String COLUMN_NAME_GPSAccuracy = "gpsacc";
        public static final String COLUMN_NAME_ROUND = "round";
    }
}