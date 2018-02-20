package com.denzcoskun.steemtrackerandroid;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denzcoskun.steemtrackerandroid.models.ProfileModel;
import com.denzcoskun.steemtrackerandroid.models.UserModel;
import com.denzcoskun.steemtrackerandroid.profile.adapters.TabsPagerAdapter;
import com.denzcoskun.steemtrackerandroid.transformations.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

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


    private TabsPagerAdapter tabsPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        linearLayoutProfile.post((Runnable) () -> {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearLayoutProfile.getWidth(), linearLayoutProfile.getHeight());
            coverImage.setLayoutParams(layoutParams);
        });

        ProfileModel profileModel = (ProfileModel) getIntent().getSerializableExtra("model");

        Picasso.with(this)
                .load(profileModel.profileImage)
                .transform(new PicassoCircleTransformation())
                .into(profileImage);

        Picasso.with(this)
                .load(profileModel.coverImage)
                .fit()
                .centerCrop()
                .into(coverImage);

        textViewUsername.setText(profileModel.name);
        textViewAbout.setText(profileModel.about);
        textViewLocation.setText(profileModel.location);

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getBaseContext());

        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }

        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
