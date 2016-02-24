package com.example.mipo;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipo.StaticMethods.GpsICallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class MainPageActivity extends Activity implements AdapterView.OnItemClickListener, GpsICallback {
    GridView grid;
    public static List<UserDetails> filteredUsersList = new ArrayList<UserDetails> ();
    public static GridAdaptor gridAdapter;
    private TextView turnOnGPS;
    static Context context;
    static MainPageActivity mainPageActivity;
    private Handler handler = new Handler ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_main_page);
        grid = (GridView) findViewById (R.id.gridView1);
        turnOnGPS = (TextView) findViewById (R.id.turnOnGps);
        context = this;
        mainPageActivity = this;
        if (!StaticMethods.isLocationEnabled (this)) {
            turnOnGPS.setVisibility (View.VISIBLE);
        } else{
            turnOnGPS.setVisibility (View.GONE);
        }
        if (GlobalVariables.userDataList.size () == 0) {
            downloadProfilesDataInBackGround ();
        } else {
            StaticMethods.updateDeviceLocationGPS (context, mainPageActivity);
        }

        gridAdapter = new GridAdaptor (this, filteredUsersList, false);
        grid.setAdapter (gridAdapter);
        grid.setOnItemClickListener (this);
        handler.postDelayed (runnable, 0);
    }

    public static void downloadProfilesDataInBackGround() {
        GlobalVariables.userDataList.clear ();
        ParseQuery<Profile> query = new ParseQuery ("Profile");
        query.orderByDescending ("createdAt");
        query.findInBackground (new FindCallback<Profile> () {
            public void done(List<Profile> profilesList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < profilesList.size (); i++) {
                        Profile profile = profilesList.get (i);
                        Date currentDate = new Date();
                        long diff = currentDate.getTime() - profile.getLastSeen().getTime ();
                        long diffMinutes = diff / (60 * 1000);
                        GlobalVariables.userDataList.add (new UserDetails (
                                                                                  profile.getNumber (),
                                                                                  profile.getName (),
                                                                                  profile.getAge (),
                                                                                  profile.getLastSeen (),
                                                                                  profile.getStatus (),
                                                                                  profile.getHeight (),
                                                                                  profile.getWeight (),
                                                                                  profile.getNation (),
                                                                                  profile.getBody_type (),
                                                                                  profile.getRelationship_status (),
                                                                                  profile.getLooking_for (),
                                                                                  profile.getAbout (),
                                                                                  profile.getPic ().getUrl (),
                                                                                  profile.getLocation (),
                                                                                  i,
                                                                                  diffMinutes < 10

                        ));
                        if (GlobalVariables.userDataList.get (i).getUserPhoneNum ().equals ("GUEST") &&
                                    (GlobalVariables.CUSTOMER_PHONE_NUM == null || GlobalVariables.CUSTOMER_PHONE_NUM.isEmpty ())) {
                            GlobalVariables.currentUser = GlobalVariables.userDataList.get (i);
                        }
                        if (GlobalVariables.CUSTOMER_PHONE_NUM != null &&
                                    !GlobalVariables.CUSTOMER_PHONE_NUM.isEmpty () &&
                                    GlobalVariables.userDataList.get (i).getUserPhoneNum ().equals (GlobalVariables.CUSTOMER_PHONE_NUM)) {
                            GlobalVariables.currentUser = GlobalVariables.userDataList.get (i);
                        }
                    }
                    filteredUsersList.clear ();
                    filteredUsersList.addAll (GlobalVariables.userDataList);
                    StaticMethods.updateDeviceLocationGPS (context, mainPageActivity);
                } else {
                    e.printStackTrace ();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle b = new Bundle ();
        Intent intent = new Intent (this, UserPage.class);
        UserDetails user = filteredUsersList.get (position);
        intent.putExtra ("userName", user.name);
        intent.putExtra ("userCurrent", StaticMethods.isCurrentUser (user));
        b.putString ("userID", user.getUserPhoneNum ());
        b.putInt ("index", user.getIndexInAllDataList ());
        Date currentDate = new Date();
        long diff = currentDate.getTime() - user.getLastSeen().getTime ();
        long diffMinutes = diff / (60 * 1000);
        b.putInt ("online", (int)diffMinutes);
        intent.putExtras (b);
        startActivity (intent);
    }

    public void goToMessages(View view) {
        if (!StaticMethods.isGuestUser ()) {
            Intent intent = new Intent (this, MessagesRoom.class);
            startActivity (intent);
        } else {
            Toast.makeText (getApplicationContext (), "to continue please create a profile", Toast.LENGTH_SHORT).show ();
        }
    }

    public void goToFavorites(View view) {
        Intent i = new Intent (this, FavoritesPage.class);
        startActivity (i);
    }

    public void goToFilter(View v) {
        Intent intent = new Intent (this, FilterActivity.class);
        startActivity (intent);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        handler.postDelayed (runnable, 0);
        StaticMethods.updateDeviceLocationGPS (context, mainPageActivity);
    }

    @Override
    protected void onPause() {
        super.onPause ();
        handler.removeCallbacks (runnable);
        if (ActivityCompat.checkSelfPermission (this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            StaticMethods.locationManager.removeUpdates (StaticMethods.locationListener);
        }
    }

    public void sortFilteredProfilesListByDistAndUpdateAllDist() {
        for (int i = 0; i < GlobalVariables.userDataList.size (); i++) {
            UserDetails user = GlobalVariables.userDataList.get (i);
            if (user.equals (GlobalVariables.currentUser)) {
                user.setDist (-1);
            } else {
                ParseGeoPoint parseGeoPoint =
                        user.getUserLocation ();
                Location userLocation = new Location ("GPS");
                userLocation.setLatitude (parseGeoPoint.getLatitude ());
                userLocation.setLongitude (parseGeoPoint.getLongitude ());
                double distance = (double) GlobalVariables.MY_LOCATION.distanceTo (userLocation) / 1000;
                DecimalFormat df = new DecimalFormat ("#.##");
                String dx = df.format (distance);
                distance = Double.valueOf (dx);
                user.setDist (distance);
            }
        }
        Collections.sort (filteredUsersList, new Comparator<UserDetails> () {
            @Override
            public int compare(UserDetails a, UserDetails b) {
                if (a.dist < b.dist) return -1;
                if (a.dist >= b.dist) return 1;
                return 0;
            }
        });
    }

    public void openMenu(View v) {
        Intent i = new Intent (this, MenuActivity.class);
        startActivity (i);
    }

    @Override
    public void gpsCallback() {
        sortFilteredProfilesListByDistAndUpdateAllDist ();
        gridAdapter.notifyDataSetChanged ();
        turnOnGPS.setVisibility (View.GONE);
    }

    private Runnable runnable = new Runnable () {
        @Override
        public void run() {
            if (!StaticMethods.isGuestUser ()) {
                ParseQuery<Profile> query = ParseQuery.getQuery ("Profile");
                query.whereEqualTo ("number", GlobalVariables.CUSTOMER_PHONE_NUM);
                query.findInBackground (new FindCallback<Profile> () {
                    public void done(List<Profile> list, ParseException e) {
                        if (e == null) {
                            Profile profile = list.get (0);
                            Date currentDate = new Date ();
                            profile.setLastSeen (currentDate);
                            profile.saveInBackground ();
                        } else {
                            e.printStackTrace ();
                        }
                    }
                });
            }
            handler.postDelayed (this, 30000);
        }
    };
}