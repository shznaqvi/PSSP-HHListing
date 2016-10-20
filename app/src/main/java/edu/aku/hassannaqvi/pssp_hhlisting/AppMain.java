package edu.aku.hassannaqvi.pssp_hhlisting;

import android.app.Application;
import android.util.Log;

/**
 * Created by hassan.naqvi on 10/15/2016.
 */

public class AppMain extends Application {

    public static ListingContract lc;
    public static String hh01txt = "0000";
    public static String hh02txt;
    public static int hh03txt;
    public static String hh07txt;
    public static int fCount = 0;
    public static int fTotal = 0;
    public static int cCount = 0;
    public static int cTotal = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Creating...");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JameelNooriNastaleeq.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

    }
}
