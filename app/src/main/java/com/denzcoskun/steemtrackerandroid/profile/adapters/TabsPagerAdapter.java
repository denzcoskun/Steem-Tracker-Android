package com.denzcoskun.steemtrackerandroid.profile.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.denzcoskun.steemtrackerandroid.profile.fragments.RewardFragment;

/**
 * Created by Denx on 21.02.2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fragmentManager, Context context ) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        return new RewardFragment();
    }

    @Override
    public int getCount() {

        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Rewards";
    }

}