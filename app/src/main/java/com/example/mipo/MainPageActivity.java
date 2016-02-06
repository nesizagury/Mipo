package com.example.mipo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mipo.model.Profile;
import com.example.mipo.model.ProfileBean;
import com.example.mipo.ui.MainPageAdapter;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainPageActivity extends Activity implements AdapterView.OnItemClickListener {

    GridView grid;
    public final static List<User> firstUsersList = new ArrayList<User> ();
    public final static List<User> filteredUsersList = new ArrayList<User> ();
    public static List<ProfileBean> newList = new ArrayList<ProfileBean> ();
    public static List<UserDetails> userDataList = new ArrayList<UserDetails> ();
    static int conversationId;
    static int otherConversationId;
    static UserDetails currentUser;
    static int currUserIndex;
    static boolean didInit = false;
    private static final String TAG = "MainPageActivity";
    MainPageAdapter mainPageAdapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        this.requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView (R.layout.activity_main_page);
        grid = (GridView) findViewById (R.id.gridView1);
        if (!didInit) {
//            uploadUserData();
            getProfileData ();
            addToList ();
            didInit = true;
            firstUsersList.addAll (filteredUsersList);
        }
        grid.setAdapter (new GridAdapter (this, filteredUsersList));
        //  grid.setAdapter(new MainPageAdapter(this, newList));
        grid.setOnItemClickListener (this);
    }

    private void uploadUserData() {
        InputStream is;
        BufferedReader input;
        List<String> list;
        boolean newUser = true;
        for (int i = 0; i <= 26; i++) {
            is = this.getResources ().openRawResource (R.raw.user0 + i);
            input = new BufferedReader (new InputStreamReader (is), 1024 * 8);
            String line;
            list = new ArrayList<String> ();
            try {
                int j = 0;
                while ((line = input.readLine ()) != null) {
                    if (j % 2 == 0)
                        list.add (line);
                    j++;
                }
            } catch (IOException e) {
                e.printStackTrace ();
            }
//            if (ParseUser.getCurrentUser ().getObjectId ().equals (list.get (1))) {
//                userDataList.add (new UserDetails (list.get (0), list.get (1), list.get (2), "0", list.get (4), list.get (5), list.get (6),
//                                                          list.get (7), list.get (8), list.get (9), list.get (10), list.get (11),
//                                                          list.get (12), list.get (13), Integer.parseInt (list.get (14)),
//                                                          Integer.parseInt (list.get (15)), Integer.parseInt (list.get (16))));
//            } else {
//                userDataList.add (new UserDetails (list.get (0), list.get (1), list.get (2), list.get (3), list.get (4), list.get (5), list.get (6),
//                                                          list.get (7), list.get (8), list.get (9), list.get (10), list.get (11),
//                                                          list.get (12), list.get (13), Integer.parseInt (list.get (14)),
//                                                          Integer.parseInt (list.get (15)), Integer.parseInt (list.get (16))));
//            }

            // Log.e(TAG, "list " + list);
            //===================================================================
//            final Profile profile = new Profile();
//            profile.put("name", list.get(0));
//            profile.put("id", list.get(1));
//            if (list.get(2).equals("Online")){
//                profile.put("isOnline", true);
//            }else{
//                profile.put("isOnline", false);
//            }
//
//            profile.put("distance", list.get(3));
//            profile.put("seen", list.get(4));
//            profile.put("age", list.get(5));
//            profile.put("status", list.get(6));
//            profile.put("height", list.get(7));
//            profile.put("weight", list.get(8));
//            profile.put("nation", list.get(9));
//            profile.put("body_type", list.get(10));
//            profile.put("relationship_status", list.get(11));
//            profile.put("looking_for", list.get(12));
//            profile.put("about", list.get(13));
//            profile.put("imageId", list.get(14));
//            profile.put("message_roomId", list.get(15));
//            profile.put("distanceType", list.get(16));
//            profile.put("isFavorite", false);
//            profile.put("isFilteredOK", false);
//            Bitmap bitmap;
//            ByteArrayOutputStream stream;
//            byte[] image;
//            final ParseFile file;
//
//
//            switch (Integer.parseInt(list.get(14))) {
//
//                case 0:
//                    bitmap = BitmapFactory.decodeResource(getResources(),
//                            R.drawable.me0);
//                    // Convert it to byte
//                    stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
//                    image = stream.toByteArray();
//                    file = new ParseFile("picturePath", image);
//                    file.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if(e == null){
//                                profile.put("pic", file);
//                                profile.saveInBackground(new SaveCallback() {
//                                    @Override
//                                    public void done(ParseException e) {
//                                        if (e == null) {
//                                            final BigPicture bigPicture = new BigPicture();
//                                            Log.e(TAG, "Object id of "+profile.getName()+" profile is "+ profile.getObjectId()+" get id is "+ profile.getObjectId());
//                                            bigPicture.put("num", profile.getObjectId());
//                                            bigPicture.put("name", profile.getName());
//                                            Bitmap bitmap;
//                                            ByteArrayOutputStream stream;
//                                            byte[] image;
//                                            final ParseFile file;
//                                            bitmap = BitmapFactory.decodeResource(getResources(),
//                                                    R.drawable.me0);
//                                            // Convert it to byte
//                                            stream = new ByteArrayOutputStream();
//                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
//                                            image = stream.toByteArray();
//                                            file = new ParseFile("picturePath.jpg", image);
//                                            file.saveInBackground(new SaveCallback() {
//                                                @Override
//                                                public void done(ParseException e) {
//                                                    if (e == null) {
//                                                        bigPicture.put("pic", file);
//                                                        bigPicture.put("parent", ParseObject.createWithoutData("Profile", profile.getObjectId()));
//                                                        Log.e(TAG, "id in callback of saving file "+ profile.getObjectId());
//                                                        bigPicture.saveInBackground(new SaveCallback() {
//                                                            @Override
//                                                            public void done(ParseException e) {
//                                                                if (e == null) {
//                                                                    Log.e(TAG, "Big picture class saved "+ bigPicture.getName()+ " id is "+ bigPicture.getNum());
//
//                                                                }else{
//                                                                    Log.e(TAG, "Big Picture class not saved!!! " + bigPicture.getName()+" exception "+ e.toString());
//
//                                                                }
//                                                            }
//                                                        });
//                                                        Log.e(TAG, "Big picture saved "+ bigPicture.getName());
//
//                                                    }else{
//                                                        Log.e(TAG, "Big Picture not saved!!! " + bigPicture.getName()+ " exception "+ e.toString());
//
//                                                    }
//                                                }
//                                            });
//                                            bitmap.recycle();
//                                            Log.e(TAG, "Profile saved "+ profile.getName()+ " pic number "+ profile.getImageId());
//                                        } else {
//                                            Log.e(TAG, "Not saved!!! " + profile.getName() + " pic number " + profile.getImageId());
//                                        }
//                                    }
//                                });
//                            }else{
//                                Log.e(TAG, "pic not saved "+ e.toString());
//                            }
//                        }
//                    });
//                    bitmap.recycle();
//
//                    break;
//
//            }

            //==========================================================================================


        }
        //Sorting
        Collections.sort (userDataList, new Comparator<UserDetails> ()

                {
                    @Override
                    public int compare(UserDetails user1, UserDetails user2) {
                        Double dist1, dist2;
                        if (user1.getDistanceType () == 0) {
                            dist1 = Double.parseDouble (user1.getDistance ());
                        } else {
                            dist1 = Double.parseDouble (user1.getDistance ()) * 1000.0;
                        }
                        if (user2.getDistanceType () == 0) {
                            dist2 = Double.parseDouble (user2.getDistance ());
                        } else {
                            dist2 = Double.parseDouble (user2.getDistance ()) * 1000.0;
                        }
                        if (dist1 > dist2) {
                            return 1;
                        } else if (dist1 < dist2) {
                            return -1;
                        }
                        return 0;
                    }
                }

        );

        for (
                int i = 0;
                i <= 26; i++)

        {
            if (ParseUser.getCurrentUser ().getObjectId ().equals (userDataList.get (i).getId ())) {
                currUserIndex = i;
                conversationId = userDataList.get (i).getMessage_roomId ();
                newUser = false;
                currentUser = userDataList.get (i);
                break;
            }
        }

        if (newUser)

        {
            Random r = new Random ();
            int Low = 0;
            int High = 27;
            currUserIndex = r.nextInt (High - Low) + Low;
            UserDetails userDetails = userDataList.get (currUserIndex);
            userDataList.remove (currUserIndex);
            userDataList.add (0, userDetails);
            currentUser = userDataList.get (0);
            conversationId = userDataList.get (0).getMessage_roomId ();
            currentUser.setDistance ("0");
            currentUser.setDistanceType (0);
        }

        for (
                int i = 7;
                i < 12; i++)

        {
            if (i != currUserIndex) {
                userDataList.get (i).setFavorite (true);
            }
        }

    }


    private void getProfileData() {
        final ArrayList<UserDetails> tempProfileBeans = new ArrayList<UserDetails> ();
        ParseQuery<Profile> query = new ParseQuery ("Profile");
        query.orderByDescending ("createdAt");
        dialog = new ProgressDialog (this);
        dialog.setMessage ("Loading...");
        dialog.show ();
        List<Profile> list = null;
        try {
            list = query.find ();
            ParseFile imageFile;
            byte[] data = null;
            Bitmap bmp;
            //  Bitmap resized = null;
            for (int i = 0; i < 5; i++) {
                imageFile = (ParseFile) list.get (i).get ("pic");
                if (imageFile != null) {
                    try {
                        data = imageFile.getData ();
                    } catch (ParseException e1) {
                        e1.printStackTrace ();
                    }
                    bmp = BitmapFactory.decodeByteArray (data, 0, data.length);
                } else {
                    bmp = null;
                }
                if (ParseUser.getCurrentUser ().getObjectId ().equals (list.get (i).getObjectId ())) {
                    tempProfileBeans.add (new UserDetails (
                                                                  list.get (i).getName (),
                                                                  list.get (i).getObjectId (),
                                                                  "Online",
                                                                  list.get (i).getAge (),
                                                                  list.get (i).getSeen (),
                                                                  "6",
                                                                  list.get (i).getStatus (),
                                                                  list.get (i).getHeight (),
                                                                  list.get (i).getWeight (),
                                                                  list.get (i).getNation (),
                                                                  list.get (i).getBody_type (),
                                                                  list.get (i).getRelationship_status (),
                                                                  list.get (i).getLooking_for (),
                                                                  list.get (i).getAbout (),
                                                                  bmp,
                                                                  Integer.parseInt (list.get (i).getMessage_roomId ()),
                                                                  1));

                } else {
                    tempProfileBeans.add (new UserDetails (
                                                                  list.get (i).getName (),
                                                                  list.get (i).getObjectId (),
                                                                  "Online",
                                                                  list.get (i).getAge (),
                                                                  list.get (i).getSeen (),
                                                                  "6",
                                                                  list.get (i).getStatus (),
                                                                  list.get (i).getHeight (),
                                                                  list.get (i).getWeight (),
                                                                  list.get (i).getNation (),
                                                                  list.get (i).getBody_type (),
                                                                  list.get (i).getRelationship_status (),
                                                                  list.get (i).getLooking_for (),
                                                                  list.get (i).getAbout (),
                                                                  bmp,
                                                                  Integer.parseInt (list.get (i).getMessage_roomId ()),
                                                                  1));
                }
            }
            Random r = new Random ();
            int Low = 0;
            int High = tempProfileBeans.size ();
            currUserIndex = r.nextInt (High - Low) + Low;
            UserDetails userDetails = tempProfileBeans.get (currUserIndex);
            tempProfileBeans.remove (currUserIndex);
            tempProfileBeans.add (0, userDetails);
            currUserIndex = 0;
            currentUser = tempProfileBeans.get (0);
            conversationId = tempProfileBeans.get (0).getMessage_roomId ();
            currentUser.setDistance ("0");
            currentUser.setDistanceType (0);
            userDataList.clear ();
            userDataList.addAll (tempProfileBeans);
            dialog.dismiss ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }


    public static void addToList() {
        filteredUsersList.add (new User (currentUser.getImageId (),
                                                userDataList.get (currUserIndex).getName (), 0,
                                                true, userDataList.get (currUserIndex).id, currUserIndex
                                                , userDataList.get (0)));
        filteredUsersList.get (0).getUserDetails ().setIsFilteredOK (true);
        for (int i = 1; i < 5; i++) {
            if (userDataList.get (i).getOn_off ().equals ("Online"))
                filteredUsersList.add (new User (userDataList.get(i).getImageId (),
                                                        userDataList.get (i).getName (),
                                                        R.drawable.online, false, userDataList.get (i).id, i,
                                                        userDataList.get (i)));
            else {
                filteredUsersList.add (new User (userDataList.get(i).getImageId (),
                                                        userDataList.get (i).getName (),
                                                        0, false, userDataList.get (i).id, i
                                                        , userDataList.get (i)));
            }
            filteredUsersList.get (i).getUserDetails ().setIsFilteredOK (true);
        }
    }

    public static User getUser(int i) {
        return filteredUsersList.get (i);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle b = new Bundle ();
        Intent intent = new Intent (this, UserPage.class);
        User user = filteredUsersList.get (position);
        intent.putExtra ("userImage", user.imageId);
        intent.putExtra ("userName", user.name);
        intent.putExtra ("userCurrent", user.currentUser);
        b.putString ("userID", filteredUsersList.get (position).id);
        b.putInt ("index", user.getIndexInUD ());
        b.putInt ("online", user.on_off);
        intent.putExtras (b);
        startActivity (intent);
    }

    public void goToMessages(View view) {
        Intent intent = new Intent (this, MessagesRoom.class);
        Bundle b = new Bundle ();
        b.putInt ("otherConvId", otherConversationId);
        startActivity (intent);
    }

    public void goToFavorites(View view) {
        Intent i = new Intent (this, FavoritesPage.class);
        startActivity (i);
    }

    public void goToFilter(View v) {
        Intent intent = new Intent (this, FilterActivity.class);
        startActivity (intent);
    }


//    public static void ref()
//    {
//        GridAdapter gridAdaptor = (GridAdapter)grid.getAdapter ();
//        gridAdaptor.notifyDataSetChanged ();
//    }
}
