package com.design.movie.Adapter;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.design.movie.Fragment.TabFragment;
import com.design.movie.Listener.CustomListener;

public class TabPagerAdapter extends FragmentPagerAdapter {

    CustomListener listener;
    public TabPagerAdapter(FragmentManager fm, CustomListener listener) {
        super(fm);
        this.listener=listener;
    }
    private String getStringFromPos(int position){
        switch(position){
            case 0:return "ACTION";
            case 1:return "ADVENTURE";
            case 2:return "COMEDY";
            default:return "notFoundPositionInTab";
        }
    }
    @Override
    public Fragment getItem(int position) {
        TabFragment tabFragment = new TabFragment(listener);
        Bundle bundle = new Bundle();
        bundle.putString("TabName", getStringFromPos(position));
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}