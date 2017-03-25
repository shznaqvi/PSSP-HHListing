package edu.aku.hassannaqvi.cbt_hhlisting;

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

/**
 * Created by hassan.naqvi on 10/15/2016.
 */

public class AppMain extends Application {

    // GPS Related Field Variables

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public static String _IP = "http://10.1.42.139:3000"; // Testing Server
    //public static String _IP = "http://43.245.131.159:3000"; //Production server
    public static String PROJECT_URI = _IP + "/cbt/"; //Production server
    public static String TAG = "AppMain";

    private static final long MILLIS_IN_SECOND = 1000;
    private static final long SECONDS_IN_MINUTE = 60;
    private static final long MINUTES_IN_HOUR = 60;
    private static final long HOURS_IN_DAY = 24;
    public static final long MILLISECONDS_IN_DAY = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;
    private static final long DAYS_IN_YEAR = 365;
    public static final long MILLISECONDS_IN_YEAR = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR;


    public static String userName;
    public static ListingContract lc;
    public static PWContract pw;
    public static ChildContract cc;
    public static ClusterContract clc;
    public static String hh01txt = "0000";
    public static String hh02txt;
    public static int hh03txt = 0;
    public static String hh07txt;

    public static String household;

    public static int fCount = 0;
    public static int fTotal = 0;
    public static int cCount = 0;
    public static int hh07 = 0;
    public static int cTotal = 0;
    public static int pwCount = 0;
    public static int pwTotal = 0;
    public static SharedPreferences sharedPref;
    protected static LocationManager locationManager;

    public static String tehsilCode;
    public static Boolean UCsCodeFlag = true;
    public static int UCsCode;
    public static Boolean VillageCodeFlag = true;
    public static int VillageCode;

    public static Boolean ClusterActivityFlag = false;

    public static void updatePSU(String psuCode, String structureNo) {

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(psuCode, structureNo);

        editor.apply();
        Log.d(TAG, "updatePSU: " + psuCode + " " + structureNo);

    }

    public static Boolean PSUExist(String psuCode) {
        Log.d(TAG, "PSUExist: " + psuCode);
        AppMain.hh03txt = Integer.valueOf(sharedPref.getString(psuCode, "0"));
        Log.d(TAG, "PSUExist (Test): " + sharedPref.getString(psuCode, "0"));

        if (AppMain.hh03txt == 0) {
            Log.d(TAG, "PSUExist (False): " + AppMain.hh03txt);

            return false;
        } else {
            Log.d(TAG, "PSUExist (True): " + AppMain.hh03txt);

            return true;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "Creating...");
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JameelNooriNastaleeq.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        // Declare and Initialize GPS collection module
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new GPSLocationListener() // Implement this class from code
        );

        sharedPref = getSharedPreferences("PSUCodes", Context.MODE_PRIVATE);

    }

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {

            SharedPreferences sharedPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            String dt = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(sharedPref.getString("Time", "0"))).toString();

            Location bestLocation = new Location("storedProvider");
            bestLocation.setAccuracy(Float.parseFloat(sharedPref.getString("Accuracy", "0")));
            bestLocation.setTime(Long.parseLong(sharedPref.getString(dt, "0")));
            bestLocation.setLatitude(Float.parseFloat(sharedPref.getString("Latitude", "0")));
            bestLocation.setLongitude(Float.parseFloat(sharedPref.getString("Longitude", "0")));

            if (isBetterLocation(location, bestLocation)) {
                editor.putString("Longitude", String.valueOf(location.getLongitude()));
                editor.putString("Latitude", String.valueOf(location.getLatitude()));
                editor.putString("Accuracy", String.valueOf(location.getAccuracy()));
                editor.putString("Time", String.valueOf(location.getTime()));
//                String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(String.valueOf(location.getTime()))).toString();
//                Toast.makeText(getApplicationContext(),
//                        "GPS Commit! LAT: " + String.valueOf(location.getLongitude()) +
//                                " LNG: " + String.valueOf(location.getLatitude()) +
//                                " Accuracy: " + String.valueOf(location.getAccuracy()) +
//                                " Time: " + date,
//                        Toast.LENGTH_SHORT).show();

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
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
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
//                String message = String.format(
//                        "Current Location \n Longitude: %1$s \n Latitude: %2$s",
//                        location.getLongitude(), location.getLatitude()
//                );
//                Toast.makeText(AppMain.this, message,
//                        Toast.LENGTH_SHORT).show();
                String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(String.valueOf(location.getTime()))).toString();
                Toast.makeText(getApplicationContext(),
                        "GPS Commit! LAT: " + String.valueOf(location.getLongitude()) +
                                " LNG: " + String.valueOf(location.getLatitude()) +
                                " Accuracy: " + String.valueOf(location.getAccuracy()) +
                                " Time: " + date,
                        Toast.LENGTH_SHORT).show();
            }

        }

    }

}
