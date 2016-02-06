package com.example.mipo;

import android.graphics.Bitmap;

public class User {

    Bitmap imageId;
    String name;
    int on_off;
    boolean currentUser;
    String id;
    int indexInUD;
    UserDetails userDetails;

    public User(Bitmap imageId,
                String name,
                int on_off,
                boolean currentUser,
                String id,
                int indexInUD,
                UserDetails userDetails) {
        this.imageId = imageId;
        this.name = name;
        this.on_off = on_off;
        this.currentUser = currentUser;
        this.id = id;
        this.indexInUD = indexInUD;
        this.userDetails = userDetails;
    }

    public String getId() {
        return id;
    }

    public int getIndexInUD() {
        return indexInUD;
    }

    public void setIndexInUD(int indexInUD) {
        this.indexInUD = indexInUD;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}