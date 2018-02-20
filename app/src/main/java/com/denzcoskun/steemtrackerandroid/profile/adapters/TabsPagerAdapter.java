package com.denzcoskun.steemtrackerandroid.profile.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.denzcoskun.steemtrackerandroid.profile.fragments.PostFragment;

import java.util.List;

/**
 * Created by Denx on 21.02.2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    Context context = null;
    public TabsPagerAdapter(FragmentManager fragmentManager, Context context ) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        return new PostFragment();
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Posts";
    }

}