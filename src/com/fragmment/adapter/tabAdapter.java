package com.fragmment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class tabAdapter extends FragmentPagerAdapter {
 
    public tabAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // add fragment activity
            return new addFragment();
        case 1:
            // list fragment activity
            return new ListFragment();
        case 2:
        	//Alert fragment
        	return new AlertFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
   /* @Override 
    public int getItemPosition(Object object){
    	return POSITION_NONE;
    }*/
 
}