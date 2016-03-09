package com.example.mipo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    MessagesRoomFragment tab1;
    TracksActivity tab2;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super (fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                tab1 = new MessagesRoomFragment ();
                return tab1;
            case 1:
                tab2 = new TracksActivity ();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}