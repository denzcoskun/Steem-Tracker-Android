package com.denzcoskun.steemtrackerandroid.profile.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.denzcoskun.steemtrackerandroid.AppSingleton;
import com.denzcoskun.steemtrackerandroid.R;
import com.denzcoskun.steemtrackerandroid.constants.CoinConstants;
import com.denzcoskun.steemtrackerandroid.constants.CurrencyConstants;
import com.denzcoskun.steemtrackerandroid.models.ConvertModel;
import com.denzcoskun.steemtrackerandroid.profile.ProfileActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Denx on 21.02.2018.
 */

public class RewardFragment extends Fragment {

    @BindView(R.id.segmented)
    SegmentedGroup segmentedGroup;

    @BindView(R.id.usd_button)
    RadioButton usdButton;

    @BindView(R.id.euro_button)
    RadioButton eurButton;

    @BindView(R.id.try_button)
    RadioButton tryButton;

    @BindView(R.id.rub_button)
    RadioButton rubButton;

    @BindView(R.id.text_view_steem_value)
    TextView textViewSteemValue;

    @BindView(R.id.text_view_sbd_value)
    TextView textViewSbdValue;

    @BindView(R.id.text_view_calculated_value)
    TextView textViewCalculatedValue;

    ProgressDialog pDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        segmentedGroup.setTintColor(Color.parseColor("#04d6a7"), Color.parseColor("#424242"));


        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.loading));
        pDialog.setCancelable(false);

        makeRequestConvert(ProfileActivity.sbdbalance,CurrencyConstants.DOLLAR);

        textViewSteemValue.setText(ProfileActivity.balance);
        textViewSbdValue.setText(ProfileActivity.sbdbalance);

        usdButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                makeRequestConvert(ProfileActivity.sbdbalance,CurrencyConstants.DOLLAR);
            }
        });
        tryButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                makeRequestConvert(ProfileActivity.sbdbalance,CurrencyConstants.LIRA);
            }
        });
        eurButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                makeRequestConvert(ProfileActivity.sbdbalance,CurrencyConstants.EURO);
            }
        });
        rubButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                makeRequestConvert(ProfileActivity.sbdbalance,CurrencyConstants.RUBLE);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reward_fragment, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void makeRequestConvert(String balance, String currency){
        showpDialog();
        String dayUrl = getString(R.string.api_coin_market)+ CoinConstants.STEEMDOLLAR+"/?convert=" + currency;
        JsonArrayRequest userRequest = new JsonArrayRequest
                (Request.Method.GET, dayUrl, null, response -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JSONArray jsonArray = response;
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        ProfileActivity.convertModel = mapper.readValue(jsonObject.toString(), ConvertModel.class);
                        if (currency.equalsIgnoreCase(CurrencyConstants.LIRA)){
                            textViewCalculatedValue.setText(calculateAmount(balance, ProfileActivity.convertModel.priceTry)+" "+getString(R.string.try_symbol));
                        }else if(currency.equalsIgnoreCase(CurrencyConstants.EURO)){
                            textViewCalculatedValue.setText(calculateAmount(balance, ProfileActivity.convertModel.priceEur)+" "+getString(R.string.eur_symbol));
                        }else if(currency.equalsIgnoreCase(CurrencyConstants.RUBLE)){
                            textViewCalculatedValue.setText(calculateAmount(balance, ProfileActivity.convertModel.priceRub)+" "+getString(R.string.rub_symbol));
                        }else if(currency.equalsIgnoreCase(CurrencyConstants.DOLLAR)){
                            textViewCalculatedValue.setText(calculateAmount(balance, ProfileActivity.convertModel.priceUsd)+" "+getString(R.string.usd_symbol));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hidepDialog();
                }, error -> {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    System.out.println( error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(),
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
        Double total = Double.parseDouble(balance.substring(0,4))*Double.parseDouble(value);
        return Double.toString(total);
    }
}
