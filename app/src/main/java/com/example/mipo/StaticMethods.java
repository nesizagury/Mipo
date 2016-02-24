package com.example.mipo;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.util.List;

public class StaticMethods {
    //in millis
    private static final int GPS_UPDATE_TIME_INTERVAL = 10000;

    public static LocationManager locationManager;
    public static LocationListener locationListener;

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt (context.getContentResolver (), Settings.Secure.LOCATION_MODE);
            } catch (SettingNotFoundException e) {
                e.printStackTrace ();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString (context.getContentResolver (), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty (locationProviders);
        }
    }

    public interface GpsICallback {
        void gpsCallback();
    }

    public static void updateDeviceLocationGPS(Context context, GpsICallback iCallback) {
        boolean gps_enabled = false;
        boolean network_enabled = false;
        boolean passive_enabled = false;

        locationManager = (LocationManager) context.getSystemService (Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener (iCallback, context);
        try {
            gps_enabled = locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        try {
            network_enabled = locationManager.isProviderEnabled (LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        try {
            passive_enabled = locationManager.isProviderEnabled (LocationManager.PASSIVE_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission (context, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (context, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //do none
            } else {
                locationManager.requestLocationUpdates (LocationManager.GPS_PROVIDER, GPS_UPDATE_TIME_INTERVAL, 0, locationListener);
            }
        }
        if (network_enabled) {
            locationManager.requestLocationUpdates (LocationManager.NETWORK_PROVIDER, GPS_UPDATE_TIME_INTERVAL, 0, locationListener);
        }
        if (passive_enabled) {
            locationManager.requestLocationUpdates (LocationManager.PASSIVE_PROVIDER, GPS_UPDATE_TIME_INTERVAL, 0, locationListener);
        }
    }

    private static class MyLocationListener implements LocationListener {
        GpsICallback ic;
        Context context;

        MyLocationListener(GpsICallback iCallback, Context context) {
            ic = iCallback;
            this.context = context;
        }

        @Override
        public void onLocationChanged(final Location location) {
            if (location != null) {
                GlobalVariables.MY_LOCATION = location;
                if (!isGuestUser ()) {
                    ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
                    query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
                    query.findInBackground (new FindCallback<Profile> () {
                        public void done(List<Profile> list, ParseException e) {
                            if (e == null) {
                                Profile profile = list.get (0);
                                ParseGeoPoint parseGeoPoint = new ParseGeoPoint (location.getLatitude (),
                                                                                        location.getLongitude ());
                                profile.setLocation (parseGeoPoint);
                                profile.saveInBackground ();
                            } else {
                                e.printStackTrace ();
                            }
                        }
                    });
                }
                ic.gpsCallback ();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public static boolean isCurrentUser(UserDetails userDetails) {
        if (GlobalVariables.CUSTOMER_PHONE_NUM != null &&
                    !GlobalVariables.CUSTOMER_PHONE_NUM.isEmpty ()) {
            if (userDetails.getUserPhoneNum ().equals (GlobalVariables.CUSTOMER_PHONE_NUM)) {
                return true;
            }
        } else {
            if (userDetails.getUserPhoneNum ().equals ("GUEST")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGuestUser() {
        return GlobalVariables.CUSTOMER_PHONE_NUM == null || GlobalVariables.CUSTOMER_PHONE_NUM.isEmpty ();
    }

    public static UserDetails getUserFromPhoneNum(String phone){
        for(UserDetails userDetails : GlobalVariables.userDataList){
            if(userDetails.getUserPhoneNum ().equals (phone)){
                return userDetails;
            }
        }
        return null;
    }
}
