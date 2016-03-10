package com.example.mipo;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mipo.StaticMethods.GpsICallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

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
    boolean firstTimeGotLocation = true;
    TextView gpsAccuracy;
    ImageView gpsAccuracyIcon;
    int numOfAccuracyChanges = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_main_page);
        if (getIntent ().getStringExtra ("fundigo") != null &&
                    getIntent ().getStringExtra ("fundigo").equals ("fun") && !MainActivity.didLogin) {
            GlobalVariables.userPhoneNumFromFundigo = getIntent ().getStringExtra ("index");
            Intent intent = new Intent (MainPageActivity.this, MainActivity.class);
            startActivity (intent);
            getIntent ().putExtra ("fundigo", "none");
            finish ();
        } else if(!MainActivity.didLogin){
            Intent intent = new Intent (MainPageActivity.this, MainActivity.class);
            startActivity (intent);
            finish ();
        }
        if(MainActivity.didLogin) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow ().getDecorView ().setLayoutDirection (View.LAYOUT_DIRECTION_LOCALE);
            }
            grid = (GridView) findViewById (R.id.gridView1);
            turnOnGPS = (TextView) findViewById (R.id.turnOnGps);
            gpsAccuracy = (TextView) findViewById (R.id.distanceAcuuraty);
            gpsAccuracyIcon = (ImageView) findViewById (R.id.imageView3);
            gpsAccuracy.setVisibility (View.INVISIBLE);
            gpsAccuracyIcon.setVisibility (View.INVISIBLE);
            context = this;
            mainPageActivity = this;
            if (!StaticMethods.isLocationEnabled (this)) {
                turnOnGPS.setVisibility (View.VISIBLE);
            } else {
                turnOnGPS.setVisibility (View.GONE);
            }
            if (GlobalVariables.userDataList.size () == 0) {
                downloadProfilesDataInBackGround ();
            } else {
                StaticMethods.updateDeviceLocationGPS (context, mainPageActivity);
                if (GlobalVariables.userPhoneNumFromFundigo != null) {
                    UserDetails userDetails = StaticMethods.getUserFromPhoneNum (GlobalVariables.userPhoneNumFromFundigo);
                    if (userDetails != null) {
                        int position = userDetails.getIndexInAllDataList ();
                        Bundle b = new Bundle ();
                        Intent intent = new Intent (context, UserPage.class);
                        UserDetails user = GlobalVariables.userDataList.get (position);
                        intent.putExtra ("userName", user.name);
                        intent.putExtra ("userCurrent", StaticMethods.isCurrentUser (user));
                        b.putString ("userID", user.getUserPhoneNum ());
                        b.putInt ("index", user.getIndexInAllDataList ());
                        Date currentDate = new Date ();
                        long diff = currentDate.getTime () - user.getLastSeen ().getTime ();
                        long diffMinutes = diff / (60 * 1000);
                        b.putInt ("online", (int) diffMinutes);
                        intent.putExtras (b);
                        context.startActivity (intent);
                        GlobalVariables.userPhoneNumFromFundigo = null;
                    }
                }
            }
            gridAdapter = new GridAdaptor (this, filteredUsersList, false);
            grid.setAdapter (gridAdapter);
            grid.setOnItemClickListener (this);
            handler.postDelayed (runnable, 0);
        }
    }

    public static void downloadProfilesDataInBackGround() {
        ParseQuery<Profile> query = new ParseQuery ("Profile");
        query.whereExists ("lastSeen");
        query.orderByDescending ("createdAt");
        List<Profile> profilesList = null;
        try {
            profilesList = query.find ();
            GlobalVariables.userDataList.clear ();
            for (int i = 0; i < profilesList.size (); i++) {
                Profile profile = profilesList.get (i);
                Date currentDate = new Date ();
                long diff = currentDate.getTime () - profile.getLastSeen ().getTime ();
                long diffMinutes = diff / (60 * 1000);
                GlobalVariables.userDataList.add (new UserDetails (
                                                                          profile.getNumber (),
                                                                          profile.getName (),
                                                                          profile.getAge (),
                                                                          profile.getLastSeen (),
                                                                          profile.getStatus (),
                                                                          profile.getHeight (),
                                                                          profile.getWeight (),
                                                                          profile.getEthnicity (),
                                                                          profile.getBody_type (),
                                                                          profile.getRelationship_status (),
                                                                          profile.getLooking_for (),
                                                                          profile.getAbout (),
                                                                          profile.getPic ().getUrl (),
                                                                          profile.getLocation (),
                                                                          i,
                                                                          diffMinutes < 10,
                                                                          profile.getBlocked ()

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
                SharedPreferences prefs = context.getSharedPreferences (
                                                                               "com.example.mipo", Context.MODE_PRIVATE);
                UserDetails userDetails = GlobalVariables.userDataList.get (i);
                boolean isFav = prefs.getBoolean (userDetails.getUserPhoneNum (),false);
                if (isFav) {
                    userDetails.setFavorite (true);
                } else {
                    userDetails.setFavorite (false);
                }
            }
            if (GlobalVariables.currentUser.getBlocked () != null) {
                List<String> blockedList = null;
                blockedList = GlobalVariables.currentUser.getBlocked ();
                for (int i = 0; i < blockedList.size (); i++) {
                    for (int j = 0; j < GlobalVariables.userDataList.size (); j++) {
                        if (blockedList.get (i).equals (GlobalVariables.userDataList.get (j).getUserPhoneNum ())) {
                            GlobalVariables.userDataList.remove (j);
                            break;
                        }
                    }
                }

                for (int i = 0; i < GlobalVariables.userDataList.size (); i++)
                    GlobalVariables.userDataList.get (i).setIndexInAllDataList (i);
            }
            if (GlobalVariables.MY_LOCATION != null) {
                filteredUsersList.clear ();
                filteredUsersList.addAll (GlobalVariables.userDataList);
                sortFilteredProfilesListByDistAndUpdateAllDist ();
                gridAdapter.notifyDataSetChanged ();
            } else {
                StaticMethods.updateDeviceLocationGPS (context, mainPageActivity);
            }
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        openUserPage (position);
    }

    public static void openUserPage(int position) {
        Bundle b = new Bundle ();
        Intent intent = new Intent (context, UserPage.class);
        UserDetails user = filteredUsersList.get (position);
        intent.putExtra ("userName", user.name);
        intent.putExtra ("userCurrent", StaticMethods.isCurrentUser (user));
        b.putString ("userID", user.getUserPhoneNum ());
        b.putInt ("index", user.getIndexInAllDataList ());
        Date currentDate = new Date ();
        long diff = currentDate.getTime () - user.getLastSeen ().getTime ();
        long diffMinutes = diff / (60 * 1000);
        b.putInt ("online", (int) diffMinutes);
        intent.putExtras (b);
        context.startActivity (intent);
    }

    public void goToMessages(View view) {
        if (!StaticMethods.isGuestUser ()) {
            Intent intent = new Intent (this, CommunicationsActivity.class);
            startActivity (intent);
        } else {
            Toast.makeText (getApplicationContext (), getResources ().getString (R.string.pleaseProfile), Toast.LENGTH_SHORT).show ();
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
        if (getIntent ().getStringExtra ("fundigo") != null &&
                    getIntent ().getStringExtra ("fundigo").equals ("fun") &&
                    GlobalVariables.userDataList.size () > 0) {
            UserDetails userDetails = StaticMethods.getUserFromPhoneNum (getIntent ().getStringExtra ("index"));
            if (userDetails != null) {
                int position = userDetails.getIndexInAllDataList ();
                Bundle b = new Bundle ();
                Intent intent = new Intent (context, UserPage.class);
                UserDetails user = GlobalVariables.userDataList.get (position);
                intent.putExtra ("userName", user.name);
                intent.putExtra ("userCurrent", StaticMethods.isCurrentUser (user));
                b.putString ("userID", user.getUserPhoneNum ());
                b.putInt ("index", user.getIndexInAllDataList ());
                Date currentDate = new Date ();
                long diff = currentDate.getTime () - user.getLastSeen ().getTime ();
                long diffMinutes = diff / (60 * 1000);
                b.putInt ("online", (int) diffMinutes);
                intent.putExtras (b);
                context.startActivity (intent);
            }
            getIntent ().putExtra ("fundigo", "none");
        } else if (getIntent ().getStringExtra ("fundigo") != null &&
                           getIntent ().getStringExtra ("fundigo").equals ("fun")) {
            GlobalVariables.userPhoneNumFromFundigo = getIntent ().getStringExtra ("index");
            Intent intent = new Intent (MainPageActivity.this, MainActivity.class);
            startActivity (intent);
            getIntent ().putExtra ("fundigo", "none");
            finish ();
        }
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

    public static void sortFilteredProfilesListByDistAndUpdateAllDist() {
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
                double distance = (double) GlobalVariables.MY_LOCATION.distanceTo (userLocation);
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
        if (firstTimeGotLocation) {
            filteredUsersList.clear ();
            filteredUsersList.addAll (GlobalVariables.userDataList);
            sortFilteredProfilesListByDistAndUpdateAllDist ();
            gridAdapter.notifyDataSetChanged ();
            firstTimeGotLocation = false;
            if (GlobalVariables.userPhoneNumFromFundigo != null) {
                UserDetails userDetails = StaticMethods.getUserFromPhoneNum (GlobalVariables.userPhoneNumFromFundigo);
                if (userDetails != null) {
                    int position = userDetails.getIndexInAllDataList ();
                    Bundle b = new Bundle ();
                    Intent intent = new Intent (context, UserPage.class);
                    UserDetails user = GlobalVariables.userDataList.get (position);
                    intent.putExtra ("userName", user.name);
                    intent.putExtra ("userCurrent", StaticMethods.isCurrentUser (user));
                    b.putString ("userID", user.getUserPhoneNum ());
                    b.putInt ("index", user.getIndexInAllDataList ());
                    Date currentDate = new Date ();
                    long diff = currentDate.getTime () - user.getLastSeen ().getTime ();
                    long diffMinutes = diff / (60 * 1000);
                    b.putInt ("online", (int) diffMinutes);
                    intent.putExtras (b);
                    context.startActivity (intent);
                    GlobalVariables.userPhoneNumFromFundigo = null;
                }
            }
        }
        turnOnGPS.setVisibility (View.GONE);
        if (numOfAccuracyChanges % 50 == 0) {
            int locationAccuracyInt = (int) GlobalVariables.LOCATION_ACCURACY;
            GlobalVariables.Last_SHOWED_LOCATION_ACCURACY = locationAccuracyInt;
            gpsAccuracy.setText (locationAccuracyInt + "m");
            gpsAccuracy.setVisibility (View.VISIBLE);
            gpsAccuracyIcon.setVisibility (View.VISIBLE);
        }
        numOfAccuracyChanges++;
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
            ParseQuery<Profile> query = new ParseQuery ("Profile");
            query.orderByDescending ("createdAt");
            query.findInBackground (new FindCallback<Profile> () {
                public void done(List<Profile> profilesList, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < profilesList.size (); i++) {
                            Profile profile = profilesList.get (i);
                            for (UserDetails userDetails : GlobalVariables.userDataList) {
                                if (userDetails.getUserPhoneNum ().equals (profile.getNumber ())) {
                                    userDetails.setLastSeen (profile.getLastSeen ());
                                    userDetails.setUserLocation (profile.getLocation ());
                                    break;
                                }
                            }
                        }
                    } else {
                        e.printStackTrace ();
                    }
                }
            });
            handler.postDelayed (this, 10000);
        }
    };
}