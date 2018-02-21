package com.denzcoskun.steemtrackerandroid.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.denzcoskun.steemtrackerandroid.AppSingleton;
import com.denzcoskun.steemtrackerandroid.MainActivity;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.constants.CurrencyConstants;
import com.denzcoskun.steemtrackerandroid.models.ConvertModel;
import com.denzcoskun.steemtrackerandroid.models.ProfileModel;
import com.denzcoskun.steemtrackerandroid.models.UserModel;
import com.denzcoskun.steemtrackerandroid.profile.adapters.TabsPagerAdapter;
import com.denzcoskun.steemtrackerandroid.transformations.PicassoCircleTransformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONObject;

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

    @BindView(R.id.back_button)
    ImageButton backButton;

    ProgressDialog pDialog;
    String balance;
    String sbdbalance;
    ConvertModel convertModel;
    private TabsPagerAdapter tabsPagerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);

        linearLayoutProfile.post((Runnable) () -> {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(linearLayoutProfile.getWidth(), linearLayoutProfile.getHeight());
            coverImage.setLayoutParams(layoutParams);
        });

        ProfileModel profileModel = (ProfileModel) getIntent().getSerializableExtra("model");
        balance = getIntent().getStringExtra("balance");
        sbdbalance = getIntent().getStringExtra("sbdbalance");

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

        backButton.setOnClickListener(v -> finish());

    }

    private void makeRequestConvert(String balance, String currency){
        showpDialog();
        String dayUrl = getString(R.string.api_coin_market)+"/?convert=" + currency;
        JsonArrayRequest userRequest = new JsonArrayRequest
                (Request.Method.GET, dayUrl, null, response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JSONArray jsonArray = response;
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        convertModel = mapper.readValue(jsonObject.toString(), ConvertModel.class);
                        if (currency.equalsIgnoreCase(CurrencyConstants.LIRA)){
                            calculateAmount(balance, convertModel.priceTry);
                        }else if(currency.equalsIgnoreCase(CurrencyConstants.EURO)){
                            calculateAmount(balance, convertModel.priceEur);
                        }else if(currency.equalsIgnoreCase(CurrencyConstants.RUBLE)){
                            calculateAmount(balance, convertModel.priceRub);
                        }else{
                            calculateAmount(balance, convertModel.priceUsd);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hidepDialog();
                }, error -> {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    System.out.println( error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    hidepDialog();
                });
        AppSingleton.getInstance().addToRequestQueue(userRequest);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public String calculateAmount(String balance, String value){
        Double total = Double.parseDouble(balance)*Double.parseDouble(value);
        return Double.toString(total);
    }
}
