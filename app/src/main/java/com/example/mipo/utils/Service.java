package com.example.mipo.utils;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.mipo.model.Profile;
import com.example.mipo.model.ProfileBean;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sprintzin on 04/02/2016.
 */
public class Service extends IntentService {

    public static final String TAG = "Service";
    public static final String ACTION_1 = "com.myApp.action.ACTION_1";
    public static final String ACTION_2 = "com.myApp.action.ACTION_2";
    public static List<ProfileBean> newList = new ArrayList<ProfileBean>();
    private String objectId;

    public Service() {
        super("Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_1.equals(action)) {
                objectId = intent.getStringExtra("ObjectId");
                Log.e(TAG, "Ob "+ objectId);
                if(objectId != null) {
                    getProfileData();
                }



            }
        }
    }

    private void getProfileData() {
        final ArrayList<ProfileBean> tempProfileBeans = new ArrayList<ProfileBean>();
        ParseQuery<Profile> query = new ParseQuery("Profile");
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<Profile>() {
            public void done(List<Profile> profileParses, ParseException e) {
                if (e == null) {
                    ParseFile imageFile;
                    byte[] data = null;
                    Bitmap bmp;
                    //  Bitmap resized = null;
                    for (int i = 0; i < profileParses.size(); i++) {
                        imageFile = (ParseFile) profileParses.get(i).get("pic");
                        if (imageFile != null) {
                            try {
                                data = imageFile.getData();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            // resized = Bitmap.createScaledBitmap(bmp, 703, 1000, true);


                        } else {
                            bmp = null;
                        }
                        if (ParseUser.getCurrentUser().getObjectId().equals(profileParses.get(i).getObjectId())) {
                            tempProfileBeans.add(new ProfileBean(
                                    profileParses.get(i).getName(),
                                    profileParses.get(i).getObjectId(),
                                    profileParses.get(i).getOn_off(),
                                    "0",
                                    profileParses.get(i).getSeen(),
                                    profileParses.get(i).getAge(),
                                    profileParses.get(i).getStatus(),
                                    profileParses.get(i).getHeight(),
                                    profileParses.get(i).getWeight(),
                                    profileParses.get(i).getNation(),
                                    profileParses.get(i).getBody_type(),
                                    profileParses.get(i).getRelationship_status(),
                                    profileParses.get(i).getLooking_for(),
                                    profileParses.get(i).getAbout(),
                                    profileParses.get(i).getImageId(),
                                    bmp,
                                    profileParses.get(i).getMessage_roomId(),
                                    profileParses.get(i).getDistanceType(),
                                    profileParses.get(i).isFavorite(),
                                    profileParses.get(i).isFilteredOK()));

                        } else {
                            tempProfileBeans.add(new ProfileBean(
                                    profileParses.get(i).getName(),
                                    profileParses.get(i).getObjectId(),
                                    profileParses.get(i).getOn_off(),
                                    profileParses.get(i).getDistance(),
                                    profileParses.get(i).getSeen(),
                                    profileParses.get(i).getAge(),
                                    profileParses.get(i).getStatus(),
                                    profileParses.get(i).getHeight(),
                                    profileParses.get(i).getWeight(),
                                    profileParses.get(i).getNation(),
                                    profileParses.get(i).getBody_type(),
                                    profileParses.get(i).getRelationship_status(),
                                    profileParses.get(i).getLooking_for(),
                                    profileParses.get(i).getAbout(),
                                    profileParses.get(i).getImageId(),
                                    bmp,
                                    profileParses.get(i).getMessage_roomId(),
                                    profileParses.get(i).getDistanceType(),
                                    profileParses.get(i).isFavorite(),
                                    profileParses.get(i).isFilteredOK()));
                        }
                        if (bmp != null) {
                            bmp.recycle();
                        }


                    }
                    newList.clear();
                    newList.addAll(tempProfileBeans);
                    Toast.makeText(Service.this, "Service done!!!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "newList is " + newList);
                    // adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Error getting results " + e.toString());
                }
            }
        });
    }
}
