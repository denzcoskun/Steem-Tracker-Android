package com.denzcoskun.steemtrackerandroid.screens.profile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denzcoskun.libdenx.activities.BaseActivity;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.screens.profile.models.ConvertModel;
import com.denzcoskun.steemtrackerandroid.screens.profile.models.ProfileModel;
import com.denzcoskun.steemtrackerandroid.screens.profile.adapters.TabsPagerAdapter;
import com.denzcoskun.steemtrackerandroid.helpers.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.profile_image)
    ImageView profileImage;

    @BindView(R.id.cover_image)
    ImageView coverImage;

    @BindView(R.id.text_view_username)
    TextView textViewUsername;

    @BindView(R.id.text_view_about)
    TextView textViewAbout;

    @BindView(R.id.text_view_location)
    TextView textViewLocation;

    @BindView(R.id.linear_layout_profile)
    LinearLayout linearLayoutProfile;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.image_view_location)
    ImageView imageViewLocation;

    @BindView(R.id.back_button)
    ImageButton backButton;

    public static String balance;
    public static String sbdbalance;
    public static ConvertModel convertModel;
    private TabsPagerAdapter tabsPagerAdapter = null;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent){
        super.onViewReady(savedInstanceState,intent);

        linearLayoutProfile.post(() -> {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearLayoutProfile.getWidth(), linearLayoutProfile.getHeight());
            coverImage.setLayoutParams(layoutParams);
        });

        ProfileModel profileModel = (ProfileModel) getIntent().getSerializableExtra("model");
        balance = getIntent().getStringExtra("balance");
        sbdbalance = getIntent().getStringExtra("sbdBalance");

        Picasso.with(this)
                .load(profileModel.profileImage)
                .transform(new PicassoCircleTransformation())
                .into(profileImage);

        Picasso.with(this)
                .load(profileModel.coverImage)
                .fit()
                .centerCrop()
                .into(coverImage);

        if (profileModel.name == null) {
            textViewUsername.setVisibility(View.GONE);
        } else {
            textViewUsername.setText(profileModel.name);
        }

        if (profileModel.about == null) {
            textViewAbout.setVisibility(View.GONE);
        } else {
            textViewAbout.setText(profileModel.about);
        }

        if (profileModel.location == null) {
            textViewLocation.setVisibility(View.GONE);
            imageViewLocation.setVisibility(View.GONE);
        } else {
            textViewLocation.setText(profileModel.location);
        }

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getBaseContext());

        tabLayout.addTab(tabLayout.newTab());

        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        backButton.setOnClickListener(v -> finish());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_profile;
    }

}
