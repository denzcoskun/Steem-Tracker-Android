package com.denzcoskun.steemtrackerandroid.profile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.steemtrackerandroid.R;

/**
 * Created by Denx on 21.02.2018.
 */

public class PostFragment extends Fragment {

    private int position;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_fragment, viewGroup, false);
        return view;
    }
}
