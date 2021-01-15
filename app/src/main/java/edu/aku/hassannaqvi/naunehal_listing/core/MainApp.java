package edu.aku.hassannaqvi.naunehal_listing.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.aku.hassannaqvi.naunehal_listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.naunehal_listing.models.Listings;

/**
 * Created by hassan.naqvi on 10/15/2016.
 */

public class MainApp extends Application {

    //public static String _IP = "http://192.168.1.10:3000"; // Testing Server
    //public static String _IP = "http://43.245.131.159:8080"; //Production server
    //public static final String _IP = "https://vcoe1.aku.edu";// .LIVE server
    public static final String _IP = "http://f38158";// .TEST server
    //public static final String _IP = "http://43.245.131.159:8080";// .TEST server
    public static final String _HOST_URL = MainApp._IP + "/naunehal/api/";// .TEST server;

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final String _SERVER_URL = "sync.php";
    public static final String _SERVER_GET_URL = "getData.php";
    public static final String _PHOTO_UPLOAD_URL = MainApp._IP + _HOST_URL + "uploads.php";
    public static final String _UPDATE_URL = MainApp._IP + "/naunehal/app/";
    public static String IMEI = "";
    // GPS Related Field Variables
    public static String PROJECT_NAME = "NAUNEHAL_LISTING";
    public static String DeviceURL = "devices.php";


    public static String TAG = "MainApp";
    public static Listings listing;
    public static String hl01txt = "0000";
    public static String hl02txt;
    public static int hl03txt = 0;
    public static String hl07txt;
    public static int fCount = 0;
    public static int fTotal = 0;
    public static int cCount = 0;
    public static int hl07 = 0;
    public static int cTotal = 0;
    public static AppInfo appInfo;
    public static String districtsCode;
    public static String ucCode;
    public static String clusterCode;
    public static int structureNo;
    public static String userName;

    public static SharedPreferences sharedPref;
    public static boolean admin;
    public static List<String> districts = new ArrayList<String>();
    protected static LocationManager locationManager;
    public static List<String> ucs = new ArrayList<String>();
    private DatabaseHelper db;

    public static void updateCluster(String clusterCode, String structureNo) {

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(clusterCode, structureNo);

        editor.apply();
        Log.d(TAG, "updateCluster: " + clusterCode + " " + structureNo);


    }

    public static Boolean ClusterExist(String clusterCode) {
        Log.d(TAG, "ClusterExist: " + clusterCode);
        MainApp.structureNo = Integer.parseInt(sharedPref.getString(clusterCode, "0"));
        Log.d(TAG, "ClusterExist (Test): " + sharedPref.getString(clusterCode, "0"));

        if (MainApp.clusterCode.equals(0)) {
            Log.d(TAG, "ClusterExist (False): " + MainApp.clusterCode);

            return false;
        } else {
            Log.d(TAG, "ClusterExist (True): " + MainApp.clusterCode);

            return true;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Creating...");
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JameelNooriNastaleeq.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


/*
        List<String> Code = new ArrayList<>();
        List<String> Name = new ArrayList<>();


        Code.add("....");
        Name.add("....");

        Collection<DistrictsContract> dc = db.getAllDistricts();
        for (DistrictsContract d : dc) {
            Code.add(d.getDistrictCode());
            Name.add(d.getDistrictCode());
        }

      districts.addAll(db.getAllDistricts());
*/

        // Declare and Initialize GPS collection module
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener() // Implement this class from code
        );

        sharedPref = getSharedPreferences("ClusterCodes", Context.MODE_PRIVATE);

    }

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {


            SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Location bestLocation = new Location("storedProvider");
            bestLocation.setAccuracy(Float.parseFloat(sharedPref.getString("Accuracy", "0")));
            bestLocation.setTime(Long.parseLong(sharedPref.getString("Time", "0")));
            bestLocation.setLatitude(Float.parseFloat(sharedPref.getString("Latitude", "0")));
            bestLocation.setLongitude(Float.parseFloat(sharedPref.getString("Longitude", "0")));

            if (isBetterLocation(location, bestLocation)) {
                editor.putString("Longitude", String.valueOf(location.getLongitude()));
                editor.putString("Latitude", String.valueOf(location.getLatitude()));
                editor.putString("Accuracy", String.valueOf(location.getAccuracy()));
                editor.putString("Time", String.valueOf(location.getTime()));
                String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(String.valueOf(location.getTime()))).toString();
                Toast.makeText(getApplicationContext(),
                        "GPS Commit! LAT: " + location.getLongitude() +
                                " LNG: " + location.getLatitude() +
                                " Accuracy: " + location.getAccuracy() +
                                " Time: " + date,
                        Toast.LENGTH_SHORT).show();

                editor.apply();
            }
        }

        protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
            boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
            boolean isNewer = timeDelta > 0;

            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(),
                    currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
        }

        /**
         * Checks whether two providers are the same
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }


        public void onStatusChanged(String s, int i, Bundle b) {
            showCurrentLocation();
        }

        public void onProviderDisabled(String s) {

        }

        public void onProviderEnabled(String s) {

        }

        protected void showCurrentLocation() {


            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            if (location != null) {
                String message = String.format(
                        "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                        location.getLongitude(), location.getLatitude()
                );
                Toast.makeText(MainApp.this, message,
                        Toast.LENGTH_SHORT).show();
            }

        }


    }

}
