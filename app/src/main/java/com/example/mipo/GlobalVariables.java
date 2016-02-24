package com.example.mipo;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    public static String CUSTOMER_PHONE_NUM = null;
    public static Location MY_LOCATION = null;
    public static List<UserDetails> userDataList = new ArrayList<UserDetails> ();
    public static UserDetails currentUser;
}
